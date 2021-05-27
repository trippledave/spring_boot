package de.cibek.mscasa.webrtc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;

@SpringBootApplication
@EnableBinding(Sink.class)
public class MsCasaWebRtcApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsCasaWebRtcApplication.class, args);
    }

    @StreamListener(Sink.INPUT)
    public void get(Message log) {
        System.out.println("LOG: " + log.getPayload());
    }

}
