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
	
    @Override
    protected void init(VaadinRequest vaadinRequest) {
    	/*
    	String a = "Palo" ;
    	Integer b = 1 ;
    	Integer c = 5 ;
    	Double d = (double) 66 ;
    	Double h = (double) 2 ;
    	Double f = (double) 8;
    	
    	Producto porc = new Producto(a,b,c,d,h,f,);
    */
    	
    //	ListaProductos.getInstance().getLista_productos().add(porc);
    	
    	
    	//Campos a rellenar tanto en añadir como editar
    	FormLayout formLayout = new FormLayout();
    	TextField textFieldNombre = new TextField("Nombre");
    	TextField textFieldCantidad = new TextField("Cantidad" );
    	TextField textFieldVenta = new TextField("Precio Venta");    	
    	TextField textFieldCompra = new TextField("Precio Compra");
    	TextField textFieldFabricacion = new TextField("Precio Fabricación " );
    	
    	//CHECKBOX PRODUCTOS
    	CheckBoxGroup<String> multi =
    		    new CheckBoxGroup<>("Seleccion Componentes");
    	
    		//multi.setItems("Nom", "Muchos", "Monta");
    	Iterator<Producto> it = ListaProductos.getInstance().getLista_productos().iterator();
    	ArrayList<String> nombres = new ArrayList<String>() ;
		while (it.hasNext()) {
				nombres.add(it.next().getNombre());
			}
    	multi.setItems(nombres);
    	//multi.g
    	
    	
    	//Campos y botones añadir,borrar
    	//TextField textFieldType = new TextField("Tipo");
    	Button buttonAddProc = new Button("Añadir/Editar");
    	
    	//MyUIListBuilder pestaña = new MyUIListBuilder();
        // Create an opener extension
        BrowserWindowOpener opener =
            new BrowserWindowOpener(TablaWeb.class);
        opener.setFeatures("height=1000px,width=1000px,resizable");

        // Attach it to a button
        Button button = new Button("Pop It Up");
        opener.extend(button);
        
    	//String cantidad = Integer.toString(textFieldCantidad.getValue());
        //Tabla Uno
    	Grid<Producto> grid = new Grid<Producto>();
    	HorizontalLayout horizontalLayout = new HorizontalLayout();	
     	formLayout.addComponents(
    			textFieldNombre, 
    			textFieldCantidad, 
    			textFieldVenta,
    			textFieldCompra,
    			textFieldFabricacion,
    			multi,
    			buttonAddProc,
    			button
    			
    			
    	);
    	horizontalLayout.addComponents(grid, formLayout);
    	horizontalLayout.setComponentAlignment(formLayout, Alignment.MIDDLE_CENTER);
    	setContent(horizontalLayout);
    	//formLayout.setComponentAlignment( horizontalLayout, Alignment.TOP_CENTER);
    	
    	
    	//TAbla dos
    	/*
    	Grid<Producto> grid2 = new Grid<Producto>();
     	formLayout.addComponents(
    			textFieldNombre, 
    			textFieldCantidad, 
    			textFieldVenta,
    			textFieldCompra,
    			textFieldFabricacion,
    			multi,
    			buttonAddProc,
    			button
    			
    			
    	);
    	horizontalLayout.addComponents(grid2, formLayout);
    	 */
    	
    	//Ventana que se abre al clicar con los campos correspondientes
    	Window subWindow = new Window("Detalles Producto");
        VerticalLayout subContent = new VerticalLayout();
        subWindow.setHeight("400px");
        subWindow.setWidth("300px");
        Label labelNombre = new Label();
        Label labelCantidad = new Label();
        Label labelVenta = new Label();
        Label labelCompra = new Label();
        Label labelFabricacion = new Label();
        Button buttonDelete = new Button("Borrar Producto");
        Button buttonEdit = new Button("Editar Producto");
        
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
    		textFieldVenta.clear();
			textFieldCompra.clear();
			textFieldFabricacion.clear();
			
			textFieldNombre.setValue(selectedProducto.getNombre());
    		textFieldCantidad.setValue(selectedProducto.getCantidad().toString());
    		textFieldVenta.setValue(selectedProducto.getPrecioVenta().toString());
			textFieldCompra.setValue(selectedProducto.getPrecioCompra().toString());
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
	    			textFieldVenta,
	    			textFieldCompra,
	    			textFieldFabricacion,
	    			buttonAddProc
	    			
	    	);
	    	
	    	horizontalLayout.addComponents(grid, formLayout);
	    	horizontalLayout.setComponentAlignment(grid, Alignment.MIDDLE_CENTER);
	    	setContent(horizontalLayout);
	    	
        	removeWindow(subWindow);
        });
   

        
        subContent.addComponents(labelNombre, labelCantidad, labelVenta,labelCompra,labelFabricacion, buttonDelete,buttonEdit);       
        subWindow.center();
        subWindow.setContent(subContent);
        
    
        
    
    	
    	//Tabla Uno campos 
    	//grid.addColumn(propertyName)tt
     
        //informationCell.setText("Basic Information");
        //grid.addHeaderRowAt(getTabIndex()).setStyleName("Productos Basicos");
     //   grid.addColumn(Producto::getNombre).seth
    	grid.addColumn(Producto::getNombre).setCaption("Nombre");
    	grid.addColumn(Producto::getCantidad).setCaption("Cantidad");
    	grid.addColumn(Producto::getPrecioCompra).setCaption("Compra");
    	grid.addColumn(Producto::getPrecioVenta).setCaption("Venta");
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
        	labelCompra.setValue(Double.toString(selectedProducto.getPrecioCompra()));
        	labelVenta.setValue(Double.toString(selectedProducto.getPrecioVenta()));
        	labelFabricacion.setValue(Double.toString(selectedProducto.getPrecioFabricacion()));
        //	labelType.setValue(selectedProducto.getTipo());
        	
        	
        	removeWindow(subWindow);
        	addWindow(subWindow);
        	
    	});
    	
    	//tabla dos
    	/*
    	grid2.addColumn(Producto::getNombre).setCaption("Nombre");
    	grid2.addColumn(Producto::getCantidad).setCaption("Cantidad");
    	grid2.addColumn(Producto::getPrecioCompra).setCaption("Compra");
    	grid2.addColumn(Producto::getPrecioVenta).setCaption("Venta");
    	grid2.addColumn(Producto::getPrecioFabricacion).setCaption("Fabricacion");
    	//grid.addColumn(Mueble::getTipo).setCaption("Tipo");
    	grid2.setSelectionMode(SelectionMode.SINGLE);
		grid2.setItems(ListaProductos.getInstance().getLista_productos());
	
    	grid2.addItemClickListener(event -> {
    		
    		selectedProducto = event.getItem();
    		
        	// Notification.show("Value: " + event.getItem());
        	labelNombre.setValue(selectedProducto.getNombre());
        	//labelNumber.setValue(selectedProducto.getCantidad());
        	labelCantidad.setValue(Integer.toString(selectedProducto.getCantidad()));
        	labelCompra.setValue(Double.toString(selectedProducto.getPrecioCompra()));
        	labelVenta.setValue(Double.toString(selectedProducto.getPrecioVenta()));
        	labelFabricacion.setValue(Double.toString(selectedProducto.getPrecioFabricacion()));
        //	labelType.setValue(selectedProducto.getTipo());
        	
        	
        	removeWindow(subWindow);
        	addWindow(subWindow);
        	
    	});
    	horizontalLayout.addComponents(grid2, formLayout);
    	*/
    	/* FORM */
    	
    	//BOTON AÑADIR!!!!!!!! // Editar
    	buttonAddProc.addClickListener(e -> {
    		
    		//Añadimos un producto si no existe , si exite lo editamos
	    	if(selectedProducto == null) {	
	    		
	    		Boolean encontrado = false;
	        	Iterator<Producto> it2 = ListaProductos.getInstance().getLista_productos().iterator();
	        	ArrayList<String> nombres2 = new ArrayList<String>() ;
	    		while (it2.hasNext()) {
	    			
	    				if(it2.next().getNombre().equals(textFieldNombre.getValue())) {
	    					encontrado = true;
	    				}
	    					
	    			}
	    		if(!encontrado){
	    	    	//System.out.println(multi.getValue());
	    		Set<String>check = multi.getValue();
	    		ArrayList<Producto> componentesProc = new ArrayList<Producto>() ;
	        	Iterator<Producto> it3 = ListaProductos.getInstance().getLista_productos().iterator();
	    		while (it3.hasNext()) {
	    			Producto auxiliar = it3.next();
	    				if(check.contains(auxiliar.getNombre())) {
	    					componentesProc.add(auxiliar);
	    					//check.remove(auxiliar.getNombre());
	    				}
	    					
	    			}
	    		Producto p = new Producto(
	    				textFieldNombre.getValue(),
	    				Integer.parseInt(textFieldCantidad.getValue()),
	    				(int) (Math.random() * 100) + 1,
	    				Double.parseDouble(textFieldVenta.getValue()),
	    				Double.parseDouble(textFieldCompra.getValue()),
	    				Double.parseDouble(textFieldFabricacion.getValue()),
	    				componentesProc
	    				
	    				//textFieldType.getValue()
	    				);
	    		
	    		ListaProductos.getInstance().getLista_productos().add(p);  		
	    		textFieldNombre.clear();
	    		textFieldCantidad.clear();
	    		textFieldVenta.clear();
				textFieldCompra.clear();
				textFieldFabricacion.clear();
	    		//textFieldType.clear();  		
	    		grid.setItems(ListaProductos.getInstance().getLista_productos());
	    		
	    		
	    		formLayout.addComponents(
		    			textFieldNombre, 
		    			textFieldCantidad, 
		    			textFieldVenta,
		    			textFieldCompra,
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
    		while (it3.hasNext()) {
    			Producto auxiliar = it3.next();
    				if(check.contains(auxiliar.getNombre())) {
    					componentesProc.add(auxiliar);
    					//check.remove(auxiliar.getNombre());
    				}
    					
    			}
    		ListaProductos.getInstance().getLista_productos().remove(selectedProducto);
    		ListaProductos.getInstance().getLista_productos().add(new Producto(
    				textFieldNombre.getValue(),
    				Integer.parseInt(textFieldCantidad.getValue()),
    				(int) (Math.random() * 100) + 1,
    				Double.parseDouble(textFieldVenta.getValue()),
    				Double.parseDouble(textFieldCompra.getValue()),
    				Double.parseDouble(textFieldFabricacion.getValue()),
    				componentesProc
    			
    				//textFieldType.getValue()
    				));
    		
    		selectedProducto= null;
    		textFieldNombre.clear();
    		textFieldCantidad.clear();
    		textFieldVenta.clear();
			textFieldCompra.clear();
			textFieldFabricacion.clear();
    		//textFieldType.clear();  	
		//	grid.clearSortOrder();
    		grid.setItems(ListaProductos.getInstance().getLista_productos());
    		
    		formLayout.addComponents(
	    			textFieldNombre, 
	    			textFieldCantidad, 
	    			textFieldVenta,
	    			textFieldCompra,
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
