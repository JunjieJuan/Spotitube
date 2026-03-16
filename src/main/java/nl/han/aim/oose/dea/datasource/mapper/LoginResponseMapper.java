package nl.han.aim.oose.dea.datasource.mapper;

import nl.han.aim.oose.dea.service.dto.login.LoginTokenDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginResponseMapper implements IMapper<LoginTokenDTO> {
    @Override
    public LoginTokenDTO mapToDTO(ResultSet rs) throws SQLException {
        return new LoginTokenDTO(rs.getString("username"));
    }
}