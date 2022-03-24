package com.gori.primeraaplicacion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.time.Duration;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int[] buttonIdentifiers = {R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6, R.id.button7, R.id.button8,R.id.button9};
    Button[] buttons = new Button[10];
    Button deleteButton, checkButton;
    int rightAnswersCounter = 0, record = 0;
    TextView textViewAnswer, textViewQuestion, textViewCorrectAnswers, textViewRecord;
    String correctAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewAnswer = findViewById(R.id.textViewAnswer);
        textViewQuestion = findViewById(R.id.textViewQuestion);
        textViewCorrectAnswers = findViewById(R.id.textViewCorrectAnswersCounter);
        textViewRecord = findViewById(R.id.textViewRecordCounter);
        checkButton = findViewById(R.id.buttonCheck);
        deleteButton = findViewById(R.id.buttonDelete);

        for (int i = 0; i < buttonIdentifiers.length; i++) {
            buttons[i] = findViewById(buttonIdentifiers[i]);
            buttons[i].setOnClickListener(view -> {
                textViewAnswer.setText(textViewAnswer.getText().toString() + ((Button) view).getText());
            });
        }

        generateQuestion();

        deleteButton.setEnabled(false);
        checkButton.setEnabled(false);

        textViewAnswer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(textViewAnswer.length() > 0){
                    deleteButton.setEnabled(true);
                    checkButton.setEnabled(true);
                } else {
                    deleteButton.setEnabled(false);
                    checkButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        deleteButton.setOnClickListener(view -> {
            textViewAnswer.setText("");
        });

        checkButton.setOnClickListener(view -> {
            if(correctAnswer.equals(textViewAnswer.getText().toString())){
                Toast.makeText(MainActivity.this, "ACIERTO +1", Toast.LENGTH_SHORT).show();
                rightAnswersCounter = rightAnswersCounter + 1;
            } else {
                Toast.makeText(MainActivity.this, "SE TERMINÓ EL JUEGO", Toast.LENGTH_SHORT).show();
                if(rightAnswersCounter > record){
                    record = rightAnswersCounter;
                    textViewRecord.setText("Record: " + record);
                }
                rightAnswersCounter = 0;
            }

            textViewCorrectAnswers.setText("Aciertos: " + rightAnswersCounter);
            textViewAnswer.setText("");
            generateQuestion();
        });
    }

    private void generateQuestion(){
        Random r = new Random();

        int max = 10;

        int a = r.nextInt(max);
        int b = r.nextInt(max);
        int result = 0;

        int randomOperator = r.nextInt(4);
        String operatorValue = null;

        switch (randomOperator) {
            case 0:
                operatorValue = " + ";
                result = a + b;
                break;
            case 1:
                operatorValue = " - ";
                if(b > a){
                    int temp = a;
                    a = b;
                    b = temp;
                }
                result = a - b;
                break;
            case 2:
                operatorValue = " * ";
                result = a * b;
                break;
            case 3:
                operatorValue = " / ";
                if(b == 0){
                    b = 1;
                }
                if(a % b != 0){
                    b = a;
                }
                result = a / b;
                break;
            default:
                operatorValue = null;
                result = 0;
        }

        textViewQuestion.setText(a + operatorValue + b + " = ");
        correctAnswer = String.valueOf(result);
    }
}