![kuriTavo banner](/readme-resources/banner.jpg)
## kuriTavoQuiz

###### Developer: [Šarūnas Verbus](https://www.linkedin.com/in/sarunas-verbus/)    Client: [Codeacademy.lt](https://www.codeacademy.lt/)

### CONTENTS
- INTRODUCTION
- LAUNCHING INSTRUCTIONS
- APPLICATION DESCRIPTION
- EDUCATIONAL GOAL LIST
- ACHIEVEMENTS
- FAILURES


### INTRODUCTION
The application is based on CodeAcademy exercise sample on: https://github.com/pavelvrublevskij/KTCareerQuestion. I chose to recreate the application from scratch rather than refactor the original code. This improved version is a Maven project and uses JavaFX + Hibernate + MySQL instead of original Swing + JDBC + SQLite.

### LAUNCHING INSTRUCTIONS
To launch the app successfully you need to make the following steps:
1. `git clone https://github.com/verbusharas/kuriTavoQuiz.git`
1. Download and add JavaFX library to the project structure: `Project Structure -> Project Settings -> Libraries -> add... -> (path to your JavaFX lib folder)`
1. Add the following line to VM option (Run -> Edit Configurations...): `--module-path "\path\to\javafx-sdk-14\lib" --add-modules javafx.controls,javafx.fxml`
1. Configure `hibernate.cfg.xml` according to your database. I called my database `person_portrait` and used MySQL dialect.
1. The app is all about average statistics, so the database needs to have at least one data entry. You can easily create necessary tables and seed initial entries by executing the provided *.sql file.
1. Run the app

### APPLICATION DESCRIPTION
![kuriTavo banner](/readme-resources/ui-screenshots.jpg)
The application works similarly to the original one, but also has some important algorithmic differences. The application acts in following sequence:
#### 1. Repository validation
When launched it first checks if number of questions in the repository matches the previous statistics. 
If, for example, administrator added a new question to the repository - the pop up informs user about this change and asks either to exit program and fix the questions 
or to override the previous statistics by new data.
#### 2. User identification
User is asked to identify his/her self by filling a simple form. Program requires text fields not to be empty. For testing purposes - an additional button is provided for auto-filling the fields with fake info.
#### 3. Quiz
User is sequentially presented with questions, asking to predict when a certain IT innovation is going to happen. 
To ensure consistent statistics - user is forced to predict using a “year-slider” which ensures proper answer value. 
Default setting is a 110 years span from today. Admin can adjust the `MIN` and `MAX` values through application properties file.
#### 4. Results
There are 3 types of results:
* **Overall *USER-OTHER USERS* comparison**: by comparing average of all user answers to overall average of all previous users.
* **Single *USER-OTHER USERS* comparison**: by comparing each answer separately.
* **Single *USER-PLAUSIBLE ANSWER* comparison**: by comparing each answer of the user to a predefined correct/plausible answer.

Based on the overall comparison, application decides whether the user meets **_PESSIMIST / REALIST / OPTIMIST_** criteria. Each prototype covers similar span of percentage: 
**▼100%-▼34% _PESSIMIST_ | ▼34%-▲34% _REALIST_ | ▲34%-▲100% _OPTIMIST_**

Percentage represents deviation from compared value - the bigger the number the further away from other users current user stands. 
Down arrow ▼ indicates that deviation was to pessimistic side (guessed later year than others), and up arrow ▲ indicates deviation to optimistic side 
(guessed earlier year than others). 

Deviation percentage is calculated with presumption that **100%** means the biggest possible deviation and **0%** means equality. 
For example if average answer was **2030**, but user guessed **2020** - it should mean **100%** optimism (because **2020** was the lowest possible choice), 
but if user guessed **2130** it should mean **100%** pessimism.

### EDUCATIONAL GOAL LIST
Along with the goals stated in the original Readme.md I decided to also use this opportunity for revisiting Java topics that I felt not too strong about:
* Create JavaFX project using proper MVC architecture 
* Learn defining Scenes via FXML files both manually and through SceneBuilder
* Use external, centralized JavaFX CSS styling rather than inline.
* Learn to meaningfully create custom lambdas and functional interfaces.
* Learn to meaningfully use multithreading for performance improvement.
* Learn to meaningfully use JUnit testing.


