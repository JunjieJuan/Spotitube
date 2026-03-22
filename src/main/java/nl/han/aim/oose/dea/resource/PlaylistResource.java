package nl.han.aim.oose.dea.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.han.aim.oose.dea.service.PlaylistService;
import nl.han.aim.oose.dea.service.dto.PlaylistDTO;
import nl.han.aim.oose.dea.service.dto.PlaylistsResponseDTO;

@Path("/playlists")
public class PlaylistResource {

    private PlaylistService playlistService;

    @Inject
    public void setPlaylistService(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPlaylists(@QueryParam("token") String token) {
        PlaylistsResponseDTO playlists = playlistService.getAllPlaylists(token);
        return Response.ok(playlists).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPlaylist(@QueryParam("token") String token, PlaylistDTO playlist) {
        PlaylistsResponseDTO playlists = playlistService.addPlaylist(playlist, token);
        return Response.status(201).entity(playlists).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePlaylist(@PathParam("id") int id, @QueryParam("token") String token) {
        PlaylistsResponseDTO playlists = playlistService.deletePlaylist(id, token);
        return Response.ok(playlists).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editPlaylist(@PathParam("id") int id, @QueryParam("token") String token, PlaylistDTO playlist) {
        PlaylistsResponseDTO playlists = playlistService.editPlaylist(id, playlist, token);
        return Response.ok(playlists).build();
    }
}