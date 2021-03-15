package jpatopshiriq.topshiriq2.Task2.Controller;

import jpatopshiriq.topshiriq2.Task2.Entities.Address;
import jpatopshiriq.topshiriq2.Task2.Entities.District;
import jpatopshiriq.topshiriq2.Task2.Payload.AddressDTO;
import jpatopshiriq.topshiriq2.Task2.Repository.AddressRepository;
import jpatopshiriq.topshiriq2.Task2.Repository.DistrictRepository;
import jpatopshiriq.topshiriq2.Task3.Entities.University;
import jpatopshiriq.topshiriq2.Task3.Repository.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/address")
public class AddressController {
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    DistrictRepository districtRepository;
    @Autowired
    UniversityRepository universityRepository;

    // READ all the addresses
    @GetMapping
    public List<Address> getAllAddresses() {
        List<Address> addressList = addressRepository.findAll();
        return addressList;
    }

    // CREATE new address
    @PostMapping
    public String addAddress(@RequestBody AddressDTO addressDTO) {
        Address address = new Address();
        address.setKocha(addressDTO.getKocha());
        address.setUy(addressDTO.getUy());

        // district
        Optional<District> optionalDistrict = districtRepository.findById(addressDTO.getTumanId());
        if (optionalDistrict.isPresent()) {
            address.setTuman(optionalDistrict.get());
        } else {
            return "District with this id is not found";
        }



        addressRepository.save(address);
        return "Address added";
    }

    // UPDATE address by id
    @PutMapping("/{id}")
    public String updateAddress(@PathVariable Integer id, @RequestBody AddressDTO addressDTO) {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        if (optionalAddress.isPresent()) {
            Address address = optionalAddress.get();

            // address
            address.setKocha(addressDTO.getKocha());
            address.setUy(addressDTO.getUy());

            // district
            Optional<District> optionalDistrict = districtRepository.findById(addressDTO.getTumanId());
            if (optionalDistrict.isPresent()) {
                address.setTuman(optionalDistrict.get());
            } else {
                return "District with this id is not found";
            }

        }
        return "Address with this id is not found";
    }

    // DELETE address by id
    @DeleteMapping("/{id}")
    public String deleteAddress(@PathVariable Integer id) {
        List<Address> addressList = addressRepository.findAll();
        boolean delete = false;
        for (Address address : addressList) {
            if (address.getId() == id) {
                addressRepository.deleteById(id);
                delete = true;
                break;
            }
        }
        return delete ? "Address deleted" : "Address with this id is not found";
    }
}
