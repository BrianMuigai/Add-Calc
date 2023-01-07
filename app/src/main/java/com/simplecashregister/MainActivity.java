package com.simplecashregister;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.simplecashregister.customViews.KeyboardView;
import com.simplecashregister.databinding.ActivityMainBinding;
import com.simplecashregister.historyView.HistoryAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private List<String> history;
    private HistoryAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        history = new ArrayList<>();

        handleKeyboard();
        handleHistory();
    }

    @SuppressLint("DefaultLocale")
    private void handleKeyboard() {
        KeyboardView keyboardView = binding.getRoot().findViewById(R.id.numPad);
        TextView total = binding.getRoot().findViewById(R.id.total);
        keyboardView.setAddListener(val -> {
            adapter.add(String.format("%,.2f", Float.parseFloat(val)));
            recyclerView.scrollToPosition(adapter.getItemCount() - 1);
            total.setText(sum(total.getText().toString(), val));
            handleEmptyRecord();
        });
    }

    private void handleHistory() {
        recyclerView = binding.getRoot().findViewById(R.id.history_recycler);
        adapter = new HistoryAdapter(history);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        handleEmptyRecord();
    }

    private void handleEmptyRecord() {
        if (adapter.getItemCount() == 0) {
            binding.getRoot().findViewById(R.id.empty).setVisibility(View.VISIBLE);
            binding.getRoot().findViewById(R.id.totalView).setVisibility(View.GONE);
        } else {
            binding.getRoot().findViewById(R.id.empty).setVisibility(View.GONE);
            binding.getRoot().findViewById(R.id.totalView).setVisibility(View.VISIBLE);
        }
    }

    @SuppressLint("DefaultLocale")
    private String sum(String num1, String num2) {
        float total = Float.parseFloat(num1.isEmpty() ? "0" : num1.replaceAll(",", "")) + Float.parseFloat(num2);
        return String.format("%,.2f", total);
    }


}