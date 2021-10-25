package sql;

import api.QueryGenerator;
import domain.Column;
import domain.Entity;

import java.lang.reflect.Field;
import java.util.StringJoiner;

public class SqlGenerator implements QueryGenerator {
    @Override
    public String findAll(Class<?> clazz) {
        hasEntityAnnotation(clazz);

        StringBuilder sql = new StringBuilder("SELECT ");
        String tableName = tableName(clazz);

        StringJoiner columnNames = new StringJoiner(", ");

        for (Field field : clazz.getDeclaredFields()) {
            Column columnAnnotation = field.getAnnotation(Column.class);

            if (columnAnnotation != null) {
                String columnNameFromAnnotation = columnAnnotation.name();
                String columnName = columnNameFromAnnotation.isEmpty() ? field.getName() : columnNameFromAnnotation;

                columnNames.add(columnName);
            }
        }

        sql.append(columnNames);
        sql.append(" FROM ");
        sql.append(tableName);
        sql.append(";");

        return sql.toString();
    }

    @Override
    public String findById(Object object) throws IllegalAccessException, ClassNotFoundException {
        Class clazz = object.getClass();

        hasEntityAnnotation(clazz);

        String idColumnName = "";
        int id = 0;

        StringBuilder query = new StringBuilder("SELECT ");

        String tableName = tableName(clazz);
        StringJoiner columnNames = new StringJoiner(", ");

        for (Field field : clazz.getDeclaredFields()) {
            Column columnAnnotation = field.getAnnotation(Column.class);
            if (columnAnnotation != null) {
                String columnNameFromAnnotation = columnAnnotation.name();
                String columnName = columnNameFromAnnotation.isEmpty() ? field.getName() : columnNameFromAnnotation;

                columnNames.add(columnName);

                if (field.getName().equals("id")) {
                    field.setAccessible(true);
                    Column column = field.getAnnotation(Column.class);
                    idColumnName = column.name().isEmpty() ? field.getName() : column.name();
                    id = field.getInt(object);
                }
            }
        }

        query.append(columnNames);
        query.append(" FROM ");
        query.append(tableName);
        query.append(" WHERE ");
        query.append(idColumnName);
        query.append(" LIKE ");
        query.append(id);
        query.append(";");

        return query.toString();
    }

    @Override
    public String insert(Object value) throws IllegalAccessException {
        Class clazz = value.getClass();

        hasEntityAnnotation(clazz);

        String tableName = tableName(clazz);

        StringBuilder query = new StringBuilder("INSERT INTO ").append(tableName).append(" VALUES ").append("(");
        StringJoiner columnNames = new StringJoiner(", ");

        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field currentField : declaredFields) {
            currentField.setAccessible(true);
            if (currentField.getType().toString().equals("class java.lang.String")) {
                columnNames.add(String.format("'%s'", currentField.get(value)));
            } else columnNames.add(currentField.get(value).toString());
        }
        query.append(columnNames);
        query.append(");");
        return query.toString();
    }

    @Override
    public String remove(Object id) throws IllegalAccessException {
        Class clazz = id.getClass();

        hasEntityAnnotation(clazz);

        String idColumnName = "";
        int userId = 0;

        String tableName = tableName(clazz);

        StringBuilder query = new StringBuilder("DELETE FROM ").append(tableName).append(" WHERE ");
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            if (declaredField.getName().equals("id")) {
                declaredField.setAccessible(true);
                Column columnName = declaredField.getAnnotation(Column.class);
                idColumnName = columnName.name().isEmpty() ? declaredField.getName() : columnName.name();
                userId = declaredField.getInt(id);
            }
        }
        query.append(idColumnName).append("=").append(userId).append(";");
        return query.toString();
    }

    @Override
    public String update(Object value) throws IllegalAccessException {
        Class clazz = value.getClass();

        String idColumnName = "";
        int userId = 0;

        hasEntityAnnotation(clazz);

        String tableName = tableName(clazz);
        StringJoiner columnNames = new StringJoiner(", ");
        StringBuilder stringBuilder = new StringBuilder("UPDATE ").append(tableName).append(" SET ");

        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            if (declaredField.getType().toString().equals("class java.lang.String")) {
                Column column = declaredField.getAnnotation(Column.class);
                String columnName = column.name().isEmpty() ? declaredField.getName() : column.name();
                columnNames.add(String.format("%s = '%s'", columnName, declaredField.get(value)));
            } else {
                Column column = declaredField.getAnnotation(Column.class);
                String columnName = column.name().isEmpty() ? declaredField.getName() : column.name();
                columnNames.add(String.format("%s = %s", columnName, declaredField.get(value)));

                if (declaredField.getName().equals("id")) {
                    declaredField.setAccessible(true);
                    Column annotationColumnName = declaredField.getAnnotation(Column.class);
                    idColumnName = annotationColumnName.name().isEmpty() ? declaredField.getName() : annotationColumnName.name();
                    userId = declaredField.getInt(value);
                }
            }
        }
        stringBuilder.append(columnNames).append(" WHERE ").append(idColumnName).append(" = ").append(userId);
        return stringBuilder.toString();
    }

    private void hasEntityAnnotation(Class clazz) {
        if (!clazz.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("Annotation @Entity should be present");
        }
    }

    private String tableName(Class clazz) {
        Entity entityAnnotation = (Entity) clazz.getAnnotation(Entity.class);
        String tableName = entityAnnotation.table().isEmpty() ? clazz.getName() : entityAnnotation.table();
        return tableName;
    }
}
