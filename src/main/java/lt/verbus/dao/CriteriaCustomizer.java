package lt.verbus.dao;

import javax.persistence.criteria.CriteriaQuery;

@FunctionalInterface
public interface CriteriaCustomizer<T> {

    CriteriaQuery<T> customize(CriteriaQuery<T> criteria);

}
