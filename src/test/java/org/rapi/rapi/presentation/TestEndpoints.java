package org.rapi.rapi.presentation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestEndpoints {
    @GetMapping("/test")
    public void test() {
        System.out.println("Test");
    }

}
