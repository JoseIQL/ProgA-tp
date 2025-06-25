package GUI;

import BLL.Producto;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.LinkedList;

public class VistaGestionProductos extends JFrame {

	private JTable table;
	private DefaultTableModel model;
	private TableRowSorter<DefaultTableModel> sorter;

	public VistaGestionProductos() {
		setTitle("Gestión de Catálogo de Productos");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(800, 600);
		setLocationRelativeTo(null);

		JPanel contentPane = new JPanel(new BorderLayout(10, 10));
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(contentPane);

		// Tabla productos
		model = new DefaultTableModel(new String[] { "ID", "Producto", "Categoría", "Precio", "Stock" }, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table = new JTable(model);
		sorter = new TableRowSorter<>(model);
		table.setRowSorter(sorter);
		contentPane.add(new JScrollPane(table), BorderLayout.CENTER);

		JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		JButton btnAnadir = new JButton("Añadir Producto Nuevo");
		JButton btnModificarPrecio = new JButton("Modificar Precio de Producto");
		bottomPanel.add(btnAnadir);
		bottomPanel.add(btnModificarPrecio);
		contentPane.add(bottomPanel, BorderLayout.SOUTH);

		cargarTabla();

		btnAnadir.addActionListener(e -> {
			if (vgpAnadirProducto()) {
				cargarTabla();
			}
		});

		btnModificarPrecio.addActionListener(e -> {
			if (vgpModificarPrecio()) {
				cargarTabla();
			}
		});
	}

	private void cargarTabla() {
		model.setRowCount(0);
		LinkedList<Producto> productos = Producto.obtenerTodos();
		for (Producto p : productos) {
			model.addRow(new Object[] { p.getIdProducto(), p.getNombre(), p.getCategoria(), p.getPrecioActual(),
					p.getStock() });
		}
	}

	private boolean vgpAnadirProducto() {
		String nombre = JOptionPane.showInputDialog("Nombre del producto:");
		if (nombre == null)
			return false;
		String codigo = JOptionPane.showInputDialog("Código del producto:");
		if (codigo == null)
			return false;
		String categoria = JOptionPane.showInputDialog("Categoría del producto:");
		if (categoria == null)
			return false;
		String unidad = JOptionPane.showInputDialog("Unidad de medida:");
		if (unidad == null)
			return false;
		String precioStr = JOptionPane.showInputDialog("Precio del producto:");
		if (precioStr == null)
			return false;

		try {
			double precioValor = Double.parseDouble(precioStr);
			Producto p = new Producto(0, nombre, codigo, categoria, unidad, precioValor, null);
			if (Producto.crear(p)) {
				JOptionPane.showMessageDialog(this, "¡Producto añadido con éxito!");
				return true;
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "El precio debe ser un número válido");
		}
		return false;
	}

	private boolean vgpModificarPrecio() {
		int selectedRow = table.convertRowIndexToModel(table.getSelectedRow());
		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(this, "Seleccione un producto de la tabla primero");
			return false;
		}

		int idProducto = (int) model.getValueAt(selectedRow, 0);
		String nombreProducto = (String) model.getValueAt(selectedRow, 1);

		String precioStr = JOptionPane.showInputDialog(this, "Ingrese el nuevo precio para '" + nombreProducto + "':");
		if (precioStr == null)
			return false;

		try {
			double nuevoPrecio = Double.parseDouble(precioStr);
			if (Producto.actualizarPrecio(idProducto, nuevoPrecio)) {
				JOptionPane.showMessageDialog(this, "¡Precio actualizado con éxito!");
				return true;
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Ingrese un precio numérico válido");
		}
		return false;
	}
}