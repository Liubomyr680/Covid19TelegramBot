package com.covid19.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "data")
@Data
@AllArgsConstructor

@NoArgsConstructor
public class Covid19Data {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "date")
    private String date;

    @Column(name = "area")
    private String area;

    @Column(name = "region")
    private String region;

    @Column(name = "settlement")
    private String settlement;

    @Column(name = "settlement_coordinate_lng")
    private double settlement_coordinate_lng;

    @Column(name = "settlement_coordinate_wid")
    private double settlement_coordinate_wid;

    @Column(name = "total_suspect")
    private int total_suspect;

    @Column(name = "total_confirm")
    private int total_confirm;

    @Column(name = "total_death")
    private int total_death;

    @Column(name = "total_recover")
    private int total_recover;
}
