package nl.han.aim.oose.dea.datasource.mapper;
import nl.han.aim.oose.dea.service.dto.LoginTokenDTO;
import org.junit.jupiter.api.Test;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class LoginResponseMapperTest {

    @Test
    void mapToDTO_shouldMapUsername() throws SQLException {
        // Arrange
        ResultSet rs = mock(ResultSet.class);
        when(rs.getString("username")).thenReturn("junjie");

        LoginResponseMapper mapper = new LoginResponseMapper();

        // Act
        LoginTokenDTO result = mapper.mapToDTO(rs);

        // Assert
        assertEquals("junjie", result.getUser());
    }
}
