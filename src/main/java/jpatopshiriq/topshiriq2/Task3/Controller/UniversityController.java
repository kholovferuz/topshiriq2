package jpatopshiriq.topshiriq2.Task3.Controller;

import jpatopshiriq.topshiriq2.Task2.Entities.Address;
import jpatopshiriq.topshiriq2.Task2.Entities.Continent;
import jpatopshiriq.topshiriq2.Task2.Repository.AddressRepository;
import jpatopshiriq.topshiriq2.Task2.Repository.RegionRepository;
import jpatopshiriq.topshiriq2.Task3.Entities.University;
import jpatopshiriq.topshiriq2.Task3.Payload.UniversityDTO;
import jpatopshiriq.topshiriq2.Task3.Repository.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/university")
public class UniversityController {
    @Autowired
    UniversityRepository universityRepository;
    @Autowired
    AddressRepository addressRepository;


    // READ all universities
    @GetMapping
    public List<University> getAllUniversities() {
        List<University> universityList = universityRepository.findAll();
        return universityList;
    }

    // CREATE new university
    @PostMapping
    public String addUniversities(@RequestBody UniversityDTO universityDTO) {
        University universities = new University();
        universities.setName(universityDTO.getName());

        // address
        Optional<Address> optionalAddress = addressRepository.findById(universityDTO.getAddressId());
        if (optionalAddress.isPresent()){
            universities.setAddress(optionalAddress.get());
        }else {
            return "Address not found";
        }

        universityRepository.save(universities);
        return "University added";
    }

    //UPDATE university by id
    @PutMapping("/{id}")
    public String updateUniversity(@PathVariable Integer id, @RequestBody UniversityDTO universityDTO) {
        Optional<University> optionalUniversity = universityRepository.findById(id);
        boolean update = false;
        if (optionalUniversity.isPresent()) {
            University editedUniversity = optionalUniversity.get();

            // university
            editedUniversity.setName(universityDTO.getName());

            // address
            Optional<Address> optionalAddress = addressRepository.findById(universityDTO.getAddressId());
            if (optionalAddress.isPresent()){
                editedUniversity.setAddress(optionalAddress.get());
            }else {
                return "Address not found";
            }

            universityRepository.save(editedUniversity);
            update = true;
        }
        return update ? "University updated" : "University with this id is not found";
    }

    // DELETE university by id
    @DeleteMapping("/{id}")
    public String deleteUniversity(@PathVariable Integer id) {
        List<University> universityList = universityRepository.findAll();
        boolean delete = false;
        for (University university : universityList) {
            if (university.getId() == id) {
                universityRepository.deleteById(id);
                delete = true;
                break;
            }
        }
        return delete ? "University deleted" : "University with this id is not found";
    }
}
