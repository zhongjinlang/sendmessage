package com.zhongjinlang.serice.impl;
import com.zhongjinlang.pojo.User;
import com.zhongjinlang.serice.UserService;
import com.zhongjinlang.vo.ResultVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;



/**
 * @author: zhongjinlang
 * @date: Created in 2019/4/30 0:20
 * @description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void collectionI(){
        rabbitTemplate.convertAndSend("sms", "1");
    }

    @Test
    public void findAll() {
        for (User user : userService.findAll()) {
            System.out.println(user);
        }
    }

    @Test
    public void sendMessages() {
        String mobile = "15766371781";
        ResultVo resultVo = userService.sendCode(mobile);
        System.out.println(resultVo);
    }

    @Test
    public void register() {
        User user = new User();
        user.setUsername("zhangsan");
        user.setMobile("15766371781");
        user.setPassword("123");

        ResultVo register = userService.register(user, "410401");
        System.out.println(register);
    }
}