package de.cibek.mscasa.common.session.config;

import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * Connection to default redis address, where the session user is stored. 
 * TODO: Clear session table when changing session user, or it will come to problems when desializing
 */
@EnableRedisHttpSession
public class SessionConnectionConfig {

    @Bean
    public LettuceConnectionFactory connectionFactory() {
        return new LettuceConnectionFactory();
    }
}
