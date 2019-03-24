package util.builder;

import entity.User;
import entity.UserStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.Timestamp;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

public class UserBuilderTest {

    private UserBuilder builder = new UserBuilder();
    private UserBuilder readyBuilder = new UserBuilder("Id");

    @BeforeClass
    public void init() {
        readyBuilder.withUsername("Username")
                .withFirstName("First")
                .withLastName("Last")
                .withStatus(UserStatus.ADMIN)
                .hasEmail("E-mail")
                .isBanned(false)
                .hasBirthDate(new Timestamp(100000000));
    }

    @Test
    public void withUsername_validParameters_currentBuilder() {
        //given
        //when
        builder.withUsername("Username");
        //then
        assertEquals(builder.getUsername(), "Username");
    }

    @Test
    public void withFirstName_validParameters_currentBuilder() {
        //given
        //when
        builder.withFirstName("First");
        //then
        assertEquals(builder.getFirstName(), "First");
    }

    @Test
    public void withLastName_validParameters_currentBuilder() {
        //given
        //when
        builder.withLastName("Last");
        //then
        assertEquals(builder.getLastName(), "Last");
    }

    @Test
    public void withStatus_validParameters_currentBuilder() {
        //given
        //when
        builder.withStatus(UserStatus.ADMIN);
        //then
        assertEquals(builder.getStatus(), UserStatus.ADMIN);
    }

    @Test
    public void hasEmail_validParameters_currentBuilder() {
        //given
        //when
        builder.hasEmail("E-mail");
        //then
        assertEquals(builder.getEmail(), "E-mail");
    }

    @Test
    public void isBanned_validParameters_currentBuilder() {
        //given
        //when
        builder.isBanned(false);
        //then
        assertFalse(builder.isBanned());
    }

    @Test
    public void hasBirthDate_validParameters_currentBuilder() {
        //given
        //when
        builder.hasBirthDate(new Timestamp(100000000));
        //then
        assertEquals(builder.getBirthDate(), new Timestamp(100000000));
    }

    @Test
    public void build_readyBuilder_user() {
        //given
        User user = new User("Id");
        user.setUsername("Username");
        user.setFirstName("First");
        user.setLastName("Last");
        user.setStatus(UserStatus.ADMIN);
        user.setEmail("E-mail");
        user.setBanned(false);
        user.setBirthDate(new Timestamp(100000000));
        //when
        User result = readyBuilder.build();
        //then
        assertEquals(result, user);
    }
}