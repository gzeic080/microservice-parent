package com.maomiyibian.microservice.provider.dao;

import com.maomiyibian.microservice.api.domain.Permission;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PermissionDao {
    int deleteByPrimaryKey(Long id);

    int insert(Permission record);

    int insertSelective(Permission record);

    Permission selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);
}