package top.aoao.javalearnrabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_NAME = "testQueue";
    public static final String EXCHANGE_NAME = "testExchange";
    public static final String ROUTING_KEY = "testRoutingKey";

    // 定义队列
    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, true);
    }

    // 定义直连交换机
    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    // 定义绑定关系：队列 <-> 交换机
    @Bean
    public Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }



}