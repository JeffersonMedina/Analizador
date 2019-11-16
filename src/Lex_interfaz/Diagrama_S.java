/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lex_interfaz;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Usuario iTC
 */
public class Diagrama_S {

    NewLex obj_fichero;
    List<SintCSV> sintacticocsv;
    List<LexCSV> estadoSintactico;
    Object[] infsint = new Object[12];
    Tabla_Sintactica obj_sintactico;
    Stack<String> entrada;
    List<Token> token2;
    Stack<String> pila;
    int filas, columans;

    public Diagrama_S() {      
    }

    public void tabla_Sint() {
        File csv;

        FileReader frcsv;
        BufferedReader brcsv;
        String[] dato;
        try {
            sintacticocsv = new ArrayList<>();

            JFileChooser jf = new JFileChooser();
            jf.showOpenDialog(jf);
            csv = jf.getSelectedFile();

            frcsv = new FileReader(csv);
            brcsv = new BufferedReader(frcsv);
            String linea;
            while ((linea = brcsv.readLine()) != null) {
                filas++;
                dato = linea.split(";");
                columans = dato.length;
                sintacticocsv.add(new SintCSV(dato[0], dato[1], dato[2], dato[3], dato[4], dato[5], dato[6], dato[7], dato[8], dato[9], dato[10], dato[11]));
            }
        } catch (Exception e) {
            System.out.println("Error tabla_Sint: " + e);
        }
    }

    public void cargarTablaSint() {
        try {
            obj_sintactico = new Tabla_Sintactica();
            obj_sintactico.setVisible(true);
            obj_sintactico.sintactica_completa = (DefaultTableModel) obj_sintactico.tabla_sintactica.getModel();

            for (int a = 0; a < sintacticocsv.size(); a++) {
                infsint[0] = sintacticocsv.get(a).Simbolo;
                infsint[1] = sintacticocsv.get(a).MAS;
                infsint[2] = sintacticocsv.get(a).MENOS;
                infsint[3] = sintacticocsv.get(a).POR;
                infsint[4] = sintacticocsv.get(a).DIV;
                infsint[5] = sintacticocsv.get(a).ID;
                infsint[6] = sintacticocsv.get(a).NUM;
                infsint[7] = sintacticocsv.get(a).PAC_A;
                infsint[8] = sintacticocsv.get(a).PAR_C;
                infsint[9] = sintacticocsv.get(a).POT;
                infsint[10] = sintacticocsv.get(a).MOD;
                infsint[11] = sintacticocsv.get(a).FIN;
                obj_sintactico.sintactica_completa.addRow(infsint);
            }
        } catch (Exception e) {
            System.out.println("Error cargarTablaSint: " + e);
        }
    }

    public void matrizSintactica() {
        String MAS = "+", MENOR = "-", POR = "*", DIV = "/", ID = "id", NUM = "num", PAC_A = "(", PAR_C = ")", POT = "pot(num,num)", MOD = "mod(num,num)", FIN = "$";
        String V;
        try {
            estadoSintactico = new ArrayList<>();

            for (int i = 0; i < sintacticocsv.size(); i++) {
                estadoSintactico.add(new LexCSV(sintacticocsv.get(i).Simbolo, MAS, sintacticocsv.get(i).MAS));
                estadoSintactico.add(new LexCSV(sintacticocsv.get(i).Simbolo, MENOR, sintacticocsv.get(i).MENOS));
                estadoSintactico.add(new LexCSV(sintacticocsv.get(i).Simbolo, POR, sintacticocsv.get(i).POR));
                estadoSintactico.add(new LexCSV(sintacticocsv.get(i).Simbolo, DIV, sintacticocsv.get(i).DIV));
                estadoSintactico.add(new LexCSV(sintacticocsv.get(i).Simbolo, ID, sintacticocsv.get(i).ID));
                estadoSintactico.add(new LexCSV(sintacticocsv.get(i).Simbolo, NUM, sintacticocsv.get(i).NUM));
                estadoSintactico.add(new LexCSV(sintacticocsv.get(i).Simbolo, PAC_A, sintacticocsv.get(i).PAC_A));
                estadoSintactico.add(new LexCSV(sintacticocsv.get(i).Simbolo, PAR_C, sintacticocsv.get(i).PAR_C));
                estadoSintactico.add(new LexCSV(sintacticocsv.get(i).Simbolo, POT, sintacticocsv.get(i).POT));
                estadoSintactico.add(new LexCSV(sintacticocsv.get(i).Simbolo, MOD, sintacticocsv.get(i).MOD));
                estadoSintactico.add(new LexCSV(sintacticocsv.get(i).Simbolo, FIN, sintacticocsv.get(i).FIN));
            }
//            entrada();
//            V = sig_entrada();
            System.out.println("Token: "+obj_fichero.token);
        } catch (Exception e) {
            System.out.println("Error matrizSintactica: " + e);
        }
    }

    public void entrada() {

        try {
            entrada = new Stack<String>();
            for (int i = 0; i < obj_fichero.token.size(); i++) {
                entrada.push(obj_fichero.token.get(i).ID);
            }
            entrada.push("$");
        } catch (Exception e) {
            System.out.println("Error entrada: " + e);
        }
    }

    public void recorrer_sintactico() {
        String[] nodo = {"E","E'","T","T'","F","P","M"};
        String ent;
        try {
            entrada();
            pila.push("$");
            pila.push("E");
            while ((ent=entrada.peek()) != "") {                
                while (ent != "$") {                    
                    //b = pila.peek();
                }
            }
            
        } catch (Exception e) {
            System.out.println("Error recorrer_sintactico: "+e);
        }
    }

    void errorSintactico() {

    }
}
