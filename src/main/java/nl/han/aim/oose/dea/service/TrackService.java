package nl.han.aim.oose.dea.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import nl.han.aim.oose.dea.datasource.dao.TrackDao;
import nl.han.aim.oose.dea.service.dto.TrackDTO;

import java.util.ArrayList;

@ApplicationScoped
public class TrackService {

    private TrackDao trackDao;

    public TrackService() {}

    @Inject
    public void setTrackDao(TrackDao trackDao) {
        this.trackDao = trackDao;
    }

    public ArrayList<TrackDTO> getTracksForPlaylist(int playlistId) {
        return trackDao.getTracksForPlaylist(playlistId);
    }

    public ArrayList<TrackDTO> getAvailableTracks(int playlistId) {
        return trackDao.getAvailableTracks(playlistId);
    }

    public ArrayList<TrackDTO> addTrackToPlaylist(int playlistId, TrackDTO track) {
        return trackDao.addTrackToPlaylist(playlistId, track);
    }

    public ArrayList<TrackDTO> removeTrackFromPlaylist(int playlistId, int trackId) {
        return trackDao.removeTrackFromPlaylist(playlistId, trackId);
    }
}