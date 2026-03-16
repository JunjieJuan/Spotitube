package nl.han.aim.oose.dea.service.dto.login;

import java.util.UUID;

public class LoginTokenDTO {
    private String user;      // was: username
    private String token;

    public LoginTokenDTO(String user) {
        this.user = user;
        this.token = UUID.randomUUID().toString();
    }

    public String getUser() { return user; }
    public void setUser(String user) { this.user = user; }
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
}
