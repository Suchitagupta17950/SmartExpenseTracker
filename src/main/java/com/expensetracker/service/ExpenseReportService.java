
package com.expensetracker.service;

import com.expensetracker.model.Expense;
import com.expensetracker.model.ExpenseCategory;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.EnumMap;

public class ExpenseReportService {

    public void exportMonthlyReport(
            List<Expense> expenses,
            YearMonth month,
            Path filePath) throws IOException {

        Map<ExpenseCategory, Double> categoryTotals =
                new EnumMap<>(ExpenseCategory.class);

        double totalSpent = 0;

        try (BufferedWriter writer = Files.newBufferedWriter(
                filePath, StandardCharsets.UTF_8)) {

            writer.write("Monthly Expense Report");
            writer.newLine();
            writer.write("Month," + csv(month.toString()));
            writer.newLine();
            writer.newLine();

            writer.write("Date,Category,Type,Amount,Description");
            writer.newLine();

            for (Expense expense : expenses) {
                if (YearMonth.from(expense.getDate()).equals(month)) {

                    writer.write(
                            csv(expense.getDate().toString()) + "," +
                            csv(expense.getCategory().toString()) + "," +
                            csv(expense.getType().toString()) + "," +
                            String.format(
                                    java.util.Locale.US,
                                    "%.2f",
                                    expense.getAmount()
                            ) + "," +
                            csv(expense.getDescription())
                    );
                    writer.newLine();

                    totalSpent += expense.getAmount();

                    categoryTotals.merge(
                            expense.getCategory(),
                            expense.getAmount(),
                            Double::sum
                    );
                }
            }

            writer.newLine();
            writer.write("Monthly Total," + String.format(
                    java.util.Locale.US, "%.2f", totalSpent));
            writer.newLine();
            writer.newLine();

            writer.write("Category,Total Amount");
            writer.newLine();

            for (Map.Entry<ExpenseCategory, Double> entry
                    : categoryTotals.entrySet()) {
                writer.write(
                        csv(entry.getKey().toString()) + "," +
                        String.format(
                                java.util.Locale.US,
                                "%.2f",
                                entry.getValue()
                        )
                );
                writer.newLine();
            }
        }
    }

    private String csv(String value) {
        if (value == null) {
            return "\"\"";
        }

        return "\"" + value.replace("\"", "\"\"") + "\"";
    }
}
