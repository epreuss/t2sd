package interfaces;

import java.rmi.RemoteException;

/**
 *
 * @author Estevan
 */
public interface IRoomChat extends java.rmi.Remote
{
    public void sendMsg(String usrName, String msg) throws RemoteException;
    public void joinRoom(String usrName, IUserChat localObjRef) throws RemoteException;
    public void leaveRoom(String usrName) throws RemoteException;
    public void closeRoom() throws RemoteException;
}