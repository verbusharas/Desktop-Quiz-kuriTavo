package lt.verbus.dao;

import java.util.List;

public interface CrudRepository<T> {

    List<T> findAll();

    T findById(int id);

    void save(T t);

    void delete(T t);
}
