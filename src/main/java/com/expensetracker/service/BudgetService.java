package com.expensetracker.service;

import com.expensetracker.model.Budget;
import com.expensetracker.model.BudgetStatus;
import com.expensetracker.model.Expense;
import com.expensetracker.model.ExpenseType;

import java.util.List;

public class BudgetService {

    private final Budget budget;

   public BudgetService(Budget budget) {

    if (budget == null) {
        throw new IllegalArgumentException(
                "Budget cannot be null."
        );
    }

    if (budget.getMonthlyLimit() <= 0) {
        throw new IllegalArgumentException(
                "Monthly budget must be greater than zero."
        );
    }

    this.budget = budget;
}
private List<Expense> getCurrentMonthExpenses(
        List<Expense> expenses) {

    java.time.YearMonth currentMonth =
            java.time.YearMonth.now();

    List<Expense> currentMonthExpenses =
            new java.util.ArrayList<>();

    for (Expense expense : expenses) {

        java.time.YearMonth expenseMonth =
                java.time.YearMonth.from(
                        expense.getDate()
                );

        if (expenseMonth.equals(currentMonth)) {
            currentMonthExpenses.add(expense);
        }
    }

    return currentMonthExpenses;
}
    
public double calculateTotalSpent(List<Expense> expenses) {

    List<Expense> currentMonthExpenses =
            getCurrentMonthExpenses(expenses);

    double total = 0;

    for (Expense expense : currentMonthExpenses) {
        total += expense.getAmount();
    }

    return total;
}

    public double calculateUsagePercentage(List<Expense> expenses) {

        double totalSpent = calculateTotalSpent(expenses);

        return (totalSpent / budget.getMonthlyLimit()) * 100;
    }

    public BudgetStatus getBudgetStatus(List<Expense> expenses) {

        double usagePercentage = calculateUsagePercentage(expenses);

        if (usagePercentage >= 100) {
            return BudgetStatus.EXCEEDED;
        }

        if (usagePercentage >= 90) {
            return BudgetStatus.CRITICAL;
        }

        if (usagePercentage >= 70) {
            return BudgetStatus.WARNING;
        }

        return BudgetStatus.NORMAL;
    }

    public double getRemainingBudget(List<Expense> expenses) {

        double totalSpent = calculateTotalSpent(expenses);

        return budget.getMonthlyLimit() - totalSpent;
    }
public boolean canAfford(double amount, List<Expense> expenses) {

    double remainingBudget =
            getRemainingBudget(expenses);

    return amount <= remainingBudget;
}
public double calculateDailySpendingLimit(
        List<Expense> expenses,
        int daysRemaining) {

    if (daysRemaining <= 0) {
        return 0;
    }

    double remainingBudget =
            getRemainingBudget(expenses);

    return remainingBudget / daysRemaining;
}
public int calculateSpendingHealthScore(
        List<Expense> expenses) {

    expenses =
        getCurrentMonthExpenses(expenses);

if (expenses.isEmpty()) {
    return 100;
}

    double usagePercentage =
            calculateUsagePercentage(expenses);

    int score = 100;

    // Budget usage penalty
    if (usagePercentage >= 100) {
        score -= 50;
    } else if (usagePercentage >= 90) {
        score -= 35;
    } else if (usagePercentage >= 70) {
        score -= 20;
    } else if (usagePercentage >= 50) {
        score -= 10;
    }

    // Wants spending penalty
    double totalSpent =
            calculateTotalSpent(expenses);

    double wantsSpent = 0;

    for (Expense expense : expenses) {

        if (expense.getType() == ExpenseType.WANT) {
            wantsSpent += expense.getAmount();
        }
    }

    double wantsPercentage =
            (wantsSpent / totalSpent) * 100;

    if (wantsPercentage >= 50) {
        score -= 25;
    } else if (wantsPercentage >= 30) {
        score -= 15;
    } else if (wantsPercentage >= 20) {
        score -= 5;
    }

    return Math.max(0, Math.min(100, score));
}
}