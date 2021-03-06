package com.logiweb.avaji.config;

import com.logiweb.avaji.dao.*;
import com.logiweb.avaji.dtos.*;
import com.logiweb.avaji.entity.enums.CargoStatus;
import com.logiweb.avaji.entity.enums.DriverStatus;
import com.logiweb.avaji.entity.enums.OrderStatus;
import com.logiweb.avaji.entity.model.*;
import com.logiweb.avaji.service.api.management.DriverService;
import com.logiweb.avaji.service.api.management.OrderService;
import com.logiweb.avaji.service.api.map.CountryMapService;
import com.logiweb.avaji.service.api.mq.InformationProducerService;
import com.logiweb.avaji.service.api.path.PathDetailsService;
import com.logiweb.avaji.service.implementetions.management.OrderServiceImpl;
import com.logiweb.avaji.service.implementetions.path.PathDetailsServiceImpl;
import com.logiweb.avaji.service.implementetions.profile.OrderDetailsServiceImpl;
import com.logiweb.avaji.service.implementetions.profile.ShiftDetailsServiceImpl;
import com.logiweb.avaji.service.implementetions.utils.DateTimeService;
import com.logiweb.avaji.service.implementetions.utils.Mapper;
import com.logiweb.avaji.service.implementetions.utils.PathParser;
import com.logiweb.avaji.validation.EmailUniqueValidator;
import com.logiweb.avaji.validation.TruckIDUniqueValidator;
import com.logiweb.avaji.validation.WaypointsValidator;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.EntityManagerFactory;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@Configuration
@Profile("test")
@EnableScheduling
public class TestConfig {

    public List<String> userList = Stream.of(
            "avaji@gmail.com", "driver@gmail.com", "admin@gmail.com"
    ).collect(Collectors.toList());

    public List<String> truckList = Stream.of(
            "MN12312", "BG12345", "AB12345", "PO54321"
    ).collect(Collectors.toList());

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
            new Cargo(10,0,"D1",4.0, CargoStatus.DELIVERED),
            new Cargo(11,0,"E1",5.0, CargoStatus.SHIPPED)
    ).collect(Collectors.toList());



    List<ShiftDetailsDTO> shiftDetailsList = Stream.of(
            new ShiftDetailsDTO(1, DriverStatus.REST, false, null, null, 20),
            new ShiftDetailsDTO(2, DriverStatus.DRIVING, true,
                    LocalDateTime.of(2021,1, 12, 7,0), null, 20)
    ).collect(Collectors.toList());

    public List<City> cityList = Stream.of(
            new City(1, "A"),
            new City(2, "B"),
            new City(3, "C"),
            new City(4, "D"),
            new City(5, "E"),
            new City(6, "F"),
            new City(7, "G"),
            new City(8, "H"),
            new City(9, "I"),
            new City(10, "J")
    ).collect(Collectors.toList());

    public List<Truck> trucks = Stream.of(
            new Truck("XB12312", 15, true, false, new City(5, "E")),
            new Truck("KI12312", 25, true, false, new City(5, "E")),
            new Truck("AR12312", 35, true, false, new City(5, "E")),
            new Truck("CB12312", 35, false, false, new City(5, "E")),
            new Truck("MQ12312", 35, true, true, new City(5, "E"))
    ).collect(Collectors.toList());

    public List<DriverDTO> drivers = Stream.of(
            new DriverDTO("Alex", "Matushkin", 170, 1L),
            new DriverDTO("Polina", "Kuznetsova", 10, 1L),
            new DriverDTO("Grigori", "Ivanov", 20, 1L),
            new DriverDTO("Vasia", "Buben", 140, 1L)
    ).collect(Collectors.toList());

    public List<Order> orderList = Stream.of(
            new Order(OrderStatus.UNCOMPLETED, "1-2-3-6-", new Truck("MN12121",15, true, false, new City(1, "A")), 10, LocalDateTime.now()),
            new Order(OrderStatus.UNCOMPLETED, "5-3-4-", new Truck("FG12122"), 20, LocalDateTime.now()),
            new Order(OrderStatus.UNCOMPLETED, "7-8-3-1-", new Truck("KI12902",35, true, false, new City(2, "B")), 30, LocalDateTime.now()),
            new Order(OrderStatus.UNCOMPLETED, "5-4-2-1-", null, 20, LocalDateTime.now()),
            new Order(OrderStatus.UNCOMPLETED, "5-3-", null, 30, LocalDateTime.now())
    ).collect(Collectors.toList());

    public List<OrderDetails> orderDetailsEntities = Stream.of(
            new OrderDetails(1, orderList.get(0), "2-3-5-", 5.0),
            new OrderDetails(2, orderList.get(1), "3-5-", 3.0),
            new OrderDetails(3, orderList.get(2), "1-", 0.0)
    ).collect(Collectors.toList());

    public List<OrderDetailsDTO> orderDetailsList = Stream.of(
            new OrderDetailsDTO(1, "MN12121", "1-2-3-4-", "2-3-5-", 10.0, 5.0),
            new OrderDetailsDTO(2, "FG12122", "5-3-4-", "3-5-", 20.0, 3.0),
            new OrderDetailsDTO(3, "KI12902", "7-8-3-1-", "1-", 30.0, 0.0)
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

        doAnswer(invocation -> {
            Cargo cargo = invocation.getArgument(0);
            int index = Integer.parseInt(String.valueOf(cargo.getCargoId())) - 1;
            cargoList.remove(index);
            cargoList.add(index, cargo);
            return null;
        }).when(mock).updateCargo(any(Cargo.class));

        return mock;
    }

    @Bean
    public OrderDetailsDAO orderDetailsDAO() {
        OrderDetailsDAO mock = Mockito.mock(OrderDetailsDAO.class);
        Mockito.when(mock.findOrderDetails(anyLong())).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] arguments = invocationOnMock.getArguments();
                int index = Integer.parseInt(String.valueOf(arguments[0])) - 1;
                if(index > orderDetailsList.size() - 1) {
                    return null;
                }
                return orderDetailsList.get(index);
            }
        });

        Mockito.when(mock.findOrderIdOfDriverId(anyLong()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Mockito.when(mock.findOrderDetailsEntity(anyLong())).thenAnswer(invocation -> {
            long id = invocation.getArgument(0);
            int index = Integer.parseInt(String.valueOf(id)) - 1;
            return orderDetailsEntities.get(index);
        });

        doAnswer(invocation -> {
            OrderDetails orderDetails = invocation.getArgument(0);
            int index = Integer.parseInt(String.valueOf(orderDetails.getId()))-1;
            orderDetailsEntities.remove(index);
            orderDetailsEntities.add(index, orderDetails);
            return null;
        }).when(mock).updateOrderDetails(any(OrderDetails.class), anyLong());

        return mock;
    }

    @Bean
    public OrderDAO orderDAO() {
        OrderDAO mock = Mockito.mock(OrderDAO.class);

        when(mock.findOrderById(anyLong())).thenAnswer(invocation -> {
            long id = invocation.getArgument(0);
            int index = Integer.parseInt(String.valueOf(id)) - 1;
            return orderList.get(index);
        });

        when(mock.findTrucksForOrder(anyDouble(), anyLong())).thenAnswer(invocation -> {
            double capacity = invocation.getArgument(0);
            long id = invocation.getArgument(1);
            return trucks.stream()
                    .filter(t -> t.getCapacity() >= capacity && t.getCurrentCity().getCityCode() == id
                    && !t.isInUse() && t.isServiceable())
                    .collect(Collectors.toList());
        });

        when(mock.findTruckByOrderId(anyLong())).thenAnswer(invocation -> {
            long id = invocation.getArgument(0);
            int index = Integer.parseInt(String.valueOf(id));
            Order order = orderList.get(index - 1);
            return order.getDesignatedTruck();
        });

        when(mock.findDriverForOrder(anyDouble(), anyLong())).thenAnswer(invocation -> {
            double shiftHours = invocation.getArgument(0);
            long cityCode = invocation.getArgument(1);
            return drivers.stream()
                    .filter(d -> d.getHoursWorked() + shiftHours < 172 && d.getCityCode() == cityCode)
                    .collect(Collectors.toList());
        });

        when(mock.saveOrder(any(Order.class))).then(invocation -> {
            Order order = invocation.getArgument(0);
            int id = orderList.size() + 1;
            order.setId(id);
            orderList.add(order);
            return null;
        }).thenReturn((long) orderList.size());
        return mock;
    }

    @Bean
    public ShiftDetailsDAO shiftDetailsDAO() {
        ShiftDetailsDAO mock = Mockito.mock(ShiftDetailsDAO.class);
        Mockito.when(mock.findShiftDetails(anyLong())).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] arguments = invocationOnMock.getArguments();
                int index = Integer.parseInt(String.valueOf(arguments[0])) - 1;
                return shiftDetailsList.get(index);
            }
        });
        doAnswer(invocation -> {
            ShiftDetailsDTO dto = invocation.getArgument(0);
            int index = Integer.parseInt(String.valueOf(dto.getId())) - 1;
            shiftDetailsList.remove(index);
            shiftDetailsList.add(index, dto);
            return null;
        }).when(mock).updateShiftDetails(any(ShiftDetailsDTO.class));
        return mock;
    }

    @Bean
    public ShiftDetailsServiceImpl shiftDetailsService() {
        return new ShiftDetailsServiceImpl(shiftDetailsDAO(), informationProducerService());
    }

    @Bean
    public PathDetailsService pathDetailsService() {
        return new PathDetailsServiceImpl(countryMapService());
    }

    @Bean
    public InformationProducerService informationProducerService() {
        return Mockito.mock(InformationProducerService.class);
    }

    @Bean
    public OrderDetailsServiceImpl orderDetailsService() {
        return new OrderDetailsServiceImpl(orderDetailsDAO(), orderDAO(),
                cargoDAO(), pathParser(),shiftDetailsService(),
                pathDetailsService(), informationProducerService());
    }

    @Bean
    public ScheduleDAO scheduleDAO() {
        ScheduleDAO mock = Mockito.mock(ScheduleDAO.class);
        Mockito.when(mock.refreshWorkedHours()).thenReturn(-1);
        return mock;
    }
    @Bean
    public DateTimeService timeService() {
        return new DateTimeService(scheduleDAO());
    }

    @Bean
    public CountryMapService countryMapService() {
        CountryMapService mock = Mockito.mock(CountryMapService.class);
        Mockito.when(mock.findCityByCode(anyLong())).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] arguments = invocationOnMock.getArguments();
                int index = Integer.parseInt(String.valueOf(arguments[0])) - 1;
                return cityList.get(index);
            }
        });
        Mockito.when(mock.readAllCities())
                .thenReturn(Stream.of(
                        new CityDTO(1, "A"),
                        new CityDTO(2, "B"),
                        new CityDTO(3, "C"),
                        new CityDTO(4, "D"),
                        new CityDTO(5, "E"),
                        new CityDTO(6, "G"),
                        new CityDTO(7, "H")
                ).collect(Collectors.toList()));
        Mockito.when(mock.readAllRoads())
                .thenReturn(Stream.of(
                        new RoadDTO(1,2,2),
                        new RoadDTO(1,3,2),
                        new RoadDTO(1,6,5),
                        new RoadDTO(2,1,2),
                        new RoadDTO(2,3,1),
                        new RoadDTO(2,4,2),
                        new RoadDTO(2,5,3),
                        new RoadDTO(3,1,2),
                        new RoadDTO(3,2,1),
                        new RoadDTO(3,5,2),
                        new RoadDTO(3,6,2),
                        new RoadDTO(4,2,2),
                        new RoadDTO(4,5,2),
                        new RoadDTO(5,2,3),
                        new RoadDTO(5,3,2),
                        new RoadDTO(5,4,2),
                        new RoadDTO(5,6,3),
                        new RoadDTO(5,7,3),
                        new RoadDTO(6,1,5),
                        new RoadDTO(6,3,2),
                        new RoadDTO(6,5,3),
                        new RoadDTO(6,7,1),
                        new RoadDTO(7,5,3),
                        new RoadDTO(7,6,1)
                ).collect(Collectors.toList()));
        return mock;
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public Mapper mapper() {
        return new Mapper(countryMapService(), cargoDAO(), passwordEncoder());
    }

    @Bean
    public CountryMapDAO countryMapDAO() {
        CountryMapDAO mock = Mockito.mock(CountryMapDAO.class);
        Mockito.when(mock.findCityByCode(anyLong())).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] arguments = invocationOnMock.getArguments();
                int index = Integer.parseInt(String.valueOf(arguments[0])) - 1;
                return cityList.get(index);
            }
        });
        return mock;
    }

    @Bean
    public PathParser pathParser() {
        return new PathParser(countryMapDAO());
    }

    @Bean
    public ValidatorDAO validatorDAO() {
        ValidatorDAO mock = Mockito.mock(ValidatorDAO.class);
        Mockito.when(mock.checkEmailUnique(anyString())).thenAnswer(new Answer<Boolean>() {
            @Override
            public Boolean answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] arguments = invocationOnMock.getArguments();
                String email = (String) arguments[0];
                return userList.stream().anyMatch(e -> e.equals(email));
            }
        });

        Mockito.when(mock.checkIdUnique(anyString())).thenAnswer(new Answer<Boolean>() {
            @Override
            public Boolean answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] arguments = invocationOnMock.getArguments();
                String id = (String) arguments[0];
                return truckList.stream().anyMatch(i -> i.equals(id));
            }
        });


        return mock;
    }

    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(orderDAO(), mapper(), pathDetailsService(), pathParser(),
                Mockito.mock(DriverService.class),
                cargoDAO(), informationProducerService(), timeService());
    }

    @Bean
    public WaypointsValidator waypointsValidator() {
        return new WaypointsValidator(pathDetailsService(), orderService());
    }

    @Bean
    public EmailUniqueValidator emailUniqueValidator() {
        return new EmailUniqueValidator(validatorDAO());
    }

    @Bean
    public TruckIDUniqueValidator idUniqueValidator() {
        return new TruckIDUniqueValidator(validatorDAO());
    }

    @Bean
    public CountryMap countryMap() {
        return new CountryMap(countryMapService());
    }
}
