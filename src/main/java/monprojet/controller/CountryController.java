package monprojet.controller;

import javax.naming.Binding;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import lombok.extern.slf4j.Slf4j;
import monprojet.dao.CityRepository;
import monprojet.dao.CountryRepository;
import monprojet.entity.City;
import monprojet.entity.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller // This means that this class is a Controller
@RequestMapping(path = "/cities") // This means URL's start with /country (after Application path)
@Slf4j
public class CountryController {

    private static final String CITIES_VIEW = "cities";
    private static final String EDIT_VIEW = "editCity";
    private static final String CREATE_VIEW = "createCity";

    @Autowired
    private CityRepository cityDao;

    @Autowired
    private CountryRepository countryDao;

    // Print all cities
    @GetMapping()
    public String printAllCities(Model model) {
        model.addAttribute("cities", cityDao.findAll());
        return CITIES_VIEW;
    }

    // Create one city
    @GetMapping("/create")
    public String createCity(City city, Model model) {
        model.addAttribute("countries", countryDao.findAll());
        return CREATE_VIEW;
    }

    @PostMapping("/add")
    public String addUser(
            @RequestParam String name,
            @RequestParam int population,
            @RequestParam int country,
            Model model) {
        
        City city = new City(name, countryDao.findById(country).get());
        city.setPopulation(population);

        cityDao.save(city);
        return "redirect:/" + CITIES_VIEW;
    }

    // Edit one city
    
    @GetMapping(path = "/edit/{id}")
    public String editCity(@PathVariable int id, Model model) {

        model.addAttribute("city", cityDao.findById(id).get());
        model.addAttribute("countries", countryDao.findAll());
        return EDIT_VIEW;
    }

    @PostMapping(path = "/update")
    public String updateCity(
            @RequestParam int id,
            @RequestParam String name,
            @RequestParam int population,
            @RequestParam int country,
            Model model) {

        City city = cityDao.findById(id).get();
        city.setName(name);
        city.setPopulation(population);
        city.setCountry(countryDao.findById(country).get());

        cityDao.save(city);
        return "redirect:/" + CITIES_VIEW;
    }

    // Delete one city
    @GetMapping(path = "delete/{id}")
    public String deleteCity(@PathVariable int id, Model model) {
        cityDao.deleteById(id);
        return "redirect:/" + CITIES_VIEW;
    }

}
