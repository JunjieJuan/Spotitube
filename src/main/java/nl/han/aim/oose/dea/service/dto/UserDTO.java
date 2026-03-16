package nl.han.aim.oose.dea.service.dto;

public class UserDTO {
    private String user;
    private String password;

    public UserDTO() {

    }

    public void setUser(String user){
        this.user = user;
    }

    public String getUser() {
        return user;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
