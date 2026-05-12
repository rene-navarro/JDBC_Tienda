import java.sql.*;
import java.util.Scanner;

public class BuscarProductosPorCategoria {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/tienda_db";
        String user = "developer";
        String password = "tu_password";

        Scanner sc = new Scanner(System.in);
        System.out.print("Ingrese el nombre de la categoría: ");
        String categoria = sc.nextLine();

        String sql = """
            SELECT p.product_id, p.nombre, p.precio, p.stock
            FROM tienda.productos p
            JOIN tienda.categorias c ON p.categoria_id = c.categoria_id
            WHERE c.nombre = ?
        """;

        try (
            Connection conn = DriverManager.getConnection(url, user, password);
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, categoria);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                System.out.println(
                    rs.getInt("product_id") + " | " +
                    rs.getString("nombre") + " | " +
                    rs.getDouble("precio") + " | " +
                    rs.getInt("stock")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}