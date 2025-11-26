import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Simula la interacción con la Base de Datos para todas las entidades.
 * En un proyecto real, estos métodos tendrían código JDBC.
 */
public class BancoDAO {
    // Listas para simular las tablas de la Base de Datos
    private List<Banco> bancos = new ArrayList<>();
    private List<Cliente> clientes = new ArrayList<>();
    private List<Cuenta> cuentas = new ArrayList<>();
    private List<Movimiento> movimientos = new ArrayList<>();
    
    // Contadores para IDs
    private int nextMovimientoId = 1;

    public BancoDAO() {
        // Precarga de datos
        bancos.add(new Banco(1, "Banamex"));
        bancos.add(new Banco(2, "Santander"));
        
        clientes.add(new Cliente(12345, "Ignacio"));
        clientes.add(new Cliente(67890, "Maria"));
        
        cuentas.add(new Cuenta("1234567890", 12345, 2, 85.00)); // Cuenta de Ignacio en Santander
    }

    // --- Métodos de Cliente ---
    public Cliente buscarCliente(int numeroCliente) {
        return clientes.stream()
            .filter(c -> c.getNumeroCliente() == numeroCliente)
            .findFirst()
            .orElse(null);
    }
    
    // --- Métodos de Cuenta ---
    public List<Cuenta> listarCuentasPorCliente(int numeroCliente) {
        return cuentas.stream()
            .filter(c -> c.getNumeroCliente() == numeroCliente)
            .collect(Collectors.toList());
    }
    
    public Cuenta buscarCuenta(String numeroCuenta) {
        return cuentas.stream()
            .filter(c -> c.getNumeroCuenta().equals(numeroCuenta))
            .findFirst()
            .orElse(null);
    }
    
    public void actualizarSaldoCuenta(String numeroCuenta, double nuevoSaldo) {
        Cuenta cuenta = buscarCuenta(numeroCuenta);
        if (cuenta != null) {
            cuenta.setSaldoActual(nuevoSaldo);
        }
    }

    // --- Métodos de Movimiento ---
    public void registrarMovimiento(Movimiento movimiento) {
        // Asignar ID simulado
        movimientos.add(new Movimiento(
            nextMovimientoId++, 
            movimiento.getNumeroCuenta(),
            movimiento.getFechaMovimiento(),
            movimiento.getConcepto(),
            movimiento.getMonto(),
            movimiento.getTipo()
        ));
        
        // Simular actualización del saldo
        Cuenta cuenta = buscarCuenta(movimiento.getNumeroCuenta());
        if (cuenta != null) {
            double nuevoSaldo = cuenta.getSaldoActual();
            if (movimiento.getTipo().equals("ABONO")) {
                nuevoSaldo += movimiento.getMonto();
            } else if (movimiento.getTipo().equals("CARGO")) {
                nuevoSaldo -= movimiento.getMonto();
            }
            actualizarSaldoCuenta(movimiento.getNumeroCuenta(), nuevoSaldo);
        }
    }
    
    public List<Movimiento> listarMovimientosPorCuenta(String numeroCuenta) {
        return movimientos.stream()
            .filter(m -> m.getNumeroCuenta().equals(numeroCuenta))
            .collect(Collectors.toList());
    }

    // --- Métodos de Banco (para Combobox) ---
    public Banco buscarBanco(int idBanco) {
        return bancos.stream()
            .filter(b -> b.getIdBanco() == idBanco)
            .findFirst()
            .orElse(null);
    }
}
