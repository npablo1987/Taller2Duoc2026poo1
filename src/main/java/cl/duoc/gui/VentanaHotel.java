package cl.duoc.gui;
import cl.duoc.hotel.Habitaciones;
import cl.duoc.hotel.Hotel;
import cl.duoc.personas.Cliente;
import cl.duoc.personas.Empleado;
import cl.duoc.reservas.Reserva;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class VentanaHotel extends JFrame {

    private final Hotel hotel;
    private final ArrayList<Cliente> clientes = new ArrayList<>();
    private final ArrayList<Empleado> empleados = new ArrayList<>();
    private final ArrayList<Reserva> reservas = new ArrayList<>();

    private JTextArea areaHotel;
    private JTextArea areaClientes;
    private JTextArea areHabitaciones;
    private JTextArea areaReservas;
    private JTextArea areaEmpleados;

    private JComboBox<String> comboCliente;
    private JComboBox<String> comboHabitacion;
    private JComboBox<String> comboReservaActivas;

    public VentanaHotel(){
        hotel = new Hotel("Hotel Duoc", "Direccion", "Ciudad", 5);
        hotel.agregarHabitacion(new Habitaciones(1, "Simple", 100.0, 2, true));
        hotel.agregarHabitacion(new Habitaciones(2, "Doble", 150.0, 4, true));
        hotel.agregarHabitacion(new Habitaciones(3, "Suite", 250.0, 6, true));
        hotel.agregarHabitacion(new Habitaciones(4, "Municipio", 250.0, 6, false));
        hotel.agregarHabitacion(new Habitaciones(5, "Municipio", 250.0, 6, false));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Hotel", crearPanelHotel());
        tabs.addTab("Clientes", crearPanelClientes());
        tabs.addTab("Habitaciones", crearPanelHabitaciones());
        tabs.addTab("Reservas", crearPanelReservas());
        tabs.addTab("Empleados", crearPanelEmpleados());
        add(tabs);

    }


    private JTextArea crearArea(){
        JTextArea area = new JTextArea();
        area.setEditable(false);
        return area;
    }

    private Component crearPanelEmpleados() {
        return null;
    }

    private Component crearPanelReservas() {
        return null;
    }

    private Component crearPanelHabitaciones() {
        JPanel panel = new JPanel(new BorderLayout());
        areHabitaciones = crearArea();

        JButton btnTodas = new JButton("Mostrar todas");
        JButton btnDisponibles = new JButton("Mostrar disponibles");

        btnTodas.addActionListener(e -> mostrarHabitaciones(false));
        btnDisponibles.addActionListener(e -> mostrarHabitaciones(true));

        JPanel botones = new JPanel();
        botones.add(btnTodas);
        botones.add(btnDisponibles);

        panel.add(new JScrollPane(areHabitaciones), BorderLayout.CENTER);
        panel.add(botones, BorderLayout.SOUTH);
        mostrarHabitaciones(false);
        return panel;

    }

    private void mostrarHabitaciones(boolean disponibles) {
        StringBuilder sb = new StringBuilder();
        for(Habitaciones h : hotel.getHabitaciones()){
            if( disponibles && !h.estaDisponible()){
                continue;
            }
            sb.append("N°").append(h.getNumero())
                    .append(" | ").append(h.getTipo())
                    .append(" | $").append(h.getPrecioPorNoche())
                    .append(" | Capacidad: ").append(h.getCapacidad())
                    .append(" | Disponible: ").append(h.isDisponible())
                    .append("\n");
        }
        areHabitaciones.setText(sb.toString());
    }


    private Component crearPanelClientes() {
        return null;
    }

    private Component crearPanelHotel() {
       JPanel panel = new JPanel(new BorderLayout());
       areaHotel = crearArea();
       JButton btnActualizar = new JButton("Actualizar");
       btnActualizar.addActionListener(e -> actualizarHotel());
       panel.add(new JScrollPane(areaHotel), BorderLayout.CENTER);
       panel.add(btnActualizar, BorderLayout.SOUTH);
       actualizarHotel();
       return panel;
    }

    private void actualizarHotel() {
        StringBuilder sb = new StringBuilder();
        sb.append("Hotel: ").append(hotel.getNombre()).append("\n");
        sb.append("Total Habitaciones: ").append(hotel.getHabitaciones().size()).append("\n");
        areaHotel.setText(sb.toString());


    }


}
