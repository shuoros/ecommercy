package io.github.shuoros.ecommercy.dao.repository;

import io.github.shuoros.ecommercy.AbstractContainerBaseTest;
import io.github.shuoros.ecommercy.dao.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link org.springframework.data.jpa.repository.JpaRepository} User DAO interface.
 *
 * @author Soroush Shemshadi
 * @version 1.0.0
 * @see io.github.shuoros.ecommercy.dao.repository.UserRepository
 * @since 1.0.0
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserRepositoryTests extends AbstractContainerBaseTest {

    @Autowired
    private UserRepository userRepository;

    /**
     * Initialize the test environment.
     * Delete all users from the database before each test.
     */
    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
    }

    /**
     * Test that {@link io.github.shuoros.ecommercy.dao.repository.UserRepository#saveAndFlush(Object)}
     * When everything is OK:
     * -> Must save user return it.
     */
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

    /**
     * Test that {@link io.github.shuoros.ecommercy.dao.repository.UserRepository#saveAndFlush(Object)}
     * When email is null:
     * -> Must throw {@link org.springframework.dao.DataIntegrityViolationException}.
     */
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

    /**
     * Test that {@link io.github.shuoros.ecommercy.dao.repository.UserRepository#saveAndFlush(Object)}
     * When password is null:
     * -> Must throw {@link org.springframework.dao.DataIntegrityViolationException}.
     */
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

    /**
     * Test that {@link io.github.shuoros.ecommercy.dao.repository.UserRepository#saveAndFlush(Object)}
     * When name is null:
     * -> Must throw {@link org.springframework.dao.DataIntegrityViolationException}.
     */
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

    /**
     * Test that {@link io.github.shuoros.ecommercy.dao.repository.UserRepository#saveAndFlush(Object)}
     * When a user with the same email already exists:
     * -> Must throw {@link org.springframework.dao.DataIntegrityViolationException}.
     */
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

    /**
     * Test that {@link io.github.shuoros.ecommercy.dao.repository.UserRepository#saveAndFlush(Object)}
     * When a user with the same phone number already exists:
     * -> Must throw {@link org.springframework.dao.DataIntegrityViolationException}.
     */
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

    /**
     * Test that {@link io.github.shuoros.ecommercy.dao.repository.UserRepository#findById(Object)}
     * When find a user by valid id:
     * -> Must find user and return it.
     */
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

    /**
     * Test that {@link io.github.shuoros.ecommercy.dao.repository.UserRepository#findById(Object)}
     * When find a user by invalid id:
     * -> Returned user must be null.
     */
    @Test
    void findByIdWhenGivenIdIsWrongMustThrowException() {
        // Arrange
        final User user = User.builder()//
                .name("John")//
                .lastName("Doe")//
                .email("johndoe@email.com")//
                .password("password")//
                .phoneNumber("123456789")//
                .build();
        userRepository.saveAndFlush(user);

        final Optional<User> foundUser = userRepository.findById("WRONG_USER_ID");

        // Assert
        assertTrue(foundUser.isEmpty());
    }

    /**
     * Test that {@link io.github.shuoros.ecommercy.dao.repository.UserRepository#findByEmail(String)}
     * When find a user by valid email:
     * -> Must find user and return it.
     */
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

    /**
     * Test that {@link io.github.shuoros.ecommercy.dao.repository.UserRepository#findByEmail(String)}
     * When find a user by invalid email:
     * -> Must throw {@link org.springframework.dao.DataIntegrityViolationException}.
     */
    @Test
    void findByEmailWhenGivenEmailIsWrongMustThrowException() {
        // Arrange
        final User user = User.builder()//
                .name("John")//
                .lastName("Doe")//
                .email("johndoe@email.com")//
                .password("password")//
                .phoneNumber("123456789")//
                .build();

        userRepository.saveAndFlush(user);

        // Act
        final Optional<User> foundUser = userRepository.findByEmail("WRONG_EMAIL");

        // Assert
        assertTrue(foundUser.isEmpty());
    }

    /**
     * Test that {@link io.github.shuoros.ecommercy.dao.repository.UserRepository#findByPhoneNumber(String)}
     * When find a user by valid phone number:
     * -> Must find user and return it.
     */
    @Test
    void findByPhoneNumberMustReturnUserWithSamePhoneNumber() {
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
        final Optional<User> foundUser = userRepository.findByPhoneNumber(savedUser.getPhoneNumber());

        // Assert
        assertTrue(foundUser.isPresent());
        assertEquals(foundUser.get().getPhoneNumber(), savedUser.getPhoneNumber());
    }

    /**
     * Test that {@link io.github.shuoros.ecommercy.dao.repository.UserRepository#findByPhoneNumber(String)}
     * When find a user by invalid phone number:
     * -> Must throw {@link org.springframework.dao.DataIntegrityViolationException}.
     */
    @Test
    void findByPhoneNumberWhenGivenEmailIsWrongMustThrowException() {
        // Arrange
        final User user = User.builder()//
                .name("John")//
                .lastName("Doe")//
                .email("johndoe@email.com")//
                .password("password")//
                .phoneNumber("123456789")//
                .build();

        userRepository.saveAndFlush(user);

        // Act
        final Optional<User> foundUser = userRepository.findByPhoneNumber("WRONG_PHONE_NUMBER");

        // Assert
        assertTrue(foundUser.isEmpty());
    }

    /**
     * Test that {@link io.github.shuoros.ecommercy.dao.repository.UserRepository#findAll()}
     * When get all users:
     * -> Must return all users.
     */
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

        // Act
        List<User> users = userRepository.findAll();

        // Act & Assert
        assertEquals(2, users.size());
        assertEquals(user1.getId(), users.get(0).getId());
        assertEquals(user2.getId(), users.get(1).getId());
    }

    /**
     * Test that {@link io.github.shuoros.ecommercy.dao.repository.UserRepository#count()}
     * When get count of users:
     * -> Must return correct count.
     */
    @Test
    void countUsersMustReturnCountOfUsers() {
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

    /**
     * Test that {@link io.github.shuoros.ecommercy.dao.repository.UserRepository#saveAndFlush(Object)} (for update)
     * When update a user:
     * -> Must update user and return it.
     */
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

    /**
     * Test that {@link io.github.shuoros.ecommercy.dao.repository.UserRepository#saveAndFlush(Object)} (for update)
     * When a user with the same email already exists:
     * -> Must throw {@link org.springframework.dao.DataIntegrityViolationException}.
     */
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

    /**
     * Test that {@link io.github.shuoros.ecommercy.dao.repository.UserRepository#saveAndFlush(Object)} (for update)
     * When a user with the same phone number already exists:
     * -> Must throw {@link org.springframework.dao.DataIntegrityViolationException}.
     */
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

    /**
     * Test that {@link io.github.shuoros.ecommercy.dao.repository.UserRepository#delete(Object)}
     * When delete a user:
     * -> Must update user and return it.
     */
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
