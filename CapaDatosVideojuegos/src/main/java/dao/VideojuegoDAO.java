/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dominio.Videojuego;
import conexion.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mario
 */
public class VideojuegoDAO {
    
    private Conexion conexion;

    public VideojuegoDAO() {
        conexion = new Conexion();
    }
    
    public Videojuego registrarVideojuego(Videojuego videojuego) {
        PreparedStatement pst = null;
        try {
            String consulta = "INSERT INTO VIDEOJUEGOS(NOMBRE, ANIOLANZAMIENTO) VALUES(?,?);";
            pst = this.conexion.getConexion().prepareStatement(consulta);
            pst.setString(1, videojuego.getNombre());
            pst.setInt(2, videojuego.getAnioLanzamiento());

            if (pst.executeUpdate() == 1) {
                return videojuego;
            }
        } catch (Exception ex) {
            return null;
        }
        return null;
    }

    public Videojuego consultarVideojuego(Long idVideojuego) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            String consulta = "SELECT * FROM VIDEOJUEGOS WHERE ID = ?;";
            pst = this.conexion.getConexion().prepareStatement(consulta);
            pst.setLong(1, idVideojuego);
            rs = pst.executeQuery();

            if (rs.next()) {
                Videojuego videojuego = new Videojuego();
                videojuego.setId(rs.getLong("ID"));
                videojuego.setNombre(rs.getString("NOMBRE"));
                videojuego.setAnioLanzamiento(rs.getInt("ANIOLANZAMIENTO"));
                return videojuego;
            }
        } catch (Exception ex) {
            return null;
        }
        return null;
    }

    public Videojuego[] consultarVideojuegos() {
        PreparedStatement pst = null;
        ResultSet rs = null;
        List<Videojuego> videojuegos = new ArrayList<>();
        try {
            String consulta = "SELECT * FROM VIDEOJUEGOS;";
            pst = this.conexion.getConexion().prepareStatement(consulta);
            rs = pst.executeQuery();

            while (rs.next()) {
                Videojuego videojuego = new Videojuego();
                videojuego.setId(rs.getLong("ID"));
                videojuego.setNombre(rs.getString("NOMBRE"));
                videojuego.setAnioLanzamiento(rs.getInt("ANIOLANZAMIENTO"));
                videojuegos.add(videojuego);
            }
            return videojuegos.toArray(new Videojuego[videojuegos.size()]);
        } catch (Exception ex) {
            return null;
        }
    }

    public Videojuego[] consultarVideojuegos(String inicioNombre, int anioLanzamiento) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        List<Videojuego> videojuegos = new ArrayList<>();
        try {
            String consulta = "SELECT * FROM VIDEOJUEGOS WHERE NOMBRE LIKE ? AND ANIOLANZAMIENTO = ?;";
            pst = this.conexion.getConexion().prepareStatement(consulta);
            pst.setString(1, inicioNombre + "%");
            pst.setInt(2, anioLanzamiento);
            rs = pst.executeQuery();

            while (rs.next()) {
                Videojuego videojuego = new Videojuego();
                videojuego.setId(rs.getLong("ID"));
                videojuego.setNombre(rs.getString("NOMBRE"));
                videojuego.setAnioLanzamiento(rs.getInt("ANIOLANZAMIENTO"));
                videojuegos.add(videojuego);
            }
            return videojuegos.toArray(new Videojuego[videojuegos.size()]);
        } catch (Exception ex) {
            return null;
        }
    }

    public Videojuego modificarVideojuego(Videojuego videojuego) {
        PreparedStatement pst = null;
        try {
            String consulta = "UPDATE VIDEOJUEGOS SET NOMBRE = ?, ANIOLANZAMIENTO = ? WHERE ID = ?;";
            pst = this.conexion.getConexion().prepareStatement(consulta);
            pst.setString(1, videojuego.getNombre());
            pst.setInt(2, videojuego.getAnioLanzamiento());
            pst.setLong(3, videojuego.getId());

            if (pst.executeUpdate() == 1) {
                return videojuego;
            }
        } catch (Exception ex) {
            return null;
        }
        return null;
    }

    public Videojuego eliminarVideojuego(Long idVideojuego) {
        PreparedStatement pst = null;
        try {
            String consulta = "DELETE FROM VIDEOJUEGOS WHERE ID = ?;";
            pst = this.conexion.getConexion().prepareStatement(consulta);
            pst.setLong(1, idVideojuego);

            if (pst.executeUpdate() == 1) {
                Videojuego videojuego = new Videojuego();
                videojuego.setId(idVideojuego);
                return videojuego;
            }
        } catch (Exception ex) {
            return null;
        }
        return null;
    }

    
}
