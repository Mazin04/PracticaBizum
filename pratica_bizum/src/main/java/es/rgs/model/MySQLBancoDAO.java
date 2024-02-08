package es.rgs.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import at.favre.lib.crypto.bcrypt.BCrypt;
import es.rgs.controller.BizumController;
import es.rgs.model.entities.Cuenta;
import es.rgs.model.entities.SingleUsuario;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * Clase que implementa la interfaz BancoDAO para la conexión y operaciones con una base de datos MySQL.
 * Utiliza JDBC para la conexión y manipulación de datos.
 *  
 * @author Rubén García
 * @version 1.0
 */
public class MySQLBancoDAO implements BancoDAO {
    
    private static Connection conn;

    /**
     * Método para establecer la conexión a la base de datos MySQL.
     * 
     * @return Conexión a la base de datos, o null si hay un error.
     */
    private static Connection conectar() {
        try {
            String url = "jdbc:mysql://sql.freedb.tech:3306/freedb_bizumRuben";
            String usuario = "freedb_rubengs";
            String contraseña = "DQMNrP2PG6!cN@t";
            Connection conn = DriverManager.getConnection(url, usuario, contraseña);
            return conn;
        } catch (SQLException e) {
            System.out.println("Hay un error en la conexión a la base de datos.");
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
        } catch (NullPointerException e){
            mostrarAviso("La base de datos está caída", "Error", AlertType.ERROR);
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

    /**
     * Método para encriptar una contraseña utilizando el algoritmo bcrypt.
     * 
     * @param contraseña Contraseña a ser encriptada.
     * @return Cadena encriptada utilizando bcrypt.
     */
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
    public boolean comprobarRegistroTelefono(String telefono) {
        conn = conectar();
        try {
            String sql = "SELECT TELEFONO FROM USUARIO WHERE TELEFONO = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, telefono);
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

    @Override
    public boolean comprobarTelefono(String telefono) {
        conn = conectar();
        try {
            String sql = "SELECT * FROM BIZUM WHERE TELEFONO = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, telefono);
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
    public String getCuentaTelefono(String telefono) {
        conn = conectar();
        try {
            String sql = "SELECT N_CUENTA FROM BIZUM WHERE TELEFONO = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, telefono);
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
    public boolean comprobarTelefonoExiste(String telefono) {
        conn = conectar();
        try {
            String sql = "SELECT * FROM BIZUM WHERE TELEFONO = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, telefono);
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
    public void hacerBizum(String telefono, Double dinero) {
        conn = conectar();
        try {
            String sql = "UPDATE CUENTAS SET DINERO = DINERO - ? WHERE N_CUENTA = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDouble(1, dinero);
            ps.setBigDecimal(2, new BigDecimal(getCuentaTelefono(SingleUsuario.getTelefono())));
            ps.executeUpdate();
            conn.close();
            conn = conectar();
            sql = "UPDATE CUENTAS SET DINERO = DINERO + ? WHERE N_CUENTA = ?";
            PreparedStatement ps2 = conn.prepareStatement(sql);
            ps2.setDouble(1, dinero);
            ps2.setBigDecimal(2, new BigDecimal(getCuentaTelefono(telefono)));
            ps2.executeUpdate();
        } catch (SQLException e) {
            e.getSQLState();
            e.getMessage();
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
    public void registrarBizumUsuario(String numeroCuenta) {
        conn = conectar();
        try {
            String sql = "INSERT INTO BIZUM VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, SingleUsuario.getUsuario());
            ps.setBigDecimal(2, new BigDecimal(numeroCuenta));
            ps.setString(3, SingleUsuario.getTelefono());
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

        /**
     * Método para mostrar un aviso utilizando un cuadro de diálogo.
     * 
     * @param msgTexto Mensaje a mostrar en el aviso.
     * @param titulo Título del cuadro de diálogo.
     * @param tipo Tipo de aviso (INFORMATION, ERROR, etc.).
     */
    public void mostrarAviso(String msgTexto, String titulo, AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setHeaderText(null);
        alerta.setTitle(titulo);
        alerta.setContentText(msgTexto);

        ImageView imageView = new ImageView(new Image(BizumController.LOGO));
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);

        alerta.setGraphic(imageView);

        Stage stage = (Stage) alerta.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(BizumController.LOGO));

        alerta.showAndWait();
    }
}
