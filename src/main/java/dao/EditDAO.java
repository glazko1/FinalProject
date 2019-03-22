package dao;

import dao.exception.DAOException;
import entity.Edit;

import java.util.List;

public interface EditDAO {

    void addNewEdit(Edit edit) throws DAOException;
    List<Edit> getAllSuggestedEdits() throws DAOException;
    Edit getSuggestedEdit(String editId) throws DAOException;
    void deleteEdit(String editId) throws DAOException;
}
