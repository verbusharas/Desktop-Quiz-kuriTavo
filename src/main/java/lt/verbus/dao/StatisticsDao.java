package lt.verbus.dao;

import lt.verbus.domain.entity.Answer;

public class StatisticsDao extends UserDao {

    public Double getOverallAvgAnswerValue() {
        CriteriaCustomizer<Double> criteriaCustomizer =
                (criteria) -> {
                    criteria.select(builder.avg(sourceRoot.get("answer")));
                    return criteria;
                };
        return super.createCustomQuery(criteriaCustomizer, Double.class, Answer.class).getSingleResult();
    }

    public Double getAvgSingleAnswerValueByQuestionIndex(int questionIndex) {
        CriteriaCustomizer<Double> criteriaCustomizer =
                (criteria) -> {
                    criteria.select(builder.avg(sourceRoot.get("answer"))).where(
                            builder.equal(sourceRoot.get("questionIndex"), questionIndex)
                    );
                    return criteria;
                };
        return super.createCustomQuery(criteriaCustomizer, Double.class, Answer.class).getSingleResult();
    }

    public Double getUserAverageAnswerValue(int userId) {
        CriteriaCustomizer<Double> criteriaCustomizer =
                (criteria) -> {
                    criteria.select(builder.avg(sourceRoot.get("answer"))).where(
                            builder.equal(sourceRoot.get("user"), userId)
                    );
                    return criteria;
                };
        return super.createCustomQuery(criteriaCustomizer, Double.class, Answer.class).getSingleResult();
    }

}