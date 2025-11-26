public class Autenticacion {
    // Simulación de la tabla de usuarios
    private static final String USUARIO_VALIDO = "admin";
    private static final String PASSWORD_VALIDA = "12345";

    public boolean validarCredenciales(String usuario, String password) {
        return USUARIO_VALIDO.equals(usuario) && PASSWORD_VALIDA.equals(password);
    }
}
