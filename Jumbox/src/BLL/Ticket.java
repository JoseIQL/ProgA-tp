package BLL;

import java.time.LocalDate;

public class Ticket {

	private int idTicket;
	private Pedido pedido;
	private LocalDate fecha;
	private double total;

	public Ticket(int idTicket, Pedido pedido, LocalDate fecha, double total) {
		super();
		this.idTicket = idTicket;
		this.pedido = pedido;
		this.fecha = fecha;
		this.total = total;
	}

	public int getIdTicket() {
		return idTicket;
	}

	public void setIdTicket(int idTicket) {
		this.idTicket = idTicket;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "Ticket [idTicket=" + idTicket + ", pedido=" + pedido + ", fecha=" + fecha + ", total=" + total
				+ ", getIdTicket()=" + getIdTicket() + ", getPedido()=" + getPedido() + ", getFecha()=" + getFecha()
				+ ", getTotal()=" + getTotal() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}

	public void imprimirTicket() {

	}
}
