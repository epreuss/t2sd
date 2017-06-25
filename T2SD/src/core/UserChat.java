package core;

import chatPackage.IUserChat;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import javax.swing.JTextArea;

/**
 *
 * @author Estevan
 */
public class UserChat extends UnicastRemoteObject implements IUserChat
{
    public Integer[][] clock;
    public TreeMap<String, IUserChat> users;
    
    public int id;
    public String usrName;
    public JTextArea areaChat;
    public JTextArea areaBuffer;
    public ArrayList<Buffer> buffers;
    public final int size = 20;    
    public final boolean printer = false;
    
    public UserChat(String usrName) throws RemoteException 
    {
        super();
        clock = new Integer[size][size];
        buffers = new ArrayList();
        this.usrName = usrName;
    }
    
    private void createNewClock()
    {
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                clock[i][j] = 0;
    }
    
    public void printClock()
    {
        if (!printer) return;
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
                System.out.print(clock[i][j] + " ");
            System.out.print("\n");
        }
    }
    
    public void printClocks(Integer[][] senderClock)
    {
        if (!printer) return;
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size*2; j++)
            {
                if (j <= 2)
                    System.out.print(clock[i][j] + " ");
                else
                    System.out.print(senderClock[i][j-size] + " ");
            }
            System.out.print("\n");
        }
    }

    public void printBuffers()
    {
        for (int i = 0; i < buffers.size(); i++)
            System.out.println("Buffer: " + buffers.get(i).msg);
    }
    
    @Override
    public void deliverMsg(String senderName, String msg, Integer[][] senderClock) throws RemoteException
    {
        if (senderClock == null)
        {
            deliverToGUI(senderName, msg);
            users.clear();
            return;
        }
        
        System.out.println("Msg recebida[" + senderName + ": " + msg + "]");
        printClocks(senderClock);
        int senderId = getIdFromName(senderName);
        
        //deliverMessage(senderName, msg, senderId, senderClock);
        //if (true) return;
        
        // Verifica se está em ordem.
        for (int i = 0; i < size; i++)
        {
            if (i == id)// || i == senderId)
                continue;
            
            if (!clock[id][i].equals(senderClock[senderId][i]))
            {
                // Não está em ordem, guarda no buffer e sai.
                buffers.add(new Buffer(senderName, msg, senderId, senderClock[senderId]));
                updateAreaBuffer();
                return;
            }
        }
                               
        // Está em ordem! Entregar e verificar os buffers.
        deliverMessage(senderName, msg, senderId, senderClock);
        verifyBuffers(senderClock);
        printBuffers();
    }
    
    private void deliverMessage(String senderName, String msg, int senderId, Integer[][] senderClock)
    {
        // Copia o vetor do sender.
        for (int i = 0; i < size; i++)
            clock[senderId][i] = senderClock[senderId][i];
        
        // É o esperado!
        deliverToGUI(senderName, msg);
        if (id != senderId)
            clock[id][senderId] += 1;
    }
    
    private void verifyBuffers(Integer[][] senderClock)
    {
        // Verifica se algum buffer será ativado.
        ArrayList<Buffer> buffersToRemove = new ArrayList<>();
        boolean showBuffer;
        for (Buffer b : buffers)
        {
            showBuffer = true;
            // Olha o clock do buffer.
            for (int i = 0; i < size; i++)
            {
                if (i == id)
                    continue;
                
                if (!b.clocks[i].equals(clock[id][i]))
                    showBuffer = false;
            }
            
            if (showBuffer)
            {            
                deliverMessage(b.senderName, b.msg, b.senderId, senderClock);            
                buffersToRemove.add(b);
            }
        }
        for (int i = 0; i < buffersToRemove.size(); i++)
        {
            System.out.println("Removed[" + buffersToRemove.get(i).senderName + ": " + buffersToRemove.get(i).msg + "]");
            buffers.remove(buffersToRemove.get(i));
        }
        System.out.println("Buffer size: " + buffers.size());
        updateAreaBuffer();
    }
    
    private void updateAreaBuffer()
    {
        if (areaBuffer != null)
        {   
            areaBuffer.setText("");
            for (Buffer b : buffers)
                areaBuffer.append(b.senderName + ": " + b.msg + "\n");
        }
    }
    
    private void stabilizeBuffer()
    {
        for (Buffer b : buffers)
            deliverToGUI(b.senderName, b.msg);         
        buffers.clear();
        updateAreaBuffer();
    }
    
    private int getIdFromName(String senderName) throws RemoteException
    {
        if (users.containsKey(senderName))
            return users.get(senderName).getId();
        else
            return -1;           
    }
    
    public void deliverToGUI(String senderName, String msg) // GUI do cliente
    {
        System.out.println("Msg entregue[" + senderName + ": " + msg + "]");
        if (areaChat != null)
        {
            areaChat.append(senderName + ": " + msg + "\n");
            areaChat.setCaretPosition(areaChat.getText().length());
        }
    }
    
    public void setAreaChat(JTextArea areaChat) 
    {
        this.areaChat = areaChat;
    }
    
    public void setAreaBuffer(JTextArea areaBuffer) 
    {
        this.areaBuffer = areaBuffer;
    }

    // Estabilizar mensagens.
    @Override
    public void updateUserList(TreeMap<String, IUserChat> userList) throws RemoteException 
    {
        stabilizeBuffer();
        createNewClock();
        users = userList;
        System.out.print("Me: " + usrName + ", List: ");
        for (String user : users.keySet())
            System.out.print(user + ", ");
        System.out.print("\n");
    }

    @Override
    public int getId() throws RemoteException 
    {
        return id;
    }
}
