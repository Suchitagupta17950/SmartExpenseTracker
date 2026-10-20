package com.expensetracker.model;

import java.time.LocalDate;

public class Expense {

    private int id;
    private double amount;
    private ExpenseCategory category;
    private ExpenseType type;
    private LocalDate date;
    private String description;

    public Expense(int id, double amount,
                   ExpenseCategory category,
                   ExpenseType type,
                   LocalDate date,
                   String description) {

        this.id = id;
        this.amount = amount;
        this.category = category;
        this.type = type;
        this.date = date;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public ExpenseCategory getCategory() {
        return category;
    }

    public ExpenseType getType() {
        return type;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setCategory(ExpenseCategory category) {
        this.category = category;
    }

    public void setType(ExpenseType type) {
        this.type = type;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}