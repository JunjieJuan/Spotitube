package nl.han.aim.oose.dea.datasource.mapper;

import com.mysql.cj.protocol.Resultset;
import nl.han.aim.oose.dea.service.dto.login.LoginTokenDTO;

import java.sql.SQLException;

public class LoginResponseMapper implements IMapper<LoginTokenDTO>{
    @Override
    public LoginTokenDTO mapToDTO(Resultset rs) throws SQLException {
        rs.next();
        return new LoginResponseDTO(rs.getString("fullname"), rs.getString("token"));
    }
}
