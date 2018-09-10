package com.maomiyibian.microservice.customer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.maomiyibian.microservice.api.domain.User;
import com.maomiyibian.microservice.api.service.UserService;
import org.springframework.web.bind.annotation.*;

/**
 * TODO: 类描述
 *
 * @author junyunxiao
 * @date 2018-9-10 10:06
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Reference(version = "${service.version}",
            application = "${dubbo.application.id}",
            url = "dubbo://localhost:20880"
    )
    private UserService userService;


    @GetMapping("/login/{username}")
    @ResponseBody
    public User login(@PathVariable(value = "username") String username, String password)throws Exception{

        return userService.queryUserByName(username);
    }


}
