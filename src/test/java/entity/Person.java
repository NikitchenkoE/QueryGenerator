package entity;

import domain.Column;
import domain.Entity;

@Entity(table = "persons")
public class Person {
    @Column
    private int id;
    @Column(name = "person_name")
    private String name;
    @Column
    private double salary;

    public Person() {
    }

    public Person(int id, String name, double salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }


}
