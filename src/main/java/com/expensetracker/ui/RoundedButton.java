
package com.expensetracker.ui;

import javax.swing.*;
import java.awt.*;

public class RoundedButton extends JButton {

    private final Color normalColor;

    public RoundedButton(String text, Color color) {
        super(text);
        this.normalColor = color;

        setBackground(color);
        setForeground(Color.WHITE);
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setFont(new Font("Segoe UI", Font.BOLD, 13));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON
        );

        g2.setColor(getModel().isRollover()
                ? new Color(30, 100, 180)
                : normalColor);

        g2.fillRoundRect(
            0, 0, getWidth(), getHeight(), 18, 18
        );

        g2.dispose();
        super.paintComponent(g);
    }
}
