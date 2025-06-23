package GUI;

import BLL.Pedido;
import BLL.Usuario;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.LinkedList;

public class TablaMisPedidos extends JFrame {

	private DefaultTableModel model;

	public TablaMisPedidos(Usuario cliente) {
		setTitle("Historial de Pedidos de: " + cliente.getUsername());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(800, 400);
		setLocationRelativeTo(null);

		JPanel contentPane = new JPanel(new BorderLayout());
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(contentPane);

		model = new DefaultTableModel(new String[] { "ID Pedido", "Fecha", "Estado", "Productos en el Pedido" }, 0);
		JTable table = new JTable(model);

		table.getColumnModel().getColumn(3).setPreferredWidth(350);
		contentPane.add(new JScrollPane(table), BorderLayout.CENTER);

		cargarTabla(cliente);
	}

	private void cargarTabla(Usuario cliente) {
		model.setRowCount(0);
		LinkedList<Pedido> pedidos = Pedido.verMisPedidos(cliente);
		if (pedidos.isEmpty()) {
			model.addRow(new Object[] { "", "No tienes pedidos todavía", "", "" });
		} else {

			for (Pedido p : pedidos) {
				model.addRow(new Object[] { p.getIdPedido(), p.getFecha(), p.getEstado(), p.getProductosString() });
			}
		}
	}
}