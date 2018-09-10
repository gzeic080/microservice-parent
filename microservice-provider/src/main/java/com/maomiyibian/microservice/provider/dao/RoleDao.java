package com.maomiyibian.microservice.provider.dao;

import com.maomiyibian.microservice.api.domain.Role;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoleDao {
    int deleteByPrimaryKey(Long id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
}