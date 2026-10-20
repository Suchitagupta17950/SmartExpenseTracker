package com.expensetracker.model;

import java.time.LocalDate;

public class TestExpense {

    public static void main(String[] args) {

        Expense expense = new Expense(
                1,
                500.0,
                ExpenseCategory.FOOD,
                ExpenseType.WANT,
                LocalDate.now(),
                "Dinner with friends"
        );

        System.out.println("Expense ID: " + expense.getId());
        System.out.println("Amount: ₹" + expense.getAmount());
        System.out.println("Category: " + expense.getCategory());
        System.out.println("Type: " + expense.getType());
        System.out.println("Date: " + expense.getDate());
        System.out.println("Description: " + expense.getDescription());
    }
}