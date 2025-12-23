package fm.project.repository.DAO.interfaces;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

// NOTE: this code was copied from github repo 2332_cognizant_java_fs

public interface DAOInterface<T> {

    // CREATE
    public Integer create(T entity) throws SQLException;

    // READ BY ID
    public Optional<T> findById(Integer id) throws SQLException;

    // READ ALL
    public List<T> findAll() throws SQLException;

    // UPDATE
    public T updateById(T entity) throws SQLException;
    
    // DELETE
    public boolean deleteById(Integer id) throws SQLException;
}
