package it.bz.davinci.innovationscoreboard.it;

import it.bz.davinci.innovationscoreboard.it.util.Credentials;
import it.bz.davinci.innovationscoreboard.it.util.LoginResponseBody;
import it.bz.davinci.innovationscoreboard.user.dto.NewUserRequest;
import it.bz.davinci.innovationscoreboard.user.dto.UserResponse;
import it.bz.davinci.innovationscoreboard.user.jpa.UserRepository;
import it.bz.davinci.innovationscoreboard.user.jpa.UserRoleRepository;
import it.bz.davinci.innovationscoreboard.user.model.ApiUser;
import it.bz.davinci.innovationscoreboard.user.model.Role;
import it.bz.davinci.innovationscoreboard.user.model.UserRole;
import org.jetbrains.annotations.NotNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebSecurityIT {

    @Autowired
    private TestRestTemplate template;

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Before
    public void setup() {
        final ApiUser admin = new ApiUser();
        admin.setPassword("$2a$10$DKKUcKqQlNk.LaQLAeBZiejD2eG03vG86XeLuIwYKWeInN.Gs1nvm");
        admin.setEmail("info@davinici.bz.it");
        admin.setEnabled(true);
        userRepository.save(admin);

        final UserRole adminRole = new UserRole();
        adminRole.setEmail("info@davinici.bz.it");
        adminRole.setRole(Role.ROLE_ADMIN);

        userRoleRepository.save(adminRole);

    }

    @After
    public void tearDown() {
        userRepository.deleteAll();
        userRoleRepository.deleteAll();
    }


    @Test
    public void givenValidCredentials_LoginUser() {
        final ResponseEntity<LoginResponseBody> responseEntity = loginAsAdmin();
        assertThat(responseEntity.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    public void givenUnauthenticatedUser_dontAllowAdminProtectedEndpoints() {
        final NewUserRequest newUserRequest = getNewUserRequest();

        final ResponseEntity<UserResponse> createNewUserResponse = template.postForEntity("/v1/users/new", newUserRequest, UserResponse.class);
        assertThat(createNewUserResponse.getStatusCode(), equalTo(HttpStatus.FORBIDDEN));
    }

    @Test
    public void givenAdminToken_allowAdminProtectedEndpoints() {
        final ResponseEntity<LoginResponseBody> responseEntity = loginAsAdmin();

        final HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + responseEntity.getBody().getToken());
        final NewUserRequest newUserRequest = getNewUserRequest();

        final ResponseEntity<UserResponse> createNewUserResponse = template.postForEntity("/v1/users/new", new HttpEntity<>(newUserRequest, headers), UserResponse.class);
        assertThat(createNewUserResponse.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(createNewUserResponse.getBody().getEmail(), equalTo(newUserRequest.getEmail()));
    }

    @NotNull
    private NewUserRequest getNewUserRequest() {
        final NewUserRequest newUserRequest = new NewUserRequest();
        newUserRequest.setPassword("test1234");
        newUserRequest.setEmail("test@davinci.bz.it");
        return newUserRequest;
    }

    private ResponseEntity<LoginResponseBody> loginAsAdmin() {
        return template.postForEntity("/v1/authenticate", new Credentials("info@davinici.bz.it", "password"), LoginResponseBody.class);
    }


}
