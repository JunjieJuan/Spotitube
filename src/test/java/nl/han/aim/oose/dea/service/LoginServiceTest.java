package nl.han.aim.oose.dea.service;

import nl.han.aim.oose.dea.datasource.dao.LoginDao;
import nl.han.aim.oose.dea.service.dto.UserDTO;
import nl.han.aim.oose.dea.service.dto.login.LoginTokenDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {

    @Mock
    private LoginDao loginDao;

    @InjectMocks
    private LoginService loginService;

    @Test
    void testGeldigeGebruikerGeeftTokenTerug() {
        // Arrange
        UserDTO user = new UserDTO();
        user.setUser("user");
        user.setPassword("wachtwoord");
        when(loginDao.checkUser("user", "wachtwoord"))
                .thenReturn(new LoginTokenDTO("user"));

        // Act
        LoginTokenDTO result = loginService.checkUser(user);

        // Assert
        assertNotNull(result);
    }

    @Test
    void testOngeldigeGebruikerGeeftNullTerug() {
        // Arrange
        UserDTO user = new UserDTO();
        user.setUser("fout");
        user.setPassword("fout");
        when(loginDao.checkUser("fout", "fout"))
                .thenReturn(null);

        // Act
        LoginTokenDTO result = loginService.checkUser(user);

        // Assert
        assertNull(result);
    }
}