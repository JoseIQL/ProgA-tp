package DLL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DLLInventario {

	private static final Connection con = Conexion.getInstance().getConnection();

	public static int consultarStock(int idProducto, int idSucursal) {
		String sql = "SELECT cantidad_actual FROM inventario WHERE id_producto = ? AND id_sucursal = ?";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setInt(1, idProducto);
			stmt.setInt(2, idSucursal);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt("cantidad_actual");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1; // Devuelve -1 si no se encuentra el producto en el inventario
	}

	public static boolean actualizarStock(int idProducto, int idSucursal, int cantidadVendida) {
		String sql = "UPDATE inventario SET cantidad_actual = cantidad_actual - ? WHERE id_producto = ? AND id_sucursal = ?";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setInt(1, cantidadVendida);
			stmt.setInt(2, idProducto);
			stmt.setInt(3, idSucursal);
			return stmt.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
}
