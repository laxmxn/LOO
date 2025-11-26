public class Cuenta {
    private String numeroCuenta;
    private int numeroCliente;
    private int idBanco;
    private double saldoActual;

    public Cuenta(String numeroCuenta, int numeroCliente, int idBanco, double saldoActual) {
        this.numeroCuenta = numeroCuenta;
        this.numeroCliente = numeroCliente;
        this.idBanco = idBanco;
        this.saldoActual = saldoActual;
    }
    public String getNumeroCuenta() { return numeroCuenta; }
    public int getNumeroCliente() { return numeroCliente; }
    public int getIdBanco() { return idBanco; }
    public double getSaldoActual() { return saldoActual; }

    public void setSaldoActual(double saldoActual) { this.saldoActual = saldoActual; }
    
    @Override
    public String toString() {
        return "Cuenta: " + numeroCuenta + " (Saldo: " + String.format("%.2f", saldoActual) + ")";
    }
}
