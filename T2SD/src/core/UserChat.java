package core;

import interfaces.IUserChat;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    public UserChat(String usrName, String ip) throws RemoteException 
    {
        super();
        this.usrName = usrName;
        serverIp = ip;
        try {
            Registry registry = LocateRegistry.getRegistry(ip, 2020);
            registry.bind("UserChat#" + usrName, this);
        } catch (Exception ex) {
            Logger.getLogger(UserChat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void deliverMsg(String senderName, String msg) throws RemoteException
    {
        areaChat.append(senderName + ": " + msg + "\n");
    }

    @Override
    public String getName() throws RemoteException 
    {
        return usrName;
    }
}
