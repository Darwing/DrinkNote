package lb.yiimgo.drinknote.Entity;

/**
 * Created by Darwing on 15-Dec-18.
 */

public class RoomDrinks {
    private Integer IdRoom;
    private String NameRoom;
    private String RoomUbication;
    private String Status;

    public Integer getIdRoom() {
        return IdRoom;
    }

    public void setIdRoom(Integer idRoom) {
        IdRoom = idRoom;
    }

    public String getNameRoom() {
        return NameRoom;
    }

    public void setNameRoom(String nameRoom) {
        NameRoom = nameRoom;
    }

    public String getRoomUbication() {
        return RoomUbication;
    }

    public void setRoomUbication(String roomUbication) {
        RoomUbication = roomUbication;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
