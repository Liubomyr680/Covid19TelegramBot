package com.covid19.data.controller;

import com.covid19.data.dto.FileUrl;
import com.covid19.data.service.StartParsingService;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataController {

    private final StartParsingService startParsing;

    public DataController(StartParsingService startParsing) {
        this.startParsing = startParsing;
    }

    @SneakyThrows
    @PostMapping(value = "/start")
    public void start(@RequestBody FileUrl fileUrl){

        startParsing.processData(fileUrl.getFileUrl());
    }
}
