package com.expensetracker.service;

import com.expensetracker.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TestCategoryAnalysis {

    public static void main(String[] args) {

        ExpenseAnalyticsService analyticsService =
                new ExpenseAnalyticsService();

        List<Expense> expenses = new ArrayList<>();

        expenses.add(new Expense(
                1, 8000,
                ExpenseCategory.FOOD,
                ExpenseType.NEED,
                LocalDate.now(),
                "Groceries and food"
        ));

        expenses.add(new Expense(
                2, 6000,
                ExpenseCategory.BILLS,
                ExpenseType.NEED,
                LocalDate.now(),
                "Electricity and bills"
        ));

        expenses.add(new Expense(
                3, 3000,
                ExpenseCategory.TRAVEL,
                ExpenseType.NEED,
                LocalDate.now(),
                "Travel"
        ));

        expenses.add(new Expense(
                4, 2000,
                ExpenseCategory.SHOPPING,
                ExpenseType.WANT,
                LocalDate.now(),
                "Shopping"
        ));

        expenses.add(new Expense(
                5, 1000,
                ExpenseCategory.ENTERTAINMENT,
                ExpenseType.WANT,
                LocalDate.now(),
                "Movies"
        ));

        System.out.println("Where Did My Money Go?\n");

        double totalSpent = 0;

        for (Expense expense : expenses) {
            totalSpent += expense.getAmount();
        }

        System.out.println("Total Spending: ₹" + totalSpent);
        System.out.println();

        for (ExpenseCategory category :
                ExpenseCategory.values()) {

            double amount =
                    analyticsService.calculateCategorySpending(
                            expenses,
                            category
                    );

            if (amount > 0) {

                double percentage =
                        analyticsService.calculateCategoryPercentage(
                                expenses,
                                category
                        );

                System.out.println(
                        category +
                        " → ₹" + amount +
                        " → " + percentage + "%"
                );
            }
        }
    }
}