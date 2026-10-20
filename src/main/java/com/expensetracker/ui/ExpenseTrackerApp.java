package com.expensetracker.ui;

import com.expensetracker.model.Budget;
import com.expensetracker.model.Expense;
import com.expensetracker.model.ExpenseCategory;
import com.expensetracker.model.ExpenseType;
import com.expensetracker.repository.ExpenseFileRepository;
import com.expensetracker.repository.ExpenseRepository;
import com.expensetracker.service.BudgetService;
import com.expensetracker.service.ExpenseAnalyticsService;
import com.expensetracker.service.ExpenseService;
import com.expensetracker.repository.BudgetRepository;
import com.expensetracker.repository.BudgetFileRepository;
import com.expensetracker.service.SpendingPredictionService;
import com.expensetracker.repository.SavingsGoalRepository;
import com.expensetracker.repository.SavingsGoalFileRepository;
import com.expensetracker.service.SavingsGoalService;
import com.expensetracker.model.SavingsGoal;
import com.expensetracker.service.ExpenseReportService;

import com.expensetracker.model.Budget;
import com.expensetracker.service.BudgetService;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.List;

import java.util.Map;
import java.util.EnumMap;

public class ExpenseTrackerApp extends JFrame {

    private Budget budget;
    private BudgetService budgetService;

    private ExpenseRepository expenseRepository;
    private ExpenseService expenseService;

    private ExpenseAnalyticsService analyticsService;
private SpendingPredictionService spendingPredictionService;
private BudgetRepository budgetRepository;
private SavingsGoalRepository savingsGoalRepository;
private SavingsGoalService savingsGoalService;
private double budgetAmount;

    public ExpenseTrackerApp() {

        expenseRepository =
                new ExpenseFileRepository();
budgetRepository =
        new BudgetFileRepository();


        analyticsService =
                new ExpenseAnalyticsService();
savingsGoalRepository =
        new SavingsGoalFileRepository();

savingsGoalService =
        new SavingsGoalService();

spendingPredictionService =
        new SpendingPredictionService();

Budget savedBudget =
        budgetRepository.getBudget();

if (savedBudget != null) {

    budget = savedBudget;

    budgetService =
            new BudgetService(budget);

    expenseService =
            new ExpenseService(
                    expenseRepository,
                    budgetService
            );
}

        setTitle("Smart Expense Tracker");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
getContentPane().setBackground(
        new Color(245, 247, 250)
);


        // TITLE

        // HEADER

JPanel headerPanel = new JPanel();
headerPanel.setBackground(
        new Color(41, 128, 185)
);
headerPanel.setLayout(new BorderLayout());

headerPanel.setBorder(
        BorderFactory.createEmptyBorder(
                25, 20, 25, 20
        )
);
JLabel titleLabel = new JLabel(
        "Smart Expense Tracker",
        SwingConstants.CENTER
);

titleLabel.setFont(
        new Font("Arial", Font.BOLD, 30)
);
titleLabel.setForeground(Color.WHITE);


JLabel welcomeLabel = new JLabel(
        "Track your spending • Manage your budget • Build better habits",
        SwingConstants.CENTER
);

welcomeLabel.setFont(
        new Font("Arial", Font.PLAIN, 16)
);
welcomeLabel.setForeground(Color.WHITE);

headerPanel.add(
        titleLabel,
        BorderLayout.NORTH
);

headerPanel.add(
        welcomeLabel,
        BorderLayout.CENTER
);

add(
        headerPanel,
        BorderLayout.NORTH
);
        // SET BUDGET

        JButton budgetButton =
        new RoundedButton("Set Monthly Budget",
                new Color(0, 123, 255));

JButton savingsGoalButton =
         new RoundedButton("Set Savings Goal", new Color(111, 66, 193));

savingsGoalButton.addActionListener(e ->
        setSavingsGoal()
);


        budgetButton.addActionListener(e -> {

            String input = JOptionPane.showInputDialog(
                    this,
                    "Enter your monthly budget:",
                    "Set Budget",
                    JOptionPane.QUESTION_MESSAGE
            );

            if (input == null) {
                return;
            }

            try {

                double budgetAmount =
                        Double.parseDouble(input);

                if (budgetAmount <= 0) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Budget must be greater than zero.",
                            "Invalid Budget",
                            JOptionPane.ERROR_MESSAGE
                    );

                    return;
                }

                budget =
                        new Budget(budgetAmount);

                budgetService =
                        new BudgetService(budget);
budgetRepository.saveBudget(budget);

                expenseService =
                        new ExpenseService(
                                expenseRepository,
                                budgetService
                        );

                JOptionPane.showMessageDialog(
                        this,
                        "Monthly Budget Set: ₹"
                                + budgetAmount,
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter a valid amount.",
                        "Invalid Input",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });


        // ADD EXPENSE

        
                JButton addExpenseButton =
        new RoundedButton("Add Expense", new Color(40, 167, 69));
        addExpenseButton.addActionListener(e -> {

            if (expenseService == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please set your monthly budget first.",
                        "Budget Required",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            JTextField amountField =
                    new JTextField();

            JComboBox<ExpenseCategory> categoryBox =
                    new JComboBox<>(
                            ExpenseCategory.values()
                    );

            JComboBox<ExpenseType> typeBox =
                    new JComboBox<>(
                            ExpenseType.values()
                    );

            JTextField descriptionField =
                    new JTextField();

            Object[] fields = {

                    "Amount:",
                    amountField,

                    "Category:",
                    categoryBox,

                    "Type:",
                    typeBox,

                    "Description:",
                    descriptionField
            };

            int result =
                    JOptionPane.showConfirmDialog(
                            this,
                            fields,
                            "Add Expense",
                            JOptionPane.OK_CANCEL_OPTION
                    );

            if (result != JOptionPane.OK_OPTION) {
                return;
            }

            try {

                double amount =
                        Double.parseDouble(
                                amountField
                                        .getText()
                                        .trim()
                        );

                if (amount <= 0) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Amount must be greater than zero.",
                            "Invalid Amount",
                            JOptionPane.ERROR_MESSAGE
                    );

                    return;
                }

                ExpenseCategory category =
                        (ExpenseCategory)
                                categoryBox
                                        .getSelectedItem();

                ExpenseType type =
                        (ExpenseType)
                                typeBox
                                        .getSelectedItem();

                String description =
                        descriptionField
                                .getText()
                                .trim();

                if (description.isEmpty()) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Description cannot be empty.",
                            "Invalid Description",
                            JOptionPane.ERROR_MESSAGE
                    );

                    return;
                }

                Expense expense =
                        new Expense(
                                0,
                                amount,
                                category,
                                type,
                                LocalDate.now(),
                                description
                        );

List<Expense> previousExpenses =
        expenseRepository.getAllExpenses();

String spendingAlert =
        analyticsService.getSpendingAlert(
                expense,
                previousExpenses
        );

if (!spendingAlert.equals("NORMAL")) {

    String alertMessage;

    if (spendingAlert.equals("HIGHLY UNUSUAL")) {
        alertMessage = "🚨 HIGHLY UNUSUAL EXPENSE!\n\n"
                + "This expense is more than 3 times "
                + "your category average.";
    } else {
        alertMessage = "⚠ UNUSUAL EXPENSE DETECTED!\n\n"
                + "This expense is more than 1.5 times "
                + "your category average.";
    }

    int choice = JOptionPane.showConfirmDialog(
            this,
            alertMessage
                    + "\n\nAmount: ₹"
                    + expense.getAmount()
                    + "\nCategory: "
                    + expense.getCategory()
                    + "\n\nDo you want to continue?",
            "Spending Alert: " + spendingAlert,
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
    );

    if (choice != JOptionPane.YES_OPTION) {
        JOptionPane.showMessageDialog(
                this,
                "Expense cancelled.",
                "Expense Cancelled",
                JOptionPane.INFORMATION_MESSAGE
        );

        return;
    }
}
if (expense.getType() == ExpenseType.WANT) {

    if (expense.getAmount() >= 2000) {

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "⚠ HIGH-VALUE WANT EXPENSE\n\n"
                        + "Amount: ₹"
                        + expense.getAmount()
                        + "\n\n"
                        + "This is a WANT expense "
                        + "and the amount is ₹2000 or more.\n\n"
                        + "Is this purchase really necessary?\n\n"
                        + "Do you still want to continue?",
                        "Impulse Purchase Warning",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

        if (choice != JOptionPane.YES_OPTION) {

            JOptionPane.showMessageDialog(
                    this,
                    "Expense cancelled.\n\n"
                    + "Taking some time before a large "
                    + "want purchase can help avoid "
                    + "impulse spending.",
                    "Purchase Cancelled",
                    JOptionPane.INFORMATION_MESSAGE
            );

            return;
        }

    } else {

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "🛒 IMPULSE PURCHASE CHECK\n\n"
                        + "This is marked as a WANT expense.\n\n"
                        + "Is this purchase planned?\n\n"
                        + "Do you want to continue?",
                        "Impulse Purchase Check",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

        if (choice != JOptionPane.YES_OPTION) {

            JOptionPane.showMessageDialog(
                    this,
                    "Expense cancelled.",
                    "Purchase Cancelled",
                    JOptionPane.INFORMATION_MESSAGE
            );

            return;
        }
    }
}

boolean added =
        expenseService.addExpense(expense);

                if (added) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Expense added successfully!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                    List<Expense> expenses =
                            expenseRepository
                                    .getAllExpenses();

                    double usage =
                            budgetService
                                    .calculateUsagePercentage(
                                            expenses
                                    );

                    String status =
                            budgetService
                                    .getBudgetStatus(
                                            expenses
                                    )
                                    .toString();

                    if (usage >= 100) {

                        JOptionPane.showMessageDialog(
                                this,
                                "⚠ BUDGET EXCEEDED!\n\n"
                                + "You have reached or exceeded "
                                + "your monthly budget.",
                                "Budget Alert",
                                JOptionPane.ERROR_MESSAGE
                        );

                    } else if (usage >= 90) {

                        JOptionPane.showMessageDialog(
                                this,
                                "⚠ CRITICAL BUDGET ALERT!\n\n"
                                + "You have used "
                                + String.format(
                                        "%.2f",
                                        usage
                                )
                                + "% of your budget.\n\n"
                                + "Status: "
                                + status,
                                "Budget Alert",
                                JOptionPane.WARNING_MESSAGE
                        );

                    } else if (usage >= 70) {

                        JOptionPane.showMessageDialog(
                                this,
                                "⚠ BUDGET WARNING!\n\n"
                                + "You have used "
                                + String.format(
                                        "%.2f",
                                        usage
                                )
                                + "% of your budget.\n\n"
                                + "Status: "
                                + status,
                                "Budget Alert",
                                JOptionPane.WARNING_MESSAGE
                        );
                    }
                }

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter a valid amount.",
                        "Invalid Amount",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });
// SEARCH EXPENSE

JButton searchExpenseButton =
        new RoundedButton("Search Expense", new Color(0, 123, 255));


searchExpenseButton.addActionListener(e -> {

    if (expenseService == null) {

        JOptionPane.showMessageDialog(
                this,
                "Please set your monthly budget first.",
                "Budget Required",
                JOptionPane.WARNING_MESSAGE
        );

        return;
    }

    String keyword =
            JOptionPane.showInputDialog(
                    this,
                    "Enter description keyword:",
                    "Search Expense",
                    JOptionPane.QUESTION_MESSAGE
            );

    if (keyword == null) {
        return;
    }

    keyword = keyword.trim();

    if (keyword.isEmpty()) {

        JOptionPane.showMessageDialog(
                this,
                "Search keyword cannot be empty.",
                "Invalid Input",
                JOptionPane.WARNING_MESSAGE
        );

        return;
    }

    List<Expense> results =
            expenseService.searchExpenses(keyword);

    if (results.isEmpty()) {

        JOptionPane.showMessageDialog(
                this,
                "No expenses found for: " + keyword,
                "Search Result",
                JOptionPane.INFORMATION_MESSAGE
        );

        return;
    }

    StringBuilder output =
            new StringBuilder();

    output.append(
            "========== SEARCH RESULTS ==========\n\n"
    );

    for (Expense expense : results) {

        output.append("ID: ")
                .append(expense.getId())
                .append(" | ₹")
                .append(expense.getAmount())
                .append(" | ")
                .append(expense.getCategory())
                .append(" | ")
                .append(expense.getType())
                .append(" | ")
                .append(expense.getDate())
                .append(" | ")
                .append(expense.getDescription())
                .append("\n");
    }

    JTextArea textArea =
            new JTextArea(output.toString());

    textArea.setEditable(false);

    textArea.setFont(
            new Font(
                    "Monospaced",
                    Font.PLAIN,
                    14
            )
    );

    JScrollPane scrollPane =
            new JScrollPane(textArea);

    scrollPane.setPreferredSize(
            new Dimension(750, 400)
    );

    JOptionPane.showMessageDialog(
            this,
            scrollPane,
            "Search Results",
            JOptionPane.INFORMATION_MESSAGE
    );
});
JButton updateExpenseButton =
         new RoundedButton("Update Expense", new Color(0, 123, 255));
updateExpenseButton.addActionListener(e -> {

    if (expenseService == null) {

        JOptionPane.showMessageDialog(
                this,
                "Please set your monthly budget first.",
                "Budget Required",
                JOptionPane.WARNING_MESSAGE
        );

        return;
    }

    String idInput =
            JOptionPane.showInputDialog(
                    this,
                    "Enter Expense ID to update:",
                    "Update Expense",
                    JOptionPane.QUESTION_MESSAGE
            );

    if (idInput == null) {
        return;
    }

    try {

        int id = Integer.parseInt(idInput.trim());

        Expense existingExpense =
                expenseService.findExpense(id);

        if (existingExpense == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Expense not found!",
                    "Update Expense",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        JTextField amountField =
                new JTextField(
                        String.valueOf(
                                existingExpense.getAmount()
                        )
                );

        JComboBox<ExpenseCategory> categoryBox =
                new JComboBox<>(
                        ExpenseCategory.values()
                );

        categoryBox.setSelectedItem(
                existingExpense.getCategory()
        );

        JComboBox<ExpenseType> typeBox =
                new JComboBox<>(
                        ExpenseType.values()
                );

        typeBox.setSelectedItem(
                existingExpense.getType()
        );

        JTextField descriptionField =
                new JTextField(
                        existingExpense.getDescription()
                );

        Object[] fields = {

                "Amount:",
                amountField,

                "Category:",
                categoryBox,

                "Type:",
                typeBox,

                "Description:",
                descriptionField
        };

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        fields,
                        "Update Expense #" + id,
                        JOptionPane.OK_CANCEL_OPTION
                );

        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        double amount =
                Double.parseDouble(
                        amountField.getText().trim()
                );

        if (amount <= 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Amount must be greater than zero.",
                    "Invalid Amount",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        String description =
                descriptionField.getText().trim();

        if (description.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Description cannot be empty.",
                    "Invalid Description",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        ExpenseCategory category =
                (ExpenseCategory)
                        categoryBox.getSelectedItem();

        ExpenseType type =
                (ExpenseType)
                        typeBox.getSelectedItem();

        Expense updatedExpense =
                new Expense(
                        id,
                        amount,
                        category,
                        type,
                        existingExpense.getDate(),
                        description
                );

        boolean updated =
                expenseService.updateExpense(
                        updatedExpense
                );

        if (updated) {

            JOptionPane.showMessageDialog(
                    this,
                    "Expense updated successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }

    } catch (NumberFormatException ex) {

        JOptionPane.showMessageDialog(
                this,
                "Please enter valid numeric values.",
                "Invalid Input",
                JOptionPane.ERROR_MESSAGE
        );
    }
});
JButton deleteExpenseButton =
        new RoundedButton("Delete Expense", new Color(220, 53, 69));
deleteExpenseButton.addActionListener(e -> {

    if (expenseService == null) {

        JOptionPane.showMessageDialog(
                this,
                "Please set your monthly budget first.",
                "Budget Required",
                JOptionPane.WARNING_MESSAGE
        );

        return;
    }

    String idInput =
            JOptionPane.showInputDialog(
                    this,
                    "Enter Expense ID to delete:",
                    "Delete Expense",
                    JOptionPane.QUESTION_MESSAGE
            );

    if (idInput == null) {
        return;
    }

    try {

        int id =
                Integer.parseInt(
                        idInput.trim()
                );

        Expense expense =
                expenseService.findExpense(id);

        if (expense == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Expense not found!",
                    "Delete Expense",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int confirmation =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete?\n\n"
                        + "₹" + expense.getAmount()
                        + " | "
                        + expense.getCategory()
                        + " | "
                        + expense.getDescription(),
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

        if (confirmation != JOptionPane.YES_OPTION) {
            return;
        }

        boolean deleted =
                expenseService.deleteExpense(id);

        if (deleted) {

            JOptionPane.showMessageDialog(
                    this,
                    "Expense deleted successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }

    } catch (NumberFormatException ex) {

        JOptionPane.showMessageDialog(
                this,
                "Please enter a valid Expense ID.",
                "Invalid Input",
                JOptionPane.ERROR_MESSAGE
        );
    }
});


        // VIEW EXPENSES

        JButton viewExpensesButton =
new RoundedButton("View Expenses", new Color(0, 123, 255));

        viewExpensesButton.addActionListener(e -> {

            List<Expense> expenses =
                    expenseRepository
                            .getAllExpenses();

            if (expenses.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "No expenses found.",
                        "Expenses",
                        JOptionPane.INFORMATION_MESSAGE
                );

                return;
            }

            StringBuilder result =
                    new StringBuilder();

            for (Expense expense : expenses) {

                result.append("ID: ")
                        .append(expense.getId())
                        .append(" | ₹")
                        .append(expense.getAmount())
                        .append(" | ")
                        .append(expense.getCategory())
                        .append(" | ")
                        .append(expense.getType())
                        .append(" | ")
                        .append(expense.getDate())
                        .append(" | ")
                        .append(expense.getDescription())
                        .append("\n");
            }

            JTextArea textArea =
                    new JTextArea(
                            result.toString()
                    );

            textArea.setEditable(false);

            textArea.setFont(
                    new Font(
                            "Monospaced",
                            Font.PLAIN,
                            14
                    )
            );

            JScrollPane scrollPane =
                    new JScrollPane(textArea);

            scrollPane.setPreferredSize(
                    new Dimension(750, 400)
            );

            JOptionPane.showMessageDialog(
                    this,
                    scrollPane,
                    "All Expenses",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });


        // DASHBOARD

        JButton dashboardButton =
                 new RoundedButton("Dashboard", new Color(108, 117, 125));

        dashboardButton.addActionListener(e -> {

            if (budgetService == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please set your monthly budget first.",
                        "Budget Required",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            List<Expense> expenses =
                    expenseRepository
                            .getAllExpenses();

            double totalSpent =
                    budgetService
                            .calculateTotalSpent(
                                    expenses
                            );

            double monthlyBudget =
                    budget.getMonthlyLimit();

            double remaining =
                    budgetService
                            .getRemainingBudget(
                                    expenses
                            );

            double usage =
                    budgetService
                            .calculateUsagePercentage(
                                    expenses
                            );

            String status =
                    budgetService
                            .getBudgetStatus(
                                    expenses
                            )
                            .toString();

            int healthScore =
                    budgetService
                            .calculateSpendingHealthScore(
                                    expenses
                            );






java.time.YearMonth dashboardMonth =
        java.time.YearMonth.now();

String monthTitle =
        dashboardMonth.format(
                java.time.format.DateTimeFormatter.ofPattern(
                        "MMMM yyyy",
                        java.util.Locale.ENGLISH
                )
        );

JLabel monthLabel = new JLabel(
        monthTitle,
        SwingConstants.CENTER
);

monthLabel.setFont(
        new Font("Arial", Font.BOLD, 24)
);

monthLabel.setForeground(
        new Color(45, 55, 72)
);
            
JPanel dashboardPanel = new JPanel(
        new GridLayout(2, 3, 12, 12)
);

dashboardPanel.setBackground(
        new Color(245, 247, 250)
);

dashboardPanel.setBorder(
        BorderFactory.createEmptyBorder(20, 20, 20, 20)
);

addDashboardCard(
        dashboardPanel,
        "MONTHLY BUDGET",
        String.format("₹%.2f", monthlyBudget),
        new Color(0, 123, 255)
);

addDashboardCard(
        dashboardPanel,
        "TOTAL SPENT",
        String.format("₹%.2f", totalSpent),
        new Color(220, 53, 69)
);

addDashboardCard(
        dashboardPanel,
        "REMAINING",
        String.format("₹%.2f", remaining),
        new Color(40, 167, 69)
);

addDashboardCard(
        dashboardPanel,
        "BUDGET USED",
        String.format("%.2f%%", usage),
        new Color(255, 153, 0)
);

Color statusColor;

if (status.equals("EXCEEDED")) {
    statusColor = new Color(220, 53, 69);  // Red
} else if (status.equals("CRITICAL")) {
    statusColor = new Color(255, 153, 0);  // Orange
} else if (status.equals("WARNING")) {
    statusColor = new Color(255, 193, 7);   // Yellow
} else {
    statusColor = new Color(40, 167, 69);  // Green
}

addDashboardCard(
        dashboardPanel,
        "BUDGET STATUS",
        status,
        statusColor
);
Color healthColor;

if (healthScore >= 80) {
    healthColor = new Color(40, 167, 69);  // Green
} else if (healthScore >= 50) {
    healthColor = new Color(255, 153, 0);  // Orange
} else {
    healthColor = new Color(220, 53, 69);  // Red
}

addDashboardCard(
        dashboardPanel,
        "HEALTH SCORE",
        healthScore + " / 100",
        healthColor
);

JPanel progressPanel = new JPanel(new BorderLayout(10, 5));

JLabel budgetWarning = new JLabel();

budgetWarning.setFont(
        new Font("Segoe UI", Font.BOLD, 13)
);

if (remaining < 0) {
    budgetWarning.setText(
            "⚠ Budget exceeded by ₹" + String.format("%.2f", Math.abs(remaining))
    );
    budgetWarning.setForeground(new Color(220, 53, 69));
} else if (usage >= 80) {
    budgetWarning.setText(
            "⚠ Warning: You have used " + String.format("%.1f%%", usage) + " of your budget"
    );
    budgetWarning.setForeground(new Color(255, 153, 0));
} else {
    budgetWarning.setText("You are within your monthly budget.");
    budgetWarning.setForeground(new Color(40, 167, 69));
}
progressPanel.setBackground(new Color(245, 247, 250));
progressPanel.setBorder(
        BorderFactory.createEmptyBorder(0, 20, 15, 20)
);

JLabel progressTitle = new JLabel("Monthly Budget Usage");
progressTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));

JProgressBar budgetProgress = new JProgressBar(0, 100);
budgetProgress.setValue((int) Math.max(0, Math.min(100, usage)));
budgetProgress.setStringPainted(true);
budgetProgress.setString(String.format("%.1f%% used", usage));
if (usage >= 80) {
    budgetProgress.setForeground(new Color(220, 53, 69)); // Red
} else if (usage >= 50) {
    budgetProgress.setForeground(new Color(255, 153, 0)); // Orange
} else {
    budgetProgress.setForeground(new Color(40, 167, 69)); // Green
}

progressPanel.add(progressTitle, BorderLayout.NORTH);
progressPanel.add(budgetProgress, BorderLayout.CENTER);
progressPanel.add(budgetWarning, BorderLayout.SOUTH);


Map<ExpenseCategory, Double> categoryTotals =
        new EnumMap<>(ExpenseCategory.class);

java.time.YearMonth currentMonth =
        java.time.YearMonth.now();

for (Expense expense : expenses) {

    java.time.YearMonth expenseMonth =
            java.time.YearMonth.from(
                    expense.getDate()
            );

    if (expenseMonth.equals(currentMonth)) {
        categoryTotals.merge(
                expense.getCategory(),
                expense.getAmount(),
                Double::sum
        );
    }
}

JPanel categoryPanel = new JPanel(new BorderLayout());
categoryPanel.setBackground(Color.WHITE);

categoryPanel.setBorder(
        BorderFactory.createTitledBorder(
                "Expense Category Breakdown"
        )
);

categoryPanel.add(
        createCategoryBarChart(categoryTotals),
        BorderLayout.CENTER
);

JPanel fullDashboard = new JPanel(new BorderLayout(0, 10));
fullDashboard.setBackground(new Color(245, 247, 250));

JPanel dashboardContent = new JPanel(new BorderLayout(0, 10));
dashboardContent.setBackground(new Color(245, 247, 250));

JPanel cardsWithMonth = new JPanel(new BorderLayout(0, 10));
cardsWithMonth.setBackground(new Color(245, 247, 250));

cardsWithMonth.add(monthLabel, BorderLayout.NORTH);
cardsWithMonth.add(dashboardPanel, BorderLayout.CENTER);

dashboardContent.add(cardsWithMonth, BorderLayout.CENTER);
dashboardContent.add(categoryPanel, BorderLayout.SOUTH);

fullDashboard.add(dashboardContent, BorderLayout.CENTER);
fullDashboard.add(progressPanel, BorderLayout.SOUTH);
fullDashboard.add(progressPanel, BorderLayout.SOUTH);

JOptionPane.showMessageDialog(
        this,
        fullDashboard,
        "Smart Expense Tracker Dashboard",
        JOptionPane.PLAIN_MESSAGE
);



        });


        // CAN I AFFORD THIS

        JButton affordButton =
                new RoundedButton("Can I Afford This?", new Color(0, 123, 255));
        affordButton.addActionListener(e -> {

            if (budgetService == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please set your monthly budget first.",
                        "Budget Required",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            String input =
                    JOptionPane.showInputDialog(
                            this,
                            "Enter the amount you want to spend:",
                            "Can I Afford This?",
                            JOptionPane.QUESTION_MESSAGE
                    );

            if (input == null) {
                return;
            }

            try {

                double amount =
                        Double.parseDouble(input);

                List<Expense> expenses =
                        expenseRepository
                                .getAllExpenses();

                boolean affordable =
                        budgetService.canAfford(
                                amount,
                                expenses
                        );

                double remaining =
                        budgetService
                                .getRemainingBudget(
                                        expenses
                                );

                if (affordable) {

                    JOptionPane.showMessageDialog(
                            this,
                            "YES! You can afford this.\n\n"
                            + "Amount: ₹"
                            + amount
                            + "\nRemaining Budget: ₹"
                            + remaining,
                            "Affordability Check",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "NO! This expense exceeds "
                            + "your remaining budget.\n\n"
                            + "Amount: ₹"
                            + amount
                            + "\nRemaining Budget: ₹"
                            + remaining,
                            "Affordability Check",
                            JOptionPane.WARNING_MESSAGE
                    );
                }

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter a valid amount.",
                        "Invalid Input",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });


        // DAILY SPENDING LIMIT

        // DAILY SPENDING LIMIT

JButton dailyLimitButton =
        new RoundedButton("Daily Spending Limit", new Color(0, 123, 255));

dailyLimitButton.addActionListener(e -> {

    if (budgetService == null) {

        JOptionPane.showMessageDialog(
                this,
                "Please set your monthly budget first.",
                "Budget Required",
                JOptionPane.WARNING_MESSAGE
        );

        return;
    }

    java.time.LocalDate today =
            java.time.LocalDate.now();

    int daysRemaining =
            today.lengthOfMonth()
            - today.getDayOfMonth();

    List<Expense> expenses =
            expenseRepository.getAllExpenses();

    double dailyLimit =
            budgetService.calculateDailySpendingLimit(
                    expenses,
                    daysRemaining
            );

    double remaining =
            budgetService.getRemainingBudget(
                    expenses
            );

    JOptionPane.showMessageDialog(
            this,
            "Remaining Budget: ₹"
            + String.format(
                    "%.2f",
                    remaining
            )
            + "\n\nDays Remaining: "
            + daysRemaining
            + "\n\nRecommended Daily Limit: ₹"
            + String.format(
                    "%.2f",
                    dailyLimit
            ),
            "Daily Spending Limit",
            JOptionPane.INFORMATION_MESSAGE
    );
});


JButton predictButton =
         new RoundedButton("Predict Month-End Spending", new Color(23, 162, 184));
predictButton.addActionListener(e ->
        predictMonthEndSpending()
);

add(predictButton);
JButton exportReportButton =
        new RoundedButton(
                "Export Monthly Report",
                new Color(40, 167, 69)
        );

exportReportButton.addActionListener(
        e -> exportMonthlyReport()
);

add(exportReportButton);


        // WHERE DID MY MONEY GO?

JButton categoryAnalysisButton =
        new RoundedButton("Where Did My Money Go?", new Color(0, 123, 255));        
               
        categoryAnalysisButton.addActionListener(e -> {

            List<Expense> expenses =
                    expenseRepository
                            .getAllExpenses();

            if (expenses.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "No expenses found.",
                        "Category Analysis",
                        JOptionPane.INFORMATION_MESSAGE
                );

                return;
            }

           
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

double totalSpent = 0;

for (Expense expense : currentMonthExpenses) {
    totalSpent += expense.getAmount();
}

            StringBuilder result =
                    new StringBuilder();

            result.append(
                    "========== WHERE DID MY MONEY GO? ==========\n\n"
            );

            result.append(
                    "Total Spending: ₹"
                    + String.format(
                            "%.2f",
                            totalSpent
                    )
                    + "\n\n"
            );

           
            for (ExpenseCategory category :
                    ExpenseCategory.values()) {

                double categorySpending =
                        analyticsService
                                .calculateCategorySpending(
                                        currentMonthExpenses,
                                        category
                                );

                if (categorySpending > 0) {

                    double percentage =
                            analyticsService
                                    .calculateCategoryPercentage(
                                            currentMonthExpenses,
                                            category
                                    );

                    result.append(
                            category
                            + " → ₹"
                            + String.format(
                                    "%.2f",
                                    categorySpending
                            )
                            + " → "
                            + String.format(
                                    "%.2f",
                                    percentage
                            )
                            + "%\n"
                    );
                }
            }

            JTextArea textArea =
                    new JTextArea(
                            result.toString()
                    );

            textArea.setEditable(false);

            textArea.setFont(
                    new Font(
                            "Monospaced",
                            Font.PLAIN,
                            14
                    )
            );

            JScrollPane scrollPane =
                    new JScrollPane(textArea);

            scrollPane.setPreferredSize(
                    new Dimension(650, 400)
            );

            JOptionPane.showMessageDialog(
                    this,
                    scrollPane,
                    "Category Analysis",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });



        // BUTTON PANEL

        // BUTTON PANEL
// BUTTON STYLE

Font buttonFont = new Font(
        "Arial",
        Font.BOLD,
        14
);
Color buttonColor = new Color(52, 152, 219);
Color buttonTextColor = Color.WHITE;


JPanel buttonPanel = new JPanel(
        new GridLayout(4, 3, 12, 12)
);
buttonPanel.setBackground(
        new Color(245, 247, 250)
);

 
buttonPanel.setBorder(
        BorderFactory.createEmptyBorder(
                20, 40, 30, 40
        )
);

// Row 1

addExpenseButton.setFont(buttonFont);
addExpenseButton.setBackground(buttonColor);
addExpenseButton.setForeground(buttonTextColor);
buttonPanel.add(addExpenseButton);

viewExpensesButton.setFont(buttonFont);
viewExpensesButton.setBackground(buttonColor);
viewExpensesButton.setForeground(buttonTextColor);
buttonPanel.add(viewExpensesButton);

searchExpenseButton.setFont(buttonFont);
searchExpenseButton.setBackground(buttonColor);
searchExpenseButton.setForeground(buttonTextColor);
buttonPanel.add(searchExpenseButton);

updateExpenseButton.setFont(buttonFont);
updateExpenseButton.setBackground(buttonColor);
updateExpenseButton.setForeground(buttonTextColor);
buttonPanel.add(updateExpenseButton);

deleteExpenseButton.setFont(buttonFont);
deleteExpenseButton.setBackground(buttonColor);
deleteExpenseButton.setForeground(buttonTextColor);
buttonPanel.add(deleteExpenseButton);

budgetButton.setFont(buttonFont);
budgetButton.setBackground(buttonColor);
budgetButton.setForeground(buttonTextColor);
buttonPanel.add(budgetButton);

dashboardButton.setFont(buttonFont);
dashboardButton.setBackground(buttonColor);
dashboardButton.setForeground(buttonTextColor);
buttonPanel.add(dashboardButton);

categoryAnalysisButton.setFont(buttonFont);
categoryAnalysisButton.setBackground(buttonColor);
categoryAnalysisButton.setForeground(buttonTextColor);
buttonPanel.add(categoryAnalysisButton);

dailyLimitButton.setFont(buttonFont);
dailyLimitButton.setBackground(buttonColor);
dailyLimitButton.setForeground(buttonTextColor);
buttonPanel.add(dailyLimitButton);

affordButton.setFont(buttonFont);
affordButton.setBackground(buttonColor);
affordButton.setForeground(buttonTextColor);
buttonPanel.add(affordButton);

savingsGoalButton.setFont(buttonFont);
savingsGoalButton.setBackground(buttonColor);
savingsGoalButton.setForeground(buttonTextColor);
buttonPanel.add(savingsGoalButton);

predictButton.setFont(buttonFont);
predictButton.setBackground(buttonColor);
predictButton.setForeground(buttonTextColor);
buttonPanel.add(predictButton);

exportReportButton.setFont(buttonFont);
exportReportButton.setBackground(new Color(40, 167, 69));
exportReportButton.setForeground(buttonTextColor);
buttonPanel.add(exportReportButton);
// APPLY BUTTON STYLE

addExpenseButton.setFocusPainted(false);
viewExpensesButton.setFocusPainted(false);
searchExpenseButton.setFocusPainted(false);
updateExpenseButton.setFocusPainted(false);
deleteExpenseButton.setFocusPainted(false);
budgetButton.setFocusPainted(false);
dashboardButton.setFocusPainted(false);
categoryAnalysisButton.setFocusPainted(false);
dailyLimitButton.setFocusPainted(false);
affordButton.setFocusPainted(false);
savingsGoalButton.setFocusPainted(false);
predictButton.setFocusPainted(false);



add(
        buttonPanel,
        BorderLayout.CENTER
);


java.awt.event.MouseAdapter hoverEffect = new java.awt.event.MouseAdapter() {
    @Override
    public void mouseEntered(java.awt.event.MouseEvent e) {
        e.getComponent().setBackground(new Color(30, 100, 180));
    }

    @Override
    public void mouseExited(java.awt.event.MouseEvent e) {
       javax.swing.JButton button =
                (javax.swing.JButton) e.getComponent();

        if (button == addExpenseButton) {
            button.setBackground(new Color(40, 167, 69));
        } else if (button == budgetButton) {
            button.setBackground(new Color(0, 123, 255));
        } else if (button == dashboardButton) {
            button.setBackground(new Color(108, 117, 125));
        } else if (button == savingsGoalButton) {
            button.setBackground(new Color(111, 66, 193));
        } else if (button == predictButton) {
            button.setBackground(new Color(23, 162, 184));
        } else if (button == deleteExpenseButton) {
            button.setBackground(new Color(220, 53, 69));
        } else {
            button.setBackground(buttonColor);
        }
    }
};

addExpenseButton.addMouseListener(hoverEffect);
viewExpensesButton.addMouseListener(hoverEffect);
searchExpenseButton.addMouseListener(hoverEffect);
updateExpenseButton.addMouseListener(hoverEffect);
deleteExpenseButton.addMouseListener(hoverEffect);
budgetButton.addMouseListener(hoverEffect);
dashboardButton.addMouseListener(hoverEffect);
categoryAnalysisButton.addMouseListener(hoverEffect);
dailyLimitButton.addMouseListener(hoverEffect);
affordButton.addMouseListener(hoverEffect);
savingsGoalButton.addMouseListener(hoverEffect);
predictButton.addMouseListener(hoverEffect);


addExpenseButton.setBackground(new Color(40, 167, 69));
budgetButton.setBackground(new Color(0, 123, 255));
dashboardButton.setBackground(new Color(108, 117, 125));
savingsGoalButton.setBackground(new Color(111, 66, 193));
predictButton.setBackground(new Color(23, 162, 184));
deleteExpenseButton.setBackground(new Color(220, 53, 69));



setVisible(true);
    }


private void addDashboardCard(
        JPanel panel,
        String title,
        String value,
        Color accentColor) {

    JPanel card = new JPanel(new BorderLayout(5, 12)) {
    @Override
protected void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D) g.create();

    g2.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON
    );

    g2.setColor(getBackground());

    g2.fillRoundRect(
            0, 0,
            getWidth(),
            getHeight(),
            20, 20
    );

    g2.dispose();
}
};
    card.setBackground(Color.WHITE);
card.setOpaque(false);

    card.setBorder(
        BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(225, 230, 237),
                        1
                ),
                BorderFactory.createEmptyBorder(
                        18, 15, 18, 15
                )
        )
);

    JLabel titleLabel = new JLabel(title);
    titleLabel.setFont(
            new Font("Segoe UI", Font.BOLD, 13)

    );
    titleLabel.setForeground(new Color(95, 105, 120));

    JLabel valueLabel = new JLabel(value);
    valueLabel.setFont(
            new Font("Segoe UI", Font.BOLD, 24)
    );
    valueLabel.setForeground(accentColor);

    card.add(titleLabel, BorderLayout.NORTH);
    card.add(valueLabel, BorderLayout.CENTER);

    panel.add(card);
}

private JPanel createCategoryBarChart(
        Map<ExpenseCategory, Double> categoryTotals) {

    JPanel chartPanel = new JPanel();
    chartPanel.setLayout(
            new BoxLayout(chartPanel, BoxLayout.Y_AXIS)
    );
    chartPanel.setBackground(Color.WHITE);
    chartPanel.setBorder(
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
    );

    double maxAmount = 0;

    for (double amount : categoryTotals.values()) {
        maxAmount = Math.max(maxAmount, amount);
    }

    for (Map.Entry<ExpenseCategory, Double> entry
            : categoryTotals.entrySet()) {

        JPanel row = new JPanel(new BorderLayout(10, 5));
        row.setBackground(Color.WHITE);
        row.setBorder(
                BorderFactory.createEmptyBorder(5, 0, 5, 0)
        );

        JLabel nameLabel = new JLabel(entry.getKey().toString());
        nameLabel.setPreferredSize(new Dimension(100, 25));

        JProgressBar bar = new JProgressBar(0, 100);

        int percentage = maxAmount > 0
                ? (int) (entry.getValue() / maxAmount * 100)
                : 0;

        bar.setValue(percentage);
        bar.setStringPainted(false);
        

Color barColor;

switch (entry.getKey()) {
    case FOOD:
        barColor = new Color(46, 204, 113);
        break;
    case TRAVEL:
        barColor = new Color(52, 152, 219);
        break;
    case SHOPPING:
        barColor = new Color(155, 89, 182);
        break;
    case BILLS:
        barColor = new Color(241, 196, 15);
        break;
    case ENTERTAINMENT:
        barColor = new Color(231, 76, 60);
        break;
    case HEALTH:
        barColor = new Color(26, 188, 156);
        break;
    case EDUCATION:
        barColor = new Color(230, 126, 34);
        break;
    case OTHER:
        barColor = new Color(127, 140, 141);
        break;
    default:
        barColor = new Color(54, 132, 235);
}

bar.setForeground(barColor);

bar.setForeground(barColor);

        bar.setBackground(new Color(235, 240, 246));

        JLabel amountLabel = new JLabel(
                String.format("₹%.2f", entry.getValue())
        );
        amountLabel.setPreferredSize(new Dimension(100, 25));
        amountLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        row.add(nameLabel, BorderLayout.WEST);
        row.add(bar, BorderLayout.CENTER);
        row.add(amountLabel, BorderLayout.EAST);

        chartPanel.add(row);
    }

    return chartPanel;
}



    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                ExpenseTrackerApp::new
        );
    }

private void predictMonthEndSpending() {

    List<Expense> expenses =
            expenseRepository.getAllExpenses();

    double predictedSpending =
            spendingPredictionService
                    .predictMonthEndSpending(
                            expenses
                    );

    java.time.LocalDate today =
            java.time.LocalDate.now();

    int currentDay =
            today.getDayOfMonth();

    double totalSpent = 0;

    java.time.YearMonth currentMonth =
            java.time.YearMonth.now();

    for (Expense expense : expenses) {

        if (java.time.YearMonth.from(
                expense.getDate()
        ).equals(currentMonth)) {

            totalSpent += expense.getAmount();
        }
    }

    double averageDailySpending =
            currentDay > 0
                    ? totalSpent / currentDay
                    : 0;

}


private void exportMonthlyReport() {

    java.time.YearMonth selectedMonth =
            java.time.YearMonth.now();

    List<Expense> expenses =
            expenseRepository.getAllExpenses();

    javax.swing.JFileChooser fileChooser =
            new javax.swing.JFileChooser();

    fileChooser.setDialogTitle(
            "Save Monthly Expense Report"
    );

    fileChooser.setSelectedFile(
            new java.io.File(
                    "Expense_Report_"
                    + selectedMonth
                    + ".csv"
            )
    );

    int result = fileChooser.showSaveDialog(this);

    if (result != javax.swing.JFileChooser.APPROVE_OPTION) {
        return;
    }

    java.io.File selectedFile =
            fileChooser.getSelectedFile();

    String filePath = selectedFile.getAbsolutePath();

    if (!filePath.toLowerCase().endsWith(".csv")) {
        filePath += ".csv";
    }

    java.nio.file.Path path =
            java.nio.file.Paths.get(filePath);

    if (java.nio.file.Files.exists(path)) {

        int confirm = javax.swing.JOptionPane.showConfirmDialog(
                this,
                "This file already exists. Replace it?",
                "Confirm Overwrite",
                javax.swing.JOptionPane.YES_NO_OPTION
        );

        if (confirm != javax.swing.JOptionPane.YES_OPTION) {
            return;
        }
    }

    try {

        ExpenseReportService reportService =
                new ExpenseReportService();

        reportService.exportMonthlyReport(
                expenses,
                selectedMonth,
                path
        );

        javax.swing.JOptionPane.showMessageDialog(
                this,
                "Monthly report exported successfully!\n\n"
                + "Month: " + selectedMonth + "\n"
                + "File: " + path.toAbsolutePath(),
                "Export Successful",
                javax.swing.JOptionPane.INFORMATION_MESSAGE
        );

    } catch (java.io.IOException ex) {

        javax.swing.JOptionPane.showMessageDialog(
                this,
                "Could not export report:\n"
                + ex.getMessage(),
                "Export Error",
                javax.swing.JOptionPane.ERROR_MESSAGE
        );
    }
}

private void setSavingsGoal() {

    String goalName =
            JOptionPane.showInputDialog(
                    this,
                    "Enter your savings goal:"
            );

    if (goalName == null ||
            goalName.trim().isEmpty()) {

        return;
    }

    String targetInput =
            JOptionPane.showInputDialog(
                    this,
                    "Enter target amount:"
            );

    if (targetInput == null) {
        return;
    }

    String savedInput =
            JOptionPane.showInputDialog(
                    this,
                    "Enter amount already saved:"
            );

    if (savedInput == null) {
        return;
    }

    try {

        double targetAmount =
                Double.parseDouble(
                        targetInput
                );

        double savedAmount =
                Double.parseDouble(
                        savedInput
                );

        if (targetAmount <= 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Target amount must be greater than zero.",
                    "Invalid Amount",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        if (savedAmount < 0 ||
                savedAmount > targetAmount) {

            JOptionPane.showMessageDialog(
                    this,
                    "Saved amount must be between ₹0 and the target amount.",
                    "Invalid Amount",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        SavingsGoal goal =
                new SavingsGoal(
                        goalName.trim(),
                        targetAmount,
                        savedAmount
                );

        savingsGoalRepository.saveGoal(goal);

        double remaining =
                savingsGoalService
                        .calculateRemainingAmount(goal);

        double progress =
                savingsGoalService
                        .calculateProgressPercentage(goal);

        JOptionPane.showMessageDialog(
                this,
                "🎯 SAVINGS GOAL CREATED\n\n"
                + "Goal: "
                + goal.getGoalName()
                + "\n\n"
                + "Target: ₹"
                + String.format(
                        "%.2f",
                        targetAmount
                )
                + "\n\n"
                + "Saved: ₹"
                + String.format(
                        "%.2f",
                        savedAmount
                )
                + "\n\n"
                + "Remaining: ₹"
                + String.format(
                        "%.2f",
                        remaining
                )
                + "\n\n"
                + "Progress: "
                + String.format(
                        "%.2f",
                        progress
                )
                + "%",
                "Savings Goal",
                JOptionPane.INFORMATION_MESSAGE
        );

    } catch (NumberFormatException e) {

        JOptionPane.showMessageDialog(
                this,
                "Please enter valid numeric amounts.",
                "Invalid Input",
                JOptionPane.ERROR_MESSAGE
        );
    }
}
}