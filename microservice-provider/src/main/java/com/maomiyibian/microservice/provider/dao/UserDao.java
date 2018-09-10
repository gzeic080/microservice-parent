package com.maomiyibian.microservice.provider.dao;

import com.maomiyibian.microservice.api.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
    int deleteByPrimaryKey(Long id);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    User queryUserByName(String userName);

    int updateByPrimaryKeySelective(User record);
}