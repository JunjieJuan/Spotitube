package nl.han.aim.oose.dea.resource.exception;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import nl.han.aim.oose.dea.service.exception.UserNotAuthorizedException;

@Provider
@ApplicationScoped
public class UserNotAuthorizedExceptionMapper implements ExceptionMapper<UserNotAuthorizedException> {

    @Override
    public Response toResponse(UserNotAuthorizedException exception) {
        return Response
                .status(Response.Status.UNAUTHORIZED)
                .entity("{\"error\": \"Ongeldige gebruikersnaam of wachtwoord\"}")
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
