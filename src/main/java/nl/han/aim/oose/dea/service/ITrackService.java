package nl.han.aim.oose.dea.service;

import nl.han.aim.oose.dea.service.dto.TrackDTO;

import java.util.ArrayList;

public interface ITrackService {
    ArrayList<TrackDTO> getTracksForPlaylist(int playlistId);
    ArrayList<TrackDTO> getAvailableTracks(int playlistId);
    ArrayList<TrackDTO> addTrackToPlaylist(int playlistId, TrackDTO track);
    ArrayList<TrackDTO> removeTrackFromPlaylist(int playlistId, int trackId);
}