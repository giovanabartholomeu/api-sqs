package com.example.api_sqs.config;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class SqsListener {

    @JmsListener(destination = "YOUR_QUEUE_NAME")
    public void receiveMessage(String message) {
        System.out.println("Mensagem recebida da fila SQS: " + message);
        // Aqui você pode implementar a lógica para processar a mensagem recebida
    }
}