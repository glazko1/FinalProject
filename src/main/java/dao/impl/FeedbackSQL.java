package dao.impl;

import connection.ProxyConnection;
import dao.FeedbackDAO;
import dao.exception.DAOException;
import entity.Feedback;
import pool.DatabaseConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FeedbackSQL implements FeedbackDAO {

    private static final FeedbackSQL INSTANCE = new FeedbackSQL();

    public static FeedbackSQL getInstance() {
        return INSTANCE;
    }

    private FeedbackSQL() {}

    private static final String GET_FEEDBACK_BY_ID_SQL = "SELECT FeedbackId, AlienId, UserId, Rating, FeedbackText, FeedbackDateTime FROM Feedback WHERE FeedbackId = ?";
    private DatabaseConnectionPool pool = DatabaseConnectionPool.getInstance();

    @Override
    public Feedback getFeedbackById(long id) {
        try (ProxyConnection proxyConnection = pool.getConnection()) {
            Connection connection = proxyConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_FEEDBACK_BY_ID_SQL);
            statement.setLong(1, id);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                return new Feedback(set.getLong(1),
                        set.getLong(2),
                        set.getLong(3),
                        set.getInt(4),
                        set.getString(5),
                        set.getDate(6));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
