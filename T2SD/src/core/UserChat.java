package core;

import interfaces.IServerRoomChat;
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
    
    public UserChat(String usrName, String serverIp) throws RemoteException 
    {
        super();
        this.usrName = usrName;
        this.serverIp = serverIp;
        try {
            Registry registry;
            registry = LocateRegistry.getRegistry(serverIp, 2020);
            IServerRoomChat stub = (IServerRoomChat) registry.lookup("ServerRoomChat");
            stub.bindUser(this);
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
