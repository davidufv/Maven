package com.inventario.vaadin_inventario;

import javax.servlet.annotation.WebServlet;


import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
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
    	

    	
    	Grid<Producto> grid = new Grid<Producto>();
    	HorizontalLayout horizontalLayout = new HorizontalLayout();	
    	/* VENTANA DETALLE */
    	
    	Window subWindow = new Window("Detalles Producto");
    	
        VerticalLayout subContent = new VerticalLayout();
        subWindow.setHeight("200px");
        subWindow.setWidth("400px");
        Label labelNumber = new Label();
        Label labelName = new Label();
        Label labelType = new Label();
        Button buttonDelete = new Button("Borrar Producto");
       // Button buttonEdit = new Button("Editar Mueble");
        
        buttonDelete.addClickListener(e -> {
        	ListaProductos.getInstance().getLista_productos().remove(selectedProducto);
        	grid.setItems(ListaProductos.getInstance().getLista_productos());
        	//grid.setItems(Pokedex.getInstance().getPokemons());
        	removeWindow(subWindow);
        });
        
   
              
        subContent.addComponents(labelNumber, labelName, labelType, buttonDelete);       
        subWindow.center();
        subWindow.setContent(subContent);
        
        
        
        // addWindow(subWindow);
    	
    	/* TABLE */
    	//grid.addColumn(propertyName)
     
        //informationCell.setText("Basic Information");
        //grid.addHeaderRowAt(getTabIndex()).setStyleName("Productos Basicos");
     //   grid.addColumn(Producto::getNombre).seth
    	grid.addColumn(Producto::getNombre).setCaption("Nombre");
    	grid.addColumn(Producto::getCantidad).setCaption("Cantidad");
    	//grid.addColumn(Mueble::getTipo).setCaption("Tipo");
    	grid.setSelectionMode(SelectionMode.SINGLE);
    	
    	grid.addItemClickListener(event -> {
    		
    		selectedProducto = event.getItem();
    		
        	// Notification.show("Value: " + event.getItem());
        	labelNumber.setValue(selectedProducto.getNombre());
        	labelNumber.setValue(selectedProducto.getCantidad());
        //	labelName.setValue(Integer.toString(selectedProducto.getCantidad()));
        //	labelType.setValue(selectedProducto.getTipo());
        	
        	
        	removeWindow(subWindow);
        	addWindow(subWindow);
        	
    	});
    	
    	
    	/* FORM */
    	
    	
    	FormLayout formLayout = new FormLayout();
    
    	TextField textFieldNombre = new TextField("Nombre");

    	TextField textFieldCantidad = new TextField("Cantidad" );
    	//TextField textFieldType = new TextField("Tipo");
    	Button buttonAddProc = new Button("Añadir");
    	//String cantidad = Integer.toString(textFieldCantidad.getValue());

    	buttonAddProc.addClickListener(e -> {
    		
    		Producto p = new Producto(
    				textFieldNombre.getValue(),
    				textFieldCantidad.getValue()
    				//textFieldType.getValue()
    				);
    		
    		ListaProductos.getInstance().getLista_productos().add(p);
    		
    		textFieldNombre.clear();
    		textFieldCantidad.clear();
    		//textFieldType.clear();
    		
    		grid.setItems(ListaProductos.getInstance().getLista_productos());
    		
    		
    		Notification.show("Productos totales " + 
    				ListaProductos.getInstance().getLista_productos().size() + "!!",
    				Notification.TYPE_TRAY_NOTIFICATION);
    	});	

    	formLayout.addComponents(
    			textFieldNombre, 
    			textFieldCantidad, 
    		 
    			buttonAddProc
    			
    	);
    	
    	horizontalLayout.addComponents(grid, formLayout);
    	setContent(horizontalLayout);
    	
    	
    }
    	
    	

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
