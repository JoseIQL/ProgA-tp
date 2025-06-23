package DLL;

import java.security.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import BLL.Pedido;
import BLL.Producto;

public class DLLPedido {
	private static final Connection con = Conexion.getInstance().getConnection();

	public static boolean crearPedido(Pedido pedido) {

		String sqlPedido = "INSERT INTO pedido (id_usuario, fecha, estado) VALUES (?, ?, ?)";
		String sqlPedidoProducto = "INSERT INTO pedidoproducto (id_pedido, id_producto, cantidad, precio_unitario) VALUES (?, ?, ?, ?)";

		ResultSet generatedKeys = null;

		try {
			con.setAutoCommit(false);
			PreparedStatement stmtPedido = con.prepareStatement(sqlPedido, Statement.RETURN_GENERATED_KEYS);
			stmtPedido.setInt(1, pedido.getUsuario().getIdUsuario());
			LocalDateTime ahora = LocalDateTime.now();
			java.util.Date fechaUtil = java.util.Date.from(ahora.atZone(ZoneId.systemDefault()).toInstant()); // convertir
																												// a un
																												// objeto
																												// Date
																												// estandar
																												// de
																												// Java
			java.sql.Timestamp fechaSql = new java.sql.Timestamp(fechaUtil.getTime());// creamos un Timestamp de SQL a
																						// partir del objeto Date
			stmtPedido.setTimestamp(2, fechaSql);
			stmtPedido.setString(3, "Completado");
			stmtPedido.executeUpdate();

			generatedKeys = stmtPedido.getGeneratedKeys();
			if (!generatedKeys.next()) {
				throw new SQLException("No se pudo obtener el ID del pedido.");
			}
			int idPedido = generatedKeys.getInt(1);

			PreparedStatement stmtPedidoProducto = con.prepareStatement(sqlPedidoProducto);
			for (int i = 0; i < pedido.getProductos().size(); i++) {
				Producto p = pedido.getProductos().get(i);
				int cantidad = pedido.getCantidades().get(i);
				stmtPedidoProducto.setInt(1, idPedido);
				stmtPedidoProducto.setInt(2, p.getIdProducto());
				stmtPedidoProducto.setInt(3, cantidad);
				stmtPedidoProducto.setDouble(4, 0.0);
				stmtPedidoProducto.addBatch(); // Agrupamos las inserciones

				boolean stockActualizado = DLLInventario.actualizarStock(p.getIdProducto(), 1, cantidad);

				if (!stockActualizado) {
					throw new SQLException("No se pudo actualizar el stock para el producto ID: " + p.getIdProducto());
				}
			}

			stmtPedidoProducto.executeBatch();

			con.commit();// confirmamos
			return true;

		} catch (SQLException e) {
			try {
				con.rollback();// descartamos
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			JOptionPane.showMessageDialog(null, "Error al crear el pedido: " + e.getMessage());
			e.printStackTrace();
			return false;
		} finally {

			try {
				con.setAutoCommit(true);// activar el autocommit para el resto de operaciones
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public static LinkedList<Pedido> obtenerPedidosPorUsuario(int idUsuario) {
		LinkedList<Pedido> pedidos = new LinkedList<>();
		String sql = "SELECT * FROM pedido WHERE id_usuario = ? ORDER BY fecha DESC";

		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setInt(1, idUsuario);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				int idPedido = rs.getInt("id_pedido");

				Pedido pedido = new Pedido(idPedido, null, rs.getString("estado"),
						rs.getTimestamp("fecha").toLocalDateTime().toLocalDate(), null, null, null);

				String sqlDetalles = "SELECT pr.nombre, pp.cantidad FROM pedidoproducto pp "
						+ "JOIN producto pr ON pp.id_producto = pr.id_producto " + "WHERE pp.id_pedido = ?";

				StringBuilder productosStr = new StringBuilder();
				try (PreparedStatement stmtDetalles = con.prepareStatement(sqlDetalles)) {
					stmtDetalles.setInt(1, idPedido);
					ResultSet rsDetalles = stmtDetalles.executeQuery();
					while (rsDetalles.next()) {
						if (productosStr.length() > 0)
							productosStr.append(", ");
						productosStr.append(rsDetalles.getString("nombre")).append(" (x")
								.append(rsDetalles.getInt("cantidad")).append(")");
					}
				}
				pedido.setProductosString(productosStr.toString());
				pedidos.add(pedido);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al obtener los pedidos: " + e.getMessage());
		}
		return pedidos;
	}

}
