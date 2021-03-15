package jpatopshiriq.topshiriq2.Task2.Controller;

import jpatopshiriq.topshiriq2.Task2.Entities.Continent;
import jpatopshiriq.topshiriq2.Task2.Entities.Country;
import jpatopshiriq.topshiriq2.Task2.Payload.CountryDTO;
import jpatopshiriq.topshiriq2.Task2.Repository.ContinentRepository;
import jpatopshiriq.topshiriq2.Task2.Repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/country")
public class CountryController {
    @Autowired
    CountryRepository countryRepository;
    @Autowired
    ContinentRepository continentRepository;

    // READ all the countries
    @GetMapping
    public List<Country> getAllCountries() {
        List<Country> countryList = countryRepository.findAll();
        return countryList;
    }

    // CREATE new country
    @PostMapping
    public String addCountry(@RequestBody CountryDTO countryDTO) {
        Country country = new Country();
        country.setDavlat(countryDTO.getDavlat());

        // continent
        Optional<Continent> optionalContinent = continentRepository.findById(countryDTO.getContinentId());
        if (optionalContinent.isPresent()) {
            country.setQita(optionalContinent.get());
        } else {
            return "Continent with this id is not found";
        }
        countryRepository.save(country);
        return "Country added";
    }

    // UPDATE country
    @PutMapping("/{id}")
    public String updateCountry(@PathVariable Integer id, @RequestBody CountryDTO countryDTO) {
        Optional<Country> optionalCountry = countryRepository.findById(id);
        if (optionalCountry.isPresent()) {
            Country country = optionalCountry.get();

            // country
            country.setDavlat(countryDTO.getDavlat());

            // continent
            Optional<Continent> optionalContinent = continentRepository.findById(countryDTO.getContinentId());
            if (optionalContinent.isPresent()) {
                country.setQita(optionalContinent.get());
            } else {
                return "Continent with this id is not found";
            }

            countryRepository.save(country);
            return "Country updated";
        }
        return "Country with this id is not found";
    }

    // DELETE country by id
    @DeleteMapping("/{id}")
    public String deleteMapping(@PathVariable Integer id) {
        List<Country> countries = countryRepository.findAll();
        boolean delete = false;
        for (Country country : countries) {
            if (country.getId() == id) {
                countryRepository.deleteById(id);
                delete = true;
                break;
            }
        }
        return delete ? "Country deleted" : "Country with this id is not found";
    }
}
