package dao;

import dao.exception.DAOException;
import entity.Edit;

import java.util.List;

public interface EditDAO {

    void addNewEdit(Edit edit) throws DAOException;
    List<Edit> getAllSuggestedEdits() throws DAOException;
    Edit getSuggestedEdit(long editId) throws DAOException;
    void deleteEdit(long editId) throws DAOException;
}
