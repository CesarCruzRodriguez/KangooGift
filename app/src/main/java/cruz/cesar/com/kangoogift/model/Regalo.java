package cruz.cesar.com.kangoogift.model;

/**
 * Created by Cesar on 16/04/2016.
 */
public class Regalo {

    private int id;
    private int persona_id;
    private String nombre;
    private String estado;
    private String comentario;

    public Regalo(int id, int persona_id, String nombre, String estado, String comentario) {
        this.id = id;
        this.persona_id = persona_id;
        this.nombre = nombre;
        this.estado = estado;
        this.comentario = comentario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPersona_id() {
        return persona_id;
    }

    public void setPersona_id(int persona_id) {
        this.persona_id = persona_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
