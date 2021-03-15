package jpatopshiriq.topshiriq2.Task3.Repository;

import jpatopshiriq.topshiriq2.Task3.Entities.Students;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Students, Integer> {
}
