package cl.duoc.gui;

import cl.duoc.hotel.Habitaciones;
import cl.duoc.hotel.Hotel;
import cl.duoc.personas.Cliente;
import cl.duoc.personas.Empleado;
import cl.duoc.reservas.Reserva;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Interfaz gráfica simple (Swing) para el Sistema de Hotel y Reservas.
 * Reutiliza las mismas clases de modelo que la versión de consola.
 */
public class VentanaHotel extends JFrame {

    // Datos en memoria del sistema (equivalentes a los que manejaba SistemaHotel)
    private final Hotel hotel;
    private final ArrayList<Cliente> clientes = new ArrayList<>();
    private final ArrayList<Empleado> empleados = new ArrayList<>();
    private final ArrayList<Reserva> reservas = new ArrayList<>();

    // Áreas de texto (una por pestaña) donde se listan los datos
    private JTextArea areaHotel;
    private JTextArea areaClientes;
    private JTextArea areaHabitaciones;
    private JTextArea areaReservas;
    private JTextArea areaEmpleados;

    // Combos que se rellenan dinámicamente para crear/cancelar reservas
    private JComboBox<Cliente> comboClientes;
    private JComboBox<Habitaciones> comboHabitaciones;
    private JComboBox<Reserva> comboReservasActivas;

    public VentanaHotel() {
        super("Sistema de Hotel y Reservas");

        // Datos iniciales del hotel (mismos valores que tenía la versión de consola)
        hotel = new Hotel("Hotel Costa Sur", "Av. Principal 123", "Puerto Varas", 4);
        hotel.agregarHabitacion(new Habitaciones(101, "Simple", 45000, 1, true));
        hotel.agregarHabitacion(new Habitaciones(202, "Doble", 75000, 2, true));
        hotel.agregarHabitacion(new Habitaciones(303, "Suite", 120000, 4, true));
        hotel.agregarHabitacion(new Habitaciones(404, "Deluxe", 150000, 2, true));
        empleados.add(new Empleado("María González", "11.111.111-1", "maria@hotel.cl", "912345678", "Recepcionista", 750000));

        // Configuración básica de la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null); // centra la ventana en la pantalla

        // Una pestaña por módulo del sistema
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Hotel", crearPanelHotel());
        tabs.addTab("Clientes", crearPanelClientes());
        tabs.addTab("Habitaciones", crearPanelHabitaciones());
        tabs.addTab("Reservas", crearPanelReservas());
        tabs.addTab("Empleados", crearPanelEmpleados());
        add(tabs);
    }

    // Crea un área de texto de solo lectura, usada para mostrar listados
    private JTextArea crearArea() {
        JTextArea area = new JTextArea();
        area.setEditable(false);
        return area;
    }

    // ---------- Hotel ----------
    // Pestaña con la info general del hotel y un botón para refrescarla
    private JPanel crearPanelHotel() {
        JPanel panel = new JPanel(new BorderLayout());
        areaHotel = crearArea();
        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.addActionListener(e -> actualizarHotel());
        panel.add(new JScrollPane(areaHotel), BorderLayout.CENTER);
        panel.add(btnActualizar, BorderLayout.SOUTH);
        actualizarHotel(); // carga los datos apenas se crea la pestaña
        return panel;
    }

    // Vuelca el nombre del hotel y el total de habitaciones en el área de texto
    private void actualizarHotel() {
        StringBuilder sb = new StringBuilder();
        sb.append("Hotel: ").append(hotel.getNombre()).append("\n");
        sb.append("Total de habitaciones: ").append(hotel.getHabitaciones().size()).append("\n");
        areaHotel.setText(sb.toString());
    }

    // ---------- Clientes ----------
    // Pestaña con formulario para agregar clientes y listado de los ya registrados
    private JPanel crearPanelClientes() {
        JPanel panel = new JPanel(new BorderLayout());

        // Campos del formulario
        JTextField txtNombre = new JTextField();
        JTextField txtRut = new JTextField();
        JTextField txtCorreo = new JTextField();
        JTextField txtTelefono = new JTextField();
        JComboBox<String> comboTipo = new JComboBox<>(new String[]{"Regular", "Premium"});

        // Grilla de 5 filas (label + campo) para el formulario
        JPanel form = new JPanel(new GridLayout(5, 2, 5, 5));
        form.add(new JLabel("Nombre:"));
        form.add(txtNombre);
        form.add(new JLabel("RUT:"));
        form.add(txtRut);
        form.add(new JLabel("Correo:"));
        form.add(txtCorreo);
        form.add(new JLabel("Teléfono:"));
        form.add(txtTelefono);
        form.add(new JLabel("Tipo cliente:"));
        form.add(comboTipo);

        // Al agregar: valida campos obligatorios, crea el Cliente, limpia el formulario y refresca la lista
        JButton btnAgregar = new JButton("Agregar cliente");
        btnAgregar.addActionListener(e -> {
            if (txtNombre.getText().isBlank() || txtRut.getText().isBlank()) {
                JOptionPane.showMessageDialog(this, "Nombre y RUT son obligatorios.");
                return;
            }
            clientes.add(new Cliente(txtNombre.getText(), txtRut.getText(), txtCorreo.getText(),
                    txtTelefono.getText(), 0, (String) comboTipo.getSelectedItem()));
            txtNombre.setText("");
            txtRut.setText("");
            txtCorreo.setText("");
            txtTelefono.setText("");
            actualizarClientes();
        });

        JPanel norte = new JPanel(new BorderLayout());
        norte.add(form, BorderLayout.CENTER);
        norte.add(btnAgregar, BorderLayout.SOUTH);

        areaClientes = crearArea();
        panel.add(norte, BorderLayout.NORTH);
        panel.add(new JScrollPane(areaClientes), BorderLayout.CENTER);
        actualizarClientes();
        return panel;
    }

    // Redibuja el listado de clientes y sincroniza el combo usado en la pestaña Reservas
    private void actualizarClientes() {
        StringBuilder sb = new StringBuilder();
        if (clientes.isEmpty()) {
            sb.append("No hay clientes registrados.");
        } else {
            for (Cliente c : clientes) {
                sb.append("Nombre: ").append(c.getNombre())
                        .append(" | RUT: ").append(c.getRut())
                        .append(" | Tipo: ").append(c.getTipoCliente())
                        .append("\n");
            }
        }
        areaClientes.setText(sb.toString());
        // comboClientes se crea recién en la pestaña Reservas; puede no existir aún
        // la primera vez que se llama a este método desde crearPanelClientes()
        if (comboClientes != null) {
            comboClientes.removeAllItems();
            for (Cliente c : clientes) {
                comboClientes.addItem(c);
            }
        }
    }

    // ---------- Habitaciones ----------
    // Pestaña de solo lectura con botones para listar todas o solo las disponibles
    private JPanel crearPanelHabitaciones() {
        JPanel panel = new JPanel(new BorderLayout());
        areaHabitaciones = crearArea();

        JButton btnTodas = new JButton("Listar todas");
        JButton btnDisponibles = new JButton("Listar disponibles");
        btnTodas.addActionListener(e -> mostrarHabitaciones(false));
        btnDisponibles.addActionListener(e -> mostrarHabitaciones(true));

        JPanel botones = new JPanel();
        botones.add(btnTodas);
        botones.add(btnDisponibles);

        panel.add(new JScrollPane(areaHabitaciones), BorderLayout.CENTER);
        panel.add(botones, BorderLayout.SOUTH);
        mostrarHabitaciones(false); // por defecto muestra todas
        return panel;
    }

    // Lista las habitaciones del hotel; si soloDisponibles es true, filtra las ocupadas
    private void mostrarHabitaciones(boolean soloDisponibles) {
        StringBuilder sb = new StringBuilder();
        for (Habitaciones h : hotel.getHabitaciones()) {
            if (soloDisponibles && !h.estaDisponible()) {
                continue;
            }
            sb.append("N°").append(h.getNumero())
                    .append(" | ").append(h.getTipo())
                    .append(" | $").append(h.getPrecioPorNoche())
                    .append(" | Capacidad: ").append(h.getCapacidad())
                    .append(" | Disponible: ").append(h.estaDisponible())
                    .append("\n");
        }
        areaHabitaciones.setText(sb.toString());
    }

    // ---------- Reservas ----------
    // Pestaña para crear una reserva (cliente + habitación + fechas) y cancelar reservas activas
    private JPanel crearPanelReservas() {
        JPanel panel = new JPanel(new BorderLayout());

        // Combo de clientes se llena en actualizarClientes(); el de habitaciones es fijo (no cambia la lista)
        comboClientes = new JComboBox<>();
        comboHabitaciones = new JComboBox<>();
        for (Habitaciones h : hotel.getHabitaciones()) {
            comboHabitaciones.addItem(h);
        }
        JTextField txtEntrada = new JTextField();
        JTextField txtSalida = new JTextField();
        JSpinner spinnerNoches = new JSpinner(new SpinnerNumberModel(1, 1, 30, 1));

        JPanel form = new JPanel(new GridLayout(5, 2, 5, 5));
        form.add(new JLabel("Cliente:"));
        form.add(comboClientes);
        form.add(new JLabel("Habitación:"));
        form.add(comboHabitaciones);
        form.add(new JLabel("Fecha entrada (dd-mm-yyyy):"));
        form.add(txtEntrada);
        form.add(new JLabel("Fecha salida (dd-mm-yyyy):"));
        form.add(txtSalida);
        form.add(new JLabel("Cantidad de noches:"));
        form.add(spinnerNoches);

        // Al crear: valida selección y disponibilidad, reserva la habitación y agrega la Reserva a la lista
        JButton btnCrear = new JButton("Crear reserva");
        btnCrear.addActionListener(e -> {
            Cliente cliente = (Cliente) comboClientes.getSelectedItem();
            Habitaciones habitacion = (Habitaciones) comboHabitaciones.getSelectedItem();
            if (cliente == null || habitacion == null) {
                JOptionPane.showMessageDialog(this, "Debe existir al menos un cliente y una habitación.");
                return;
            }
            if (!habitacion.estaDisponible()) {
                JOptionPane.showMessageDialog(this, "La habitación no está disponible.");
                return;
            }
            Reserva reserva = new Reserva(cliente, habitacion, txtEntrada.getText(), txtSalida.getText(),
                    (Integer) spinnerNoches.getValue());
            reserva.reservar(); // marca la habitación como no disponible
            reservas.add(reserva);
            actualizarReservas();
            mostrarHabitaciones(false); // refleja que la habitación ya no está disponible
            JOptionPane.showMessageDialog(this, "Reserva creada exitosamente.");
        });

        // Combo con solo las reservas activas, para poder cancelarlas
        comboReservasActivas = new JComboBox<>();
        JButton btnCancelar = new JButton("Cancelar reserva seleccionada");
        btnCancelar.addActionListener(e -> {
            Reserva reserva = (Reserva) comboReservasActivas.getSelectedItem();
            if (reserva != null) {
                reserva.cancelarReserva(); // libera la habitación
                actualizarReservas();
            }
        });

        JPanel norte = new JPanel(new BorderLayout());
        norte.add(form, BorderLayout.CENTER);
        norte.add(btnCrear, BorderLayout.SOUTH);

        JPanel sur = new JPanel(new BorderLayout());
        sur.add(comboReservasActivas, BorderLayout.CENTER);
        sur.add(btnCancelar, BorderLayout.EAST);

        areaReservas = crearArea();
        panel.add(norte, BorderLayout.NORTH);
        panel.add(new JScrollPane(areaReservas), BorderLayout.CENTER);
        panel.add(sur, BorderLayout.SOUTH);
        actualizarReservas();
        return panel;
    }

    // Redibuja el listado de reservas y reconstruye el combo de reservas activas (para cancelar)
    private void actualizarReservas() {
        StringBuilder sb = new StringBuilder();
        comboReservasActivas.removeAllItems();
        if (reservas.isEmpty()) {
            sb.append("No hay reservas registradas.");
        } else {
            for (Reserva r : reservas) {
                sb.append("Cliente: ").append(r.getCliente().getNombre())
                        .append(" | Habitación: ").append(r.getHabitacion().getNumero())
                        .append(" | Activa: ").append(r.isActiva())
                        .append("\n");
                if (r.isActiva()) {
                    comboReservasActivas.addItem(r);
                }
            }
        }
        areaReservas.setText(sb.toString());
    }

    // ---------- Empleados ----------
    // Pestaña con formulario para agregar empleados y listado de los ya registrados
    private JPanel crearPanelEmpleados() {
        JPanel panel = new JPanel(new BorderLayout());

        JTextField txtNombre = new JTextField();
        JTextField txtRut = new JTextField();
        JTextField txtCorreo = new JTextField();
        JTextField txtTelefono = new JTextField();
        JTextField txtCargo = new JTextField();
        JTextField txtSueldo = new JTextField();

        JPanel form = new JPanel(new GridLayout(6, 2, 5, 5));
        form.add(new JLabel("Nombre:"));
        form.add(txtNombre);
        form.add(new JLabel("RUT:"));
        form.add(txtRut);
        form.add(new JLabel("Correo:"));
        form.add(txtCorreo);
        form.add(new JLabel("Teléfono:"));
        form.add(txtTelefono);
        form.add(new JLabel("Cargo:"));
        form.add(txtCargo);
        form.add(new JLabel("Sueldo:"));
        form.add(txtSueldo);

        // Al agregar: valida obligatorios y que el sueldo sea numérico, crea el Empleado y limpia el formulario
        JButton btnAgregar = new JButton("Agregar empleado");
        btnAgregar.addActionListener(e -> {
            if (txtNombre.getText().isBlank() || txtRut.getText().isBlank()) {
                JOptionPane.showMessageDialog(this, "Nombre y RUT son obligatorios.");
                return;
            }
            double sueldo;
            try {
                sueldo = Double.parseDouble(txtSueldo.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Sueldo inválido.");
                return;
            }
            empleados.add(new Empleado(txtNombre.getText(), txtRut.getText(), txtCorreo.getText(),
                    txtTelefono.getText(), txtCargo.getText(), sueldo));
            txtNombre.setText("");
            txtRut.setText("");
            txtCorreo.setText("");
            txtTelefono.setText("");
            txtCargo.setText("");
            txtSueldo.setText("");
            actualizarEmpleados();
        });

        JPanel norte = new JPanel(new BorderLayout());
        norte.add(form, BorderLayout.CENTER);
        norte.add(btnAgregar, BorderLayout.SOUTH);

        areaEmpleados = crearArea();
        panel.add(norte, BorderLayout.NORTH);
        panel.add(new JScrollPane(areaEmpleados), BorderLayout.CENTER);
        actualizarEmpleados();
        return panel;
    }

    // Redibuja el listado de empleados registrados
    private void actualizarEmpleados() {
        StringBuilder sb = new StringBuilder();
        for (Empleado emp : empleados) {
            sb.append("Nombre: ").append(emp.getNombre())
                    .append(" | Cargo: ").append(emp.getCargo())
                    .append(" | Sueldo: $").append(emp.getSueldo())
                    .append("\n");
        }
        areaEmpleados.setText(sb.toString());
    }

    // Punto de entrada alternativo: permite ejecutar esta clase directamente además de cl.duoc.Main
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaHotel().setVisible(true));
    }
}
