package se1app.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import se1app.datatypes.ImageType;
import se1app.datatypes.PlaylistType;

import javax.persistence.*;


@Entity
@Table(name = "room")
@JsonIgnoreProperties(value = {"space"})
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int _id;

    @ManyToOne
    @JoinColumn(name = "space_id", nullable = false)
    private Space _space;

    @Column(name = "wall_cover")
    private ImageType _wallCover;

    @Column(name = "playlist")
    private PlaylistType _playlist;

    public Room(ImageType _wallCover, Space _space) {
        this._wallCover = _wallCover;
        this._playlist = new PlaylistType();
        this._space = _space;
    }

    public int getRoomId() {
        return _id;
    }
    public void setRoomId(int id) {
        this._id=id;
    }

    public ImageType getWallCover() {
        return _wallCover;
    }
    public void setWallCover(ImageType _wallCover) {
        this._wallCover = _wallCover;
    }

    public PlaylistType getPlaylist() {
        return _playlist;
    }
    public void setPlaylist(PlaylistType _playlist) {
        this._playlist = _playlist;
    }

    public Space getSpace() {
        return _space;
    }
    public void setSpace(Space space) {
       this._space = space;
    }

    public Room() {
    }
}