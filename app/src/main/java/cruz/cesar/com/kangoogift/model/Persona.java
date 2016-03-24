package cruz.cesar.com.kangoogift.model;

/**
 * Created by Cesar on 24/03/2016.
 */
public class Persona {

    private int id;
    private int evento_id;
    private String nombre;
    private String fecha;
    private String comentario;

    public Persona(int id, int evento_id, String nombre, String fecha, String comentario) {
        this.comentario = comentario;
        this.evento_id = evento_id;
        this.fecha = fecha;
        this.id = id;
        this.nombre = nombre;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public int getEvento_id() {
        return evento_id;
    }

    public void setEvento_id(int evento_id) {
        this.evento_id = evento_id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
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
}
