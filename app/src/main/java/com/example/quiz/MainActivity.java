package com.example.quiz;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String KEY_EXTRA_ANSWER = "com.example.quiz.correctAnswer";
    private static final String KEY_CURRENT_INDEX = "currentIndex";
    private static final String KEY_COUNTER = "counter";
    private static final int REQUEST_CODE_PROMPT = 0;
    private Button trueButton;
    private Button falseButton;
    private Button resetButton;
    private Button hintButton;
    private TextView questionTextView;
    private TextView trueAnswerCounter;
    private int counter=0;
    private int currentIndex =0;
    private final Question[] questions = new Question[]{
            new Question(R.string.q_activity, true),
            new Question(R.string.q_find_resources,  false),
            new Question(R.string.q_listener, true),
            new Question(R.string.q_resources, true),
            new Question(R.string.q_version,  false),
    };
    private boolean answerWasShown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("mojTag","metoda onCreate() została wywołana");

        if(savedInstanceState != null){
            currentIndex =savedInstanceState.getInt(KEY_CURRENT_INDEX);
            counter = savedInstanceState.getInt(KEY_COUNTER);
        }

        setContentView(R.layout.activity_main);
        trueButton= findViewById(R.id.button_true);
        falseButton= findViewById(R.id.button_false);
        resetButton = findViewById(R.id.button_next);
        questionTextView = findViewById(R.id.question_text_view);
        trueAnswerCounter = findViewById(R.id.trueAnswerCounter);
        hintButton = findViewById(R.id.button_hint);

        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                checkAnswerCorrectness(true);
            }
        });
        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                checkAnswerCorrectness(false);
            }
        });
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                answerWasShown = false;
                setQuestionTextView();
            }
        });
        hintButton.setOnClickListener((v)-> {
            Intent intent = new Intent(MainActivity.this, PromptActivity.class);
            boolean correctAnswer = questions[currentIndex].isTrueAnswer();
            intent.putExtra(KEY_EXTRA_ANSWER, correctAnswer);
            startActivityForResult(intent, REQUEST_CODE_PROMPT,null);
        } );
        setQuestionTextView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("mojTag","metoda onStart() została wywołana");
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("mojTag","metoda onResume() została wywołana");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.d("mojTag","metoda onPause() została wywołana");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.d("mojTag","metoda onStop() została wywołana");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("mojTag","metoda onDestroy() została wywołana");
    }
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("mojTag", "metoda onSaveInstanceState() została wywołana");
        outState.putInt(KEY_CURRENT_INDEX,currentIndex);
        outState.putInt(KEY_COUNTER,counter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != RESULT_OK){return;}
        if(requestCode == REQUEST_CODE_PROMPT)
        {
            if(data == null){
                return;
            }
            answerWasShown = data.getBooleanExtra(PromptActivity.KEY_EXTRA_ANSWER_SHOWN,false);
        }
    }

    public void reset(){
        counter=0;
        currentIndex=0;
        setQuestionTextView();
        trueAnswerCounter.setText("");
    }
    public  void setQuestionTextView(){
        questionTextView.setText(questions[currentIndex].getQuestionId());
    }
    public void setNextQuestion(){
        currentIndex = (currentIndex+1)%questions.length;
        questionTextView.setText(questions[currentIndex].getQuestionId());
    }
    public void checkAnswerCorrectness(boolean userAnswer){
        boolean correctAnswer = questions[currentIndex].isTrueAnswer();
        int resultMessageId =0;
        if(answerWasShown) {
            resultMessageId=R.string.answear_was_shown;
        }
        else {
            if (userAnswer == correctAnswer) {
                resultMessageId = R.string.correct_answer;
                counter++;
            } else {
                resultMessageId = R.string.incorrect_answer;
            }
        }
        Toast.makeText(this, resultMessageId, Toast.LENGTH_SHORT).show();
        if(currentIndex==questions.length-1)
        {
            String naps = "liczba poprawnych odpowiedzi: ";
            naps = naps + Integer.toString(counter);
            trueAnswerCounter.setText(naps);
        }
        answerWasShown=false;
        setNextQuestion();
    }
}