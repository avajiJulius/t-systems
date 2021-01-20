package com.logiweb.avaji.parser;

import com.logiweb.avaji.daos.CountryMapDAO;
import com.logiweb.avaji.dtos.CityDTO;
import com.logiweb.avaji.entities.models.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PathStringParser {

    private final CountryMapDAO countryMapDAO;

    @Autowired
    public PathStringParser(CountryMapDAO countryMapDAO) {
        this.countryMapDAO = countryMapDAO;
    }

    public List<CityDTO> pathStringToCityDTOList(String path) {
        List<CityDTO> cities = new ArrayList<>();
        List<Long> codes = Arrays.stream(path.split("-"))
                .map(Long::parseLong).collect(Collectors.toList());
        for(long code: codes) {
            City city = countryMapDAO.findCityByCode(code);
            cities.add(new CityDTO(city.getCityCode(), city.getCityName()));
        }
        return cities;
    }

    public String parsePathDequeToString(Deque<CityDTO> path) {
        StringBuffer sb = new StringBuffer();
        for (CityDTO city: path) {
            sb.append(city.getCityCode());
            sb.append("-");
        }
        return sb.toString();
    }

    public String parsePathListToString(List<Long> path) {
        StringBuffer sb = new StringBuffer();
        for (long code: path) {
            sb.append(code);
            sb.append("-");
        }
        return sb.toString();
    }
}
