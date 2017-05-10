package interfaces;

import java.util.List;

/**
 *
 * @author Estevan
 */
public interface IServerRoomChat extends java.rmi.Remote
{
    public List<IRoomChat> getRooms();
    public void criateRoom(String roomName);
}
