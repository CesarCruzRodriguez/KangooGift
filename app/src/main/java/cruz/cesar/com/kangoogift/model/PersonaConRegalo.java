package cruz.cesar.com.kangoogift.model;

/**
 * Created by practicas on 21/4/16.
 */
public class PersonaConRegalo {

    private int id;
    private int evento_id;
    private String nombre;
    private String fecha;
    private int num_regalos;
    private String comentario;

    public PersonaConRegalo(int id, int evento_id, String nombre, String fecha, String comentario, int num_regalos) {
        this.id = id;
        this.evento_id = evento_id;
        this.nombre = nombre;
        this.fecha = fecha;
        this.comentario = comentario;
        this.num_regalos = num_regalos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEvento_id() {
        return evento_id;
    }

    public void setEvento_id(int evento_id) {
        this.evento_id = evento_id;
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

    public int getNum_regalos() {
        return num_regalos;
    }

    public void setNum_regalos(int num_regalos) {
        this.num_regalos = num_regalos;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
