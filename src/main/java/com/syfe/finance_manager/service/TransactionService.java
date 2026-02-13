package com.syfe.finance_manager.service;

import com.syfe.finance_manager.dto.CreateTransactionRequest;
import com.syfe.finance_manager.dto.UpdateTransactionRequest;
import com.syfe.finance_manager.entity.Category;
import com.syfe.finance_manager.entity.Transaction;
import com.syfe.finance_manager.repository.CategoryRepository;
import com.syfe.finance_manager.repository.TransactionRepository;

import com.syfe.finance_manager.exception.BadRequestException;
import com.syfe.finance_manager.exception.NotFoundException;
import com.syfe.finance_manager.exception.ForbiddenException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;

    // CREATE TRANSACTION
    public Transaction createTransaction(CreateTransactionRequest request, Long userId){

        LocalDate date = LocalDate.parse(request.getDate());

        if(date.isAfter(LocalDate.now())){
            throw new BadRequestException("Future date not allowed");
        }

        Category category = categoryRepository.findByNameAndUserId(request.getCategory(), userId)
                .orElseGet(() -> categoryRepository.findAll()
                        .stream()
                        .filter(c -> c.getName().equalsIgnoreCase(request.getCategory()) && !c.isCustom())
                        .findFirst()
                        .orElseThrow(() -> new BadRequestException("Invalid category")));

        Transaction tx = Transaction.builder()
                .amount(request.getAmount())
                .date(date)
                .category(category.getName())
                .description(request.getDescription())
                .type(Transaction.Type.valueOf(category.getType().name()))
                .userId(userId)
                .build();

        return transactionRepository.save(tx);
    }

    // GET ALL
    public List<Transaction> getAllTransactions(Long userId){
        return transactionRepository.findByUserIdOrderByDateDesc(userId);
    }
    public List<Transaction> getByCategory(Long userId, String category){
        return transactionRepository
                .findByUserIdAndCategoryOrderByDateDesc(userId, category);
    }

    public List<Transaction> getByDateRange(Long userId, String start, String end){

        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);

        return transactionRepository
                .findByUserIdAndDateBetweenOrderByDateDesc(userId, startDate, endDate);
    }


    // UPDATE
    public Transaction updateTransaction(Long id,
                                         UpdateTransactionRequest request,
                                         Long userId){

        Transaction tx = transactionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Transaction not found"));

        // other user access
        if(!tx.getUserId().equals(userId)){
            throw new ForbiddenException("You cannot access this transaction");
        }

        if(request.getAmount() != null){
            tx.setAmount(request.getAmount());
        }

        if(request.getDescription() != null){
            tx.setDescription(request.getDescription());
        }

        if(request.getCategory() != null){

            Category category = categoryRepository.findByNameAndUserId(request.getCategory(), userId)
                    .orElseGet(() -> categoryRepository.findAll()
                            .stream()
                            .filter(c -> c.getName().equalsIgnoreCase(request.getCategory()) && !c.isCustom())
                            .findFirst()
                            .orElseThrow(() -> new BadRequestException("Invalid category")));

            tx.setCategory(category.getName());
            tx.setType(Transaction.Type.valueOf(category.getType().name()));
        }

        return transactionRepository.save(tx);
    }

    // DELETE
    public String deleteTransaction(Long id, Long userId){

        Transaction tx = transactionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Transaction not found"));

        if(!tx.getUserId().equals(userId)){
            throw new ForbiddenException("You cannot access this transaction");
        }

        transactionRepository.delete(tx);

        return "Transaction deleted successfully";
    }
}
