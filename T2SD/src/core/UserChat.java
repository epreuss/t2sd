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
    public JTextArea areaChat;
    
    public UserChat(String usrName) throws RemoteException 
    {
        super();
        this.usrName = usrName;
        try {
            Registry registry = LocateRegistry.getRegistry(2020);
            registry.bind("UserChat#" + usrName, this);
        } catch (Exception ex) {
            Logger.getLogger(ServerRoomChat.class.getName()).log(Level.SEVERE, null, ex);
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
