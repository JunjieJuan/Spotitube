package nl.han.aim.oose.dea.service;

import nl.han.aim.oose.dea.datasource.dao.PlaylistDao;
import nl.han.aim.oose.dea.service.dto.PlaylistDTO;
import nl.han.aim.oose.dea.service.dto.PlaylistsResponseDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlaylistServiceTest {

    @Mock
    private PlaylistDao playlistDao;

    @InjectMocks
    private PlaylistService playlistService;

    @Test
    void testGetAllPlaylistsReturnsPlaylists() {
        // Arrange
        String token = "test-token";
        ArrayList<PlaylistDTO> playlists = new ArrayList<>();
        playlists.add(new PlaylistDTO(1, "Heavy Metal", true));
        PlaylistsResponseDTO response = new PlaylistsResponseDTO(playlists, 350);
        when(playlistDao.getAllPlaylists(token)).thenReturn(response);

        // Act
        PlaylistsResponseDTO result = playlistService.getAllPlaylists(token);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getPlaylists().size());
        assertEquals("Heavy Metal", result.getPlaylists().get(0).getName());
    }

    @Test
    void testAddPlaylistReturnsUpdatedPlaylists() {
        // Arrange
        String token = "test-token";
        PlaylistDTO playlist = new PlaylistDTO(1, "New Playlist", true);
        ArrayList<PlaylistDTO> playlists = new ArrayList<>();
        playlists.add(playlist);
        PlaylistsResponseDTO response = new PlaylistsResponseDTO(playlists, 0);
        when(playlistDao.addPlaylist(playlist, token)).thenReturn(response);

        // Act
        PlaylistsResponseDTO result = playlistService.addPlaylist(playlist, token);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getPlaylists().size());
    }

    @Test
    void testDeletePlaylistReturnsUpdatedPlaylists() {
        // Arrange
        String token = "test-token";
        PlaylistsResponseDTO response = new PlaylistsResponseDTO(new ArrayList<>(), 0);
        when(playlistDao.deletePlaylist(1, token)).thenReturn(response);

        // Act
        PlaylistsResponseDTO result = playlistService.deletePlaylist(1, token);

        // Assert
        assertNotNull(result);
        assertEquals(0, result.getPlaylists().size());
    }

    @Test
    void testEditPlaylistReturnsUpdatedPlaylists() {
        // Arrange
        String token = "test-token";
        PlaylistDTO playlist = new PlaylistDTO(1, "Updated Playlist", true);
        ArrayList<PlaylistDTO> playlists = new ArrayList<>();
        playlists.add(playlist);
        PlaylistsResponseDTO response = new PlaylistsResponseDTO(playlists, 0);
        when(playlistDao.editPlaylist(1, playlist, token)).thenReturn(response);

        // Act
        PlaylistsResponseDTO result = playlistService.editPlaylist(1, playlist, token);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Playlist", result.getPlaylists().get(0).getName());
    }
}