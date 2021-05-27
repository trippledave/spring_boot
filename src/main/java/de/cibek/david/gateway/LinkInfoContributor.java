package de.cibek.david.gateway;

import java.nio.charset.Charset;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class LinkInfoContributor implements InfoContributor {

    @Autowired
    private DiscoveryClient client;
    
    @Autowired
    private WebClient.Builder webClientBuilder;

    @Value("${spring.application.name}")
    private String appName;

    @Override
    public void contribute(Info.Builder builder) {
        final List<String> gatewayUrls = new LinkedList<>();
        client.getInstances(appName).forEach((ServiceInstance instance) -> {
            gatewayUrls.add(instance.getUri().toString());
        });

        final Map<String, Object> details = new HashMap<>();
        for (String baseUrl : gatewayUrls) {
            details.put("health", baseUrl + "/actuator/health");
            details.put("routes", baseUrl + "/actuator/gateway/routes");
        }
        
        details.put("beans", printBeans());
        
        details.put("rest", getInfo());
        
        builder.withDetail("More useful links", details);
    }


    @Autowired
    ApplicationContext applicationContext;

    public String[] printBeans() {
        return (applicationContext.getBeanDefinitionNames());
    }
    
    private String getInfo() {
        WebClient client3 = webClientBuilder
                .baseUrl("lb://DEMO3")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        WebClient.RequestBodySpec uri1 = client3
                .method(HttpMethod.GET)
                .uri("/api/v1/test");

        WebClient.ResponseSpec response1 = uri1
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML)
                .acceptCharset(Charset.forName("UTF-8"))
                .retrieve();

        return response1.bodyToMono(String.class).block();

    }
}
