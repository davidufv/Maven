package com.inventario.vaadin_inventario;

import java.util.ArrayList;

public class Producto {
	private String nombre;
	private Integer cantidad;
	private Integer id;

	private Double precioVenta;
	private Double precioCompra;
	private Double precioFabricacion;
	private ArrayList<Producto> componentes;
	public Producto() {
		// TODO Auto-generated constructor stub
	}

	public Producto(String nombre, Integer cantidad, Integer id, Double precioVenta, Double precioCompra,
			Double precioFabricacion,ArrayList<Producto> componentes) {
		super();
		this.nombre = nombre;
		this.cantidad = cantidad;
		this.id = id;
		this.precioVenta = precioVenta;
		this.precioCompra = precioCompra;
		this.precioFabricacion = precioFabricacion;
		this.componentes = componentes;
	}

	
	public ArrayList<Producto> getComponentes() {
		return componentes;
	}

	public void setComponentes(ArrayList<Producto> componentes) {
		this.componentes = componentes;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Double getPrecioVenta() {
		return precioVenta;
	}

	public void setPrecioVenta(Double precioVenta) {
		this.precioVenta = precioVenta;
	}

	public Double getPrecioCompra() {
		return precioCompra;
	}

	public void setPrecioCompra(Double precioCompra) {
		this.precioCompra = precioCompra;
	}

	public Double getPrecioFabricacion() {
		return precioFabricacion;
	}

	public void setPrecioFabricacion(Double precioFabricacion) {
		this.precioFabricacion = precioFabricacion;
	}


}
