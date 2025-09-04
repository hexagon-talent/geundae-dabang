package com.gundaero.alley.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HealthMapper {
    String selectNow();
}
