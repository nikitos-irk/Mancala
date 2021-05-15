package com.example.demo.api.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.http.MediaType;
import java.util.HashMap;


@RestController
public class UrlController {
    @RequestMapping(
            method = RequestMethod.GET,
            value = {"/index"},
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public HashMap<String, String> getIndex() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("Nikita", "Kovrigin");
        return hashMap;
    }
}
