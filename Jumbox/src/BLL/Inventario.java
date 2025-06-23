package BLL;

import DLL.DLLInventario;

public class Inventario {

	private Producto producto;
	private Sucursal sucursal;
	private int cantidadActual;
	private int StockMinimo;

	public Inventario(Producto producto, Sucursal sucursal, int cantidadActual, int stockMinimo) {
		super();
		this.producto = producto;
		this.sucursal = sucursal;
		this.cantidadActual = cantidadActual;
		StockMinimo = stockMinimo;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public int getCantidadActual() {
		return cantidadActual;
	}

	public void setCantidadActual(int cantidadActual) {
		this.cantidadActual = cantidadActual;
	}

	public int getStockMinimo() {
		return StockMinimo;
	}

	public void setStockMinimo(int stockMinimo) {
		StockMinimo = stockMinimo;
	}

	@Override
	public String toString() {
		return "Inventario [producto=" + producto + ", sucursal=" + sucursal + ", cantidadActual=" + cantidadActual
				+ ", StockMinimo=" + StockMinimo + "]";
	}

	public void actualizarStock(int nuevaCantidad) {
		this.cantidadActual = nuevaCantidad;
	}

	public static int consultarStock(int idProducto) {
		return DLLInventario.consultarStock(idProducto, 1);
	}
}
