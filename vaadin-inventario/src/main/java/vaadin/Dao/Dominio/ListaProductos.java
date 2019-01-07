package vaadin.Dao.Dominio;

import java.util.ArrayList;

public class ListaProductos {

	private static ListaProductos singletonProducto;
	private ArrayList<Producto> lista_productos = null;

	// El patrón singleton 
	
	public static ListaProductos getInstance() {
		if (singletonProducto == null)
			singletonProducto = new ListaProductos();

		return singletonProducto;
	}
	private ListaProductos() {
		lista_productos = new ArrayList<Producto>();
	}
	public ArrayList<Producto> getLista_productos() {
		return lista_productos;
	}
	public void setLista_productos(ArrayList<Producto> lista_productos) {
		this.lista_productos = lista_productos;
	}	
	
}
