package com.logiweb.avaji.service.implementetions.utils;

import com.logiweb.avaji.dao.CountryMapDAO;
import com.logiweb.avaji.dtos.CityDTO;
import com.logiweb.avaji.entity.model.City;
import com.logiweb.avaji.exception.PathParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This service convert List of cityCodes to string which reflects path and vice versa.
 * Parse string which reflect path to pretty string
 * 1-2-6- CONVERT TO Saint-Petersburg - Moscow - Samara
 *
 */

@Service
public class PathParser {

    private static final String DELIMITER = "-";
    private final CountryMapDAO countryMapDAO;

    @Autowired
    public PathParser(CountryMapDAO countryMapDAO) {
        this.countryMapDAO = countryMapDAO;
    }

    public List<CityDTO> pathStringToCityDTOList(String path) {
        List<CityDTO> cities = new ArrayList<>();
        List<Long> codes = parseStringToLongList(path);
        for(long code: codes) {
            City city = countryMapDAO.findCityByCode(code);
            cities.add(new CityDTO(city.getCityCode(), city.getCityName()));
        }
        return cities;
    }

    public String parseLongListToString(List<Long> path) {
        StringBuilder result = new StringBuilder();
        for (long code: path) {
            result.append(code);
            result.append(DELIMITER);
        }
        return result.toString();
    }

    public String toPrettyPath(String path) {
        List<Long> codes = parseStringToLongList(path);
        StringBuilder result = new StringBuilder();
        for(long code: codes) {
            City city = countryMapDAO.findCityByCode(code);
            result.append(city.getCityName()).append(" - ");
        }
        return result.substring(0, result.length() - 3);
    }

    public List<Long> parseStringToLongList(String path)  {
        List<Long> result;
        try {
             result = Arrays.stream(path.split(DELIMITER))
                     .map(Long::parseLong).collect(Collectors.toList());
        } catch (Exception e) {
            throw new PathParseException("Cannot parse path");
        }
        return result;
    }
}
