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
    public Response agregarVideojuego(Videojuego videojuego) {
        dao.registrarVideojuego(videojuego);
        return Response.ok().build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response actualizarVideojuego(Videojuego videojuego) {
        if (dao.modificarVideojuego(videojuego) == null) {
            return Response.status(404).build();
        } else {
            return Response.ok().build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response eliminarVideojuego(@PathParam("id") Long idVideojuego) {
        if (dao.eliminarVideojuego(idVideojuego) == null) {
            return Response.status(404).build();
        } else {
            return Response.ok().build();
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
