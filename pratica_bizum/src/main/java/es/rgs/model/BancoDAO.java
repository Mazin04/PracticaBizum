package es.rgs.model;

public interface BancoDAO {
    public boolean iniciarSesion(String usuario, String contraseña);
    public String getNombre(String usuario);   
}
