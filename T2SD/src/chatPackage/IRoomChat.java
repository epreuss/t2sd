package chatPackage;

import java.rmi.RemoteException;

/**
 *
 * @author Estevan
 */
public interface IRoomChat extends java.rmi.Remote
{
    public int joinRoom(String usrName, IUserChat localObjRef) throws RemoteException;
    public void leaveRoom(String usrName) throws RemoteException;
    public void closeRoom() throws RemoteException;
}