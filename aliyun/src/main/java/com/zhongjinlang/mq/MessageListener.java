package com.zhongjinlang.mq;

import com.alibaba.fastjson.JSON;
import com.zhongjinlang.aliyun.CommonRpc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.rmi.runtime.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: zhongjinlang
 * @date: Created in 2019/5/2 0:27
 * @description:
 */
@Component
@RabbitListener(queues = "msg")
@Slf4j
public class MessageListener {

    @Autowired
    private CommonRpc commonRpc;

    @RabbitHandler
    public void sendMessage(Map<String, String> map){
        System.out.println("监听消息....");

        String mobile = map.get("mobile");
        String code = map.get("code");
        Map<String, String> param = new HashMap<>();
        param.put("code", code);

        log.info("【监听消息】手机号={}, 验证码={}", mobile, code);

        try {
            commonRpc.send(mobile, JSON.toJSONString(param));
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }
    }
}
