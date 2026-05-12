import java.sql.*;

public class ConsultarPedidosClientes {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/tienda_db";
        String user = "developer";
        String password = "tu_password";

        String sql = """
            SELECT p.pedido_id, c.nombre, p.fecha, p.estado
            FROM tienda.pedidos p
            JOIN tienda.clientes c ON p.cliente_id = c.cliente_id
            ORDER BY p.pedido_id
        """;

        try (
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)
        ) {
            while (rs.next()) {
                System.out.println(
                    rs.getInt("pedido_id") + " | " +
                    rs.getString("nombre") + " | " +
                    rs.getTimestamp("fecha") + " | " +
                    rs.getString("estado")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}