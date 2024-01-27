package es.rgs.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import at.favre.lib.crypto.bcrypt.BCrypt;

public class MySQLBancoDAO implements BancoDAO {
    private static Connection conn;

    private static Connection conectar() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://sql8.freemysqlhosting.net:3306/sql8679344";
            String usuario = "sql8679344";
            String contraseña = "yGgzwittje";
            Connection conn = DriverManager.getConnection(url, usuario, contraseña);
            return conn;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean iniciarSesion(String usuario, String contraseña) {
        conn = conectar();
        try {
            String sql = "SELECT PASSWORD FROM USUARIO WHERE USERNAME = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, usuario);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String hash = rs.getString(1);
                BCrypt.Result result = BCrypt.verifyer().verify(contraseña.toCharArray(), hash);
                return result.verified;
            }
        } catch (SQLException e) {
            e.getSQLState();
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.getSQLState();
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public String getNombre(String usuario) {
        conn = conectar();
        try {
            String sql = "SELECT NOMBRE FROM USUARIO WHERE USERNAME = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, usuario);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
            e.getSQLState();
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.getSQLState();
                e.printStackTrace();
            }
        }
        return null;
    }

    public String encriptar(String contraseña) {
        String bcryptHashString = BCrypt.withDefaults().hashToString(12, contraseña.toCharArray());
        return bcryptHashString;
    }
}
