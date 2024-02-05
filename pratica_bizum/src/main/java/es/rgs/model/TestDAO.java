package es.rgs.model;

import java.math.BigInteger;

public class TestDAO implements BancoDAO {

    @Override
    public boolean iniciarSesion(String usuario, String contraseña) {
        
        throw new UnsupportedOperationException("Unimplemented method 'iniciarSesion'");
    }

    @Override
    public String getNombre(String usuario) {
        
        throw new UnsupportedOperationException("Unimplemented method 'getUsuario'");
    }

    @Override
    public boolean comprobarUsuario(String usuario) {
        
        throw new UnsupportedOperationException("Unimplemented method 'comprobarUsuario'");
    }

    @Override
    public void registrarUsuario(String usuario, String contraseña, String nombre, Integer telefono) {
        
        throw new UnsupportedOperationException("Unimplemented method 'registrarUsuario'");
    }

    @Override
    public String getTelefono(String usuario) {
        
        throw new UnsupportedOperationException("Unimplemented method 'getTelefono'");
    }

    @Override
    public void getCuentas(String usuario) {
        
        throw new UnsupportedOperationException("Unimplemented method 'getCuentas'");
    }

    @Override
    public void agregarCuenta(String username, BigInteger numCuenta, Double dinero) {
        
        throw new UnsupportedOperationException("Unimplemented method 'agregarCuenta'");
    }

    @Override
    public boolean comprobarCuenta(BigInteger numCuenta) {
        
        throw new UnsupportedOperationException("Unimplemented method 'comprobarCuenta'");
    }

    @Override
    public Double getDinero(String numeroCuenta) {
        
        throw new UnsupportedOperationException("Unimplemented method 'getDinero'");
    }

    @Override
    public void sacarDinero(String numCuenta, Double dinero) {
        
        throw new UnsupportedOperationException("Unimplemented method 'sacarDinero'");
    }

    @Override
    public void ingresarDinero(String numeroCuenta, Double dinero) {
        
        throw new UnsupportedOperationException("Unimplemented method 'ingresarDinero'");
    }

    @Override
    public boolean comprobarTelefono(String telefono) {
        
        throw new UnsupportedOperationException("Unimplemented method 'comprobarTelefono'");
    }

    @Override
    public String getCuentaTelefono(String telefono) {
        
        throw new UnsupportedOperationException("Unimplemented method 'getCuentaTelefono'");
    }

    @Override
    public boolean comprobarTelefonoExiste(String telefono) {
        
        throw new UnsupportedOperationException("Unimplemented method 'comprobarTelefonoExiste'");
    }

    @Override
    public boolean comprobarRegistroTelefono(String telefono) {
        
        throw new UnsupportedOperationException("Unimplemented method 'comprobarRegistroTelefono'");
    }

    @Override
    public void hacerBizum(String telefono, Double dinero) {
        
        throw new UnsupportedOperationException("Unimplemented method 'hacerBizum'");
    }

    @Override
    public void registrarBizumUsuario(String numeroCuenta) {
        
        throw new UnsupportedOperationException("Unimplemented method 'registrarBizumUsuario'");
    }
    
}
