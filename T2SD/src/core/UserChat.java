package core;

import interfaces.IUserChat;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
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
    public final int size = 20;
    
    public UserChat(String usrName) throws RemoteException 
    {
        super();
        clockMatrix = new Integer[size][size];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                clockMatrix[i][j] = 0;
        this.usrName = usrName;
    }

    @Override
    public void deliverMsg(String senderName, String msg, Integer[][] clockMatrix) throws RemoteException
    {
        System.out.println(senderName + ": " + msg);
        for (int i = 0; i < size; i++)
            clockMatrix[id][i] = clockMatrix[i][i];
        deliverToGUI(senderName, msg);
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
}
