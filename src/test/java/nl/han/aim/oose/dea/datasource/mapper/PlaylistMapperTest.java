package nl.han.aim.oose.dea.datasource.mapper;

import nl.han.aim.oose.dea.service.dto.PlaylistDTO;
import org.junit.jupiter.api.Test;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlaylistMapperTest {

    @Test
    void mapToDTO_shouldMapCorrectly() throws SQLException {
        // Arrange
        ResultSet rs = mock(ResultSet.class);
        when(rs.getInt("id")).thenReturn(1);
        when(rs.getString("name")).thenReturn("My Playlist");

        PlaylistMapper mapper = new PlaylistMapper();

        // Act
        PlaylistDTO result = mapper.mapToDTO(rs);

        // Assert
        assertEquals(1, result.getId());
        assertEquals("My Playlist", result.getName());
        assertFalse(result.isOwner());
    }
}