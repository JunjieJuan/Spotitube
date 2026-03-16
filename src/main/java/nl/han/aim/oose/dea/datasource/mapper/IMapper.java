package nl.han.aim.oose.dea.datasource.mapper;

import com.mysql.cj.protocol.Resultset;

import java.sql.SQLException;

public interface IMapper<T> {
    T mapToDTO(Resultset rs) throws SQLException;
}
