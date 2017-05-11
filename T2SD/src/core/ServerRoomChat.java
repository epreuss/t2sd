package core;

import frames.Server;
import interfaces.IRoomChat;
import interfaces.IServerRoomChat;
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
        try {
            Registry registry = LocateRegistry.createRegistry(2020);
            registry.bind("ServerRoomChat", this);
            criateRoom("Treta");        
        } catch (Exception ex) {
            Logger.getLogger(ServerRoomChat.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Server start!");
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
    
    public void removeRoom(IRoomChat room)
    {
        roomList.remove(room);
        room = null;
    }
}
