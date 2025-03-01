package top.aoao.javalearnrabbitmq;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MessageReceiver {

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME, ackMode = "MANUAL")
    public void receive(Message message, Channel channel) {
        try {
            String msg = new String(message.getBody());
            System.out.println("接收到消息: " + msg);
            // 处理消息...
            if (msg.contains("error")) {
                throw new RuntimeException("error");
            }
            // 如果处理成功，手动确认消息
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            //false：表示只确认当前消息（true 代表批量确认所有未确认的消息）。
            //deliveryTag：唯一标识该消息。
        } catch (Exception e) {
            try {
                System.err.println("消息处理失败，拒绝消息：" + new String(message.getBody()));
                // 拒绝消息，并且不重新放回队列
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
                //第一个 false：表示只拒绝当前消息
                //第二个 false：表示不重新放回队列，丢弃消息（如果设为 true，消息会重新进入队列）。
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}

