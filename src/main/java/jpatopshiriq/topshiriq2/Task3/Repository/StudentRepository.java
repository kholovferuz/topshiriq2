package jpatopshiriq.topshiriq2.Task3.Repository;

import jpatopshiriq.topshiriq2.Task3.Entities.Students;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Students, Integer> {
    Page<Students> findAllByGroup_Faculty_UniversityId(Integer group_faculty_university_id, Pageable pageable);

    Page<Students> findAllByGroup_FacultyId(Integer group_faculty_id, Pageable pageable);

    Page<Students> findAllByGroupId(Integer group_id, Pageable pageable);
}
