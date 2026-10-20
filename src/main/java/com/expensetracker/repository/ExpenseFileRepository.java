package com.expensetracker.repository;

import com.expensetracker.model.Expense;
import com.expensetracker.model.ExpenseCategory;
import com.expensetracker.model.ExpenseType;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ExpenseFileRepository implements ExpenseRepository {

    private final String filePath = "data/expenses.txt";

    public ExpenseFileRepository() {
        createFileIfNotExists();
    }

    private void createFileIfNotExists() {

        File file = new File(filePath);

        try {
            File parentDirectory = file.getParentFile();

            if (!parentDirectory.exists()) {
                parentDirectory.mkdirs();
            }

            if (!file.exists()) {
                file.createNewFile();
            }

        } catch (IOException e) {
            System.out.println("Unable to create expense file.");
        }
    }

    @Override
public void addExpense(Expense expense) {

    List<Expense> expenses = getAllExpenses();

    int nextId = 1;

    for (Expense existingExpense : expenses) {

        if (existingExpense.getId() >= nextId) {
            nextId = existingExpense.getId() + 1;
        }
    }

    Expense expenseWithId = new Expense(
            nextId,
            expense.getAmount(),
            expense.getCategory(),
            expense.getType(),
            expense.getDate(),
            expense.getDescription()
    );

    try (BufferedWriter writer =
                 new BufferedWriter(new FileWriter(filePath, true))) {

        writer.write(convertToFileFormat(expenseWithId));
        writer.newLine();

    } catch (IOException e) {

        System.out.println(
                "Unable to save expense."
        );
    }
} 
   @Override
    public List<Expense> getAllExpenses() {

        List<Expense> expenses = new ArrayList<>();

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(filePath))) {

            String line;

            while ((line = reader.readLine()) != null) {

                if (!line.trim().isEmpty()) {
                    expenses.add(convertFromFileFormat(line));
                }
            }

        } catch (IOException e) {
            System.out.println("Unable to read expenses.");
        }

        return expenses;
    }

    @Override
    public Expense findById(int id) {

        List<Expense> expenses = getAllExpenses();

        for (Expense expense : expenses) {

            if (expense.getId() == id) {
                return expense;
            }
        }

        return null;
    }

    @Override
    public void updateExpense(Expense updatedExpense) {

        List<Expense> expenses = getAllExpenses();

        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(filePath))) {

            for (Expense expense : expenses) {

                if (expense.getId() == updatedExpense.getId()) {
                    writer.write(convertToFileFormat(updatedExpense));
                } else {
                    writer.write(convertToFileFormat(expense));
                }

                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println("Unable to update expense.");
        }
    }

    @Override
    public void deleteExpense(int id) {

        List<Expense> expenses = getAllExpenses();

        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(filePath))) {

            for (Expense expense : expenses) {

                if (expense.getId() != id) {
                    writer.write(convertToFileFormat(expense));
                    writer.newLine();
                }
            }

        } catch (IOException e) {
            System.out.println("Unable to delete expense.");
        }
    }

    private String convertToFileFormat(Expense expense) {

        return expense.getId() + "|" +
                expense.getAmount() + "|" +
                expense.getCategory() + "|" +
                expense.getType() + "|" +
                expense.getDate() + "|" +
                expense.getDescription();
    }

    private Expense convertFromFileFormat(String line) {

        String[] data = line.split("\\|", -1);

        return new Expense(
                Integer.parseInt(data[0]),
                Double.parseDouble(data[1]),
                ExpenseCategory.valueOf(data[2]),
                ExpenseType.valueOf(data[3]),
                LocalDate.parse(data[4]),
                data[5]
        );
    }
}