package jpatopshiriq.topshiriq2.Task2.Controller;

import jpatopshiriq.topshiriq2.Task2.Entities.Continent;
import jpatopshiriq.topshiriq2.Task2.Repository.ContinentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/continent")
public class ContinentController {
    @Autowired
    ContinentRepository continentRepository;

    // READ all the continents
    @GetMapping
    public List<Continent> getAllContinents() {
        List<Continent> continentList = continentRepository.findAll();
        return continentList;
    }

    // CREATE new continent
    @PostMapping
    public String addContinent(@RequestBody Continent continent) {
        continentRepository.save(continent);
        return "Continent added";
    }

    // UPDATE continent by id
    @PutMapping("/{id}")
    public String updateContinent(@PathVariable Integer id, @RequestBody Continent continent) {
        Optional<Continent> optionalContinent = continentRepository.findById(id);
        if (optionalContinent.isPresent()) {
            Continent editedContinent = optionalContinent.get();
            editedContinent.setQita(continent.getQita());

            continentRepository.save(editedContinent);
            return "Continent edited";
        } else {
            return "Continent with this id is not found";
        }
    }

    // DELETE continent by id
    @DeleteMapping("/{id}")
    public String deleteContinent(@PathVariable Integer id) {
        List<Continent> continents = continentRepository.findAll();
        boolean delete = false;
        for (Continent continent : continents) {
            if (continent.getId() == id) {
                continentRepository.deleteById(id);
                delete = true;
                break;
            }
        }
        return delete ? "Continent deleted" : "Continent with this id is not found";
    }
}
