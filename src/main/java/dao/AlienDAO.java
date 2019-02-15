package dao;

import dao.exception.DAOException;
import entity.Alien;

import java.util.List;

public interface AlienDAO {

    Alien getAlienById(long id) throws DAOException;
    List<Alien> getAllAliens() throws DAOException;
    void addNewAlien(Alien alien) throws DAOException;
}
