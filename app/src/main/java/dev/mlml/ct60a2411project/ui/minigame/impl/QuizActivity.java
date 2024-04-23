package dev.mlml.ct60a2411project.ui.minigame.impl;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import dev.mlml.ct60a2411project.R;
import dev.mlml.ct60a2411project.data.impl.CityCodesDataFetcher;
import dev.mlml.ct60a2411project.data.impl.CityData;
import dev.mlml.ct60a2411project.data.impl.CityDataFetcher;
import dev.mlml.ct60a2411project.data.impl.WeatherData;
import dev.mlml.ct60a2411project.data.impl.WeatherDataFetcher;
import dev.mlml.ct60a2411project.ui.MainActivity;
import dev.mlml.ct60a2411project.ui.compare.Comparator;
import lombok.SneakyThrows;

public class QuizActivity extends AppCompatActivity {
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

        Quiz.RunningQuiz quiz = Quiz.getQuiz(area);

        Log.d("QuizActivity", "Quiz progress: " + quiz.getProgress());

        setContentView(R.layout.activity_quiz);

        String name = CityCodesDataFetcher.getRegions().get().get(area);
        String cityNameNormalized = Normalizer.normalize(name, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").toLowerCase();
        int resId = getResources().getIdentifier(cityNameNormalized, "drawable", getPackageName());
        Log.d("QuizActivity", "Got city name: " + name + ", resId: " + resId);

        ((ImageView) findViewById(R.id.quizCityCoatOfArms)).setImageResource(resId);
        ((TextView) findViewById(R.id.quizCityNameTextView)).setText(name);
        ((TextView) findViewById(R.id.quizProgressTextView)).setText(quiz.getProgress());
        ((TextView) findViewById(R.id.quizScoreTextView)).setText(quiz.getScore());

        Log.d("QuizActivity", "Set text views.");

        Quiz.Question question = quiz.getCurrentQuestion();
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

        Log.d("QuizActivity", "Question: " + question.question());
        Log.d("QuizActivity", "Correct answer: " + question.correctAnswer());

        String finalArea = area;
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            boolean correct = radioButton.getText().equals(question.correctAnswer());
            quiz.progress(correct);
            if (quiz.getCurrentQuestion() == null) {
                Intent intent = new Intent(this, QuizResultActivity.class);
                intent.putExtra("area", finalArea);
                intent.putExtra("score", quiz.getScore());
                startActivity(intent);
            } else {
                Intent intent = new Intent(this, QuizActivity.class);
                intent.putExtra("area", finalArea);
                startActivity(intent);
            }
        });
    }
}
