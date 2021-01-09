package com.logiweb.avaji.validation;

import com.logiweb.avaji.crud.order.dto.WaypointDTO;
import com.logiweb.avaji.entities.enums.WaypointType;
import com.logiweb.avaji.exceptions.LoadAndUnloadValidateException;

import java.util.List;
import java.util.stream.Collectors;

public class Validator {

    public boolean  validateOrderByWaypoints(List<WaypointDTO> waypoints) {
        List<Long> load = waypoints.stream().filter(w -> w.getType() == WaypointType.LOADING)
                .map(w -> w.getCargoId()).sorted().collect(Collectors.toList());
        List<Long> unload = waypoints.stream().filter(w -> w.getType() == WaypointType.UNLOADING)
                .map(w -> w.getCargoId()).sorted().collect(Collectors.toList());
        return load.equals(unload);
    }
}
