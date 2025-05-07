package at.dists.usageservice.messaging;

import at.dists.usageservice.service.UsageUpdateService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class RabbitMessageListener {

    private final UsageUpdateService usageUpdateService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public RabbitMessageListener(UsageUpdateService usageUpdateService) {
        this.usageUpdateService = usageUpdateService;
    }

    @RabbitListener(queues = "energy-queue")
    public void receiveMessage(String message) {
        try {
            JsonNode node = objectMapper.readTree(message);
            String type = node.get("type").asText();
            double kwh = node.get("kwh").asDouble();
            LocalDateTime timestamp = LocalDateTime.parse(node.get("datetime").asText());

            usageUpdateService.updateUsage(type, kwh, timestamp);
            System.out.println("Processed: " + message);
        } catch (Exception e) {
            System.err.println("Invalid message: " + message);
            e.printStackTrace();
        }
    }
}
