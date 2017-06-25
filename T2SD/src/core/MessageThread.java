/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import chatPackage.IUserChat;
import frames.Room;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Estevan
 */
public class MessageThread extends Thread
{
    UserChat user;
    String msg;
    int target;
    
    public MessageThread(UserChat user)
    {
        this.user = user;
    }
    
    public void setStuff(String msg, int target)
    {
        this.msg = msg;
        this.target = target;
    }
    
    @Override
    public void run() 
    {
        try {
            Thread.sleep(2000);
            sendMessageToTarget();
        } catch (InterruptedException ex) {
            Logger.getLogger(MessageThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    private void sendMessageToTarget()
    {
        try {
            int i = 0;
            for (IUserChat listener : user.users.values())
            {
                if (i == target)
                {
                    listener.deliverMsg(user.usrName, msg, user.clock);
                    return;
                }
                i++;
            }
        } catch (RemoteException ex) {
            Logger.getLogger(Room.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
}
