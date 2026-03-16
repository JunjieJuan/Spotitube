package nl.han.aim.oose.dea.resource;

import jakarta.ws.rs.core.Response;
import nl.han.aim.oose.dea.service.LoginService;
import nl.han.aim.oose.dea.service.dto.UserDTO;
import nl.han.aim.oose.dea.service.dto.login.LoginTokenDTO;
import nl.han.aim.oose.dea.service.exception.UserNotAuthorizedException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginResourceTest {

    @Mock
    private LoginService loginService;

    @InjectMocks
    private LoginResource loginResource;

    @Test
    void testSuccesvolleLoginGeeft200Terug() {
        // Arrange
        UserDTO user = new UserDTO();
        user.setUser("user");
        user.setPassword("wachtwoord");
        when(loginService.checkUser(user))
                .thenReturn(new LoginTokenDTO("user"));

        // Act
        Response response = loginResource.login(user);

        // Assert
        assertEquals(200, response.getStatus());
    }

    @Test
    void testMislukteLoginGooidException() {
        // Arrange
        UserDTO user = new UserDTO();
        user.setUser("fout");
        user.setPassword("fout");
        when(loginService.checkUser(user))
                .thenReturn(null);

        // Assert
        assertThrows(UserNotAuthorizedException.class, () -> {
            loginResource.login(user);
        });
    }
}