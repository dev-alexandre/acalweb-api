package br.com.acalapi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AppConfig {

    @Value("${trust.password}")
    public String trustStorePassword;

    @Value("${certificado: /home/wwacal/java/acalcert.p12}")
    public String certificado;

}