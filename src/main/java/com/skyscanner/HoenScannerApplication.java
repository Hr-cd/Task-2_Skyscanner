package com.skyscanner;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;

import java.util.ArrayList;
import java.util.List;

public class HoenScannerApplication extends Application<HoenScannerConfiguration> {

    public static void main(final String[] args) throws Exception {
        new HoenScannerApplication().run(args);
    }

    @Override
    public String getName() {
        return "Hoen Scanner";
    }

    @Override
    public void initialize(final Bootstrap<HoenScannerConfiguration> bootstrap) {
    }

    @Override
    public void run(final HoenScannerConfiguration configuration,
                    final Environment environment) throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        List<SearchResult> hotels = mapper.readValue(
                getClass().getClassLoader().getResourceAsStream("hotels.json"),
                new TypeReference<List<SearchResult>>() {}
        );

        List<SearchResult> rentalCars = mapper.readValue(
                getClass().getClassLoader().getResourceAsStream("rental_cars.json"),
                new TypeReference<List<SearchResult>>() {}
        );

        List<SearchResult> searchResults = new ArrayList<>();
        searchResults.addAll(hotels);
        searchResults.addAll(rentalCars);

        environment.jersey().register(new SearchResource(searchResults));
    }
}