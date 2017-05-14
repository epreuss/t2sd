/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frames;

import core.Definitions;
import core.ServerRoomChat;
import core.UserChat;
import interfaces.IServerRoomChat;
import interfaces.IUserChat;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Estevan
 */
public class Main extends javax.swing.JFrame {

    /**
     * Creates new form Main
     */
    public Main() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonUser = new javax.swing.JButton();
        buttonServer = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        fieldIp = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        fieldNick = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("!");
        setMinimumSize(new java.awt.Dimension(160, 300));
        getContentPane().setLayout(null);

        buttonUser.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        buttonUser.setText("User");
        buttonUser.setToolTipText("");
        buttonUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonUserActionPerformed(evt);
            }
        });
        getContentPane().add(buttonUser);
        buttonUser.setBounds(10, 220, 120, 30);

        buttonServer.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        buttonServer.setText("Server");
        buttonServer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonServerActionPerformed(evt);
            }
        });
        getContentPane().add(buttonServer);
        buttonServer.setBounds(10, 39, 120, 30);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Start as:");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(10, 11, 120, 17);

        fieldIp.setText("localhost");
        getContentPane().add(fieldIp);
        fieldIp.setBounds(10, 120, 120, 30);
        getContentPane().add(jSeparator1);
        jSeparator1.setBounds(10, 80, 120, 10);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Server IP:");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(10, 100, 90, 17);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Username:");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(10, 160, 90, 17);

        fieldNick.setText("Nick");
        getContentPane().add(fieldNick);
        fieldNick.setBounds(10, 180, 120, 30);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void buttonUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonUserActionPerformed
        Definitions.serverIp = fieldIp.getText();
        try {
            Registry registry = LocateRegistry.getRegistry(Definitions.serverIp, 2020);
            IServerRoomChat stub = (IServerRoomChat) registry.lookup(Definitions.serverBindName);
            UserChat user = new UserChat(fieldNick.getText());
            stub.bindUser((IUserChat) user);
            User.main(user);
            dispose();
        } catch (Exception ex) {
            Logger.getLogger(UserChat.class.getName()).log(Level.SEVERE, null, ex);
        }
        buttonUser.setText("Bug");
    }//GEN-LAST:event_buttonUserActionPerformed

    private void buttonServerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonServerActionPerformed
        ServerRoomChat server = null;
        try {
            server = new ServerRoomChat();
        } catch (RemoteException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        Server.main(server);
        dispose();
    }//GEN-LAST:event_buttonServerActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
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
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonServer;
    private javax.swing.JButton buttonUser;
    private javax.swing.JTextField fieldIp;
    private javax.swing.JTextField fieldNick;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables
}
