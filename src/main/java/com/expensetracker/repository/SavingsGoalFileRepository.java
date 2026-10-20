package com.expensetracker.repository;

import com.expensetracker.model.SavingsGoal;

import java.io.*;

public class SavingsGoalFileRepository
        implements SavingsGoalRepository {

    private final String filePath =
            "data/savings_goal.txt";

    public SavingsGoalFileRepository() {
        createFileIfNotExists();
    }

    private void createFileIfNotExists() {

        File file = new File(filePath);

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
                    "Unable to create savings goal file."
            );
        }
    }

    @Override
    public void saveGoal(SavingsGoal goal) {

        try (
                BufferedWriter writer =
                        new BufferedWriter(
                                new FileWriter(filePath)
                        )
        ) {

            writer.write(
                    goal.getGoalName()
                            + "|"
                            + goal.getTargetAmount()
                            + "|"
                            + goal.getSavedAmount()
            );

        } catch (IOException e) {

            System.out.println(
                    "Unable to save savings goal."
            );
        }
    }

    @Override
    public SavingsGoal getGoal() {

        try (
                BufferedReader reader =
                        new BufferedReader(
                                new FileReader(filePath)
                        )
        ) {

            String line = reader.readLine();

            if (line == null ||
                    line.trim().isEmpty()) {

                return null;
            }

            String[] data =
                    line.split("\\|", -1);

            return new SavingsGoal(
                    data[0],
                    Double.parseDouble(data[1]),
                    Double.parseDouble(data[2])
            );

        } catch (IOException |
                 NumberFormatException e) {

            System.out.println(
                    "Unable to read savings goal."
            );

            return null;
        }
    }
}