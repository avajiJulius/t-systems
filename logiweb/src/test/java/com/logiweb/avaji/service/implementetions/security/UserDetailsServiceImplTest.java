package com.logiweb.avaji.service.implementetions.security;

import com.logiweb.avaji.dao.UserDAO;
import com.logiweb.avaji.entity.enums.Permission;
import com.logiweb.avaji.entity.enums.Role;
import com.logiweb.avaji.entity.model.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.persistence.EntityManagerFactory;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringJUnitConfig(UserDetailsServiceImplTest.Config.class)
class UserDetailsServiceImplTest {

    static class Config {
        private List<User> users = Stream.of(
                new User(1, "avaji", "123", true, Role.DRIVER),
                new User(2, "cat", "123", true, Role.EMPLOYEE),
                new User(3, "admin", "123", true, Role.EMPLOYEE),
                new User(4, "dog", "123", true, Role.DRIVER)
        ).collect(Collectors.toList());

        @Bean
        public EntityManagerFactory entityManagerFactory() {
            return Mockito.mock(EntityManagerFactory.class);
        }

        public UserDAO userDAO() {
            UserDAO mock = Mockito.mock(UserDAO.class);

           when(mock.findUserByEmail(anyString())).thenAnswer(invocation -> {
               String email = invocation.getArgument(0);
               return users.stream().filter(u -> u.getEmail().equals(email)).findFirst().get();
           });

           return mock;
        }

        @Bean
        public UserDetailsServiceImpl userDetailsService() {
            return new UserDetailsServiceImpl(userDAO());
        }
    }

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Test
    void givenUserWithRoleDriver_thenReturnDriverAuthorities() {
        Collection<? extends GrantedAuthority> expected = Stream.of(
                new SimpleGrantedAuthority(Permission.DRIVER_READ.getPermission()),
                new SimpleGrantedAuthority(Permission.DRIVER_WRITE.getPermission())
        ).collect(Collectors.toList());
        UserDetails user = userDetailsService.loadUserByUsername("avaji");

        assertTrue(user.getAuthorities().containsAll(expected));
        assertEquals(expected.size(), user.getAuthorities().size());
    }


    @Test
    void givenUserWithRoleEmployee_thenReturnEmployeeAuthorities() {
        Collection<? extends GrantedAuthority> expected = Stream.of(
                new SimpleGrantedAuthority(Permission.EMPLOYEE_READ.getPermission()),
                new SimpleGrantedAuthority(Permission.EMPLOYEE_WRITE.getPermission())
        ).collect(Collectors.toList());
        UserDetails user = userDetailsService.loadUserByUsername("admin");

        assertTrue(user.getAuthorities().containsAll(expected));
        assertEquals(expected.size(), user.getAuthorities().size());
    }
}