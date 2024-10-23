package com.example.api_sqs.controller;

import com.example.api_sqs.dto.TransactionDTO;
import com.example.api_sqs.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService service;

    @PostMapping
    public ResponseEntity<TransactionDTO> createTransaction(@RequestBody TransactionDTO dto) {
        TransactionDTO createdTransaction = service.createTransaction(dto);
        return ResponseEntity.ok(createdTransaction);
    }

    @GetMapping
    public ResponseEntity<List<TransactionDTO>> getAllTransactions() {
        List<TransactionDTO> transactions = service.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDTO> getTransactionById(@PathVariable Long id) {
        TransactionDTO transaction = service.getTransactionById(id);
        return transaction != null ? ResponseEntity.ok(transaction) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionDTO> updateTransaction(
            @PathVariable Long id, @RequestBody TransactionDTO dto) {
        TransactionDTO updatedTransaction = service.updateTransaction(id, dto);
        return updatedTransaction != null ? ResponseEntity.ok(updatedTransaction) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        boolean deleted = service.deleteTransaction(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
