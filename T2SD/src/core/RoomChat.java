package core;

import interfaces.IRoomChat;
import interfaces.IUserChat;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Estevan
 */
public class RoomChat extends UnicastRemoteObject implements IRoomChat
{
    public String name;
    List<IUserChat> userList;

    public RoomChat(String roomName) throws RemoteException
    {
        super();
        this.name = roomName;
        userList = new ArrayList();
        try {
            Registry registry = LocateRegistry.getRegistry(2020);
            registry.bind("RoomChat#" + name, this);
        } catch (Exception ex) {
            Logger.getLogger(RoomChat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void joinRoom(String usrName) throws RemoteException
    {
        try {
            Registry registry = LocateRegistry.getRegistry(2020);
            IUserChat stub = (IUserChat) registry.lookup("UserChat#" + usrName);
            userList.add(stub);
            System.out.println("Room " + name + " joined " + stub.getName());
        } catch (Exception ex) {
            Logger.getLogger(RoomChat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void sendMsg(String usrName, String msg) throws RemoteException
    {
        for (IUserChat user : userList)
            user.deliverMsg(usrName, msg);
    }

    @Override
    public void leaveRoom(String usrName) throws RemoteException 
    {
        for (IUserChat user : userList)
            if (user.getName().equals(usrName))
            {
                userList.remove(user);
                break;
            }
    }

    @Override
    public void closeRoom() throws RemoteException
    {
        try {
            Registry registry = LocateRegistry.getRegistry(2020);
            registry.unbind("RoomChat#" + name);
        } catch (Exception ex) {
            Logger.getLogger(RoomChat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getName()
    {
        return name;
    }

}
