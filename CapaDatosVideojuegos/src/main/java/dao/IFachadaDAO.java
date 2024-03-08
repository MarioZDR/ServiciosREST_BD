package dao;

import dominio.Videojuego;

public interface IFachadaDAO {

    public Videojuego registrarVideojuego(Videojuego videojuego);

    public Videojuego consultarVideojuego(Long idVideojuego);

    public Videojuego[] consultarVideojuegos();

    public Videojuego[] consultarVideojuegos(String inicioNombre, int anioLanzamiento);

    public Videojuego modificarVideojuego(Videojuego videojuego);

    public Videojuego eliminarVideojuego(Long idVideojuego);
    
}
