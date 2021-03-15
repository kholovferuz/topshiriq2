package jpatopshiriq.topshiriq2.Task3.Controller;

import jpatopshiriq.topshiriq2.Task3.Entities.Groups;
import jpatopshiriq.topshiriq2.Task3.Entities.Subjects;
import jpatopshiriq.topshiriq2.Task3.Entities.Teachers;
import jpatopshiriq.topshiriq2.Task3.Entities.University;
import jpatopshiriq.topshiriq2.Task3.Payload.TeachersDTO;
import jpatopshiriq.topshiriq2.Task3.Repository.GroupRepository;
import jpatopshiriq.topshiriq2.Task3.Repository.SubjectRepository;
import jpatopshiriq.topshiriq2.Task3.Repository.TeacherRepository;
import jpatopshiriq.topshiriq2.Task3.Repository.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/teacher")
public class TeacherController {
    @Autowired
    TeacherRepository teacherRepository;
    @Autowired
    UniversityRepository universityRepository;
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    SubjectRepository subjectRepository;

    // READ all the Teachers
    @GetMapping
    public List<Teachers> getAllTeachers() {
        List<Teachers> teacherList = teacherRepository.findAll();
        return teacherList;
    }

    // CREATE new teacher
    @PostMapping
    public String addTeacher(@RequestBody TeachersDTO teacherDTO) {
        Teachers teacher = new Teachers();
        teacher.setFirstName(teacherDTO.getFirstName());
        teacher.setLastName(teacherDTO.getLastName());
        teacher.setGender(teacherDTO.getGender());

        // call university
        Optional<University> optionalUniversity = universityRepository.findById(teacherDTO.getUniversityId());
        if (optionalUniversity.isPresent()) {
            teacher.setUniversity(optionalUniversity.get());
        } else {
            return "University not found";
        }

        // call group
        List<Groups> groups = groupRepository.findAllById(teacherDTO.getGroupsId());
        teacher.setGroups(groups);

        // call subjects
        List<Subjects> subjects = subjectRepository.findAllById(teacherDTO.getSubjectId());
        teacher.setSubjects(subjects);

        teacherRepository.save(teacher);
        return "Teacher added";
    }

    // UPDATE teacher
    @PutMapping("/{id}")
    public String updateTeacher(@PathVariable Integer id, @RequestBody TeachersDTO teacherDTO) {
        Optional<Teachers> optionalTeacher = teacherRepository.findById(id);

        boolean update = false;
        if (optionalTeacher.isPresent()) {
            Teachers teacher = optionalTeacher.get();
            // teacher
            teacher.setFirstName(teacherDTO.getFirstName());
            teacher.setLastName(teacherDTO.getLastName());
            teacher.setGender(teacherDTO.getGender());

            // university
            Optional<University> optionalUniversity = universityRepository.findById(teacherDTO.getUniversityId());
            if (optionalUniversity.isPresent()) {
                teacher.setUniversity(optionalUniversity.get());
            } else {
                return "University not found";
            }
            teacherRepository.save(teacher);
            update = true;
        }

        return update ? "Teacher updated" : "Teacher with this id is not found";
    }

    //DELETE student by id
    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable Integer id) {
        List<Teachers> teacherList = teacherRepository.findAll();
        boolean delete = false;
        for (Teachers teacher : teacherList) {
            if (teacher.getId() == id) {
                teacherRepository.deleteById(id);
                delete = true;
                break;
            }
        }
        return delete ? "Teacher deleted" : "Teacher with this id is not found";
    }
}
