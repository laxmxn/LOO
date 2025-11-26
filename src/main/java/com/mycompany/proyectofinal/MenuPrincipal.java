import java.util.Scanner;
import java.time.LocalDate;
import java.util.List;

public class MenuPrincipal {
    private static BancoDAO dao = new BancoDAO();
    private static Scanner scanner = new Scanner(System.in);
    private static Consulta consulta = new Consulta(dao);
    private static Cliente clienteActual = null;

    public static void main(String[] args) {
        if (autenticarUsuario()) {
            menuCliente();
        } else {
            System.out.println("Autenticación fallida. Cerrando sistema.");
        }
    }
    
    private static boolean autenticarUsuario() {
        Autenticacion auth = new Autenticacion();
        System.out.println("--- AUTENTICACIÓN ---");
        System.out.print("Usuario: ");
        String user = scanner.nextLine();
        System.out.print("Contraseña: ");
        String pass = scanner.nextLine();
        
        return auth.validarCredenciales(user, pass);
    }
    
    private static void menuCliente() {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n--- MENU PRINCIPAL ---");
            if (clienteActual != null) {
                System.out.println("CLIENTE SELECCIONADO: " + clienteActual.getNombre());
            } else {
                System.out.println("CLIENTE SELECCIONADO: NINGUNO");
            }
            
            System.out.println("1. Seleccionar Cliente");
            System.out.println("2. Realizar Movimiento (Cargo/Abono)");
            System.out.println("3. Consultar Estado de Cuenta");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");
            
            try {
                int opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 1: seleccionarCliente(); break;
                    case 2: realizarMovimiento(); break;
                    case 3: consultarEstadoDeCuenta(); break;
                    case 4: salir = true; System.out.println("¡Hasta pronto!"); break;
                    default: System.out.println("Opción no válida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("ERROR: Ingrese un número válido.");
            }
        }
    }

    private static void seleccionarCliente() {
        System.out.print("Ingrese el Número de Cliente (Ej. 12345): ");
        try {
            int numCliente = Integer.parseInt(scanner.nextLine());
            Cliente cliente = dao.buscarCliente(numCliente);
            if (cliente != null) {
                clienteActual = cliente;
                System.out.println("Cliente seleccionado: " + cliente.getNombre());
            } else {
                System.out.println("Cliente no encontrado.");
            }
        } catch (NumberFormatException e) {
            System.out.println("ERROR: El número de cliente debe ser numérico.");
        }
    }
    
    private static Cuenta seleccionarCuenta(int numeroCliente) {
        List<Cuenta> cuentas = dao.listarCuentasPorCliente(numeroCliente);
        if (cuentas.isEmpty()) {
            System.out.println("El cliente no tiene cuentas registradas.");
            return null;
        }

        System.out.println("\n--- SELECCIÓN DE CUENTA ---");
        // Simulación de JComboBox
        for (int i = 0; i < cuentas.size(); i++) {
            System.out.println((i + 1) + ". " + cuentas.get(i).getNumeroCuenta() 
                + " (" + dao.buscarBanco(cuentas.get(i).getIdBanco()).getNombre() + ")");
        }
        
        System.out.print("Seleccione el número de cuenta de la lista: ");
        try {
            int indice = Integer.parseInt(scanner.nextLine()) - 1;
            if (indice >= 0 && indice < cuentas.size()) {
                return cuentas.get(indice);
            } else {
                System.out.println("Selección fuera de rango.");
                return null;
            }
        } catch (NumberFormatException e) {
            System.out.println("ERROR: Ingrese un número de la lista.");
            return null;
        }
    }

    private static void realizarMovimiento() {
        if (clienteActual == null) {
            System.out.println("ERROR: Primero debe seleccionar un cliente (Opción 1).");
            return;
        }
        
        Cuenta cuenta = seleccionarCuenta(clienteActual.getNumeroCliente());
        if (cuenta == null) return;

        try {
            System.out.print("Tipo (1=ABONO / 2=CARGO): ");
            String tipoMov = scanner.nextLine().equals("1") ? "ABONO" : "CARGO";
            
            System.out.print("Monto (Ej. 100.00): ");
            double monto = Double.parseDouble(scanner.nextLine());
            
            System.out.print("Concepto: ");
            String concepto = scanner.nextLine();
            
            // Validación de monto
            if (monto <= 0) {
                 System.out.println("ERROR: El monto debe ser positivo.");
                 return;
            }
          
            // Creación y registro del movimiento (ID se asigna en DAO)
            Movimiento nuevoMov = new Movimiento(
                0, 
                cuenta.getNumeroCuenta(), 
                LocalDate.now(), // Uso de la clase Fecha
                concepto, 
                monto, 
                tipoMov
            );
            
            dao.registrarMovimiento(nuevoMov);
            System.out.println("\n✅ Movimiento de " + tipoMov + " registrado con éxito.");
            System.out.println("Nuevo saldo: " + String.format("%.2f", dao.buscarCuenta(cuenta.getNumeroCuenta()).getSaldoActual()));

        } catch (NumberFormatException e) {
            System.out.println("ERROR: Ingrese un monto numérico válido.");
        }
    }
    
    private static void consultarEstadoDeCuenta() {
        if (clienteActual == null) {
            System.out.println("ERROR: Primero debe seleccionar un cliente (Opción 1).");
            return;
        }
        
        Cuenta cuenta = seleccionarCuenta(clienteActual.getNumeroCliente());
        if (cuenta == null) return;
        
        consulta.mostrarEstadoDeCuenta(cuenta.getNumeroCuenta(), clienteActual.getNombre());
    }
}
