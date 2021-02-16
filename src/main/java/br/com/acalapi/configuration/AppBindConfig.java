package br.com.acalapi.configuration;

import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;

@Configuration
public class AppBindConfig {

    private static final String LocalDateFormat = "ddMMyyyy";
    private static final String LocalDateTimeFormat = "ddMMyyyyHHmmss";

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder -> {
            builder.simpleDateFormat(LocalDateFormat);
            builder.serializers(new LocalDateSerializer(DateTimeFormatter.ofPattern(LocalDateFormat)));
            builder.serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(LocalDateTimeFormat)));
        };
    }

}