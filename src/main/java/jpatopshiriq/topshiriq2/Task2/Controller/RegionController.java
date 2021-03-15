package jpatopshiriq.topshiriq2.Task2.Controller;

import jpatopshiriq.topshiriq2.Task2.Entities.Country;
import jpatopshiriq.topshiriq2.Task2.Entities.Region;
import jpatopshiriq.topshiriq2.Task2.Payload.RegionDTO;
import jpatopshiriq.topshiriq2.Task2.Repository.CountryRepository;
import jpatopshiriq.topshiriq2.Task2.Repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/region")
public class RegionController {
    @Autowired
    RegionRepository regionRepository;
    @Autowired
    CountryRepository countryRepository;

    // READ all the regions
    @GetMapping
    public List<Region> getAllRegions() {
        List<Region> regionList = regionRepository.findAll();
        return regionList;
    }

    // CREATE new region
    @PostMapping
    public String addRegion(@RequestBody RegionDTO regionDTO) {
        Region region = new Region();
        region.setViloyat(regionDTO.getViloyat());

        // country
        Optional<Country> optionalCountry = countryRepository.findById(regionDTO.getCountryId());
        if (optionalCountry.isPresent()) {
            region.setName(optionalCountry.get());
        } else {
            return "Country with this id is not found";
        }
        regionRepository.save(region);
        return "Region added";
    }

    // UPDATE region
    @PutMapping("/{id}")
    public String updateRegion(@PathVariable Integer id, @RequestBody RegionDTO regionDTO) {
        Optional<Region> optionalRegion = regionRepository.findById(id);
        if (optionalRegion.isPresent()) {
            Region region = optionalRegion.get();

            // region
            region.setViloyat(regionDTO.getViloyat());

            // country
            Optional<Country> optionalCountry = countryRepository.findById(regionDTO.getCountryId());
            if (optionalCountry.isPresent()) {
                region.setName(optionalCountry.get());
            } else {
                return "Country with this id is not found";
            }

            regionRepository.save(region);
            return "Region updated";
        }
        return "Region with this id is not found";
    }

    // DELETE country by id
    @DeleteMapping("/{id}")
    public String deleteRegion(@PathVariable Integer id) {
        List<Region> regionList = regionRepository.findAll();
        boolean delete = false;
        for (Region region : regionList) {
            if (region.getId() == id) {
                regionRepository.deleteById(id);
                delete = true;
                break;
            }
        }
        return delete ? "Region deleted" : "Region with this id is not found";
    }
}
