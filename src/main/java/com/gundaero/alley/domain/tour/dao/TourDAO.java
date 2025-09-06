package com.gundaero.alley.domain.tour.dao;

import com.gundaero.alley.domain.tour.entity.TourLocation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TourDAO {
    TourLocation findById(@Param("id") Long id);
}
