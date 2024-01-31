package es.rgs.model.entities;

import java.math.BigInteger;

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
