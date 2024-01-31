package es.rgs.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import at.favre.lib.crypto.bcrypt.BCrypt;
import es.rgs.model.entities.Cuenta;
import es.rgs.model.entities.SingleUsuario;

public class MySQLBancoDAO implements BancoDAO {
    private static Connection conn;

    private static Connection conectar() {
        try {
            String url = "jdbc:mysql://sql8.freemysqlhosting.net:3306/sql8679344";
            String usuario = "sql8679344";
            String contraseña = "yGgzwittje";
            Connection conn = DriverManager.getConnection(url, usuario, contraseña);
            return conn;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public boolean iniciarSesion(String usuario, String contraseña) {
        conn = conectar();
        try {
            String sql = "SELECT CONTRASENA FROM USUARIO WHERE USERNAME = ?";
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

    @Override
    public boolean comprobarUsuario(String usuario) {
        conn = conectar();
        try {
            String sql = "SELECT USERNAME FROM USUARIO WHERE USERNAME = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, usuario);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            } else {
                return false;
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
    public void registrarUsuario(String usuario, String contraseña, String nombre, Integer telefono) {
        conn = conectar();
        try {
            String sql = "INSERT INTO USUARIO VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, usuario);
            ps.setString(2, nombre);
            ps.setInt(3, telefono);
            ps.setString(4, encriptar(contraseña));
            ps.executeUpdate();
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
    }

    @Override
    public String getTelefono(String usuario) {
        conn = conectar();
        try {
            String sql = "SELECT TELEFONO FROM USUARIO WHERE USERNAME = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, usuario);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            } else {
                return null;
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

    @Override
    public void getCuentas(String usuario) {
        conn = conectar();
        try {
            String sql = "SELECT N_CUENTA, DINERO FROM CUENTAS WHERE USERNAME = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, usuario);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SingleUsuario.addCuenta(new Cuenta(BigInteger.valueOf(rs.getLong(1)), rs.getDouble(2)));
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
    }

    @Override
    public void agregarCuenta(String username, BigInteger numCuenta, Double dinero) {
        conn = conectar();
        try {
            String sql = "INSERT INTO CUENTAS VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setBigDecimal(1, new BigDecimal(numCuenta));
            ps.setString(2, username);
            ps.setDouble(3, dinero);
            ps.executeUpdate();
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
    }

    @Override
    public boolean comprobarCuenta(BigInteger numCuenta) {
        conn = conectar();
        try {
            String sql = "SELECT N_CUENTA FROM CUENTAS WHERE N_CUENTA = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setBigDecimal(1, new BigDecimal(numCuenta));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            } else {
                return false;
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
    public Double getDinero(String numeroCuenta) {
        conn = conectar();
        try {
            String sql = "SELECT DINERO FROM CUENTAS WHERE N_CUENTA = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setBigDecimal(1, new BigDecimal(numeroCuenta));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            } else {
                return null;
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

    @Override
    public void sacarDinero(String numCuenta, Double dinero) {
        conn = conectar();
        try {
            String sql = "UPDATE CUENTAS SET DINERO = DINERO - ? WHERE N_CUENTA = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDouble(1, dinero);
            ps.setBigDecimal(2, new BigDecimal(numCuenta));
            ps.executeUpdate();
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
    }

    @Override
    public void ingresarDinero(String numeroCuenta, Double dinero) {
        conn = conectar();
        try {
            String sql = "UPDATE CUENTAS SET DINERO = DINERO + ? WHERE N_CUENTA = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDouble(1, dinero);
            ps.setBigDecimal(2, new BigDecimal(numeroCuenta));
            ps.executeUpdate();
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
    }
}
