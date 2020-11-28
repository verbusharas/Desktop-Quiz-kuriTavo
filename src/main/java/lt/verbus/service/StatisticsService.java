package lt.verbus.service;

import lt.verbus.dao.StatisticsDao;
import lt.verbus.domain.entity.Answer;
import lt.verbus.domain.entity.User;
import lt.verbus.domain.model.Question;
import lt.verbus.enums.PersonType;
import lt.verbus.util.PropertiesReader;

public class StatisticsService {

    private final StatisticsDao statisticsDao;
    private final QuestionService questionService;

    private final int MAX_YEAR;
    private final int MIN_YEAR;

    public StatisticsService() {
        questionService = new QuestionService();
        statisticsDao = new StatisticsDao();
        PropertiesReader properties = new PropertiesReader();
        MAX_YEAR = properties.getMaxYear();
        MIN_YEAR = properties.getMinyear();
    }

    public StatisticsService(StatisticsDao statisticsDao, QuestionService questionService) {
        this.statisticsDao = statisticsDao;
        this.questionService = questionService;
        PropertiesReader properties = new PropertiesReader();
        MAX_YEAR = properties.getMaxYear();
        MIN_YEAR = properties.getMinyear();
    }

    public Double getOverallAvgAnswerValue() {
        return statisticsDao.getOverallAvgAnswerValue();
    }

    public Double getAvgSingleAnswerValueByQuestionIndex(int questionIndex) {
        return statisticsDao.getAvgSingleAnswerValueByQuestionIndex(questionIndex);
    }

    public double compareUserAnswerToSingleAvg(Answer userAnswer) {
        int questionIndex = userAnswer.getQuestionIndex();
        double avgAnswer = getAvgSingleAnswerValueByQuestionIndex(questionIndex);

        return compareAnswerValues(userAnswer.getAnswer(), avgAnswer);
    }

    public double compareUserAnswerToCorrectAnswer(Answer userAnswer) {
        int questionIndex = userAnswer.getQuestionIndex();
        Question question = (questionService.getQuestionByIndex(questionIndex));
        double correctAnswer = Double.parseDouble(question.getCorrectAnswer());

        return compareAnswerValues(userAnswer.getAnswer(), correctAnswer);
    }

    public double compareUserAvgToOverallAvg(User user) {
        double userAvg = calculateUserAvg(user);
        double overallAvg = getOverallAvgAnswerValue();
        return compareAnswerValues(userAvg, overallAvg);
    }

    public double compareUserAvgToCorrectAvg(User user) {
        double userAvg = calculateUserAvg(user);
        double correctAvg = calculateCorrectAvg();
        return compareAnswerValues(userAvg, correctAvg);
    }

    public PersonType calculatePersonType(User user) {
        double ratioOverall = compareUserAvgToOverallAvg(user);
        double ratioCorrect = compareUserAvgToCorrectAvg(user);
        double ratioCombined = (ratioOverall+ratioCorrect)/2;
        if (ratioCombined < -0.34) {
            return PersonType.OPTIMIST;
        }
        if (ratioCombined > 0.34) {
            return PersonType.PESSIMIST;
        } else {
            return PersonType.REALIST;
        }
    }

    private double calculateCorrectAvg() {
        double correctAnswerSum = questionService
                .findAll()
                .stream()
                .mapToInt(question ->
                        Integer.parseInt(question.getCorrectAnswer())).sum();
        double correctAnswerEntries = questionService.findAll().size();
        return correctAnswerSum / correctAnswerEntries;
    }

    private double calculateUserAvg(User user) {
        double userAnswerSum = user.getAnswers().stream().mapToInt(Answer::getAnswer).sum();
        double userAnswerEntries = user.getAnswers().size();
        return userAnswerSum / userAnswerEntries;
    }

    private double compareAnswerValues(double userAnswerValue, double comparedAnswerValue) {
        double actualDif = userAnswerValue - comparedAnswerValue;
        double maxDif = getMaxPossibleDif(comparedAnswerValue, actualDif);

        return actualDif / maxDif;
    }

    private double getMaxPossibleDif(double differenceFrom, double actualDif) {
        if (actualDif < 0) {
            return differenceFrom - (double)MIN_YEAR;
        } else return (double)MAX_YEAR - differenceFrom;
    }

}