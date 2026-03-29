package nl.han.aim.oose.dea.service;

import nl.han.aim.oose.dea.datasource.dao.TrackDao;
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
class TrackServiceTest {

    @Mock
    private TrackDao trackDao;

    @InjectMocks
    private TrackService trackService;

    @Test
    void testGetTracksForPlaylistReturnsTracks() {
        // Arrange
        ArrayList<TrackDTO> tracks = new ArrayList<>();
        tracks.add(new TrackDTO(1, "Song for someone", "The Frames", 350, "https://example.com/1", "The cost", 0, null, null, false));
        when(trackDao.getTracksForPlaylist(1)).thenReturn(tracks);

        // Act
        ArrayList<TrackDTO> result = trackService.getTracksForPlaylist(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Song for someone", result.get(0).getTitle());
    }

    @Test
    void testGetAvailableTracksReturnsTracks() {
        // Arrange
        ArrayList<TrackDTO> tracks = new ArrayList<>();
        tracks.add(new TrackDTO(2, "The cost", "The Frames", 423, "https://example.com/2", null, 37, null, null, false));
        when(trackDao.getAvailableTracks(1)).thenReturn(tracks);

        // Act
        ArrayList<TrackDTO> result = trackService.getAvailableTracks(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("The cost", result.get(0).getTitle());
    }

    @Test
    void testAddTrackToPlaylistReturnsUpdatedTracks() {
        // Arrange
        TrackDTO track = new TrackDTO(1, "Song for someone", "The Frames", 350, "https://example.com/1", "The cost", 0, null, null, false);
        ArrayList<TrackDTO> tracks = new ArrayList<>();
        tracks.add(track);
        when(trackDao.addTrackToPlaylist(1, track)).thenReturn(tracks);

        // Act
        ArrayList<TrackDTO> result = trackService.addTrackToPlaylist(1, track);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testRemoveTrackFromPlaylistReturnsUpdatedTracks() {
        // Arrange
        ArrayList<TrackDTO> tracks = new ArrayList<>();
        when(trackDao.removeTrackFromPlaylist(1, 1)).thenReturn(tracks);

        // Act
        ArrayList<TrackDTO> result = trackService.removeTrackFromPlaylist(1, 1);

        // Assert
        assertNotNull(result);
        assertEquals(0, result.size());
    }
}