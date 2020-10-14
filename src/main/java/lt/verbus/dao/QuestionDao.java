package lt.verbus.dao;

import java.util.List;

import lt.verbus.domain.model.Question;
import lt.verbus.util.XmlQuestionReader;

public class QuestionDao implements CrudRepository<Question> {

    private final String questionsFileAddress;

    public QuestionDao() {
        this.questionsFileAddress = "src/main/resources/questions/original_questions.xml";
    }

    @Override
    public List<Question> findAll() {
        XmlQuestionReader xmlQuestionReader = new XmlQuestionReader(questionsFileAddress);
        return xmlQuestionReader.getAllQuestions();
    }

    @Override
    public Question findById(int id) {
        return findAll().get(id);
    }

    @Override
    public void save(Question question) {
        //TODO: save new question into XML
    }

    @Override
    public void delete(Question question) {
        //TODO: delete question from XML
    }
}
