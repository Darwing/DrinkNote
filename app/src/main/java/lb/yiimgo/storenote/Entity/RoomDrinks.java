package lb.yiimgo.storenote.Entity;

/**
 * Created by Darwing on 15-Dec-18.
 */

public class RoomDrinks {
    private String IdRoom;
    private String NameRoom;
    private String WaiterRoom;
    private String RoomUbication;
    private String Status;

    public String getWaiterRoom() {
        return WaiterRoom;
    }

    public void setWaiterRoom(String waiterRoom) {
        WaiterRoom = waiterRoom;
    }

    public String getIdRoom() {
        return IdRoom;
    }

    public void setIdRoom(String idRoom) {
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
