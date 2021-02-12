package com.logiweb.avaji.validation;

import com.logiweb.avaji.dtos.WaypointDTO;
import com.logiweb.avaji.exception.SuboptimalPathException;
import com.logiweb.avaji.service.api.management.OrderService;
import com.logiweb.avaji.service.api.path.PathDetailsService;
import com.logiweb.avaji.validation.annotation.WaypointsValid;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class WaypointsValidator implements ConstraintValidator<WaypointsValid, List<WaypointDTO>> {

    private final PathDetailsService pathDetailsService;
    private final OrderService orderService;

    @Autowired
    public WaypointsValidator(PathDetailsService pathDetailsService, OrderService orderService) {
        this.pathDetailsService = pathDetailsService;
        this.orderService = orderService;
    }


    @Override
    public void initialize(WaypointsValid constraintAnnotation) {

    }

    @Override
    public boolean isValid(List<WaypointDTO> waypointDTOS, ConstraintValidatorContext constraintValidatorContext) {
        String message = "";
        try {
            orderService.setCargoWeight(waypointDTOS);
            List<Long> path = pathDetailsService.getPath(waypointDTOS).getPath();
            double capacity = pathDetailsService.getMaxCapacityInTons(path, waypointDTOS);
            if (capacity > 50) {
                message = message.concat("Max capacity exceeds 50 tons.");
                return false;
            }
        } catch (SuboptimalPathException exception) {
            message = message.concat(exception.getMessage());
            return false;
        } finally {
            constraintValidatorContext.buildConstraintViolationWithTemplate(message)
                    .addConstraintViolation();
        }
        return true;
    }
}
