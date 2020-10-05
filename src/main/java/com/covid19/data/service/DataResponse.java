package com.covid19.data.service;

import com.covid19.data.entity.Covid19Data;
import com.covid19.data.repository.Covid19Repository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class DataResponse {
    private final Covid19Repository covid19Repository;

    public DataResponse(Covid19Repository covid19Repository) {
        this.covid19Repository = covid19Repository;
    }

    public List<Covid19Data> getDataBySettlement(String settlement){

        List<Covid19Data> str = covid19Repository.findBySettlement(settlement);
        return str;
    }

    public Covid19Data getDataByAreaAndSettlement(String area, String sattlement){
        Covid19Data str = covid19Repository.findByAreaAndSettlement(area, sattlement);
        return str;
    }

    public HashSet<String> getAllAreaFromTable(){
        HashSet<String> data = covid19Repository.selectAllArea();
        return data;
    }

    public List<Covid19Data> getByArea(String area){
        List<Covid19Data> data = covid19Repository.findByArea(area);
        return data;
    }
}
