package cl.duoc.hotel;

import cl.duoc.interfaces.Reservable;

public class Habitaciones implements Reservable {

    private int numero;
    private String tipo;
    private double precioPorNoche;
    private int capacidad;
    private boolean disponible;

    public Habitaciones(int numero, String tipo, double precioPorNoche, int capacidad, boolean disponible) {
        this.numero = numero;
        this.tipo = tipo;
        this.precioPorNoche = precioPorNoche;
        this.capacidad = capacidad;
        this.disponible = disponible;
    }

    @Override
    public void reservar() {
        if (disponible){
            disponible = false;
            System.out.println("Habitacion "+ numero + " reservada correctamente");
        }else{
            System.out.println("Habitacion "+ numero + " no disponible");
        }
    }

    @Override
    public void cancelarReserva() {
        disponible = true;
        System.out.println("Habitacion "+ numero + " cancelada correctamente");

    }

    @Override
    public boolean estaDisponible() {
        return disponible;
    }

    public void mostrarHabitacion(){
        System.out.println("Habitacion n°"+numero);
        System.out.println("Tipo:"+tipo);
        System.out.println("Precio por noche:$"+precioPorNoche);
        System.out.println("Capacidad:"+capacidad);
        System.out.println("Disponible:"+disponible);
        System.out.println("--------------------------------");
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getPrecioPorNoche() {
        return precioPorNoche;
    }

    public void setPrecioPorNoche(double precioPorNoche) {
        this.precioPorNoche = precioPorNoche;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
}
