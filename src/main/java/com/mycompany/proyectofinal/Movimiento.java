import java.time.LocalDate;

public class Movimiento {
    private int idMovimiento;
    private String numeroCuenta;
    private LocalDate fechaMovimiento;
    private String concepto;
    private double monto;
    private String tipo;

    public Movimiento(int idMovimiento, String numeroCuenta, LocalDate fechaMovimiento, String concepto, double monto, String tipo) {
        this.idMovimiento = idMovimiento;
        this.numeroCuenta = numeroCuenta;
        this.fechaMovimiento = fechaMovimiento;
        this.concepto = concepto;
        this.monto = monto;
        this.tipo = tipo;
    }
    public int getIdMovimiento() { return idMovimiento; }
    public String getNumeroCuenta() { return numeroCuenta; }
    public LocalDate getFechaMovimiento() { return fechaMovimiento; }
    public String getConcepto() { return concepto; }
    public double getMonto() { return monto; }
    public String getTipo() { return tipo; }
}
