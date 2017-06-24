package chatPackage;

import java.rmi.RemoteException;
import java.util.TreeMap;

/**
 *
 * @author Estevan
 */
public interface IServerRoomChat extends java.rmi.Remote
{
    public TreeMap<String, IRoomChat> getRooms() throws RemoteException;
    public void createRoom(String roomName) throws RemoteException;
}
