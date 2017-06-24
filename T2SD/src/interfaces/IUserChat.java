package interfaces;

import java.rmi.RemoteException;
import java.util.TreeMap;

/**
 *
 * @author Estevan
 */
public interface IUserChat extends java.rmi.Remote 
{
    public void updateUserList(TreeMap<String, IUserChat> userList) throws RemoteException;
    public void deliverMsg(String senderName, String msg, Integer[][] clockMatrix) throws RemoteException;
}
