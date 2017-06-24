package core;

import frames.Server;
import interfaces.IRoomChat;
import interfaces.IServerRoomChat;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Estevan
 */
public class ServerRoomChat extends UnicastRemoteObject implements IServerRoomChat
{
    TreeMap<String, IRoomChat> rooms;
    private Server frame;

    public ServerRoomChat() throws RemoteException
    {
        super();
        rooms = new TreeMap<String, IRoomChat>();
        //System.setProperty("java.rmi.server.hostname","192.168.0.105");
        try {
            Registry registry = LocateRegistry.createRegistry(2020);
            registry.bind(Definitions.serverBindName, this);
            createRoom("RollSafe");
            System.out.println("Server start!");
        } catch (Exception ex) {
            Logger.getLogger(ServerRoomChat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void createRoom(String roomName) throws RemoteException
    {
        // Não deixa criar com nomes iguais.
        for (String name : rooms.keySet())
            if (name.equals(roomName))
                return;
        
        try {
            new RoomChat(roomName);
            Registry registry = LocateRegistry.getRegistry(2020);
            IRoomChat stub = (IRoomChat) registry.lookup(Definitions.roomBindPrefix + roomName);
            rooms.put(roomName, stub);
        } catch (Exception ex) {
            Logger.getLogger(ServerRoomChat.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (frame != null)
            frame.refreshRooms();
    }

    @Override
    public TreeMap<String, IRoomChat> getRooms() throws RemoteException
    {        
        return rooms;
    }

    public void removeRoom(String name)
    {
        rooms.remove(name);        
    }
    
    public IRoomChat getRoomRef(String name) throws RemoteException
    {
        return rooms.get(name);        
    }
    
    public void setFrame(Server frame)
    {
        this.frame = frame;
    }

    /*
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
    */
}
