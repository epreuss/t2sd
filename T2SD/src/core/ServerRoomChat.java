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
    private Server frame;

    public ServerRoomChat() throws RemoteException
    {
        super();
        roomList = new ArrayList();
        //System.setProperty("java.rmi.server.hostname","192.168.0.105");
        try {
            Registry registry = LocateRegistry.createRegistry(2020);
            registry.bind(Definitions.serverBindName, this);
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
            IRoomChat stub = (IRoomChat) registry.lookup(Definitions.roomBindPrefix + roomName);
            roomList.add(stub);
        } catch (Exception ex) {
            Logger.getLogger(ServerRoomChat.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (frame != null)
            frame.refreshRooms();
    }

    @Override
    public List<String> getRooms() throws RemoteException
    {
        List<String> roomNames = new ArrayList();
        for (IRoomChat room : roomList)
            roomNames.add(room.getName());
        return roomNames;
    }

    public void removeRoom(IRoomChat room)
    {
        roomList.remove(room);
        room = null;
    }
    
    public IRoomChat getRoomRef(String name) throws RemoteException
    {
        for (IRoomChat room : roomList)
            if (room.getName().equals(name))
                return room;
        return null;
    }
    
    public void setFrame(Server frame)
    {
        this.frame = frame;
    }

    @Override
    public void bindUser(IUserChat stub) throws RemoteException
    {
        try {
            Registry registry = LocateRegistry.getRegistry(2020);
            registry.bind(Definitions.userBindPrefix + stub.getName(), stub);
            System.out.println("Server binded " + (Definitions.userBindPrefix + stub.getName()));            
        } catch (Exception ex) {
            Logger.getLogger(ServerRoomChat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
