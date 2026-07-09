package cl.duoc.hotel;

import java.util.ArrayList;

public class Hotel {
    private String nombre;
    private String direccion;
    private String ciudad;
    private int estrellas;
    private ArrayList<Habitaciones> habitaciones;

    public Hotel(String nombre, String direccion, String ciudad, int estrellas) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.estrellas = estrellas;
        this.habitaciones = new ArrayList<>();
    }

    public void agregarHabitacion(Habitaciones habitacion) {
        habitaciones.add(habitacion);
    }

    public void mostrarHotel(){
        System.out.println("Hotel:"+nombre);
        System.out.println("Direccion:"+direccion);
        System.out.println("Ciudad:"+ciudad);
        System.out.println("Estrellas:"+estrellas);
    }

    public void listarHabitaciones(){
        for (Habitaciones habitacion : habitaciones) {
            habitacion.mostrarHabitacion();
        }
    }

    public void listarHabitacionesDisponibles(){
        for (Habitaciones habitacion : habitaciones) {
            if (habitacion.isDisponible()) {
                habitacion.mostrarHabitacion();
            }
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public int getEstrellas() {
        return estrellas;
    }

    public void setEstrellas(int estrellas) {
        this.estrellas = estrellas;
    }

    public ArrayList<Habitaciones> getHabitaciones() {
        return habitaciones;
    }

    public void setHabitaciones(ArrayList<Habitaciones> habitaciones) {
        this.habitaciones = habitaciones;
    }
}

