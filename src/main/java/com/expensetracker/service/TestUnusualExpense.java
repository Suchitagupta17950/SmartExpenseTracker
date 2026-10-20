
package com.expensetracker.service;

import com.expensetracker.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TestUnusualExpense {

    public static void main(String[] args) {

        ExpenseAnalyticsService analyticsService =
                new ExpenseAnalyticsService();

        List<Expense> previousExpenses = new ArrayList<>();

        previousExpenses.add(new Expense(
                1, 300, ExpenseCategory.FOOD,
                ExpenseType.NEED, LocalDate.now(), "Breakfast"
        ));

        previousExpenses.add(new Expense(
                2, 500, ExpenseCategory.FOOD,
                ExpenseType.NEED, LocalDate.now(), "Lunch"
        ));

        previousExpenses.add(new Expense(
                3, 400, ExpenseCategory.FOOD,
                ExpenseType.NEED, LocalDate.now(), "Dinner"
        ));

        double average =
                analyticsService.calculateCategoryAverage(
                        previousExpenses,
                        ExpenseCategory.FOOD
                );

        System.out.println("Average FOOD Expense: ₹" + average);

        Expense newExpense = new Expense(
                4, 3000, ExpenseCategory.FOOD,
                ExpenseType.WANT, LocalDate.now(),
                "Expensive Dinner"
        );

        String spendingAlert =
                analyticsService.getSpendingAlert(
                        newExpense,
                        previousExpenses
                );

        System.out.println("New Expense: ₹"
                + newExpense.getAmount());

        System.out.println("Spending Alert: "
                + spendingAlert);
    }
}
