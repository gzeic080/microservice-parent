package com.maomiyibian.microservice.api.service;

import com.maomiyibian.microservice.api.domain.User;
import com.maomiyibian.microservice.common.page.Page;

/**
 * TODO: 用户Rpc接口
 *
 * @author junyunxiao
 * @date 2018-9-10 9:33
 */
public interface UserService {

    User queryUserByName(String userName) throws Exception;

    /**
     *
     * @param parameter
     * @param parmeter
     * @return
     * @throws Exception
     */
    Page<User> queryUserByPage(Object parameter,Page parmeter) throws Exception;
}
