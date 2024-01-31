package es.rgs.model.entities;

import java.util.ArrayList;
import java.util.List;

public class SingleUsuario {
    private static String usuarioActual;
    private static String telefonoActual;
    private static List<Cuenta> cuentasActuales;
    
    private SingleUsuario() {}

    public static void setUsuario(String usuario) {
        usuarioActual = usuario;
    }

    public static String getUsuario() {
        return usuarioActual;
    }

    public static void setTelefono(String telefono) {
        telefonoActual = telefono;
    }

    public static String getTelefono() {
        return telefonoActual;
    }

    public static void addCuenta(Cuenta cuenta) {
        if (cuentasActuales == null) {
            cuentasActuales = new ArrayList<Cuenta>();
        }
        cuentasActuales.add(cuenta);
    }

    public static void setCuentas(List<Cuenta> cuentas) {
        cuentasActuales = cuentas;
    }

    public static List<Cuenta> getCuentas() {
        if (cuentasActuales == null) {
            cuentasActuales = new ArrayList<Cuenta>();
        }
        return cuentasActuales;
    }
}
