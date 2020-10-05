package com.covid19.data.bot;

import com.covid19.data.entity.Covid19Data;
import com.covid19.data.service.DataResponse;

public class Data {

    private final DataResponse dataResponse;

    public Data(DataResponse dataResponse) {
        this.dataResponse = dataResponse;
    }

    public  String result(String area, String settlement){
        Covid19Data covid19Data = dataResponse.getDataByAreaAndSettlement(area, settlement);

        return "Область: "+ covid19Data.getArea()+ "\n" +
                "Район: "+ covid19Data.getRegion()+ "\n"+
                "Село: "+ covid19Data.getSettlement()+ "\n"+
                "Дата: "+ covid19Data.getDate()+ "\n\n" +
                "Координати довгота: "+ covid19Data.getSettlement_coordinate_lng()+ "\n" +
                "Координати широта: " + covid19Data.getSettlement_coordinate_wid()+ "\n\n"+
                "Всього з підозрою: " + covid19Data.getTotal_suspect()+ "\n" +
                "Всього підтверджених: " + covid19Data.getTotal_confirm()+ "\n" +
                "Всього померло: " + covid19Data.getTotal_death()+ "\n" +
                "Всього виздоровіло: " + covid19Data.getTotal_recover();
    }
}
