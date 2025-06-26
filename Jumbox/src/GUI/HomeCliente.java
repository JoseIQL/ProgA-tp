package GUI;

import BLL.Usuario;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class HomeCliente extends JFrame {

	private Usuario clienteLogueado;

	public HomeCliente(Usuario cliente) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(HomeCliente.class.getResource("/img/super.png")));
		this.clienteLogueado = cliente;

		setTitle("Jumbox - Home de Cliente: " + cliente.getUsername());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(450, 350);
		setLocationRelativeTo(null);

		JPanel panel = new JPanel(new GridLayout(4, 1, 15, 15));
		panel.setBorder(new EmptyBorder(30, 60, 30, 60));

		JButton btnVerInventario = new JButton("Catálogo de Productos");
		JButton btnCrearPedido = new JButton("Nuevo Pedido");
		JButton btnVerMisPedidos = new JButton("Mis Pedidos");
		JButton btnCerrarSesion = new JButton("Cerrar Sesión");

		panel.add(btnVerInventario);
		panel.add(btnCrearPedido);
		panel.add(btnVerMisPedidos);
		panel.add(btnCerrarSesion);

		getContentPane().add(panel);

		btnVerInventario.addActionListener(e -> {
			new TablaProductosCliente(clienteLogueado).setVisible(true);
		});

		btnCrearPedido.addActionListener(e -> {
			new VistaCrearPedido(clienteLogueado).setVisible(true);
		});

		btnVerMisPedidos.addActionListener(e -> {
			new TablaMisPedidos(clienteLogueado).setVisible(true);
		});

		btnCerrarSesion.addActionListener(e -> {
			this.dispose();
			PantallaDividida.main(null);
		});
	}
}