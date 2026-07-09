package cl.duoc.sistema;

import cl.duoc.gui.VentanaHotel;
import cl.duoc.personas.Persona;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VentanaHotel().setVisible(true);
        });

    }
}