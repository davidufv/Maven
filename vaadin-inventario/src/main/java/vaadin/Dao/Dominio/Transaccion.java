package vaadin.Dao.Dominio;

import java.util.ArrayList;
import java.util.Date;

public class Transaccion {

	private Date fecha;
	private Integer codigo;
	private Double cantidadT;
	
	
	
	public Transaccion(Double cantidadT, Integer codigo,Date fecha ) {
		super();
		this.fecha = fecha;
		this.codigo = codigo;
		this.cantidadT = cantidadT;
	}

	public Double getCantidadT() {
		return cantidadT;
	}

	public void setCantidadT(Double cantidadT) {
		this.cantidadT = cantidadT;
	}


	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}



}
