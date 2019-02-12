package dao.factory;

public class DAOFactory {

    private static final DAOFactory INSTANCE = new DAOFactory();

    public static DAOFactory getInstance() {
        return INSTANCE;
    }

    private DAOFactory() {}


}
