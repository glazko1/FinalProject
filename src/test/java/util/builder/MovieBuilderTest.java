package util.builder;

import entity.Movie;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.Timestamp;

import static org.testng.Assert.assertEquals;

public class MovieBuilderTest {

    private MovieBuilder builder = new MovieBuilder();
    private MovieBuilder readyBuilder = new MovieBuilder("Id");

    @BeforeClass
    public void init() {
        readyBuilder.withTitle("Title")
                .withRunningTime(150)
                .withBudget(100000000)
                .hasReleaseDate(new Timestamp(100000000));
    }

    @Test
    public void withTitle_validParameters_currentBuilder() {
        //given
        //when
        builder.withTitle("Title");
        //then
        assertEquals(builder.getTitle(), "Title");
    }

    @Test
    public void withRunningTime_validParameters_currentBuilder() {
        //given
        //when
        builder.withRunningTime(150);
        //then
        assertEquals(builder.getRunningTime(), 150);
    }

    @Test
    public void withBudget_validParameters_currentBuilder() {
        //given
        //when
        builder.withBudget(100000000);
        //then
        assertEquals(builder.getBudget(), 100000000);
    }

    @Test
    public void hasReleaseDate_validParameters_currentBuilder() {
        //given
        //when
        builder.hasReleaseDate(new Timestamp(100000000));
        //then
        assertEquals(builder.getReleaseDate(), new Timestamp(100000000));
    }

    @Test
    public void build_readyBuilder_movie() {
        //given
        Movie movie = new Movie("Id");
        movie.setTitle("Title");
        movie.setRunningTime(150);
        movie.setBudget(100000000);
        movie.setReleaseDate(new Timestamp(100000000));
        //when
        Movie result = readyBuilder.build();
        //then
        assertEquals(result, movie);
    }
}