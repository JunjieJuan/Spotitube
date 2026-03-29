package nl.han.aim.oose.dea.service;

import nl.han.aim.oose.dea.service.dto.PlaylistDTO;
import nl.han.aim.oose.dea.service.dto.PlaylistsResponseDTO;

public interface IPlaylistService {
    PlaylistsResponseDTO getAllPlaylists(String token);
    PlaylistsResponseDTO addPlaylist(PlaylistDTO playlist, String token);
    PlaylistsResponseDTO deletePlaylist(int id, String token);
    PlaylistsResponseDTO editPlaylist(int id, PlaylistDTO playlist, String token);
}