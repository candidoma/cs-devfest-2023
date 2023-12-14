package com.candidoma.controller;

import com.candidoma.entity.Version;
import com.candidoma.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@Slf4j
public class VersionController {

    @Value("${app.version}")
    private String version;

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity version() {
        Version versionEntity=new Version(version);
        Response<Version> response=new Response<>();
        response.setBody(versionEntity);
        return ResponseEntity.ok(response);
    }
}
