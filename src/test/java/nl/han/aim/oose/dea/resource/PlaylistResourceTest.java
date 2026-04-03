package nl.han.aim.oose.dea.resource;

import jakarta.ws.rs.core.Response;
import nl.han.aim.oose.dea.service.IPlaylistService;
import nl.han.aim.oose.dea.service.ITrackService;
import nl.han.aim.oose.dea.service.dto.PlaylistDTO;
import nl.han.aim.oose.dea.service.dto.PlaylistsResponseDTO;
import nl.han.aim.oose.dea.service.dto.TrackDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlaylistResourceTest {

    @Mock
    private IPlaylistService playlistService;
    @Mock
    private ITrackService trackService;

    @InjectMocks
    private PlaylistResource playlistResource;

    @Test
    void testGetAllPlaylistsGeeft200Terug() {
        // Arrange
        String token = "test-token";
        ArrayList<PlaylistDTO> playlists = new ArrayList<>();
        playlists.add(new PlaylistDTO(1, "Heavy Metal", true));
        PlaylistsResponseDTO response = new PlaylistsResponseDTO(playlists, 350);
        when(playlistService.getAllPlaylists(token)).thenReturn(response);

        // Act
        Response result = playlistResource.getAllPlaylists(token);

        // Assert
        assertEquals(200, result.getStatus());
    }

    @Test
    void testAddPlaylistGeeft201Terug() {
        // Arrange
        String token = "test-token";
        PlaylistDTO playlist = new PlaylistDTO(1, "New Playlist", true);
        PlaylistsResponseDTO response = new PlaylistsResponseDTO(new ArrayList<>(), 0);
        when(playlistService.addPlaylist(playlist, token)).thenReturn(response);

        // Act
        Response result = playlistResource.addPlaylist(token, playlist);

        // Assert
        assertEquals(201, result.getStatus());
    }

    @Test
    void testDeletePlaylistGeeft200Terug() {
        // Arrange
        String token = "test-token";
        PlaylistsResponseDTO response = new PlaylistsResponseDTO(new ArrayList<>(), 0);
        when(playlistService.deletePlaylist(1, token)).thenReturn(response);

        // Act
        Response result = playlistResource.deletePlaylist(1, token);

        // Assert
        assertEquals(200, result.getStatus());
    }

    @Test
    void testEditPlaylistGeeft200Terug() {
        // Arrange
        String token = "test-token";
        PlaylistDTO playlist = new PlaylistDTO(1, "Updated Playlist", true);
        PlaylistsResponseDTO response = new PlaylistsResponseDTO(new ArrayList<>(), 0);
        when(playlistService.editPlaylist(1, playlist, token)).thenReturn(response);

        // Act
        Response result = playlistResource.editPlaylist(1, token, playlist);

        // Assert
        assertEquals(200, result.getStatus());
    }

    @Test
    void testAddTrackToPlaylistGeeft201Terug() {
        // Arrange
        int playlistId = 1;
        String token = "test-token";
        TrackDTO track = new TrackDTO();
        ArrayList<TrackDTO> tracks = new ArrayList<>();
        tracks.add(track);
        when(trackService.addTrackToPlaylist(playlistId, track)).thenReturn(tracks);

        // Act
        Response result = playlistResource.addTrackToPlaylist(playlistId, token, track);

        // Assert
        assertEquals(201, result.getStatus());
        PlaylistResource.TracksResponse body = (PlaylistResource.TracksResponse) result.getEntity();
        assertNotNull(body);
        assertEquals(1, body.getTracks().size());
    }

    @Test
    void testRemoveTrackFromPlaylistGeeft200Terug() {
        // Arrange
        int playlistId = 1;
        int trackId = 1;
        String token = "test-token";
        ArrayList<TrackDTO> tracks = new ArrayList<>();
        tracks.add(new TrackDTO());
        when(trackService.removeTrackFromPlaylist(playlistId, trackId)).thenReturn(tracks);

        // Act
        Response result = playlistResource.removeTrackFromPlaylist(playlistId, trackId, token);

        // Assert
        assertEquals(200, result.getStatus());
        PlaylistResource.TracksResponse body = (PlaylistResource.TracksResponse) result.getEntity();
        assertNotNull(body);
        assertEquals(1, body.getTracks().size());
    }

    @Test
    void testGetTracksForPlaylistGeeft200Terug() {
        // Arrange
        int playlistId = 1;
        String token = "test-token";
        ArrayList<TrackDTO> tracks = new ArrayList<>();
        tracks.add(new TrackDTO());
        when(trackService.getTracksForPlaylist(playlistId)).thenReturn(tracks);

        // Act
        Response result = playlistResource.getTracksForPlaylist(playlistId, token);

        // Assert
        assertEquals(200, result.getStatus());
        PlaylistResource.TracksResponse body = (PlaylistResource.TracksResponse) result.getEntity();
        assertNotNull(body);
        assertEquals(1, body.getTracks().size());
    }
}