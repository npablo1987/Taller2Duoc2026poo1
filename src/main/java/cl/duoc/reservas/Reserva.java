package cl.duoc.reservas;

import cl.duoc.hotel.Habitaciones;
import cl.duoc.interfaces.Reservable;
import cl.duoc.personas.Cliente;

public class Reserva implements Reservable {

    private Cliente cliente;
    private Habitaciones habitacion;
    private String fechaIngreso;
    private String fechaSalida;
    private int cantidadNoches;
    private boolean activa;


    public Reserva(Cliente cliente, Habitaciones habitacion, String fechaIngreso, String fechaSalida, int cantidadNoches) {
        this.cliente = cliente;
        this.habitacion = habitacion;
        this.fechaIngreso = fechaIngreso;
        this.fechaSalida = fechaSalida;
        this.cantidadNoches = cantidadNoches;
        this.activa = false;
    }

    @Override
    public void reservar() {
        if (habitacion.estaDisponible()){
            habitacion.reservar();
            activa = true;
            System.out.println("Reserva realizada correctamente");
        }else{
            System.out.println("No se puede realizar la reserva");
        }

    }

    @Override
    public void cancelarReserva() {
        habitacion.cancelarReserva();
        activa = false;
        System.out.println("Reserva cancelada correctamente");
    }

    @Override
    public boolean estaDisponible() {
        return habitacion.estaDisponible();
    }

    public void mostrarReserva(){
        System.out.println("Cliente:"+ cliente.getNombre());
        System.out.println("Habitacion:"+ habitacion.getNumero());
        System.out.println("Fecha de ingreso:"+ fechaIngreso);
        System.out.println("Fecha de salida:"+ fechaSalida);
        System.out.println("Cantidad de noches:"+ cantidadNoches);
        System.out.println("Activa:"+ activa);
        System.out.println("Costo total $"+ (habitacion.getPrecioPorNoche() * cantidadNoches));
        System.out.println("--------------------------------");
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Habitaciones getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(Habitaciones habitacion) {
        this.habitacion = habitacion;
    }

    public String getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(String fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(String fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public int getCantidadNoches() {
        return cantidadNoches;
    }

    public void setCantidadNoches(int cantidadNoches) {
        this.cantidadNoches = cantidadNoches;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }
}
