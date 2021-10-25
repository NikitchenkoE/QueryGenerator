package sql;

import entity.Person;
import entity.PersonWithoutAnnotation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SqlQueryGeneratorTest {
    @Test
    public void testFindAllReturnValidQuery() {
        SqlGenerator sqlGenerator = new SqlGenerator();
        String expected = "SELECT id, person_name, salary FROM persons;";

        String actual = sqlGenerator.findAll(Person.class);

        assertEquals(expected, actual);
    }

    @Test
    public void testFindByIdReturnValidQuery() throws IllegalAccessException, ClassNotFoundException {
        SqlGenerator sqlGenerator = new SqlGenerator();
        String expected = "SELECT id, person_name, salary FROM persons WHERE id LIKE 225;";
        Person person = new Person();
        person.setId(225);

        String actual = sqlGenerator.findById(person);
        assertEquals(expected, actual);
    }

    @Test
    public void testFindById_Exception() throws ClassNotFoundException, IllegalAccessException {
        SqlGenerator sqlGenerator = new SqlGenerator();
        PersonWithoutAnnotation person = new PersonWithoutAnnotation();
        String expected = "Annotation @Entity should be present";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> sqlGenerator.findById(person));
        String actualMessage = exception.getMessage();

        assertEquals(expected, actualMessage);
    }

    @Test
    public void testInsertReturnValidQuery() throws IllegalAccessException {
        SqlGenerator sqlGenerator = new SqlGenerator();
        Person person = new Person(25,"Jess",2500.0);
        String expected = "INSERT INTO persons VALUES (25, 'Jess', 2500.0);";
        String actual = sqlGenerator.insert(person);
        assertEquals(expected, actual);
    }

    @Test
    public void testInsertException(){
        SqlGenerator sqlGenerator = new SqlGenerator();
        PersonWithoutAnnotation person = new PersonWithoutAnnotation();
        String expected = "Annotation @Entity should be present";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> sqlGenerator.insert(person));
        String actualMessage = exception.getMessage();

        assertEquals(expected, actualMessage);
    }

    @Test
    public void deleteById() throws IllegalAccessException {
        SqlGenerator sqlGenerator = new SqlGenerator();
        Person person = new Person();
        person.setId(25);
        String expected = "DELETE FROM persons WHERE id=25;";
        String actual = sqlGenerator.remove(person);
        assertEquals(expected, actual);
    }

    @Test
    public void testDeleteException(){
        SqlGenerator sqlGenerator = new SqlGenerator();
        PersonWithoutAnnotation person = new PersonWithoutAnnotation();
        String expected = "Annotation @Entity should be present";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> sqlGenerator.remove(person));
        String actualMessage = exception.getMessage();

        assertEquals(expected, actualMessage);
    }

    @Test
    public void updateById() throws IllegalAccessException {
        SqlGenerator sqlGenerator = new SqlGenerator();
        Person person = new Person(58,"Jacl",6500.0);
        String expected = "UPDATE persons SET id = 58, person_name = 'Jacl', salary = 6500.0 WHERE id = 58";
        String actual = sqlGenerator.update(person);
        assertEquals(expected, actual);
    }

}

