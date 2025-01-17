/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.ejerciciobd2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

/**
 *
 * @author damdu109
 */
public class BorrarUsuario extends javax.swing.JFrame {

    /**
     * Creates new form BorrarUsuario
     */
    public BorrarUsuario() {
        initComponents();
        mostrarUsuarios();
        this.setResizable(false);
        listaUsuarios.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    eliminarUsuario();
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        
        this.getRootPane().registerKeyboardAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                botonVolver.doClick(); 
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);;
        
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
        jScrollPane1 = new javax.swing.JScrollPane();
        listaUsuarios = new javax.swing.JList<>();
        jButton1 = new javax.swing.JButton();
        botonVolver = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Copperplate Gothic Bold", 0, 18)); // NOI18N
        jLabel1.setText("BORRAR USUARIOS");

        listaUsuarios.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(listaUsuarios);

        jButton1.setText("Borrar Usuario Seleccionado");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

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
                .addGap(48, 48, 48)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(botonVolver))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(48, 48, 48))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(187, 187, 187)
                .addComponent(jLabel1)
                .addGap(187, 187, 187))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(botonVolver)
                    .addComponent(jButton1))
                .addContainerGap(34, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        eliminarUsuario();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void botonVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonVolverActionPerformed
        this.setVisible(false);         // TODO add your handling code here:
    }//GEN-LAST:event_botonVolverActionPerformed

    /**
     * @param args the command line arguments
     */
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

    private void eliminarUsuario() {
        String selectedUser = listaUsuarios.getSelectedValue();
        if (selectedUser == null) {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona un usuario para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Estás seguro de que deseas eliminar al usuario seleccionado?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }

        try ( Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3307/biblioteca", "root", "");  PreparedStatement ps = conexion.prepareStatement("DELETE FROM usuarios WHERE id_usuario = ?")) {
            ps.setInt(1, Integer.parseInt(selectedUser.split(";")[0].trim()));
            int filasEliminadas = ps.executeUpdate();
            if (filasEliminadas > 0) {
                JOptionPane.showMessageDialog(this, "Usuario eliminado correctamente.", "Eliminación Exitosa", JOptionPane.INFORMATION_MESSAGE);
                mostrarUsuarios();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo eliminar al usuario.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al ejecutar la consulta SQL:\n" + ex.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonVolver;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<String> listaUsuarios;
    // End of variables declaration//GEN-END:variables
}
