package nl.han.aim.oose.dea.domain;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.han.aim.oose.dea.service.dto.login.LoginTokenDTO;
import nl.han.aim.oose.dea.service.dto.UserDTO;
import nl.han.aim.oose.dea.service.exception.UserNotAuthorizedException;

@Path("/login")
public class LoginResource {

    private LoginService loginService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(UserDTO user) {
        LoginTokenDTO loginTokenDTO = loginService.checkUser(user);
        if(loginTokenDTO != null) {
            return Response.ok().entity(loginTokenDTO).build();
        }
        throw new UserNotAuthorizedException("UnAuthorized user");
    }

    @Inject
    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }


}
