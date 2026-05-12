import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertarCliente {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/tienda_db";
        String user = "developer";
        String password = "tu_password";

        String sql = "INSERT INTO tienda.clientes(nombre, email, telefono) VALUES (?, ?, ?)";

        try (
            Connection conn = DriverManager.getConnection(url, user, password);
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, "Carlos Ruiz");
            pstmt.setString(2, "carlos@email.com");
            pstmt.setString(3, "6671234567");

            int filas = pstmt.executeUpdate();
            System.out.println("Filas insertadas: " + filas);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}