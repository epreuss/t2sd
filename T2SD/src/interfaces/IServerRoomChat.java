package interfaces;

import core.UserChat;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author Estevan
 */
public interface IServerRoomChat extends java.rmi.Remote
{
    public void criateRoom(String roomName) throws RemoteException;
    public List<String> getRooms() throws RemoteException;
    public void bindUser(IUserChat stub) throws RemoteException;
}
