package io.github.shuoros.ecommercy.dao.repository;

import io.github.shuoros.ecommercy.dao.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    void createUserMustCreateANewUserInDB() {
        // Arrange
        User user = User.builder()//
                .name("John")//
                .lastName("Doe")//
                .email("johndoe@email.com")//
                .password("password")//
                .phoneNumber("123456789")//
                .build();

        // Act
        User savedUser = userRepository.saveAndFlush(user);

        // Assert
        assertNotNull(savedUser);
        assertNotNull(savedUser.getId());
    }

    @Test
    void ifEmailIsNullMustThrowException() {
        // Arrange
        User user = User.builder()//
                .name("John")//
                .lastName("Doe")//
                .password("password")//
                .phoneNumber("123456789")//
                .build();

        // Act & Assert
        assertThrows(org.springframework.dao.DataIntegrityViolationException.class,//
                () -> userRepository.saveAndFlush(user));
    }

    @Test
    void ifPasswordIsNullMustThrowException() {
        // Arrange
        User user = User.builder()//
                .name("John")//
                .lastName("Doe")//
                .email("johndoe@email.com")//
                .phoneNumber("123456789")//
                .build();

        // Act & Assert
        assertThrows(org.springframework.dao.DataIntegrityViolationException.class,//
                () -> userRepository.saveAndFlush(user));
    }

    @Test
    void ifNameIsNullMustThrowException() {
        // Arrange
        User user = User.builder()//
                .lastName("Doe")//
                .email("johndoe@email.com")//
                .password("password")//
                .phoneNumber("123456789")//
                .build();

        // Act & Assert
        assertThrows(org.springframework.dao.DataIntegrityViolationException.class,//
                () -> userRepository.saveAndFlush(user));
    }

    @Test
    void ifAUserExistWithGivenEmailMustThrowException() {
        // Arrange
        User user1 = User.builder()//
                .name("John")//
                .lastName("Doe")//
                .email("johndoe@email.com")//
                .password("password")//
                .phoneNumber("123456789")//
                .build();
        User user2 = User.builder()//
                .name("John")//
                .lastName("Doe")//
                .email("johndoe@email.com")//
                .password("password")//
                .phoneNumber("987654321")//
                .build();
        userRepository.saveAndFlush(user1);

        // Act & Assert
        assertThrows(org.springframework.dao.DataIntegrityViolationException.class,//
                () -> userRepository.saveAndFlush(user2));
    }

    @Test
    void ifAUserExistWithGivenPhoneNumberMustThrowException() {
        // Arrange
        User user1 = User.builder()//
                .name("John")//
                .lastName("Doe")//
                .email("johndoe@email.com")//
                .password("password")//
                .phoneNumber("123456789")//
                .build();
        User user2 = User.builder()//
                .name("John")//
                .lastName("Doe")//
                .email("doejoun@email.com")//
                .password("password")//
                .phoneNumber("123456789")//
                .build();
        userRepository.saveAndFlush(user1);

        // Act & Assert
        assertThrows(org.springframework.dao.DataIntegrityViolationException.class,//
                () -> userRepository.saveAndFlush(user2));
    }

    @Test
    void findByIdMustReturnUserWithSameId() {
        // Arrange
        User user = User.builder()//
                .name("John")//
                .lastName("Doe")//
                .email("johndoe@email.com")//
                .password("password")//
                .phoneNumber("123456789")//
                .build();
        User savedUser = userRepository.saveAndFlush(user);

        // Act
        Optional<User> foundUser = userRepository.findById(savedUser.getId());

        // Assert
        assertTrue(foundUser.isPresent());
        assertEquals(foundUser.get().getId(), savedUser.getId());
    }

    @Test
    void findByEmailMustReturnUserWithSameEmail() {
        // Arrange
        User user = User.builder()//
                .name("John")//
                .lastName("Doe")//
                .email("johndoe@email.com")//
                .password("password")//
                .phoneNumber("123456789")//
                .build();
        User savedUser = userRepository.saveAndFlush(user);

        // Act
        Optional<User> foundUser = userRepository.findByEmail(savedUser.getEmail());

        // Assert
        assertTrue(foundUser.isPresent());
        assertEquals(foundUser.get().getEmail(), savedUser.getEmail());
    }

    @Test
    void findAllMustReturnAllUsers() {
        // Arrange
        User user1 = User.builder()//
                .name("John")//
                .lastName("Doe")//
                .email("johndoe@email.com")//
                .password("password")//
                .phoneNumber("123456789")//
                .build();
        User user2 = User.builder()//
                .name("John")//
                .lastName("Doe")//
                .email("doejoun@email.com")//
                .password("password")//
                .phoneNumber("987654321")//
                .build();
        userRepository.saveAndFlush(user1);
        userRepository.saveAndFlush(user2);

        // Act & Assert
        assertEquals(2, userRepository.count());
    }

    @Test
    void updateAUserMustUpdateTheUserNotSaveAnotherUser() {
        // Arrange
        User user = User.builder()//
                .name("John")//
                .lastName("Doe")//
                .email("johndoe@email.com")//
                .password("password")//
                .phoneNumber("123456789")//
                .build();
        User savedUser = userRepository.saveAndFlush(user);

        // Act
        savedUser.setName("Jane");
        User updatedUser = userRepository.saveAndFlush(savedUser);

        // Assert
        assertEquals(updatedUser.getName(), "Jane");
        assertEquals(updatedUser.getId(), savedUser.getId());
        assertEquals(1, userRepository.count());
    }

    @Test
    void deleteAUserMustDeleteTheUser() {
        // Arrange
        User user = User.builder()//
                .name("John")//
                .lastName("Doe")//
                .email("johndoe@email.com")//
                .password("password")//
                .phoneNumber("123456789")//
                .build();
        User savedUser = userRepository.saveAndFlush(user);

        // Act
        userRepository.delete(savedUser);

        // Assert
        assertEquals(0, userRepository.count());
        assertFalse(userRepository.findById(savedUser.getId()).isPresent());
    }

}
