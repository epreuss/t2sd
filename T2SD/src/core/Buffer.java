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
    public int senderId;
    public int clock;
    public Integer[] clocks;
    
    public Buffer(String senderName, String msg, int senderId, int clock)
    {
        this.senderName = senderName;
        this.msg = msg;
        this.senderId = senderId;
        this.clock = clock;
    }
    
    public Buffer(String senderName, String msg, int senderId, Integer[] clocks)
    {
        this.senderName = senderName;
        this.msg = msg;
        this.senderId = senderId;
        this.clocks = clocks;
    }
}
