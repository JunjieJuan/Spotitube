package nl.han.aim.oose.dea.domain;

import nl.han.aim.oose.dea.datasource.dao.LoginDao;
import nl.han.aim.oose.dea.service.dto.login.LoginTokenDTO;
import nl.han.aim.oose.dea.service.dto.UserDTO;

public class LoginService {

    private LoginDao loginDao;

    public LoginService(LoginDao loginDao) {
        this.loginDao = loginDao;
    }

    public LoginTokenDTO checkUser(UserDTO user) {

        return loginDao.checkUser(user.getUsername(), user.getPassword());
    }
}
