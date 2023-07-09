package com.example.expensetracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.expensetracker.R;
import java.util.List;

public class ExpenseAdapter extends BaseAdapter {
    private Context context;
    private List<Expense> expenseList;

    public ExpenseAdapter(Context context, List<Expense> expenseList) {
        this.context = context;
        this.expenseList = expenseList;
    }

    @Override
    public int getCount() {
        return expenseList.size();
    }

    @Override
    public Object getItem(int position) {
        return expenseList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.expense_item, parent, false);
        }

        Expense expense = expenseList.get(position);

        TextView dateTextView = convertView.findViewById(R.id.dateTextView);
        TextView categoryTextView = convertView.findViewById(R.id.categoryTextView);
        TextView amountTextView = convertView.findViewById(R.id.amountTextView);

        dateTextView.setText(expense.getDate());
        categoryTextView.setText(expense.getCategory());
        amountTextView.setText(String.valueOf(expense.getAmount()));

        return convertView;
    }
}
