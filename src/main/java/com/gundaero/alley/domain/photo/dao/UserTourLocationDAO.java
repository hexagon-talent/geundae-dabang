package com.gundaero.alley.domain.photo.dao;

import com.gundaero.alley.domain.photo.entity.PhotoRow;
import com.gundaero.alley.domain.photo.entity.UserTourLocation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserTourLocationDAO {
    int upsert(UserTourLocation row);

    UserTourLocation findOne(@Param("userId") Long userId,
                             @Param("locationId") Long locationId);
    List<PhotoRow> findAllByUserWithTitle(@Param("userId") Long userId);
}
