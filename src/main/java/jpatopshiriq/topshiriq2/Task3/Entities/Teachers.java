package jpatopshiriq.topshiriq2.Task3.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Teachers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private String gender;
    @ManyToOne(optional = false)
    private University university;
    @ManyToMany
    private List<Subjects> subjects;
    @ManyToMany
    private List<Groups> groups;
}
