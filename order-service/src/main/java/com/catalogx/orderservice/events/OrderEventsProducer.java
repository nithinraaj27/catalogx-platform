package com.catalogx.orderservice.events;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.kafka.core.KafkaTemplate;

@Component
@RequiredArgsConstructor
public class OrderEventsProducer {

    private final KafkaTemplate<String , Object> kafkaTemplate;
    private static final String TOPIC = "order-events";

    public void publish(String key, Object event)
    {
        kafkaTemplate.send(TOPIC, key, event).whenComplete((result, ex) ->{
            if(ex == null)
            {
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
