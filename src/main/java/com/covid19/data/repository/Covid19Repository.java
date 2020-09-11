package com.covid19.data.repository;

import com.covid19.data.entity.Covid19Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Covid19Repository extends JpaRepository<Covid19Data, Long> {
}
