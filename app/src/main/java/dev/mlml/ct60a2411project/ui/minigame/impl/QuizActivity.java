package dev.mlml.ct60a2411project.ui.minigame.impl;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import dev.mlml.ct60a2411project.data.impl.CityCodesDataFetcher;
import dev.mlml.ct60a2411project.data.impl.CityData;
import dev.mlml.ct60a2411project.data.impl.CityDataFetcher;
import dev.mlml.ct60a2411project.data.impl.WeatherData;
import dev.mlml.ct60a2411project.data.impl.WeatherDataFetcher;
import lombok.SneakyThrows;

public class QuizActivity extends AppCompatActivity {
    private record Question(
            String question,
            String correctAnswer,
            String... wrongAnswers) {
    }

    private static List<Question> questions = new ArrayList<>();

    private static int wiggle(int value, int wiggle) {
        return value + (int) (Math.random() * wiggle * 2) - wiggle;
    }

    private static String wiggleP(int value, int wiggle) {
        int wiggled = wiggle(value, wiggle);
        while (wiggled < 0) {
            wiggled = wiggle(value, wiggle);
        }
        return String.valueOf(wiggled);
    }

    private static String time(long time) {
        return String.format(Locale.getDefault(), "%02d:%02d", time / 3600, (time % 3600) / 60);
    }

    @SneakyThrows
    private static List<Question> generateQuiz(String area) {
        String name = CityCodesDataFetcher.getRegions().get().get(area);
        CityData cityData = CityDataFetcher.fetchArea(area).get();
        WeatherData weatherData = WeatherDataFetcher.getWeatherData(name).get();
        int pop = cityData.getPopulation().value();
        questions.add(new Question("What is the population of " + name + "?", String.valueOf(pop), wiggleP(pop, 1000), wiggleP(pop, 10000), wiggleP(pop, 100000)));
        int births = cityData.getLiveBirths().value();
        questions.add(new Question("How many live births were there in " + name + "?", String.valueOf(births), wiggleP(births, 100), wiggleP(births, 1000), wiggleP(births, 10000)));
        int deaths = cityData.getDeaths().value();
        questions.add(new Question("How many deaths were there in " + name + "?", String.valueOf(deaths), wiggleP(deaths, 100), wiggleP(deaths, 1000), wiggleP(deaths, 10000)));
        int imm = cityData.getImmigration().value();
        questions.add(new Question("How many people immigrated to " + name + "?", String.valueOf(imm), wiggleP(imm, 100), wiggleP(imm, 1000), wiggleP(imm, 10000)));
        int emm = cityData.getEmigration().value();
        questions.add(new Question("How many people emigrated from " + name + "?", String.valueOf(emm), wiggleP(emm, 100), wiggleP(emm, 1000), wiggleP(emm, 10000)));
        int mar = cityData.getMarriages().value();
        questions.add(new Question("How many marriages were there in " + name + "?", String.valueOf(mar), wiggleP(mar, 100), wiggleP(mar, 1000), wiggleP(mar, 10000)));
        int div = cityData.getDivorces().value();
        questions.add(new Question("How many divorces were there in " + name + "?", String.valueOf(div), wiggleP(div, 100), wiggleP(div, 1000), wiggleP(div, 10000)));
        int popCh = cityData.getTotalChange().value();
        questions.add(new Question("What was the total population change in " + name + "?", String.valueOf(popCh), wiggleP(popCh, 100), wiggleP(popCh, 1000), wiggleP(popCh, 10000)));
        int temp = (int) weatherData.getTemperature();
        questions.add(new Question("What is the temperature in " + name + "?", String.valueOf(temp), wiggleP(temp, 1), wiggleP(temp, 5), wiggleP(temp, 10)));
        int feels = (int) weatherData.getFeelsLike();
        questions.add(new Question("What does it feel like in " + name + "?", String.valueOf(feels), wiggleP(feels, 1), wiggleP(feels, 5), wiggleP(feels, 10)));
        int hum = (int) weatherData.getHumidity();
        questions.add(new Question("What is the humidity in " + name + "?", String.valueOf(hum), wiggleP(hum, 1), wiggleP(hum, 5), wiggleP(hum, 10)));
        int wind = (int) weatherData.getWindSpeed();
        questions.add(new Question("What is the wind speed in " + name + "?", String.valueOf(wind), wiggleP(wind, 1), wiggleP(wind, 5), wiggleP(wind, 10)));
        long sunrise = weatherData.getSunrise();
        questions.add(new Question("When does the sun rise in " + name + "?", time(sunrise), time(sunrise - 3600), time(sunrise + 3600), time(sunrise + 7200)));
        long sunset = weatherData.getSunset();
        questions.add(new Question("When does the sun set in " + name + "?", time(sunset), time(sunset - 3600), time(sunset + 3600), time(sunset + 7200)));

        List<Question> randomizedQuestions = new ArrayList<>(questions);
        for (int i = 0; i < randomizedQuestions.size(); i++) {
            int randomIndex = (int) (Math.random() * randomizedQuestions.size());
            Question tempQuestion = randomizedQuestions.get(i);
            randomizedQuestions.set(i, randomizedQuestions.get(randomIndex));
            randomizedQuestions.set(randomIndex, tempQuestion);
        }

        return randomizedQuestions.subList(0, 10);
    }
}
