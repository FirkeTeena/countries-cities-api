package com.example.countries;

// A city has ID, a Name, and the ID of it's Country.
public record City(int id, String name, int countryId) {
}
