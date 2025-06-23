package BLL;

import java.util.LinkedList;

import javax.swing.JOptionPane;

import DLL.DLLUsuario;

public class Usuario {

	private int idUsuario;
	private String username;
	private String email;
	private String password;
	private String rol;
	private Sucursal sucursal;
	private LinkedList<Pedido> pedidos;

	public Usuario(int idUsuario, String username, String email, String password, String rol, Sucursal sucursal,
			LinkedList<Pedido> pedidos) {
		super();
		this.idUsuario = idUsuario;
		this.username = username;
		this.email = email;
		this.password = password;
		this.rol = rol;
		this.sucursal = sucursal;
		this.pedidos = pedidos;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public LinkedList<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(LinkedList<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	@Override
	public String toString() {
		return "Usuario [idUsuario=" + idUsuario + ", username=" + username + ", email=" + email + ", password="
				+ password + ", rol=" + rol + ", sucursal=" + sucursal + ", pedidos=" + pedidos + "]";
	}

	public static Usuario iniciarSesion(String email, String password) {
		if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Email y password son obligatorios");
			return null;
		}
		return DLLUsuario.iniciarSesion(email, password);
	}

	public static boolean registrarse(String usuario, String email, String password, String rol) {
		if (usuario == null || usuario.trim().isEmpty() || email == null || email.trim().isEmpty() || password == null
				|| password.trim().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
			return false;
		}
		if (!email.contains("@") || !email.contains(".")) {
			JOptionPane.showMessageDialog(null, "El formato del email no es válido");
			return false;
		}

		return DLLUsuario.registrarUsuario(usuario, email, password, rol);
	}

	public static LinkedList<Usuario> obtenerTodos() {
		return DLLUsuario.obtenerTodosLosUsuarios();
	}

	public static boolean eliminar(int idUsuario, Usuario gerenteActual) {

		if (idUsuario == gerenteActual.getIdUsuario()) {
			JOptionPane.showMessageDialog(null, "Un gerente no puede eliminarse a sí mismo.", "Operación no permitida",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}

		if (idUsuario == 1) {
			JOptionPane.showMessageDialog(null, "No se puede eliminar al administrador principal del sistema.",
					"Operación no permitida", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return DLLUsuario.eliminarUsuario(idUsuario);
	}

	public static boolean editar(Usuario usuario) {
		if (usuario.getUsername().trim().isEmpty() || usuario.getEmail().trim().isEmpty()) {
			JOptionPane.showMessageDialog(null, "El nombre y el email no pueden estar vacíos");
			return false;
		}
		return DLLUsuario.editarUsuario(usuario);
	}
}
