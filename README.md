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

![kuriTavo banner](/readme-resources/statistics-screenshot.jpg)

Percentage represents deviation from compared value - the bigger the number the further away from other users current user stands. 
Down arrow ▼ indicates that deviation was to pessimistic side (guessed later year than others), and up arrow ▲ indicates deviation to optimistic side 
(guessed earlier year than others). 

Deviation percentage is calculated with presumption that **100%** means the biggest possible deviation and **0%** means equality. 
For example if average answer was **2030**, but user guessed **2020** - it should mean **100%** optimism (because **2020** was the lowest possible choice), 
but if user guessed **2130** it should mean **100%** pessimism.

### EDUCATIONAL GOAL LIST
Along with the goals stated in the original Readme.md I decided to also use this opportunity for revisiting Java topics that I felt not too strong about:
- [x] Create JavaFX project using proper MVC architecture 
- [x] Learn defining Scenes via FXML files both manually and through SceneBuilder
- [x] Use external, centralized JavaFX CSS styling rather than inline.
- [x] Learn to meaningfully create custom lambdas and functional interfaces.
- [x] Learn to meaningfully use multithreading for performance improvement.
- [x] Learn to meaningfully use JUnit testing.

### ACHIEVEMENTS
Based on the above stated goals:
1. **MVC.**
The architecture consists of: 
- Model (JPA entities and auxiliary POJOs), 
- View (JavaFX scenes defined in corresponding FXML and CSS files)
- Controller (Scene controllers that have necessary nodes automatically injected from FXML files)
2. **FXML.**
The main Scenes of Stages are connected to FXML files. However, the “anonymous/custom” Scenes act on their own and does not have corresponding FXML or CSS files, because number of such scenes is unknown before runtime - they are created on demand (e.g. number of answer statistics panels depends on the number of questions in the `questions.xml` file)
3. **CSS.**
There are two centralized CSS files: one for main window and the other for pop-up windows. The custom - nested panels use their own - inline styles.
4. **Lambdas.**
Hibernate DAO results lots of repetitive boilerplate code with usually only one custom method call per DAO operation to set the proper persistence criteria. For this reason I saw a good use case for functional programming, so that all different DAO operations would call the same method, but would pass a unique criteria-setting-method as a parameter. 
You can see the implementation of this logic in `UserDao` and `StatisticsDao` classes, and in `CriteriaCustomizer` acting as a functional interface for lambdas. You can also find some brief documentation block in UserDao at `createCustomQuery(..)` method.

5. **Multithreading**
When developing my application I quickly started noticing high latency on certain moments. I discovered that the reason was the hibernate Session opening process. Main Thread waits for the session opening to complete (it takes a few seconds) and only then proceeds. 

**CASE #1**. This problem was most apparent when switching to results screen because to get users average, DAOs started connecting to DB and therefore froze the application till the session finished opening. To speed up the process I decided to branch out session opening process to separate thread and start it whenever the application starts with presumption that opening will be done when user reaches the result screen.

**Case #2**. To check if previous statistics match up with the current amount of questions I needed to ask DB for data beforehand. And if not - inform user with a pop-up. This is also done in separate thread when the application starts.

6. **JUnit testing**
I tried understanding unit testing advantages and applying it to my code. The best use case, in my opinion, had to be statistical calculations, because having redone them plenty of times, I decided that they had the biggest room for error that cannot be caught by compiler or runtime exceptions. I used `Mockito` library for mocking database query results. The unit testing proved itself worth it, because I caught several errors in early stages.


### FAILURES

**Issue #1**: Even though my concurrency solution works most of the time, it does not prevent some marginal cases. For example - if user somehow finishes the quiz in ~2 seconds, the session is not yet open and the program crashes.

**Issue #2**: Initial validation (for no. of questions) also needs to wait for an open session, therefore the warning dialog pops up a few seconds later than the actual application (this is accomplished via WHILE loop checking each 1000ms whether session is already open). It does not have a negative impact on performance, but it feels weird from the UX side.

**Issue #3**: Program has some average-statistics safety mechanism to seed a fake user in case the database is empty (e.g. after wiping out previous results from error pop-up dialog). 
However If database tables are dropped before application launch, the Hibernate validate config prevents the app from creating new tables instead.

**Issue #4**: lots of issues could have been avoided if Session object could be properly synchronized when multiple threads call it simultaneously, but for some reason synchronized(session) { } didn’t help. Maybe because documentation states that Session objects are not thread-safe.
