package corp.poopapps.afinal;

public class Esculturas {

    private String Escultor, Foto, Nombre, Ubicacion, id;
    private long ano;

    public Esculturas(long ano, String Escultor, String Foto, String Nombre, String Ubicacion, String id) {
        this.ano = ano;
        this.Escultor = Escultor;
        this.Foto = Foto;
        this.Nombre = Nombre;
        this.Ubicacion = Ubicacion;
        this.id = id;
    }

    public Esculturas() {
    }

    public long getAno() { return ano; }

    public String getEscultor() {
        return Escultor;
    }

    public String getFoto() {
        return Foto;
    }

    public String getNombre() {
        return Nombre;
    }

    public String getUbicacion() {
        return Ubicacion;
    }

    public String getId() {
        return id;
    }

    public void setAno(long ano) {
        this.ano = ano;
    }

    public void setEscultor(String escultor) {
        this.Escultor = Escultor;
    }

    public void setFoto(String foto) {
        this.Foto = foto;
    }

    public void setNombre(String nombre) {
        this.Nombre = nombre;
    }

    public void setUbicacion(String ubicacion) {
        this.Ubicacion = ubicacion;
    }

    public void setId(String id) {
        this.id = id;
    }
}
