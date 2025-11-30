package com.catalogx.inventoryservice;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InventoryEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final String TOPIC = "inventory-events";

    public void publish(String key, Object event)
    {
        kafkaTemplate.send(TOPIC, key, event).whenComplete((result, ex) -> {
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
