package com.example.quiz;

public class Question {
    private int question_Id;
    private boolean trueAnswer;

    public Question(int question_Id, boolean trueAnswer)
    {
        this.question_Id=question_Id;
        this.trueAnswer=trueAnswer;
    }

    public int getQuestionId() {
        return question_Id;
    }

    public boolean isTrueAnswer() {
        return trueAnswer;
    }
}
