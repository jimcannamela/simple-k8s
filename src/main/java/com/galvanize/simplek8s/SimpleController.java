package com.galvanize.simplek8s;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleController {

    SimpleConfig simpleConfig;

    public SimpleController(SimpleConfig simpleConfig) {
        this.simpleConfig = simpleConfig;
    }

    @GetMapping("/")
    public SimpleResponse messagePath() {
        SimpleResponse simpleResponse = new SimpleResponse();
        simpleResponse.setMessage(simpleConfig.getMessage());
        return simpleResponse;
    }
}
