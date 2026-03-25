package nl.han.aim.oose.dea.datasource.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import nl.han.aim.oose.dea.service.dto.PlaylistDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

@ApplicationScoped
public class PlaylistMapper implements IMapper<PlaylistDTO> {
    @Override
    public PlaylistDTO mapToDTO(ResultSet rs) throws SQLException {
        return new PlaylistDTO(
                rs.getInt("id"),
                rs.getString("name"),
                false // owner wordt bepaald in de DAO
        );
    }
}