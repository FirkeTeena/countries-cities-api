package com.example.countries;

import java.util.List;


public record PageResponse<T>(
        List<T> content,
        int totalElements,     // Total cities of Country
        int page,              // Current Page
        int size,              // City per page
        int totalPages) {      // Total Pages
}
