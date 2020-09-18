package com.covid19.data.bot;

import com.covid19.data.entity.Covid19Data;
import com.covid19.data.service.DataResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Data {

    private final DataResponse dataResponse;

    public Data(DataResponse dataResponse) {
        this.dataResponse = dataResponse;
    }

    public  String result(String text){
        List<Covid19Data> list = new ArrayList<>(dataResponse.getData(text));
        List<Covid19Data> list2 = new ArrayList<>();


        JSONArray jsonArray= new JSONArray(list);
        Iterator i = jsonArray.iterator();

        while (i.hasNext()) {

            Covid19Data covid19Data = new Covid19Data();
            JSONObject jsonObject = (JSONObject) i.next();

            covid19Data.setDate(jsonObject.getString("date"));
            covid19Data.setArea(jsonObject.getString("area"));
            covid19Data.setRegion(jsonObject.getString("region"));
            covid19Data.setSettlement(jsonObject.getString("settlement"));
            covid19Data.setSettlement_coordinate_lng(jsonObject.getDouble("settlement_coordinate_lng"));
            covid19Data.setSettlement_coordinate_wid(jsonObject.getDouble("settlement_coordinate_wid"));
            covid19Data.setTotal_suspect(jsonObject.getInt("total_confirm"));
            covid19Data.setTotal_confirm(jsonObject.getInt("total_death"));
            covid19Data.setTotal_death(jsonObject.getInt("total_recover"));
            covid19Data.setTotal_recover(jsonObject.getInt("total_recover"));
            list2.add(covid19Data);

        }






        System.out.println(list);
        text = list2.toString();
        return text;
    }
}
