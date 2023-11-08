package cl.janodevg.restService.services;

import cl.janodevg.restService.entities.models.User;
import cl.janodevg.restService.repository.UserRepository;
import cl.janodevg.restService.common.exceptions.EmailAlreadyExistsException;
import cl.janodevg.restService.common.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class UserServiceImplementTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImplement userService;

    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setEmail("test@example.com");
    }

    @Test
    public void whenFindUserByEmail_thenShouldReturnUser() throws ResourceNotFoundException {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        User foundUser = userService.findUserByEmail("test@example.com");
        assertNotNull(foundUser);
        assertEquals(user.getEmail(), foundUser.getEmail());
    }

    @Test
    public void whenFindUserByEmail_thenShouldThrowResourceNotFoundException() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.findUserByEmail("test@example.com"));
    }

    @Test
    public void whenCreateUser_thenShouldThrowEmailAlreadyExistsException() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        assertThrows(EmailAlreadyExistsException.class, () -> userService.createUser(user));
    }


    @Test
    public void whenUpdateUser_thenShouldThrowResourceNotFoundException() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.updateUser(user, user.getEmail()));
    }

    @Test
    public void whenDeleteUser_thenShouldThrowResourceNotFoundException() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.deleteUser(user.getEmail()));
    }
}
