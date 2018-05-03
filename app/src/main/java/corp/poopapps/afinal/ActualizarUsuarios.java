package corp.poopapps.afinal;

public class ActualizarUsuarios {

    private String id, nombre, correo, sexo;
    private int edad;

    public ActualizarUsuarios(String id, String Nombre, String Sexo, int Edad) {
        this.id = id;
        this.nombre = Nombre;
        this.sexo = Sexo;
        this.edad = Edad;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getSexo(){
        return sexo;
    }

    public int getEdad(){
        return edad;
    }
}
