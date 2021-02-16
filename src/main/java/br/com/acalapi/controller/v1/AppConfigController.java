package br.com.acalapi.controller.v1;

import br.com.acalapi.AppConfig;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api
@RestController
@RequestMapping("/appconfig")
public class AppConfigController {

    @Autowired
    private AppConfig appConfig;

}
