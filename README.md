# CT60A2411 Project

## General description of the work

Our app is a Finnish municipalities learning app. It allows users search for municipalities they already know and get statistics and information about them. They can also explore new municipalities according to different atributes such as the highest population and 


## Class diagram

## Division of labour


## Features breakdown
| Feature | Pts. | Explanation |
|---|---|---|
| Object-oriented code | Mandatory | - |
| Code, comments, and related documentation are in English | Mandatory | - |
| App works in Android | Mandatory | - |
| Application includes basic functionality | Mandatory | - |
| Documentation | Mandatory | - |
| You are fetching data via API | Mandatory | `CityCodesDataFetcher` and `CityDataFetcher` implement data fetching from [stat.fi](https://pxdata.stat.fi:443/PxWeb/api/v1/en/StatFin/synt/statfin_synt_pxt_12dy.px) |
| RecyclerView | 3 | `CityDetailsActivity` + `CityDetailAdapter` implement RecyclerView |
| You are displaying images | 2 | Throughout the app the correct Coat of Arms images are displayed for each municipality |
| There is more than one data source | 3 | `WeatherDataFetcher` fetches data from [api.openweathermap.org](https://api.openweathermap.org/) |
| There are more than two data sources | 2 | `CoatOfArmsFetcher` fetches data from [mlml.dev](https://www.mlml.dev/vaakunat/) |
| Statistics | 2 | `Comparator` implements history that is displayed in `CompareActivity` + `ComparisonHistoryAdapter` |
| Comparing municipalities | 3 | `CompareActivity` allows user to compare two municipalities |
| Quiz | 4 | `QuizActivity` allows user to answer questions about municipalities' statistics and weather |
| Game: Flashcards | 4 | `FlashcardsActivity` allows the user to play a matching game with the Coat of Arms images |
| Game: HigherLower | 4 | `HigherLowerActivity` allows the user to play a Higher or Lower game with various statistics of two municipalities |
| Feature: Explore | 3 | `ExploreActivity` allows the user to explore municipalities by sorting by different attributes |

### Targeted Points: 30

