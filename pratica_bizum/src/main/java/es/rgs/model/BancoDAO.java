package es.rgs.model;

public interface BancoDAO {
    public boolean iniciarSesion(String usuario, String contraseña);
    public String getNombre(String usuario);
    public boolean comprobarUsuario(String usuario);
    public void registrarUsuario(String usuario, String contraseña, String nombre, Integer telefono);   
}
