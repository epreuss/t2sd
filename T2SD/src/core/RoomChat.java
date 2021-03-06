package core;

import chatPackage.IRoomChat;
import chatPackage.IUserChat;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Estevan
 */
public class RoomChat extends UnicastRemoteObject implements IRoomChat
{
    public String name;
    TreeMap<String, IUserChat> users;
    int idCounter;

    public RoomChat(String roomName) throws RemoteException
    {
        super();
        idCounter = -1;
        this.name = roomName;
        users = new TreeMap<String, IUserChat>();
        try {
            Registry registry = LocateRegistry.getRegistry(2020);
            registry.bind(Definitions.roomBindPrefix + name, this);
        } catch (Exception ex) {
            Logger.getLogger(RoomChat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public int joinRoom(String usrName, IUserChat localObjRef) throws RemoteException
    {
        users.put(usrName, localObjRef);
        // Atualiza a lista de users para todos.
        for (IUserChat user : users.values())
            user.updateUserList(users);
        System.out.println((Definitions.userBindPrefix + usrName) + " joined " + "Room " + (Definitions.roomBindPrefix + name));
        idCounter++;
        return idCounter;
    }
    
    public void sendMsgToAll(String usrName, String msg) throws RemoteException
    {
        for (IUserChat user : users.values())
            user.deliverMsg(usrName, msg, null);
    }

    @Override
    public void leaveRoom(String usrName) throws RemoteException 
    {
        if (users == null)
            return;
        
        for (String name : users.keySet())
            if (name.equals(usrName))
            {
                users.remove(name);
                break;
            }
        // Atualiza a lista de users para todos.
        for (IUserChat user : users.values())
            user.updateUserList(users);
    }

    @Override
    public void closeRoom() throws RemoteException
    {
        try {
            Registry registry = LocateRegistry.getRegistry(2020);
            sendMsgToAll("Server", "Sala fechada pelo servidor!");
            registry.unbind(Definitions.roomBindPrefix + name);
            users = null;
        } catch (Exception ex) {
            Logger.getLogger(RoomChat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

