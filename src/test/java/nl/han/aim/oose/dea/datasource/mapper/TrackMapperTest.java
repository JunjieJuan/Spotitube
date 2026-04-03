package nl.han.aim.oose.dea.datasource.mapper;

import nl.han.aim.oose.dea.service.dto.TrackDTO;
import org.junit.jupiter.api.Test;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TrackMapperTest {

    @Test
    void mapToDTO_shouldMapAllCorrectly() throws SQLException {
        // Arrange
        ResultSet rs = mock(ResultSet.class);
        when(rs.getInt("id")).thenReturn(1);
        when(rs.getString("title")).thenReturn("Animals");
        when(rs.getString("performer")).thenReturn("Martin");
        when(rs.getInt("duration")).thenReturn(180);
        when(rs.getString("url")).thenReturn("http://test.nl");
        when(rs.getString("album")).thenReturn("American Idiot");
        when(rs.getInt("playcount")).thenReturn(8);
        when(rs.getString("publicationDate")).thenReturn("02-04-206");
        when(rs.getString("description")).thenReturn("Nice EDM");
        when(rs.getBoolean("offlineAvailable")).thenReturn(true);

        TrackMapper mapper = new TrackMapper();

        // Act
        TrackDTO result = mapper.mapToDTO(rs);

        // Assert
        assertEquals(1, result.getId());
        assertEquals("Animals", result.getTitle());
        assertEquals("Martin", result.getPerformer());
        assertEquals(180, result.getDuration());
        assertEquals("http://test.nl", result.getUrl());
        assertEquals("American Idiot", result.getAlbum());
        assertEquals(8, result.getPlaycount());
        assertEquals("02-04-206", result.getPublicationDate());
        assertEquals("Nice EDM", result.getDescription());
        assertTrue(result.isOfflineAvailable());
    }

    @Test
    void mapToDTO_shouldSetOfflineAvailableFalse() throws SQLException {
        // Arrange
        ResultSet rs = mock(ResultSet.class);

        when(rs.getInt("id")).thenReturn(1);
        when(rs.getString("title")).thenReturn("Animals");
        when(rs.getString("performer")).thenReturn("Martin");
        when(rs.getInt("duration")).thenReturn(180);
        when(rs.getString("url")).thenReturn("http://test.nl");
        when(rs.getString("album")).thenReturn("American Idiot");
        when(rs.getInt("playcount")).thenReturn(8);
        when(rs.getString("publicationDate")).thenReturn("02-04-206");
        when(rs.getString("description")).thenReturn("Nice EDM");

        // Forceer exception
        when(rs.getBoolean("offlineAvailable")).thenThrow(new SQLException());

        TrackMapper mapper = new TrackMapper();

        // Act
        TrackDTO result = mapper.mapToDTO(rs);

        // Assert
        assertFalse(result.isOfflineAvailable()); // fallback = false
    }
}
