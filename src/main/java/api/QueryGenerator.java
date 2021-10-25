package api;

public interface QueryGenerator {
    String findAll(Class<?> clazz);

    String findById(Object id, Class clazz) throws IllegalAccessException, ClassNotFoundException;

    String insert(Object value, Class clazz) throws IllegalAccessException;

    String remove(Object id, Class claz) throws IllegalAccessException;

    String update(Object value, Class clazz) throws IllegalAccessException;
}
