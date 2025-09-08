package com.gundaero.alley.domain.map.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface MapLocationDAO {
    List<Map<String, Object>> findAllAsGeoJson();
    Map<String, Object> findOneAsGeoJson(@Param("id") Long id);
    List<Map<String, Object>> findAllUploadStatusByUser(@Param("userId") Long userId);
}
