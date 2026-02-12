package com.syfe.finance_manager.controller;

import com.syfe.finance_manager.dto.CreateTransactionRequest;
import com.syfe.finance_manager.dto.UpdateTransactionRequest;
import com.syfe.finance_manager.entity.Transaction;
import com.syfe.finance_manager.service.TransactionService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public Transaction createTransaction(@RequestBody CreateTransactionRequest request,
                                         HttpSession session){

        Long userId = (Long) session.getAttribute("userId");
        return transactionService.createTransaction(request, userId);
    }
    @GetMapping
    public List<Transaction> getAllTransactions(HttpSession session){

        Long userId = (Long) session.getAttribute("userId");
        return transactionService.getAllTransactions(userId);
    }

    @PutMapping("/{id}")
    public Transaction updateTransaction(@PathVariable Long id,
                                         @RequestBody UpdateTransactionRequest request,
                                         HttpSession session){

        Long userId = (Long) session.getAttribute("userId");
        return transactionService.updateTransaction(id, request, userId);
    }

    @DeleteMapping("/{id}")
    public String deleteTransaction(@PathVariable Long id, HttpSession session){

        Long userId = (Long) session.getAttribute("userId");
        return transactionService.deleteTransaction(id, userId);
    }


}
