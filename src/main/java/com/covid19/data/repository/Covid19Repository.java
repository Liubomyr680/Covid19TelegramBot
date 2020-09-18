package com.covid19.data.repository;

import com.covid19.data.entity.Covid19Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface Covid19Repository extends JpaRepository<Covid19Data, Long> {

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "truncate table data", nativeQuery = true)
    void deleteAllTable();

//    @Query(value = "SELECT * FROM json_data j WHERE j.number LIKE CONCAT(:number ,'%')",
//            nativeQuery = true)
//
//    @Query(value = "SELECT * FROM data d WHERE d.area LIKE CONCAT (:area ,'%')",
//        nativeQuery = true)
//    List<Covid19Data> findByArea(@Param("area") String area);

    List<Covid19Data> findBySettlement(String settlement);

}
