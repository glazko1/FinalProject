package util.builder;

import entity.Alien;
import entity.Feedback;
import entity.User;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.Timestamp;

import static org.mockito.Mockito.mock;
import static org.testng.Assert.assertEquals;

public class FeedbackBuilderTest {

    private FeedbackBuilder builder = new FeedbackBuilder();
    private FeedbackBuilder readyBuilder = new FeedbackBuilder("Id");
    private Alien alien = mock(Alien.class);
    private User user = mock(User.class);

    @BeforeClass
    public void init() {
        readyBuilder.aboutAlien(alien)
                .leftByUser(user)
                .withText("Text")
                .withRating(5)
                .withFeedbackDateTime(new Timestamp(100000000));
    }

    @Test
    public void aboutAlien_validParameters_currentBuilder() {
        //given
        //when
        builder.aboutAlien(alien);
        //then
        assertEquals(builder.getAlien(), alien);
    }

    @Test
    public void leftByUser_validParameters_currentBuilder() {
        //given
        //when
        builder.leftByUser(user);
        //then
        assertEquals(builder.getUser(), user);
    }

    @Test
    public void withText_validParameters_currentBuilder() {
        //given
        //when
        builder.withText("Text");
        //then
        assertEquals(builder.getFeedbackText(), "Text");
    }

    @Test
    public void withRating_validParameters_currentBuilder() {
        //given
        //when
        builder.withRating(5);
        //then
        assertEquals(builder.getRating(), 5);
    }

    @Test
    public void withFeedbackDateTime_validParameters_currentBuilder() {
        //given
        //when
        builder.withFeedbackDateTime(new Timestamp(100000000));
        //then
        assertEquals(builder.getFeedbackDateTime(), new Timestamp(100000000));
    }

    @Test
    public void build_readyBuilder_feedback() {
        //given
        Feedback feedback = new Feedback("Id");
        feedback.setAlien(alien);
        feedback.setUser(user);
        feedback.setFeedbackText("Text");
        feedback.setRating(5);
        feedback.setFeedbackDateTime(new Timestamp(100000000));
        //when
        Feedback result = readyBuilder.build();
        //then
        assertEquals(result, feedback);
    }
}