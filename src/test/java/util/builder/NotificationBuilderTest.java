package util.builder;

import entity.Notification;
import entity.User;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.Timestamp;

import static org.mockito.Mockito.mock;
import static org.testng.Assert.assertEquals;

public class NotificationBuilderTest {

    private NotificationBuilder builder = new NotificationBuilder();
    private NotificationBuilder readyBuilder = new NotificationBuilder("Id");
    private User user = mock(User.class);

    @BeforeClass
    public void init() {
        readyBuilder.toUser(user)
                .withText("Text")
                .withNotificationDateTime(new Timestamp(100000000));
    }

    @Test
    public void toUser_validParameters_currentBuilder() {
        //given
        //when
        builder.toUser(user);
        //then
        assertEquals(builder.getUser(), user);
    }

    @Test
    public void withText_validParameters_currentBuilder() {
        //given
        //when
        builder.withText("Text");
        //then
        assertEquals(builder.getNotificationText(), "Text");
    }

    @Test
    public void withNotificationDateTime_validParameters_currentBuilder() {
        //given
        //when
        builder.withNotificationDateTime(new Timestamp(100000000));
        //then
        assertEquals(builder.getNotificationDateTime(), new Timestamp(100000000));
    }

    @Test
    public void build_readyBuilder_notification() {
        //given
        Notification notification = new Notification("Id");
        notification.setUser(user);
        notification.setNotificationText("Text");
        notification.setNotificationDateTime(new Timestamp(100000000));
        //when
        Notification result = readyBuilder.build();
        //then
        assertEquals(result, notification);
    }
}