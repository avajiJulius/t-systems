##Abstract:
This is the company’s website. Company that provides cargo transportation services. This site is designed for two types of workers. Drivers, delivering cargo, and logisticians, who create orders and distribute cars and drivers for them. This website solves the problems like: сreating the shortest path and calculating max capacity for the path manually, calculate working hours for drivers and facilitate manipulation with trucks, drivers and orders.

##Business cases:
1) The logistician needs to create an order for several cargo,
2) Add to this order serviceable truck with capacity greater or equals max capacity of this order and current city of truck be the same as the start point of this order.
3) Add drivers to this order considering that the limit of hours worked in this month not been exceeded during order execution.and current city is the same as the order truck city.
4) Logistician need to view, delete, edit and create the drivers and trucks.
5) Driver needs to see  his order, truck and co-drivers.
6) Driver needs to update his status and finish the shift.
7) Driver needs to change cargo status then they load and unload.

##Data Model:
User - have unique id, email, password and role, need to authorize in the security system.
Driver - entity that reflects drivers. Relation: driver related to truck by @ManyToOne, because one truck entity may have many drivers.
Truck - entity that reflects trucks of this company. Relation: truck related to driver by @OneToMany, because drivers of this truck can relate only to it.
Order - entity that reflects order with waypoints, status and truck on which the order will be executed.
Waypoint - entity that reflects path points where cargo loading or unloading.
Cargo - entity that reflects cargo.
City - entity that reflects cities;
Road - entity that connects cities and defines distance between them. Relation: one road related to two cities by @ManyToOne
CountryMap - entity that reflects map for path creation.


##Architecture:
Use MVC architecture pattern. For building some entities use pattern Builder. For faster data retrieval from the database and data transfer between layers use DTO pattern.

Controller layer:
HomeController - home page endpoint.
AuthController - endpoint for login
DriverController - represent methods for the view of all drivers, have endpoints for creation, editing and delete drivers.
TruckController - represent methods for the view of all trucks, have endpoints for creation, editing and delete trucks.
OrderController - represent methods for the view of all orders, have endpoints for creation, adding truck and drivers to order, view cargo of order.
ProfileController - represent methods for the view of order and shift details of drivers, have endpoints for changing cargo and driver status, city and finish the shift when order is completed.

##Service layer:
CargoService - view all cargo or cargo of order.
DriverService - provide methods for creating, reading, updating and delete drivers from drivers list from database.
OrderService - provide methods for creating and reading orders, reading and adding drivers and trucks for order.
TruckService - provided methods for creating, reading, updating and delete trucks from database
CountryMapService - provided methods for reading all cities and roads.
PathDetailsService - provided methods for calculating shortest path, max capacity on this path, approximate time it takes to overcome the path.
OrderDetailsService - provided methods for view order details, change cargo status and remaining path.
ShiftDetailsService - provided methods for view shift details, change driver status and shift active status, calculate worked hours and finish shift of completed order.
UserDetailsServiceImpl - implementation for UserDetailsService, load user by email, if user has role EMPLOYEE logging entrance.

##DAO layer:
CargoDAO - find all Cargo entities and Cargo entities of order ID or of cargo ID, update Cargo and find cargo weight of cargo ID.
CountryMapDAO - find all City entities and return a list of CityDTO. Find City entity by city code and find all Road entities and return a list of RoadDTO.
DriverDAO - crud operation on Driver entity. Save WorkShift entity.
OrderDAO - crud operation on Order entity. Find Waypoints entities by order and return the WaypointDTO list and save Waypoint entities. Find TruckDTO and DriverDTO lists by parameters for order. Save and find OrderDetails entities.
OrderDetailsDAO - find and update order details entities.
ShiftDetailsDAO - find and update shift details entities.
TruckDAO - crud operation on Truck entity and count drivers which related to this truck.
UserDAO - find user by email, return UserDTO.

##Views:
For UI page user thymeleaf with bootstrap 4. Have a page for creating, editing and view list of drivers, trucks, orders. User thymeleaf fragments for navbar and script for bootstrap. Profile view for shift and order details of driver. In exception folder have views for exceptions(404,403,500 and etc.).

##Database:
Table cities no relation, pk citi_code field.
Table roads reference to cities table by fk city_a/b_code, pk road_id field.
Table trucks reference to cities table by fk city_code, pk id field.
Table orders reference to trucks table by fk truck_id, pk id field.
Table order_details reference to orders table by fk id, pk id field.
Table users no reference, pk id field.
Table drivers reference to users, cities, trucks, order_details tables by fk id, city_code, truck_id, order_details, pk id field.
Table work_shifts reference to drivers by fk id, pk id field.
Table cargo no reference, pk id field.
Table waypoints reference to cities, orders, cargo tables by fk city_code, order_id, cargo_id, pk waypoint_id field.

##Technologies:
Core: Java, Spring MVC, Security,  Hibernate.
Database: PostgreSQL
View: Thymeleaf and Bootstrap 4
Logging: Log4j2
Testing: Mockito and JUnit 5
Utils: Lombok

##Tests:
ShiftDetailsServiceTest:
givenRestDriverStatus_WhenShiftActiveTrue_ThenItShouldReturnFalse, givenRestDriverStatus_WhenShiftActiveFalse_ThenItShouldReturnTrue,
givenDrivingDriverStatus_WhenShiftActiveTrue_ThenItShouldReturnTrue,
givenDrivingDriverStatus_WhenShiftActiveFalse_ThenItShouldReturnFalse - test validate method.
thenUpdateShiftDetailsThenreturnUpdateDetailsValues1/2 - test changeShiftDetails method for correct change entity.

OrderDetailsServicetest:
whenAllCargoStatusAreDeliveredReturnTrue, whenNotAllCargoStatusAreDeliveredReturnFalse - test orderIsCompleted method on correct work.

PathDetailsServiceTest:
returnSamePathOfWaypointAsExpected1/5 - add wapoints to getPath method and result path should equals expected path that has been calculated before.
returnDistanceAsExpected1/5 - calculate distance of path and result should be equals distance that has been calculated before.
returnShiftHoursAsExpected1/5 - calculate shift hours of path and result should be equals shift hours that has been calculated before.
returnMaxCapacityAsExpected1/5 - calculate capacity of path and result should be equals capacity that has been calculated before.

PathParserTest:
whenParseListThenReturnRightString1/2, whenParseListThenReturnWrongString1, whenParseEmptyListThenReturnEmptyString - test parseLongListToString on correct parse.
whenParseSringThenReturnRightPreetyPath1/2 - test toPrettyPath method correct work.
whenParseEmptyStringThenThrowException, whenParseUncorrectStringThenThrowException - test parseStringToLongList throw PathParseException, if string empty or string uncorrect
whenParseStringThenReturnRightList1/2, whenParseStringThenReturnWrongList1 - test parseStringToLongList to correct parse.





