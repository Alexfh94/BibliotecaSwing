/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.ejerciciobd2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author damdu109
 */
public class BuscarUsuario extends javax.swing.JFrame {

   
    public BuscarUsuario() {
        initComponents();
        mostrarUsuarios();
       
        respuesta.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                BuscarNombre();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                BuscarNombre();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                BuscarNombre();
            }
        });
        
        this.getRootPane().registerKeyboardAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                botonVolver.doClick(); 
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);;
        
    }
    


    
    private void mostrarUsuarios() {
        DefaultListModel<String> userListModel = new DefaultListModel<>();
        listaUsuarios.setModel(userListModel);
        userListModel.clear();

        try ( Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3307/biblioteca", "root", "")) {
            String sql = "SELECT * FROM usuarios";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String usuario = rs.getInt(1) + " ; " + rs.getString(2) + " ; " + rs.getString(3) + " ; " + rs.getString(4);
                userListModel.addElement(usuario);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al recuperar usuarios:\n" + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    

    private void BuscarNombre() {

        DefaultListModel<String> userListModel = new DefaultListModel<>();
        listaUsuarios.setModel(userListModel);
        userListModel.clear();

        if(respuesta.getText().contains("@")){
        
        try ( Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3307/biblioteca", "root", "")) {

            String sql = "SELECT * FROM usuarios WHERE email LIKE ?";

            PreparedStatement ps = conexion.prepareStatement(sql);

            ps.setString(1, "%"+respuesta.getText()+"%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String usuario = rs.getInt(1) + " ; " + rs.getString(2) + " ; " + rs.getString(3) + " ; " + rs.getString(4);
                userListModel.addElement(usuario);
            }

        } catch (SQLException e) {
            System.out.println("Código de Error: " + e.getErrorCode()
                    + "\nSLQState: " + e.getSQLState()
                    + "\nMensaje: " + e.getMessage());
        }

    }
        
        else{
        
         try ( Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3307/biblioteca", "root", "")) {

            String sql = "SELECT * FROM usuarios WHERE nombre LIKE ?";

            PreparedStatement ps = conexion.prepareStatement(sql);

            ps.setString(1, "%"+respuesta.getText()+"%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String usuario = rs.getInt(1) + " ; " + rs.getString(2) + " ; " + rs.getString(3) + " ; " + rs.getString(4);
                userListModel.addElement(usuario);
            }

        } catch (SQLException e) {
            System.out.println("Código de Error: " + e.getErrorCode()
                    + "\nSLQState: " + e.getSQLState()
                    + "\nMensaje: " + e.getMessage());
        }
        
            
        }
        
        
        
    }
   
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        respuesta = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        listaUsuarios = new javax.swing.JList<>();
        botonVolver = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Copperplate Gothic Bold", 0, 18)); // NOI18N
        jLabel1.setText("BUSCAR USUARIO");

        jLabel2.setText("Introduce Nombre/Email a buscar:");

        respuesta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                respuestaActionPerformed(evt);
            }
        });

        listaUsuarios.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(listaUsuarios);

        botonVolver.setText("Volver");
        botonVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonVolverActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(respuesta, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(215, 215, 215)
                                .addComponent(jLabel1))
                            .addComponent(jLabel2))
                        .addGap(215, 215, 215)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(279, 279, 279)
                .addComponent(botonVolver)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(respuesta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(botonVolver)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void respuestaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_respuestaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_respuestaActionPerformed

    private void botonVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonVolverActionPerformed
      this.setVisible(false);  // TODO add your handling code here:
    }//GEN-LAST:event_botonVolverActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonVolver;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<String> listaUsuarios;
    private javax.swing.JTextField respuesta;
    // End of variables declaration//GEN-END:variables
}
