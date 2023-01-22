package io.github.shuoros.ecommercy.dao.repository;

import io.github.shuoros.ecommercy.AbstractContainerBaseTest;
import io.github.shuoros.ecommercy.dao.Customer;
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
 * Unit tests for {@link org.springframework.data.rest.core} ("Spring-Data-Rest") endpoint of Customer DAO.
 *
 * @author Soroush Shemshadi
 * @version 1.0.0
 * @see CustomerRepository
 * @since 1.0.0
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CustomerDataRestTests extends AbstractContainerBaseTest {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private Jwt jwtTokenUtil;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerRepository customerRepository;

    /**
     * Initialize the test environment.
     * Delete all customers from the database before each test.
     */
    @BeforeEach
    public void setUp() {
        customerRepository.deleteAll();
    }

    /**
     * Test the endpoint "/customer" with the HTTP method "POST".
     * When everything is OK:
     * -> Must create a new customer in the database and return a 201 status code and created customer.
     * !Endpoint allowed for anyone!
     *
     * @throws Exception Any exception that may occur during the test.
     */
    @Test
    void signUpCustomerMustCreateANewCustomerInDB() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");

        // Act
        ResultActions response = mockMvc.perform(//
                post("/customer")//
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
     * Test the endpoint "/customer" with the HTTP method "POST".
     * When email field is missing:
     * -> Must return a 422 status code.
     * !Endpoint allowed for anyone!
     *
     * @throws Exception Any exception that may occur during the test.
     * @see CustomerEventHandler#handleBeforeCreate(Customer)
     */
    @Test
    void signUpCustomerWithMissedEmailFieldMustReturn422() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                //.put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");

        // Act
        ResultActions response = mockMvc.perform(//
                post("/customer")//
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
     * Test the endpoint "/customer" with the HTTP method "POST".
     * When name field is missing:
     * -> Must return a 422 status code.
     * !Endpoint allowed for anyone!
     *
     * @throws Exception Any exception that may occur during the test.
     * @see CustomerEventHandler#handleBeforeCreate(Customer)
     */
    @Test
    void signUpCustomerWithMissedNameFieldMustReturn422() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                //.put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");

        // Act
        ResultActions response = mockMvc.perform(//
                post("/customer")//
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
     * Test the endpoint "/customer" with the HTTP method "POST".
     * When password field is missing:
     * -> Must return a 422 status code.
     * !Endpoint allowed for anyone!
     *
     * @throws Exception Any exception that may occur during the test.
     * @see CustomerEventHandler#handleBeforeCreate(Customer)
     */
    @Test
    void signUpCustomerWithMissedPasswordFieldMustReturn422() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                //.put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");

        // Act
        ResultActions response = mockMvc.perform(//
                post("/customer")//
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
     * Test the endpoint "/customer" with the HTTP method "POST".
     * When given password is not strong enough:
     * -> Must return a 422 status code.
     * !Endpoint allowed for anyone!
     *
     * @throws Exception Any exception that may occur during the test.
     * @see CustomerEventHandler#isValidPassword(String)
     */
    @Test
    void signUpCustomerWithSimplePasswordFieldMustReturn422() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "123456789")//
                .put("phoneNumber", "123456789");

        // Act
        ResultActions response = mockMvc.perform(//
                post("/customer")//
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
     * Test the endpoint "/customer" with the HTTP method "POST".
     * When a customer with given email is already exist:
     * -> Must return a 409 status code.
     * !Endpoint allowed for anyone!
     *
     * @throws Exception Any exception that may occur during the test.
     * @see CustomerEventHandler#handleBeforeCreate(Customer)
     */
    @Test
    void signUpCustomerWhenACustomerWithGivenEmailExistMustReturn409() throws Exception {
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
        mockMvc.perform(post("/customer")//
                .contentType(MediaType.APPLICATION_JSON)//
                .accept(MediaType.APPLICATION_JSON)//
                .content(content1.toString()));

        // Act
        ResultActions response = mockMvc.perform(//
                post("/customer")//
                        .contentType(MediaType.APPLICATION_JSON)//
                        .accept(MediaType.APPLICATION_JSON)//
                        .content(content2.toString()));

        // Assert
        response.andDo(print()).//
                andExpect(status().isConflict())//
                .andExpect(jsonPath("$.message",//
                        is("Customer with such email already exists!")));
    }

    /**
     * Test the endpoint "/customer" with the HTTP method "POST".
     * When a customer with given phone number is already exist:
     * -> Must return a 409 status code.
     * !Endpoint allowed for anyone!
     *
     * @throws Exception Any exception that may occur during the test.
     * @see CustomerEventHandler#handleBeforeCreate(Customer)
     */
    @Test
    void signUpCustomerWhenACustomerWithGivenPhoneNumberExistMustReturn409() throws Exception {
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
        mockMvc.perform(post("/customer")//
                .contentType(MediaType.APPLICATION_JSON)//
                .accept(MediaType.APPLICATION_JSON)//
                .content(content1.toString()));

        // Act
        ResultActions response = mockMvc.perform(//
                post("/customer")//
                        .contentType(MediaType.APPLICATION_JSON)//
                        .accept(MediaType.APPLICATION_JSON)//
                        .content(content2.toString()));

        // Assert
        response.andDo(print()).//
                andExpect(status().isConflict())//
                .andExpect(jsonPath("$.message",//
                        is("Customer with such phone number already exists!")));
    }

    /**
     * Test the endpoint "/customer/USER_ID/basket" with the HTTP method "GET".
     * When a customer created successfully then a basket must be created for him/her.
     * -> Must return a 200 status code and the desired basket.
     * !Endpoint allowed for owner customer and admin!
     *
     * @throws Exception Any exception that may occur during the test.
     */
    @Test
    void signUpCustomerMustCreateABasketForNewCustomerInDB() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");
        final JSONObject customerLinks = new JSONObject(mockMvc.perform(post("/customer")//
                        .contentType(MediaType.APPLICATION_JSON)//
                        .accept(MediaType.APPLICATION_JSON)//
                        .content(content.toString()))//
                .andReturn().getResponse().getContentAsString()).getJSONObject("_links");

        // Act
        ResultActions response = mockMvc.perform(//
                get(//
                        customerLinks.getJSONObject("basket").getString("href"))//
                        .header("Authorization",//
                                "Bearer" + authenticateCustomer(//
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
     * Test the endpoint "/customer/ANOTHER_USER'S_ID/basket" with the HTTP method "GET".
     * When a customer tries to get a basket of another customer:
     * -> Must return a 403 status code.
     * !Endpoint allowed for owner customer and admin!
     *
     * @throws Exception Any exception that may occur during the test.
     * @see io.github.shuoros.ecommercy.security.filter.RestDataSecurityFilter#doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)
     */
    @Test
    void getBasketOfCustomerWithAnotherCustomerIdMustReturn403() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");
        mockMvc.perform(post("/customer")//
                .contentType(MediaType.APPLICATION_JSON)//
                .accept(MediaType.APPLICATION_JSON)//
                .content(content.toString()));

        // Act
        ResultActions response = mockMvc.perform(//
                get(//
                        "/customer/ANOTHER_USERS'S_ID/basket")//
                        .header("Authorization",//
                                "Bearer" + authenticateCustomer(//
                                        content.getString("email"), content.getString("password")))//
                        .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andDo(print()).//
                andExpect(status().isForbidden())//
                .andExpect(jsonPath("$.message",//
                        is("You are not allowed to access this resource!")));
    }

    /**
     * Test the endpoint "/customer/SOME_USER'S_ID/basket" with the HTTP method "GET".
     * When an unauthorized client tries to get a basket of some customer:
     * -> Must return a 401 status code.
     * !Endpoint allowed for owner customer and admin!
     *
     * @throws Exception Any exception that may occur during the test.
     * @see io.github.shuoros.ecommercy.security.SecurityConfig#configure(HttpSecurity)
     */
    @Test
    void getBasketOfCustomerWithAnUnAuthorizedClientMustReturn401() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");
        mockMvc.perform(post("/customer")//
                .contentType(MediaType.APPLICATION_JSON)//
                .accept(MediaType.APPLICATION_JSON)//
                .content(content.toString()));

        // Act
        ResultActions response = mockMvc.perform(//
                get(//
                        "/customer/SOME_USERS'S_ID/basket")//
                        .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andDo(print()).//
                andExpect(status().isUnauthorized());
    }

    /**
     * Test the endpoint "/customer/USER'S_ID/basket" with the HTTP method "GET".
     * When admin wants to get basket of some customer:
     * -> Must return a 200 status code and the desired basket.
     * !Endpoint allowed for owner customer and admin!
     *
     * @throws Exception Any exception that may occur during the test.
     */
    @Test
    void getBasketOfCustomerWithAdminAuthorityMustReturnBasket() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");
        final JSONObject customerLinks = new JSONObject(mockMvc.perform(post("/customer")//
                        .contentType(MediaType.APPLICATION_JSON)//
                        .accept(MediaType.APPLICATION_JSON)//
                        .content(content.toString()))//
                .andReturn().getResponse().getContentAsString()).getJSONObject("_links");

        // Act
        ResultActions response = mockMvc.perform(//
                get(//
                        customerLinks.getJSONObject("basket").getString("href"))//
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
     * Test the endpoint "/customer/WRONG_USER_ID/basket" with the HTTP method "GET".
     * When admin wants to get basket of some customer:
     * -> Must return a 404 status code.
     * !Endpoint allowed for owner customer and admin!
     *
     * @throws Exception Any exception that may occur during the test.
     */
    @Test
    void getBasketWithWrongCustomerIDAndWithAdminAuthorityMustReturn404() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");
        mockMvc.perform(post("/customer")//
                .contentType(MediaType.APPLICATION_JSON)//
                .accept(MediaType.APPLICATION_JSON)//
                .content(content.toString()));

        // Act
        ResultActions response = mockMvc.perform(//
                get(//
                        "/customer/WRONG_USER_ID/basket")//
                        .header("Authorization",//
                                "Bearer" + authenticateAdmin())//
                        .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andDo(print()).//
                andExpect(status().isNotFound());
    }

    /**
     * Test the endpoint "/customer/USER_ID" with the HTTP method "GET".
     * When customer wants to ger him/herself:
     * -> Must return a 200 status code and customer.
     * !Endpoint allowed for owner customer and admin!
     *
     * @throws Exception Any exception that may occur during the test.
     */
    @Test
    void getCustomerMustReturnCorrectCustomer() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");
        final JSONObject customerLinks = new JSONObject(mockMvc.perform(post("/customer")//
                        .contentType(MediaType.APPLICATION_JSON)//
                        .accept(MediaType.APPLICATION_JSON)//
                        .content(content.toString()))//
                .andReturn().getResponse().getContentAsString()).getJSONObject("_links");

        // Act
        ResultActions response = mockMvc.perform(//
                get(//
                        customerLinks.getJSONObject("self").getString("href"))//
                        .header("Authorization",//
                                "Bearer" + authenticateCustomer(//
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
     * Test the endpoint "/customer/ANOTHER_USER'S_ID" with the HTTP method "GET".
     * When a customer tries to get a basket of another customer:
     * -> Must return a 403 status code.
     * !Endpoint allowed for owner customer and admin!
     *
     * @throws Exception Any exception that may occur during the test.
     * @see io.github.shuoros.ecommercy.security.filter.RestDataSecurityFilter#doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)
     */
    @Test
    void getCustomerWithAnotherCustomerIdMustReturn403() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");
        mockMvc.perform(post("/customer")//
                .contentType(MediaType.APPLICATION_JSON)//
                .accept(MediaType.APPLICATION_JSON)//
                .content(content.toString()));

        // Act
        ResultActions response = mockMvc.perform(//
                get(//
                        "/customer/ANOTHER_USERS'S_ID")//
                        .header("Authorization",//
                                "Bearer" + authenticateCustomer(//
                                        content.getString("email"), content.getString("password")))//
                        .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andDo(print()).//
                andExpect(status().isForbidden())//
                .andExpect(jsonPath("$.message",//
                        is("You are not allowed to access this resource!")));
    }

    /**
     * Test the endpoint "/customer/SOME_USER'S_ID" with the HTTP method "GET".
     * When an unauthorized client tries to get a basket of some customer:
     * -> Must return a 401 status code.
     * !Endpoint allowed for owner customer and admin!
     *
     * @throws Exception Any exception that may occur during the test.
     * @see io.github.shuoros.ecommercy.security.SecurityConfig#configure(HttpSecurity)
     */
    @Test
    void getCustomerWithAnUnAuthorizedClientMustReturn401() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");
        mockMvc.perform(post("/customer")//
                .contentType(MediaType.APPLICATION_JSON)//
                .accept(MediaType.APPLICATION_JSON)//
                .content(content.toString()));

        // Act
        ResultActions response = mockMvc.perform(//
                get(//
                        "/customer/SOME_USERS'S_ID")//
                        .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andDo(print()).//
                andExpect(status().isUnauthorized());
    }

    /**
     * Test the endpoint "/customer/USER_ID" with the HTTP method "GET".
     * When admin wants to get a customer:
     * -> Must return a 200 status code and customer.
     * !Endpoint allowed for owner customer and admin!
     *
     * @throws Exception Any exception that may occur during the test.
     */
    @Test
    void getCustomerWithAdminAuthorityMustReturnCorrectCustomer() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");
        final JSONObject customerLinks = new JSONObject(mockMvc.perform(post("/customer")//
                        .contentType(MediaType.APPLICATION_JSON)//
                        .accept(MediaType.APPLICATION_JSON)//
                        .content(content.toString()))//
                .andReturn().getResponse().getContentAsString()).getJSONObject("_links");

        // Act
        ResultActions response = mockMvc.perform(//
                get(//
                        customerLinks.getJSONObject("self").getString("href"))//
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
     * Test the endpoint "/customer/WRONG_USER_ID" with the HTTP method "GET".
     * When admin wants to get a wrong customer:
     * -> Must return a 404 status code.
     * !Endpoint allowed for owner customer and admin!
     *
     * @throws Exception Any exception that may occur during the test.
     */
    @Test
    void getAWrongCustomerWithAdminAuthorityMustReturn404() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");
        mockMvc.perform(post("/customer")//
                .contentType(MediaType.APPLICATION_JSON)//
                .accept(MediaType.APPLICATION_JSON)//
                .content(content.toString()));

        // Act
        ResultActions response = mockMvc.perform(//
                get(//
                        "/customer/WRONG_USER_ID")//
                        .header("Authorization",//
                                "Bearer" + authenticateAdmin())//
                        .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andDo(print()).//
                andExpect(status().isNotFound());
    }

    /**
     * Test the endpoint "/customer" with the HTTP method "GET".
     * When admin wants to get list of all customers:
     * -> Must return a 200 status code and list of all customers.
     * !Endpoint allowed just for admin!
     *
     * @throws Exception Any exception that may occur during the test.
     */
    @Test
    void getAllCustomersMustReturnAllCustomers() throws Exception {
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
        mockMvc.perform(post("/customer")//
                .contentType(MediaType.APPLICATION_JSON)//
                .accept(MediaType.APPLICATION_JSON)//
                .content(content1.toString()));
        mockMvc.perform(post("/customer")//
                .contentType(MediaType.APPLICATION_JSON)//
                .accept(MediaType.APPLICATION_JSON)//
                .content(content2.toString()));

        // Act
        ResultActions response = mockMvc.perform(//
                get("/customer")//
                        .header("Authorization",//
                                "Bearer" + authenticateAdmin())//
                        .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andDo(print()).//
                andExpect(status().isOk())//
                .andExpect(jsonPath("$.page.totalElements",//
                        is(2)))//
                .andExpect(jsonPath("$._embedded.customer")//
                        .isArray());
    }

    /**
     * Test the endpoint "/customer" with the HTTP method "GET".
     * When a customer tries to get list of all customers:
     * -> Must return a 403 status code.
     * !Endpoint allowed just for admin!
     *
     * @throws Exception Any exception that may occur during the test.
     * @see io.github.shuoros.ecommercy.security.SecurityConfig#configure(HttpSecurity)
     */
    @Test
    void getAllCustomersWithCustomerAuthorityMustReturn403() throws Exception {
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
        mockMvc.perform(post("/customer")//
                .contentType(MediaType.APPLICATION_JSON)//
                .accept(MediaType.APPLICATION_JSON)//
                .content(content1.toString()));
        mockMvc.perform(post("/customer")//
                .contentType(MediaType.APPLICATION_JSON)//
                .accept(MediaType.APPLICATION_JSON)//
                .content(content2.toString()));

        // Act
        ResultActions response = mockMvc.perform(//
                get("/customer")//
                        .header("Authorization",//
                                "Bearer" + authenticateCustomer(//
                                        content1.getString("email"), content1.getString("password")))//
                        .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andDo(print()).//
                andExpect(status().isForbidden());
    }

    /**
     * Test the endpoint "/customer" with the HTTP method "GET".
     * When an unauthorized tries to get list of all customers:
     * -> Must return a 401 status code.
     * !Endpoint allowed just for admin!
     *
     * @throws Exception Any exception that may occur during the test.
     * @see io.github.shuoros.ecommercy.security.SecurityConfig#configure(HttpSecurity)
     */
    @Test
    void getAllCustomersWithAnUnAuthorizedClientMustReturn401() throws Exception {
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
        mockMvc.perform(post("/customer")//
                .contentType(MediaType.APPLICATION_JSON)//
                .accept(MediaType.APPLICATION_JSON)//
                .content(content1.toString()));
        mockMvc.perform(post("/customer")//
                .contentType(MediaType.APPLICATION_JSON)//
                .accept(MediaType.APPLICATION_JSON)//
                .content(content2.toString()));

        // Act
        ResultActions response = mockMvc.perform(//
                get("/customer")//
                        .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andDo(print()).//
                andExpect(status().isUnauthorized());
    }

    /**
     * Test the endpoint "/customer/USER_ID/addresses" with the HTTP method "GET".
     * When a customer wants to get its addresses.
     * -> Must return a 200 status code and list of addresses.
     * !Endpoint allowed for owner customer and admin!
     *
     * @throws Exception Any exception that may occur during the test.
     */
    @Test
    void getAddressesOfACustomerMustReturnAllAddresses() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");
        final JSONObject customerLinks = new JSONObject(mockMvc.perform(post("/customer")//
                        .contentType(MediaType.APPLICATION_JSON)//
                        .accept(MediaType.APPLICATION_JSON)//
                        .content(content.toString()))//
                .andReturn().getResponse().getContentAsString()).getJSONObject("_links");

        // Act
        ResultActions response = mockMvc.perform(//
                get(//
                        customerLinks.getJSONObject("addresses").getString("href"))//
                        .header("Authorization",//
                                "Bearer" + authenticateCustomer(//
                                        content.getString("email"), content.getString("password")))//
                        .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andDo(print()).//
                andExpect(status().isOk())//
                .andExpect(jsonPath("$._embedded.address").isArray())//
                .andExpect(jsonPath("$._embedded.address").isEmpty());
    }

    /**
     * Test the endpoint "/customer/ANOTHER_USERS'S_ID/addresses" with the HTTP method "GET".
     * When a customer tries to get another customer's addresses:
     * -> Must return a 403 status code.
     * !Endpoint allowed for owner customer and admin!
     *
     * @throws Exception Any exception that may occur during the test.
     * @see io.github.shuoros.ecommercy.security.filter.RestDataSecurityFilter#doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)
     */
    @Test
    void getAddressesOfACustomerWithAnotherCustomerIdMustReturn403() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");
        mockMvc.perform(post("/customer")//
                .contentType(MediaType.APPLICATION_JSON)//
                .accept(MediaType.APPLICATION_JSON)//
                .content(content.toString()));

        // Act
        ResultActions response = mockMvc.perform(//
                get(//
                        "/customer/ANOTHER_USERS'S_ID/addresses")//
                        .header("Authorization",//
                                "Bearer" + authenticateCustomer(//
                                        content.getString("email"), content.getString("password")))//
                        .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andDo(print()).//
                andExpect(status().isForbidden())//
                .andExpect(jsonPath("$.message",//
                        is("You are not allowed to access this resource!")));
    }

    /**
     * Test the endpoint "/customer/SOME_USERS'S_ID/addresses" with the HTTP method "GET".
     * When an unauthorized client tries to get some customer's addresses:
     * -> Must return a 401 status code.
     * !Endpoint allowed for owner customer and admin!
     *
     * @throws Exception Any exception that may occur during the test.
     * @see io.github.shuoros.ecommercy.security.SecurityConfig#configure(HttpSecurity)
     */
    @Test
    void getAddressesOfSomeCustomerWithUnauthorizedClientMustReturn401() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");
        mockMvc.perform(post("/customer")//
                .contentType(MediaType.APPLICATION_JSON)//
                .accept(MediaType.APPLICATION_JSON)//
                .content(content.toString()));

        // Act
        ResultActions response = mockMvc.perform(//
                get(//
                        "/customer/SOME_USERS'S_ID/addresses")//
                        .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andDo(print()).//
                andExpect(status().isUnauthorized());
    }

    /**
     * Test the endpoint "/customer/USER_ID/addresses" with the HTTP method "GET".
     * When an admin wants to get a customer's addresses.
     * -> Must return a 200 status code and list of addresses.
     * !Endpoint allowed for owner customer and admin!
     *
     * @throws Exception Any exception that may occur during the test.
     */
    @Test
    void getAddressesOfACustomerWithAdminAuthorityMustReturnAllAddresses() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");
        final JSONObject customerLinks = new JSONObject(mockMvc.perform(post("/customer")//
                        .contentType(MediaType.APPLICATION_JSON)//
                        .accept(MediaType.APPLICATION_JSON)//
                        .content(content.toString()))//
                .andReturn().getResponse().getContentAsString()).getJSONObject("_links");

        // Act
        ResultActions response = mockMvc.perform(//
                get(//
                        customerLinks.getJSONObject("addresses").getString("href"))//
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
     * Test the endpoint "/customer/WRONG_USER_ID/addresses" with the HTTP method "GET".
     * When an admin wants to get a wrong customer's addresses.
     * -> Must return a 404 status code.
     * !Endpoint allowed for owner customer and admin!
     *
     * @throws Exception Any exception that may occur during the test.
     */
    @Test
    void getAddressesOfAWrongCustomerWithAdminAuthorityMustReturn404() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");
        mockMvc.perform(post("/customer")//
                .contentType(MediaType.APPLICATION_JSON)//
                .accept(MediaType.APPLICATION_JSON)//
                .content(content.toString()));

        // Act
        ResultActions response = mockMvc.perform(//
                get(//
                        "/customer/WRONG_USER_ID/addresses")//
                        .header("Authorization",//
                                "Bearer" + authenticateAdmin())//
                        .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andDo(print()).//
                andExpect(status().isNotFound());
    }

    /**
     * Test the endpoint "/customer/USER_ID/comments" with the HTTP method "GET".
     * When a customer wants to get its comments.
     * -> Must return a 200 status code and list of comments.
     * !Endpoint allowed for owner customer and admin!
     *
     * @throws Exception Any exception that may occur during the test.
     */
    @Test
    void getCommentsOfACustomerMustReturnAllComments() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");
        final JSONObject customerLinks = new JSONObject(mockMvc.perform(post("/customer")//
                        .contentType(MediaType.APPLICATION_JSON)//
                        .accept(MediaType.APPLICATION_JSON)//
                        .content(content.toString()))//
                .andReturn().getResponse().getContentAsString()).getJSONObject("_links");

        // Act
        ResultActions response = mockMvc.perform(//
                get(//
                        customerLinks.getJSONObject("comments").getString("href"))//
                        .header("Authorization",//
                                "Bearer" + authenticateCustomer(//
                                        content.getString("email"), content.getString("password")))//
                        .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andDo(print()).//
                andExpect(status().isOk())//
                .andExpect(jsonPath("$._embedded.comment").isArray())//
                .andExpect(jsonPath("$._embedded.comment").isEmpty());
    }

    /**
     * Test the endpoint "/customer/ANOTHER_USERS'S_ID/comments" with the HTTP method "GET".
     * When a customer tries to get another customer's comments:
     * -> Must return a 403 status code.
     * !Endpoint allowed for owner customer and admin!
     *
     * @throws Exception Any exception that may occur during the test.
     * @see io.github.shuoros.ecommercy.security.filter.RestDataSecurityFilter#doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)
     */
    @Test
    void getCommentsOfACustomerWithAnotherCustomerIdMustReturn403() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");
        mockMvc.perform(post("/customer")//
                .contentType(MediaType.APPLICATION_JSON)//
                .accept(MediaType.APPLICATION_JSON)//
                .content(content.toString()));

        // Act
        ResultActions response = mockMvc.perform(//
                get(//
                        "/customer/ANOTHER_USERS'S_ID/comments")//
                        .header("Authorization",//
                                "Bearer" + authenticateCustomer(//
                                        content.getString("email"), content.getString("password")))//
                        .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andDo(print()).//
                andExpect(status().isForbidden())//
                .andExpect(jsonPath("$.message",//
                        is("You are not allowed to access this resource!")));
    }

    /**
     * Test the endpoint "/customer/SOME_USERS'S_ID/comments" with the HTTP method "GET".
     * When an unauthorized client tries to get some customer's comments:
     * -> Must return a 401 status code.
     * !Endpoint allowed for owner customer and admin!
     *
     * @throws Exception Any exception that may occur during the test.
     * @see io.github.shuoros.ecommercy.security.SecurityConfig#configure(HttpSecurity)
     */
    @Test
    void getCommentsOfACustomerWithUnauthorizedClientMustReturn401() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");
        mockMvc.perform(post("/customer")//
                .contentType(MediaType.APPLICATION_JSON)//
                .accept(MediaType.APPLICATION_JSON)//
                .content(content.toString()));

        // Act
        ResultActions response = mockMvc.perform(//
                get(//
                        "/customer/SOME_USERS'S_ID/comments")//
                        .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andDo(print()).//
                andExpect(status().isUnauthorized());
    }

    /**
     * Test the endpoint "/customer/USER_ID/comments" with the HTTP method "GET".
     * When an admin wants to get a customer's comments.
     * -> Must return a 200 status code and list of comments.
     * !Endpoint allowed for owner customer and admin!
     *
     * @throws Exception Any exception that may occur during the test.
     */
    @Test
    void getCommentsOfACustomerWithAdminAuthorityMustReturnAllComments() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");
        final JSONObject customerLinks = new JSONObject(mockMvc.perform(post("/customer")//
                        .contentType(MediaType.APPLICATION_JSON)//
                        .accept(MediaType.APPLICATION_JSON)//
                        .content(content.toString()))//
                .andReturn().getResponse().getContentAsString()).getJSONObject("_links");

        // Act
        ResultActions response = mockMvc.perform(//
                get(//
                        customerLinks.getJSONObject("comments").getString("href"))//
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
     * Test the endpoint "/customer/WRONG_USER_ID/comments" with the HTTP method "GET".
     * When an admin wants to get a wrong customer's comments.
     * -> Must return a 404 status code.
     * !Endpoint allowed for owner customer and admin!
     *
     * @throws Exception Any exception that may occur during the test.
     */
    @Test
    void getCommentsOfAWrongCustomerWithAdminAuthorityMustReturn404() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");
        mockMvc.perform(post("/customer")//
                .contentType(MediaType.APPLICATION_JSON)//
                .accept(MediaType.APPLICATION_JSON)//
                .content(content.toString()));

        // Act
        ResultActions response = mockMvc.perform(//
                get(//
                        "/customer/WRONG_USER_ID/comments")//
                        .header("Authorization",//
                                "Bearer" + authenticateAdmin())//
                        .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andDo(print()).//
                andExpect(status().isNotFound());
    }

    /**
     * Test the endpoint "/customer/USER_ID/orders" with the HTTP method "GET".
     * When a customer wants to get its orders.
     * -> Must return a 200 status code and list of orders.
     * !Endpoint allowed for owner customer and admin!
     *
     * @throws Exception Any exception that may occur during the test.
     */
    @Test
    void getOrdersOfACustomerMustReturnAllOrders() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");
        final JSONObject customerLinks = new JSONObject(mockMvc.perform(post("/customer")//
                        .contentType(MediaType.APPLICATION_JSON)//
                        .accept(MediaType.APPLICATION_JSON)//
                        .content(content.toString()))//
                .andReturn().getResponse().getContentAsString()).getJSONObject("_links");

        // Act
        ResultActions response = mockMvc.perform(//
                get(//
                        customerLinks.getJSONObject("orders").getString("href"))//
                        .header("Authorization",//
                                "Bearer" + authenticateCustomer(//
                                        content.getString("email"), content.getString("password")))//
                        .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andDo(print()).//
                andExpect(status().isOk())//
                .andExpect(jsonPath("$._embedded.order").isArray())//
                .andExpect(jsonPath("$._embedded.order").isEmpty());
    }

    /**
     * Test the endpoint "/customer/ANOTHER_USERS'S_ID/orders" with the HTTP method "GET".
     * When a customer tries to get another customer's orders:
     * -> Must return a 403 status code.
     * !Endpoint allowed for owner customer and admin!
     *
     * @throws Exception Any exception that may occur during the test.
     * @see io.github.shuoros.ecommercy.security.filter.RestDataSecurityFilter#doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)
     */
    @Test
    void getOrdersOfACustomerWithAnotherCustomerIdMustReturn403() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");
        mockMvc.perform(post("/customer")//
                .contentType(MediaType.APPLICATION_JSON)//
                .accept(MediaType.APPLICATION_JSON)//
                .content(content.toString()));

        // Act
        ResultActions response = mockMvc.perform(//
                get(//
                        "/customer/ANOTHER_USERS'S_ID/orders")//
                        .header("Authorization",//
                                "Bearer" + authenticateCustomer(//
                                        content.getString("email"), content.getString("password")))//
                        .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andDo(print()).//
                andExpect(status().isForbidden())//
                .andExpect(jsonPath("$.message",//
                        is("You are not allowed to access this resource!")));
    }

    /**
     * Test the endpoint "/customer/SOME_USERS'S_ID/orders" with the HTTP method "GET".
     * When an unauthorized client tries to some customer's orders:
     * -> Must return a 401 status code.
     * !Endpoint allowed for owner customer and admin!
     *
     * @throws Exception Any exception that may occur during the test.
     * @see io.github.shuoros.ecommercy.security.SecurityConfig#configure(HttpSecurity)
     */
    @Test
    void getOrdersOfSomeCustomerWithUnauthorizedClientMustReturn401() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");
        mockMvc.perform(post("/customer")//
                .contentType(MediaType.APPLICATION_JSON)//
                .accept(MediaType.APPLICATION_JSON)//
                .content(content.toString()));

        // Act
        ResultActions response = mockMvc.perform(//
                get(//
                        "/customer/SOME_USERS'S_ID/orders")//
                        .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andDo(print()).//
                andExpect(status().isUnauthorized());
    }

    /**
     * Test the endpoint "/customer/USER_ID/orders" with the HTTP method "GET".
     * When an admin wants to get some customer's orders.
     * -> Must return a 200 status code and list of orders.
     * !Endpoint allowed for owner customer and admin!
     *
     * @throws Exception Any exception that may occur during the test.
     */
    @Test
    void getOrdersOfACustomerWithAdminAuthorityMustReturnAllOrders() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");
        final JSONObject customerLinks = new JSONObject(mockMvc.perform(post("/customer")//
                        .contentType(MediaType.APPLICATION_JSON)//
                        .accept(MediaType.APPLICATION_JSON)//
                        .content(content.toString()))//
                .andReturn().getResponse().getContentAsString()).getJSONObject("_links");

        // Act
        ResultActions response = mockMvc.perform(//
                get(//
                        customerLinks.getJSONObject("orders").getString("href"))//
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
     * Test the endpoint "/customer/WRONG_USER_ID/orders" with the HTTP method "GET".
     * When an admin wants to get some customer's orders.
     * -> Must return a 200 status code and list of orders.
     * !Endpoint allowed for owner customer and admin!
     *
     * @throws Exception Any exception that may occur during the test.
     */
    @Test
    void getOrdersOfAWrongCustomerWithAdminAuthorityMustReturn404() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");
        mockMvc.perform(post("/customer")//
                .contentType(MediaType.APPLICATION_JSON)//
                .accept(MediaType.APPLICATION_JSON)//
                .content(content.toString()));

        // Act
        ResultActions response = mockMvc.perform(//
                get(//
                        "/customer/WRONG_USER_ID/orders")//
                        .header("Authorization",//
                                "Bearer" + authenticateAdmin())//
                        .accept(MediaType.APPLICATION_JSON));

        // Assert
        response.andDo(print()).//
                andExpect(status().isNotFound());
    }

    /**
     * Test the endpoint "/customer/USER_ID" with the HTTP method "PATCH".
     * When everything is OK:
     * -> Must update the customer and return a 200 status code and the updated customer.
     * !Endpoint allowed for owner customer and admin!
     *
     * @throws Exception Any exception that may occur during the test.
     */
    @Test
    void updateCustomerMustUpdateCustomerNotCreateAnotherCustomer() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");
        final JSONObject customerLinks = new JSONObject(mockMvc.perform(post("/customer")//
                        .contentType(MediaType.APPLICATION_JSON)//
                        .accept(MediaType.APPLICATION_JSON)//
                        .content(content.toString()))//
                .andReturn().getResponse().getContentAsString()).getJSONObject("_links");

        // Act
        final JSONObject updateContent = new JSONObject()//
                .put("name", "Jane");
        ResultActions response = mockMvc.perform(//
                patch(//
                        customerLinks.getJSONObject("self").getString("href"))//
                        .header("Authorization",//
                                "Bearer" + authenticateCustomer(//
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
     * Test the endpoint "/customer/USER_ID" with the HTTP method "PATCH".
     * When customer update its email to an already existing one:
     * -> Must return a 409 status code.
     * !Endpoint allowed for owner customer and admin!
     *
     * @throws Exception Any exception that may occur during the test.
     * @see CustomerEventHandler#handleBeforeSave(Customer)
     */
    @Test
    void updateEmailOfACustomerToEmailOfAExistingCustomerMustReturn409() throws Exception {
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
        mockMvc.perform(post("/customer")//
                .contentType(MediaType.APPLICATION_JSON)//
                .accept(MediaType.APPLICATION_JSON)//
                .content(content1.toString()));
        final JSONObject customerLinks = new JSONObject(mockMvc.perform(post("/customer")//
                        .contentType(MediaType.APPLICATION_JSON)//
                        .accept(MediaType.APPLICATION_JSON)//
                        .content(content2.toString()))//
                .andReturn().getResponse().getContentAsString()).getJSONObject("_links");

        // Act
        final JSONObject updateContent = new JSONObject()//
                .put("email", "johndoe@email.com");
        ResultActions response = mockMvc.perform(//
                patch(//
                        customerLinks.getJSONObject("self").getString("href"))//
                        .header("Authorization",//
                                "Bearer" + authenticateCustomer(//
                                        content2.getString("email"), content2.getString("password")))//
                        .contentType(MediaType.APPLICATION_JSON)//
                        .accept(MediaType.APPLICATION_JSON)//
                        .content(updateContent.toString()));

        // Assert
        response.andDo(print()).//
                andExpect(status().isConflict())//
                .andExpect(jsonPath("$.message",//
                        is("Customer with such email already exists!")));
    }

    /**
     * Test the endpoint "/customer/USER_ID" with the HTTP method "PATCH".
     * When customer update its phone number to an already existing one:
     * -> Must return a 409 status code.
     * !Endpoint allowed for owner customer and admin!
     *
     * @throws Exception Any exception that may occur during the test.
     * @see CustomerEventHandler#handleBeforeSave(Customer)
     */
    @Test
    void updatePhoneNumberOfACustomerToPhoneNumberOfAExistingCustomerMustReturn409() throws Exception {
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
        mockMvc.perform(post("/customer")//
                .contentType(MediaType.APPLICATION_JSON)//
                .accept(MediaType.APPLICATION_JSON)//
                .content(content1.toString()));
        final JSONObject customerLinks = new JSONObject(mockMvc.perform(post("/customer")//
                        .contentType(MediaType.APPLICATION_JSON)//
                        .accept(MediaType.APPLICATION_JSON)//
                        .content(content2.toString()))//
                .andReturn().getResponse().getContentAsString()).getJSONObject("_links");

        // Act
        final JSONObject updateContent = new JSONObject()//
                .put("phoneNumber", "123456789");
        ResultActions response = mockMvc.perform(//
                patch(//
                        customerLinks.getJSONObject("self").getString("href"))//
                        .header("Authorization",//
                                "Bearer" + authenticateCustomer(//
                                        content2.getString("email"), content2.getString("password")))//
                        .contentType(MediaType.APPLICATION_JSON)//
                        .accept(MediaType.APPLICATION_JSON)//
                        .content(updateContent.toString()));

        // Assert
        response.andDo(print()).//
                andExpect(status().isConflict())//
                .andExpect(jsonPath("$.message",//
                        is("Customer with such phone number already exists!")));
    }

    /**
     * Test the endpoint "/customer/ANOTHER_USER'S_ID" with the HTTP method "PATCH".
     * When a customer tries to update another customer:
     * -> Must return a 403 status code.
     * !Endpoint allowed for owner customer and admin!
     *
     * @throws Exception Any exception that may occur during the test.
     * @see io.github.shuoros.ecommercy.security.filter.RestDataSecurityFilter#doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)
     */
    @Test
    void updateCustomerWithAnotherCustomerIdMustReturn403() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");
        final JSONObject customerLinks = new JSONObject(mockMvc.perform(post("/customer")//
                        .contentType(MediaType.APPLICATION_JSON)//
                        .accept(MediaType.APPLICATION_JSON)//
                        .content(content.toString()))//
                .andReturn().getResponse().getContentAsString()).getJSONObject("_links");

        // Act
        final JSONObject updateContent = new JSONObject()//
                .put("name", "Jane");
        ResultActions response = mockMvc.perform(//
                patch("/customer/ANOTHER_USER'S_ID")//
                        .header("Authorization",//
                                "Bearer" + authenticateCustomer(//
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
     * Test the endpoint "/customer/SOME_USER'S_ID" with the HTTP method "PATCH".
     * When an unauthorized client tries to update some customer:
     * -> Must return a 401 status code.
     * !Endpoint allowed for owner customer and admin!
     *
     * @throws Exception Any exception that may occur during the test.
     * @see io.github.shuoros.ecommercy.security.SecurityConfig#configure(HttpSecurity)
     */
    @Test
    void updateSomeCustomerWithAnUnauthorizedClientMustReturn401() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");
        final JSONObject customerLinks = new JSONObject(mockMvc.perform(post("/customer")//
                        .contentType(MediaType.APPLICATION_JSON)//
                        .accept(MediaType.APPLICATION_JSON)//
                        .content(content.toString()))//
                .andReturn().getResponse().getContentAsString()).getJSONObject("_links");

        // Act
        final JSONObject updateContent = new JSONObject()//
                .put("name", "Jane");
        ResultActions response = mockMvc.perform(//
                patch("/customer/SOME_USER'S_ID")//
                        .contentType(MediaType.APPLICATION_JSON)//
                        .accept(MediaType.APPLICATION_JSON)//
                        .content(updateContent.toString()));

        // Assert
        response.andDo(print()).//
                andExpect(status().isUnauthorized());
    }

    /**
     * Test the endpoint "/customer/USER_ID" with the HTTP method "PATCH".
     * When admin wants to update a customer and everything is OK:
     * -> Must update the customer and return a 200 status code and the updated customer.
     * !Endpoint allowed for owner customer and admin!
     *
     * @throws Exception Any exception that may occur during the test.
     */
    @Test
    void updateCustomerWithAdminAuthorityMustUpdateCustomerNotCreateAnotherCustomer() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");
        final JSONObject customerLinks = new JSONObject(mockMvc.perform(post("/customer")//
                        .contentType(MediaType.APPLICATION_JSON)//
                        .accept(MediaType.APPLICATION_JSON)//
                        .content(content.toString()))//
                .andReturn().getResponse().getContentAsString()).getJSONObject("_links");

        // Act
        final JSONObject updateContent = new JSONObject()//
                .put("name", "Jane");
        ResultActions response = mockMvc.perform(//
                patch(//
                        customerLinks.getJSONObject("self").getString("href"))//
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
     * Test the endpoint "/customer/WRONG_USER_ID" with the HTTP method "PATCH".
     * When admin wants to update a wrong customer:
     * -> Must return a 404 status code.
     * !Endpoint allowed for owner customer and admin!
     *
     * @throws Exception Any exception that may occur during the test.
     */
    @Test
    void updateWrongCustomerWithAdminAuthorityMustReturn404() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");
        mockMvc.perform(post("/customer")//
                .contentType(MediaType.APPLICATION_JSON)//
                .accept(MediaType.APPLICATION_JSON)//
                .content(content.toString()));

        // Act
        final JSONObject updateContent = new JSONObject()//
                .put("name", "Jane");
        ResultActions response = mockMvc.perform(//
                patch(//
                        "/customer/WRONG_USER_ID")//
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
     * Test the endpoint "/customer/USER_ID" with the HTTP method "DELETE".
     * When everything is OK:
     * -> Must delete the customer and return a 204 status code.
     * !Endpoint allowed for owner customer and admin!
     *
     * @throws Exception Any exception that may occur during the test.
     */
    @Test
    void deleteCustomerMustDeleteCustomer() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");
        final JSONObject customerLinks = new JSONObject(mockMvc.perform(post("/customer")//
                        .contentType(MediaType.APPLICATION_JSON)//
                        .accept(MediaType.APPLICATION_JSON)//
                        .content(content.toString()))//
                .andReturn().getResponse().getContentAsString()).getJSONObject("_links");

        // Act
        ResultActions response = mockMvc.perform(//
                delete(//
                        customerLinks.getJSONObject("self").getString("href"))//
                        .header("Authorization",//
                                "Bearer" + authenticateCustomer(//
                                        content.getString("email"), content.getString("password"))));

        // Assert
        response.andDo(print()).//
                andExpect(status().isNoContent());
    }

    /**
     * Test the endpoint "/customer/ANOTHER_USER'S_ID" with the HTTP method "DELETE".
     * When a customer tries to delete another customer:
     * -> Must return a 403 status code.
     * !Endpoint allowed for owner customer and admin!
     *
     * @throws Exception Any exception that may occur during the test.
     */
    @Test
    void deleteCustomerWithAnotherCustomerIdMustReturn403() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");
        mockMvc.perform(post("/customer")//
                .contentType(MediaType.APPLICATION_JSON)//
                .accept(MediaType.APPLICATION_JSON)//
                .content(content.toString()));

        // Act
        ResultActions response = mockMvc.perform(//
                delete("/customer/ANOTHER_USER'S_ID")//
                        .header("Authorization",//
                                "Bearer" + authenticateCustomer(//
                                        content.getString("email"), content.getString("password"))));

        // Assert
        response.andDo(print()).//
                andExpect(status().isForbidden())//
                .andExpect(jsonPath("$.message",//
                        is("You are not allowed to access this resource!")));
    }

    /**
     * Test the endpoint "/customer/SOME_USER'S_ID" with the HTTP method "DELETE".
     * When an unauthorized client tries to delete some customer:
     * -> Must return a 401 status code.
     * !Endpoint allowed for owner customer and admin!
     *
     * @throws Exception Any exception that may occur during the test.
     */
    @Test
    void deleteSomeCustomerWithAnUnauthorizedClientMustReturn401() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");
        mockMvc.perform(post("/customer")//
                .contentType(MediaType.APPLICATION_JSON)//
                .accept(MediaType.APPLICATION_JSON)//
                .content(content.toString()));

        // Act
        ResultActions response = mockMvc.perform(//
                delete("/customer/SOME_USER'S_ID"));

        // Assert
        response.andDo(print()).//
                andExpect(status().isUnauthorized());
    }

    /**
     * Test the endpoint "/customer/USER_ID" with the HTTP method "DELETE".
     * When admin wants to delete a customer and everything is OK:
     * -> Must delete the customer and return a 204 status code.
     * !Endpoint allowed for owner customer and admin!
     *
     * @throws Exception Any exception that may occur during the test.
     */
    @Test
    void deleteCustomerWithAdminAuthorityMustDeleteCustomer() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");
        final JSONObject customerLinks = new JSONObject(mockMvc.perform(post("/customer")//
                        .contentType(MediaType.APPLICATION_JSON)//
                        .accept(MediaType.APPLICATION_JSON)//
                        .content(content.toString()))//
                .andReturn().getResponse().getContentAsString()).getJSONObject("_links");

        // Act
        ResultActions response = mockMvc.perform(//
                delete(//
                        customerLinks.getJSONObject("self").getString("href"))//
                        .header("Authorization",//
                                "Bearer" + authenticateAdmin()));

        // Assert
        response.andDo(print()).//
                andExpect(status().isNoContent());
    }

    /**
     * Test the endpoint "/customer/WRONG_USER_ID" with the HTTP method "DELETE".
     * When admin wants to delete a wrong customer:
     * -> Must return a 404 status code.
     * !Endpoint allowed for owner customer and admin!
     *
     * @throws Exception Any exception that may occur during the test.
     */
    @Test
    void deleteWrongCustomerWithAdminAuthorityMustReturn404() throws Exception {
        // Arrange
        final JSONObject content = new JSONObject()//
                .put("name", "John")//
                .put("lastName", "Doe")//
                .put("email", "johndoe@email.com")//
                .put("password", "@JohnDoe007")//
                .put("phoneNumber", "123456789");
        mockMvc.perform(post("/customer")//
                .contentType(MediaType.APPLICATION_JSON)//
                .accept(MediaType.APPLICATION_JSON)//
                .content(content.toString()));

        // Act
        ResultActions response = mockMvc.perform(//
                delete(//
                        "/customer/WRONG_USER_ID")//
                        .header("Authorization",//
                                "Bearer" + authenticateAdmin()));

        // Assert
        response.andDo(print()).//
                andExpect(status().isNotFound());
    }

    /**
     * Authenticate a customer with the given email and password.
     *
     * @param username The email of the customer.
     * @param password The password of the customer.
     * @return The token of the customer.
     */
    private String authenticateCustomer(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(//
                new UsernamePasswordAuthenticationToken(//
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
        Authentication authentication = authenticationManager.authenticate(//
                new UsernamePasswordAuthenticationToken(//
                        "root@ecommercy.app", //
                        "1234"));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtTokenUtil.generateToken(authentication);
    }

}
