package br.com.acalapi.controller;

import br.com.acalapi.AppConfig;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@Api
@RestController
@RequestMapping("/appconfig")
public class AppConfigController {

    @Autowired
    private AppConfig appConfig;

}
