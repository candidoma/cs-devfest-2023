package com.candidoma.controller;

import com.candidoma.entity.Calc;
import com.candidoma.response.Response;
import com.candidoma.utils.Pi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.BigInteger;

@RestController
@Slf4j
public class CalcController {
    private static int MAX_SLEEP_TIME = 20;

    @GetMapping(value = "/calc/pi/{digit}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity pi(@PathVariable("digit") int digit) {
        long startTime = System.nanoTime();
        BigDecimal pi = Pi.pi(digit);
        long endTime = System.nanoTime();
        long totalTime = endTime - startTime;
        Calc entity = new Calc(digit, pi.toString(),totalTime);
        Response<Calc> response=new Response<>();
        response.setBody(entity);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/calc/fact/{digit}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity factorial(@PathVariable("digit")int digit) {
        long startTime = System.nanoTime();
        BigInteger fact = BigInteger.ONE;
        for (int i = 2; i <= digit; i++)
            fact = fact.multiply(BigInteger.valueOf(i));
        long endTime = System.nanoTime();
        long totalTime = endTime - startTime;
        Calc entity = new Calc(digit, fact.toString(),totalTime);
        Response<Calc> response=new Response<>();
        response.setBody(entity);
        return ResponseEntity.ok(response);
    }
}
