package jpatopshiriq.topshiriq2.Task3.Controller;

import jpatopshiriq.topshiriq2.Task3.Entities.Faculty;
import jpatopshiriq.topshiriq2.Task3.Entities.University;
import jpatopshiriq.topshiriq2.Task3.Payload.FacultyDTO;
import jpatopshiriq.topshiriq2.Task3.Repository.FacultyRepository;
import jpatopshiriq.topshiriq2.Task3.Repository.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    @Autowired
    FacultyRepository facultyRepository;
    @Autowired
    UniversityRepository universityRepository;

    // READ all the faculties
    @GetMapping
    public List<Faculty> getAllFaculties() {
        List<Faculty> facultyList = facultyRepository.findAll();
        return facultyList;
    }

    // CREATE new faculty
    @PostMapping
    public String addFaculty(@RequestBody FacultyDTO facultyDTO) {
        Faculty faculty = new Faculty();
        faculty.setName(facultyDTO.getName());

        // university
        Optional<University> optionalUniversity = universityRepository.findById(facultyDTO.getUniversityId());
        if (optionalUniversity.isPresent()) {
            faculty.setUniversity(optionalUniversity.get());
        } else {
            return "University with this id is not found";
        }
        facultyRepository.save(faculty);
        return "Faculty added";
    }

    // UPDATE faculty by id
    @PutMapping("/{id}")
    public String updateFaculty(@PathVariable Integer id, @RequestBody FacultyDTO facultyDTO) {
        Optional<Faculty> optionalFaculty = facultyRepository.findById(id);
        if (optionalFaculty.isPresent()) {
            Faculty faculty = optionalFaculty.get();

            // faculty
            faculty.setName(facultyDTO.getName());

            // university
            Optional<University> optionalUniversity = universityRepository.findById(facultyDTO.getUniversityId());
            if (optionalUniversity.isPresent()) {
                faculty.setUniversity(optionalUniversity.get());
            } else {
                return "University with this id is not found";
            }
            facultyRepository.save(faculty);
            return "Faculty updated";
        }
        return "Faculty with this id is not found";
    }

    // DELETE faculty by id
    @DeleteMapping("/{id}")
    public String deleteDistrict(@PathVariable Integer id) {
        List<Faculty> facultyList = facultyRepository.findAll();
        boolean delete = false;
        for (Faculty faculty : facultyList) {
            if (faculty.getId() == id) {
                facultyRepository.deleteById(id);
                delete = true;
                break;
            }
        }
        return delete ? "Faculty deleted" : "Faculty with this id is not found";
    }
}
