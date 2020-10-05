package com.covid19.data.repository;

import com.covid19.data.entity.Covid19Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;

@Repository
public interface Covid19Repository extends JpaRepository<Covid19Data, Long> {

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "truncate table CovidData", nativeQuery = true)
    void deleteAllTable();

    @Query(value = "select area from CovidData", nativeQuery = true)
    HashSet<String> selectAllArea();

    List<Covid19Data> findByArea(String area);
    List<Covid19Data> findBySettlement(String settlement);

    Covid19Data findByAreaAndSettlement(String area, String settlement);


}
