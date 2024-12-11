/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package com.mycompany.ejerciciobd2;

import java.util.Scanner;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Date;

/**
 *
 * @author damdu109
 */
public class EjercicioBD2 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Scanner scStr = new Scanner(System.in);
        Scanner scInt = new Scanner(System.in);
        int contador = 0;
        int idUsuario = 0;
        String email = "";
        String telefono = "";
        String nombre = "";
        LocalDateTime fechaHoraActual = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fechaFormateada = fechaHoraActual.format(formatter);

        int opcion;
        
        NewJFrame menu = new NewJFrame();
        menu.setVisible(true);
        menu.setLocationRelativeTo(null);
        
        /*
        do {
            System.out.println("Menu:");
            System.out.println("1. Añadir nuevo usuario");
            System.out.println("2. Eliminar usuario de la base de datos");
            System.out.println("3. Modificar datos de un usuario");
            System.out.println("4. Buscar usuario por nombre o email");
            System.out.println("5. Realizar un prestamo");
            System.out.println("6. Devolver un libro");
            System.out.println("0. Salir");
            System.out.println("Seleccione una opcion: ");
            opcion = scInt.nextInt();

            switch (opcion) {
                case 1:

                    System.out.println("Voy a solicitarte los datos del empleado que quieres añadir:");
                    System.out.println("Nombre: ");
                    nombre = scStr.nextLine();
                    System.out.println("Email: ");
                    email = scStr.nextLine();
                    System.out.println("Telefono: ");
                    telefono = scStr.nextLine();

                    try ( Connection conexion = DriverManager.getConnection(
                            "jdbc:mysql://localhost:3307/biblioteca", "root", "");  PreparedStatement ps = conexion.prepareStatement("INSERT INTO usuarios (nombre, email, telefono) VALUES (?, ?, ?)")) {
                        ps.setString(1, nombre);
                        ps.setString(2, email);
                        ps.setString(3, telefono);

                        int filasInsertadas = ps.executeUpdate();
                        if (filasInsertadas > 0) {
                            System.out.println("Usuario añadido correctamente.");
                        } else {
                            System.out.println("No se pudo añadir el empleado.");
                        }

                    } catch (SQLException e) {
                        System.out.println("Código de Error:" + e.getErrorCode() + "\n"
                                + "SLQState:" + e.getSQLState() + "\n"
                                + "Mensaje:" + e.getMessage() + "\n");
                    }

                    break;
                case 2:
                    
                    try ( Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3307/biblioteca", "root", "")) {

                    System.out.println("Esta es la lista de usuarios disponibles");

                    String sql = "SELECT * FROM usuarios";

                    PreparedStatement ps = conexion.prepareStatement(sql);
                    ResultSet rs = ps.executeQuery();

                    while (rs.next()) {

                        System.out.println("Numero: " + rs.getInt(1) + "\nNome: " + rs.getString(2) + "\nEmail :" + rs.getString(3) + "\nTelefono: " + rs.getString(4));

                        contador++;

                    }

                } catch (SQLException e) {
                    System.out.println("Código de Error: " + e.getErrorCode()
                            + "\nSLQState: " + e.getSQLState()
                            + "\nMensaje: " + e.getMessage());
                }

                System.out.println("Dime el numero de empleado que deseas eliminar: ");

                int numeroBorrar = scInt.nextInt();

                try ( Connection conexion = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3307/biblioteca", "root", "");  PreparedStatement ps = conexion.prepareStatement("DELETE FROM usuarios WHERE id_usuario = ?")) {

                    ps.setInt(1, numeroBorrar);

                    int filasInsertadas = ps.executeUpdate();
                    if (filasInsertadas > 0) {
                        System.out.println("Usuario eliminado correctamente.");
                    } else {
                        System.out.println("No se pudo eliminar al usuario.");
                    }

                } catch (SQLException e) {
                    System.out.println("Código de Error:" + e.getErrorCode() + "\n"
                            + "SLQState:" + e.getSQLState() + "\n"
                            + "Mensaje:" + e.getMessage() + "\n");
                }

                break;
                
                case 3:
                    
                    try ( Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3307/biblioteca", "root", "")) {

                    System.out.println("Esta es la lista de usuarios disponibles");

                    String sql = "SELECT * FROM usuarios";

                    PreparedStatement ps = conexion.prepareStatement(sql);
                    ResultSet rs = ps.executeQuery();

                    while (rs.next()) {

                        System.out.println("Numero: " + rs.getInt(1) + "\nNome: " + rs.getString(2) + "\nEmail :" + rs.getString(3) + "\nTelefono: " + rs.getString(4));

                    }

                } catch (SQLException e) {
                    System.out.println("Código de Error: " + e.getErrorCode()
                            + "\nSLQState: " + e.getSQLState()
                            + "\nMensaje: " + e.getMessage());
                }

                System.out.println("Dime el numero de usuario que deseas modificar: ");

                int escogido = scInt.nextInt();

                try ( Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3307/biblioteca", "root", "")) {

                    String sql = "SELECT * FROM usuarios WHERE id_usuario = ?";

                    PreparedStatement ps = conexion.prepareStatement(sql);

                    ps.setInt(1, escogido);

                    ResultSet rs = ps.executeQuery();

                    while (rs.next()) {

                        nombre = rs.getString(2);
                        email = rs.getString(3);
                        telefono = rs.getString(4);

                    }

                } catch (SQLException e) {
                    System.out.println("Código de Error: " + e.getErrorCode()
                            + "\nSLQState: " + e.getSQLState()
                            + "\nMensaje: " + e.getMessage());
                }

                int opcion2;
                do {
                    System.out.println("Selecciona el dato que quieres modificar:");
                    System.out.println("1. Nombre");
                    System.out.println("2. Email");
                    System.out.println("3. Telefono");
                    System.out.println("0. Guardar y Salir");
                    System.out.println("Seleccione una opcion: ");
                    opcion2 = scInt.nextInt();

                    switch (opcion2) {
                        case 1:
                            System.out.println("Dime el nuevo nombre del usuario: ");
                            nombre = scStr.nextLine();
                            break;

                        case 2:
                            System.out.println("Dime el nuevo email del usuario: ");
                            email = scStr.nextLine();
                            break;

                        case 3:
                            System.out.println("Dime el nuevo telefono del usuario");
                            telefono = scStr.nextLine();
                            break;

                        case 0:
                            String sql = "UPDATE usuarios SET nombre = ?, email = ?, telefono = ? WHERE id_usuario = ?";
                            try ( Connection conexion = DriverManager.getConnection(
                                    "jdbc:mysql://localhost:3307/biblioteca", "root", "");  PreparedStatement ps = conexion.prepareStatement(sql)) {

                                ps.setString(1, nombre);
                                ps.setString(2, email);
                                ps.setString(3, telefono);
                                ps.setInt(4, escogido);

                                int rs = ps.executeUpdate();
                                System.out.println("Filas afectadas: " + rs);

                                if (rs > 0) {
                                    System.out.println("Empleado modificado correctamente.");
                                } else {
                                    System.out.println("Se produjo un error. No se ha realizado ninguna modificacion.");
                                }

                            } catch (SQLException e) {
                                System.out.println("Código de Error:" + e.getErrorCode() + "\n"
                                        + "SLQState:" + e.getSQLState() + "\n"
                                        + "Mensaje:" + e.getMessage() + "\n");
                            }

                            break;

                    }

                } while (opcion2 != 0);

                break;

                case 4:

                    System.out.println("Por que dato quieres buscar?: ");

                    int opcion3;
                    do {
                        System.out.println("1. Nombre");
                        System.out.println("2. Email");
                        System.out.println("0. Salir");
                        System.out.println("Seleccione una opcion: ");
                        opcion3 = scInt.nextInt();

                        switch (opcion3) {

                            case 1:

                                System.out.println("Dime el nombre del usuario que quieres buscar: ");

                                String nombreBuscar = sc.nextLine();

                                try ( Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3307/biblioteca", "root", "")) {

                                    String sql = "SELECT * FROM usuarios WHERE nombre = ?";

                                    PreparedStatement ps = conexion.prepareStatement(sql);

                                    ps.setString(1, nombreBuscar);

                                    ResultSet rs = ps.executeQuery();

                                    while (rs.next()) {

                                        System.out.println("Numero: " + rs.getInt(1) + "\nNome: " + rs.getString(2) + "\nEmail :" + rs.getString(3) + "\nTelefono: " + rs.getString(4));

                                    }

                                } catch (SQLException e) {
                                    System.out.println("Código de Error: " + e.getErrorCode()
                                            + "\nSLQState: " + e.getSQLState()
                                            + "\nMensaje: " + e.getMessage());
                                }

                                break;

                            case 2:

                                System.out.println("Dime el email del usuario que quieres buscar: ");

                                String emailBuscar = sc.nextLine();

                                try ( Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3307/biblioteca", "root", "")) {

                                    String sql = "SELECT * FROM usuarios WHERE email = ?";

                                    PreparedStatement ps = conexion.prepareStatement(sql);

                                    ps.setString(1, emailBuscar);

                                    ResultSet rs = ps.executeQuery();

                                    while (rs.next()) {

                                        System.out.println("Numero: " + rs.getInt(1) + "\nNome: " + rs.getString(2) + "\nEmail :" + rs.getString(3) + "\nTelefono: " + rs.getString(4));

                                    }

                                } catch (SQLException e) {
                                    System.out.println("Código de Error: " + e.getErrorCode()
                                            + "\nSLQState: " + e.getSQLState()
                                            + "\nMensaje: " + e.getMessage());
                                }

                                break;

                        }
                    } while (opcion3 != 0);
                    break;

                case 5:

                    System.out.println("Dime tu ID de usuario: ");

                    int id = sc.nextInt();

                    System.out.println("Estos son los libros disponibles: ");

                    try ( Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3307/biblioteca", "root", "")) {

                        String sql = "SELECT * FROM libros WHERE disponible > 0";

                        PreparedStatement ps = conexion.prepareStatement(sql);

                        ResultSet rs = ps.executeQuery();

                        while (rs.next()) {

                            System.out.println("ID: " + rs.getInt(1) + "Titulo: " + rs.getString(2) + "Autor :" + rs.getString(3) + "Año de publicacion: " + rs.getString(4));

                        }

                    } catch (SQLException e) {
                        System.out.println("Código de Error: " + e.getErrorCode()
                                + "\nSLQState: " + e.getSQLState()
                                + "\nMensaje: " + e.getMessage());
                    }

                    System.out.println("Selecciona el ID del libro que quieres encargar: ");

                    int prestamo = sc.nextInt();

                    String sql = "UPDATE libros SET disponible = ? WHERE ID = ?";

                    try ( Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3307/biblioteca", "root", "")) {

                        PreparedStatement ps = conexion.prepareStatement(sql);

                        ps.setInt(1, 0);
                        ps.setInt(2, prestamo);

                        int rs = ps.executeUpdate();
                        System.out.println("Filas afectadas: " + rs);

                        if (rs > 0) {
                            System.out.println("Libro prestado correctamente.");
                        } else {
                            System.out.println("Se produjo un error. No se ha realizado el prestamo.");
                        }

                    } catch (SQLException e) {
                        System.out.println("Código de Error: " + e.getErrorCode()
                                + "\nSLQState: " + e.getSQLState()
                                + "\nMensaje: " + e.getMessage());
                    }

                    try ( Connection conexion = DriverManager.getConnection(
                            "jdbc:mysql://localhost:3307/biblioteca", "root", "");  PreparedStatement ps = conexion.prepareStatement("INSERT INTO prestamos (id_libro, id_usuario, fecha_prestamo) VALUES (?, ?, ?)")) {

                        ps.setInt(1, prestamo);
                        ps.setInt(2, id);
                        ps.setString(3, fechaFormateada);

                        int filasInsertadas = ps.executeUpdate();
                        if (filasInsertadas > 0) {
                            System.out.println("Prestamo añadido correctamente.");
                        } else {
                            System.out.println("No se pudo añadir el prestamo.");
                        }

                    } catch (SQLException e) {
                        System.out.println("Código de Error:" + e.getErrorCode() + "\n"
                                + "SLQState:" + e.getSQLState() + "\n"
                                + "Mensaje:" + e.getMessage() + "\n");
                    }

                    break;
                    
                    case 6:

                    System.out.println("Dime tu ID de usuario: ");

                    id = sc.nextInt();

                    System.out.println("Estos son los prestamos actuales: ");

                    try ( Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3307/biblioteca", "root", "")) {

                        sql = "SELECT * FROM prestamos WHERE id_usuario = ? AND fecha_devolucion IS NULL";

                        PreparedStatement ps = conexion.prepareStatement(sql);
                        
                        ps.setInt(1, id);

                        ResultSet rs = ps.executeQuery();

                        while (rs.next()) {

                            System.out.println("ID: " + rs.getInt(1) + "Titulo: " + rs.getString(2) + "Autor :" + rs.getString(3) + "Año de publicacion: " + rs.getString(4));

                        }

                    } catch (SQLException e) {
                        System.out.println("Código de Error: " + e.getErrorCode()
                                + "\nSLQState: " + e.getSQLState()
                                + "\nMensaje: " + e.getMessage());
                    }

                    System.out.println("Selecciona el ID del libro que quieres devolver: ");

                    int devolucion = sc.nextInt();

                    sql = "UPDATE libros SET disponible = ? WHERE ID = ?";

                    try ( Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3307/biblioteca", "root", "")) {

                        PreparedStatement ps = conexion.prepareStatement(sql);

                        ps.setInt(1, 1);
                        ps.setInt(2, devolucion);

                        int rs = ps.executeUpdate();
                        System.out.println("Filas afectadas: " + rs);

                        if (rs > 0) {
                            System.out.println("Libro devuelto correctamente.");
                        } else {
                            System.out.println("Se produjo un error. No se ha realizado la devolucion.");
                        }

                    } catch (SQLException e) {
                        System.out.println("Código de Error: " + e.getErrorCode()
                                + "\nSLQState: " + e.getSQLState()
                                + "\nMensaje: " + e.getMessage());
                    }

                    try ( Connection conexion = DriverManager.getConnection(
                            "jdbc:mysql://localhost:3307/biblioteca", "root", "");  PreparedStatement ps = conexion.prepareStatement("UPDATE prestamos SET fecha_devolucion = ? WHERE id_libro = ?")) {

                        ps.setString(1, fechaFormateada);
                        ps.setInt(2, devolucion);

                        int filasInsertadas = ps.executeUpdate();
                        if (filasInsertadas > 0) {
                            System.out.println("Devolucion gestionada correctamente.");
                        } else {
                            System.out.println("No se pudo gestionar la devolucion.");
                        }

                    } catch (SQLException e) {
                        System.out.println("Código de Error:" + e.getErrorCode() + "\n"
                                + "SLQState:" + e.getSQLState() + "\n"
                                + "Mensaje:" + e.getMessage() + "\n");
                    }

                    break;
                    
                    
                case 0:

                    System.out.println("Saliendo del programa. Hasta luego!");

                    break;

                default:

                    System.out.println("Opción no válida. Por favor, seleccione una opción correcta.");

            }

        } while (opcion != 0);
    */}
}
