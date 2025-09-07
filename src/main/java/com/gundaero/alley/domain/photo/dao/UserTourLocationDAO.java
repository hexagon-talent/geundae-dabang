package com.gundaero.alley.domain.photo.dao;

import com.gundaero.alley.domain.photo.entity.UserTourLocation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserTourLocationDAO {
    int upsert(UserTourLocation row);

    UserTourLocation findOne(@Param("userId") Long userId,
                             @Param("locationId") Long locationId);
}
