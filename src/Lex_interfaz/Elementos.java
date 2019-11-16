/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lex_interfaz;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import javax.swing.JFileChooser;

/**
 *
 * @author Usuario iTC
 */
public class Elementos {

    File archivo, csv;
    EstadosCSV obj_csv;
    DefaultTableModel tabla;
    Object[] inf = new Object[2];
    Object[] infcsv = new Object[3];
    List<LexCSV> lexcsv;
    List<Token> token;
    StringBuffer valor, sb;
    int cont = 0;
    int z = 0, linea = 1;
    Vector notificacion;
    Vector estados;

    public Elementos() {

    }

    public void abrirArchivo() {
        try {
            JFileChooser jf = new JFileChooser();
            jf.showOpenDialog(jf);
            archivo = jf.getSelectedFile();
        } catch (Exception e) {
            System.out.println("Error abrirArchivo: " + e);
        }
    }

    public void abrirCSV() {

        while (tabla.getRowCount() > 0) {
            tabla.removeRow(0);
        }
        try {
            JFileChooser jf = new JFileChooser();
            jf.showOpenDialog(jf);
            csv = jf.getSelectedFile();
        } catch (Exception e) {
            System.out.println("Error abrirCSV: " + e);
        }

    }

    public String leerArchivo() {
        String caracter = "";
        String texto = "";
        FileReader fr;
        BufferedReader br;
        try {
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);
            if (archivo != null) {
                while ((caracter = br.readLine()) != null) {
                    texto += caracter + "\n";
                }
            }
        } catch (Exception e) {
            System.out.println("Error leerArchivo: " + e);
        }
        return texto;
    }

    public void lexema() {
        getCsv();
        estados();
        getTexto();
        cargarCSV();
        recorrer();
    }

    public void getCsv() {
        lexcsv = new ArrayList<>();
        FileReader frcsv;
        BufferedReader brcsv;
        String[] dato;
        try {
            frcsv = new FileReader(csv);
            brcsv = new BufferedReader(frcsv);
            String linea;
            while ((linea = brcsv.readLine()) != null) {
                dato = linea.split(";");
                lexcsv.add(new LexCSV(dato[0], dato[1], dato[2]));
            }
        } catch (Exception e) {
            System.out.println("Error getCsv: " + e);
        }
    }

    public void getTexto() {
        String caracter = null;
        FileReader fr;
        BufferedReader br;
        try {
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);
            sb = new StringBuffer();
            while ((caracter = br.readLine()) != null) {
                sb.append(caracter + "\n");
            }
        } catch (Exception e) {
            System.out.println("Error getTexto: " + e);
        }
    }

    public void recorrer() {

        try {
            char d;
            Vector numes = new Vector();
            notificacion = new Vector();
            token = new ArrayList<>();
            valor = new StringBuffer();
            int b = 0, q = estados.size();
            sb.append(" ");
            while ((d = sig_Caracter()) != ' ') {
                while (d != '.') {
                    b = mover(String.valueOf(b), String.valueOf(d));
                    valor.append(d);
                    d = (char) sig_Caracter();
                    numes.add(b);
                }
                if (!numes.contains(estados.size() - q)) {
                    token.add(new Token("Identificador", valor.toString()));
                    valor = new StringBuffer();
                    b = 0;
                    numes = new Vector();
                } else {
                    notificacion.addElement(notificacion());
                    token.add(new Token("Error", valor.toString()));
                    valor = new StringBuffer();
                    b = 0;
                    numes = new Vector();
                }
            }
            cargarTabla();
        } catch (Exception e) {
            System.out.println("Error recorrer: " + e);
        }
    }

    public int mover(String estado, String caracter) {
        int sig = 0;
        for (int i = 0; i < lexcsv.size(); i++) {
            String inicio = lexcsv.get(i).inicio;
            String atributo = lexcsv.get(i).atributo;
            String fin = lexcsv.get(i).fin;
            if (inicio.equals(estado) && atributo.equals(caracter)) {
                sig = Integer.parseInt(fin);
            }
        }
        return sig;
    }

    public char sig_Caracter() {
        char c = sb.charAt(cont);
        cont++;
        z++;
        if (c == '\n') {
            z = 0;
            linea++;
            c = sb.charAt(cont);
            cont++;
        }
        return c;
    }

    public Vector estados() {
        estados = new Vector();
        for (int i = 0; i < lexcsv.size(); i++) {
            String inicio = lexcsv.get(i).inicio;
            if (!estados.contains(inicio)) {
                estados.addElement(inicio);
            }
        }
        return estados;
    }

    public void cargarTabla() {
        try {
            for (int a = 0; a < token.size(); a++) {
                inf[0] = token.get(a).ID;
                inf[1] = token.get(a).Valor;
                tabla.addRow(inf);
            }
        } catch (Exception e) {
            System.out.println("Error cargarTabla: " + e);
        }

    }

    public void cargarCSV() {
        try {
            obj_csv = new EstadosCSV();
            obj_csv.setVisible(true);
            obj_csv.tablacompleta = (DefaultTableModel) obj_csv.tablaCsv.getModel();

            for (int a = 0; a < lexcsv.size(); a++) {
                infcsv[0] = lexcsv.get(a).inicio;
                infcsv[1] = lexcsv.get(a).atributo;
                infcsv[2] = lexcsv.get(a).fin;
                obj_csv.tablacompleta.addRow(infcsv);
            }
        } catch (Exception e) {
            System.out.println("Error cargarCSV: " + e);
        }

    }

    public String notificacion() {
        String Notif = "ERROR: linea " + linea + ", columna " + z;
        return Notif;
    }

    public void limpiar() {
        archivo = null;
        csv = null;
        lexcsv = null;
        token = null;
        valor = null;
        sb = null;
        cont = 0;
        z = 0;
        linea = 1;
        notificacion = null;
        estados = null;
    }
}
