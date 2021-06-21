package se1app.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "spaces")
@JsonIgnoreProperties(value = {"customer"})

public class Space {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int _id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer _customer;

    @Column(name = "name")
    private String _name;
    @Column(name = "opened")
    private boolean _opened;
    @Column(name = "face_control")
    private boolean _faceControl;

    @Column(name = "entry_fee")
    private int _entryFee;

    @OneToMany(mappedBy = "_space", fetch = FetchType.LAZY)
    private List<Room> _rooms;

    public Space(String _name, Customer customer) {
        this._name = _name;
        this._opened = false;
        this._faceControl = false;
        this._customer = customer;
        this._rooms = new ArrayList<Room>();
        this._entryFee = 0;
    }
    public Customer getCustomer() {
        return _customer;
    }
    public void setCustomer(Customer customer) {
        this._customer = customer;
    }

    public int getSpaceId() {
        return _id;
    }
    public void setSpaceId(int _spaceId) {
        this._id = _spaceId;
    }

    public String getName() {
        return _name;
    }
    public void setName(String _name) {
        this._name = _name;
    }

    public boolean getFaceControl() {
        return _faceControl;
    }
    public void setFaceControl(boolean _faceControl) {
        this._faceControl = _faceControl;
    }

    public boolean getOpened() {
        return _opened;
    }
    public void setOpened(boolean _opened) {
        this._opened = _opened;
    }

    public int getEntryFee() {
        return _entryFee;
    }
    public void setEntryFee(int _entryFee) {
        this._entryFee = _entryFee;
    }

    public List<Room> getRooms(){return _rooms;}

    public void addRoom(Room room){
        this._rooms.add(room);
        room.setSpace(this);
    }
    public void removeRoom(Room room){
        this._rooms.remove(room);
        room.setSpace(null);
    }



    public Space() {
    }
}
