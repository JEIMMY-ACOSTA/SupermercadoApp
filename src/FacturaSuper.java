import java.util.LinkedList;
import java.util.List;

public class FacturaSuper {
    private List<Producto> productos;

    public FacturaSuper() {
        productos = new LinkedList<>();
    }

    // Agregando los Productos en orden segun el código consecutivo
    public void agregarProducto(Producto producto) {
        int i = 0;
        while (i < productos.size() && productos.get(i).getCodigo().compareTo(producto.getCodigo()) < 0) {
            i++;
        }
        productos.add(i, producto);
    }

    // Modificando los Productos: Incrementando el precio en un 5%
    public void modificarProducto(String identificador) {
        for (Producto p : productos) {
            if (p.getCodigo().equals(identificador) || p.getNombre().equalsIgnoreCase(identificador)) {
                double nuevoPrecio = p.getPrecioUnidad() * 1.05; // Incremento del 5%
                p.setPrecioUnidad(nuevoPrecio);
                break;
            }
        }
    }

    // Eliminando datos de la factura - Producto x
    public void eliminarProducto(String identificador) {
        productos.removeIf(p -> p.getCodigo().equals(identificador) || p.getNombre().equalsIgnoreCase(identificador));
    }

    // Buscando los datos correspondientes a un Producto x - muestra datos en Detalles producto : cod, nombre, cant, valor
    public Producto buscarProducto(String identificador) {
        for (Producto p : productos) {
            if (p.getCodigo().equals(identificador) || p.getNombre().equalsIgnoreCase(identificador)) {
                return p;
            }
        }
        return null;
    }

    // Calculando el  Total de la Factura
    public double calcularTotalFactura() {
        double total = 0;
        for (Producto p : productos) {
            total += p.getTotal();
        }
        return total;
    }

    // Calculando el IVA al (19%)  - segun indicacion del taller
    public double calcularIVA() {
        return calcularTotalFactura() * 0.19;
    }

    // Mostrando la Factura Completa - en la sección factura
    public String mostrarFactura() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-10s |  %-20s   | %-18s    | %-20s    | %-15s\n", "Código", "Nombre", "Cantidad", "Precio", "Total"));
        sb.append("-------------------------------------------------------------------------------------------------------------------\n");
        for (Producto p : productos) {  // productos es la lista de productos en la factura
            sb.append(String.format("%-10s     %-20s     %-18d       %-20.2f       %-15.2f\n",
                    p.getCodigo(),
                    p.getNombre(),
                    p.getCantidad(),
                    p.getPrecioUnidad(),
                    p.getTotal()));
        }

        sb.append(String.format("\nPrecio final: %.2f\n", calcularTotalFactura()));
        sb.append(String.format("IVA: %.2f\n", calcularIVA()));
        return sb.toString();
    }

    // Mostrando Detalles de un Producto Específico
    public String mostrarProducto(String codigo) {
        Producto p = buscarProducto(codigo);
        if (p != null) {
            return String.format("Código: %s\nNombre: %s\nCantidad: %d\nPrecio Unidad: %.2f\nTotal: %.2f\n",
                    p.getCodigo(),
                    p.getNombre(),
                    p.getCantidad(),
                    p.getPrecioUnidad(),
                    p.getTotal());
        } else {
            return "Producto no encontrado.";
        }
    }
}