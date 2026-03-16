package nl.han.aim.oose.dea.service.dto;

public class UserDTO {
    private String user;
    private String password;

    public UserDTO() {

    }

    public void setUsername(String user){
        this.user = user;
    }

    public String getUsername() {
        return user;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
