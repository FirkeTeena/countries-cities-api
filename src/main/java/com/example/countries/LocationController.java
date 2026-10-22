package com.example.countries;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
public class LocationController {


    private final List<Country> countries = List.of(
            new Country(1, "India"),
            new Country(2, "Luxembourg"),
            new Country(3, "Japan"));

    private final List<City> cities = List.of(
            // India (countryId = 1)
            new City(1, "Mumbai", 1, "Vada Pav"),
            new City(2, "Delhi", 1, "Chole Bhature"),
            new City(3, "Bengaluru", 1, "Masala Dosa"),
            new City(4, "Hyderabad", 1, "Hyderabadi Biryani"),
            new City(5, "Ahmedabad", 1, "Dhokla"),
            new City(6, "Chennai", 1, "Idli Sambar"),
            new City(7, "Kolkata", 1, "Kathi Roll"),
            new City(8, "Pune", 1, "Misal Pav"),
            new City(9, "Jaipur", 1, "Dal Baati Churma"),
            new City(10, "Surat", 1, "Surati Locho"),
            new City(11, "Lucknow", 1, "Galouti Kebab"),
            new City(12, "Nagpur", 1, "Saoji Chicken"),

            // Luxembourg (countryId = 2)
            new City(13, "Luxembourg City", 2, "Judd mat Gaardebounen"),
            new City(14, "Esch-sur-Alzette", 2, "Bouneschlupp"),
            new City(15, "Differdange", 2, "Gromperekichelcher"),
            // Japan (countryId = 3)
            new City(16, "Tokyo", 3, "Sushi"),
            new City(17, "Osaka", 3, "Takoyaki"),
            new City(18, "Kyoto", 3, "Kaiseki"));


    // GET all Countries
    @GetMapping("/countries")
    public List<Country> getCountries() {
        return countries;
    }

    //GET City of particular Country.
    @GetMapping("/countries/{countryId}/cities")
    public PageResponse<City> getCitiesByCountry(
            @PathVariable int countryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        // Check(Country)
        boolean countryFound = false;
        for (Country country : countries) {
            if (country.id() == countryId) {
                countryFound = true;
            }
        }
        if (!countryFound) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Country not found");
        }

        // Basic Check
        if (page < 0) {
            page = 0;
        }
        if (size < 1) {
            size = 10;
        }

        // Only Cities of the Country
        List<City> countryCities = new ArrayList<>();
        for (City city : cities) {
            if (city.countryId() == countryId) {
                countryCities.add(city);
            }
        }

        // Particular Page
        int total = countryCities.size();
        int start = Math.min(page * size, total);
        int end = Math.min(start + size, total);
        List<City> cityPage = countryCities.subList(start, end);

        // Total Pages
        int totalPages = (int) Math.ceil((double) total / size);

        return new PageResponse<>(cityPage, total, page, size, totalPages);
    }

    //GET Details of the city
    @GetMapping("/cities/{cityId}")
    public City getCity(@PathVariable int cityId) {
        for (City city : cities) {
            if (city.id() == cityId) {
                return city;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "City not found");
    }
}
