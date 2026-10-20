import com.expensetracker.model.Expense;
import com.expensetracker.model.ExpenseCategory;
import com.expensetracker.model.ExpenseType;
import com.expensetracker.service.SpendingPredictionService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TestSpendingPrediction {

    public static void main(String[] args) {

        List<Expense> expenses =
                new ArrayList<>();

        expenses.add(
                new Expense(
                        1,
                        500,
                        ExpenseCategory.FOOD,
                        ExpenseType.NEED,
                        LocalDate.now(),
                        "Lunch"
                )
        );

        expenses.add(
                new Expense(
                        2,
                        1000,
                        ExpenseCategory.TRAVEL,
                        ExpenseType.NEED,
                        LocalDate.now(),
                        "Cab"
                )
        );

        SpendingPredictionService service =
                new SpendingPredictionService();

        double prediction =
                service.predictMonthEndSpending(
                        expenses
                );

        System.out.println(
                "Predicted Month-End Spending: ₹"
                + String.format("%.2f", prediction)
        );
    }
}