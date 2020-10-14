package lt.verbus.dao;

import lt.verbus.domain.entity.User;
import lt.verbus.multithreading.UserSaveThread;
import lt.verbus.util.SessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class UserDao implements CrudRepository<User> {

    protected CriteriaBuilder builder;
    protected Root<User> userRoot;
    protected Root<?> sourceRoot;
    protected Session session;

    @Override
    public List<User> findAll() {
        CriteriaCustomizer<User> criteriaCustomizer =
                (criteria) -> {
                    criteria.select(userRoot);
                    return criteria;
                };
        return createCustomQuery(criteriaCustomizer, User.class, User.class).getResultList();
    }

    @Override
    public User findById(int id) {
        CriteriaCustomizer<User> criteriaCustomizer =
                (criteria) -> {
                    criteria.select(userRoot).where(builder.equal(sourceRoot.get("id"), id));
                    return criteria;
                };
        return createCustomQuery(criteriaCustomizer, User.class, User.class).getSingleResult();
    }

    @Override
    public void save(User user) {
        new UserSaveThread(session, user).start();
    }

    @Override
    public void delete(User user) {
        //TODO: delete user entry;
    }

    /**
     * @param criteriaCustomizer - functional interface for custom criteria definition
     * @param resultClass        - class name of object that query should return
     * @param sourceClass        - class name of object that query parameters should be based on
     * @param <T>                - object type to be constructed from database
     * @param <S>                - object type the query parameters are based on
     */
    protected <T, S> Query<T> createCustomQuery(CriteriaCustomizer<T> criteriaCustomizer,
                                                Class<T> resultClass,
                                                Class<S> sourceClass) {

        session = SessionFactoryUtil.getSession();
        builder = session.getCriteriaBuilder();

        CriteriaQuery<T> criteria = builder.createQuery(resultClass);
        sourceRoot = criteria.from(sourceClass);

        criteriaCustomizer.customize(criteria);
        return session.createQuery(criteria);
    }

    public void deleteAll() {
        session = SessionFactoryUtil.getSession();
        Transaction transaction = null;

        String deleteAllUsersHql = "delete from User";
        Query deleteAllUsersQuery = session.createQuery(deleteAllUsersHql);
        String deleteAllAnswersHql = "delete from Answer";
        Query deleteAllAnswersQuery = session.createQuery(deleteAllAnswersHql);

        try {
            transaction = session.beginTransaction();
            System.out.println(deleteAllUsersQuery.executeUpdate() + " entries deleted.");
            System.out.println(deleteAllAnswersQuery.executeUpdate() + " entries deleted.");
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                e.printStackTrace();
            }
        }
    }
}
