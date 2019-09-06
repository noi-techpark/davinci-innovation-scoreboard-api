package it.bz.davinci.innovationscoreboard.it;

import it.bz.davinci.innovationscoreboard.user.jpa.UserRepository;
import it.bz.davinci.innovationscoreboard.user.jpa.UserRoleRepository;
import it.bz.davinci.innovationscoreboard.user.model.ApiUser;
import it.bz.davinci.innovationscoreboard.user.model.Role;
import it.bz.davinci.innovationscoreboard.user.model.UserRole;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

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
    public void givenValidCredentials_LoginUser() throws Exception {
        final ResponseEntity<Object> responseEntity = template.postForEntity("/v1/authenticate", new Credentials("info@davinici.bz.it", "password"), Object.class);
        assertThat(responseEntity.getStatusCode(), equalTo(HttpStatus.OK));
    }

    class Credentials {
        private String email;
        private String password;

        public Credentials(String email, String password) {
            this.email = email;
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }
    }


}
