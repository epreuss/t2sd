/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frames;

import core.UserChat;
import interfaces.IRoomChat;
import interfaces.IServerRoomChat;
import interfaces.IUserChat;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Estevan
 */
public class User extends javax.swing.JFrame {

    List<IRoomChat> roomsRefs;
    UserChat user;
    
    public User(UserChat user) throws RemoteException {
        initComponents();      
        this.user = user;
        labelUser.setText(user.usrName);
        requestServerRooms();
    }
    
    public void emptyRoomsList()
    {
        listRooms.setListData(new String[]{});
    }
    
    public void requestServerRooms()
    {
        try {
            Registry registry = LocateRegistry.getRegistry(user.serverIp, 2020);
            IServerRoomChat stub = (IServerRoomChat) registry.lookup("ServerRoomChat");
            roomsRefs = stub.getRooms();
            refreshListRooms();
        } catch (Exception ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            emptyRoomsList();
        }
    }
    
    public void requestRoomCreation(String roomName)
    {
        try {
            Registry registry = LocateRegistry.getRegistry(user.serverIp, 2020);
            IServerRoomChat stub = (IServerRoomChat) registry.lookup("ServerRoomChat");
            stub.criateRoom(roomName);
        } catch (Exception ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void refreshListRooms() throws RemoteException
    {
        String[] listData = new String[roomsRefs.size()];
        for (int i = 0; i < roomsRefs.size(); i++)
            listData[i] = roomsRefs.get(i).getName();
        listRooms.setListData(listData);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        listRooms = new javax.swing.JList<>();
        labelUser = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        buttonJoin = new javax.swing.JButton();
        buttonCreate = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        fieldRoom = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        buttonRefresh = new javax.swing.JButton();

        jButton1.setText("jButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("User");
        setMinimumSize(new java.awt.Dimension(320, 345));
        getContentPane().setLayout(null);

        listRooms.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(listRooms);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(10, 70, 120, 160);

        labelUser.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        labelUser.setText("User");
        getContentPane().add(labelUser);
        labelUser.setBounds(10, 11, 280, 22);
        getContentPane().add(jSeparator1);
        jSeparator1.setBounds(10, 39, 280, 10);

        buttonJoin.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        buttonJoin.setText("Join");
        buttonJoin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonJoinActionPerformed(evt);
            }
        });
        getContentPane().add(buttonJoin);
        buttonJoin.setBounds(10, 270, 120, 25);

        buttonCreate.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        buttonCreate.setText("Create");
        buttonCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCreateActionPerformed(evt);
            }
        });
        getContentPane().add(buttonCreate);
        buttonCreate.setBounds(140, 110, 140, 25);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Rooms:");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(10, 50, 90, 17);

        fieldRoom.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        fieldRoom.setText("MyRoom");
        getContentPane().add(fieldRoom);
        fieldRoom.setBounds(140, 70, 140, 30);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Room name:");
        jLabel5.setToolTipText("");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(140, 50, 140, 17);

        buttonRefresh.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        buttonRefresh.setText("Refresh");
        buttonRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonRefreshActionPerformed(evt);
            }
        });
        getContentPane().add(buttonRefresh);
        buttonRefresh.setBounds(10, 240, 120, 25);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void buttonCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCreateActionPerformed
        requestRoomCreation(fieldRoom.getText());
        requestServerRooms();
    }//GEN-LAST:event_buttonCreateActionPerformed

    private void buttonJoinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonJoinActionPerformed
        String selectedRoom = listRooms.getSelectedValue();
        if (selectedRoom == "null")
        {
            System.out.println("No selection");
            return;
        }
        requestServerRooms();
        System.out.println("Selected room: " + selectedRoom);
        try {
            for (IRoomChat room : roomsRefs)
            {
                if (room.getName().equals(selectedRoom))
                {
                    room.joinRoom(user.usrName);
                    Room.main(room, user);
                    System.out.println("Join success");      
                    dispose();
                    return;
                }
            }
        } catch (Exception ex) 
        {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_buttonJoinActionPerformed

    private void buttonRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonRefreshActionPerformed
        requestServerRooms();
    }//GEN-LAST:event_buttonRefreshActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(UserChat user) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(User.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(User.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(User.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(User.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new User(user).setVisible(true);
                } catch (RemoteException ex) {
                    Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonCreate;
    private javax.swing.JButton buttonJoin;
    private javax.swing.JButton buttonRefresh;
    private javax.swing.JTextField fieldRoom;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel labelUser;
    private javax.swing.JList<String> listRooms;
    // End of variables declaration//GEN-END:variables
}
