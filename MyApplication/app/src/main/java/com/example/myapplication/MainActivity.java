package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button buttonConvert;
    private EditText editTextSumToConvert;
    private TextView textViewCurrencyNameLeft, textViewCurrencyNameRight,
            textViewConvertedCurrencyLeft, textViewConvertedCurrencyRight;
    private final String[] currency = { "Rubles", "Dollars", "Euro"};
    private Spinner spinnerChooseCurrency;
    private Object View;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonConvert = findViewById(R.id.buttonConvert);
        editTextSumToConvert = findViewById(R.id.editTextSumToConvert);
        textViewCurrencyNameLeft = findViewById(R.id.textViewCurrencyNameLeft);
        textViewCurrencyNameRight = findViewById(R.id.textViewCurrencyNameRight);
        textViewConvertedCurrencyLeft = findViewById(R.id.textViewConvertedCurrencyLeft);
        textViewConvertedCurrencyRight = findViewById(R.id.textViewConvertedCurrencyRight);
        spinnerChooseCurrency = findViewById(R.id.spinnerChooseCurrency);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, currency);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerChooseCurrency.setAdapter(adapter);
        spinnerChooseCurrency.setPrompt("Currency");
        spinnerChooseCurrency.setSelection(0);

        editTextSumToConvert.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(android.view.View v, boolean hasFocus) {
                if(hasFocus){
                    editTextSumToConvert.getText().clear();
                }
            }
        });
        editTextSumToConvert.setOnClickListener(v -> {
            editTextSumToConvert.getText().clear();
        });

        buttonConvert.setOnClickListener(v -> {
            if (editTextSumToConvert.getText().toString().length() != 0 &&
                    TextUtils.isDigitsOnly(editTextSumToConvert.getText())) {
                switch (spinnerChooseCurrency.getSelectedItem().toString()){
                    case "Rubles":
                        textViewCurrencyNameLeft.setText("Dollars");
                        textViewCurrencyNameRight.setText("Euro");
                        textViewConvertedCurrencyLeft.setText(String.valueOf(Math.round(Integer.parseInt(String.valueOf(editTextSumToConvert.getText()))*0.013 * 100.0)/100.0));
                        textViewConvertedCurrencyRight.setText(String.valueOf(Math.round(Integer.parseInt(String.valueOf(editTextSumToConvert.getText()))*0.011 * 100.0)/100.0));
                        break;
                    case "Dollars":
                        textViewCurrencyNameLeft.setText("Rubles");
                        textViewCurrencyNameRight.setText("Euro");
                        textViewConvertedCurrencyLeft.setText(String.valueOf(Math.round(Integer.parseInt(String.valueOf(editTextSumToConvert.getText()))*75.28*100.0)/100.0));
                        textViewConvertedCurrencyRight.setText(String.valueOf(Math.round(Integer.parseInt(String.valueOf(editTextSumToConvert.getText()))*0.84*100.0)/100.0));
                        break;
                    case "Euro":
                        textViewCurrencyNameLeft.setText("Rubles");
                        textViewCurrencyNameRight.setText("Dollars");
                        textViewConvertedCurrencyLeft.setText(String.valueOf(Math.round(Integer.parseInt(String.valueOf(editTextSumToConvert.getText()))*89.77*100.0)/100.0));
                        textViewConvertedCurrencyRight.setText(String.valueOf(Math.round(Integer.parseInt(String.valueOf(editTextSumToConvert.getText()))*1.19*100.0)/100.0));
                        break;
                }
            } else {
                Toast.makeText(getApplicationContext(), "Input is incorrect", Toast.LENGTH_SHORT).show();
            }
        });
    }
}