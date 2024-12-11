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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
public class RealizarPrestamo extends javax.swing.JFrame {

    String idLibro;
    String tituloLibro;
    int idUsuario;
    LocalDateTime fechaHoraActual = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String fechaFormateada = fechaHoraActual.format(formatter);

    public RealizarPrestamo(int idUsuario) {
        initComponents();
        mostrarLibros();
        this.idUsuario = idUsuario;
        this.setResizable(false);

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

        listaLibros.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    realizarPrestamo();
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

    private void mostrarLibros() {
        DefaultListModel<String> userListModel = new DefaultListModel<>();
        listaLibros.setModel(userListModel);
        userListModel.clear();

        try ( Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3307/biblioteca", "root", "")) {
            String sql = "SELECT * FROM libros WHERE disponible = 1";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            String inicio = "ID ; TITULO ; AUTOR ; AÑO PUBLICACION ";
            String linea = "------------------------------------ ";
            userListModel.addElement(inicio);
            userListModel.addElement(linea);

            while (rs.next()) {
                String libro = rs.getString(1) + " ; " + rs.getString(2) + " ; " + rs.getString(3) + " ; " + rs.getString(4);
                userListModel.addElement(libro);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al recuperar usuarios:\n" + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void BuscarNombre() {

        DefaultListModel<String> userListModel = new DefaultListModel<>();
        listaLibros.setModel(userListModel);
        userListModel.clear();

        try ( Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3307/biblioteca", "root", "")) {

            String sql = "SELECT * FROM libros WHERE disponible = 1 AND titulo LIKE ?";

            PreparedStatement ps = conexion.prepareStatement(sql);

            ps.setString(1, "%" + respuesta.getText() + "%");

            ResultSet rs = ps.executeQuery();

            String inicio = "ID ; TITULO ; AUTOR ; AÑO PUBLICACION ";
            String linea = "-------------------------------------------------------------- ";
            userListModel.addElement(inicio);
            userListModel.addElement(linea);

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

    private void realizarPrestamo() {
        String selectedBook = listaLibros.getSelectedValue();
        if (selectedBook == null) {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona el libro que quieres alquilar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        idLibro = selectedBook.split(";")[0].trim();
        tituloLibro = selectedBook.split(";")[1].trim();

        int confirmacion = JOptionPane.showConfirmDialog(this, "Vas a alquilar el libro: " + tituloLibro + " , ¿Es correcto?", "Confirmar alquiler", JOptionPane.YES_NO_OPTION);
        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }

        String sql = "UPDATE libros SET disponible = ? WHERE ID = ?";

        try ( Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3307/biblioteca", "root", "")) {

            PreparedStatement ps = conexion.prepareStatement(sql);

            ps.setInt(1, 0);
            ps.setInt(2, Integer.parseInt(idLibro));

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Código de Error: " + e.getErrorCode()
                    + "\nSLQState: " + e.getSQLState()
                    + "\nMensaje: " + e.getMessage());
        }

        try ( Connection conexion = DriverManager.getConnection(
                "jdbc:mysql://localhost:3307/biblioteca", "root", "");  PreparedStatement ps = conexion.prepareStatement("INSERT INTO prestamos (id_libro, id_usuario, fecha_prestamo) VALUES (?, ?, ?)")) {

            ps.setString(1, idLibro);
            ps.setInt(2, idUsuario);
            ps.setString(3, fechaFormateada);

            int filasInsertadas = ps.executeUpdate();
            if (filasInsertadas > 0) {
                JOptionPane.showMessageDialog(this, "Prestamo registrado correctamente", "Prestado Correctamente", JOptionPane.INFORMATION_MESSAGE);
                this.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, "No se ha podido realizar el prestamo. Intentelo de nuevo.", "ERROR", JOptionPane.ERROR_MESSAGE);

            }

        } catch (SQLException e) {
            System.out.println("Código de Error:" + e.getErrorCode() + "\n"
                    + "SLQState:" + e.getSQLState() + "\n"
                    + "Mensaje:" + e.getMessage() + "\n");
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
        jScrollPane1 = new javax.swing.JScrollPane();
        listaLibros = new javax.swing.JList<>();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        botonVolver = new javax.swing.JButton();
        respuesta = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Copperplate Gothic Bold", 0, 18)); // NOI18N
        jLabel1.setText("REALIZAR PRESTAMO");

        listaLibros.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(listaLibros);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Buscar titulo en la lista:");

        jButton1.setText("Realizar Prestamo");
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

        respuesta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                respuestaActionPerformed(evt);
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
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(botonVolver))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(respuesta, javax.swing.GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(respuesta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(botonVolver))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonVolverActionPerformed
        this.setVisible(false);    // TODO add your handling code here:
    }//GEN-LAST:event_botonVolverActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        realizarPrestamo();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void respuestaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_respuestaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_respuestaActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonVolver;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<String> listaLibros;
    private javax.swing.JTextField respuesta;
    // End of variables declaration//GEN-END:variables
}
