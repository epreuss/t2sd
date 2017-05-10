package interfaces;

import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author Estevan
 */
public interface IServerRoomChat extends java.rmi.Remote
{
    public void criateRoom(String roomName) throws RemoteException;
    public List<IRoomChat> getRooms() throws RemoteException;
}
