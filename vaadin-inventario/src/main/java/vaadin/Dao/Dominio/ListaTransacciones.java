package vaadin.Dao.Dominio;

import java.util.ArrayList;

public class ListaTransacciones {


	private static ListaTransacciones singletonTransaccion;
	private ArrayList<Transaccion> lista_transaccion = null;

	// El patrón singleton 
	
	public static ListaTransacciones getInstance() {
		if (singletonTransaccion == null)
			singletonTransaccion = new ListaTransacciones();

		return singletonTransaccion;
	}
	private ListaTransacciones() {
		lista_transaccion = new ArrayList<Transaccion>();
	}
	public ArrayList<Transaccion> getListaTransacciones() {
		return lista_transaccion;
	}
	public void setLista_transaccion(ArrayList<Transaccion> lista_transaccion) {
		this.lista_transaccion = lista_transaccion;
	}	

}
