package com.syfe.finance_manager.controller;

import com.syfe.finance_manager.service.ReportService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/monthly/{year}/{month}")
    public Map<String,Object> getMonthlyReport(@PathVariable int year,
                                               @PathVariable int month,
                                               HttpSession session){

        Long userId = (Long) session.getAttribute("userId");
        return reportService.getMonthlyReport(userId, year, month);
    }

    @GetMapping("/yearly/{year}")
    public Map<String,Object> getYearlyReport(@PathVariable int year,
                                              HttpSession session){

        Long userId = (Long) session.getAttribute("userId");
        return reportService.getYearlyReport(userId, year);
    }

}
