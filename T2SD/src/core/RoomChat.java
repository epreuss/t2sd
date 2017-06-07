package core;

import interfaces.IRoomChat;
import interfaces.IUserChat;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Estevan
 */
public class RoomChat extends UnicastRemoteObject implements IRoomChat
{
    public String name;
    Map<String, IUserChat> users;

    public RoomChat(String roomName) throws RemoteException
    {
        super();
        this.name = roomName;
        users = new HashMap<String, IUserChat>();
        try {
            Registry registry = LocateRegistry.getRegistry(2020);
            registry.bind(Definitions.roomBindPrefix + name, this);
        } catch (Exception ex) {
            Logger.getLogger(RoomChat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void joinRoom(String usrName, IUserChat localObjRef) throws RemoteException
    {
        /*
        try {
            Registry registry = LocateRegistry.getRegistry(2020);
            IUserChat stub = (IUserChat) registry.lookup(Definitions.userBindPrefix + usrName);
            userRemotes.add(stub);
            System.out.println((Definitions.userBindPrefix + stub.getName()) + " joined " + "Room " + (Definitions.roomBindPrefix + name));
        } catch (Exception ex) {
            Logger.getLogger(RoomChat.class.getName()).log(Level.SEVERE, null, ex);
        }
        */
        users.put(usrName, localObjRef);
        System.out.println((Definitions.userBindPrefix + usrName) + " joined " + "Room " + (Definitions.roomBindPrefix + name));
    }
    
    @Override
    public void sendMsg(String usrName, String msg) throws RemoteException
    {
        for (IUserChat user : users.values())
            user.deliverMsg(usrName, msg);
    }

    @Override
    public void leaveRoom(String usrName) throws RemoteException 
    {
        for (String name : users.keySet())
            if (name.equals(usrName))
            {
                users.remove(name);
                break;
            }
    }

    @Override
    public void closeRoom() throws RemoteException
    {
        try {
            Registry registry = LocateRegistry.getRegistry(2020);
            sendMsg("Server", "Sala fechada pelo servidor!");
            registry.unbind(Definitions.roomBindPrefix + name);
            users = null;
        } catch (Exception ex) {
            Logger.getLogger(RoomChat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
