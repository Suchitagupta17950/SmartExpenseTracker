package com.expensetracker.service;

import com.expensetracker.model.Expense;
import com.expensetracker.model.ExpenseCategory;

import java.util.List;

public class ExpenseAnalyticsService {

    public double calculateCategoryAverage(
            List<Expense> expenses,
            ExpenseCategory category) {

        double total = 0;
        int count = 0;

        for (Expense expense : expenses) {

            if (expense.getCategory() == category) {
                total += expense.getAmount();
                count++;
            }
        }

        if (count == 0) {
            return 0;
        }

        return total / count;
    }

  
public String getSpendingAlert(
        Expense newExpense,
        List<Expense> previousExpenses) {

    double average = calculateCategoryAverage(
            previousExpenses,
            newExpense.getCategory()
    );

    if (average <= 0) {
        return "NORMAL";
    }

    double ratio = newExpense.getAmount() / average;

    if (ratio > 3) {
        return "HIGHLY UNUSUAL";
    } else if (ratio > 1.5) {
        return "UNUSUAL";
    }

    return "NORMAL";
}

    public double calculateCategorySpending(
            List<Expense> expenses,
            ExpenseCategory category) {

        double total = 0;

        for (Expense expense : expenses) {

            if (expense.getCategory() == category) {
                total += expense.getAmount();
            }
        }

        return total;
    }

    public double calculateCategoryPercentage(
            List<Expense> expenses,
            ExpenseCategory category) {

        double totalSpent = 0;

        for (Expense expense : expenses) {
            totalSpent += expense.getAmount();
        }

        if (totalSpent == 0) {
            return 0;
        }

        double categorySpending =
                calculateCategorySpending(
                        expenses,
                        category
                );

        return (categorySpending / totalSpent) * 100;
    }
}