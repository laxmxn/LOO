import java.time.LocalDate;
public class RegistroEstado {
    private LocalDate fecha;
    private String concepto;
    private double cargo;
    private double abono;
    private double saldo;

    public RegistroEstado(LocalDate fecha, String concepto, double cargo, double abono, double saldo) {
        this.fecha = fecha;
        this.concepto = concepto;
        this.cargo = cargo;
        this.abono = abono;
        this.saldo = saldo;
    }

    public LocalDate getFecha() { return fecha; }
    public String getConcepto() { return concepto; }
    public double getCargo() { return cargo; }
    public double getAbono() { return abono; }
    public double getSaldo() { return saldo; }
}
