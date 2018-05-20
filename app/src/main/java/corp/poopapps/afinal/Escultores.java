package corp.poopapps.afinal;

public class Escultores {

    private String fallecimiento, id, lugarNacimiento, nacimiento, nombre;

    public Escultores(String fallecimiento, String id, String lugarNacimiento, String nacimiento, String nombre) {
        this.fallecimiento = fallecimiento;
        this.id = id;
        this.lugarNacimiento = lugarNacimiento;
        this.nacimiento = nacimiento;
        this.nombre = nombre;
    }

    public Escultores() {
    }

    public String getFallecimiento() {
        return fallecimiento;
    }

    public String getId() {
        return id;
    }

    public String getLugarNacimiento() {
        return lugarNacimiento;
    }

    public String getNacimiento() {
        return nacimiento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setFallecimiento(String fallecimiento) {
        this.fallecimiento = fallecimiento;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLugarNacimiento(String lugarNacimiento) {
        this.lugarNacimiento = lugarNacimiento;
    }

    public void setNacimiento(String nacimiento) {
        this.nacimiento = nacimiento;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
