package com.zhongjinlang.serice;

import com.zhongjinlang.pojo.User;
import com.zhongjinlang.vo.ResultVo;

import java.util.List;

/**
 * @author: zhongjinlang
 * @date: Created in 2019/4/30 0:19
 * @description:
 */
public interface UserService {

    List<User> findAll();

    /**
     * 发送验证码
     * @param mobile 手机号
     * @return
     */
    ResultVo sendCode(String mobile);

    /**
     * 用户注册
     * @param user 用户
     * @param code 验证码
     * @return
     */
    ResultVo register(User user, String code);
}
