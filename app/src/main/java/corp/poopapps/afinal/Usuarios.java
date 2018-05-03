package corp.poopapps.afinal;

public class Usuarios {

    private String id, nombre, correo;

    public Usuarios(String id, String Nombre, String Correo) {
        this.id = id;
        this.nombre = Nombre;
        this.correo = Correo;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }
}
