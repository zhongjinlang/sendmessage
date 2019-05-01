package com.zhongjinlang.serice.impl;
import com.zhongjinlang.dao.UserDao;
import com.zhongjinlang.pojo.User;
import com.zhongjinlang.serice.UserService;
import com.zhongjinlang.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author: zhongjinlang
 * @date: Created in 2019/4/30 0:19
 * @description:
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 列表查询
     */
    @Override
    public List<User> findAll() {
        List<User> all = userDao.findAll();
        return all;
    }

    /**
     * 发送验证码
     *
     * @param mobile 手机号
     */
    @Override
    public ResultVo sendCode(String mobile) {
        // 校验
        if (StringUtils.isEmpty(mobile)){
            return new ResultVo(1, "手机号错误");
        }
        // 判断该手机号是否注册
        User result = userDao.findByMobile(mobile);
        if (result != null){
            return new ResultVo(1, "手机号已被注册");
        }

        // 生成随机数
        Random random = new Random();
        int max = 999999;
        int min = 100000;
        int code = random.nextInt(max);
        if (code < min) {
            code = code + min;
        }
        log.info("【生成的验证码为】={}, 【手机号为】={}", code, mobile);

        // 存入redis
        redisTemplate.boundValueOps("msgcode_" + mobile).set(code + "", 2, TimeUnit.MINUTES);

        // 讲验证码和手机号发送到消息队列
        Map<String, String> parameter = new HashMap<>();
        parameter.put("mobile", mobile);
        parameter.put("code", code + "");
        rabbitTemplate.convertAndSend("msg", parameter);

        return new ResultVo(0, "验证码发送成功");
    }

    /**
     * 用户注册
     *
     * @param user 用户
     * @param code 验证码
     */
    @Override
    public ResultVo register(User user, String code) {
        if (StringUtils.isEmpty(code)){
            return new ResultVo(1, "验证码不能为空");
        }

        String msgCode = (String) redisTemplate.boundValueOps("msgcode_" + user.getMobile()).get();
        log.info("【用户注册】redis中验证码为 = {}", msgCode);

        if (msgCode == null) {
            throw new RuntimeException("验证码不存在");
        }

        if (!msgCode.equals(code)) {
            return new ResultVo(1, "验证码不正确");
        }

        User result = userDao.save(user);
        if (result != null) {
            return new ResultVo(0, "注册成功");
        }

        return new ResultVo(1, "注册失败");
    }
}
