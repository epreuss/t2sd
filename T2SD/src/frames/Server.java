/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package frames;

import core.ServerRoomChat;
import chatPackage.IRoomChat;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Estevan
 */
public class Server extends javax.swing.JFrame {

    ServerRoomChat server;
    
    /** Creates new form Server */
    public Server(ServerRoomChat server) throws RemoteException {
        initComponents();
        this.server = server;
        this.server.setFrame(this);
        refreshRooms();
    }
    
    public void refreshRooms() throws RemoteException
    {
        Map<String, IRoomChat> rooms = server.getRooms();
        String[] listData = new String[rooms.size()];
        int i = 0;
        for (String name : rooms.keySet())
        {
            listData[i] = name;
            i++;
        }
        listRooms.setListData(listData);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labelServer = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        listRooms = new javax.swing.JList<>();
        buttonCreate = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        fieldRoom = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        buttonClose = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Server");
        setMinimumSize(new java.awt.Dimension(315, 315));
        setResizable(false);
        getContentPane().setLayout(null);

        labelServer.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        labelServer.setText("Server");
        getContentPane().add(labelServer);
        labelServer.setBounds(10, 11, 280, 22);
        getContentPane().add(jSeparator1);
        jSeparator1.setBounds(10, 39, 280, 10);

        listRooms.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        listRooms.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listRoomsValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(listRooms);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(10, 70, 120, 160);

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

        buttonClose.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        buttonClose.setText("Close");
        buttonClose.setEnabled(false);
        buttonClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCloseActionPerformed(evt);
            }
        });
        getContentPane().add(buttonClose);
        buttonClose.setBounds(10, 240, 120, 25);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void buttonCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCreateActionPerformed
        try 
        {
            server.createRoom(fieldRoom.getText());
        } catch (RemoteException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        buttonClose.setEnabled(false);
    }//GEN-LAST:event_buttonCreateActionPerformed

    private void buttonCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCloseActionPerformed
        String selectedRoom = listRooms.getSelectedValue();
        if (selectedRoom == "null")
        {
            System.out.println("No selection");
            return;
        }
        try 
        {
            IRoomChat stub = server.getRoomRef(selectedRoom);
            stub.closeRoom();
            server.removeRoom(selectedRoom);
            stub = null;
            refreshRooms();
        } catch (Exception ex) 
        {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_buttonCloseActionPerformed

    private void listRoomsValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listRoomsValueChanged
        buttonClose.setEnabled(listRooms.getSelectedValue() != "null");
    }//GEN-LAST:event_listRoomsValueChanged

    /**
     * @param args the command line arguments
     */
    public static void main(ServerRoomChat server) {
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
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Server(server).setVisible(true);
                } catch (RemoteException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonClose;
    private javax.swing.JButton buttonCreate;
    private javax.swing.JTextField fieldRoom;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel labelServer;
    private javax.swing.JList<String> listRooms;
    // End of variables declaration//GEN-END:variables

}
