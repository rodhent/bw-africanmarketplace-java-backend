package backend.lambdaschool.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import backend.lambdaschool.models.Role;
import backend.lambdaschool.models.User;
import backend.lambdaschool.models.UserRoles;
import backend.lambdaschool.models.Useremail;
import backend.lambdaschool.services.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    List<User> userList = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        Role r1 = new Role("admin");
        Role r2 = new Role("user");
        Role r3 = new Role("data");

        r1.setRoleid(1);
        r2.setRoleid(2);
        r3.setRoleid(3);

        // admin, data, user
        User u1 = new User("Test admin",
                "password",
                "admin@lambdaschool.local");
        u1.setUserid(1);
        u1.getRoles().add(new UserRoles(u1, r1));
        u1.getRoles().add(new UserRoles(u1, r2));
        u1.getRoles().add(new UserRoles(u1, r3));
        u1.getUseremails()
                .add(new Useremail(u1,
                        "admin@email.local"));
        u1.getUseremails().get(0).setUseremailid(11);
        u1.getUseremails()
                .add(new Useremail(u1,
                        "admin@mymail.local"));
        u1.getUseremails().get(1).setUseremailid(12);
        userList.add(u1);

        // data, user
        User u2 = new User("testing",
                "1234567",
                "cinnamon@lambdaschool.local");
        u2.setUserid(2);
        u2.getRoles().add(new UserRoles(u2, r2));
        u2.getRoles().add(new UserRoles(u2, r3));
        u2.getUseremails()
                .add(new Useremail(u2,
                        "cinnamon@mymail.local"));
        u2.getUseremails().get(0).setUseremailid(21);
        u2.getUseremails()
                .add(new Useremail(u2,
                        "hops@mymail.local"));
        u2.getUseremails().get(1).setUseremailid(22);
        u2.getUseremails()
                .add(new Useremail(u2,
                        "bunny@email.local"));
        u2.getUseremails().get(2).setUseremailid(23);
        userList.add(u2);

        // user
        User u3 = new User("Test barnbarn",
                "ILuvM4th!",
                "barnbarn@lambdaschool.local");
        u3.setUserid(3);
        u3.getRoles().add(new UserRoles(u3, r2));
        u3.getUseremails()
                .add(new Useremail(u3,
                        "barnbarn@email.local"));
        u3.getUseremails().get(0).setUseremailid(31);
        userList.add(u3);

        User u4 = new User("Test puttat",
                "password",
                "puttat@school.lambda");
        u4.setUserid(4);
        u4.getRoles().add(new UserRoles(u4, r2));
        userList.add(u4);

        User u5 = new User("test misskitty",
                "password",
                "misskitty@school.lambda");
        u5.setUserid(5);
        u5.getRoles().add(new UserRoles(u5, r2));
        userList.add(u5);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void listAllUsers() throws Exception {
        String apiUrl = "/users/users";
        Mockito.when(userService.findAll()).thenReturn(userList);

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);
        MvcResult results = mockMvc.perform(rb).andReturn();
        String testResult = results.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        String expectedResult = mapper.writeValueAsString(userList);

        assertEquals(expectedResult, testResult);
    }

    @Test
    public void getUserById() throws Exception {
        String apiUrl = "/users/user/1";
        Mockito.when(userService.findUserById(1)).thenReturn(userList.get(0));
        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);
        MvcResult results = mockMvc.perform(rb).andReturn();
        String testResult = results.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        String expectedResult = mapper.writeValueAsString(userList.get(0));
        assertEquals(expectedResult, testResult);
    }

    @Test
    public void getUserByName() throws Exception {
        String apiUrl = "/users/user/name/testing";
        Mockito.when(userService.findByName("testing")).thenReturn(userList.get(0));
        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);

        MvcResult results = mockMvc.perform(rb).andReturn();
        String testResult = results.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        String expectedResult = mapper.writeValueAsString(userList.get(0));
        System.out.println("expect: " + expectedResult);
        System.out.println("expect: " + testResult);
        assertEquals(expectedResult, testResult);
    }

    @Test
    public void getUserLikeName() throws Exception{
        String apiUrl = "/users/user/name/like/tes";
        List<User> usersWithTty = new ArrayList<>();
        usersWithTty.add(userList.get(0));
        Mockito.when(userService.findByNameContaining("tes")).thenReturn(usersWithTty);
        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(rb).andReturn();
        String testResult = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        String exResult = mapper.writeValueAsString(usersWithTty);
        assertEquals(exResult, testResult);
    }

    @Test
    public void addNewUser() throws Exception {
        String apiUrl = "/users/user";
        Role r1 = new Role("student");
        Role r2 = new Role("prof");
        Role r3 = new Role("intern");

        r1.setRoleid(4);
        r2.setRoleid(5);
        r3.setRoleid(6);
        String userName = "Test user";
        User u2 = new User(userName,
                "1234567",
                "user@lambdaschool.local");
        u2.setUserid(35);
        u2.getRoles().add(new UserRoles(u2, r2));
        u2.getRoles().add(new UserRoles(u2, r3));
        u2.getUseremails()
                .add(new Useremail(u2,
                        "cinnamon@mymail.local"));
        u2.getUseremails().get(0).setUseremailid(21);
        u2.getUseremails()
                .add(new Useremail(u2,
                        "hops@mymail.local"));
        u2.getUseremails().get(1).setUseremailid(22);
        u2.getUseremails()
                .add(new Useremail(u2,
                        "bunny@email.local"));
        u2.getUseremails().get(2).setUseremailid(23);

        ObjectMapper mapper = new ObjectMapper();
        String userString = mapper.writeValueAsString(u2);
        Mockito.when(userService.save(any(User.class))).thenReturn(u2);

        RequestBuilder rb = MockMvcRequestBuilders.post(apiUrl)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(userString);
        mockMvc.perform(rb).andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void updateFullUser() throws Exception {
        String apiUrl = "/users/user/35";
        Role r1 = new Role("student");
        Role r2 = new Role("prof");
        Role r3 = new Role("intern");

        r1.setRoleid(4);
        r2.setRoleid(5);
        r3.setRoleid(6);
        String userName = "Test username";
        User u2 = new User(userName,
                "1234567",
                "username@lambdaschool.local");
        u2.getRoles().add(new UserRoles(u2, r2));
        u2.getRoles().add(new UserRoles(u2, r3));
        u2.getUseremails()
                .add(new Useremail(u2,
                        "cinnamon@mymail.local"));
        u2.getUseremails().get(0).setUseremailid(21);
        u2.getUseremails()
                .add(new Useremail(u2,
                        "hops@mymail.local"));
        u2.getUseremails().get(1).setUseremailid(22);
        u2.getUseremails()
                .add(new Useremail(u2,
                        "bunny@email.local"));
        u2.getUseremails().get(2).setUseremailid(23);

        ObjectMapper mapper = new ObjectMapper();
        String userString = mapper.writeValueAsString(u2);
        Mockito.when(userService.save(any(User.class))).thenReturn(u2);

        RequestBuilder rb = MockMvcRequestBuilders.put(apiUrl)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(userString);
        mockMvc.perform(rb).andExpect(status().is2xxSuccessful()).andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void updateUser() {
    }

    @Test
    public void deleteUserById() {
    }
}