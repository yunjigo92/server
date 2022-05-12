package com.yunji.server.controller;

import com.yunji.server.dto.Req;
import com.yunji.server.dto.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
@RestController
@RequestMapping("/api/server")
public class ServerApiController {


    @GetMapping("/naver")
    public String naver(){


        String query = "갈비집";

        //https://openapi.naver.com/v1/search/local.json?query=%EA%B0%88%EB%B9%84%EC%A7%91%0A&display=10&start=1&sort=random
        URI uri = UriComponentsBuilder
                .fromUriString("https://openapi.naver.com")
                .path("/v1/search/local.json")
                .queryParam("query",query)
                .queryParam("display",10)
                .queryParam("start",1)
                .queryParam("sort","random")
                .encode(Charset.forName("UTF-8"))
                .build()
                .toUri();

        RequestEntity<Void> req = RequestEntity
                .get(uri)
                .header("X-Naver-Client-Id","aRyjDlNoJdgzg9rMHKtq")
                .header("X-Naver-Client-Secret","8EsKcxb_a4")
                .build();

        RestTemplate restTemplate = new RestTemplate();

        log.info("log: {}",uri);
        ResponseEntity<String> resoponseEntity = restTemplate.exchange(req, String.class);

        return resoponseEntity.getBody();
    }


    @GetMapping("/hello")
    public User hello(@RequestParam String name, @RequestParam int age){

        User user = new User();
        user.setName(name);
        user.setAge(age);

        return user;
    }

    @PostMapping("/user/{userId}/name/{userName}")
    public User post(@RequestBody User user, @PathVariable int userId, @PathVariable String userName,
                    @RequestHeader("x-authorization") String authorization,
                    @RequestHeader("custom-header") String customHeader ){
        log.info("client request : {}", user);
        log.info("user name : {}, age : {}", userName, userId);
        log.info("user authorization : {}, custom : {}", authorization, customHeader);

        return user;
    }

    @PostMapping("/user/{userId}/name/{userName}/req")
    public Req post(@RequestBody Req<User> user, @PathVariable int userId, @PathVariable String userName,
                     @RequestHeader("x-authorization") String authorization,
                     @RequestHeader("custom-header") String customHeader ){
        log.info("client request : {}", user);
        log.info("user name : {}, age : {}", userName, userId);
        log.info("user authorization : {}, custom : {}", authorization, customHeader);

        Req<User> response = new Req();
        response.setHeader( new Req.Header());

        response.setBody(user.getBody());

        return response;
    }

}
