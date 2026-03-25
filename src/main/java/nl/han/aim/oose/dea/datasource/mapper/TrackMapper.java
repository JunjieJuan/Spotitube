package nl.han.aim.oose.dea.datasource.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import nl.han.aim.oose.dea.service.dto.TrackDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

@ApplicationScoped
public class TrackMapper implements IMapper<TrackDTO> {
    @Override
    public TrackDTO mapToDTO(ResultSet rs) throws SQLException {
        boolean offlineAvailable = false;
        try {
            offlineAvailable = rs.getBoolean("offlineAvailable");
        } catch (SQLException e) {
            // standaard false
        }
        return new TrackDTO(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("performer"),
                rs.getInt("duration"),
                rs.getString("url"),
                rs.getString("album"),
                rs.getInt("playcount"),
                rs.getString("publicationDate"),
                rs.getString("description"),
                offlineAvailable
        );
    }
}