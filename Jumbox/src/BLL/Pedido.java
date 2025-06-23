package BLL;

import java.time.LocalDate;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import DLL.DLLPedido;

public class Pedido {

	private int idPedido;
	private Usuario usuario;
	private String estado;
	private LocalDate fecha;
	private Ticket ticket;
	private LinkedList<Producto> productos;
	private LinkedList<Integer> cantidades;
	private String productosString;

	public Pedido(int idPedido, Usuario usuario, String estado, LocalDate fecha, Ticket ticket,
			LinkedList<Producto> productos, LinkedList<Integer> cantidades) {
		super();
		this.idPedido = idPedido;
		this.usuario = usuario;
		this.estado = estado;
		this.fecha = fecha;
		this.ticket = ticket;
		this.productos = productos;
		this.cantidades = cantidades;
	}

	public Pedido(Usuario usuario, LinkedList<Producto> productos, LinkedList<Integer> cantidades) {
		super();
		this.usuario = usuario;
		this.productos = productos;
		this.cantidades = cantidades;
	}

	public int getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(int idPedido) {
		this.idPedido = idPedido;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	public LinkedList<Producto> getProductos() {
		return productos;
	}

	public void setProductos(LinkedList<Producto> productos) {
		this.productos = productos;
	}

	public LinkedList<Integer> getCantidades() {
		return cantidades;
	}

	public void setCantidades(LinkedList<Integer> cantidades) {
		this.cantidades = cantidades;
	}

	public String getProductosString() {
		return productosString;
	}

	public void setProductosString(String productosString) {
		this.productosString = productosString;
	}

	@Override
	public String toString() {

		return "Pedido ID: " + idPedido + " | Fecha: " + fecha + " | Estado: " + estado;
	}

	public static boolean crear(Pedido nuevoPedido) {
		if (nuevoPedido.getProductos() == null || nuevoPedido.getProductos().isEmpty()) {
			JOptionPane.showMessageDialog(null, "No se puede crear un pedido vacio");
			return false;
		}

		for (int i = 0; i < nuevoPedido.getProductos().size(); i++) {
			Producto p = nuevoPedido.getProductos().get(i);
			int cantidadPedida = nuevoPedido.getCantidades().get(i);
			int stockDisponible = Inventario.consultarStock(p.getIdProducto());

			if (cantidadPedida > stockDisponible) {
				JOptionPane.showMessageDialog(null, "No hay stock suficiente para el producto: " + p.getNombre()
						+ "\nStock disponible: " + stockDisponible + ", Pedido: " + cantidadPedida);
				return false;
			}
		}

		return DLLPedido.crearPedido(nuevoPedido);
	}

	public static LinkedList<Pedido> verMisPedidos(Usuario usuario) {
		return DLLPedido.obtenerPedidosPorUsuario(usuario.getIdUsuario());
	}

}
