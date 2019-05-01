package com.zhongjinlang.mq;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: zhongjinlang
 * @date: Created in 2019/4/30 2:50
 * @description:
 */
@Configuration
public class RabbitConfig {

    @Bean
    public Queue message_code(){
        return new Queue("msg", true);
    }
}
