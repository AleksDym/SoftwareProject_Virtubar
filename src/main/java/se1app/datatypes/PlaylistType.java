package se1app.datatypes;

import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;


import java.util.ArrayList;
import java.util.List;


@Embeddable
public class PlaylistType {

    @ElementCollection
    private List<String> playlist = new ArrayList<>();

    public PlaylistType(List<String> playlist) {
        this.playlist = playlist;
    }

    public List<String> getPlaylist() {
        return playlist;
    }

    public void setPlaylist(List<String> playlist) {
        this.playlist = playlist;
    }

    public PlaylistType() {
    }
}
