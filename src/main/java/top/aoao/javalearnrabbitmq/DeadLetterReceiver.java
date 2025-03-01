package top.aoao.javalearnrabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class DeadLetterReceiver {

    @RabbitListener(queues = "dead_letter_queue")
    public void receiveDeadLetter(String message) {
        System.err.println("死信队列接收到消息：" + message);
    }
}
