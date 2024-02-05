package es.rgs.model.entities;

import java.math.BigInteger;

/**
 * Clase que representa una cuenta en el sistema Bizum.
 * Contiene información sobre el número de cuenta y el saldo disponible.
 *
 * @author Rubén García
 * @version 1.0
 */
public class Cuenta {
    private BigInteger numeroCuenta;
    private double dinero;

    public Cuenta(BigInteger numeroCuenta, double dinero) {
        this.numeroCuenta = numeroCuenta;
        this.dinero = dinero;
    }

    public BigInteger getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(BigInteger numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public double getDinero() {
        return dinero;
    }

    public void setDinero(double dinero) {
        this.dinero = dinero;
    }
}
