package top.aoao.javalearnrabbitmq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestController {

    @Autowired
    private MessageSender messageSender;

    // 访问 http://localhost:8080/send?msg=你的消息 可发送消息到 RabbitMQ
    // 访问 http://localhost:8080/send?msg=error 可测试信息消费失败的情况
    @GetMapping("/send")
    public String sendMessage(String msg) {
        if (msg == null || msg.trim().isEmpty()) {
            msg = "Hello RabbitMQ!";
        }
        messageSender.send(msg);
        return "消息发送成功: " + msg;
    }
}
