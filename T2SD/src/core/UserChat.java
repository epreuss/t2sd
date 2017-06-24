package core;

import chatPackage.IUserChat;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.TreeMap;
import javax.swing.JTextArea;

/**
 *
 * @author Estevan
 */
public class UserChat extends UnicastRemoteObject implements IUserChat
{
    public Integer[][] clockMatrix;
    public TreeMap<String, IUserChat> users;
    
    public int id;
    public String usrName;
    public JTextArea areaChat;
    public ArrayList<Buffer> buffers;
    public final int size = 20;    
    
    public UserChat(String usrName) throws RemoteException 
    {
        super();
        clockMatrix = new Integer[size][size];
        buffers = new ArrayList();
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                clockMatrix[i][j] = 0;
        this.usrName = usrName;
    }

    @Override
    public void deliverMsg(String senderName, String msg, Integer[][] receivedClock) throws RemoteException
    {
        /*
        // Ao receber de si mesmo: Aumenta clock.
        if (senderName.equals(usrName))
            clockMatrix[id][id] += 1;
        */
        /*
        Ao receber de outro:
        1 - Verifica se é o clock esperado.
        2 - Copia vetor alheio.
        */        
        
        /*
        System.out.println(senderName + ": " + msg);
        deliverToGUI(senderName, msg);
        if (true) return;
        */                
        
        int senderId = getIdFromName(senderName);
        if (clockMatrix[senderId][senderId].equals(receivedClock[senderId][senderId]))
        {
            // É o esperado!
            System.out.println(senderName + ": " + msg);
            deliverToGUI(senderName, msg);
            clockMatrix[senderId][senderId] += 1;
        }
        else
        {
            // Não é o esperado! Bufferiza.
            buffers.add(new Buffer(senderName, msg, senderId, receivedClock[senderId][senderId]));
        }
        for (int i = 0; i < size; i++)
            clockMatrix[id][i] = clockMatrix[i][i];
    }
    
    private int getIdFromName(String senderName) throws RemoteException
    {
        if (users.containsValue(senderName))
            return 1;//users.get(senderName).getId();
        else
            return -1;           
    }
    
    public void deliverToGUI(String senderName, String msg) // GUI do cliente
    {
        areaChat.append(senderName + ": " + msg + "\n");
    }
    
    public void setAreaChat(JTextArea areaChat) 
    {
        this.areaChat = areaChat;
    }

    @Override
    public void updateUserList(TreeMap<String, IUserChat> userList) throws RemoteException 
    {
        users = userList;
    }

    //@Override
    public int getId() throws RemoteException 
    {
        return id;
    }
}
