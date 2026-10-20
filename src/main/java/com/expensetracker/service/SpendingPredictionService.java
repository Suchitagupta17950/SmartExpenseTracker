package com.expensetracker.service;

import com.expensetracker.model.Expense;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public class SpendingPredictionService {

    public double predictMonthEndSpending(
            List<Expense> expenses) {

        YearMonth currentMonth =
                YearMonth.now();

        double totalSpent = 0;

        for (Expense expense : expenses) {

            if (YearMonth.from(expense.getDate())
                    .equals(currentMonth)) {

                totalSpent += expense.getAmount();
            }
        }

        int currentDay =
                LocalDate.now().getDayOfMonth();

        if (currentDay == 0) {
            return 0;
        }

        double averageDailySpending =
                totalSpent / currentDay;

        int totalDays =
                currentMonth.lengthOfMonth();

        return averageDailySpending * totalDays;
    }
}