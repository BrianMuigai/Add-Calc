package com.simplecashregister;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.simplecashregister.customViews.KeyboardView;
import com.simplecashregister.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        handleKeyboard();
    }

    private void handleKeyboard() {
        KeyboardView keyboardView = binding.getRoot().findViewById(R.id.numPad);
        keyboardView.setCustomListener(new KeyboardView.KeyboardListener() {
            @Override
            public void onAdd(String val) {
                Log.e("Main Activity", "Add "+val);
            }
        });
    }


}