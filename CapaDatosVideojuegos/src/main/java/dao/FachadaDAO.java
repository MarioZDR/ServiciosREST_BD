/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dominio.Videojuego;

public class FachadaDAO implements IFachadaDAO{
    
    private VideojuegoDAO videojuegoDAO;

    public FachadaDAO() {
        videojuegoDAO = new VideojuegoDAO();
    }
    
    @Override
    public Videojuego registrarVideojuego(Videojuego videojuego) {
        return videojuegoDAO.registrarVideojuego(videojuego);
    }
    
    @Override
    public Videojuego consultarVideojuego(Long idVideojuego) {
        return videojuegoDAO.consultarVideojuego(idVideojuego);
    }

    @Override
    public Videojuego[] consultarVideojuegos() {
        return videojuegoDAO.consultarVideojuegos();
    }

    @Override
    public Videojuego[] consultarVideojuegos(String inicioNombre, int anioLanzamiento) {
        return videojuegoDAO.consultarVideojuegos(inicioNombre, anioLanzamiento);
    }

    @Override
    public Videojuego modificarVideojuego(Videojuego videojuego) {
        return videojuegoDAO.modificarVideojuego(videojuego);
    }

    @Override
    public Videojuego eliminarVideojuego(Long idVideojuego) {
        return videojuegoDAO.eliminarVideojuego(idVideojuego);
    }
    
}
