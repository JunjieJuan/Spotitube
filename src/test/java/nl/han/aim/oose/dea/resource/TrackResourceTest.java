package nl.han.aim.oose.dea.resource;

import jakarta.ws.rs.core.Response;
import nl.han.aim.oose.dea.service.ITrackService;
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
class TrackResourceTest {

    @Mock
    private ITrackService trackService;

    @InjectMocks
    private TrackResource trackResource;

    @Test
    void testGetAvailableTracksGeeft200Terug() {
        // Arrange
        ArrayList<TrackDTO> tracks = new ArrayList<>();
        tracks.add(new TrackDTO(1, "Song for someone", "The Frames", 350, "https://example.com/1", "The cost", 0, null, null, false));
        when(trackService.getAvailableTracks(1)).thenReturn(tracks);

        // Act
        Response result = trackResource.getAvailableTracks(1, "test-token");

        // Assert
        assertEquals(200, result.getStatus());
    }
}