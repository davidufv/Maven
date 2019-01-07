package vaadin.Dao.Logica;

public class CambioDoblones implements CambioMonedas {

	public CambioDoblones() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Double cambio(Double cantidadCambio) {
		// TODO Auto-generated method stub
		cantidadCambio = cantidadCambio * 0.3;
		return cantidadCambio;
	}

}
