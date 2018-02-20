package com.yboberskiy.braintrainer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // String constants

    private final String CORRECT = "Correct!";
    private final String WRONG = "Wrong!";

    private final int maxWrongNumber = 99;
    private final int minWrongNumber = 1;
    private final int maxSummNumber = 49;
    private final int minSummNumber = 1;
    private int clickCounter = 0;
    private int correctAnswers = 0;

    private TextView countdownTimeTV;
    private TextView taskTV;
    private TextView scoreTV;
    private TextView resultTV;

    private Button guessButton_1;
    private Button guessButton_2;
    private Button guessButton_3;
    private Button guessButton_4;

    private Button startButton;

    private int correctNumber;
    private ArrayList<String> numbersOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inflate all required Views

        countdownTimeTV = (TextView) findViewById(R.id.timerTextView);
        taskTV = (TextView) findViewById(R.id.taskTextView);
        scoreTV = (TextView) findViewById(R.id.scoreTextView);
        resultTV = (TextView) findViewById(R.id.resultTextView);

        guessButton_1 = (Button) findViewById(R.id.guessButton_1);
        guessButton_2 = (Button) findViewById(R.id.guessButton_2);
        guessButton_3 = (Button) findViewById(R.id.guessButton_3);
        guessButton_4 = (Button) findViewById(R.id.guessButton_4);

        startButton = (Button) findViewById(R.id.startButton);

        // Set OnClick listeners on All buttons

        guessButton_1.setOnClickListener(this);
        guessButton_2.setOnClickListener(this);
        guessButton_3.setOnClickListener(this);
        guessButton_4.setOnClickListener(this);
        startButton.setOnClickListener(this);

    }

    // Returns list of number order.
    // First two numbers are for task;
    // Third number is a correct answer;
    // All another numbers are wrong answers;

    public void generateListOfNumbers() {
        Random r = new Random();
        numbersOrder = new ArrayList<String>();
        int sum1Number = r.nextInt((maxSummNumber-minSummNumber)+1)+minSummNumber;
        int sum2Number = r.nextInt((maxSummNumber-minSummNumber)+1)+minSummNumber;
        correctNumber = sum1Number + sum2Number;
        numbersOrder.add(Integer.toString(sum1Number));
        numbersOrder.add(Integer.toString(sum2Number));
        numbersOrder.add(Integer.toString(correctNumber));
        for (int i=0; i<3; i++) {
            int wrongRandNum = r.nextInt((maxWrongNumber-minWrongNumber)+1)+minWrongNumber;
            numbersOrder.add(Integer.toString(wrongRandNum));
        }
    }

    public void setupContent() {
        generateListOfNumbers();
        taskTV.setText(numbersOrder.get(0) + " + " + numbersOrder.get(1) + " = ?");

        List<Integer> digits = new ArrayList<Integer>();
        digits.add(2);
        digits.add(3);
        digits.add(4);
        digits.add(5);

        Collections.shuffle(digits);

        guessButton_1.setText(numbersOrder.get(digits.get(0)));
        guessButton_2.setText(numbersOrder.get(digits.get(1)));
        guessButton_3.setText(numbersOrder.get(digits.get(2)));
        guessButton_4.setText(numbersOrder.get(digits.get(3)));
    }

    public void checkAnswer(Button buttonView) {
        clickCounter ++;
        if (buttonView.getText().equals(Integer.toString(correctNumber))) {
            resultTV.setText(CORRECT);
            correctAnswers ++;
            scoreTV.setText(correctAnswers + "/" + clickCounter);
        } else {
            resultTV.setText(WRONG);
            scoreTV.setText(correctAnswers + "/" + clickCounter);
        }
    }

    public void resetCounters() {
        correctAnswers = 0;
        clickCounter = 0;
        scoreTV.setText(correctAnswers + "/" + clickCounter);
        resultTV.setText("");
        setupCoundownTimer();
    }

    public void disableGuessButtons() {
        guessButton_1.setEnabled(false);
        guessButton_2.setEnabled(false);
        guessButton_3.setEnabled(false);
        guessButton_4.setEnabled(false);
    }

    public void enableGuessButtons() {
        guessButton_1.setEnabled(true);
        guessButton_2.setEnabled(true);
        guessButton_3.setEnabled(true);
        guessButton_4.setEnabled(true);
    }

    public void setupCoundownTimer () {
        new CountDownTimer(10000, 1000) {

            public void onTick(long millisUntilFinished) {
                countdownTimeTV.setText(Long.toString(millisUntilFinished/1000));
            }

            public void onFinish() {
                countdownTimeTV.setText("");
                scoreTV.setText("");
                if (correctAnswers == clickCounter) {
                    resultTV.setText("You are the Winner!");
                } else {
                    resultTV.setText("Game Over!");
                }
                taskTV.setText("Your score " + correctAnswers + "/" + clickCounter);
                disableGuessButtons();
                startButton.setVisibility(View.VISIBLE);

            }
        }.start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.guessButton_1:
                checkAnswer((Button) view);
                setupContent();
                break;
            case R.id.guessButton_2:
                checkAnswer((Button) view);
                setupContent();
                break;
            case R.id.guessButton_3:
                checkAnswer((Button) view);
                setupContent();
                break;
            case R.id.guessButton_4:
                checkAnswer((Button) view);
                setupContent();
                break;
            case R.id.startButton:
                setupContent();
                resetCounters();
                enableGuessButtons();
                startButton.setVisibility(View.INVISIBLE);
                break;
        }
    }
}
