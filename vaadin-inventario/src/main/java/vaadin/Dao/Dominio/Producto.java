package vaadin.Dao.Dominio;

import java.util.ArrayList;

public class Producto {
	private String nombre;
	private Integer cantidad;
	private Double precioFabricacion;
	private ArrayList<Producto> componentes;
	public Producto() {
		// TODO Auto-generated constructor stub
	}

	public Producto(String nombre, Integer cantidad, 
			Double precioFabricacion,ArrayList<Producto> componentes) {
		super();
		this.nombre = nombre;
		this.cantidad = cantidad;
		this.precioFabricacion = precioFabricacion;
		this.componentes = componentes;
	}

	

	public ArrayList<Producto> getComponentes() {
		return componentes;
	}

	public void setComponentes(ArrayList<Producto> componentes) {
		this.componentes = componentes;
	}


	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}



	public Double getPrecioFabricacion() {
		return precioFabricacion;
	}

	public void setPrecioFabricacion(Double precioFabricacion) {
		this.precioFabricacion = precioFabricacion;
	}


}
