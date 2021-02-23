package com.logiweb.avaji.service.implementetions.path;

import com.logiweb.avaji.dtos.*;

import com.logiweb.avaji.entity.model.Path;
import com.logiweb.avaji.exception.SuboptimalPathException;
import com.logiweb.avaji.service.api.map.CountryMapService;
import com.logiweb.avaji.service.api.path.PathDetailsService;
import com.logiweb.avaji.entity.model.CountryMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PathDetailsServiceImpl implements PathDetailsService {

    private static final Logger logger = LogManager.getLogger(PathDetailsServiceImpl.class);

    private final CountryMap countryMap;

    @Autowired
    public PathDetailsServiceImpl(CountryMapService countryMapService) {
        this.countryMap = new CountryMap(countryMapService);
    }

    @Override
    public double getMaxCapacityInTons(List<Long> citiesCodes, List<WaypointDTO> waypoints) {
        List<WaypointDTO> loadAvailable = new ArrayList<>(waypoints);
        List<WaypointDTO> unloadAvailable = new ArrayList<>();
        double maxCapacity = 0;
        double capacity = 0;

        for (long code : citiesCodes) {
            double loadCapacity = 0.0;
            double unloadCapacity = 0.0;

            List<WaypointDTO> load = loadAvailable.stream()
                    .filter(waypoint -> waypoint.getLoadCityCode() == code)
                    .collect(Collectors.toList());

            if (!load.isEmpty()) {
                loadCapacity += calculateTempCapacity(load);
                loadAvailable.removeAll(load);
                unloadAvailable.addAll(load);
            }

            capacity += loadCapacity;

            if(capacity > maxCapacity) {
                maxCapacity = capacity;
            }

            List<WaypointDTO> unload = unloadAvailable.stream()
                    .filter(waypoint -> waypoint.getUnloadCityCode() == code)
                    .collect(Collectors.toList());

            if(!unload.isEmpty()) {
                unloadCapacity += calculateTempCapacity(unload);
                unloadAvailable.removeAll(unload);
            }

            capacity -= unloadCapacity;

            if(capacity == 0 && !loadAvailable.isEmpty()) {
                logger.error("Path is suboptimal");
                throw new SuboptimalPathException("Path is suboptimal. Because truck goes empty from " + countryMap.getCityNameByCode(code)+
                        " to next waypoint city.");
            }
        }

        return convertKilogramsToTons(maxCapacity);
    }


    @Override
    public double getShiftHours(List<Long> path) {
        double shiftHours = 0.0;
        for (int i = 0; i < path.size() - 1; i++) {
            int index = i;
            shiftHours += countryMap.getDistanceBetween(path.get(index), path.get(index+1));
        }
        return shiftHours;
    }



    @Override
    public Path getPath(List<WaypointDTO> waypoints) {
        List<Path> available = new ArrayList<>();
        for(WaypointDTO waypoint : waypoints) {
            if (waypoint.getLoadCityCode() == waypoint.getUnloadCityCode()) {
                available.add(new Path(new ArrayList<>(),
                        waypoint.getLoadCityCode(), Double.MAX_VALUE));
            } else {
                available.add(new Path(new ArrayList<>(),
                        waypoint.getLoadCityCode(), waypoint.getUnloadCityCode(), Double.MAX_VALUE));
            }
        }
        long maxCounter = 0L;
        int index = 0;

        for (int i = 0; i < available.size(); i++) {
            long loadCode = available.get(i).getFirst();
            long count = available.stream().filter(p -> p.getFirst() == loadCode).count();
            if(count > maxCounter) {
                maxCounter = count;
                index = i;
            }
        }


        long newStart = available.get(index).getFirst();
        available.stream().filter(a -> a.getFirst() == newStart).forEach(Path::removeFirst);

        logger.info("Create Path");

        return getNewPath(new Path(new ArrayList<>(), newStart, 0), available, new ArrayList<>());

    }

    private double convertKilogramsToTons(double maxCapacity) {
        return (maxCapacity / 1000);
    }

    private double calculateTempCapacity(List<WaypointDTO> waypoints) {
        return waypoints.stream().map(WaypointDTO::getCargoWeight)
                .reduce(Double::sum).get();
    }

    private Path getNewPath(Path path, List<Path> available, List<Path> open) {
        if (isEmpty(available)) {
            return path;
        }

        long maxCounter = 0L;
        int index = 0;

        List<Long> connected = countryMap.findConnected(path.getLast());
        for (int i = 0; i < connected.size(); i++) {
            long loadCode = connected.get(i);
            long count = available.stream().filter(a -> a.getFirst() == loadCode).count();
            if(count > maxCounter) {
                maxCounter = count;
                index = i;
            }
        }

        if(maxCounter == 0L) {
            double minDist = Double.MAX_VALUE;
            index = 0;

            for (long codes: connected) {
                open.add(createNewPath(path, path.getLast(), codes));
            }
            for (int i = 0; i < open.size(); i++) {
                double dist = open.get(i).getDistance();
                if(minDist > dist) {
                    minDist = dist;
                    index = i;
                }
            }

            Path newPath = open.remove(index);

            return getNewPath(newPath, available, open);
        }

        long newStart = connected.get(index);
        available.stream().filter(a -> a.getFirst() == newStart).forEach(Path::removeFirst);
        double distance = countryMap.getDistanceBetween(path.getLast(), newStart);
        path.add(newStart, distance);

        return getNewPath(path, available, new ArrayList<>());

    }

    private boolean isEmpty(List<Path> available) {
        for(Path path : available) {
            if(!path.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private Path createNewPath(Path path, long start, long goal) {
        List<Long> newPath = new ArrayList<>();
        newPath.addAll(path.getPath());
        double distance = path.getDistance() +
                countryMap.getDistanceBetween(start, goal);
        return new Path.Builder()
                .withPath(newPath)
                .add(goal)
                .withDistance(distance)
                .build();
    }
}
