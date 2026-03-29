package nl.han.aim.oose.dea.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.han.aim.oose.dea.service.ITrackService;
import nl.han.aim.oose.dea.service.TrackService;
import nl.han.aim.oose.dea.service.dto.TrackDTO;

import java.util.ArrayList;

@Path("/tracks")
public class TrackResource {

    private ITrackService trackService;

    @Inject
    public void setTrackService(ITrackService trackService) {
        this.trackService = trackService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAvailableTracks(@QueryParam("forPlaylist") int playlistId,
                                       @QueryParam("token") String token) {
        ArrayList<TrackDTO> tracks = trackService.getAvailableTracks(playlistId);
        return Response.ok().entity(new TracksResponse(tracks)).build();
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
}