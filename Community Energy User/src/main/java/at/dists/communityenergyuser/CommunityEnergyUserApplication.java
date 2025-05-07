package at.dists.communityenergyuser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class CommunityEnergyUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommunityEnergyUserApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(RabbitTemplate rabbitTemplate) {
        return args -> {
            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
            scheduler.scheduleAtFixedRate(() -> {
                double kwh = generateKwhUsage();
                String json = buildMessage(kwh);
                rabbitTemplate.convertAndSend("energy-queue", json);
                System.out.println("Sent: " + json);
            }, 0, 5, TimeUnit.SECONDS); // alle 5 Sekunden senden
        };
    }

    private double generateKwhUsage() {
        int hour = LocalTime.now().getHour();
        if (hour >= 6 && hour <= 9 || hour >= 17 && hour <= 21) {
            return 0.002 + Math.random() * 0.003; // höhere Last in Spitzenzeiten
        } else {
            return 0.001 + Math.random() * 0.0015; // niedrigere Last sonst
        }
    }

    private String buildMessage(double kwh) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode message = mapper.createObjectNode();
        message.put("type", "USER");
        message.put("association", "COMMUNITY");
        message.put("kwh", Math.round(kwh * 1000.0) / 1000.0);
        message.put("datetime", LocalDateTime.now().toString());
        return message.toString();
    }
}
