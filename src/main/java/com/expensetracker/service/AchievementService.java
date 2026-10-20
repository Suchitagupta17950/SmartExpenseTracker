package com.expensetracker.service;

import com.expensetracker.model.Achievement;

import java.util.ArrayList;
import java.util.List;

public class AchievementService {

    private final List<Achievement> achievements;

    public AchievementService() {

        achievements = new ArrayList<>();

        achievements.add(
                new Achievement(
                        "Budget Master",
                        "Stayed within the monthly budget."
                )
        );

        achievements.add(
                new Achievement(
                        "Smart Saver",
                        "Completed a savings goal."
                )
        );

        achievements.add(
                new Achievement(
                        "Healthy Spender",
                        "Maintained a healthy spending score."
                )
        );
    }

    public List<Achievement> getAchievements() {
        return achievements;
    }
}