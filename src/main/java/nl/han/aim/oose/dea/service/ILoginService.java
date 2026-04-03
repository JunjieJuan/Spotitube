package nl.han.aim.oose.dea.service;

import nl.han.aim.oose.dea.service.dto.UserDTO;
import nl.han.aim.oose.dea.service.dto.LoginTokenDTO;

public interface ILoginService {
    LoginTokenDTO checkUser(UserDTO user);
}