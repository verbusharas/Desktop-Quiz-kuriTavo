package lt.verbus.util;

import lt.verbus.domain.model.Question;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class XmlQuestionReader {

    private final String fileAddress;

    public XmlQuestionReader(String fileAddress) {
        this.fileAddress = fileAddress;
    }

    public List<Question> getAllQuestions(){

        List<Question> questions = new ArrayList<>();
        NodeList allQuestionNodes = getQuestionNodesByTag("question");

        for (int i = 0; i < allQuestionNodes.getLength(); i++) {
            Node questionNode = allQuestionNodes.item(i);
            String questionTextFromXmlNode =
                    getQuestionParameterFromNodeByTag(questionNode, "text");
            String questionAnswerFromXmlNode =
                    getQuestionParameterFromNodeByTag(questionNode, "answer");
            String questionAnswerRangeFromXmlNode =
                    getQuestionParameterFromNodeByTag(questionNode, "answer_range");

            Question question = new Question();
            question.setId(i);
            question.setText(questionTextFromXmlNode);
            question.setCorrectAnswer(questionAnswerFromXmlNode);
            question.setAllowedAnswerDeviationRange(questionAnswerRangeFromXmlNode);

            questions.add(question);
        }
        return questions;
    }

    private String getQuestionParameterFromNodeByTag(Node node, String questionInfoTag) {
        String questionParameter = null;
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element questionElement = (Element) node;
            questionParameter = questionElement
                    //questionInfoTag examples: "text" / "answer" / "answer_range"
                    .getElementsByTagName(questionInfoTag)
                    .item(0)
                    .getTextContent()
                    .trim()
                    //removes unnecessary whitespaces in question "text"
                    .replaceAll("[ ]{2,}", "\n");
        }
        return questionParameter;

    }

    private NodeList getQuestionNodesByTag(String xmlTag) {
        NodeList nodes = null;
        try {
            File file = new File(fileAddress);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();
            nodes = doc.getElementsByTagName(xmlTag);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nodes;
    }

}