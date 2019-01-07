package vaadin.Dao.Logica;

import java.util.ArrayList;
import java.util.Iterator;

import vaadin.Dao.Dominio.ListaProductos;
import vaadin.Dao.Dominio.Producto;

public class ProcDefecto {

	public void ProcDefecto() {
		// TODO Auto-generated constructor stub
    
			if(ListaProductos.getInstance().getLista_productos().isEmpty()) {
						String a = "Palo" ;
				    	Integer b = 1 ;
				    	Double c = (double) 2;
				    	ArrayList<Producto> ej = new ArrayList<Producto>();
				    	Producto proc = new Producto(a,b,c, ej);
				    	ListaProductos.getInstance().getLista_productos().add(proc);
				    	
				    	String d =  "Piedra";
				    	Producto proc2 = new Producto(d,b,c, ej);
				    	ListaProductos.getInstance().getLista_productos().add(proc2);
				    	
			}
	}

}
