package com.maomiyibian.microservice.api.service;

import com.maomiyibian.microservice.api.domain.User;

/**
 * TODO: 用户Rpc接口
 *
 * @author junyunxiao
 * @date 2018-9-10 9:33
 */
public interface UserService {

    User queryUserByName(String userName) throws Exception;
}
