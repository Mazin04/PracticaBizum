package es.rgs.model;

import java.sql.SQLException;

/**
 * Clase que actúa como una fábrica para proporcionar instancias de la interfaz BancoDAO.
 * Permite seleccionar el modo de implementación de la base de datos (MySQL o Test) mediante un código numérico.
 *
 * @author Rubén García
 * @version 1.0
 */
public class DAOFactory {

    // Constantes que representan los modos de implementación de la base de datos
    public static final int MODO_TEST = 0;
    public static final int MODO_MYSQL = 1;

    /**
     * Método para obtener una instancia de la interfaz BancoDAO según el modo especificado.
     *
     * @param modo Modo de implementación de la base de datos (MODO_TEST o MODO_MYSQL).
     * @return Instancia de la interfaz BancoDAO correspondiente al modo especificado.
     * @throws SQLException Si ocurre un error al crear la instancia.
     */
    public static BancoDAO getDao(int modo) throws SQLException{
        switch(modo){
            case MODO_MYSQL: return new MySQLBancoDAO();
            default: return new TestDAO();
        }
    }
}
