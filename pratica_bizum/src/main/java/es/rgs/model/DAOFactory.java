package es.rgs.model;

import java.sql.SQLException;

public class DAOFactory {
    public static final int MODO_TEST = 0;
    public static final int MODO_MYSQL = 1;

        public static BancoDAO getDao(int modo) throws SQLException{
        switch(modo){
            case MODO_MYSQL: return new MySQLBancoDAO();
            default: return new TestDAO();
        }
    }
}
