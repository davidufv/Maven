package vaadin.Dao.Logica;

public class CambioDracmas implements CambioMonedas {

	public CambioDracmas() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Double cambio(Double cantidadCambio) {
		// TODO Auto-generated method stub
		cantidadCambio= cantidadCambio * 0.5;
		return cantidadCambio;
	}

}
