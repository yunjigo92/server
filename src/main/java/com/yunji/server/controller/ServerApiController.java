package com.yunji.server.controller;

import com.yunji.server.dto.Req;
import com.yunji.server.dto.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/server")
public class ServerApiController {


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
