package com.inventario.vaadin_inventario;

public class Producto {
	private String nombre;
	private String cantidad;

	public Producto() {
		// TODO Auto-generated constructor stub
	}

	public Producto(String nombre, String cantidad) {
		super();
		this.nombre = nombre;
		this.cantidad = cantidad;
	}


	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCantidad() {
		return cantidad;
	}

	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}
	public String getStringBusqueda() {
		return nombre  + cantidad ;
	}
}
