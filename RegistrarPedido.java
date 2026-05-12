import java.sql.*;

public class RegistrarPedido {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/tienda_db";
        String user = "developer";
        String password = "tu_password";

        String sqlPedido = "INSERT INTO tienda.pedidos(cliente_id, estado) VALUES (?, ?) RETURNING pedido_id";
        String sqlDetalle = "INSERT INTO tienda.detalles_pedido(pedido_id, producto_id, cantidad, precio_unit) VALUES (?, ?, ?, ?)";
        String sqlPago = "INSERT INTO tienda.pagos(pedido_id, monto, metodo, status) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            conn.setAutoCommit(false);

            int pedidoId = 0;

            try (
                PreparedStatement pstmtPedido = conn.prepareStatement(sqlPedido);
                PreparedStatement pstmtDetalle = conn.prepareStatement(sqlDetalle);
                PreparedStatement pstmtPago = conn.prepareStatement(sqlPago)
            ) {
                pstmtPedido.setInt(1, 1);
                pstmtPedido.setString(2, "CREADO");

                ResultSet rs = pstmtPedido.executeQuery();
                if (rs.next()) {
                    pedidoId = rs.getInt("pedido_id");
                }

                pstmtDetalle.setInt(1, pedidoId);
                pstmtDetalle.setInt(2, 1);
                pstmtDetalle.setInt(3, 2);
                pstmtDetalle.setDouble(4, 500.00);
                pstmtDetalle.executeUpdate();

                pstmtPago.setInt(1, pedidoId);
                pstmtPago.setDouble(2, 1000.00);
                pstmtPago.setString(3, "TARJETA");
                pstmtPago.setString(4, "ACEPTADO");
                pstmtPago.executeUpdate();

                conn.commit();
                System.out.println("Pedido registrado correctamente.");

            } catch (SQLException e) {
                conn.rollback();
                System.out.println("Error. Se realizó rollback.");
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}