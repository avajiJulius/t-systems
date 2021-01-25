package com.logiweb.avaji.services.implementetions.profile;

import com.logiweb.avaji.daos.CargoDAO;
import com.logiweb.avaji.daos.OrderDAO;
import com.logiweb.avaji.daos.OrderDetailsDAO;
import com.logiweb.avaji.entities.enums.CargoStatus;
import com.logiweb.avaji.entities.models.Cargo;
import com.logiweb.avaji.services.api.profile.ShiftDetailsService;
import com.logiweb.avaji.services.implementetions.utils.PathParser;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;

@SpringJUnitConfig(OrderDetailsServiceTest.Config.class)
class OrderDetailsServiceTest {

    @Configuration
    static class Config {

        public List<Cargo> cargoList = Stream.of(
                new Cargo(1,0,"A1",1.0, CargoStatus.PREPARED),
                new Cargo(2,0,"A2",1.0, CargoStatus.PREPARED),
                new Cargo(3,0,"A3",1.0, CargoStatus.PREPARED),
                new Cargo(4,0,"A4",1.0, CargoStatus.PREPARED),
                new Cargo(5,0,"B1",2.0, CargoStatus.PREPARED),
                new Cargo(6,0,"B2",2.0, CargoStatus.PREPARED),
                new Cargo(7,0,"B3",2.0, CargoStatus.PREPARED),
                new Cargo(8,0,"C1",3.0, CargoStatus.SHIPPED),
                new Cargo(9,0,"C2",3.0, CargoStatus.DELIVERED),
                new Cargo(10,0,"D1",4.0, CargoStatus.DELIVERED)
        ).collect(Collectors.toList());

        @Bean
        public EntityManagerFactory entityManagerFactory() {

            return Mockito.mock(EntityManagerFactory.class);
        }

        @Bean
        public CargoDAO cargoDAO() {
            CargoDAO mock = Mockito.mock(CargoDAO.class);
            Mockito.when(mock.findCargoById(anyLong())).thenAnswer(new Answer<Object>() {
                @Override
                public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                    Object[] arguments = invocationOnMock.getArguments();
                    int index = Integer.parseInt(String.valueOf(arguments[0])) - 1;
                    return cargoList.get(index);
                }
            });

            Mockito.when(mock.findCargoByOrderId(anyLong())).thenAnswer(new Answer<Object>() {
                @Override
                public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                    Object[] arguments = invocationOnMock.getArguments();
                    double weight = Double.parseDouble(String.valueOf(arguments[0]));
                    return cargoList.stream().filter(c -> c.getCargoWeight() == weight).collect(Collectors.toList());
                }
            });

            return mock;
        }

        public OrderDetailsDAO orderDetailsDAO() {
            return Mockito.mock(OrderDetailsDAO.class);
        }

        public OrderDAO orderDAO() {
            return Mockito.mock(OrderDAO.class);
        }

        public ShiftDetailsService shiftDetailsService() {
            return Mockito.mock(ShiftDetailsService.class);
        }

        public PathParser pathParser() {
            return Mockito.mock(PathParser.class);
        }

        @Bean
        public OrderDetailsServiceImpl orderDetailsService() {
            return new OrderDetailsServiceImpl(orderDetailsDAO(), orderDAO(), pathParser(),
                    cargoDAO(), shiftDetailsService());
        }

    }

    @Autowired
    public OrderDetailsServiceImpl orderDetailsService;

    @Test
    void whenAllCargoStatusAreDeliveredReturnTrue() {
        boolean result = orderDetailsService.orderIsComplete(4);

        assertTrue(result);
    }

    @Test
    void whenNotAllCargoStatusAreDeliveredReturnFalse() {
        boolean result = orderDetailsService.orderIsComplete(3);

        assertFalse(result);
    }

}