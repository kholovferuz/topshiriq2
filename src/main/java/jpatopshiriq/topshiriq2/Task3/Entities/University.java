package jpatopshiriq.topshiriq2.Task3.Entities;

import jpatopshiriq.topshiriq2.Task2.Entities.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class University {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String name;

    @OneToOne(optional = false)
    private Address address;
}
