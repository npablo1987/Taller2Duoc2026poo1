package cl.duoc.personas;

public class Empleado extends  Persona {

    private String cargo;
    private double sueldo;

    public Empleado(String nombre, String rut, String correo, String telefono, String cargo, double sueldo) {
        super(nombre, rut, correo, telefono);
        this.cargo = cargo;
        this.sueldo = sueldo;
    }

    @Override
    public void mostrarInformacion() {
        System.out.println("Empleado:"+ nombre);
        System.out.println("Cargo:"+ cargo);
        System.out.println("Sueldo $:"+ sueldo);
    }

    public void atenderCliente(){
        System.out.println("Atendiendo cliente --->"+nombre);
    }

}
