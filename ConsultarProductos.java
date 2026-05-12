import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConsultarProductos {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/tienda_db";
        String user = "developer";
        String password = "tu_password";

        String sql = "SELECT product_id, nombre, precio, stock, sku FROM tienda.productos";

        try (
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)
        ) {
            while (rs.next()) {
                int id = rs.getInt("product_id");
                String nombre = rs.getString("nombre");
                double precio = rs.getDouble("precio");
                int stock = rs.getInt("stock");
                String sku = rs.getString("sku");

                System.out.println(id + " | " + nombre + " | " + precio + " | " + stock + " | " + sku);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}