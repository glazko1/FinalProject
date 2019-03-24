package dao;

import dao.exception.DAOException;
import entity.Edit;

import java.util.List;

public interface EditDAO {

    /**
     * Adds new edit to some source of information (file, database, etc.)
     * according to all fields in given object.
     * @param edit object with information about new edit.
     * @throws DAOException if error occurred during the process of adding
     * information.
     */
    void addNewEdit(Edit edit) throws DAOException;

    /**
     * Creates and returns list of edits according to some source of information
     * (file, database, etc.)
     * @return list of edits.
     * @throws DAOException if error occurred during the process of getting
     * information.
     */
    List<Edit> getAllSuggestedEdits() throws DAOException;

    /**
     * Creates and returns edit according to some source of information (file,
     * database, etc.) and given parameter (edit ID).
     * @param editId ID of edit to find.
     * @return object with information about edit with given ID.
     * @throws DAOException if error occurred during the process of getting
     * information.
     */
    Edit getSuggestedEdit(String editId) throws DAOException;

    /**
     * Deletes edit with given ID from some source of information (file,
     * database, etc.)
     * @param editId ID of edit to delete.
     * @throws DAOException if error occurred during the process of deleting
     * information.
     */
    void deleteEdit(String editId) throws DAOException;
}
