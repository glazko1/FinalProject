package service;

import service.exception.ServiceException;

public interface CommonService {

    void signIn(String username, String password) throws ServiceException;
}
