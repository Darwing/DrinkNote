package lb.yiimgo.drinknote.Entity;

/**
 * Created by Darwing on 15-Dec-18.
 */

public class RoomDrinks {
    private Integer IdRoom;
    private String NameRoom;
    private String RoomUbication;
    private int Status;

    public RoomDrinks(Integer IdRoom, String NameRoom, String RoomUbication,int Status)
    {
        this.IdRoom = IdRoom;
        this.NameRoom = NameRoom;
        this.RoomUbication = RoomUbication;
        this.Status = Status;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

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

    public void setRoomUbication(String RoomUbication) {
        RoomUbication = RoomUbication;
    }
}
