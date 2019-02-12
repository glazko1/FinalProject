package dao.impl;

import connection.ProxyConnection;
import dao.AlienDAO;
import entity.Alien;
import pool.DatabaseConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlienSQL implements AlienDAO {

    private static final AlienSQL INSTANCE = new AlienSQL();

    public static AlienSQL getInstance() {
        return INSTANCE;
    }

    private AlienSQL() {}

    private static final String GET_ALIEN_BY_ID_SQL = "SELECT AlienId, AlienName, MovieId, Planet, Description, AverageRating FROM Alien WHERE AlienId = ?";
    private static final String GET_ALL_ALIENS_SQL = "SELECT AlienId, AlienName, MovieId, Planet, Description, AverageRating FROM Alien";
    private DatabaseConnectionPool pool = DatabaseConnectionPool.getInstance();

    @Override
    public Alien getAlienById(long id) {
        try (ProxyConnection proxyConnection = pool.getConnection()) {
            Connection connection = proxyConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ALIEN_BY_ID_SQL);
            statement.setLong(1, id);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                return new Alien(set.getLong(1),
                        set.getString(2),
                        set.getInt(3),
                        set.getString(4),
                        set.getString(5),
                        set.getDouble(6));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Alien> getAllAliens() {
        List<Alien> aliens = new ArrayList<>();
        try (ProxyConnection proxyConnection = pool.getConnection()) {
            Connection connection = proxyConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ALL_ALIENS_SQL);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                Alien alien = new Alien(set.getLong(1),
                        set.getString(2),
                        set.getInt(3),
                        set.getString(4),
                        set.getString(5),
                        set.getDouble(6));
                aliens.add(alien);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return aliens;
    }
}
