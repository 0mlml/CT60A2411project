package dev.mlml.ct60a2411project.ui.minigame.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import dev.mlml.ct60a2411project.data.impl.CityCodesDataFetcher;
import dev.mlml.ct60a2411project.data.impl.CityData;
import dev.mlml.ct60a2411project.data.impl.CityDataFetcher;
import dev.mlml.ct60a2411project.data.impl.WeatherData;
import dev.mlml.ct60a2411project.data.impl.WeatherDataFetcher;
import lombok.SneakyThrows;

public class Quiz {
    private static final Map<String, RunningQuiz> quizCache = new HashMap<>();

    public static RunningQuiz getQuiz(String area) {
        if (quizCache.containsKey(area)) {
            return quizCache.get(area);
        }
        return generateQuiz(area);
    }

    public static void progressQuiz(String area, boolean correct) {
        RunningQuiz quiz = quizCache.get(area);
        if (quiz != null) {
            quiz.progress(correct);
        }
    }

    private static String wiggle(int value) {
        float wiggle = value / 6f;
        return String.format(Locale.getDefault(), "%d", value + (int) (Math.random() * wiggle * 2 - wiggle));
    }

    private static String wiggle(int value, int wiggle) {
        return String.format(Locale.getDefault(), "%d", value + (int) (Math.random() * wiggle * 2 - wiggle));
    }

    private static String wiggle(double value) {
        double wiggle = value / 6f;
        return String.format(Locale.getDefault(), "%.2f", value + (Math.random() * wiggle * 2 - wiggle));
    }

    private static String wiggle(double value, double wiggle) {
        return String.format(Locale.getDefault(), "%.2f", value + (Math.random() * wiggle * 2 - wiggle));
    }

    private static String time(long unix) {
        return String.format(Locale.getDefault(), "%tR", unix * 1000);
    }

    private static Question generate(int value, String question) {
        String value1 = wiggle(value);
        String value2 = wiggle(value);
        String value3 = wiggle(value);
        int iterations = 0;
        while (value1.equals(value2) || value1.equals(value3) || value2.equals(value3)) {
            value1 = wiggle(value, iterations);
            value2 = wiggle(value, iterations);
            value3 = wiggle(value, iterations);
            iterations++;
        }
        return new Question(question, String.valueOf(value), value1, value2, value3);
    }

    private static Question generate(double value, String question) {
        String value1 = wiggle(value);
        String value2 = wiggle(value);
        String value3 = wiggle(value);
        int iterations = 0;
        while (value1.equals(value2) || value1.equals(value3) || value2.equals(value3)) {
            value1 = wiggle(value, iterations);
            value2 = wiggle(value, iterations);
            value3 = wiggle(value, iterations);
            iterations++;
        }
        return new Question(question, String.valueOf(value), value1, value2, value3);
    }

    @SneakyThrows
    private static RunningQuiz generateQuiz(String area) {
        List<Question> questions = new ArrayList<>();
        String name = CityCodesDataFetcher.getRegions().get().get(area);
        CityData cityData = CityDataFetcher.fetchArea(area).get();
        WeatherData weatherData = WeatherDataFetcher.getWeatherData(name).get();
        int pop = cityData.getPopulation().value();
        questions.add(generate(pop, "What is the population of " + name + "?"));
        int births = cityData.getLiveBirths().value();
        questions.add(generate(births, "How many births were there in " + name + "?"));
        int deaths = cityData.getDeaths().value();
        questions.add(generate(deaths, "How many deaths were there in " + name + "?"));
        int imm = cityData.getImmigration().value();
        questions.add(generate(imm, "How many people immigrated to " + name + "?"));
        int emm = cityData.getEmigration().value();
        questions.add(generate(emm, "How many people emigrated from " + name + "?"));
        int mar = cityData.getMarriages().value();
        questions.add(generate(mar, "How many marriages were there in " + name + "?"));
        int div = cityData.getDivorces().value();
        questions.add(generate(div, "How many divorces were there in " + name + "?"));
        int popCh = cityData.getTotalChange().value();
        questions.add(generate(popCh, "What was the population change in " + name + "?"));
        double temp = weatherData.getTemperature();
        questions.add(generate(temp, "What is the temperature in " + name + "?"));
        double feels = weatherData.getFeelsLike();
        questions.add(generate(feels, "What does it feel like in " + name + "?"));
        double hum = weatherData.getHumidity();
        questions.add(generate(hum, "What is the humidity in " + name + "?"));
        double wind = weatherData.getWindSpeed();
        questions.add(generate(wind, "What is the wind speed in " + name + "?"));
        long sunrise = weatherData.getSunrise();
        questions.add(new Question("When does the sun rise in " + name + "?", time(sunrise), time(sunrise - 3600), time(sunrise + 3600), time(sunrise + 7200)));
        long sunset = weatherData.getSunset();
        questions.add(new Question("When does the sun set in " + name + "?", time(sunset), time(sunset - 3600), time(sunset + 3600), time(sunset + 7200)));

        ArrayList<Question> randomizedQuestions = new ArrayList<>(questions);
        Collections.shuffle(randomizedQuestions);

        RunningQuiz quiz = new RunningQuiz(area, randomizedQuestions.subList(0, 10));
        quizCache.put(area, quiz);
        return quiz;
    }

    public record Question(
            String question,
            String correctAnswer,
            String... wrongAnswers) {
    }

    public static class RunningQuiz {
        private final String area;
        private final List<Question> questions;
        private int currentQuestion = 0;
        private int correct = 0;

        public RunningQuiz(String area, List<Question> questions) {
            this.area = area;
            this.questions = questions;
        }

        public Question getCurrentQuestion() {
            if (currentQuestion >= questions.size()) {
                return null;
            }
            return questions.get(currentQuestion);
        }

        public String getScore() {
            return correct + "/" + questions.size();
        }

        public String getProgress() {
            return (currentQuestion + 1) + "/" + questions.size();
        }

        public void progress(boolean correct) {
            if (correct) {
                this.correct++;
            }
            currentQuestion++;
        }
    }
}
