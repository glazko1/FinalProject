package util.builder;

import entity.Alien;
import entity.Edit;
import entity.User;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.Timestamp;

import static org.mockito.Mockito.mock;
import static org.testng.Assert.assertEquals;

public class EditBuilderTest {

    private EditBuilder builder = new EditBuilder();
    private EditBuilder readyBuilder = new EditBuilder("Id");
    private Alien alien = mock(Alien.class);
    private User user = mock(User.class);

    @BeforeClass
    public void init() {
        readyBuilder.aboutAlien(alien)
                .suggestedBy(user)
                .withText("Text")
                .withEditDateTime(new Timestamp(100000000));
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
    public void suggestedBy_validParameters_currentBuilder() {
        //given
        //when
        builder.suggestedBy(user);
        //then
        assertEquals(builder.getUser(), user);
    }

    @Test
    public void withText_validParameters_currentBuilder() {
        //given
        //when
        builder.withText("Text");
        //then
        assertEquals(builder.getEditText(), "Text");
    }

    @Test
    public void withEditDateTime_validParameters_currentBuilder() {
        //given
        //when
        builder.withEditDateTime(new Timestamp(100000000));
        //then
        assertEquals(builder.getEditDateTime(), new Timestamp(100000000));
    }

    @Test
    public void build_readyBuilder_edit() {
        //given
        Edit edit = new Edit("Id");
        edit.setAlien(alien);
        edit.setUser(user);
        edit.setEditText("Text");
        edit.setEditDateTime(new Timestamp(100000000));
        //when
        Edit result = readyBuilder.build();
        //then
        assertEquals(result, edit);
    }
}