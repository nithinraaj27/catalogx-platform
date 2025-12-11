package com.catalogx.productservice.events;

import com.catalogx.productservice.dto.ProductEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductEventProducer {

    private static final String TOPIC = "product-events";
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publish(ProductEvent event)
    {
        log.info("Publishing ProductEvent: {}", event);

        kafkaTemplate.send(TOPIC, String.valueOf(event.productId()), event).whenComplete((result, ex) -> {
            if (ex == null) {
                System.out.println("📨 Message sent successfully to topic: "
                        + result.getRecordMetadata().topic()
                        + " partition: " + result.getRecordMetadata().partition()
                        + " offset: " + result.getRecordMetadata().offset());
            } else {
                System.err.println("❌ Failed to send message: " + ex.getMessage());
            }
        });
    }
}
