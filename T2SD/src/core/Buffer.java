/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package core;

/**
 *
 * @author Owner
 */
public class Buffer 
{
    public String senderName;
    public String msg;
    public int userId;
    public int clock;
    
    public Buffer(String senderName, String msg, int userId, int clock)
    {
        this.senderName = senderName;
        this.msg = msg;
        this.userId = userId;
        this.clock = clock;
    }
}
