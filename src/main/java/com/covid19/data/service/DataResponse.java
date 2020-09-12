package com.covid19.data.service;

import com.covid19.data.entity.Covid19Data;
import com.covid19.data.repository.Covid19Repository;
import lombok.Data;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataResponse {
    private final Covid19Repository covid19Repository;

    public DataResponse(Covid19Repository covid19Repository) {
        this.covid19Repository = covid19Repository;
    }

    public List<Covid19Data> getData(String message){

        List<Covid19Data> str = covid19Repository.findByArea(message);
        return str;

    }
}
