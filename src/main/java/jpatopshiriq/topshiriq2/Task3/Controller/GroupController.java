package jpatopshiriq.topshiriq2.Task3.Controller;

import jpatopshiriq.topshiriq2.Task3.Entities.Faculty;
import jpatopshiriq.topshiriq2.Task3.Entities.Groups;
import jpatopshiriq.topshiriq2.Task3.Payload.GroupsDTO;
import jpatopshiriq.topshiriq2.Task3.Repository.FacultyRepository;
import jpatopshiriq.topshiriq2.Task3.Repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/group")
public class GroupController {
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    FacultyRepository facultyRepository;


    // READ all groups
    @GetMapping
    public List<Groups> getAllGroups() {
        List<Groups> groupsList = groupRepository.findAll();
        return groupsList;
    }

    // CREATE new group
    @PostMapping
    public String addGroup(@RequestBody GroupsDTO groupDTO) {
        Groups groups = new Groups();
        groups.setName(groupDTO.getName());
        groups.setNumberOfStudents(groupDTO.getNumberOfStudents());

        // faculty
        Optional<Faculty> optionalFaculty = facultyRepository.findById(groupDTO.getFacultyId());
        if (optionalFaculty.isPresent()) {
                groups.setFaculty(optionalFaculty.get());
        }else {
            return "Faculty not found";
        }
        groupRepository.save(groups);
        return "Group added";
    }

    // UPDATE a group
    @PutMapping("/{id}")
    public String updateGroup(@PathVariable Integer id, @RequestBody GroupsDTO groupDTO) {
        Optional<Groups> optionalGroups = groupRepository.findById(id);
        boolean update = false;
        if (optionalGroups.isPresent()) {
            Groups groups = optionalGroups.get();

            // group
            groups.setName(groupDTO.getName());
            groups.setNumberOfStudents(groupDTO.getNumberOfStudents());

            // faculty
            Optional<Faculty> optionalSchool = facultyRepository.findById(groupDTO.getFacultyId());
            if (optionalSchool.isPresent()){
                groups.setFaculty(optionalSchool.get());
            }else {
                return "Faculty not found";
            }

            groupRepository.save(groups);
            update = true;

        }
        return update ? "Group updated" : "Group with this id is not found";
    }

    // DELETE a group by id
    @DeleteMapping("/{id}")
    public String deleteGroup(@PathVariable Integer id){
        List<Groups> groupsList = groupRepository.findAll();
        boolean delete=false;
        for (Groups groups : groupsList) {
            if (groups.getId()==id){
                groupRepository.deleteById(id);
                delete=true;
                break;
            }
        }
        return delete?"Group deleted":"Group with this id is not found";
    }
}
