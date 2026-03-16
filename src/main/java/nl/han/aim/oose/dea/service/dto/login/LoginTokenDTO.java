package nl.han.aim.oose.dea.service.dto.login;

import java.util.UUID;

public class LoginTokenDTO {

    private String  username;
    private String token;

    public LoginTokenDTO(String username) {
        this.username = username;
        this.token = UUID.randomUUID().toString();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
