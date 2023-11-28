package br.com.unifalmg.blog.unit;

import br.com.unifalmg.blog.entity.User;
import br.com.unifalmg.blog.exception.UserNotFoundException;
import br.com.unifalmg.blog.repository.UserRepository;
import br.com.unifalmg.blog.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService service;

    @Mock
    private UserRepository repository;

    @Test
    @DisplayName("#findById > When the id is null > Throw an exception")
    void findByIdWhenTheIdIsNullThrowAnException() {
        assertThrows(IllegalArgumentException.class, () ->
                service.findById(null));
    }

    @Test
    @DisplayName("#findById > When the id is not null > When a user is found > Return the user")
    void findByIdWhenTheIdIsNotNullWhenAUserIsFoundReturnTheUser() {
        when(repository.findById(1)).thenReturn(Optional.of(User.builder()
                        .id(1)
                        .name("Fellipe")
                        .username("felliperey")
                .build()));
        User response = service.findById(1);
        assertAll(
                () -> assertEquals(1, response.getId()),
                () -> assertEquals("Fellipe", response.getName()),
                () -> assertEquals("felliperey", response.getUsername())
        );
    }

    @Test
    @DisplayName("#findById > When the id is not null > When no user is found > Throw an exception")
    void findByIdWhenTheIdIsNotNullWhenNoUserIsFoundThrowAnException() {
        when(repository.findById(2)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () ->
                service.findById(2));
    }

    @Test
    @DisplayName("#getAllUsers > When there is no users registered > Return an empty list")
    void getAllUsersWhenThereAreNoRegisteredUsersReturnsAnEmptyList(){
        List<User> response = service.getAllUsers();
        Assertions.assertTrue(response.isEmpty());
    }

    @Test
    @DisplayName("#getAllUsers > When there are registered users > Returns the list of users")
    void getAllUsersWhenThereAreRegisteredUsersReturnsTheListOfUsers(){
        when(service.getAllUsers()).thenReturn(List.of(User.builder()
                .id(1)
                .name("Marcos Vyctor Fonseca Galupo")
                .username("marcosvgalupo")
                .email("teste.exemplo@gmail.com")
                .build()));
        List<User> response = service.getAllUsers();
        Assertions.assertAll(
                () -> Assertions.assertEquals(1,response.get(0).getId()),
                () -> Assertions.assertEquals("Marcos Vyctor Fonseca Galupo",response.get(0).getName()),
                () -> Assertions.assertEquals("marcosvgalupo",response.get(0).getUsername()),
                () -> Assertions.assertEquals("teste.exemplo@gmail.com", response.get(0).getEmail())

        );
    }

}
