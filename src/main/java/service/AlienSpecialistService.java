package service;

import entity.Edit;
import service.exception.ServiceException;

import java.util.List;

public interface AlienSpecialistService {

    void addAlien(String alienName, String planet, String description, String movieTitle, String imagePath) throws ServiceException;
    void editAlien(String alienId, String movieTitle, String planet, String description) throws ServiceException;
    List<Edit> viewAllSuggestedEdits() throws ServiceException;
    Edit viewSuggestedEdit(String editId) throws ServiceException;
    void acceptEdit(String editId) throws ServiceException;
    void deleteEdit(String editId) throws ServiceException;
    void sendNotification(String userId, String notificationText) throws ServiceException;
    void sendNotificationToAll(String notificationText) throws ServiceException;
    void deleteAlien(String alienId) throws ServiceException;
}
