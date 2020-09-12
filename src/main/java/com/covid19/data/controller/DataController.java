package com.covid19.data.controller;

import com.covid19.data.bot.Bot;
import com.covid19.data.dto.FileUrl;
import com.covid19.data.service.DataResponse;
import com.covid19.data.service.StartParsingService;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataController {

    private final StartParsingService startParsing;
    private final DataResponse dataResponse;


    public DataController(StartParsingService startParsing, DataResponse dataResponse) {
        this.startParsing = startParsing;
        this.dataResponse = dataResponse;
    }


    @SneakyThrows
    @PostMapping(value = "/start")
    public void start(@RequestBody FileUrl fileUrl){

        Bot bot = new Bot(dataResponse);
        startParsing.processData(fileUrl.getFileUrl());
        bot.startBot();

    }
}
