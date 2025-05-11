package payk96.micro_service2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import payk96.micro_service1.dto.MessageRequest;
import payk96.micro_service1.dto.MessageStatusResponse;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageProcessorService {
    private final KafkaTemplate<String, MessageStatusResponse> kafkaTemplate;

    @Value("${spring.kafka.topics.reply-topic}")
    private String replyTopic;


    @KafkaListener(topics = "${spring.kafka.topics.message-topic}")
    public void processMessage(MessageRequest request) {
        log.info("Получено сообщение для обработки: {}", request);

        MessageStatusResponse response;
        if (Boolean.TRUE.equals(request.flag())) {
            response = new MessageStatusResponse("Сообщение пришло");
        } else {
            response = new MessageStatusResponse("Сообщение не пришло");
        }

        log.info("Отправка ответа в топик {}: {}", replyTopic, response);
        kafkaTemplate.send(replyTopic, response);
    }
}
