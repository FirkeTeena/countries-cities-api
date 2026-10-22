package com.example.countries;

import java.util.List;


public record PageResponse<T>(
        List<T> content,       // Cities on Page
        int totalElements,     // Total cities of Country
        int page,              // Current Page (starts at 0)
        int size,              //How many Cities PP
        int totalPages) {      // Total Pages
}
