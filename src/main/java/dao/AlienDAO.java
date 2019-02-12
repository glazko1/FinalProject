package dao;

import entity.Alien;

import java.util.List;

public interface AlienDAO {

    Alien getAlienById(long id);
    List<Alien> getAllAliens();
}
