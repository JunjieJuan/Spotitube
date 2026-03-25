package nl.han.aim.oose.dea.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.han.aim.oose.dea.service.PlaylistService;
import nl.han.aim.oose.dea.service.TrackService;
import nl.han.aim.oose.dea.service.dto.PlaylistDTO;
import nl.han.aim.oose.dea.service.dto.PlaylistsResponseDTO;
import nl.han.aim.oose.dea.service.dto.TrackDTO;

import java.util.ArrayList;

@Path("/playlists")
public class PlaylistResource {

    private PlaylistService playlistService;
    private TrackService trackService;

    @Inject
    public void setPlaylistService(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @Inject
    public void setTrackService(TrackService trackService) {
        this.trackService = trackService;
    }

    public static class TracksResponse {
        private ArrayList<TrackDTO> tracks;

        public TracksResponse(ArrayList<TrackDTO> tracks) {
            this.tracks = tracks;
        }

        public ArrayList<TrackDTO> getTracks() {
            return tracks;
        }
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

    @GET
    @Path("/{id}/tracks")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTracksForPlaylist(@PathParam("id") int playlistId,
                                         @QueryParam("token") String token) {
        ArrayList<TrackDTO> tracks = trackService.getTracksForPlaylist(playlistId);
        return Response.ok().entity(new TracksResponse(tracks)).build();
    }

    @POST
    @Path("/{id}/tracks")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addTrackToPlaylist(@PathParam("id") int playlistId,
                                       @QueryParam("token") String token,
                                       TrackDTO track) {
        ArrayList<TrackDTO> tracks = trackService.addTrackToPlaylist(playlistId, track);
        return Response.status(201).entity(new TracksResponse(tracks)).build();
    }

    @DELETE
    @Path("/{id}/tracks/{trackId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeTrackFromPlaylist(@PathParam("id") int playlistId,
                                            @PathParam("trackId") int trackId,
                                            @QueryParam("token") String token) {
        ArrayList<TrackDTO> tracks = trackService.removeTrackFromPlaylist(playlistId, trackId);
        return Response.ok().entity(new TracksResponse(tracks)).build();
    }
}