package service;

import entity.Edit;
import service.exception.ServiceException;

import java.util.List;

public interface AlienSpecialistService {

    void addAlien(String alienName, String planet, String description, String movieTitle) throws ServiceException;
    void editAlien(long alienId, String movieTitle, String planet, String description) throws ServiceException;
    List<Edit> viewAllSuggestedEdits() throws ServiceException;
    Edit viewSuggestedEdit(long editId) throws ServiceException;
    void acceptEdit(long editId) throws ServiceException;
    void deleteEdit(long editId) throws ServiceException;
    void sendNotification(long userId, String notificationText) throws ServiceException;
}
