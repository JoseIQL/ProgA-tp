package BLL;

import java.util.LinkedList;

import javax.swing.JOptionPane;

import DLL.DLLProducto;

public class Producto {

	private int idProducto;
	private String nombre;
	private String codigo;
	private String categoria;
	private String unidadMedida;
	private double precioActual;
	private LinkedList<HistorialPrecio> historia;
	private int stock;

	public Producto(int idProducto, String nombre, String codigo, String categoria, String unidadMedida,
			double precioActual, LinkedList<HistorialPrecio> historia, int stock) {
		super();
		this.idProducto = idProducto;
		this.nombre = nombre;
		this.codigo = codigo;
		this.categoria = categoria;
		this.unidadMedida = unidadMedida;
		this.precioActual = precioActual;
		this.historia = historia;
		this.stock = stock;
	}

	public Producto(int id, String n, String c, String cat, String um, double pa, LinkedList<HistorialPrecio> h) {
		this.idProducto = id;
		this.nombre = n;
		this.codigo = c;
		this.categoria = cat;
		this.unidadMedida = um;
		this.precioActual = pa;
		this.historia = h;
		this.stock = 0;
	}

	public int getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getUnidadMedida() {
		return unidadMedida;
	}

	public void setUnidadMedida(String unidadMedida) {
		this.unidadMedida = unidadMedida;
	}

	public double getPrecioActual() {
		return precioActual;
	}

	public void setPrecioActual(double precioActual) {
		this.precioActual = precioActual;
	}

	public LinkedList<HistorialPrecio> getHistoria() {
		return historia;
	}

	public void setHistoria(LinkedList<HistorialPrecio> historia) {
		this.historia = historia;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	@Override
	public String toString() {
		return "Producto [idProducto=" + idProducto + ", nombre=" + nombre + ", codigo=" + codigo + ", categoria="
				+ categoria + ", unidadMedida=" + unidadMedida + ", precioActual=" + precioActual + ", historia="
				+ historia + ", stock=" + stock + "]";
	}

	public double verPrecio() {
		return precioActual;
	}

	public static boolean crear(Producto p) {
		if (p.getNombre().trim().isEmpty() || p.getCodigo().trim().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Nombre y código son obligatorios");
			return false;
		}
		return DLLProducto.crear(p);
	}

	public static LinkedList<Producto> obtenerTodos() {
		return DLLProducto.obtenerTodos();
	}

	public static boolean actualizarPrecio(int idProducto, double nuevoPrecio) {

		if (nuevoPrecio < 0) {
			JOptionPane.showMessageDialog(null, "El precio no puede ser un valor negativo.", "Precio Inválido",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return DLLProducto.actualizarPrecio(idProducto, nuevoPrecio);
	}

	public static Producto buscarPorNombre(String nombre) {
		if (nombre == null || nombre.trim().isEmpty()) {
			JOptionPane.showMessageDialog(null, "El nombre del producto no puede estar vacío");
			return null;
		}
		return DLLProducto.buscarProductoPorNombre(nombre);
	}
}
