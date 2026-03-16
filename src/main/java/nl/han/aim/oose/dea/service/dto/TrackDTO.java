package nl.han.aim.oose.dea.service.dto;

import javax.xml.crypto.Data;

public class TrackDTO {
    private int id;
    private String title;
    private String performance;
    private int duration;
    private String album;
    private int playcount;
    private Data publicaionDate;
    private String description;
    private Boolean offlineAvailable;

    public TrackDTO(int id, String title, String performance, int duration, String album, int playcount, Data publicaionDate, String description, Boolean offlineAvailable) {
        this.id = id;
        this.title = title;
        this.performance = performance;
        this.duration = duration;
        this.album = album;
        this.playcount = playcount;
        this.publicaionDate = publicaionDate;
        this.description = description;
        this.offlineAvailable = offlineAvailable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPerformance() {
        return performance;
    }

    public void setPerformance(String performance) {
        this.performance = performance;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public int getPlaycount() {
        return playcount;
    }

    public void setPlaycount(int playcount) {
        this.playcount = playcount;
    }

    public Data getPublicaionDate() {
        return publicaionDate;
    }

    public void setPublicaionDate(Data publicaionDate) {
        this.publicaionDate = publicaionDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getOfflineAvailable() {
        return offlineAvailable;
    }

    public void setOfflineAvailable(Boolean offlineAvailable) {
        this.offlineAvailable = offlineAvailable;
    }






}
