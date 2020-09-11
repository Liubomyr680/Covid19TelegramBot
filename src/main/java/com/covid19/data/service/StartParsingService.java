package com.covid19.data.service;

import com.covid19.data.dto.FileUrl;
import com.covid19.data.entity.Covid19Data;
import com.covid19.data.repository.Covid19Repository;
import com.covid19.data.utils.FileDownload;
import com.covid19.data.utils.Parser;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Data
@Service
public class StartParsingService {

    private static final Logger log = LoggerFactory.getLogger(StartParsingService.class);
    private final Covid19Repository covid19Repository;

    public StartParsingService(Covid19Repository covid19Repository) {
        this.covid19Repository = covid19Repository;
    }

    public List<Covid19Data> processData(String fileUrl) {

        Parser parser = new Parser();
        List<Covid19Data> dataFromParsing = null;

        try {
            dataFromParsing = new ArrayList<>(parser.startParsingTheFile(FileDownload.download(fileUrl)));
        } catch (ParseException e) {
            log.error("Parsing problem", e);
        }

        List<Covid19Data> dataFromDB = new LinkedList<>(covid19Repository.findAll());
        List<Covid19Data> union = new LinkedList<>(dataFromParsing);
        union.removeAll(dataFromDB);

        for (Covid19Data newRecords : union) {
            covid19Repository.save(newRecords);
        }


        return union;
    }
}
