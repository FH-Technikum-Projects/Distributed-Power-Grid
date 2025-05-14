package at.dists.percentageservice.messaging;

import at.dists.percentageservice.service.CurrentPercentageCalculator;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PercentageMessageListener {

    private final CurrentPercentageCalculator calculator;

    public PercentageMessageListener(CurrentPercentageCalculator calculator) {
        this.calculator = calculator;
    }

    @RabbitListener(queues = "energy-queue")
    public void handleMessage(String message) {
        calculator.recalculatePercentage();
        System.out.println("Updated percentage based on usage update.");
    }
}
