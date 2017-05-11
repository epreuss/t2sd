package core;

import interfaces.IUserChat;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import javax.swing.JTextArea;

/**
 *
 * @author Estevan
 */
public class UserChat extends UnicastRemoteObject implements IUserChat 
{
    public String usrName;
    public String serverIp;
    public JTextArea areaChat;
    
    public UserChat(String usrName) throws RemoteException 
    {
        super();
        this.usrName = usrName;
    }

    @Override
    public void deliverMsg(String senderName, String msg) throws RemoteException
    {
        System.out.println(senderName + ": " + msg);
        //areaChat.append(senderName + ": " + msg + "\n");
    }

    @Override
    public String getName() throws RemoteException 
    {
        return usrName;
    }
    
    @Override
    public void setAreaChat(JTextArea areaChat) throws RemoteException
    {
        this.areaChat = areaChat;
    }
}
