package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class PromptActivity extends AppCompatActivity {

    public static String KEY_EXTRA_ANSWER_SHOWN = "com.example.quiz.answerShown";
    private Button showAnswerButton;
    private TextView hintTextView;
    private TextView corecctAnswerTextView;
    private boolean correctAnswer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prompt);

        showAnswerButton = findViewById(R.id.show_answer_button);
        hintTextView = findViewById(R.id.hint_text_view);
        corecctAnswerTextView = findViewById(R.id.corecct_answer_text_view);


        correctAnswer = getIntent().getBooleanExtra(MainActivity.KEY_EXTRA_ANSWER, true);
        showAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              int answer = correctAnswer ? R.string.button_true : R.string.button_false;
              corecctAnswerTextView.setText(answer);
              setAnswerShowResult(true);
            }
        });

    }

    private void setAnswerShowResult(boolean answerWasShown) {
        Intent resultIntent=new Intent();
        resultIntent.putExtra(KEY_EXTRA_ANSWER_SHOWN,answerWasShown);
        setResult(RESULT_OK, resultIntent);
    }
}