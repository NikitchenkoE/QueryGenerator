package api;

public interface QueryGenerator {
    String findAll(Class<?> clazz);

    String findById(Object id, Class<?> clazz);

    String insert(Object value, Class<?> clazz);

    String remove(Object id, Class<?> clazz);

    String update(Object value, Class<?> clazz);
}
