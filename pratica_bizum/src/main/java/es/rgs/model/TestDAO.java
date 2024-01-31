package es.rgs.model;

import java.math.BigInteger;

public class TestDAO implements BancoDAO {

    @Override
    public boolean iniciarSesion(String usuario, String contraseña) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'iniciarSesion'");
    }

    @Override
    public String getNombre(String usuario) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUsuario'");
    }

    @Override
    public boolean comprobarUsuario(String usuario) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'comprobarUsuario'");
    }

    @Override
    public void registrarUsuario(String usuario, String contraseña, String nombre, Integer telefono) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'registrarUsuario'");
    }

    @Override
    public String getTelefono(String usuario) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTelefono'");
    }

    @Override
    public void getCuentas(String usuario) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCuentas'");
    }

    @Override
    public void agregarCuenta(String username, BigInteger numCuenta, Double dinero) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'agregarCuenta'");
    }

    @Override
    public boolean comprobarCuenta(BigInteger numCuenta) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'comprobarCuenta'");
    }

    @Override
    public Double getDinero(String numeroCuenta) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDinero'");
    }

    @Override
    public void sacarDinero(String numCuenta, Double dinero) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sacarDinero'");
    }
    
}
