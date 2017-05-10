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
            Logger.getLogger(ServerRoomChat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void joinRoom(String usrName)
    {
        try {
            Registry registry = LocateRegistry.getRegistry(2020);
            IUserChat stub = (IUserChat) registry.lookup("UserChat#" + usrName);
            userList.add(stub);
        } catch (Exception ex) {
            Logger.getLogger(UserChat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void sendMsg(String usrName, String msg)
    {
        try {
            Registry registry = LocateRegistry.getRegistry(2020);
            for (IUserChat user : userList)
            {
                IUserChat stub = (IUserChat) registry.lookup("UserChat#" + user.getName());
                stub.deliverMsg(usrName, msg);
            }
        } catch (Exception ex) {
            Logger.getLogger(UserChat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void leaveRoom(String usrName) 
    {
    }

    @Override
    public void closeRoom() 
    {
    }

    @Override
    public String getName()
    {
        return name;
    }

}
