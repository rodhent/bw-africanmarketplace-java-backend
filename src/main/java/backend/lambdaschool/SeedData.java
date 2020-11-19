package backend.lambdaschool;

import backend.lambdaschool.models.*;
import backend.lambdaschool.services.ItemService;
import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import backend.lambdaschool.services.RoleService;
import backend.lambdaschool.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

/**
 * SeedData puts both known and random data into the database. It implements CommandLineRunner.
 * <p>
 * CoomandLineRunner: Spring Boot automatically runs the run method once and only once
 * after the application context has been loaded.
 */
@Transactional
@Component
public class SeedData
        implements CommandLineRunner
{
    /**
     * Connects the Role Service to this process
     */
    @Autowired
    RoleService roleService;

    /**
     * Connects the user service to this process
     */
    @Autowired
    UserService userService;

    @Autowired
    ItemService itemService;

    /**
     * Generates test, seed data for our application
     * First a set of known data is seeded into our database.
     * Second a random set of data using Java Faker is seeded into our database.
     * Note this process does not remove data from the database. So if data exists in the database
     * prior to running this process, that data remains in the database.
     *
     * @param args The parameter is required by the parent interface but is not used in this process.
     */
    @Transactional
    @Override
    public void run(String[] args) throws
            Exception
    {
        userService.deleteAll();
        roleService.deleteAll();
        itemService.deleteAllItems();
        Role r1 = new Role("admin");
        Role r2 = new Role("user");
        Role r3 = new Role("data");

        r1 = roleService.save(r1);
        r2 = roleService.save(r2);
        r3 = roleService.save(r3);

        // admin, data, user
        User u1 = new User("admin",
                           "password",
                           "admin@lambdaschool.local");
        u1.getRoles()
                .add(new UserRoles(u1, r1));
        u1.getRoles()
                .add(new UserRoles(u1, r2));
        u1.getRoles()
                .add(new UserRoles(u1, r3));
        u1.getUseremails()
                .add(new Useremail(u1,
                                   "admin@email.local"));
        u1.getUseremails()
                .add(new Useremail(u1,
                                   "admin@mymail.local"));

        userService.save(u1);

        // data, user
        User u2 = new User("cinnamon",
                           "1234567",
                           "cinnamon@lambdaschool.local");
        u2.getRoles()
                .add(new UserRoles(u2, r2));
        u2.getRoles()
                .add(new UserRoles(u2, r3));
        u2.getUseremails()
                .add(new Useremail(u2,
                                   "cinnamon@mymail.local"));
        u2.getUseremails()
                .add(new Useremail(u2,
                                   "hops@mymail.local"));
        u2.getUseremails()
                .add(new Useremail(u2,
                                   "bunny@email.local"));
        userService.save(u2);

        // user
        User u3 = new User("barnbarn",
                           "ILuvM4th!",
                           "barnbarn@lambdaschool.local");
        u3.getRoles()
                .add(new UserRoles(u3, r2));
        u3.getUseremails()
                .add(new Useremail(u3,
                                   "barnbarn@email.local"));
        userService.save(u3);

        User u4 = new User("puttat",
                           "password",
                           "puttat@school.lambda");
        u4.getRoles()
                .add(new UserRoles(u4, r2));
        userService.save(u4);

        User u5 = new User("misskitty",
                           "password",
                           "misskitty@school.lambda");
        u5.getRoles()
                .add(new UserRoles(u5, r2));
        userService.save(u5);

        Item i1 = new Item("macbook pro","laptop","this is dummy data","af",1000,u1);
        i1= itemService.save(i1);

        Item i2 = new Item("Gaming desktop","furniture","Smooth, steady, and modern","AF",400,u2);
        i2 = itemService.save(i2);

        Item i3 = new Item("65 Inch Tv","Acer","1ms, 144hz, 4k, curved","AF",1200,u3);
        i3 = itemService.save(i3);

        Item i4 = new Item("Punching Bag","Nike","100 pounds, red and black leather ","AF",100,u4);
        i4 = itemService.save(i4);

        Item i5 = new Item("Protein Sharms","Research","Not meant for human consuming, research purposes only 15mL","AF",89,u5);
        i5 = itemService.save(i5);
    }
}