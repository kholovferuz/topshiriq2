package jpatopshiriq.topshiriq2.Task3.Controller;

import jpatopshiriq.topshiriq2.Task3.Entities.Subjects;
import jpatopshiriq.topshiriq2.Task3.Repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/subject")
public class SubjectController {
    @Autowired
    SubjectRepository subjectRepository;

    // READ all subjects
    @GetMapping
    public List<Subjects> getAllSubjects() {
        List<Subjects> subjectList = subjectRepository.findAll();
        return subjectList;
    }

    // CREATE new Subject
    @PostMapping
    public String addSubject(@RequestBody Subjects subjects) {
        subjectRepository.save(subjects);
        return "Subject added";
    }

    //UPDATE subject by id
    @PutMapping("/{id}")
    public String updateSubject(@PathVariable Integer id, @RequestBody Subjects subject) {
        Optional<Subjects> optionalSubject = subjectRepository.findById(id);
        boolean update = false;
        if (optionalSubject.isPresent()) {
            Subjects editedSubject = optionalSubject.get();
            editedSubject.setName(subject.getName());

            subjectRepository.save(editedSubject);
            update = true;
        }
        return update ? "Subject updated" : "Subject with this id is not found";
    }

    // DELETE subject by id
    @DeleteMapping("/{id}")
    public String deleteSubject(@PathVariable Integer id) {
        List<Subjects> subjectList = subjectRepository.findAll();
        boolean delete = false;
        for (Subjects subject : subjectList) {
            if (subject.getId() == id) {
                subjectRepository.deleteById(id);
                delete = true;
                break;
            }
        }
        return delete ? "Subject deleted" : "Subject with this id is not found";
    }
}

