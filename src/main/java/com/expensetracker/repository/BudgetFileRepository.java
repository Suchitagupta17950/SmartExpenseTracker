package com.expensetracker.repository;

import com.expensetracker.model.Budget;

import java.io.*;

public class BudgetFileRepository
        implements BudgetRepository {

    private final String filePath =
            "data/budget.txt";

    public BudgetFileRepository() {
        createFileIfNotExists();
    }

    private void createFileIfNotExists() {

        File file =
                new File(filePath);

        try {

            File parentDirectory =
                    file.getParentFile();

            if (!parentDirectory.exists()) {
                parentDirectory.mkdirs();
            }

            if (!file.exists()) {
                file.createNewFile();
            }

        } catch (IOException e) {

            System.out.println(
                    "Unable to create budget file."
            );
        }
    }

    @Override
    public void saveBudget(Budget budget) {

        try (
                BufferedWriter writer =
                        new BufferedWriter(
                                new FileWriter(filePath)
                        )
        ) {

            writer.write(
                    String.valueOf(
                            budget.getMonthlyLimit()
                    )
            );

        } catch (IOException e) {

            System.out.println(
                    "Unable to save budget."
            );
        }
    }

    @Override
    public Budget getBudget() {

        try (
                BufferedReader reader =
                        new BufferedReader(
                                new FileReader(filePath)
                        )
        ) {

            String line =
                    reader.readLine();

            if (line == null ||
                    line.trim().isEmpty()) {

                return null;
            }

            double monthlyLimit =
                    Double.parseDouble(
                            line.trim()
                    );

            return new Budget(monthlyLimit);

        } catch (IOException |
                 NumberFormatException e) {

            System.out.println(
                    "Unable to read budget."
            );

            return null;
        }
    }
}