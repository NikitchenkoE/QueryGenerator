package api;

public interface QueryGenerator {
    String findAll(Class<?> clazz);

    String findById(Object id) throws IllegalAccessException, ClassNotFoundException;

    String insert(Object value) throws IllegalAccessException;

    String remove(Object id) throws IllegalAccessException;

    String update(Object value) throws IllegalAccessException;
}
