package GUI;

import BLL.Usuario;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class HomeGerente extends JFrame {

	private Usuario gerenteLogueado;

	public HomeGerente(Usuario gerente) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(HomeGerente.class.getResource("/img/super.png")));
		this.gerenteLogueado = gerente;

		setTitle("Jumbox - Home de Gerente: " + gerente.getUsername());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 450);
		setLocationRelativeTo(null);

		JPanel panel = new JPanel(new GridLayout(6, 1, 15, 15));
		panel.setBorder(new EmptyBorder(30, 60, 30, 60));

		JButton btnVerCatalogo = new JButton("Catálogo de Productos");
		JButton btnCrearPedido = new JButton("Nuevo Pedido");
		JButton btnVerMisPedidos = new JButton("Mis Pedidos");
		JButton btnGestionarProductos = new JButton("Gestionar Productos");
		JButton btnGestionarUsuarios = new JButton("Gestionar Usuarios");
		JButton btnCerrarSesion = new JButton("Cerrar Sesión");

		panel.add(btnVerCatalogo);
		panel.add(btnCrearPedido);
		panel.add(btnVerMisPedidos);
		panel.add(btnGestionarProductos);
		panel.add(btnGestionarUsuarios);
		panel.add(btnCerrarSesion);

		getContentPane().add(panel);

		btnVerCatalogo.addActionListener(e -> {
			new TablaProductosCliente(gerenteLogueado).setVisible(true);
		});

		btnCrearPedido.addActionListener(e -> {
			new VistaCrearPedido(gerenteLogueado).setVisible(true);
		});

		btnVerMisPedidos.addActionListener(e -> {
			new TablaMisPedidos(gerenteLogueado).setVisible(true);
		});

		btnGestionarProductos.addActionListener(e -> {
			new VistaGestionProductos().setVisible(true);
		});

		btnGestionarUsuarios.addActionListener(e -> {
			new TablaUsuarios(gerenteLogueado).setVisible(true);
		});

		btnCerrarSesion.addActionListener(e -> {
			this.dispose();
			PantallaDividida.main(null);
		});
	}
}