package com.expensetracker.service;

import com.expensetracker.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TestHealthScore {

    public static void main(String[] args) {

        Budget budget = new Budget(20000);

        BudgetService budgetService =
                new BudgetService(budget);

        List<Expense> expenses = new ArrayList<>();

        // NEED expenses = ₹9,000
        expenses.add(new Expense(
                1,
                5000,
                ExpenseCategory.BILLS,
                ExpenseType.NEED,
                LocalDate.now(),
                "Electricity and bills"
        ));

        expenses.add(new Expense(
                2,
                4000,
                ExpenseCategory.FOOD,
                ExpenseType.NEED,
                LocalDate.now(),
                "Groceries"
        ));

        // WANT expense = ₹1,000
        expenses.add(new Expense(
                3,
                1000,
                ExpenseCategory.ENTERTAINMENT,
                ExpenseType.WANT,
                LocalDate.now(),
                "Movie"
        ));

        System.out.println("Monthly Budget: ₹" +
                budget.getMonthlyLimit());

        System.out.println("Total Spent: ₹" +
                budgetService.calculateTotalSpent(expenses));

        System.out.println("Budget Usage: " +
                budgetService.calculateUsagePercentage(expenses)
                + "%");

        System.out.println("Spending Health Score: " +
                budgetService.calculateSpendingHealthScore(expenses)
                + "/100");
    }
}