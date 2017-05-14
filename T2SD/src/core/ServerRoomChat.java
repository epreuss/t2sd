package core;

import frames.Server;
import interfaces.IRoomChat;
import interfaces.IServerRoomChat;
import interfaces.IUserChat;
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
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
public class ServerRoomChat extends UnicastRemoteObject implements IServerRoomChat
{
    List<IRoomChat> roomList;
    public Server frame;

    public ServerRoomChat() throws RemoteException
    {
        super();
        roomList = new ArrayList();
        //System.setProperty("java.rmi.server.hostname","192.168.0.105");
        try {
            Registry registry = LocateRegistry.createRegistry(2020);
            registry.bind("ServerRoomChat", this);
            criateRoom("Treta");
            System.out.println("Server start!");
        } catch (Exception ex) {
            Logger.getLogger(ServerRoomChat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void criateRoom(String roomName) throws RemoteException
    {
        // Não deixa criar com nomes iguais.
        for (IRoomChat room : roomList)
            if (room.getName().equals(roomName))
                return;
        
        try {
            new RoomChat(roomName);
            Registry registry = LocateRegistry.getRegistry(2020);
            IRoomChat stub = (IRoomChat) registry.lookup("RoomChat#" + roomName);
            roomList.add(stub);
        } catch (Exception ex) {
            Logger.getLogger(ServerRoomChat.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (frame != null)
            frame.refreshRooms();
    }

    @Override
    public List<IRoomChat> getRooms() throws RemoteException
    {
        return roomList;
    }
    
    /*
    @Override
    public void bindUser(String usrName) throws RemoteException 
    {
        try {
            UserChat user = new UserChat(usrName);
            Registry registry = LocateRegistry.getRegistry(2020);
            registry.bind("UserChat#" + usrName, user);
            System.out.println("Server binded " + usrName);            
        } catch (Exception ex) {
            Logger.getLogger(ServerRoomChat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    */
    
    public void removeRoom(IRoomChat room)
    {
        roomList.remove(room);
        room = null;
    }

    @Override
    public void bindUser(IUserChat stub) throws RemoteException
    {
        try {
            Registry registry = LocateRegistry.getRegistry(2020);
            registry.bind("UserChat#" + stub.getName(), stub);
            System.out.println("Server binded " + stub.getName());            
        } catch (Exception ex) {
            Logger.getLogger(ServerRoomChat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
