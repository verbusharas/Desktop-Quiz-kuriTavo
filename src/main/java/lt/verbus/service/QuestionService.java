package lt.verbus.service;

import lt.verbus.domain.model.Question;
import lt.verbus.dao.QuestionDao;

import java.util.List;

public class QuestionService {

    private final QuestionDao questionRepository;

    public QuestionService() {
        this.questionRepository = new QuestionDao();
    }

    public Question getQuestionByIndex(int questionIndex) {
        return questionRepository.findAll().get(questionIndex);
    }

    public List<Question> findAll(){
        return questionRepository.findAll();
    }

}
