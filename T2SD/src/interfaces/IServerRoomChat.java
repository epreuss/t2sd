package interfaces;

import java.rmi.RemoteException;
import java.util.Map;

/**
 *
 * @author Estevan
 */
public interface IServerRoomChat extends java.rmi.Remote
{
    public Map<String, IRoomChat> getRooms() throws RemoteException;
    public void createRoom(String roomName) throws RemoteException;
}
