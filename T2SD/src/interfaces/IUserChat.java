package interfaces;

import java.rmi.RemoteException;

/**
 *
 * @author Estevan
 */
public interface IUserChat extends java.rmi.Remote 
{
    public void deliverMsg(String senderName, String msg) throws RemoteException;
    public String getName() throws RemoteException;
}
