package jpatopshiriq.topshiriq2.Task2.Entities;

import jpatopshiriq.topshiriq2.Task3.Entities.University;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String kocha;
    @Column(nullable = false)
    private String uy;
    @ManyToOne(optional = false)
    private District tuman;

}
