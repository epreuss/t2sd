package interfaces;

import java.rmi.RemoteException;
import javax.swing.JTextArea;

/**
 *
 * @author Estevan
 */
public interface IUserChat extends java.rmi.Remote 
{
    public void deliverMsg(String senderName, String msg) throws RemoteException;
    public String getName();
    public void setAreaChat(JTextArea areaChat);
}
