package BLL;

import java.util.LinkedList;

public class Sucursal {

	private int idSucursal;
	private String nombre;
	private String direccion;
	private LinkedList<Usuario> usuarios = new LinkedList<Usuario>();
	private LinkedList<Producto> inventario = new LinkedList<Producto>();

	public Sucursal(int idSucursal, String nombre, String direccion, LinkedList<Usuario> usuarios,
			LinkedList<Producto> inventario) {
		super();
		this.idSucursal = idSucursal;
		this.nombre = nombre;
		this.direccion = direccion;
		this.usuarios = usuarios;
		this.inventario = inventario;
	}

	public int getIdSucursal() {
		return idSucursal;
	}

	public void setIdSucursal(int idSucursal) {
		this.idSucursal = idSucursal;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public LinkedList<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(LinkedList<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public LinkedList<Producto> getInventario() {
		return inventario;
	}

	public void setInventario(LinkedList<Producto> inventario) {
		this.inventario = inventario;
	}

	@Override
	public String toString() {
		return "Sucursal [idSucursal=" + idSucursal + ", nombre=" + nombre + ", direccion=" + direccion + ", usuarios="
				+ usuarios + ", getIdSucursal()=" + getIdSucursal() + ", getNombre()=" + getNombre()
				+ ", getDireccion()=" + getDireccion() + ", getUsuarios()=" + getUsuarios() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

	public void mostrarDatos() {

	}
}
