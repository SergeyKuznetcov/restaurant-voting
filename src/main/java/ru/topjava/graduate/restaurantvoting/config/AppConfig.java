package ru.topjava.graduate.restaurantvoting.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.h2.tools.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.topjava.graduate.restaurantvoting.util.JsonUtil;

import java.sql.SQLException;

@Configuration
@Slf4j
public class AppConfig {
    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server h2Server() throws SQLException {
        log.info("Start H2 TCP server");
        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092");
    }

    //      https://stackoverflow.com/a/58327885
    @Bean
    public Hibernate5Module hibernate5Module() {
        return new Hibernate5Module();
    }

    //      https://stackoverflow.com/a/65362872
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        Hibernate5Module hibernate5Module = new Hibernate5Module();
        hibernate5Module.configure(Hibernate5Module.Feature.FORCE_LAZY_LOADING, false);
        // Enable below line to switch lazy loaded json from null to a blank object!
        //hibernate5Module.configure(Hibernate5Module.Feature.SERIALIZE_IDENTIFIER_FOR_LAZY_NOT_LOADED_OBJECTS, true);
        mapper.registerModule(hibernate5Module);
        mapper.registerModule(new JavaTimeModule());
        JsonUtil.setMapper(mapper);
        return mapper;
    }
}
