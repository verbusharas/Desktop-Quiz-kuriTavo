package lt.verbus.service;

import junit.framework.TestCase;
import lt.verbus.dao.StatisticsDao;
import lt.verbus.domain.entity.Answer;
import lt.verbus.domain.entity.User;
import lt.verbus.domain.model.Question;
import lt.verbus.util.PropertiesReader;
import org.junit.Before;
import org.mockito.Mockito;

public class StatisticsServiceTest extends TestCase {

    private StatisticsService statisticsService;
    private final StatisticsDao statisticsDao = Mockito.mock(StatisticsDao.class);
    private final QuestionService questionService = Mockito.mock(QuestionService.class);
    private int MAX_YEAR;
    private int MIN_YEAR;

    @Before
    public void setUp() {
        statisticsService = new StatisticsService(statisticsDao, questionService);
        PropertiesReader properties = new PropertiesReader();
        MAX_YEAR = properties.getMaxYear();
        MIN_YEAR = properties.getMinyear();
    }

    public void testCompareUserAnswerToAverageAnswer() {
        Mockito.when(statisticsDao.getAvgSingleAnswerValueByQuestionIndex(0))
                .thenReturn(2040.0);
        Mockito.when(statisticsDao.getAvgSingleAnswerValueByQuestionIndex(1))
                .thenReturn(2050.0);
        Mockito.when(statisticsDao.getAvgSingleAnswerValueByQuestionIndex(2))
                .thenReturn(2060.0);

        User mockUser = new User();
        for (int i = 0; i <3; i++) {
            Answer answer = new Answer(2030 + (i * 20));
            answer.setQuestionIndex(i);
            mockUser.getAnswers().add(answer);
        }

        double ratioWhenLess = statisticsService.compareUserAnswerToSingleAvg
                (mockUser.getAnswers().get(0));
        double ratioWhenEquals = statisticsService.compareUserAnswerToSingleAvg
                (mockUser.getAnswers().get(1));
        double ratioWhenMore = statisticsService.compareUserAnswerToSingleAvg
                (mockUser.getAnswers().get(2));

        double mockUserAnswer, mockAnswerAvg, mockDif;

        mockUserAnswer = 2030.0;
        mockAnswerAvg = 2040.0;
        mockDif = mockUserAnswer - mockAnswerAvg;
        assertEquals(mockDif / getMaxPossibleDif(mockAnswerAvg, mockDif), ratioWhenLess);

        assertEquals(0.0, ratioWhenEquals);

        mockUserAnswer = 2070.0;
        mockAnswerAvg = 2060.0;
        mockDif = mockUserAnswer - mockAnswerAvg;
        assertEquals(mockDif / getMaxPossibleDif(mockAnswerAvg, mockDif), ratioWhenMore);

        Mockito.verify(statisticsDao).getAvgSingleAnswerValueByQuestionIndex(0);
        Mockito.verify(statisticsDao).getAvgSingleAnswerValueByQuestionIndex(1);
        Mockito.verify(statisticsDao).getAvgSingleAnswerValueByQuestionIndex(2);
    }

    public void testCompareUserAnswerToCorrectAnswer() {
        Question mockedQuestion0 = new Question();
        mockedQuestion0.setCorrectAnswer("2040");
        Question mockedQuestion1 = new Question();
        mockedQuestion1.setCorrectAnswer("2050");
        Question mockedQuestion2 = new Question();
        mockedQuestion2.setCorrectAnswer("2060");

        Mockito.when(questionService.getQuestionByIndex(0))
                .thenReturn(mockedQuestion0);

        Mockito.when(questionService.getQuestionByIndex(1))
                .thenReturn(mockedQuestion1);

        Mockito.when(questionService.getQuestionByIndex(2))
                .thenReturn(mockedQuestion2);


        User mockUser = new User();
        for (int i = 0; i <3; i++) {
            Answer answer = new Answer(2030 + (i * 20));
            answer.setQuestionIndex(i);
            mockUser.getAnswers().add(answer);
        }

        double ratioWhenLess = statisticsService.compareUserAnswerToCorrectAnswer
                (mockUser.getAnswers().get(0));
        double ratioWhenEquals = statisticsService.compareUserAnswerToCorrectAnswer
                (mockUser.getAnswers().get(1));
        double ratioWhenMore = statisticsService.compareUserAnswerToCorrectAnswer
                (mockUser.getAnswers().get(2));

        double mockUserAnswer, mockAnswerAvg, mockDif;

        mockUserAnswer = 2030.0;
        mockAnswerAvg = 2040.0;
        mockDif = mockUserAnswer - mockAnswerAvg;
        assertEquals(mockDif / getMaxPossibleDif(mockAnswerAvg, mockDif), ratioWhenLess);

        assertEquals(0.0, ratioWhenEquals);

        mockUserAnswer = 2070.0;
        mockAnswerAvg = 2060.0;
        mockDif = mockUserAnswer - mockAnswerAvg;
        assertEquals(mockDif / getMaxPossibleDif(mockAnswerAvg, mockDif), ratioWhenMore);

        Mockito.verify(questionService).getQuestionByIndex(0);
        Mockito.verify(questionService).getQuestionByIndex(1);
        Mockito.verify(questionService).getQuestionByIndex(2);
    }

    public void testCompareUserAverageToOverallAverage() {

        Mockito.when(statisticsDao.getOverallAvgAnswerValue())
                .thenReturn(2030.0);

        User mockUserWithSmallerAvg = new User();
        mockUserWithSmallerAvg.getAnswers().add(new Answer(2029));

        User mockUserWithEqualAvg = new User();
        mockUserWithEqualAvg.getAnswers().add(new Answer(2030));

        User mockUserWithlargerAvg = new User();
        mockUserWithlargerAvg.getAnswers().add(new Answer(2031));

        double ratioWhenLess = statisticsService.compareUserAvgToOverallAvg(mockUserWithSmallerAvg);
        double ratioWhenEquals = statisticsService.compareUserAvgToOverallAvg(mockUserWithEqualAvg);
        double ratioWhenMore = statisticsService.compareUserAvgToOverallAvg(mockUserWithlargerAvg);


        double mockDif;
        double mockAvg;
        double mockUserAvg;

        mockAvg = 2030.0;
        mockUserAvg = 2029.0;
        mockDif = mockUserAvg - mockAvg;
        assertEquals(mockDif / getMaxPossibleDif(mockAvg, mockDif), ratioWhenLess);

        assertEquals(0.0, ratioWhenEquals);

        mockAvg = 2030.0;
        mockUserAvg = 2031.0;
        mockDif = mockUserAvg - mockAvg;
        assertEquals(mockDif / getMaxPossibleDif(mockAvg, mockDif), ratioWhenMore);

        Mockito.verify(statisticsDao, Mockito.atLeast(3)).getOverallAvgAnswerValue();
    }

    private double getMaxPossibleDif(double differenceFrom, double actualDif) {
        if (actualDif < 0) {
            return differenceFrom - (double) MIN_YEAR;
        } else return (double) MAX_YEAR - differenceFrom;
    }
}
