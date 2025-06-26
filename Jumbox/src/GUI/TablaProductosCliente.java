package GUI;

import BLL.Producto;
import BLL.Usuario;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.LinkedList;

public class TablaProductosCliente extends JFrame {

	private JTable table;
	private DefaultTableModel model;
	private JTextField inpFiltro;
	private TableRowSorter<DefaultTableModel> sorter;
	private JComboBox<String> comboCriterio;
	private JButton btnFiltrar;
	private JButton btnLimpiar;

	public TablaProductosCliente(Usuario cliente) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(TablaProductosCliente.class.getResource("/img/super.png")));
		setTitle("Catálogo de Productos - Bienvenido, " + cliente.getUsername());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		setLocationRelativeTo(null);

		JPanel contentPane = new JPanel(new BorderLayout(10, 10));
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(contentPane);

		JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));

		topPanel.add(new JLabel("Buscar por:"));
		comboCriterio = new JComboBox<>(new String[] { "Nombre", "Categoría" });
		topPanel.add(comboCriterio);

		topPanel.add(new JLabel("Texto:"));
		inpFiltro = new JTextField(20);
		topPanel.add(inpFiltro);

		btnFiltrar = new JButton("Filtrar");
		topPanel.add(btnFiltrar);

		btnLimpiar = new JButton("Limpiar Filtro");
		topPanel.add(btnLimpiar);

		contentPane.add(topPanel, BorderLayout.NORTH);

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

		cargarTabla();

		btnFiltrar.addActionListener(e -> filtrarTabla());

		btnLimpiar.addActionListener(e -> {
			inpFiltro.setText("");
			sorter.setRowFilter(null);
		});

		inpFiltro.addActionListener(e -> filtrarTabla());
	}

	private void cargarTabla() {
		model.setRowCount(0);
		LinkedList<Producto> productos = Producto.obtenerTodos();
		for (Producto p : productos) {
			model.addRow(new Object[] { p.getIdProducto(), p.getNombre(), p.getCategoria(), p.getPrecioActual(),
					p.getStock() });
		}
	}

	private void filtrarTabla() {
		String texto = inpFiltro.getText();
		String criterio = (String) comboCriterio.getSelectedItem();

		if (texto.trim().length() == 0) {
			sorter.setRowFilter(null);
		} else {
			int columnIndex;
			switch (criterio) {
			case "Categoría":
				columnIndex = 2;
				break;
			case "Nombre":
			default:
				columnIndex = 1;
				break;
			}
			// para q busqueda no distinga entre mayusculas y minusculas
			sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto, columnIndex));
		}
	}
}