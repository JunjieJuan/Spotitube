package nl.han.aim.oose.dea.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import nl.han.aim.oose.dea.datasource.dao.LoginDao;
import nl.han.aim.oose.dea.service.dto.login.LoginTokenDTO;
import nl.han.aim.oose.dea.service.dto.UserDTO;

@ApplicationScoped
public class LoginService {

    private LoginDao loginDao;

    @Inject
    public void setLoginDao(LoginDao loginDao) {
        this.loginDao = loginDao;
    }

    public LoginTokenDTO checkUser(UserDTO user) {
        return loginDao.checkUser(user.getUser(), user.getPassword());
    }
}
