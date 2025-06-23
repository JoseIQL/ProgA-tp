package GUI;

import BLL.Inventario;
import BLL.Pedido;
import BLL.Producto;
import BLL.Usuario;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.LinkedList;

public class VistaCrearPedido extends JFrame {

	private Usuario clienteLogueado;
	private JTextField nombreProductoField;
	private JTextField cantidadField;
	private JTextArea carritoArea;
	private LinkedList<Producto> productosPedido = new LinkedList<>();
	private LinkedList<Integer> cantidadesPedido = new LinkedList<>();

	public VistaCrearPedido(Usuario cliente) {
		this.clienteLogueado = cliente;

		setTitle("Crear Nuevo Pedido");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(500, 400);
		setLocationRelativeTo(null);

		JPanel contentPane = new JPanel(new BorderLayout(10, 10));
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(contentPane);

		JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));
		inputPanel.add(new JLabel("Nombre del Producto a Buscar:"));
		nombreProductoField = new JTextField();
		inputPanel.add(nombreProductoField);
		inputPanel.add(new JLabel("Cantidad a añadir:"));
		cantidadField = new JTextField();
		inputPanel.add(cantidadField);
		JButton btnAnadir = new JButton("Verificar y Añadir al Carrito");
		inputPanel.add(new JLabel());
		inputPanel.add(btnAnadir);
		contentPane.add(inputPanel, BorderLayout.NORTH);

		carritoArea = new JTextArea("Productos en el carrito:\n");
		carritoArea.setEditable(false);
		contentPane.add(new JScrollPane(carritoArea), BorderLayout.CENTER);

		JButton btnFinalizar = new JButton("Finalizar y Crear Pedido");
		btnFinalizar.setFont(new Font("Tahoma", Font.BOLD, 14));
		contentPane.add(btnFinalizar, BorderLayout.SOUTH);

		btnAnadir.addActionListener(e -> {
			Producto p = Producto.buscarPorNombre(nombreProductoField.getText());
			if (p == null) {
				JOptionPane.showMessageDialog(this, "Producto no encontrado. Intente con otro nombre.");
				return;
			}
			try {
				int cantidad = Integer.parseInt(cantidadField.getText());
				if (cantidad <= 0)
					throw new NumberFormatException();

				if (cantidad > p.getStock()) {
					JOptionPane.showMessageDialog(this, "Stock insuficiente. Disponible: " + p.getStock());
					return;
				}

				productosPedido.add(p);
				cantidadesPedido.add(cantidad);
				actualizarVistaCarrito();
				nombreProductoField.setText("");
				cantidadField.setText("");

			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Ingrese una cantidad numérica válida");
			}
		});

		btnFinalizar.addActionListener(e -> {
			if (productosPedido.isEmpty()) {
				JOptionPane.showMessageDialog(this, "El carrito está vacío. Añada productos antes de finalizar");
				return;
			}
			Pedido nuevoPedido = new Pedido(cliente, productosPedido, cantidadesPedido);
			if (Pedido.crear(nuevoPedido)) {
				JOptionPane.showMessageDialog(this, "¡Pedido creado con éxito!");
				this.dispose();
			}
		});
	}

	private void actualizarVistaCarrito() {
		StringBuilder sb = new StringBuilder("Productos en el carrito:\n");
		for (int i = 0; i < productosPedido.size(); i++) {
			sb.append("- ").append(productosPedido.get(i).getNombre()).append(" (Cantidad: ")
					.append(cantidadesPedido.get(i)).append(")\n");
		}
		carritoArea.setText(sb.toString());
	}
}