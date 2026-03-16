package nl.han.aim.oose.dea.datasource.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface IMapper<T> {
    T mapToDTO(ResultSet rs) throws SQLException;
}