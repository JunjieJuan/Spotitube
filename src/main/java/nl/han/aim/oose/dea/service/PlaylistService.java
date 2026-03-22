package nl.han.aim.oose.dea.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import nl.han.aim.oose.dea.datasource.dao.PlaylistDao;
import nl.han.aim.oose.dea.service.dto.PlaylistDTO;
import nl.han.aim.oose.dea.service.dto.PlaylistsResponseDTO;

@ApplicationScoped
public class PlaylistService {

    private PlaylistDao playlistDao;

    public PlaylistService() {}

    @Inject
    public void setPlaylistDao(PlaylistDao playlistDao) {
        this.playlistDao = playlistDao;
    }

    public PlaylistsResponseDTO getAllPlaylists(String token) {
        return playlistDao.getAllPlaylists(token);
    }

    public PlaylistsResponseDTO addPlaylist(PlaylistDTO playlist, String token) {
        return playlistDao.addPlaylist(playlist, token);
    }

    public PlaylistsResponseDTO deletePlaylist(int id, String token) {
        return playlistDao.deletePlaylist(id, token);
    }

    public PlaylistsResponseDTO editPlaylist(int id, PlaylistDTO playlist, String token) {
        return playlistDao.editPlaylist(id, playlist, token);
    }
}