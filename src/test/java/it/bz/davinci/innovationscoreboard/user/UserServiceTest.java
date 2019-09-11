package it.bz.davinci.innovationscoreboard.user;

import it.bz.davinci.innovationscoreboard.user.dto.NewUserRequest;
import it.bz.davinci.innovationscoreboard.user.dto.UserResponse;
import it.bz.davinci.innovationscoreboard.user.jpa.UserRepository;
import it.bz.davinci.innovationscoreboard.user.jpa.UserRoleRepository;
import it.bz.davinci.innovationscoreboard.utils.rest.CollectionResponse;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import static com.github.npathai.hamcrestopt.OptionalMatchers.isEmpty;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import({UserService.class, BCryptPasswordEncoder.class})
public class UserServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    UserService userService;

    @After
    public void tearDown() {
        userRepository.deleteAll();
        userRoleRepository.deleteAll();
    }

    @Test
    public void givenEmailAndPassword_CreateNewUser() {
        String email = "test@noi.bz.it";
        String password = "abcdefgh";
        NewUserRequest newUserRequest = getNewUserRequest(email, password);


        final UserResponse user = userService.createUser(newUserRequest);

        assertThat(user.getEmail(), equalTo(email));
        assertThat(user.getId(), is(notNullValue()));
    }

    @Test
    public void listUsers_ifPresentInDB() {
        NewUserRequest newUserRequest = getNewUserRequest("test@noi.bz.it", "abcdefghi");
        final UserResponse user = userService.createUser(newUserRequest);

        final CollectionResponse<UserResponse> all = userService.findAll();

        assertThat(all.getResults(), hasSize(1));
    }

    @Test
    public void deleteUser_ifPresentInDB() {
        NewUserRequest newUserRequest = getNewUserRequest("test@noi.bz.it", "abcdefghi");
        final UserResponse user = userService.createUser(newUserRequest);

        userService.deleteUser(user.getId());

        assertThat(userRepository.findByEmail("test@noi.bz.it"), isEmpty());
        assertThat(userRoleRepository.findAll(), hasSize(0));
    }

    @Test
    public void deleteUserRolesOnlyForUser_ifPresentInDB() {
        NewUserRequest newUserRequest = getNewUserRequest("test@noi.bz.it", "abcdefghi");
        NewUserRequest newUserRequest2 = getNewUserRequest("test2@noi.bz.it", "abcdefghi");
        final UserResponse user = userService.createUser(newUserRequest);
        final UserResponse user2 = userService.createUser(newUserRequest2);

        userService.deleteUser(user.getId());

        assertThat(userRepository.findByEmail(user2.getEmail()), not(isEmpty()));
        assertThat(userRoleRepository.findAll(), hasSize(1));
    }

    @Test(expected = EntityNotFoundException.class)
    public void failWithNotFoundWhenDeleteUser_ifNotPresentInDB() {
        userService.deleteUser(10);
    }

    private NewUserRequest getNewUserRequest(String email, String password) {
        NewUserRequest newUserRequest = new NewUserRequest();
        newUserRequest.setEmail(email);
        newUserRequest.setPassword(password);
        return newUserRequest;
    }
}