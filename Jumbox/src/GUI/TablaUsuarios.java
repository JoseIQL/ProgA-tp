package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import BLL.Usuario;
import java.awt.*;
import java.util.LinkedList;

public class TablaUsuarios extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel model;
	private Usuario gerenteLogueado;
	private JTextField inpFiltro;
	private TableRowSorter<DefaultTableModel> sorter;

	public TablaUsuarios(Usuario gerente) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(TablaUsuarios.class.getResource("/img/super.png")));
		this.gerenteLogueado = gerente;
		setTitle("Gestión de Usuarios - Logueado como: " + gerente.getUsername());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 500);
		setLocationRelativeTo(null);

		contentPane = new JPanel(new BorderLayout(10, 10));
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

		JPanel bottomPanel = new JPanel();
		JButton btnAgregar = new JButton("Agregar");
		JButton btnEditar = new JButton("Editar");
		JButton btnEliminar = new JButton("Eliminar");
		bottomPanel.add(btnAgregar);
		bottomPanel.add(btnEditar);
		bottomPanel.add(btnEliminar);
		contentPane.add(bottomPanel, BorderLayout.SOUTH);

		cargarTabla();

		inpFiltro.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				filtrarTabla();
			}
		});

		btnAgregar.addActionListener(e -> {
			if (tuAgregarUsuario()) {
				cargarTabla();
			}
		});

		btnEditar.addActionListener(e -> {
			if (tuEditarUsuario()) {
				cargarTabla();
			}
		});

		btnEliminar.addActionListener(e -> {
			if (tuEliminarUsuario()) {
				cargarTabla();
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

	private boolean tuAgregarUsuario() {
		JTextField nombreField = new JTextField();
		JTextField emailField = new JTextField();
		JPasswordField passField = new JPasswordField();
		JComboBox<String> rolCombo = new JComboBox<>(new String[] { "Empleado", "Gerente", "Cliente" });

		JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
		panel.add(new JLabel("Nombre:"));
		panel.add(nombreField);
		panel.add(new JLabel("Email:"));
		panel.add(emailField);
		panel.add(new JLabel("Contraseña:"));
		panel.add(passField);
		panel.add(new JLabel("Rol:"));
		panel.add(rolCombo);

		int result = JOptionPane.showConfirmDialog(this, panel, "Agregar Nuevo Usuario", JOptionPane.OK_CANCEL_OPTION);

		if (result == JOptionPane.OK_OPTION) {
			String nombre = nombreField.getText();
			String email = emailField.getText();
			String password = new String(passField.getPassword());
			String rol = (String) rolCombo.getSelectedItem();

			return Usuario.registrarse(nombre, email, password, rol);
		}
		return false;
	}

	private boolean tuEditarUsuario() {
		int selectedRow = table.convertRowIndexToModel(table.getSelectedRow());
		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(this, "Seleccione un usuario de la tabla para editar");
			return false;
		}

		int id = (int) model.getValueAt(selectedRow, 0);
		String nombre = (String) model.getValueAt(selectedRow, 1);
		String email = (String) model.getValueAt(selectedRow, 2);
		String rol = (String) model.getValueAt(selectedRow, 3);

		JTextField nombreField = new JTextField(nombre);
		JTextField emailField = new JTextField(email);
		JPasswordField passField = new JPasswordField();
		JComboBox<String> rolCombo = new JComboBox<>(new String[] { "Empleado", "Gerente" });
		rolCombo.setSelectedItem(rol);

		Object[] fields = { "Nombre:", nombreField, "Email:", emailField, "Nueva Contraseña:", passField, "Rol:",
				rolCombo };
		if (JOptionPane.showConfirmDialog(this, fields, "Editar Usuario",
				JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
			Usuario userEditado = new Usuario(id, nombreField.getText(), emailField.getText(),
					new String(passField.getPassword()), (String) rolCombo.getSelectedItem(), null, null);
			return Usuario.editar(userEditado);
		}
		return false;
	}

	private boolean tuEliminarUsuario() {
		int selectedRow = table.convertRowIndexToModel(table.getSelectedRow());
		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(this, "Seleccione un usuario de la tabla para eliminar");
			return false;
		}
		int id = (int) model.getValueAt(selectedRow, 0);
		int confirm = JOptionPane.showConfirmDialog(this, "¿Seguro que desea eliminar al usuario con ID " + id + "?",
				"Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
		return confirm == JOptionPane.YES_OPTION && Usuario.eliminar(id, gerenteLogueado);
	}
}