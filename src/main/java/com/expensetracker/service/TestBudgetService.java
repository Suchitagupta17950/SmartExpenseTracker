package com.expensetracker.service;

import com.expensetracker.model.Budget;
import com.expensetracker.model.Expense;
import com.expensetracker.model.ExpenseCategory;
import com.expensetracker.model.ExpenseType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TestBudgetService {

    public static void main(String[] args) {

        Budget budget = new Budget(20000);

        BudgetService budgetService = new BudgetService(budget);

        List<Expense> expenses = new ArrayList<>();

        expenses.add(new Expense(
                1,
                10000,
                ExpenseCategory.FOOD,
                ExpenseType.NEED,
                LocalDate.now(),
                "Groceries"
        ));

        expenses.add(new Expense(
                2,
                5000,
                ExpenseCategory.BILLS,
                ExpenseType.NEED,
                LocalDate.now(),
                "Electricity bill"
        ));

        expenses.add(new Expense(
                3,
                5000,
                ExpenseCategory.TRAVEL,
                ExpenseType.NEED,
                LocalDate.now(),
                "Travel"
        ));

        System.out.println("Monthly Budget: ₹" +
                budget.getMonthlyLimit());

        System.out.println("Total Spent: ₹" +
                budgetService.calculateTotalSpent(expenses));

        System.out.println("Usage: " +
                budgetService.calculateUsagePercentage(expenses) + "%");

        System.out.println("Status: " +
                budgetService.getBudgetStatus(expenses));

        System.out.println("Remaining Budget: ₹" +
                budgetService.getRemainingBudget(expenses));
    }
}