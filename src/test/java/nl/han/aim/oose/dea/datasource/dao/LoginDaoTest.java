package nl.han.aim.oose.dea.datasource.dao;

import nl.han.aim.oose.dea.datasource.databaseconnection.IDatabaseConnector;
import nl.han.aim.oose.dea.datasource.mapper.LoginResponseMapper;
import nl.han.aim.oose.dea.service.dto.LoginTokenDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginDaoTest {

    @Mock
    private IDatabaseConnector databaseConnector;

    @Mock
    private LoginResponseMapper loginResponseMapper;

    @InjectMocks
    private LoginDao loginDao;

    @Test
    void checkUser_shouldReturnToken_whenUserExists() throws SQLException {
        // Arrange
        Connection connection = mock(Connection.class);
        PreparedStatement selectStatement = mock(PreparedStatement.class);
        PreparedStatement updateStatement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);
        LoginTokenDTO tokenDTO = new LoginTokenDTO("user");

        when(databaseConnector.connect()).thenReturn(connection);
        when(connection.prepareStatement(startsWith("SELECT"))).thenReturn(selectStatement);
        when(connection.prepareStatement(startsWith("UPDATE"))).thenReturn(updateStatement);
        when(selectStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(loginResponseMapper.mapToDTO(resultSet)).thenReturn(tokenDTO);

        // Act
        LoginTokenDTO result = loginDao.checkUser("user", "pass");

        // Assert
        assertNotNull(result);
        assertEquals("user", result.getUser());
        verify(updateStatement).executeUpdate();
    }

    @Test
    void checkUser_shouldReturnNull_whenUserNotFound() throws SQLException {
        // Arrange
        Connection connection = mock(Connection.class);
        PreparedStatement statement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);

        when(databaseConnector.connect()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        // Act
        LoginTokenDTO result = loginDao.checkUser("user", "wrong");

        // Assert
        assertNull(result);
    }

    @Test
    void checkUser_shouldReturnNull_whenSQLExceptionOccurs() throws SQLException {
        // Arrange
        Connection connection = mock(Connection.class);

        when(databaseConnector.connect()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenThrow(new SQLException());

        // Act
        LoginTokenDTO result = loginDao.checkUser("user", "pass");

        // Assert
        assertNull(result);
    }
}