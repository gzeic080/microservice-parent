package com.maomiyibian.microservice.provider.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.maomiyibian.microservice.api.domain.User;
import com.maomiyibian.microservice.api.service.UserService;
import com.maomiyibian.microservice.common.page.Page;
import com.maomiyibian.microservice.provider.interceptor.DataServiceMybatis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * TODO: 类描述
 *
 * @author junyunxiao
 * @date 2018-9-10 9:36
 */
@Service(
        version = "${service.version}",
        application = "${dubbo.application.id}",
        protocol = "${dubbo.protocol.id}",
        registry = "${dubbo.registry.id}"
)
public class UserServiceImpl implements UserService {

    private Logger logger= LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private DataServiceMybatis dataServiceStat;

    @Override
    public User queryUserByName(String userName) throws Exception {
        logger.info("com.maomiyibian.microservice.provider.impl.UserServiceImpl.queryUserByName:Rpc调用开始");
        return dataServiceStat.getObject("com.maomiyibian.microservice.provider.dao.UserDao.queryUserByName",userName);
    }

    @Override
    public Page<User> queryUserByPage(Object parameter,Page page) throws Exception {
        logger.info("com.maomiyibian.microservice.provider.impl.UserServiceImpl.queryUserByPage:Rpc调用开始");
        return dataServiceStat.query("com.maomiyibian.microservice.provider.dao.UserDao.queryUserByPage","com.maomiyibian.microservice.provider.dao.UserDao.queryUserCount",parameter,page);
    }
}
