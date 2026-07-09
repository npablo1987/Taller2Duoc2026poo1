package cl.duoc.personas;

public class Cliente  extends Persona{

    private int puntosFidelidad;
    private String tipoCliente;

    public Cliente(String nombre, String rut, String correo, String telefono, int puntosFidelidad, String tipoCliente) {
        super(nombre, rut, correo, telefono);
        this.puntosFidelidad = puntosFidelidad;
        this.tipoCliente = tipoCliente;
    }

    @Override
    public void mostrarInformacion() {
        System.out.println("Cliente:"+ nombre);
        System.out.println("Rut:"+ rut);
        System.out.println("Correo:"+ correo);
        System.out.println("Telefono:"+ telefono);
        System.out.println("Puntos de Fidelidad:"+ puntosFidelidad);
        System.out.println("Tipo de Cliente:"+ tipoCliente);
    }

    public void aplicarDescuento() {
        System.out.println(nombre+ " tiene un descuento del 10% por fidelidad.");
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public int getPuntosFidelidad() {
        return puntosFidelidad;
    }

    public void setPuntosFidelidad(int puntosFidelidad) {
        this.puntosFidelidad = puntosFidelidad;
    }
}
