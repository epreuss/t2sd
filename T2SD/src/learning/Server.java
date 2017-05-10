package learning;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
        
public class Server  {
        
    public Server() {}

    public static void main(String args[]) {
        
        try {
            Registry registry = LocateRegistry.createRegistry(1099);
            HelloImpl obj = new HelloImpl();
            registry.bind("HelloServer", obj);
            System.out.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}