import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ImportResource;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContext;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import ru.danileyko.config.TestContext;
import ru.danileyko.config.WebConfig;
import ru.danileyko.config.WebInit;
import ru.danileyko.controller.IndexController;
import ru.danileyko.model.Role;
import ru.danileyko.model.User;
import ru.danileyko.service.UserService;

import javax.activation.DataSource;
import javax.annotation.Resource;
import javax.naming.NamingException;
import java.lang.annotation.Target;

/**
 * Created by danil on 26.03.2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {TestContext.class})
public class IndexControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @Autowired
    UserService userService;

    @Before
    public void setup()
    {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    @WithMockUser(username = "admin",value = "admin")
    @WithUserDetails("ROLE_ADMIN")
    public void testGetIndexPage() throws Exception{
        this.mockMvc.perform(get("/").header("host", "localhost:80"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    @WithMockUser(username = "admin",value = "admin")
    @WithUserDetails("ROLE_ADMIN")
    public void testGetAdminPage() throws Exception{
        this.mockMvc.perform(get("/admin").header("host", "localhost:80"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin"));
        //check delete user
        if(userService.getUser("junit")==null){
            System.out.println("User not found!");
            return;
        }
        else {
            userService.delete(userService.getUser("junit").getId());
            Assert.assertNull(userService.getUser("junit"));
        }
        //check enable/disable user
        userService.userLock(userService.getUser("test10").getId());
        Assert.assertEquals(false,userService.getUser("test10").isEnabled());
        userService.userUnLock(userService.getUser("test10").getId());
        Assert.assertEquals(true,userService.getUser("test10").isEnabled());
    }

    @Test
    public void testGetRegisterPage() throws Exception{
        this.mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"));

        User testUser = new User();

        testUser.setUsername("junit");
        testUser.setPassword("junit");
        testUser.setEnabled(true);
        //Save test
        Assert.assertNull(userService.getUser("junit"));
        userService.save(testUser);
        Assert.assertNotNull(userService.getUser("junit"));

    }
}
