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
            new Country(3, "Luxembourg"),
            new Country(4, "Japan"));

    private final List<City> cities = List.of(
            // India (countryId = 1)
            new City(1, "Mumbai", 1),
            new City(2, "Delhi", 1),
            new City(3, "Bengaluru", 1),
            new City(4, "Hyderabad", 1),
            new City(5, "Ahmedabad", 1),
            new City(6, "Chennai", 1),
            new City(7, "Kolkata", 1),
            new City(8, "Pune", 1),
            new City(9, "Jaipur", 1),
            new City(10, "Surat", 1),
            new City(11, "Lucknow", 1),
            new City(12, "Nagpur", 1),

            // Luxembourg (countryId = 2)
            new City(18, "Luxembourg City", 2),
            new City(19, "Esch-sur-Alzette", 2),
            new City(20, "Differdange", 2),
            // Japan (countryId = 3)
            new City(21, "Tokyo", 3),
            new City(22, "Osaka", 3),
            new City(23, "Kyoto", 3));


    // Returns the list of all countries(GET Countries)
    @GetMapping("/countries")
    public List<Country> getCountries() {
        return countries;
    }


    // Returns the cities of one country, one page at a time.
    @GetMapping("/countries/{countryId}/cities")
    public PageResponse<City> getCitiesByCountry(
            @PathVariable int countryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        //Country Exist or Not
        boolean countryFound = false;
        for (Country country : countries) {
            if (country.id() == countryId) {
                countryFound = true;
            }
        }
        if (!countryFound) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Country not found");
        }


        //Check
        if (page < 0) {
            page = 0;
        }
        if (size < 1) {
            size = 5;
        }


        //Get the cities of the particular Country only
        List<City> countryCities = new ArrayList<>();
        for (City city : cities) {
            if (city.countryId() == countryId) {
                countryCities.add(city);
            }
        }

        //Pagination
        int total = countryCities.size();
        int start = Math.min(page * size, total);
        int end = Math.min(start + size, total);
        List<City> cityPage = countryCities.subList(start, end);

        // Step 5: total pages
        int totalPages = (int) Math.ceil((double) total / size);

        return new PageResponse<>(cityPage, total, page, size, totalPages);
    }


    // GET City Details by City ID
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
