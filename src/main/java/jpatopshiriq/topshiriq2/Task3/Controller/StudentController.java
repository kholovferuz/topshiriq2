package jpatopshiriq.topshiriq2.Task3.Controller;

import jpatopshiriq.topshiriq2.Task3.Entities.Groups;
import jpatopshiriq.topshiriq2.Task3.Entities.Students;
import jpatopshiriq.topshiriq2.Task3.Entities.Subjects;
import jpatopshiriq.topshiriq2.Task3.Payload.StudentsDTO;
import jpatopshiriq.topshiriq2.Task3.Repository.GroupRepository;
import jpatopshiriq.topshiriq2.Task3.Repository.StudentRepository;
import jpatopshiriq.topshiriq2.Task3.Repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    SubjectRepository subjectRepository;

    // READ all the students
    @GetMapping
    public List<Students> getAllStudents() {
        List<Students> studentList = studentRepository.findAll();
        return studentList;
    }

    // CREATE new student
    @PostMapping
    public String addStudent(@RequestBody StudentsDTO studentDTO) {
        Students student = new Students();
        student.setFirstName(studentDTO.getFirstName());
        student.setLastName(studentDTO.getLastName());
        student.setAge(studentDTO.getAge());

        // call group
        Optional<Groups> optionalGroups = groupRepository.findById(studentDTO.getGroupId());
        if (optionalGroups.isPresent()) {
            student.setGroups(optionalGroups.get());
        } else {
            return "Group not found";
        }

        // call subject
        List<Subjects> subjects = subjectRepository.findAllById(studentDTO.getSubjectId());
        student.setSubjects(subjects);

        studentRepository.save(student);
        return "Student added";

    }

    // UPDATE student
    @PutMapping("/{id}")
    public String updateStudent(@PathVariable Integer id, @RequestBody StudentsDTO studentDTO) {
        Optional<Students> optionalStudent = studentRepository.findById(id);

        boolean update = false;
        if (optionalStudent.isPresent()) {
            Students student = optionalStudent.get();
            // update student
            student.setFirstName(studentDTO.getFirstName());
            student.setLastName(studentDTO.getLastName());
            student.setAge(studentDTO.getAge());


            // update group
            Optional<Groups> optionalGroups = groupRepository.findById(studentDTO.getGroupId());
            if (optionalGroups.isPresent()) {
                student.setGroups(optionalGroups.get());

            } else {
                return "Group not found";
            }

            studentRepository.save(student);
            update=true;
        }
        return update?"Student updated":"Student with this id is not found";
    }

    //DELETE student by id
    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable Integer id) {
        List<Students> studentList = studentRepository.findAll();
        boolean delete = false;
        for (Students students : studentList) {
            if (students.getId() == id) {
                studentRepository.deleteById(id);
                delete = true;
                break;
            }
        }
        return delete ? "Student deleted" : "Student with this id is not found";
    }

    // Read students for Ministry
    @GetMapping("/forMinistry")
    public Page<Students> getStudentsForMinistry(@RequestParam int page){
        Pageable pageable= PageRequest.of(page,10);
        Page<Students> studentsPage = studentRepository.findAll(pageable);
        return studentsPage;
    }
}

