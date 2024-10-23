package com.example.api_sqs.service;

import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.example.api_sqs.dto.TransactionDTO;
import com.example.api_sqs.model.Transaction;
import com.example.api_sqs.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private SqsClient sqsClient;

    private final String SQS_QUEUE_URL = "https://sqs.us-east-1.amazonaws.com/YOUR_ACCOUNT_ID/YOUR_QUEUE_NAME"; // Substitua pelo URL da sua fila

    public TransactionDTO createTransaction(TransactionDTO dto) {
        Transaction transaction = new Transaction();
        transaction.setDescription(dto.getDescription());
        transaction.setAmount(dto.getAmount());
        transaction.setDate(dto.getDate());
        transaction.setStatus(dto.getStatus());

        Transaction savedTransaction = repository.save(transaction);

        // Enviar mensagem para SQS após criar transação
        sendTransactionMessage(toDTO(savedTransaction));

        return toDTO(savedTransaction);
    }

    private void sendTransactionMessage(TransactionDTO dto) {
        String messageBody = "Transação criada: ID=" + dto.getId() + ", Valor=" + dto.getAmount() + ", Status=" + dto.getStatus();
        SendMessageRequest sendMsgRequest = SendMessageRequest.builder()
                .queueUrl(SQS_QUEUE_URL)
                .messageBody(messageBody)
                .delaySeconds(5)
                .build();

        sqsClient.sendMessage(sendMsgRequest);
        System.out.println("Mensagem enviada para a fila SQS: " + messageBody);
    }

    public List<TransactionDTO> getAllTransactions() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public TransactionDTO getTransactionById(Long id) {
        Optional<Transaction> transaction = repository.findById(id);
        return transaction.map(this::toDTO).orElse(null);
    }

    public TransactionDTO updateTransaction(Long id, TransactionDTO dto) {
        Optional<Transaction> optionalTransaction = repository.findById(id);
        if (optionalTransaction.isPresent()) {
            Transaction transaction = optionalTransaction.get();
            transaction.setDescription(dto.getDescription());
            transaction.setAmount(dto.getAmount());
            transaction.setDate(dto.getDate());
            transaction.setStatus(dto.getStatus());

            Transaction updatedTransaction = repository.save(transaction);
            return toDTO(updatedTransaction);
        }
        return null;
    }

    public boolean deleteTransaction(Long id) {
        Optional<Transaction> transaction = repository.findById(id);
        if (transaction.isPresent()) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    private TransactionDTO toDTO(Transaction transaction) {
        TransactionDTO dto = new TransactionDTO();
        dto.setId(transaction.getId());
        dto.setDescription(transaction.getDescription());
        dto.setAmount(transaction.getAmount());
        dto.setDate(transaction.getDate());
        dto.setStatus(transaction.getStatus());
        return dto;
    }
}