package GUI;

import BLL.Usuario;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class HomeEmpleado extends JFrame {

	private Usuario empleadoLogueado;

	public HomeEmpleado(Usuario empleado) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(HomeEmpleado.class.getResource("/img/super.png")));
		this.empleadoLogueado = empleado;

		setTitle("Jumbox - Home de Empleado: " + empleado.getUsername());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 450);
		setLocationRelativeTo(null);

		JPanel panel = new JPanel(new GridLayout(6, 1, 15, 15));
		panel.setBorder(new EmptyBorder(30, 60, 30, 60));

		JButton btnVerCatalogo = new JButton("Catálogo de Productos");
		JButton btnCrearPedido = new JButton("Nuevo Pedido");
		JButton btnVerMisPedidos = new JButton("Mis Pedidos");
		JButton btnGestionarProductos = new JButton("Gestionar Productos");
		JButton btnVerUsuarios = new JButton("Lista de Usuarios");
		JButton btnCerrarSesion = new JButton("Cerrar Sesión");

		panel.add(btnVerCatalogo);
		panel.add(btnCrearPedido);
		panel.add(btnVerMisPedidos);
		panel.add(btnGestionarProductos);
		panel.add(btnVerUsuarios);
		panel.add(btnCerrarSesion);

		getContentPane().add(panel);

		btnVerCatalogo.addActionListener(e -> {
			new TablaProductosCliente(empleadoLogueado).setVisible(true);
		});

		btnCrearPedido.addActionListener(e -> {
			new VistaCrearPedido(empleadoLogueado).setVisible(true);
		});

		btnVerMisPedidos.addActionListener(e -> {
			new TablaMisPedidos(empleadoLogueado).setVisible(true);
		});

		btnGestionarProductos.addActionListener(e -> {
			new VistaGestionProductos().setVisible(true);
		});

		btnVerUsuarios.addActionListener(e -> {
			new TablaUsuariosEmpleado(empleadoLogueado).setVisible(true);
		});

		btnCerrarSesion.addActionListener(e -> {
			this.dispose();
			PantallaDividida.main(null);
		});
	}
}