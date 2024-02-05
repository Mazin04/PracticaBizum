package es.rgs.model;

import java.math.BigInteger;

/**
 * Interfaz que define las operaciones relacionadas con la gestión de datos bancarios en el sistema Bizum.
 * Proporciona métodos para realizar acciones como iniciar sesión, registrar usuarios, gestionar cuentas, etc.
 *
 * @author Tu Nombre
 * @version 1.0
 */
public interface BancoDAO {

    /**
     * Método para iniciar sesión de un usuario en el sistema.
     *
     * @param usuario Nombre de usuario.
     * @param contraseña Contraseña del usuario.
     * @return true si la sesión se inicia correctamente, false en caso contrario.
     */
    public boolean iniciarSesion(String usuario, String contraseña);

    /**
     * Método para obtener el nombre asociado a un usuario.
     *
     * @param usuario Nombre de usuario.
     * @return Nombre asociado al usuario.
     */
    public String getNombre(String usuario);

    /**
     * Método para comprobar la existencia de un usuario en la base de datos.
     *
     * @param usuario Nombre de usuario a comprobar.
     * @return true si el usuario existe, false en caso contrario.
     */
    public boolean comprobarUsuario(String usuario);

    /**
     * Método para registrar un nuevo usuario en la base de datos.
     *
     * @param usuario Nombre de usuario.
     * @param contraseña Contraseña del usuario.
     * @param nombre Nombre real del usuario.
     * @param telefono Número de teléfono del usuario.
     */
    public void registrarUsuario(String usuario, String contraseña, String nombre, Integer telefono);

    /**
     * Método para obtener el número de teléfono asociado a un usuario.
     *
     * @param usuario Nombre de usuario.
     * @return Número de teléfono asociado al usuario.
     */
    public String getTelefono(String usuario);

    /**
     * Método para obtener las cuentas asociadas a un usuario.
     *
     * @param usuario Nombre de usuario.
     */
    public void getCuentas(String usuario);

    /**
     * Método para agregar una nueva cuenta a un usuario.
     *
     * @param username Nombre de usuario.
     * @param numCuenta Número de cuenta a agregar.
     * @param dinero Saldo inicial de la cuenta.
     */
    public void agregarCuenta(String username, BigInteger numCuenta, Double dinero);

    /**
     * Método para comprobar la existencia de una cuenta en la base de datos.
     *
     * @param numCuenta Número de cuenta a comprobar.
     * @return true si la cuenta existe, false en caso contrario.
     */
    public boolean comprobarCuenta(BigInteger numCuenta);

    /**
     * Método para obtener el saldo de una cuenta.
     *
     * @param numeroCuenta Número de cuenta.
     * @return Saldo actual de la cuenta.
     */
    public Double getDinero(String numeroCuenta);

    /**
     * Método para ingresar dinero en una cuenta.
     *
     * @param numCuenta Número de cuenta.
     * @param dinero Cantidad a retirar.
     */
    public void sacarDinero(String numCuenta, Double dinero);

    /**
     * Método para depositar dinero en una cuenta.
     *
     * @param numeroCuenta Número de cuenta.
     * @param dinero Cantidad a depositar.
     */
    public void ingresarDinero(String numeroCuenta, Double dinero);

    /**
     * Método para comprobar la existencia de un número de teléfono en la base de datos.
     *
     * @param telefono Número de teléfono a comprobar.
     * @return true si el teléfono existe, false en caso contrario.
     */
    public boolean comprobarTelefono(String telefono);

    /**
     * Método para obtener la cuenta asociada a un número de teléfono.
     *
     * @param telefono Número de teléfono.
     * @return Número de cuenta asociado al teléfono.
     */
    public String getCuentaTelefono(String telefono);

    /**
     * Método para comprobar la existencia de un número de teléfono en la base de datos.
     *
     * @param telefono Número de teléfono a comprobar.
     * @return true si el teléfono existe, false en caso contrario.
     */
    public boolean comprobarTelefonoExiste(String telefono);

    /**
     * Método para comprobar el registro de un número de teléfono en la base de datos.
     *
     * @param telefono Número de teléfono a comprobar.
     * @return true si el teléfono está registrado, false en caso contrario.
     */
    public boolean comprobarRegistroTelefono(String telefono);

    /**
     * Método para realizar una transferencia Bizum a otro usuario.
     *
     * @param telefono Número de teléfono del destinatario.
     * @param dinero Cantidad a transferir.
     */
    public void hacerBizum(String telefono, Double dinero);

    /**
     * Método para registrar a un usuario en el sistema Bizum.
     *
     * @param numeroCuenta Número de cuenta a registrar para Bizum.
     */
    public void registrarBizumUsuario(String numeroCuenta);
}
