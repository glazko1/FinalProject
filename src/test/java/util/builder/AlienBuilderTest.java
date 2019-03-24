package util.builder;

import entity.Alien;
import entity.Movie;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.testng.Assert.assertEquals;

public class AlienBuilderTest {

    private AlienBuilder builder = new AlienBuilder();
    private AlienBuilder readyBuilder = new AlienBuilder("Id");
    private Movie movie = mock(Movie.class);

    @BeforeClass
    public void init() {
        readyBuilder.withName("Name")
                .fromMovie(movie)
                .fromPlanet("Planet")
                .hasDescription("Description")
                .withAverageRating(5.0)
                .withPathToImage("Path");
    }

    @Test
    public void withName_validParameters_currentBuilder() {
        //given
        //when
        builder.withName("AlienName");
        //then
        assertEquals(builder.getAlienName(), "AlienName");
    }

    @Test
    public void fromMovie_validParameters_currentBuilder() {
        //given
        //when
        builder.fromMovie(movie);
        //then
        assertEquals(builder.getMovie(), movie);
    }

    @Test
    public void fromPlanet_validParameters_currentBuilder() {
        //given
        //when
        builder.fromPlanet("Planet");
        //then
        assertEquals(builder.getPlanet(), "Planet");
    }

    @Test
    public void hasDescription_validParameters_currentBuilder() {
        //given
        //when
        builder.hasDescription("Description");
        //then
        assertEquals(builder.getDescription(), "Description");
    }

    @Test
    public void withAverageRating_validParameters_currentBuilder() {
        //given
        //when
        builder.withAverageRating(5.0);
        //then
        assertEquals(builder.getAverageRating(), 5.0);
    }

    @Test
    public void withPathToImage_validParameters_currentBuilder() {
        //given
        //when
        builder.withPathToImage("Path");
        //then
        assertEquals(builder.getImagePath(), "Path");
    }

    @Test
    public void build_readyBuilder_alien() {
        //given
        Alien alien = new Alien("Id");
        alien.setAlienName("Name");
        alien.setMovie(movie);
        alien.setPlanet("Planet");
        alien.setDescription("Description");
        alien.setAverageRating(5.0);
        alien.setImagePath("Path");
        //when
        Alien result = readyBuilder.build();
        //then
        assertEquals(result, alien);
    }
}