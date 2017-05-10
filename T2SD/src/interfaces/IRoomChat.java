package interfaces;

/**
 *
 * @author Estevan
 */
public interface IRoomChat extends java.rmi.Remote
{
    public void sendMsg(String usrName, String msg);
    public void joinRoom(String usrName);
    public void leaveRoom(String usrName);
    public void closeRoom();
    public String getName();
}