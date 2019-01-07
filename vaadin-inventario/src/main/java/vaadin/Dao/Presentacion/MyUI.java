package vaadin.Dao.Presentacion;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBoxGroup;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Grid.SelectionMode;

import vaadin.Dao.Dominio.ListaProductos;
import vaadin.Dao.Dominio.ListaTransacciones;
import vaadin.Dao.Dominio.Producto;
import vaadin.Dao.Dominio.Transaccion;
import vaadin.Dao.Logica.CambioDoblones;
import vaadin.Dao.Logica.CambioDracmas;
import vaadin.Dao.Logica.ProcDefecto;



/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {

	private Producto selectedProducto; 
	private Transaccion selectedTransaccion; 
	private Double costesFabTotales = 0.0;
	
    @Override
    protected void init(VaadinRequest vaadinRequest) {
    	
    	CambioDoblones cambioADoblon = new CambioDoblones();
    	CambioDracmas cambioADracma = new CambioDracmas();
    	
    	//LLamamos a dos productos por defecto de ejemplo
    	ProcDefecto nuevoProc = new ProcDefecto();
    	nuevoProc.ProcDefecto();
    	
    	//Campos a rellenar por el usuario
    	FormLayout formLayout = new FormLayout();
    	FormLayout formLayout2 = new FormLayout();
    	TextField textFieldNombre = new TextField("Nombre");
    	TextField textFieldCantidad = new TextField("Cantidad" );
    	TextField textFieldFabricacion = new TextField("Precio Fabricación " );
    	TextField textCostesFabricacion = new TextField("Costes Fabricación totales" );
    	TextField textIngreso = new TextField("Nuevo Ingreso Efectivo");
    	TextField cambio1 = new TextField("Cambio en Dracmas");
    	TextField cambio2 = new TextField("Cambio en Doblones");
    	Button buttonAddProc = new Button("Añadir/Editar");
    	Button ingreso = new Button("Ingreso efectivo");


    	
    	//LOGICA CHECKBOX , se usa mas tarde a la hora de componer un producto
    	CheckBoxGroup<String> multi =
    		    new CheckBoxGroup<>("Seleccion Componentes");
    	Iterator<Producto> it = ListaProductos.getInstance().getLista_productos().iterator();
    	ArrayList<String> nombres = new ArrayList<String>() ;
		while (it.hasNext()) {
				nombres.add(it.next().getNombre());
				//precio.add(it.next().getPrecioFabricacion());
			}
    	multi.setItems(nombres);
    	
    
    	//Campo costes totales, se va sumando el coste de todos los productos de la lista
    	Iterator<Producto> costes = ListaProductos.getInstance().getLista_productos().iterator();
		while (costes.hasNext()) {
			 costesFabTotales += costes.next().getPrecioFabricacion();
			System.out.println(costesFabTotales);
			}
		//Se pone esos costes totales por pantalla y a su vez el cambio a otra divisa
		textCostesFabricacion.setValue(costesFabTotales.toString());
		cambio1.setValue(cambioADracma.cambio(costesFabTotales).toString());
		cambio2.setValue(cambioADoblon.cambio(costesFabTotales).toString());
		
        //TablaS, tabla uno de productos, tabla dos de transicciones
    	Grid<Producto> grid = new Grid<Producto>();
    	Grid<Transaccion> grid2 = new Grid<Transaccion>();
    	HorizontalLayout horizontalLayout = new HorizontalLayout();	
     	formLayout.addComponents(
    			textFieldNombre, 
    			textFieldCantidad, 
    			textFieldFabricacion,
    			multi,
    			buttonAddProc	
    			
    	);
     	formLayout2.addComponents(
     			textCostesFabricacion,
     			textIngreso,
     			ingreso,
     			cambio1,
     			cambio2
     			
    			
    	);

    	horizontalLayout.addComponents(grid, formLayout,grid2,formLayout2);
    	setContent(horizontalLayout);
    	
    	//Ventana que se abre al clicar con los campos correspondientes
    	Window subWindow = new Window("Detalles Producto");
    	HorizontalLayout horizontalLayout2 = new HorizontalLayout();	
        VerticalLayout subContent = new VerticalLayout();
        VerticalLayout subContent2 = new VerticalLayout();
        
        Label labelNombre = new Label();
        Label labelCantidad = new Label();
        Label labelFabricacion = new Label();
    	TextField textFieldCantidadMas = new TextField("Sumar cantidad" );
    	TextField textFieldCantidadMenos = new TextField("Restar Cantidad" );

        Button buttonDelete = new Button("Borrar Producto");
        Button buttonEdit = new Button("Editar Producto");
        Button sumarCantidad = new Button("Sumar Cantidad");
        Button restarCantidad = new Button("Restar Cantidad");
		
        
        //Boton ingreso dinero
        ingreso.addClickListener(e -> {
        		
        	Double ingresoEfectivo = 0.0;
        	java.util.Date fecha = new Date();
        	ingresoEfectivo = Double.parseDouble(textIngreso.getValue());
        	//System.out.println(ingresoEfectivo);
        	costesFabTotales = costesFabTotales - ingresoEfectivo;
        	textCostesFabricacion.setValue(costesFabTotales.toString());
        	cambio1.setValue(cambioADracma.cambio(costesFabTotales).toString());
    		cambio2.setValue(cambioADoblon.cambio(costesFabTotales).toString());
    		textIngreso.clear();
    		Transaccion nuevaT = new Transaccion(
    				ingresoEfectivo,
    				(int) (Math.random() * 100) + 1,
    				fecha
    				);
    		ListaTransacciones.getInstance().getListaTransacciones().add(nuevaT);
    		grid2.setItems(ListaTransacciones.getInstance().getListaTransacciones());
        	//removeWindow(subWindow);
        });
        
        //Boton Sumar cantidad
        sumarCantidad.addClickListener(e -> {
        	
        	Integer auxCantidad = 0;  	
        	Integer cantidadMas = 0;
        	auxCantidad = Integer.parseInt(textFieldCantidadMas.getValue());
        	cantidadMas = selectedProducto.getCantidad() + auxCantidad;
        	selectedProducto.setCantidad(cantidadMas);	
    		costesFabTotales = cantidadMas * selectedProducto.getPrecioFabricacion();
        	System.out.println(costesFabTotales);
    		textCostesFabricacion.setValue(costesFabTotales.toString());
    		cambio1.setValue(cambioADracma.cambio(costesFabTotales).toString());
    		cambio2.setValue(cambioADoblon.cambio(costesFabTotales).toString());
        	textFieldCantidadMas.clear();
        	grid.setItems(ListaProductos.getInstance().getLista_productos());
        	removeWindow(subWindow);
        });
        
      //Boton Restar cantidad
        restarCantidad.addClickListener(e -> {
        	
        	Integer auxCantidad2 = 0;
        	Integer cantidadMenos = 0;
        	auxCantidad2 = Integer.parseInt(textFieldCantidadMenos.getValue());
        	cantidadMenos = selectedProducto.getCantidad() - auxCantidad2;
        	selectedProducto.setCantidad(cantidadMenos);
        	if(selectedProducto.getCantidad() == 0) {
        		ListaProductos.getInstance().getLista_productos().remove(selectedProducto);
		    	Page.getCurrent().reload();

        	}
        	textFieldCantidadMenos.clear();
        	grid.setItems(ListaProductos.getInstance().getLista_productos());
        	removeWindow(subWindow);
        });
        
        
        //boton borrar producto
        buttonDelete.addClickListener(e -> {
        	ListaProductos.getInstance().getLista_productos().remove(selectedProducto);
        	grid.setItems(ListaProductos.getInstance().getLista_productos());
        	Page.getCurrent().reload();
        	removeWindow(subWindow);
        });
        
        //Boton editar producto
        buttonEdit.addClickListener(e -> {
        	//Este boton hace que se borren los campos de añadir y podamos volver a escribirlos, editando el producto seleccionado
        	grid.setItems(ListaProductos.getInstance().getLista_productos());
    		textFieldNombre.clear();
    		textFieldCantidad.clear();
			textFieldFabricacion.clear();
			textFieldNombre.setValue(selectedProducto.getNombre());
    		textFieldCantidad.setValue(selectedProducto.getCantidad().toString());
			textFieldFabricacion.setValue(selectedProducto.getPrecioFabricacion().toString());
	    	Iterator<Producto> it4 = ListaProductos.getInstance().getLista_productos().iterator();
	    	ArrayList<String> nombresCheck = new ArrayList<String>() ;
			while (it4.hasNext()) {
				Producto bla = it4.next();
				if(!(selectedProducto.getNombre().equals(bla.getNombre())))
					nombresCheck.add(bla.getNombre());
				}
	    	multi.setItems(nombresCheck);
			
	    	formLayout.addComponents(
	    			textFieldNombre, 
	    			textFieldCantidad, 
	    			textFieldFabricacion,
	    			buttonAddProc
	    			
	    	);
	    	horizontalLayout.addComponents(grid, formLayout);
	    	horizontalLayout.setComponentAlignment(grid, Alignment.MIDDLE_CENTER);
	    	setContent(horizontalLayout);
	    	
        	removeWindow(subWindow);
        });
   

        horizontalLayout2.addComponents(subContent,subContent2);
        subContent.addComponents(labelNombre, labelCantidad,labelFabricacion, 
        		buttonDelete,buttonEdit);  
        subContent2.addComponents(textFieldCantidadMas,sumarCantidad,textFieldCantidadMenos,restarCantidad);
        subWindow.center();
        subWindow.setContent(horizontalLayout2);

    	//Tabla Uno campos (Productos)
    	grid.addColumn(Producto::getNombre).setCaption("Nombre");
    	grid.addColumn(Producto::getCantidad).setCaption("Cantidad");
    	grid.addColumn(Producto::getPrecioFabricacion).setCaption("Fabricacion");
    	grid.setSelectionMode(SelectionMode.SINGLE);
		grid.setItems(ListaProductos.getInstance().getLista_productos());

		//Selecion de producto por pantalla
    	grid.addItemClickListener(event -> {
    		
    		selectedProducto = event.getItem();
        	labelNombre.setValue(selectedProducto.getNombre());
        	labelCantidad.setValue(Integer.toString(selectedProducto.getCantidad()));
        	labelFabricacion.setValue(Double.toString(selectedProducto.getPrecioFabricacion()));
        	removeWindow(subWindow);
        	addWindow(subWindow);
        	
    	});
    	
    	//Tabla dos campos (Transacciones)
    	grid2.addColumn(Transaccion::getCantidadT).setCaption("Cantidad");
    	grid2.addColumn(Transaccion::getFecha).setCaption("FechaTran");
    	grid2.addColumn(Transaccion::getCodigo).setCaption("Codigo");
    	grid2.setSelectionMode(SelectionMode.SINGLE);
		grid2.setItems(ListaTransacciones.getInstance().getListaTransacciones());
		

    	//Boton añadir, editar, componer
    	buttonAddProc.addClickListener(e -> {
    		
    		//Añadimos un producto si no existe , si exite lo editamos
	    	if(selectedProducto == null) {	
	    		Boolean encontrado = false;
	        	Iterator<Producto> it2 = ListaProductos.getInstance().getLista_productos().iterator();
	    		while (it2.hasNext()) {
	    				if(it2.next().getNombre().equals(textFieldNombre.getValue())) {
	    					encontrado = true;
	    				}		
	    			}
	    		//Si no se ha encontrado el producto
	    		if(!encontrado){
	    		Set<String>check = multi.getValue();
	    		double precioIterator = 0;
	    		ArrayList<Producto> componentesProc = new ArrayList<Producto>() ;
	        	Iterator<Producto> it3 = ListaProductos.getInstance().getLista_productos().iterator();

	        	//Cogemos el nombre y su precio de fabricacion y los sumamos para dar el precio total del componente
	        	//a su vez usamos array de strings "check" que contiene el nombre del producto que hemos clicado en la checkbox
	        	//Si se añade un producto y se ha clicado en la checkbox se combinan
	    		while (it3.hasNext()) {
	    			Producto auxiliar = it3.next();
	    				if(check.contains(auxiliar.getNombre())) {
	    					componentesProc.add(auxiliar);
	    					precioIterator = auxiliar.getPrecioFabricacion();
	    				} 					
	    			} 		
	    		
	    		//Pasamos los campos a un nuevo producto
	    		Double precioCampo = Double.parseDouble(textFieldFabricacion.getValue());
	    		Double precioTotal = precioCampo + precioIterator;
	    		Producto p = new Producto(
	    				textFieldNombre.getValue(),
	    				Integer.parseInt(textFieldCantidad.getValue()),
	    				precioTotal,
	    				componentesProc
	    				);
	    		
	    		//Añadimos el producto
	    		ListaProductos.getInstance().getLista_productos().add(p);  		
	    		textFieldNombre.clear();
	    		textFieldCantidad.clear();
				textFieldFabricacion.clear();
	    		grid.setItems(ListaProductos.getInstance().getLista_productos());
	    		
	    		formLayout.addComponents(
		    			textFieldNombre, 
		    			textFieldCantidad, 
		    			textFieldFabricacion,
		    			multi,
		    			buttonAddProc			
		    	);
		    	
		    	horizontalLayout.addComponents(grid, formLayout);
		    	setContent(horizontalLayout);
		    	
		    	//ACTUALIZAr pagina
		    	Page.getCurrent().reload();
	    		Notification.show("Productos totales " + 
	    				ListaProductos.getInstance().getLista_productos().size() + "!!",
	    				Notification.TYPE_TRAY_NOTIFICATION);		
	    		}
	    		else {
	    			Notification.show("Producto ya existente " + 
		    				ListaProductos.getInstance().getLista_productos().size() + "!!",
		    				Notification.TYPE_TRAY_NOTIFICATION);
	    		}
    	}else {
    		//Si al recorrer el array encontramos el producto pasa a ser editado
    		Set<String>check = multi.getValue();
    		ArrayList<Producto> componentesProc = new ArrayList<Producto>() ;
        	Iterator<Producto> it3 = ListaProductos.getInstance().getLista_productos().iterator();
        	double precioIterator2 = 0;
    		while (it3.hasNext()) {
    			Producto auxiliar = it3.next();
    				if(check.contains(auxiliar.getNombre())) {
    					componentesProc.add(auxiliar);
    					precioIterator2 = auxiliar.getPrecioFabricacion();
    				}
    					
    			}
    		
    		Double precioCampo2 = Double.parseDouble(textFieldFabricacion.getValue());
    		Double precioTotal2 = precioCampo2 + precioIterator2;
    		ListaProductos.getInstance().getLista_productos().remove(selectedProducto);
    		ListaProductos.getInstance().getLista_productos().add(new Producto(
    				textFieldNombre.getValue(),
    				Integer.parseInt(textFieldCantidad.getValue()),
    				precioTotal2,
    				componentesProc
    				));
    		
    		selectedProducto= null;
    		textFieldNombre.clear();
    		textFieldCantidad.clear();
			textFieldFabricacion.clear();
    		grid.setItems(ListaProductos.getInstance().getLista_productos());
  	
    		formLayout.addComponents(
	    			textFieldNombre, 
	    			textFieldCantidad, 
	    			textFieldFabricacion,
	    			multi,
	    			buttonAddProc
	    			
	    			
	    	);
	    	horizontalLayout.addComponents(grid, formLayout);
	    	setContent(horizontalLayout);
    	}
	    });	    	
    }	
    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
