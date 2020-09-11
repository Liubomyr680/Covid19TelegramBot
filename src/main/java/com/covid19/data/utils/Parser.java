package com.covid19.data.utils;

import com.covid19.data.entity.Covid19Data;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Parser {

    public List<Covid19Data> startParsingTheFile(File file) throws ParseException {

        BufferedReader br = null;
        String line = "";
        List<Covid19Data> list = new ArrayList<>();
        List<String> data = new ArrayList<>();

        try {

            br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null) {

                data.add(line);
            }
            data.remove(0);
            for (String i : data){
                String[] temp = i.split(",");
                Covid19Data covid19Data = new Covid19Data();

                covid19Data.setDate(temp[0]);
                covid19Data.setArea(temp[1]);
                covid19Data.setRegion(temp[2]);
                covid19Data.setSettlement(temp[3]);
                covid19Data.setSettlement_coordinate_lng(Double.parseDouble(temp[4]));
                covid19Data.setSettlement_coordinate_wid(Double.parseDouble(temp[5]));
                covid19Data.setTotal_suspect(Integer.parseInt(temp[6]));
                covid19Data.setTotal_confirm(Integer.parseInt(temp[7]));
                covid19Data.setTotal_death(Integer.parseInt(temp[8]));
                covid19Data.setTotal_recover(Integer.parseInt(temp[9]));
                list.add(covid19Data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return list;
    }
}
