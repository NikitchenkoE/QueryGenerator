package api;

public interface QueryGenerator {
    String findAll(Class<?> clazz);

    String findById(Object id, Class<?> clazz);

    String insert(Object value);

    String remove(Object id);

    String update(Object value);
}
