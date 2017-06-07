package interfaces;

import java.rmi.RemoteException;
import java.util.Map;

/**
 *
 * @author Estevan
 */
public interface IServerRoomChat extends java.rmi.Remote
{
    public void createRoom(String roomName) throws RemoteException;
    public Map<String, IRoomChat> getRooms() throws RemoteException;
    public void bindUser(IUserChat stub) throws RemoteException;
}
