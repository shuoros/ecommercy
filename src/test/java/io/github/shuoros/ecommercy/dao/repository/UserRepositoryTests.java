package io.github.shuoros.ecommercy.dao.repository;

import io.github.shuoros.ecommercy.AbstractContainerBaseTest;
import io.github.shuoros.ecommercy.dao.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserRepositoryTests extends AbstractContainerBaseTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void createUserMustCreateANewUserInDB() {
        // Arrange
        final User user = User.builder()//
                .name("John")//
                .lastName("Doe")//
                .email("johndoe@email.com")//
                .password("password")//
                .phoneNumber("123456789")//
                .build();

        // Act
        final User savedUser = userRepository.saveAndFlush(user);

        // Assert
        assertNotNull(savedUser);
        assertNotNull(savedUser.getId());
        assertNotNull(savedUser.getCreatedAt());
        assertNotNull(savedUser.getUpdatedAt());
    }

    @Test
    void ifEmailIsNullMustThrowException() {
        // Arrange
        final User user = User.builder()//
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
        final User user = User.builder()//
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
        final User user = User.builder()//
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
        final User user1 = User.builder()//
                .name("John")//
                .lastName("Doe")//
                .email("johndoe@email.com")//
                .password("password")//
                .phoneNumber("123456789")//
                .build();
        final User user2 = User.builder()//
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
        final User user1 = User.builder()//
                .name("John")//
                .lastName("Doe")//
                .email("johndoe@email.com")//
                .password("password")//
                .phoneNumber("123456789")//
                .build();
        final User user2 = User.builder()//
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
        final User user = User.builder()//
                .name("John")//
                .lastName("Doe")//
                .email("johndoe@email.com")//
                .password("password")//
                .phoneNumber("123456789")//
                .build();
        final User savedUser = userRepository.saveAndFlush(user);

        // Act
        final Optional<User> foundUser = userRepository.findById(savedUser.getId());

        // Assert
        assertTrue(foundUser.isPresent());
        assertEquals(foundUser.get().getId(), savedUser.getId());
    }

    @Test
    void findByEmailMustReturnUserWithSameEmail() {
        // Arrange
        final User user = User.builder()//
                .name("John")//
                .lastName("Doe")//
                .email("johndoe@email.com")//
                .password("password")//
                .phoneNumber("123456789")//
                .build();
        final User savedUser = userRepository.saveAndFlush(user);

        // Act
        final Optional<User> foundUser = userRepository.findByEmail(savedUser.getEmail());

        // Assert
        assertTrue(foundUser.isPresent());
        assertEquals(foundUser.get().getEmail(), savedUser.getEmail());
    }

    @Test
    void findAllMustReturnAllUsers() {
        // Arrange
        final User user1 = User.builder()//
                .name("John")//
                .lastName("Doe")//
                .email("johndoe@email.com")//
                .password("password")//
                .phoneNumber("123456789")//
                .build();
        final User user2 = User.builder()//
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
        final User user = User.builder()//
                .name("John")//
                .lastName("Doe")//
                .email("johndoe@email.com")//
                .password("password")//
                .phoneNumber("123456789")//
                .build();
        final User savedUser = userRepository.saveAndFlush(user);

        // Act
        savedUser.setName("Jane");
        final User updatedUser = userRepository.saveAndFlush(savedUser);

        // Assert
        assertEquals(updatedUser.getName(), "Jane");
        assertEquals(updatedUser.getId(), savedUser.getId());
        assertEquals(1, userRepository.count());
    }

    @Test
    void updateEmailOfAUserToEmailOfAExistingUserMustThrowException() {
        // Arrange
        final User user1 = User.builder()//
                .name("John")//
                .lastName("Doe")//
                .email("johndoe@email.com")//
                .password("password")//
                .phoneNumber("123456789")//
                .build();
        final User user2 = User.builder()//
                .name("John")//
                .lastName("Doe")//
                .email("doejoun@email.com")//
                .password("password")//
                .phoneNumber("987654321")//
                .build();
        userRepository.saveAndFlush(user1);
        final User savedUser = userRepository.saveAndFlush(user2);

        // Act & Assert
        savedUser.setEmail("johndoe@email.com");
        assertThrows(org.springframework.dao.DataIntegrityViolationException.class,//
                () -> userRepository.saveAndFlush(savedUser));
    }

    @Test
    void updatePhoneNumberOfAUserToPhoneNumberOfAExistingUserMustThrowException() {
        // Arrange
        final User user1 = User.builder()//
                .name("John")//
                .lastName("Doe")//
                .email("johndoe@email.com")//
                .password("password")//
                .phoneNumber("123456789")//
                .build();
        final User user2 = User.builder()//
                .name("John")//
                .lastName("Doe")//
                .email("doejoun@email.com")//
                .password("password")//
                .phoneNumber("987654321")//
                .build();
        userRepository.saveAndFlush(user1);
        final User savedUser = userRepository.saveAndFlush(user2);

        // Act & Assert
        savedUser.setPhoneNumber("123456789");
        assertThrows(org.springframework.dao.DataIntegrityViolationException.class,//
                () -> userRepository.saveAndFlush(savedUser));
    }

    @Test
    void deleteAUserMustDeleteTheUser() {
        // Arrange
        final User user = User.builder()//
                .name("John")//
                .lastName("Doe")//
                .email("johndoe@email.com")//
                .password("password")//
                .phoneNumber("123456789")//
                .build();
        final User savedUser = userRepository.saveAndFlush(user);

        // Act
        userRepository.delete(savedUser);

        // Assert
        assertEquals(0, userRepository.count());
        assertFalse(userRepository.findById(savedUser.getId()).isPresent());
    }

}
