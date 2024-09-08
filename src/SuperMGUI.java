
//DISEÑO DE LA INTERFAZ DE LA APLICACIÓN

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SuperMGUI {
    private JFrame frame;
    private FacturaSuper factura;
    private JTextField tfCodigo, tfNombre, tfCantidad, tfPrecio;
    private JTextArea taFactura;
    private JTextArea taBuscarProducto;

    public SuperMGUI() {
        factura = new FacturaSuper();
        frame = new JFrame("SUPERMERCADO EL MANANTIAL");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        // Panel para campos de entrada usando 'GridBagLayout'
        JPanel panelInput = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelInput.add(new JLabel("Código:"), gbc);

        gbc.gridx = 1;
        tfCodigo = new JTextField(15);
        panelInput.add(tfCodigo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelInput.add(new JLabel("Nombre:"), gbc);

        gbc.gridx = 1;
        tfNombre = new JTextField(15);
        panelInput.add(tfNombre, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panelInput.add(new JLabel("Cantidad:"), gbc);

        gbc.gridx = 1;
        tfCantidad = new JTextField(15);
        panelInput.add(tfCantidad, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panelInput.add(new JLabel("Precio Unidad:"), gbc);

        gbc.gridx = 1;
        tfPrecio = new JTextField(15);
        panelInput.add(tfPrecio, gbc);
        
        
        
        
        // Panel para los botones usando 'FlowLayout'
        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton btnAgregar = new JButton("Agregar Producto");
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarProducto();
            }
        });
        panelButtons.add(btnAgregar);

        JButton btnMostrar = new JButton("Mostrar Factura");
        btnMostrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarFactura();
            }
        });
        panelButtons.add(btnMostrar);

        JButton btnModificar = new JButton("Modificar Precio (+5%)");
        btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarProducto();
            }
        });
        panelButtons.add(btnModificar);

        JButton btnEliminar = new JButton("Eliminar Producto");
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarProducto();
            }
        });
        panelButtons.add(btnEliminar);

        JButton btnBuscar = new JButton("Buscar Producto");
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProducto();
            }
        });
        panelButtons.add(btnBuscar);

        JButton btnLimpiar = new JButton("Limpiar Áreas");
        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarAreas();
            }
        });
        panelButtons.add(btnLimpiar);

        frame.add(panelInput, BorderLayout.NORTH);
        frame.add(panelButtons, BorderLayout.SOUTH);
        
        
        
        
        // Área de Texto en Blanco en donde se ve todo de la Factura
        taFactura = new JTextArea();
        taFactura.setEditable(false);
        JScrollPane scrollPaneFactura = new JScrollPane(taFactura);
        scrollPaneFactura.setBorder(BorderFactory.createTitledBorder("Factura"));
        
        

        
        // Área de Texto Blanco en donde se va a mostrar los Detalles del Producto Buscado: cod, nombre, cant, precio
        taBuscarProducto = new JTextArea();
        taBuscarProducto.setEditable(false);
        JScrollPane scrollPaneBuscar = new JScrollPane(taBuscarProducto);
        scrollPaneBuscar.setBorder(BorderFactory.createTitledBorder("Detalles del Producto"));

        
        
        
        // Panel Central Dividido en dos Áreas de Texto - tipo scroll para que se vizualice mas de 10 productos
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollPaneFactura, scrollPaneBuscar);
        splitPane.setDividerLocation(300);

        frame.add(splitPane, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    
    
    
    // Método para Agregar Producto a la aplicación
    private void agregarProducto() {
        try {
            String codigo = tfCodigo.getText();
            String nombre = tfNombre.getText();
            int cantidad = Integer.parseInt(tfCantidad.getText());
            double precio = Double.parseDouble(tfPrecio.getText());

            Producto producto = new Producto(codigo, nombre, cantidad, precio);
            factura.agregarProducto(producto);

            limpiarCampos();
            JOptionPane.showMessageDialog(frame, "Producto agregado correctamente.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Error en los datos ingresados. Por favor revisa los valores.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    
    
    
    // Método para Mostrar Factura Completa - zona de text area en Blanco
    private void mostrarFactura() {
        String facturaCompleta = factura.mostrarFactura();
        taFactura.setText(facturaCompleta);
    }

    
    
    
    
    // Método para Modificar Producto:Se pone codigo, nombre, modifico cantidad y precio
    private void modificarProducto() {
    String identificador = tfCodigo.getText().isEmpty() ? tfNombre.getText() : tfCodigo.getText(); // Usar nombre si el código está vacío
    Producto producto = factura.buscarProducto(identificador);

    if (producto != null) {
        try {
            String nuevoNombre = tfNombre.getText();
            int nuevaCantidad = Integer.parseInt(tfCantidad.getText());
            double nuevoPrecio = Double.parseDouble(tfPrecio.getText());

            producto.setPrecioUnidad(nuevoPrecio);
            producto.setCantidad(nuevaCantidad);  
            producto.setNombre(nuevoNombre);     

            mostrarFactura(); 
            JOptionPane.showMessageDialog(frame, "Producto modificado correctamente.");
            limpiarCampos(); 
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Error en los datos ingresados. Por favor revisa los valores.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } else {
        JOptionPane.showMessageDialog(frame, "Producto no encontrado.");
    }
}


    
    
    // Método para Eliminar Producto- se puede eliminar con solo codigo, o solo nombre de producto
    private void eliminarProducto() {
    String identificador = tfCodigo.getText().isEmpty() ? tfNombre.getText() : tfCodigo.getText();
    if (factura.buscarProducto(identificador) != null) {
        factura.eliminarProducto(identificador);
        mostrarFactura(); // Actualiza la factura después de eliminar
        JOptionPane.showMessageDialog(frame, "Producto eliminado correctamente.");
        limpiarCampos(); // Limpia los campos después de eliminar
    } else {
        JOptionPane.showMessageDialog(frame, "Producto no encontrado.");
    }
}

    

    
    // Método para Buscar Producto- se puede buscar por medio de solo el codigo, o solo el nombre
    private void buscarProducto() {
    String identificador = tfCodigo.getText().isEmpty() ? tfNombre.getText() : tfCodigo.getText();
    String detalles = factura.mostrarProducto(identificador);
    taBuscarProducto.setText(detalles);
    limpiarCampos(); // Limpia los campos después de buscar
}


    
    
    // Método para Limpiar las Áreas de Texto- cada que uso boton estos campos se limpian de info
    private void limpiarAreas() {
        taFactura.setText("");
        taBuscarProducto.setText("");
    }

    
    
    
    // Método para Limpiar Campos de Entrada
    private void limpiarCampos() {
        tfCodigo.setText("");
        tfNombre.setText("");
        tfCantidad.setText("");
        tfPrecio.setText("");
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SuperMGUI();
            }
        });
    }
}
