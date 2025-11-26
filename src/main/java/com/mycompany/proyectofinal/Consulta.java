import java.util.ArrayList;
import java.util.List;

public class Consulta {
    private BancoDAO dao;

    public Consulta(BancoDAO dao) {
        this.dao = dao;
    }

    public List<RegistroEstado> obtenerEstadoDeCuenta(String numeroCuenta) {
        List<Movimiento> movimientos = dao.listarMovimientosPorCuenta(numeroCuenta);
        List<RegistroEstado> estadoDeCuenta = new ArrayList<>();
        double saldoAcumulado = 0.0;
        
        // En un sistema real, el saldo inicial se carga desde la tabla Cuenta.
        Cuenta cuenta = dao.buscarCuenta(numeroCuenta);
        if (cuenta == null) return estadoDeCuenta;

        // Recuperar movimientos y calcular saldo acumulado
        for (Movimiento m : movimientos) {
            double cargo = 0.0;
            double abono = 0.0;
            
            if (m.getTipo().equals("ABONO")) {
                abono = m.getMonto();
                saldoAcumulado += abono;
            } else if (m.getTipo().equals("CARGO")) {
                cargo = m.getMonto();
                saldoAcumulado -= cargo;
            }
            
            estadoDeCuenta.add(new RegistroEstado(
                m.getFechaMovimiento(), 
                m.getConcepto(), 
                cargo, 
                abono, 
                saldoAcumulado
            ));
        }

        return estadoDeCuenta;
    }

    public void mostrarEstadoDeCuenta(String numeroCuenta, String nombreCliente) {
        System.out.println("\n----------------------------------------------------");
        System.out.println("         ESTADO DE CUENTA");
        System.out.println("----------------------------------------------------");
        System.out.println("Cliente: " + nombreCliente);
        System.out.println("Cuenta: " + numeroCuenta);
        System.out.println("----------------------------------------------------");
        System.out.printf("%-12s | %-25s | %-10s | %-10s | %-10s\n", 
            "Fecha", "Concepto", "Cargo", "Abono", "Saldo");
        System.out.println("----------------------------------------------------");

        List<RegistroEstado> registros = obtenerEstadoDeCuenta(numeroCuenta);
        
        for (RegistroEstado r : registros) {
            System.out.printf("%-12s | %-25s | %-10.2f | %-10.2f | %-10.2f\n", 
                r.getFecha(), 
                r.getConcepto(), 
                r.getCargo(), 
                r.getAbono(), 
                r.getSaldo());
        }
        System.out.println("----------------------------------------------------");
      
        Cuenta cuenta = dao.buscarCuenta(numeroCuenta);
        if (cuenta != null) {
            System.out.println("SALDO FINAL: " + String.format("%.2f", cuenta.getSaldoActual()));
        }
    }
}
