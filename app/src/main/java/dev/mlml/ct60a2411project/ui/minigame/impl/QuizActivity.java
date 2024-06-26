package dev.mlml.ct60a2411project.ui.minigame.impl;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Stream;

import dev.mlml.ct60a2411project.R;
import dev.mlml.ct60a2411project.data.impl.CityCodesDataFetcher;
import dev.mlml.ct60a2411project.data.impl.CoatOfArmsFetcher;
import lombok.SneakyThrows;

public class QuizActivity extends AppCompatActivity {
    Quiz.RunningQuiz quiz;
    Quiz.Question latestQuestion;

    @SneakyThrows
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String area = getIntent().getStringExtra("area");
        if (area == null) {
            Set<String> areas = CityCodesDataFetcher.getRegions().get().keySet();
            area = areas.toArray(new String[0])[(int) (Math.random() * areas.size())];
            Log.d("QuizActivity", "No area provided, using " + area + " as start.");
        }

        quiz = Quiz.getQuiz(area);

        Log.d("QuizActivity", "Quiz for " + area + ": " + quiz);

        setContentView(R.layout.activity_quiz);

        String name = CityCodesDataFetcher.getRegions().get().get(area);

        ((ImageView) findViewById(R.id.quizCityCoatOfArms)).setImageBitmap(CoatOfArmsFetcher.fetchCoatOfArms(name).get());
        ((TextView) findViewById(R.id.quizCityNameTextView)).setText(name);

        renderNextQuestion();

        String finalArea = area;
        ((RadioGroup) findViewById(R.id.quizRadioGroup)).setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            boolean correct = radioButton.getText().equals(latestQuestion.correctAnswer());
            quiz.progress(correct);
            if (quiz.getCurrentQuestion() == null) {
                Intent intent = new Intent(this, QuizResultActivity.class);
                intent.putExtra("area", finalArea);
                intent.putExtra("score", quiz.getScore());
                startActivity(intent);
            } else {
                renderNextQuestion();
            }
        });
    }

    private void renderNextQuestion() {
        ((TextView) findViewById(R.id.quizProgressTextView)).setText(quiz.getProgress());
        ((TextView) findViewById(R.id.quizScoreTextView)).setText(quiz.getScore());

        Quiz.Question question = quiz.getCurrentQuestion();
        latestQuestion = question;

        ((TextView) findViewById(R.id.quizQuestionTextView)).setText(question.question());
        RadioGroup radioGroup = findViewById(R.id.quizRadioGroup);
        radioGroup.removeAllViews();
        ArrayList<String> answers = new ArrayList<>(Stream.concat(Stream.of(question.correctAnswer()), Stream.of(question.wrongAnswers())).toList());
        Collections.shuffle(answers);
        for (String answer : answers) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(answer);
            radioGroup.addView(radioButton);
        }
    }
}
