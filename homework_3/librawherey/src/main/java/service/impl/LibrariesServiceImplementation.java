package service.impl;

import model.Libraries;
import model.Schools;
import org.springframework.stereotype.Service;
import repository.jpa.LibrariesRepository;
import service.LibrariesService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LibrariesServiceImplementation implements LibrariesService{
    private final LibrariesRepository librariesRepository;

    public LibrariesServiceImplementation(LibrariesRepository librariesRepository) {
        this.librariesRepository = librariesRepository;
    }

    @Override
    public List<Libraries> findAll() { return this.librariesRepository.findAll(); }

    @Override
    public Optional<Libraries> findById(Long id) {
        return this.librariesRepository.findById(String.valueOf(id));
    }

    @Override
    public Optional<Libraries> save(String name, String opening_hours, double lat, double lon) {
        return Optional.empty();
    }


    Collection<Libraries> libraries;
    // TO-DO: staj if uslov zavisno od izbrano shkolo/univerzitet
    Schools selectedLocation;

    List<Libraries> within5km = libraries.stream()
            .filter(l -> haversine(l.getLat(), l.getLon(), selectedLocation.getLat(), selectedLocation.getLon()) <= 5)
            .collect(Collectors.toList());

    public static double haversine(double latLibrary, double lonLibrary, double latLocation, double lonLocation) {

        int radius = 6371; // average radius of the earth in km

        double distanceLat = Math.toRadians(latLocation - latLibrary);
        double distanceLon = Math.toRadians(lonLocation - lonLibrary);

        double a = Math.sin(distanceLat / 2) * Math.sin(distanceLat / 2) +
                Math.cos(Math.toRadians(latLibrary)) * Math.cos(Math.toRadians(latLocation))
                        * Math.sin(distanceLon / 2) * Math.sin(distanceLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = radius * c;

        return distance;
    }

    // within5km listata display-nija na front end (preku @PostMapping)
}