package com.example.espressoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button button;
    EditText editText;
    TextView textView;

    Button homeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();
    }

    private void bindViews() {
        button = findViewById(R.id.button);
        button.setOnClickListener(v -> updateText());
        editText = findViewById(R.id.editText);
        textView = findViewById(R.id.textView);

        homeButton = findViewById(R.id.home_button);
        homeButton.setOnClickListener(v -> navigateToHome());
    }

    private void updateText() {
        String text = editText.getText().toString();
        textView.setText(text);
    }

    private void navigateToHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}