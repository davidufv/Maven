package com.inventario.vaadin_inventario;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.annotation.WebServlet;


import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.server.BrowserWindowOpener;
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
import com.vaadin.ui.RadioButtonGroup;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Grid.SelectionMode;



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
	private Double costesFabTotales = 0.0;
    @Override
    protected void init(VaadinRequest vaadinRequest) {
    	
    	String a = "Palo" ;
    	Integer b = 1 ;
    	Double f = (double) 2;
    	ArrayList<Producto> ej = new ArrayList<Producto>();
    	Producto porc = new Producto(a,b,f, ej);
    	ListaProductos.getInstance().getLista_productos().add(porc);
  
    	    	
    	
    	//Campos a rellenar tanto en añadir como editar
    	FormLayout formLayout = new FormLayout();
    	FormLayout formLayout2 = new FormLayout();
    	TextField textFieldNombre = new TextField("Nombre");
    	TextField textFieldCantidad = new TextField("Cantidad" );
    	TextField textFieldFabricacion = new TextField("Precio Fabricación " );
    	TextField textCostesFabricacion = new TextField("Costes Fabricación totales" );
    	TextField textIngreso = new TextField("Nuevo Ingreso Efectivo");
    	Button buttonAddProc = new Button("Añadir/Editar");
    	Button ingreso = new Button("Ingreso efectivo");
    	
    	//LOGICA CHECKBOX
    	CheckBoxGroup<String> multi =
    		    new CheckBoxGroup<>("Seleccion Componentes");
    	Iterator<Producto> it = ListaProductos.getInstance().getLista_productos().iterator();
    	ArrayList<String> nombres = new ArrayList<String>() ;
		while (it.hasNext()) {
				nombres.add(it.next().getNombre());
				//precio.add(it.next().getPrecioFabricacion());
			}
    	multi.setItems(nombres);
    	
    	//CODIGO CAMPO COSTES TOTALES
    	Iterator<Producto> costes = ListaProductos.getInstance().getLista_productos().iterator();
		while (costes.hasNext()) {
			costesFabTotales += costes.next().getPrecioFabricacion();
				//precio.add(it.next().getPrecioFabricacion());
			}
		textCostesFabricacion.setValue(costesFabTotales.toString());
		
		//CODIGO CAMPO INGRESOS
		
        //Boton ingreso
		/*
        ingreso.addClickListener(e -> {
        	
        	Integer auxCantidad = 0;  	
        	Integer cantidadMas = 0;
        	auxCantidad = Integer.parseInt(textFieldCantidadMas.getValue());
        	cantidadMas = selectedProducto.getCantidad() + auxCantidad;
        	selectedProducto.setCantidad(cantidadMas);
        	costesFabTotales = cantidadMas * selectedProducto.getPrecioFabricacion();
        	System.out.println(costesFabTotales);
    		textCostesFabricacion.setValue(costesFabTotales.toString());

        	textFieldCantidadMas.clear();
        	grid.setItems(ListaProductos.getInstance().getLista_productos());
        	//grid.setItems(Pokedex.getInstance().getPokemons());
        	removeWindow(subWindow);
        });*/
        
    	//String cantidad = Integer.toString(textFieldCantidad.getValue());
        //Tabla Uno
    	Grid<Producto> grid = new Grid<Producto>();
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
     			ingreso
     			
    			
    	);
     	
     	

     	
     	
     	

    	horizontalLayout.addComponents(grid, formLayout,formLayout2);
    	//horizontalLayout.setComponentAlignment(formLayout, Alignment.MIDDLE_CENTER);
    	setContent(horizontalLayout);
    	//formLayout.setComponentAlignment( horizontalLayout, Alignment.TOP_CENTER);
    	
    	
    	
    	//Ventana que se abre al clicar con los campos correspondientes
    	Window subWindow = new Window("Detalles Producto");
    	HorizontalLayout horizontalLayout2 = new HorizontalLayout();	
        VerticalLayout subContent = new VerticalLayout();
        VerticalLayout subContent2 = new VerticalLayout();
        /*subWindow.setHeight("400px");
        subWindow.setWidth("300px");*/
        
        Label labelNombre = new Label();
        Label labelCantidad = new Label();

        Label labelFabricacion = new Label();
    	TextField textFieldCantidadMas = new TextField("Sumar cantidad" );
    	TextField textFieldCantidadMenos = new TextField("Restar Cantidad" );

        Button buttonDelete = new Button("Borrar Producto");
        Button buttonEdit = new Button("Editar Producto");
        Button sumarCantidad = new Button("Sumar Cantidad");
        Button restarCantidad = new Button("Restar Cantidad");
        
        //Boton Sumar
        sumarCantidad.addClickListener(e -> {
        	
        	Integer auxCantidad = 0;  	
        	Integer cantidadMas = 0;
        	auxCantidad = Integer.parseInt(textFieldCantidadMas.getValue());
        	cantidadMas = selectedProducto.getCantidad() + auxCantidad;
        	selectedProducto.setCantidad(cantidadMas);
        	costesFabTotales = cantidadMas * selectedProducto.getPrecioFabricacion();
        	System.out.println(costesFabTotales);
    		textCostesFabricacion.setValue(costesFabTotales.toString());

        	textFieldCantidadMas.clear();
        	grid.setItems(ListaProductos.getInstance().getLista_productos());
        	//grid.setItems(Pokedex.getInstance().getPokemons());
        	removeWindow(subWindow);
        });
        
      //Boton Restar
        restarCantidad.addClickListener(e -> {
        	
        	Integer auxCantidad2 = 0;
        	Integer cantidadMenos = 0;
        	auxCantidad2 = Integer.parseInt(textFieldCantidadMenos.getValue());
        	cantidadMenos = selectedProducto.getCantidad() - auxCantidad2;
        	selectedProducto.setCantidad(cantidadMenos);
        	//ListaProductos.getInstance().getLista_productos()
        	if(selectedProducto.getCantidad() == 0) {
        		ListaProductos.getInstance().getLista_productos().remove(selectedProducto);
        	}
        	textFieldCantidadMenos.clear();
        	grid.setItems(ListaProductos.getInstance().getLista_productos());
        	//grid.setItems(Pokedex.getInstance().getPokemons());
        	removeWindow(subWindow);
        });
        
        
        //boton borrar
        buttonDelete.addClickListener(e -> {
        	ListaProductos.getInstance().getLista_productos().remove(selectedProducto);
        	grid.setItems(ListaProductos.getInstance().getLista_productos());
        	//grid.setItems(Pokedex.getInstance().getPokemons());
        	removeWindow(subWindow);
        });
        
        //Boton editar
        buttonEdit.addClickListener(e -> {
        	
        	grid.setItems(ListaProductos.getInstance().getLista_productos());
        	//grid.setItems(Pokedex.getInstance().getPokemons());
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
    	//setContent(horizontalLayout);

        subContent.addComponents(labelNombre, labelCantidad,labelFabricacion, 
        		buttonDelete,buttonEdit);  
        subContent2.addComponents(textFieldCantidadMas,sumarCantidad,textFieldCantidadMenos,restarCantidad);
        subWindow.center();
        subWindow.setContent(horizontalLayout2);
    
    
        
    
    	
    	//Tabla Uno campos 

        //informationCell.setText("Basic Information");
        //grid.addHeaderRowAt(getTabIndex()).setStyleName("Productos Basicos");
     //   grid.addColumn(Producto::getNombre).seth
    	grid.addColumn(Producto::getNombre).setCaption("Nombre");
    	grid.addColumn(Producto::getCantidad).setCaption("Cantidad");
    	grid.addColumn(Producto::getPrecioFabricacion).setCaption("Fabricacion");
    	//grid.addColumn(Mueble::getTipo).setCaption("Tipo");
    	grid.setSelectionMode(SelectionMode.SINGLE);
		grid.setItems(ListaProductos.getInstance().getLista_productos());

    	grid.addItemClickListener(event -> {
    		
    		selectedProducto = event.getItem();
    		
        	// Notification.show("Value: " + event.getItem());
        	labelNombre.setValue(selectedProducto.getNombre());
        	//labelNumber.setValue(selectedProducto.getCantidad());
        	labelCantidad.setValue(Integer.toString(selectedProducto.getCantidad()));
        	labelFabricacion.setValue(Double.toString(selectedProducto.getPrecioFabricacion()));
        //	labelType.setValue(selectedProducto.getTipo());
        	
        	
        	removeWindow(subWindow);
        	addWindow(subWindow);
        	
    	});

    	/* FORM */
    	
    	//BOTON AÑADIR!!!!!!!! // Editar
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
	    		if(!encontrado){
	    	    	//System.out.println(multi.getValue());
	    		Set<String>check = multi.getValue();
	    		double precioIterator = 0;
	    	//	Set<Double>check2 = precio.get(index)
	    		ArrayList<Producto> componentesProc = new ArrayList<Producto>() ;
	        	Iterator<Producto> it3 = ListaProductos.getInstance().getLista_productos().iterator();
	        //	ArrayList<Double> precio = new ArrayList<Double>() ;

	        	
	        	
	        	//Cogemos el nombre y su precio de fabricacion y los sumamos para dar el precio total del componente
	    		while (it3.hasNext()) {
	    			Producto auxiliar = it3.next();
	    				if(check.contains(auxiliar.getNombre())) {
	    					componentesProc.add(auxiliar);
	    					precioIterator = auxiliar.getPrecioFabricacion();
	    					//precioIterator = auxiliar.getPrecioFabricacion();
	    					//check.remove(auxiliar.getNombre());
	    				}
	    					
	    			}
	    		
	    		Double precioCampo = Double.parseDouble(textFieldFabricacion.getValue());
	    		Double precioTotal = precioCampo + precioIterator;
	    		Producto p = new Producto(
	    				textFieldNombre.getValue(),
	    				Integer.parseInt(textFieldCantidad.getValue()),
	    				//Double.parseDouble(textFieldFabricacion.getValue()),
	    				precioTotal,
	    				componentesProc
	    				
	    				//textFieldType.getValue()
	    				);
	    		
	    		ListaProductos.getInstance().getLista_productos().add(p);  		
	    		textFieldNombre.clear();
	    		textFieldCantidad.clear();
				textFieldFabricacion.clear();
	    		//textFieldType.clear();  		
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
		    	//ACTUALIZAR
		    	Page.getCurrent().reload();
		    	//formLayout.getData()
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
    		//selectedProducto.getId()
    		/*Iterator<Producto> it = ListaProductos.getInstance().getLista_productos().iterator();
    		while (it.hasNext()) {
				Producto aux = it.next();
				if(aux.getId() == selectedProducto.getId()) {
					ListaProductos.getInstance().getLista_productos().remove(aux);
					ListaProductos.getInstance().getLista_productos().add(selectedProducto);

				}
				
			}*/
    		Set<String>check = multi.getValue();
    		ArrayList<Producto> componentesProc = new ArrayList<Producto>() ;
        	Iterator<Producto> it3 = ListaProductos.getInstance().getLista_productos().iterator();
        	double precioIterator2 = 0;
    		while (it3.hasNext()) {
    			Producto auxiliar = it3.next();
    				if(check.contains(auxiliar.getNombre())) {
    					componentesProc.add(auxiliar);
    					precioIterator2 = auxiliar.getPrecioFabricacion();
    					//precioIterator = auxiliar.getPrecioFabricacion();
    					//check.remove(auxiliar.getNombre());
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
    			
    				//textFieldType.getValue()
    				));
    		
    		selectedProducto= null;
    		textFieldNombre.clear();
    		textFieldCantidad.clear();
			textFieldFabricacion.clear();
    		//textFieldType.clear();  	
		//	grid.clearSortOrder();
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
