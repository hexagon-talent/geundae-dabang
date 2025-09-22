package com.gundaero.alley.domain.tour.dao;

import com.gundaero.alley.domain.tour.dto.request.TestRequestDTO;
import com.gundaero.alley.domain.tour.entity.TourLocation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TourDAO {
    TourLocation findById(@Param("id") Long id);
    void insertOrUpdateUserTourLocations(
            @Param("userId") Long userId,
            @Param("tourLocationIds") List<Long> tourLocationIds,
            @Param("photoUrl") String photoUrl
    );
}
