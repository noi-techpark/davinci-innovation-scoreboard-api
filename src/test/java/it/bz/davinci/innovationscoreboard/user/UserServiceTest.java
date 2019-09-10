package it.bz.davinci.innovationscoreboard.user;

import it.bz.davinci.innovationscoreboard.user.dto.NewUserRequest;
import it.bz.davinci.innovationscoreboard.user.dto.UserResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import({UserService.class, BCryptPasswordEncoder.class})
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    public void givenEmailAndPassword_CreateNewUser() {
        String email = "test@noi.bz.it";
        String password = "abcdefgh";
        NewUserRequest newUserRequest = getNewUserRequest(email, password);


        final UserResponse user = userService.createUser(newUserRequest);

        assertThat(user.getEmail(), equalTo(email));
        assertThat(user.getId(), is(notNullValue()));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void preventCreationOfDuplicateUser() {
        String email = "test@noi.bz.it";
        String password = "abcdefgh";
        NewUserRequest newUserRequest = getNewUserRequest(email, password);

        userService.createUser(newUserRequest);
        userService.createUser(newUserRequest);
    }

    private NewUserRequest getNewUserRequest(String email, String password) {
        NewUserRequest newUserRequest = new NewUserRequest();
        newUserRequest.setEmail(email);
        newUserRequest.setPassword(password);
        return newUserRequest;
    }
}