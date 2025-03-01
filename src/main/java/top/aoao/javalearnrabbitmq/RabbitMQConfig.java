package top.aoao.javalearnrabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_NAME = "testQueue";
    public static final String EXCHANGE_NAME = "testExchange";
    public static final String ROUTING_KEY = "testRoutingKey";

    public static final String DEAD_LETTER_QUEUE = "dead_letter_queue";
    public static final String DEAD_LETTER_EXCHANGE = "dead_letter_exchange";
    public static final String DEAD_LETTER_ROUTING_KEY = "dead_routing_key";

    // 定义主队列并绑定死信队列
    @Bean
    public Queue queue() {
        return QueueBuilder.durable(QUEUE_NAME)
                .deadLetterExchange(DEAD_LETTER_EXCHANGE)  // 绑定死信交换机
                .deadLetterRoutingKey(DEAD_LETTER_ROUTING_KEY)   // 绑定死信路由键
                .build();
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

    //------------------------------------------------------------
    // 添加死信队列
    @Bean
    public Queue deadLetterQueue() {
        return new Queue(DEAD_LETTER_QUEUE, true);
    }

    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(DEAD_LETTER_EXCHANGE);
    }

    @Bean
    public Binding deadLetterBinding(Queue deadLetterQueue, DirectExchange deadLetterExchange) {
        return BindingBuilder.bind(deadLetterQueue).to(deadLetterExchange).with(DEAD_LETTER_ROUTING_KEY);
    }
}
