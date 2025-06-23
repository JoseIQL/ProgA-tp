package BLL;

import java.time.LocalDate;

public class HistorialPrecio {

	private Producto producto;
	private double precio;
	private LocalDate fechaDesde;

	public HistorialPrecio(Producto producto, double precio, LocalDate fechaDesde) {
		super();
		this.producto = producto;
		this.precio = precio;
		this.fechaDesde = fechaDesde;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public LocalDate getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(LocalDate fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	@Override
	public String toString() {
		return "HistorialPrecio [producto=" + producto + ", precio=" + precio + ", fechaDesde=" + fechaDesde
				+ ", getProducto()=" + getProducto() + ", getPrecio()=" + getPrecio() + ", getFechaDesde()="
				+ getFechaDesde() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

}
