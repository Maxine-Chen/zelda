package com.pmx.zelda_java.try_1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("try_1")
/*让该类可以网络请求
* 通过  try_1  路径请求*/

public class Hello {

    @GetMapping("hello")
    //允许请求该方法
    
    public String hello() {
        return "Hello World";

    }
}
