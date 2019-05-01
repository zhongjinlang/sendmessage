package com.zhongjinlang.aliyun;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author: zhongjinlang
 * @date: Created in 2019/5/2 0:06
 * @description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CommonRpcTest {

    @Autowired
    private CommonRpc commonRpc;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Test
    public void send1(){
        rabbitTemplate.convertAndSend("msm", 1);
    }



    @Test
    public void send() {
        Map<String, String> code = new HashMap<>();
        code.put("code", "123456");
        commonRpc.send("15766371781", JSON.toJSONString(code));
        /**
         * {"Message":"OK","RequestId":"15510BA0-B641-472F-8A3D-D85406A0F15D","BizId":"520100556727328952^0","Code":"OK"}
         */
    }
}