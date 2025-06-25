package DLL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import BLL.Usuario;
import repository.Encriptador;

public class DLLUsuario {

	private static final Connection con = Conexion.getInstance().getConnection();
	private static final Encriptador encriptador = new Encriptador() {
	};

	public static Usuario iniciarSesion(String email, String password) {
		String sql = "SELECT * FROM usuario WHERE email = ? AND password = ?";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, email);
			stmt.setString(2, encriptador.encriptar(password));
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return new Usuario(rs.getInt("id_usuario"), rs.getString("nombre"), rs.getString("email"),
						rs.getString("password"), rs.getString("rol"), null, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean registrarUsuario(String username, String email, String passwordSinEncriptar, String rol) {
		String sql = "INSERT INTO usuario (nombre, email, password, rol) VALUES (?, ?, ?, ?)";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, username);
			stmt.setString(2, email);
			stmt.setString(3, encriptador.encriptar(passwordSinEncriptar));
			stmt.setString(4, rol);
			return stmt.executeUpdate() > 0;
		} catch (SQLException e) {
			if (e.getErrorCode() == 1062) {
				JOptionPane.showMessageDialog(null, "El email ingresado ya está registrado.");
			} else {
				JOptionPane.showMessageDialog(null, "Error al registrar el usuario en la base de datos.");
			}
			return false;
		}
	}

	public static LinkedList<Usuario> obtenerTodosLosUsuarios() {
		LinkedList<Usuario> usuarios = new LinkedList<>();
		String sql = "SELECT * FROM usuario";

		try (PreparedStatement stmt = con.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				usuarios.add(new Usuario(rs.getInt("id_usuario"), rs.getString("nombre"), rs.getString("email"),
						rs.getString("password"), rs.getString("rol"), null, null));
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al obtener la lista de usuarios: " + e.getMessage());
		}
		return usuarios;
	}

	public static boolean eliminarUsuario(int idUsuario) {
		String sql = "DELETE FROM usuario WHERE id_usuario = ?";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setInt(1, idUsuario);
			return stmt.executeUpdate() > 0;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al eliminar el usuario: " + e.getMessage());
			return false;
		}
	}

	public static boolean editarUsuario(Usuario usuario) {

		String sql = usuario.getPassword().isEmpty()
				? "UPDATE usuario SET nombre = ?, email = ?, rol = ? WHERE id_usuario = ?"
				: "UPDATE usuario SET nombre = ?, email = ?, rol = ?, password = ? WHERE id_usuario = ?";

		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, usuario.getUsername());
			stmt.setString(2, usuario.getEmail());
			stmt.setString(3, usuario.getRol());

			if (!usuario.getPassword().isEmpty()) {
				stmt.setString(4, encriptador.encriptar(usuario.getPassword()));
				stmt.setInt(5, usuario.getIdUsuario());
			} else {
				stmt.setInt(4, usuario.getIdUsuario());
			}

			return stmt.executeUpdate() > 0;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al editar el usuario: " + e.getMessage());
			return false;
		}
	}
}
