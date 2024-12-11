/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.ejerciciobd2;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author damdu109
 */
public class IniciarSesion extends javax.swing.JFrame {

    String administrador;
    String password;
    int contador = 0;

    public IniciarSesion() {
        initComponents();
        mostrarContraseña.setEnabled(false);
        this.setResizable(false);

        contraseña.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                actualizarEstadoBoton();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                actualizarEstadoBoton();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                actualizarEstadoBoton();
            }
        });

        contraseña.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inicioSesion.doClick();
            }
        });

        idUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inicioSesion.doClick();
            }
        });

        this.getRootPane().registerKeyboardAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                botonVolver.doClick();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);;

    }

    private void actualizarEstadoBoton() {

        if (contraseña.getPassword().length == 0) {
            mostrarContraseña.setEnabled(false); // Deshabilitar el botón "Mostrar Contraseña"
        } else {
            mostrarContraseña.setEnabled(true); // Habilitar el botón "Mostrar Contraseña"
        }

    }

    private class ProgressBarThread extends Thread {
    public void run() {
        int sleepTime = 2000; // 2 segundos
        int increment = 2; // Incremento de la barra de progreso
        int steps = sleepTime / increment;

        for (int i = 0; i <= steps; i++) {
            float fraction = (float) i / steps; // Fracción del progreso completado
            Color color = interpolateColor(Color.RED, Color.GREEN, fraction); // Interpola entre rojo y verde
            jProgressBar1.setForeground(color); // Establece el color de la barra de progreso
            jProgressBar1.setValue((i * 100) / steps);
            try {
                Thread.sleep(increment);
            } catch (InterruptedException ex) {
                System.err.println(ex);
            }
        }

        iniciarSesion();
    }

    private Color interpolateColor(Color c1, Color c2, float fraction) {
        float r = (float) (c1.getRed() + fraction * (c2.getRed() - c1.getRed()));
        float g = (float) (c1.getGreen() + fraction * (c2.getGreen() - c1.getGreen()));
        float b = (float) (c1.getBlue() + fraction * (c2.getBlue() - c1.getBlue()));
        return new Color((int) r, (int) g, (int) b);
    }
}

    private void iniciarSesion() {

        try ( Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3307/biblioteca", "root", "")) {

            String sql = "SELECT * FROM usuarios WHERE id_usuario = ?";

            PreparedStatement ps = conexion.prepareStatement(sql);

            ps.setInt(1, Integer.parseInt(idUsuario.getText()));

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                administrador = rs.getString(6);
                password = rs.getString(5);

            }

        } catch (SQLException e) {
            System.out.println("Código de Error: " + e.getErrorCode()
                    + "\nSLQState: " + e.getSQLState()
                    + "\nMensaje: " + e.getMessage());
        }
        if (contraseña.getText().equals(password)) {
            JOptionPane.showMessageDialog(this, "Inicio de sesion correcto.", "Exito", JOptionPane.INFORMATION_MESSAGE);
            this.setVisible(false);
            NewJFrame menu = new NewJFrame(administrador, idUsuario.getText());
            menu.setVisible(true);
            menu.setLocationRelativeTo(null);
        } else {

            JOptionPane.showMessageDialog(this, "Error, ID o contraseña incorrectos.", "Error", JOptionPane.ERROR_MESSAGE);
            jProgressBar1.setValue(0);
        }

    }

    private void mostrarContraseña() {
        if (contraseña.getEchoChar() == '\0') {
            contraseña.setEchoChar('\u2022'); // Oculta la contraseña
        } else {
            contraseña.setEchoChar('\0'); // Muestra la contraseña
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
        jLabel3 = new javax.swing.JLabel();
        inicioSesion = new javax.swing.JButton();
        idUsuario = new javax.swing.JTextField();
        botonVolver = new javax.swing.JButton();
        contraseña = new javax.swing.JPasswordField();
        mostrarContraseña = new javax.swing.JButton();
        jProgressBar1 = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Copperplate Gothic Bold", 0, 18)); // NOI18N
        jLabel1.setText("INICIAR  SESION ");

        jLabel2.setText("Introduce tu ID de usuario:");

        jLabel3.setText("Introduce tu contraseña:");

        inicioSesion.setText("Iniciar Sesion");
        inicioSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inicioSesionActionPerformed(evt);
            }
        });

        idUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idUsuarioActionPerformed(evt);
            }
        });

        botonVolver.setText("Volver");
        botonVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonVolverActionPerformed(evt);
            }
        });

        contraseña.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contraseñaActionPerformed(evt);
            }
        });

        mostrarContraseña.setText("Mostrar");
        mostrarContraseña.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mostrarContraseñaActionPerformed(evt);
            }
        });

        jProgressBar1.setForeground(new java.awt.Color(51, 255, 51));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(inicioSesion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(botonVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(98, 98, 98))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(idUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jProgressBar1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                    .addComponent(contraseña, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(mostrarContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(27, 27, 27))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(idUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(contraseña, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mostrarContraseña))
                .addGap(18, 18, 18)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(inicioSesion)
                    .addComponent(botonVolver))
                .addGap(14, 14, 14))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonVolverActionPerformed
        this.setVisible(false);
        NewJFrame menu = new NewJFrame();
        menu.setVisible(true);
        menu.setLocationRelativeTo(null);   // TODO add your handling code here:
    }//GEN-LAST:event_botonVolverActionPerformed

    private void inicioSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inicioSesionActionPerformed
        if(idUsuario.getText().isEmpty() || contraseña.getText().isEmpty()){
        
        JOptionPane.showMessageDialog(this, "Error, ID o contraseña incorrectos.", "Error", JOptionPane.ERROR_MESSAGE);
        
        }
        else{
        
        ProgressBarThread progressBarThread = new ProgressBarThread();
        progressBarThread.start();
        
        }
    }//GEN-LAST:event_inicioSesionActionPerformed

    private void mostrarContraseñaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mostrarContraseñaActionPerformed
        mostrarContraseña();

        contador++;
        if (contador % 2 != 0) {
            mostrarContraseña.setText("Ocultar");
        } else if (contador % 2 == 0) {
            mostrarContraseña.setText("Mostrar");
        }

    }//GEN-LAST:event_mostrarContraseñaActionPerformed

    private void contraseñaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contraseñaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_contraseñaActionPerformed

    private void idUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idUsuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_idUsuarioActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonVolver;
    private javax.swing.JPasswordField contraseña;
    private javax.swing.JTextField idUsuario;
    private javax.swing.JButton inicioSesion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JButton mostrarContraseña;
    // End of variables declaration//GEN-END:variables
}
