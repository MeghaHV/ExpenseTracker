package com.example.expensetracker;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Expense> expenseList;
    private ExpenseAdapter expenseAdapter;
    private EditText dateEditText;
    private EditText categoryEditText;
    private EditText amountEditText;
    private Button addButton;
    private ListView expenseListView;
    private String filename = "expenses.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        dateEditText = findViewById(R.id.dateEditText);
        categoryEditText = findViewById(R.id.categoryEditText);
        amountEditText = findViewById(R.id.amountEditText);
        addButton = findViewById(R.id.addButton);
        expenseListView = findViewById(R.id.expenseListView);

        // Load expenses from file
        expenseList = loadExpenses();

        // Set up the expense adapter
        expenseAdapter = new ExpenseAdapter(this, expenseList);
        expenseListView.setAdapter(expenseAdapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addExpense();
            }
        });
    }

    private void addExpense() {
        String date = dateEditText.getText().toString();
        String category = categoryEditText.getText().toString();
        String amountString = amountEditText.getText().toString();

        if (date.isEmpty() || category.isEmpty() || amountString.isEmpty()) {
            Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double amount = Double.parseDouble(amountString);
        Expense expense = new Expense(date, category, amount);

        expenseList.add(expense);
        expenseAdapter.notifyDataSetChanged();
        saveExpenses();

        // Clear input fields
        dateEditText.getText().clear();
        categoryEditText.getText().clear();
        amountEditText.getText().clear();

        Toast.makeText(this, "Expense added", Toast.LENGTH_SHORT).show();
    }

    /*private void saveExpenses() {
        try {
            File file = new File(getFilesDir(), filename);
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);

            for (Expense expense : expenseList) {
                bw.write(expense.getDate() + "," + expense.getCategory() + "," + expense.getAmount());
                bw.newLine();
            }

            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to save expenses", Toast.LENGTH_SHORT).show();
        }
    }*/

    private List<Expense> loadExpenses() {
        List<Expense> expenses = new ArrayList<>();

        try {
            File file = new File(getFilesDir(), filename);
            FileReader fr = new FileReader(file.getAbsoluteFile());
            BufferedReader br = new BufferedReader(fr);

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String date = parts[0];
                    String category = parts[1];
                    double amount = Double.parseDouble(parts[2]);
                    Expense expense = new Expense(date, category, amount);
                    expenses.add(expense);
                }
            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to load expenses", Toast.LENGTH_SHORT).show();
        }

        return expenses;
    }
    private void saveExpenses() {
        try {
            File file = new File(getFilesDir(), filename);
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);

            for (Expense expense : expenseList) {
                String expenseLine = expense.getDate() + "," + expense.getCategory() + "," + expense.getAmount();
                bw.write(expenseLine);
                bw.newLine();
            }

            bw.close();

            Toast.makeText(this, "Expenses saved", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to save expenses", Toast.LENGTH_SHORT).show();
        }
    }


}
