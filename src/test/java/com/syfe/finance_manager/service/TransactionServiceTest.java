package com.syfe.finance_manager.service;

import com.syfe.finance_manager.dto.CreateTransactionRequest;
import com.syfe.finance_manager.entity.Category;
import com.syfe.finance_manager.repository.CategoryRepository;
import com.syfe.finance_manager.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TransactionServiceTest {

    @Mock
    TransactionRepository transactionRepository;

    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    TransactionService transactionService;

    public TransactionServiceTest(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTransaction_success(){

        CreateTransactionRequest req = new CreateTransactionRequest();
        req.setAmount(5000);
        req.setDate(LocalDate.now().toString());
        req.setCategory("Salary");

        Category cat = new Category();
        cat.setName("Salary");
        cat.setType(Category.Type.INCOME);

        when(categoryRepository.findByNameAndUserId("Salary",1L))
                .thenReturn(Optional.of(cat));

        when(transactionRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        var tx = transactionService.createTransaction(req,1L);

        assertEquals(5000, tx.getAmount());
    }
}
