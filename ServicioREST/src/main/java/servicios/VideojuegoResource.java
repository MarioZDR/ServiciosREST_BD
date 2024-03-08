package servicios;

import dominio.Videojuego;
import dao.FachadaDAO;
import dao.IFachadaDAO;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("Videojuego")
public class VideojuegoResource {

    @Context
    private UriInfo context;
    private IFachadaDAO dao;

    public VideojuegoResource() {
        dao = new FachadaDAO();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response consultarVideojuegos() {
        Videojuego[] videojuegos = dao.consultarVideojuegos();
        if (videojuegos != null) {
            if (videojuegos.length > 0) {
                return Response.ok().entity(videojuegos).build();
            }
        }
        return Response.status(404).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response consultarVideojuego(@PathParam("id") Long idVideojuego) {
        Videojuego videojuegoConsultado = dao.consultarVideojuego(idVideojuego);
        if (videojuegoConsultado == null) {
            return Response.status(404).build();
        } else {
            return Response.ok().entity(videojuegoConsultado).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response agregarVideojuego(Videojuego videojuego) {
        Videojuego videojuegoRegistrado = dao.registrarVideojuego(videojuego);
        return Response.ok().entity(videojuegoRegistrado).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarVideojuego(Videojuego videojuego) {
        if (dao.modificarVideojuego(videojuego) == null) {
            return Response.status(404).build();
        } else {
            return Response.ok().entity(videojuego).build();
        }
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response eliminarVideojuego(@PathParam("id") Long idVideojuego) {
        Videojuego videojuegoEliminado = dao.eliminarVideojuego(idVideojuego);
        if (videojuegoEliminado == null) {
            return Response.status(404).build();
        } else {
            return Response.ok().entity(videojuegoEliminado).build();
        }
    }

    @GET
    @Path("/query")
    @Produces(MediaType.APPLICATION_JSON)
    public Response consultarVideojuegos(
            @QueryParam("inicioNombre") String inicioNombre,
            @QueryParam("anioLanzamiento") int anioLanzamiento) {
        Videojuego[] videojuegos = dao.consultarVideojuegos(inicioNombre, anioLanzamiento);
        if (videojuegos != null) {
            if (videojuegos.length > 0) {
                return Response.ok().entity(videojuegos).build();
            }
        }
        return Response.status(404).build();
    }
}
