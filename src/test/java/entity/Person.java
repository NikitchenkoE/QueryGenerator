package entity;

import domain.Column;
import domain.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(table = "persons")
public class Person {
    @Column
    private int id;
    @Column(name = "person_name")
    private String name;
    @Column
    private double salary;
}
