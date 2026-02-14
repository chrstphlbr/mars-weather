package net.laaber.marsweather.shared.config;

import net.laaber.marsweather.shared.json.CustomIntegerDeserializer;
import net.laaber.marsweather.shared.json.CustomStringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tools.jackson.databind.JacksonModule;
import tools.jackson.databind.module.SimpleModule;

@Configuration
public class JacksonConfig {
    @Bean
    public JacksonModule customJacksonModule() {
        var module = new SimpleModule();
        module.addDeserializer(Integer.class, new CustomIntegerDeserializer());
        module.addDeserializer(String.class, new CustomStringDeserializer());
        return module;
    }
}
