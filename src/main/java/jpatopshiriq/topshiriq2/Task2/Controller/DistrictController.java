package jpatopshiriq.topshiriq2.Task2.Controller;

import jpatopshiriq.topshiriq2.Task2.Entities.District;
import jpatopshiriq.topshiriq2.Task2.Entities.Region;
import jpatopshiriq.topshiriq2.Task2.Payload.DistrictDTO;
import jpatopshiriq.topshiriq2.Task2.Repository.DistrictRepository;
import jpatopshiriq.topshiriq2.Task2.Repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/district")
public class DistrictController {
    @Autowired
    DistrictRepository districtRepository;
    @Autowired
    RegionRepository regionRepository;

    // READ all the districts
    @GetMapping
    public List<District> getAllDistricts() {
        List<District> districtList = districtRepository.findAll();
        return districtList;
    }

    // CREATE new district
    @PostMapping
    public String addDistrict(@RequestBody DistrictDTO districtDTO) {
        District district = new District();
        district.setTuman(districtDTO.getTuman());

        // region
        Optional<Region> optionalRegion = regionRepository.findById(districtDTO.getRegionId());
        if (optionalRegion.isPresent()) {
            district.setName(optionalRegion.get());
        } else {
            return "Region with this id is not found";
        }
        districtRepository.save(district);
        return "District added";
    }

    // UPDATE district by id
    @PutMapping("/{id}")
    public String updateDistrict(@PathVariable Integer id, @RequestBody DistrictDTO districtDTO) {
        Optional<District> optionalDistrict = districtRepository.findById(id);
        if (optionalDistrict.isPresent()) {
            District district = optionalDistrict.get();

            // district
            district.setTuman(districtDTO.getTuman());

            // region
            Optional<Region> optionalRegion = regionRepository.findById(districtDTO.getRegionId());
            if (optionalRegion.isPresent()) {
                district.setName(optionalRegion.get());
            } else {
                return "Region with this id is not found";
            }

            districtRepository.save(district);
            return "District updated";
        }
        return "District with this id is not found";
    }

    // DELETE district by id
    @DeleteMapping("/{id}")
    public String deleteDistrict(@PathVariable Integer id) {
        List<District> districtList = districtRepository.findAll();
        boolean delete = false;
        for (District district : districtList) {
            if (district.getId() == id) {
                districtRepository.deleteById(id);
                delete = true;
                break;
            }
        }
        return delete ? "District deleted" : "District with this id is not found";
    }
}
