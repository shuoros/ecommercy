package io.github.shuoros.ecommercy.dao.repository;

import io.github.shuoros.ecommercy.AbstractContainerBaseTest;
import io.github.shuoros.ecommercy.dao.User;
import io.github.shuoros.ecommercy.dao.event.CustomerEventHandler;
import io.github.shuoros.ecommercy.security.jwt.Jwt;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit tests for {@link org.springframework.data.rest.core} ("Spring-Data-Rest") endpoint of User DAO.
 *
 * @author Soroush Shemshadi
 * @version 1.0.0
 * @see CustomerRepository
 * @since 1.0.0
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserDataRestTests extends AbstractContainerBaseTest {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private Jwt jwtTokenUtil;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerRepository userRepository;

    /**
     * Initialize the test environment.
     * Delete all users from the database before each test.
     */
    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
    }

    /**
     * Test the endpoint "/user" with the HTTP method "POST".
     * When everything is OK:
     * -> Must create a new user in the database and return a 201 status code and created user.
     * !Endpoint allowed for anyone!
     *
     * @throws Exception Any exception that may occur during the test.
     */
    @Test
    void signUpUserMustCreateANewUserInDB() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");

        // Act
        ResultActions response = mockMvc.perform(//
                post("/user")//
                        .contentType(MediaType.APPLICATION_JSON)//
                        .accept(MediaType.APPLICATION_JSON)//
                        .content(content.toString()));

        // Assert
        response.andDo(print()).//
                andExpect(status().isCreated())//
                .andExpect(jsonPath("$.name",//
                        is(content.getString("name"))))//
                .andExpect(jsonPath("$.lastName",//
                        is(content.getString("lastName"))))//
                .andExpect(jsonPath("$.email",//
                        is(content.getString("email"))))//
                .andExpect(jsonPath("$.phoneNumber",//
                        is(content.getString("phoneNumber"))))//
                .andExpect(jsonPath("$._links").exists());
    }

    /**
     * Test the endpoint "/user" with the HTTP method "POST".
     * When email field is missing:
     * -> Must return a 422 status code.
     * !Endpoint allowed for anyone!
     *
     * @throws Exception Any exception that may occur during the test.
     * @see CustomerEventHandler#handleBeforeCreate(User)
     */
    @Test
    void signUpUserWithMissedEmailFieldMustReturn422() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                //.put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");

        // Act
        ResultActions response = mockMvc.perform(//
                post("/user")//
                        .contentType(MediaType.APPLICATION_JSON)//
                        .accept(MediaType.APPLICATION_JSON)//
                        .content(content.toString()));

        // Assert
        response.andDo(print()).//
                andExpect(status().isUnprocessableEntity())//
                .andExpect(jsonPath("$.message",//
                        is("Missing name, email or password!")));
    }

    /**
     * Test the endpoint "/user" with the HTTP method "POST".
     * When name field is missing:
     * -> Must return a 422 status code.
     * !Endpoint allowed for anyone!
     *
     * @throws Exception Any exception that may occur during the test.
     * @see CustomerEventHandler#handleBeforeCreate(User)
     */
    @Test
    void signUpUserWithMissedNameFieldMustReturn422() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                //.put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");

        // Act
        ResultActions response = mockMvc.perform(//
                post("/user")//
                        .contentType(MediaType.APPLICATION_JSON)//
                        .accept(MediaType.APPLICATION_JSON)//
                        .content(content.toString()));

        // Assert
        response.andDo(print()).//
                andExpect(status().isUnprocessableEntity())//
                .andExpect(jsonPath("$.message",//
                        is("Missing name, email or password!")));
    }

    /**
     * Test the endpoint "/user" with the HTTP method "POST".
     * When password field is missing:
     * -> Must return a 422 status code.
     * !Endpoint allowed for anyone!
     *
     * @throws Exception Any exception that may occur during the test.
     * @see CustomerEventHandler#handleBeforeCreate(User)
     */
    @Test
    void signUpUserWithMissedPasswordFieldMustReturn422() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                //.put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");

        // Act
        ResultActions response = mockMvc.perform(//
                post("/user")//
                        .contentType(MediaType.APPLICATION_JSON)//
                        .accept(MediaType.APPLICATION_JSON)//
                        .content(content.toString()));

        // Assert
        response.andDo(print()).//
                andExpect(status().isUnprocessableEntity())//
                .andExpect(jsonPath("$.message",//
                        is("Missing name, email or password!")));
    }

    /**
     * Test the endpoint "/user" with the HTTP method "POST".
     * When given password is not strong enough:
     * -> Must return a 422 status code.
     * !Endpoint allowed for anyone!
     *
     * @throws Exception Any exception that may occur during the test.
     * @see CustomerEventHandler#isValidPassword(String)
     */
    @Test
    void signUpUserWithSimplePasswordFieldMustReturn422() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "123456789")//
                .put("phoneNumber", "123456789");

        // Act
        ResultActions response = mockMvc.perform(//
                post("/user")//
                        .contentType(MediaType.APPLICATION_JSON)//
                        .accept(MediaType.APPLICATION_JSON)//
                        .content(content.toString()));

        // Assert
        response.andDo(print()).//
                andExpect(status().isUnprocessableEntity())//
                .andExpect(jsonPath("$.message",//
                        is("Your password is not compatible! Consider choosing password with 1-at least 8 characters and at most 20 characters. 2-at least one digit. 3-at least one upper case alphabet. 4-at least one lower case alphabet. 5-at least one special character which includes !@#$%&*()-+=^.")));
    }

    /**
     * Test the endpoint "/user" with the HTTP method "POST".
     * When a user with given email is already exist:
     * -> Must return a 409 status code.
     * !Endpoint allowed for anyone!
     *
     * @throws Exception Any exception that may occur during the test.
     * @see CustomerEventHandler#handleBeforeCreate(User)
     */
    @Test
    void signUpUserWhenAUserWithGivenEmailExistMustReturn409() throws Exception {
        // Arrange
        final JSONObject content1 = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");
        final JSONObject content2 = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "987654321");
        mockMvc.perform(post("/user")//
                .contentType(MediaType.APPLICATION_JSON)//
                .accept(MediaType.APPLICATION_JSON)//
                .content(content1.toString()));

        // Act
        ResultActions response = mockMvc.perform(//
                post("/user")//
                        .contentType(MediaType.APPLICATION_JSON)//
                        .accept(MediaType.APPLICATION_JSON)//
                        .content(content2.toString()));

        // Assert
        response.andDo(print()).//
                andExpect(status().isConflict())//
                .andExpect(jsonPath("$.message",//
                        is("User with such email already exists!")));
    }

    /**
     * Test the endpoint "/user" with the HTTP method "POST".
     * When a user with given phone number is already exist:
     * -> Must return a 409 status code.
     * !Endpoint allowed for anyone!
     *
     * @throws Exception Any exception that may occur during the test.
     * @see CustomerEventHandler#handleBeforeCreate(User)
     */
    @Test
    void signUpUserWhenAUserWithGivenPhoneNumberExistMustReturn409() throws Exception {
        // Arrange
        final JSONObject content1 = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");
        final JSONObject content2 = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "doejohn@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");
        mockMvc.perform(post("/user")//
                .contentType(MediaType.APPLICATION_JSON)//
                .accept(MediaType.APPLICATION_JSON)//
                .content(content1.toString()));

        // Act
        ResultActions response = mockMvc.perform(//
                post("/user")//
                        .contentType(MediaType.APPLICATION_JSON)//
                        .accept(MediaType.APPLICATION_JSON)//
                        .content(content2.toString()));

        // Assert
        response.andDo(print()).//
                andExpect(status().isConflict())//
                .andExpect(jsonPath("$.message",//
                        is("User with such phone number already exists!")));
    }

    /**
     * Test the endpoint "/user/USER_ID/basket" with the HTTP method "GET".
     * When a user created successfully then a basket must be created for him/her.
     * -> Must return a 200 status code and the desired basket.
     * !Endpoint allowed for owner user and admin!
     *
     * @throws Exception Any exception that may occur during the test.
     */
    @Test
    void signUpUserMustCreateABasketForNewUserInDB() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");
        final JSONObject userLinks = new JSONObject(mockMvc.perform(post("/user")//
                        .contentType(MediaType.APPLICATION_JSON)//
                        .accept(MediaType.APPLICATION_JSON)//
                        .content(content.toString()))//
                .andReturn().getResponse().getContentAsString()).getJSONObject("_links");

        // Act
        ResultActions response = mockMvc.perform(//
                get(//
                        userLinks.getJSONObject("basket").getString("href"))//
                        .header("Authorization",//
                                "Bearer" + authenticateUser(//
                                        content.getString("email"), content.getString("password")))//
                        .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andDo(print()).//
                andExpect(status().isOk())//
                .andExpect(jsonPath("$.totalPrice",//
                        is(0.0)))//
                .andExpect(jsonPath("$.discount",//
                        is(0.0)));
    }

    /**
     * Test the endpoint "/user/ANOTHER_USER'S_ID/basket" with the HTTP method "GET".
     * When a user tries to get a basket of another user:
     * -> Must return a 403 status code.
     * !Endpoint allowed for owner user and admin!
     *
     * @throws Exception Any exception that may occur during the test.
     * @see io.github.shuoros.ecommercy.security.filter.RestDataSecurityFilter#doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)
     */
    @Test
    void getBasketOfUserWithAnotherUserIdMustReturn403() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");
        mockMvc.perform(post("/user")//
                .contentType(MediaType.APPLICATION_JSON)//
                .accept(MediaType.APPLICATION_JSON)//
                .content(content.toString()));

        // Act
        ResultActions response = mockMvc.perform(//
                get(//
                        "/user/ANOTHER_USERS'S_ID/basket")//
                        .header("Authorization",//
                                "Bearer" + authenticateUser(//
                                        content.getString("email"), content.getString("password")))//
                        .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andDo(print()).//
                andExpect(status().isForbidden())//
                .andExpect(jsonPath("$.message",//
                        is("You are not allowed to access this resource!")));
    }

    /**
     * Test the endpoint "/user/SOME_USER'S_ID/basket" with the HTTP method "GET".
     * When an unauthorized client tries to get a basket of some user:
     * -> Must return a 401 status code.
     * !Endpoint allowed for owner user and admin!
     *
     * @throws Exception Any exception that may occur during the test.
     * @see io.github.shuoros.ecommercy.security.SecurityConfig#configure(HttpSecurity)
     */
    @Test
    void getBasketOfUserWithAnUnAuthorizedClientMustReturn401() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");
        mockMvc.perform(post("/user")//
                .contentType(MediaType.APPLICATION_JSON)//
                .accept(MediaType.APPLICATION_JSON)//
                .content(content.toString()));

        // Act
        ResultActions response = mockMvc.perform(//
                get(//
                        "/user/SOME_USERS'S_ID/basket")//
                        .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andDo(print()).//
                andExpect(status().isUnauthorized());
    }

    /**
     * Test the endpoint "/user/USER'S_ID/basket" with the HTTP method "GET".
     * When admin wants to get basket of some user:
     * -> Must return a 200 status code and the desired basket.
     * !Endpoint allowed for owner user and admin!
     *
     * @throws Exception Any exception that may occur during the test.
     */
    @Test
    void getBasketOfUserWithAdminAuthorityMustReturnBasket() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");
        final JSONObject userLinks = new JSONObject(mockMvc.perform(post("/user")//
                        .contentType(MediaType.APPLICATION_JSON)//
                        .accept(MediaType.APPLICATION_JSON)//
                        .content(content.toString()))//
                .andReturn().getResponse().getContentAsString()).getJSONObject("_links");

        // Act
        ResultActions response = mockMvc.perform(//
                get(//
                        userLinks.getJSONObject("basket").getString("href"))//
                        .header("Authorization",//
                                "Bearer" + authenticateAdmin())//
                        .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andDo(print()).//
                andExpect(status().isOk())//
                .andExpect(jsonPath("$.totalPrice",//
                        is(0.0)))//
                .andExpect(jsonPath("$.discount",//
                        is(0.0)));
    }

    /**
     * Test the endpoint "/user/WRONG_USER_ID/basket" with the HTTP method "GET".
     * When admin wants to get basket of some user:
     * -> Must return a 404 status code.
     * !Endpoint allowed for owner user and admin!
     *
     * @throws Exception Any exception that may occur during the test.
     */
    @Test
    void getBasketWithWrongUserIDAndWithAdminAuthorityMustReturn404() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");
        mockMvc.perform(post("/user")//
                .contentType(MediaType.APPLICATION_JSON)//
                .accept(MediaType.APPLICATION_JSON)//
                .content(content.toString()));

        // Act
        ResultActions response = mockMvc.perform(//
                get(//
                        "/user/WRONG_USER_ID/basket")//
                        .header("Authorization",//
                                "Bearer" + authenticateAdmin())//
                        .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andDo(print()).//
                andExpect(status().isNotFound());
    }

    /**
     * Test the endpoint "/user/USER_ID" with the HTTP method "GET".
     * When user wants to ger him/herself:
     * -> Must return a 200 status code and user.
     * !Endpoint allowed for owner user and admin!
     *
     * @throws Exception Any exception that may occur during the test.
     */
    @Test
    void getUserMustReturnCorrectUser() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");
        final JSONObject userLinks = new JSONObject(mockMvc.perform(post("/user")//
                        .contentType(MediaType.APPLICATION_JSON)//
                        .accept(MediaType.APPLICATION_JSON)//
                        .content(content.toString()))//
                .andReturn().getResponse().getContentAsString()).getJSONObject("_links");

        // Act
        ResultActions response = mockMvc.perform(//
                get(//
                        userLinks.getJSONObject("self").getString("href"))//
                        .header("Authorization",//
                                "Bearer" + authenticateUser(//
                                        content.getString("email"), content.getString("password")))//
                        .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andDo(print()).//
                andExpect(status().isOk())//
                .andExpect(jsonPath("$.name",//
                        is(content.getString("name"))))//
                .andExpect(jsonPath("$.lastName",//
                        is(content.getString("lastName"))))//
                .andExpect(jsonPath("$.email",//
                        is(content.getString("email"))))//
                .andExpect(jsonPath("$.phoneNumber",//
                        is(content.getString("phoneNumber"))))//
                .andExpect(jsonPath("$._links").exists());
    }

    /**
     * Test the endpoint "/user/ANOTHER_USER'S_ID" with the HTTP method "GET".
     * When a user tries to get a basket of another user:
     * -> Must return a 403 status code.
     * !Endpoint allowed for owner user and admin!
     *
     * @throws Exception Any exception that may occur during the test.
     * @see io.github.shuoros.ecommercy.security.filter.RestDataSecurityFilter#doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)
     */
    @Test
    void getUserWithAnotherUserIdMustReturn403() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");
        mockMvc.perform(post("/user")//
                .contentType(MediaType.APPLICATION_JSON)//
                .accept(MediaType.APPLICATION_JSON)//
                .content(content.toString()));

        // Act
        ResultActions response = mockMvc.perform(//
                get(//
                        "/user/ANOTHER_USERS'S_ID")//
                        .header("Authorization",//
                                "Bearer" + authenticateUser(//
                                        content.getString("email"), content.getString("password")))//
                        .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andDo(print()).//
                andExpect(status().isForbidden())//
                .andExpect(jsonPath("$.message",//
                        is("You are not allowed to access this resource!")));
    }

    /**
     * Test the endpoint "/user/SOME_USER'S_ID" with the HTTP method "GET".
     * When an unauthorized client tries to get a basket of some user:
     * -> Must return a 401 status code.
     * !Endpoint allowed for owner user and admin!
     *
     * @throws Exception Any exception that may occur during the test.
     * @see io.github.shuoros.ecommercy.security.SecurityConfig#configure(HttpSecurity)
     */
    @Test
    void getUserWithAnUnAuthorizedClientMustReturn401() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");
        mockMvc.perform(post("/user")//
                .contentType(MediaType.APPLICATION_JSON)//
                .accept(MediaType.APPLICATION_JSON)//
                .content(content.toString()));

        // Act
        ResultActions response = mockMvc.perform(//
                get(//
                        "/user/SOME_USERS'S_ID")//
                        .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andDo(print()).//
                andExpect(status().isUnauthorized());
    }

    /**
     * Test the endpoint "/user/USER_ID" with the HTTP method "GET".
     * When admin wants to get a user:
     * -> Must return a 200 status code and user.
     * !Endpoint allowed for owner user and admin!
     *
     * @throws Exception Any exception that may occur during the test.
     */
    @Test
    void getUserWithAdminAuthorityMustReturnCorrectUser() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");
        final JSONObject userLinks = new JSONObject(mockMvc.perform(post("/user")//
                        .contentType(MediaType.APPLICATION_JSON)//
                        .accept(MediaType.APPLICATION_JSON)//
                        .content(content.toString()))//
                .andReturn().getResponse().getContentAsString()).getJSONObject("_links");

        // Act
        ResultActions response = mockMvc.perform(//
                get(//
                        userLinks.getJSONObject("self").getString("href"))//
                        .header("Authorization",//
                                "Bearer" + authenticateAdmin())//
                        .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andDo(print()).//
                andExpect(status().isOk())//
                .andExpect(jsonPath("$.name",//
                        is(content.getString("name"))))//
                .andExpect(jsonPath("$.lastName",//
                        is(content.getString("lastName"))))//
                .andExpect(jsonPath("$.email",//
                        is(content.getString("email"))))//
                .andExpect(jsonPath("$.phoneNumber",//
                        is(content.getString("phoneNumber"))))//
                .andExpect(jsonPath("$._links").exists());
    }

    /**
     * Test the endpoint "/user/WRONG_USER_ID" with the HTTP method "GET".
     * When admin wants to get a wrong user:
     * -> Must return a 404 status code.
     * !Endpoint allowed for owner user and admin!
     *
     * @throws Exception Any exception that may occur during the test.
     */
    @Test
    void getAWrongUserWithAdminAuthorityMustReturn404() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");
        mockMvc.perform(post("/user")//
                .contentType(MediaType.APPLICATION_JSON)//
                .accept(MediaType.APPLICATION_JSON)//
                .content(content.toString()));

        // Act
        ResultActions response = mockMvc.perform(//
                get(//
                        "/user/WRONG_USER_ID")//
                        .header("Authorization",//
                                "Bearer" + authenticateAdmin())//
                        .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andDo(print()).//
                andExpect(status().isNotFound());
    }

    /**
     * Test the endpoint "/user" with the HTTP method "GET".
     * When admin wants to get list of all users:
     * -> Must return a 200 status code and list of all users.
     * !Endpoint allowed just for admin!
     *
     * @throws Exception Any exception that may occur during the test.
     */
    @Test
    void getAllUsersMustReturnAllUsers() throws Exception {
        // Arrange
        final JSONObject content1 = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");
        final JSONObject content2 = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "doejohn@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "987654321");
        mockMvc.perform(post("/user")//
                .contentType(MediaType.APPLICATION_JSON)//
                .accept(MediaType.APPLICATION_JSON)//
                .content(content1.toString()));
        mockMvc.perform(post("/user")//
                .contentType(MediaType.APPLICATION_JSON)//
                .accept(MediaType.APPLICATION_JSON)//
                .content(content2.toString()));

        // Act
        ResultActions response = mockMvc.perform(//
                get("/user")//
                        .header("Authorization",//
                                "Bearer" + authenticateAdmin())//
                        .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andDo(print()).//
                andExpect(status().isOk())//
                .andExpect(jsonPath("$.page.totalElements",//
                        is(2)))//
                .andExpect(jsonPath("$._embedded.user")//
                        .isArray());
    }

    /**
     * Test the endpoint "/user" with the HTTP method "GET".
     * When a user tries to get list of all users:
     * -> Must return a 403 status code.
     * !Endpoint allowed just for admin!
     *
     * @throws Exception Any exception that may occur during the test.
     * @see io.github.shuoros.ecommercy.security.SecurityConfig#configure(HttpSecurity)
     */
    @Test
    void getAllUsersWithUserAuthorityMustReturn403() throws Exception {
        // Arrange
        final JSONObject content1 = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");
        final JSONObject content2 = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "doejohn@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "987654321");
        mockMvc.perform(post("/user")//
                .contentType(MediaType.APPLICATION_JSON)//
                .accept(MediaType.APPLICATION_JSON)//
                .content(content1.toString()));
        mockMvc.perform(post("/user")//
                .contentType(MediaType.APPLICATION_JSON)//
                .accept(MediaType.APPLICATION_JSON)//
                .content(content2.toString()));

        // Act
        ResultActions response = mockMvc.perform(//
                get("/user")//
                        .header("Authorization",//
                                "Bearer" + authenticateUser(//
                                        content1.getString("email"), content1.getString("password")))//
                        .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andDo(print()).//
                andExpect(status().isForbidden());
    }

    /**
     * Test the endpoint "/user" with the HTTP method "GET".
     * When an unauthorized tries to get list of all users:
     * -> Must return a 401 status code.
     * !Endpoint allowed just for admin!
     *
     * @throws Exception Any exception that may occur during the test.
     * @see io.github.shuoros.ecommercy.security.SecurityConfig#configure(HttpSecurity)
     */
    @Test
    void getAllUsersWithAnUnAuthorizedClientMustReturn401() throws Exception {
        // Arrange
        final JSONObject content1 = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");
        final JSONObject content2 = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "doejohn@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "987654321");
        mockMvc.perform(post("/user")//
                .contentType(MediaType.APPLICATION_JSON)//
                .accept(MediaType.APPLICATION_JSON)//
                .content(content1.toString()));
        mockMvc.perform(post("/user")//
                .contentType(MediaType.APPLICATION_JSON)//
                .accept(MediaType.APPLICATION_JSON)//
                .content(content2.toString()));

        // Act
        ResultActions response = mockMvc.perform(//
                get("/user")//
                        .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andDo(print()).//
                andExpect(status().isUnauthorized());
    }

    /**
     * Test the endpoint "/user/USER_ID/addresses" with the HTTP method "GET".
     * When a user wants to get its addresses.
     * -> Must return a 200 status code and list of addresses.
     * !Endpoint allowed for owner user and admin!
     *
     * @throws Exception Any exception that may occur during the test.
     */
    @Test
    void getAddressesOfAUserMustReturnAllAddresses() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");
        final JSONObject userLinks = new JSONObject(mockMvc.perform(post("/user")//
                        .contentType(MediaType.APPLICATION_JSON)//
                        .accept(MediaType.APPLICATION_JSON)//
                        .content(content.toString()))//
                .andReturn().getResponse().getContentAsString()).getJSONObject("_links");

        // Act
        ResultActions response = mockMvc.perform(//
                get(//
                        userLinks.getJSONObject("addresses").getString("href"))//
                        .header("Authorization",//
                                "Bearer" + authenticateUser(//
                                        content.getString("email"), content.getString("password")))//
                        .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andDo(print()).//
                andExpect(status().isOk())//
                .andExpect(jsonPath("$._embedded.address").isArray())//
                .andExpect(jsonPath("$._embedded.address").isEmpty());
    }

    /**
     * Test the endpoint "/user/ANOTHER_USERS'S_ID/addresses" with the HTTP method "GET".
     * When a user tries to get another user's addresses:
     * -> Must return a 403 status code.
     * !Endpoint allowed for owner user and admin!
     *
     * @throws Exception Any exception that may occur during the test.
     * @see io.github.shuoros.ecommercy.security.filter.RestDataSecurityFilter#doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)
     */
    @Test
    void getAddressesOfAUserWithAnotherUserIdMustReturn403() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");
        mockMvc.perform(post("/user")//
                .contentType(MediaType.APPLICATION_JSON)//
                .accept(MediaType.APPLICATION_JSON)//
                .content(content.toString()));

        // Act
        ResultActions response = mockMvc.perform(//
                get(//
                        "/user/ANOTHER_USERS'S_ID/addresses")//
                        .header("Authorization",//
                                "Bearer" + authenticateUser(//
                                        content.getString("email"), content.getString("password")))//
                        .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andDo(print()).//
                andExpect(status().isForbidden())//
                .andExpect(jsonPath("$.message",//
                        is("You are not allowed to access this resource!")));
    }

    /**
     * Test the endpoint "/user/SOME_USERS'S_ID/addresses" with the HTTP method "GET".
     * When an unauthorized client tries to get some user's addresses:
     * -> Must return a 401 status code.
     * !Endpoint allowed for owner user and admin!
     *
     * @throws Exception Any exception that may occur during the test.
     * @see io.github.shuoros.ecommercy.security.SecurityConfig#configure(HttpSecurity)
     */
    @Test
    void getAddressesOfSomeUserWithUnauthorizedClientMustReturn401() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");
        mockMvc.perform(post("/user")//
                .contentType(MediaType.APPLICATION_JSON)//
                .accept(MediaType.APPLICATION_JSON)//
                .content(content.toString()));

        // Act
        ResultActions response = mockMvc.perform(//
                get(//
                        "/user/SOME_USERS'S_ID/addresses")//
                        .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andDo(print()).//
                andExpect(status().isUnauthorized());
    }

    /**
     * Test the endpoint "/user/USER_ID/addresses" with the HTTP method "GET".
     * When an admin wants to get a user's addresses.
     * -> Must return a 200 status code and list of addresses.
     * !Endpoint allowed for owner user and admin!
     *
     * @throws Exception Any exception that may occur during the test.
     */
    @Test
    void getAddressesOfAUserWithAdminAuthorityMustReturnAllAddresses() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");
        final JSONObject userLinks = new JSONObject(mockMvc.perform(post("/user")//
                        .contentType(MediaType.APPLICATION_JSON)//
                        .accept(MediaType.APPLICATION_JSON)//
                        .content(content.toString()))//
                .andReturn().getResponse().getContentAsString()).getJSONObject("_links");

        // Act
        ResultActions response = mockMvc.perform(//
                get(//
                        userLinks.getJSONObject("addresses").getString("href"))//
                        .header("Authorization",//
                                "Bearer" + authenticateAdmin())//
                        .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andDo(print()).//
                andExpect(status().isOk())//
                .andExpect(jsonPath("$._embedded.address").isArray())//
                .andExpect(jsonPath("$._embedded.address").isEmpty());
    }

    /**
     * Test the endpoint "/user/WRONG_USER_ID/addresses" with the HTTP method "GET".
     * When an admin wants to get a wrong user's addresses.
     * -> Must return a 404 status code.
     * !Endpoint allowed for owner user and admin!
     *
     * @throws Exception Any exception that may occur during the test.
     */
    @Test
    void getAddressesOfAWrongUserWithAdminAuthorityMustReturn404() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");
        mockMvc.perform(post("/user")//
                .contentType(MediaType.APPLICATION_JSON)//
                .accept(MediaType.APPLICATION_JSON)//
                .content(content.toString()));

        // Act
        ResultActions response = mockMvc.perform(//
                get(//
                        "/user/WRONG_USER_ID/addresses")//
                        .header("Authorization",//
                                "Bearer" + authenticateAdmin())//
                        .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andDo(print()).//
                andExpect(status().isNotFound());
    }

    /**
     * Test the endpoint "/user/USER_ID/comments" with the HTTP method "GET".
     * When a user wants to get its comments.
     * -> Must return a 200 status code and list of comments.
     * !Endpoint allowed for owner user and admin!
     *
     * @throws Exception Any exception that may occur during the test.
     */
    @Test
    void getCommentsOfAUserMustReturnAllComments() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");
        final JSONObject userLinks = new JSONObject(mockMvc.perform(post("/user")//
                        .contentType(MediaType.APPLICATION_JSON)//
                        .accept(MediaType.APPLICATION_JSON)//
                        .content(content.toString()))//
                .andReturn().getResponse().getContentAsString()).getJSONObject("_links");

        // Act
        ResultActions response = mockMvc.perform(//
                get(//
                        userLinks.getJSONObject("comments").getString("href"))//
                        .header("Authorization",//
                                "Bearer" + authenticateUser(//
                                        content.getString("email"), content.getString("password")))//
                        .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andDo(print()).//
                andExpect(status().isOk())//
                .andExpect(jsonPath("$._embedded.comment").isArray())//
                .andExpect(jsonPath("$._embedded.comment").isEmpty());
    }

    /**
     * Test the endpoint "/user/ANOTHER_USERS'S_ID/comments" with the HTTP method "GET".
     * When a user tries to get another user's comments:
     * -> Must return a 403 status code.
     * !Endpoint allowed for owner user and admin!
     *
     * @throws Exception Any exception that may occur during the test.
     * @see io.github.shuoros.ecommercy.security.filter.RestDataSecurityFilter#doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)
     */
    @Test
    void getCommentsOfAUserWithAnotherUserIdMustReturn403() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");
        mockMvc.perform(post("/user")//
                .contentType(MediaType.APPLICATION_JSON)//
                .accept(MediaType.APPLICATION_JSON)//
                .content(content.toString()));

        // Act
        ResultActions response = mockMvc.perform(//
                get(//
                        "/user/ANOTHER_USERS'S_ID/comments")//
                        .header("Authorization",//
                                "Bearer" + authenticateUser(//
                                        content.getString("email"), content.getString("password")))//
                        .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andDo(print()).//
                andExpect(status().isForbidden())//
                .andExpect(jsonPath("$.message",//
                        is("You are not allowed to access this resource!")));
    }

    /**
     * Test the endpoint "/user/SOME_USERS'S_ID/comments" with the HTTP method "GET".
     * When an unauthorized client tries to get some user's comments:
     * -> Must return a 401 status code.
     * !Endpoint allowed for owner user and admin!
     *
     * @throws Exception Any exception that may occur during the test.
     * @see io.github.shuoros.ecommercy.security.SecurityConfig#configure(HttpSecurity)
     */
    @Test
    void getCommentsOfAUserWithUnauthorizedClientMustReturn401() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");
        mockMvc.perform(post("/user")//
                .contentType(MediaType.APPLICATION_JSON)//
                .accept(MediaType.APPLICATION_JSON)//
                .content(content.toString()));

        // Act
        ResultActions response = mockMvc.perform(//
                get(//
                        "/user/SOME_USERS'S_ID/comments")//
                        .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andDo(print()).//
                andExpect(status().isUnauthorized());
    }

    /**
     * Test the endpoint "/user/USER_ID/comments" with the HTTP method "GET".
     * When an admin wants to get a user's comments.
     * -> Must return a 200 status code and list of comments.
     * !Endpoint allowed for owner user and admin!
     *
     * @throws Exception Any exception that may occur during the test.
     */
    @Test
    void getCommentsOfAUserWithAdminAuthorityMustReturnAllComments() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");
        final JSONObject userLinks = new JSONObject(mockMvc.perform(post("/user")//
                        .contentType(MediaType.APPLICATION_JSON)//
                        .accept(MediaType.APPLICATION_JSON)//
                        .content(content.toString()))//
                .andReturn().getResponse().getContentAsString()).getJSONObject("_links");

        // Act
        ResultActions response = mockMvc.perform(//
                get(//
                        userLinks.getJSONObject("comments").getString("href"))//
                        .header("Authorization",//
                                "Bearer" + authenticateAdmin())//
                        .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andDo(print()).//
                andExpect(status().isOk())//
                .andExpect(jsonPath("$._embedded.comment").isArray())//
                .andExpect(jsonPath("$._embedded.comment").isEmpty());
    }

    /**
     * Test the endpoint "/user/WRONG_USER_ID/comments" with the HTTP method "GET".
     * When an admin wants to get a wrong user's comments.
     * -> Must return a 404 status code.
     * !Endpoint allowed for owner user and admin!
     *
     * @throws Exception Any exception that may occur during the test.
     */
    @Test
    void getCommentsOfAWrongUserWithAdminAuthorityMustReturn404() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");
        mockMvc.perform(post("/user")//
                .contentType(MediaType.APPLICATION_JSON)//
                .accept(MediaType.APPLICATION_JSON)//
                .content(content.toString()));

        // Act
        ResultActions response = mockMvc.perform(//
                get(//
                        "/user/WRONG_USER_ID/comments")//
                        .header("Authorization",//
                                "Bearer" + authenticateAdmin())//
                        .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andDo(print()).//
                andExpect(status().isNotFound());
    }

    /**
     * Test the endpoint "/user/USER_ID/orders" with the HTTP method "GET".
     * When a user wants to get its orders.
     * -> Must return a 200 status code and list of orders.
     * !Endpoint allowed for owner user and admin!
     *
     * @throws Exception Any exception that may occur during the test.
     */
    @Test
    void getOrdersOfAUserMustReturnAllOrders() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");
        final JSONObject userLinks = new JSONObject(mockMvc.perform(post("/user")//
                        .contentType(MediaType.APPLICATION_JSON)//
                        .accept(MediaType.APPLICATION_JSON)//
                        .content(content.toString()))//
                .andReturn().getResponse().getContentAsString()).getJSONObject("_links");

        // Act
        ResultActions response = mockMvc.perform(//
                get(//
                        userLinks.getJSONObject("orders").getString("href"))//
                        .header("Authorization",//
                                "Bearer" + authenticateUser(//
                                        content.getString("email"), content.getString("password")))//
                        .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andDo(print()).//
                andExpect(status().isOk())//
                .andExpect(jsonPath("$._embedded.order").isArray())//
                .andExpect(jsonPath("$._embedded.order").isEmpty());
    }

    /**
     * Test the endpoint "/user/ANOTHER_USERS'S_ID/orders" with the HTTP method "GET".
     * When a user tries to get another user's orders:
     * -> Must return a 403 status code.
     * !Endpoint allowed for owner user and admin!
     *
     * @throws Exception Any exception that may occur during the test.
     * @see io.github.shuoros.ecommercy.security.filter.RestDataSecurityFilter#doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)
     */
    @Test
    void getOrdersOfAUserWithAnotherUserIdMustReturn403() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");
        mockMvc.perform(post("/user")//
                .contentType(MediaType.APPLICATION_JSON)//
                .accept(MediaType.APPLICATION_JSON)//
                .content(content.toString()));

        // Act
        ResultActions response = mockMvc.perform(//
                get(//
                        "/user/ANOTHER_USERS'S_ID/orders")//
                        .header("Authorization",//
                                "Bearer" + authenticateUser(//
                                        content.getString("email"), content.getString("password")))//
                        .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andDo(print()).//
                andExpect(status().isForbidden())//
                .andExpect(jsonPath("$.message",//
                        is("You are not allowed to access this resource!")));
    }

    /**
     * Test the endpoint "/user/SOME_USERS'S_ID/orders" with the HTTP method "GET".
     * When an unauthorized client tries to some user's orders:
     * -> Must return a 401 status code.
     * !Endpoint allowed for owner user and admin!
     *
     * @throws Exception Any exception that may occur during the test.
     * @see io.github.shuoros.ecommercy.security.SecurityConfig#configure(HttpSecurity)
     */
    @Test
    void getOrdersOfSomeUserWithUnauthorizedClientMustReturn401() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");
        mockMvc.perform(post("/user")//
                .contentType(MediaType.APPLICATION_JSON)//
                .accept(MediaType.APPLICATION_JSON)//
                .content(content.toString()));

        // Act
        ResultActions response = mockMvc.perform(//
                get(//
                        "/user/SOME_USERS'S_ID/orders")//
                        .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andDo(print()).//
                andExpect(status().isUnauthorized());
    }

    /**
     * Test the endpoint "/user/USER_ID/orders" with the HTTP method "GET".
     * When an admin wants to get some user's orders.
     * -> Must return a 200 status code and list of orders.
     * !Endpoint allowed for owner user and admin!
     *
     * @throws Exception Any exception that may occur during the test.
     */
    @Test
    void getOrdersOfAUserWithAdminAuthorityMustReturnAllOrders() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");
        final JSONObject userLinks = new JSONObject(mockMvc.perform(post("/user")//
                        .contentType(MediaType.APPLICATION_JSON)//
                        .accept(MediaType.APPLICATION_JSON)//
                        .content(content.toString()))//
                .andReturn().getResponse().getContentAsString()).getJSONObject("_links");

        // Act
        ResultActions response = mockMvc.perform(//
                get(//
                        userLinks.getJSONObject("orders").getString("href"))//
                        .header("Authorization",//
                                "Bearer" + authenticateAdmin())//
                        .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andDo(print()).//
                andExpect(status().isOk())//
                .andExpect(jsonPath("$._embedded.order").isArray())//
                .andExpect(jsonPath("$._embedded.order").isEmpty());
    }

    /**
     * Test the endpoint "/user/WRONG_USER_ID/orders" with the HTTP method "GET".
     * When an admin wants to get some user's orders.
     * -> Must return a 200 status code and list of orders.
     * !Endpoint allowed for owner user and admin!
     *
     * @throws Exception Any exception that may occur during the test.
     */
    @Test
    void getOrdersOfAWrongUserWithAdminAuthorityMustReturn404() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");
        mockMvc.perform(post("/user")//
                .contentType(MediaType.APPLICATION_JSON)//
                .accept(MediaType.APPLICATION_JSON)//
                .content(content.toString()));

        // Act
        ResultActions response = mockMvc.perform(//
                get(//
                        "/user/WRONG_USER_ID/orders")//
                        .header("Authorization",//
                                "Bearer" + authenticateAdmin())//
                        .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andDo(print()).//
                andExpect(status().isNotFound());
    }

    /**
     * Test the endpoint "/user/USER_ID" with the HTTP method "PATCH".
     * When everything is OK:
     * -> Must update the user and return a 200 status code and the updated user.
     * !Endpoint allowed for owner user and admin!
     *
     * @throws Exception Any exception that may occur during the test.
     */
    @Test
    void updateUserMustUpdateUserNotCreateAnotherUser() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");
        final JSONObject userLinks = new JSONObject(mockMvc.perform(post("/user")//
                        .contentType(MediaType.APPLICATION_JSON)//
                        .accept(MediaType.APPLICATION_JSON)//
                        .content(content.toString()))//
                .andReturn().getResponse().getContentAsString()).getJSONObject("_links");

        // Act
        final JSONObject updateContent = new JSONObject()//
                .put("name", "Jane");
        ResultActions response = mockMvc.perform(//
                patch(//
                        userLinks.getJSONObject("self").getString("href"))//
                        .header("Authorization",//
                                "Bearer" + authenticateUser(//
                                        content.getString("email"), content.getString("password")))//
                        .contentType(MediaType.APPLICATION_JSON)//
                        .accept(MediaType.APPLICATION_JSON)//
                        .content(updateContent.toString()));

        // Assert
        response.andDo(print()).//
                andExpect(status().isOk())//
                .andExpect(jsonPath("$.name",//
                        is(updateContent.getString("name"))))//
                .andExpect(jsonPath("$.lastName",//
                        is(content.getString("lastName"))))//
                .andExpect(jsonPath("$.email",//
                        is(content.getString("email"))))//
                .andExpect(jsonPath("$.phoneNumber",//
                        is(content.getString("phoneNumber"))));
    }

    /**
     * Test the endpoint "/user/USER_ID" with the HTTP method "PATCH".
     * When user update its email to an already existing one:
     * -> Must return a 409 status code.
     * !Endpoint allowed for owner user and admin!
     *
     * @throws Exception Any exception that may occur during the test.
     * @see CustomerEventHandler#handleBeforeSave(User)
     */
    @Test
    void updateEmailOfAUserToEmailOfAExistingUserMustReturn409() throws Exception {
        // Arrange
        final JSONObject content1 = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");
        final JSONObject content2 = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "doejohn@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "987654321");
        mockMvc.perform(post("/user")//
                .contentType(MediaType.APPLICATION_JSON)//
                .accept(MediaType.APPLICATION_JSON)//
                .content(content1.toString()));
        final JSONObject userLinks = new JSONObject(mockMvc.perform(post("/user")//
                        .contentType(MediaType.APPLICATION_JSON)//
                        .accept(MediaType.APPLICATION_JSON)//
                        .content(content2.toString()))//
                .andReturn().getResponse().getContentAsString()).getJSONObject("_links");

        // Act
        final JSONObject updateContent = new JSONObject()//
                .put("email", "johndoe@email.com");
        ResultActions response = mockMvc.perform(//
                patch(//
                        userLinks.getJSONObject("self").getString("href"))//
                        .header("Authorization",//
                                "Bearer" + authenticateUser(//
                                        content2.getString("email"), content2.getString("password")))//
                        .contentType(MediaType.APPLICATION_JSON)//
                        .accept(MediaType.APPLICATION_JSON)//
                        .content(updateContent.toString()));

        // Assert
        response.andDo(print()).//
                andExpect(status().isConflict())//
                .andExpect(jsonPath("$.message",//
                        is("User with such email already exists!")));
    }

    /**
     * Test the endpoint "/user/USER_ID" with the HTTP method "PATCH".
     * When user update its phone number to an already existing one:
     * -> Must return a 409 status code.
     * !Endpoint allowed for owner user and admin!
     *
     * @throws Exception Any exception that may occur during the test.
     * @see CustomerEventHandler#handleBeforeSave(User)
     */
    @Test
    void updatePhoneNumberOfAUserToPhoneNumberOfAExistingUserMustReturn409() throws Exception {
        // Arrange
        final JSONObject content1 = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");
        final JSONObject content2 = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "doejohn@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "987654321");
        mockMvc.perform(post("/user")//
                .contentType(MediaType.APPLICATION_JSON)//
                .accept(MediaType.APPLICATION_JSON)//
                .content(content1.toString()));
        final JSONObject userLinks = new JSONObject(mockMvc.perform(post("/user")//
                        .contentType(MediaType.APPLICATION_JSON)//
                        .accept(MediaType.APPLICATION_JSON)//
                        .content(content2.toString()))//
                .andReturn().getResponse().getContentAsString()).getJSONObject("_links");

        // Act
        final JSONObject updateContent = new JSONObject()//
                .put("phoneNumber", "123456789");
        ResultActions response = mockMvc.perform(//
                patch(//
                        userLinks.getJSONObject("self").getString("href"))//
                        .header("Authorization",//
                                "Bearer" + authenticateUser(//
                                        content2.getString("email"), content2.getString("password")))//
                        .contentType(MediaType.APPLICATION_JSON)//
                        .accept(MediaType.APPLICATION_JSON)//
                        .content(updateContent.toString()));

        // Assert
        response.andDo(print()).//
                andExpect(status().isConflict())//
                .andExpect(jsonPath("$.message",//
                        is("User with such phone number already exists!")));
    }

    /**
     * Test the endpoint "/user/ANOTHER_USER'S_ID" with the HTTP method "PATCH".
     * When a user tries to update another user:
     * -> Must return a 403 status code.
     * !Endpoint allowed for owner user and admin!
     *
     * @throws Exception Any exception that may occur during the test.
     * @see io.github.shuoros.ecommercy.security.filter.RestDataSecurityFilter#doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)
     */
    @Test
    void updateUserWithAnotherUserIdMustReturn403() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");
        final JSONObject userLinks = new JSONObject(mockMvc.perform(post("/user")//
                        .contentType(MediaType.APPLICATION_JSON)//
                        .accept(MediaType.APPLICATION_JSON)//
                        .content(content.toString()))//
                .andReturn().getResponse().getContentAsString()).getJSONObject("_links");

        // Act
        final JSONObject updateContent = new JSONObject()//
                .put("name", "Jane");
        ResultActions response = mockMvc.perform(//
                patch("/user/ANOTHER_USER'S_ID")//
                        .header("Authorization",//
                                "Bearer" + authenticateUser(//
                                        content.getString("email"), content.getString("password")))//
                        .contentType(MediaType.APPLICATION_JSON)//
                        .accept(MediaType.APPLICATION_JSON)//
                        .content(updateContent.toString()));

        // Assert
        response.andDo(print()).//
                andExpect(status().isForbidden())//
                .andExpect(jsonPath("$.message",//
                        is("You are not allowed to access this resource!")));
    }

    /**
     * Test the endpoint "/user/SOME_USER'S_ID" with the HTTP method "PATCH".
     * When an unauthorized client tries to update some user:
     * -> Must return a 401 status code.
     * !Endpoint allowed for owner user and admin!
     *
     * @throws Exception Any exception that may occur during the test.
     * @see io.github.shuoros.ecommercy.security.SecurityConfig#configure(HttpSecurity)
     */
    @Test
    void updateSomeUserWithAnUnauthorizedClientMustReturn401() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");
        final JSONObject userLinks = new JSONObject(mockMvc.perform(post("/user")//
                        .contentType(MediaType.APPLICATION_JSON)//
                        .accept(MediaType.APPLICATION_JSON)//
                        .content(content.toString()))//
                .andReturn().getResponse().getContentAsString()).getJSONObject("_links");

        // Act
        final JSONObject updateContent = new JSONObject()//
                .put("name", "Jane");
        ResultActions response = mockMvc.perform(//
                patch("/user/SOME_USER'S_ID")//
                        .contentType(MediaType.APPLICATION_JSON)//
                        .accept(MediaType.APPLICATION_JSON)//
                        .content(updateContent.toString()));

        // Assert
        response.andDo(print()).//
                andExpect(status().isUnauthorized());
    }

    /**
     * Test the endpoint "/user/USER_ID" with the HTTP method "PATCH".
     * When admin wants to update a user and everything is OK:
     * -> Must update the user and return a 200 status code and the updated user.
     * !Endpoint allowed for owner user and admin!
     *
     * @throws Exception Any exception that may occur during the test.
     */
    @Test
    void updateUserWithAdminAuthorityMustUpdateUserNotCreateAnotherUser() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");
        final JSONObject userLinks = new JSONObject(mockMvc.perform(post("/user")//
                        .contentType(MediaType.APPLICATION_JSON)//
                        .accept(MediaType.APPLICATION_JSON)//
                        .content(content.toString()))//
                .andReturn().getResponse().getContentAsString()).getJSONObject("_links");

        // Act
        final JSONObject updateContent = new JSONObject()//
                .put("name", "Jane");
        ResultActions response = mockMvc.perform(//
                patch(//
                        userLinks.getJSONObject("self").getString("href"))//
                        .header("Authorization",//
                                "Bearer" + authenticateAdmin())//
                        .contentType(MediaType.APPLICATION_JSON)//
                        .accept(MediaType.APPLICATION_JSON)//
                        .content(updateContent.toString()));

        // Assert
        response.andDo(print()).//
                andExpect(status().isOk())//
                .andExpect(jsonPath("$.name",//
                        is(updateContent.getString("name"))))//
                .andExpect(jsonPath("$.lastName",//
                        is(content.getString("lastName"))))//
                .andExpect(jsonPath("$.email",//
                        is(content.getString("email"))))//
                .andExpect(jsonPath("$.phoneNumber",//
                        is(content.getString("phoneNumber"))));
    }

    /**
     * Test the endpoint "/user/WRONG_USER_ID" with the HTTP method "PATCH".
     * When admin wants to update a wrong user:
     * -> Must return a 404 status code.
     * !Endpoint allowed for owner user and admin!
     *
     * @throws Exception Any exception that may occur during the test.
     */
    @Test
    void updateWrongUserWithAdminAuthorityMustReturn404() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");
        mockMvc.perform(post("/user")//
                .contentType(MediaType.APPLICATION_JSON)//
                .accept(MediaType.APPLICATION_JSON)//
                .content(content.toString()));

        // Act
        final JSONObject updateContent = new JSONObject()//
                .put("name", "Jane");
        ResultActions response = mockMvc.perform(//
                patch(//
                        "/user/WRONG_USER_ID")//
                        .header("Authorization",//
                                "Bearer" + authenticateAdmin())//
                        .contentType(MediaType.APPLICATION_JSON)//
                        .accept(MediaType.APPLICATION_JSON)//
                        .content(updateContent.toString()));

        // Assert
        response.andDo(print()).//
                andExpect(status().isNotFound());
    }

    /**
     * Test the endpoint "/user/USER_ID" with the HTTP method "DELETE".
     * When everything is OK:
     * -> Must delete the user and return a 204 status code.
     * !Endpoint allowed for owner user and admin!
     *
     * @throws Exception Any exception that may occur during the test.
     */
    @Test
    void deleteUserMustDeleteUser() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");
        final JSONObject userLinks = new JSONObject(mockMvc.perform(post("/user")//
                        .contentType(MediaType.APPLICATION_JSON)//
                        .accept(MediaType.APPLICATION_JSON)//
                        .content(content.toString()))//
                .andReturn().getResponse().getContentAsString()).getJSONObject("_links");

        // Act
        ResultActions response = mockMvc.perform(//
                delete(//
                        userLinks.getJSONObject("self").getString("href"))//
                        .header("Authorization",//
                                "Bearer" + authenticateUser(//
                                        content.getString("email"), content.getString("password"))));

        // Assert
        response.andDo(print()).//
                andExpect(status().isNoContent());
    }

    /**
     * Test the endpoint "/user/ANOTHER_USER'S_ID" with the HTTP method "DELETE".
     * When a user tries to delete another user:
     * -> Must return a 403 status code.
     * !Endpoint allowed for owner user and admin!
     *
     * @throws Exception Any exception that may occur during the test.
     */
    @Test
    void deleteUserWithAnotherUserIdMustReturn403() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");
        mockMvc.perform(post("/user")//
                .contentType(MediaType.APPLICATION_JSON)//
                .accept(MediaType.APPLICATION_JSON)//
                .content(content.toString()));

        // Act
        ResultActions response = mockMvc.perform(//
                delete("/user/ANOTHER_USER'S_ID")//
                        .header("Authorization",//
                                "Bearer" + authenticateUser(//
                                        content.getString("email"), content.getString("password"))));

        // Assert
        response.andDo(print()).//
                andExpect(status().isForbidden())//
                .andExpect(jsonPath("$.message",//
                        is("You are not allowed to access this resource!")));
    }

    /**
     * Test the endpoint "/user/SOME_USER'S_ID" with the HTTP method "DELETE".
     * When an unauthorized client tries to delete some user:
     * -> Must return a 401 status code.
     * !Endpoint allowed for owner user and admin!
     *
     * @throws Exception Any exception that may occur during the test.
     */
    @Test
    void deleteSomeUserWithAnUnauthorizedClientMustReturn401() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");
        mockMvc.perform(post("/user")//
                .contentType(MediaType.APPLICATION_JSON)//
                .accept(MediaType.APPLICATION_JSON)//
                .content(content.toString()));

        // Act
        ResultActions response = mockMvc.perform(//
                delete("/user/SOME_USER'S_ID"));

        // Assert
        response.andDo(print()).//
                andExpect(status().isUnauthorized());
    }

    /**
     * Test the endpoint "/user/USER_ID" with the HTTP method "DELETE".
     * When admin wants to delete a user and everything is OK:
     * -> Must delete the user and return a 204 status code.
     * !Endpoint allowed for owner user and admin!
     *
     * @throws Exception Any exception that may occur during the test.
     */
    @Test
    void deleteUserWithAdminAuthorityMustDeleteUser() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");
        final JSONObject userLinks = new JSONObject(mockMvc.perform(post("/user")//
                        .contentType(MediaType.APPLICATION_JSON)//
                        .accept(MediaType.APPLICATION_JSON)//
                        .content(content.toString()))//
                .andReturn().getResponse().getContentAsString()).getJSONObject("_links");

        // Act
        ResultActions response = mockMvc.perform(//
                delete(//
                        userLinks.getJSONObject("self").getString("href"))//
                        .header("Authorization",//
                                "Bearer" + authenticateAdmin()));

        // Assert
        response.andDo(print()).//
                andExpect(status().isNoContent());
    }

    /**
     * Test the endpoint "/user/WRONG_USER_ID" with the HTTP method "DELETE".
     * When admin wants to delete a wrong user:
     * -> Must return a 404 status code.
     * !Endpoint allowed for owner user and admin!
     *
     * @throws Exception Any exception that may occur during the test.
     */
    @Test
    void deleteWrongUserWithAdminAuthorityMustReturn404() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");
        mockMvc.perform(post("/user")//
                .contentType(MediaType.APPLICATION_JSON)//
                .accept(MediaType.APPLICATION_JSON)//
                .content(content.toString()));

        // Act
        ResultActions response = mockMvc.perform(//
                delete(//
                        "/user/WRONG_USER_ID")//
                        .header("Authorization",//
                                "Bearer" + authenticateAdmin()));

        // Assert
        response.andDo(print()).//
                andExpect(status().isNotFound());
    }

    /**
     * Authenticate a user with the given email and password.
     *
     * @param username The email of the user.
     * @param password The password of the user.
     * @return The token of the user.
     */
    private String authenticateUser(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(//
                username, //
                password));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtTokenUtil.generateToken(authentication);
    }

    /**
     * Authenticate the initialized admin.
     *
     * @return The token of the admin.
     */
    private String authenticateAdmin() {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(//
                "root@ecommercy.app", //
                "1234"));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtTokenUtil.generateToken(authentication);
    }

}
