package DLL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.sql.Statement;
import javax.swing.JOptionPane;

import BLL.Producto;

public class DLLProducto {

	private static final Connection con = Conexion.getInstance().getConnection();

	public static boolean crear(Producto producto) {

		String sql = "INSERT INTO producto (nombre, codigo, categoria, unidad_medida, precio_actual) VALUES (?, ?, ?, ?, ?)";

		try (PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			stmt.setString(1, producto.getNombre());
			stmt.setString(2, producto.getCodigo());
			stmt.setString(3, producto.getCategoria());
			stmt.setString(4, producto.getUnidadMedida());
			stmt.setDouble(5, producto.getPrecioActual());

			if (stmt.executeUpdate() > 0) {
				ResultSet generatedKeys = stmt.getGeneratedKeys();
				if (generatedKeys.next()) {
					int idProducto = generatedKeys.getInt(1);
					String sqlInventario = "INSERT INTO inventario (id_producto, id_sucursal, cantidad_actual) VALUES (?, ?, ?)";
					try (PreparedStatement stmtInventario = con.prepareStatement(sqlInventario)) {
						stmtInventario.setInt(1, idProducto);
						stmtInventario.setInt(2, 1);
						stmtInventario.setInt(3, 100);
						stmtInventario.executeUpdate();
					}
				}
				return true;
			}
			return false;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al crear producto: " + e.getMessage());
			return false;
		}
	}

	public static LinkedList<Producto> obtenerTodos() {
		LinkedList<Producto> productos = new LinkedList<>();
		String sql = "SELECT p.*, i.cantidad_actual FROM producto p JOIN inventario i ON p.id_producto = i.id_producto WHERE i.id_sucursal = 1";

		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Producto p = new Producto(rs.getInt("id_producto"), rs.getString("nombre"), rs.getString("codigo"),
						rs.getString("categoria"), rs.getString("unidad_medida"), rs.getDouble("precio_actual"), null);

				p.setStock(rs.getInt("cantidad_actual"));
				productos.add(p);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al obtener la lista de productos: " + e.getMessage(),
					"Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		return productos;
	}

	public static boolean actualizarPrecio(int idProducto, double nuevoPrecio) {
		String sql = "UPDATE producto SET precio_actual = ? WHERE id_producto = ?";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setDouble(1, nuevoPrecio);
			stmt.setInt(2, idProducto);
			return stmt.executeUpdate() > 0;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al actualizar el precio: " + e.getMessage());
			return false;
		}
	}

	public static Producto buscarProductoPorNombre(String nombre) {
		String sql = "SELECT p.*, i.cantidad_actual FROM producto p "
				+ "LEFT JOIN inventario i ON p.id_producto = i.id_producto "
				+ "WHERE p.nombre LIKE ? AND i.id_sucursal = 1 LIMIT 1";// buscamos el producto cuyo nombre se parezca

		try (PreparedStatement stmt = con.prepareStatement(sql)) {

			stmt.setString(1, "%" + nombre + "%");
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				Producto p = new Producto(rs.getInt("id_producto"), rs.getString("nombre"), rs.getString("codigo"),
						rs.getString("categoria"), rs.getString("unidad_medida"), rs.getDouble("precio_actual"), null);
				p.setStock(rs.getInt("cantidad_actual"));
				return p;
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al buscar el producto: " + e.getMessage());
		}
		return null;
	}
}
