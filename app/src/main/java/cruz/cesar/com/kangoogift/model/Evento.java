package cruz.cesar.com.kangoogift.model;

/**
 * Created by Cesar on 20/03/2016.
 */
public class Evento {


    private int id;
    private String nombre;
    private String fecha;
    private String comentario;


    public Evento(int id, String nombre, String fecha, String comentario) {
        this.id = id;
        this.nombre = nombre;
        this.fecha = fecha;
        this.comentario = comentario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }


}
