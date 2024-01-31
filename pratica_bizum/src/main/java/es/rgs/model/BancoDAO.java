package es.rgs.model;

import java.math.BigInteger;

public interface BancoDAO {
    public boolean iniciarSesion(String usuario, String contraseña);
    public String getNombre(String usuario);
    public boolean comprobarUsuario(String usuario);
    public void registrarUsuario(String usuario, String contraseña, String nombre, Integer telefono);
    public String getTelefono(String usuario);
    public void getCuentas(String usuario);
    public void agregarCuenta(String username, BigInteger numCuenta, Double dinero);
    public boolean comprobarCuenta(BigInteger numCuenta);
    public Double getDinero(String numeroCuenta);
    public void sacarDinero(String numCuenta, Double dinero);   
}
