package br.com.acalapi.configuration;

import br.com.acalapi.AppConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.index.MongoPersistentEntityIndexResolver;
import org.springframework.data.mongodb.core.mapping.BasicMongoPersistentEntity;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class AppMongoConfiguration {

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private ResourceLoader resourceLoader;

    private final MongoConverter mongoConverter;

    private final MongoTemplate mongoTemplate;


    @EventListener(ApplicationReadyEvent.class)
    public void initIndicesAfterStartup() {

        log.info("Mongo InitIndicesAfterStartup init");
        var init = System.currentTimeMillis();

        var mappingContext = this.mongoConverter.getMappingContext();

        if (mappingContext instanceof MongoMappingContext) {
            MongoMappingContext mongoMappingContext = (MongoMappingContext) mappingContext;
            for (BasicMongoPersistentEntity<?> persistentEntity : mongoMappingContext.getPersistentEntities()) {
                var clazz = persistentEntity.getType();
                if (clazz.isAnnotationPresent(Document.class)) {
                    var resolver = new MongoPersistentEntityIndexResolver(mongoMappingContext);

                    var indexOps = mongoTemplate.indexOps(clazz);
                    resolver.resolveIndexFor(clazz).forEach(indexOps::ensureIndex);
                }
            }
        }

        log.info("Mongo InitIndicesAfterStartup take: {}", (System.currentTimeMillis() - init));
    }

    @Bean
    public RestTemplate restTemplate() throws Exception {
        Path certificado = Paths.get(appConfig.certificado);

        SSLContext sslContext = new SSLContextBuilder()
                .loadTrustMaterial(certificado.toFile(), appConfig.trustStorePassword.toCharArray())
                .build();

        SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext);

        HttpClient httpClient = HttpClients.custom().setSSLSocketFactory(socketFactory).build();

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);

        return new RestTemplate(factory);
    }

}