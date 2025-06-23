package GUI;

import BLL.Usuario;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.LinkedList;

public class TablaUsuariosEmpleado extends JFrame {

	private JTable table;
	private DefaultTableModel model;
	private JTextField inpFiltro;
	private TableRowSorter<DefaultTableModel> sorter;

	public TablaUsuariosEmpleado(Usuario empleado) {
		setTitle("Vista de Empleado - Logueado como: " + empleado.getUsername());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 500);
		setLocationRelativeTo(null);

		JPanel contentPane = new JPanel(new BorderLayout(10, 10));
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(contentPane);

		JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		topPanel.add(new JLabel("Filtrar por nombre:"));
		inpFiltro = new JTextField(20);
		topPanel.add(inpFiltro);
		contentPane.add(topPanel, BorderLayout.NORTH);

		model = new DefaultTableModel(new String[] { "ID", "Nombre", "Email", "Rol" }, 0) {
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

		inpFiltro.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				filtrarTabla();
			}
		});
	}

	private void cargarTabla() {
		model.setRowCount(0);
		LinkedList<Usuario> usuarios = Usuario.obtenerTodos();
		for (Usuario u : usuarios) {
			model.addRow(new Object[] { u.getIdUsuario(), u.getUsername(), u.getEmail(), u.getRol() });
		}
	}

	private void filtrarTabla() {
		String texto = inpFiltro.getText();
		sorter.setRowFilter(texto.trim().length() == 0 ? null : RowFilter.regexFilter("(?i)" + texto, 1));
	}
}