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
import java.util.Vector;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Usuario iTC
 */
public class NewLex {

//    Simbolo simbolo = new Simbolo();;
    DefaultTableModel tabla;
    List<SintCSV> sintacticocsv;
    EstadosCSV obj_csv;
    Object[] inf = new Object[2];
    Object[] infcsv = new Object[3];
    List<LexCSV> estadoSintactico;
    Object[] infsint = new Object[12];
    Tabla_Sintactica obj_sintactico;
    List<LexCSV> lexcsv;
    List<Token> token, token2, token3;
    StringBuffer sb, valor;
    int cont = 0, ent = 0, z = 0, linea = 1, filas, columans;
    Vector estados, notificacion;
    Stack<String> entrada, pila, T, token_aux, infijo;

    public String Mas = "+",
            Menos = "-",
            Por = "*",
            Div = "/",
            Id = "id",
            Num = "num",
            Par_A = "(",
            Par_C = ")",
            Pot = "pot(num,num)",
            Mod = "mod(num,num)",
            Fin = "$",
            Inicio = "E";

    public NewLex() {
    }

    public void lexema() {
        recorrer();
    }

    public String codigo_Fuente() {
        File archivo;
        limpiar_Fuente();
        String caracter = "";
        FileReader fr;
        BufferedReader br;
        try {
            JFileChooser jf = new JFileChooser();
            jf.showOpenDialog(jf);
            while (tabla.getRowCount() > 0) {
                tabla.removeRow(0);
            }
            archivo = jf.getSelectedFile();

            fr = new FileReader(archivo);
            br = new BufferedReader(fr);
            sb = new StringBuffer();
            if (archivo != null) {
                while ((caracter = br.readLine()) != null) {
                    sb.append(caracter + "\n");
                }
            }
        } catch (Exception e) {
            System.out.println("Error codigoFuente: " + e);
        }
        return sb.toString();

    }

    public void tabla_Estados() {
        while (tabla.getRowCount() > 0) {
            tabla.removeRow(0);
        }
        //limpiar();
        File csv;

        FileReader frcsv;
        BufferedReader brcsv;
        String[] dato;

        try {
            lexcsv = new ArrayList<>();

            JFileChooser jf = new JFileChooser();
            jf.showOpenDialog(jf);
            csv = jf.getSelectedFile();

            frcsv = new FileReader(csv);
            brcsv = new BufferedReader(frcsv);
            String linea;
            while ((linea = brcsv.readLine()) != null) {
                dato = linea.split(";");
                lexcsv.add(new LexCSV(dato[0], dato[1], dato[2]));
            }
            estados();
        } catch (Exception e) {
            System.out.println("Error tabla_Estados: " + e);
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

    public void recorrer() {
        Vector numes;
        try {
            token_aux = new Stack<String>();
            char d;
            numes = new Vector();
            notificacion = new Vector();
            token = new ArrayList<>();
            token2 = new ArrayList<>();
            token3 = new ArrayList<>();
            valor = new StringBuffer();
            int b = 0, q = estados.size();
            sb.append(" ");
            while ((d = sig_Caracter()) != ' ') {
                while (d != '\n') {
                    while (d != '.') {
                        b = mover(String.valueOf(b), String.valueOf(d));
                        valor.append(d);
                        numes.add(b);
                        d = sig_Caracter();
                    }
                    if (!numes.contains(estados.size() - q)) {
                        token.add(new Token("Identificador", valor.toString()));
                        token2.add(new Token("Identificador", valor.toString()));
                        valor = new StringBuffer();
                        b = 0;
                        numes = new Vector();
                    } else {
                        notificacion.addElement(notificacion());
                        token.add(new Token("Error", valor.toString()));
                        token2.add(new Token("Error", valor.toString()));
                        valor = new StringBuffer();
                        b = 0;
                        numes = new Vector();
                    }
                    d = sig_Caracter();
                }
                if (d == '\n') {
                    z = 0;
                    linea++;
                    cambiar_token();
                    for (int i = 0; i < token.size(); i++) {
                        token_aux.push(token.get(i).ID);
                        token3.add(new Token(token.get(i).ID, token.get(i).Valor));
                    }
                    token_aux.push("\n");
                    token3.add(new Token("Salto", ""));
                    token = new ArrayList<>();
                }
            }
            cambiar_token2();
            for (int a = 0; a < token2.size(); a++) {
                inf[0] = token2.get(a).ID;
                inf[1] = token2.get(a).Valor;
                tabla.addRow(inf);
            }
        } catch (Exception e) {
            System.out.println("Error recorrer: " + e);
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

    public void cambiar_token() {
        String c = "";
        for (int a = 0; a < token.size(); a++) {
            if (!token.get(a).ID.equals("Error")) {
                if (token.get(a).Valor.equals("(")) {
                    token.set(a, new Token("(", token.get(a).Valor));
                } else if (token.get(a).Valor.equals(")")) {
                    token.set(a, new Token(")", token.get(a).Valor));
                } else if (token.get(a).Valor.equals("+")) {
                    token.set(a, new Token("+", token.get(a).Valor));
                } else if (token.get(a).Valor.equals("-")) {
                    token.set(a, new Token("-", token.get(a).Valor));
                } else if (token.get(a).Valor.equals("*")) {
                    token.set(a, new Token("*", token.get(a).Valor));
                } else if (token.get(a).Valor.equals("/")) {
                    token.set(a, new Token("/", token.get(a).Valor));
                } else if (is_Numero(token.get(a).Valor)) {
                    token.set(a, new Token("num", token.get(a).Valor));
                } else if (token.get(a).Valor.length() > 7) {
                    c = token.get(a).Valor;
                    if (c.substring(0, 4).equals("pot(")) {
                        token.set(a, new Token("pot(num,num)", token.get(a).Valor));
                    } else if (c.substring(0, 4).equals("mod(")) {
                        token.set(a, new Token("mod(num,num)", token.get(a).Valor));
                    }
                } else if (is_Letra(token.get(a).Valor)) {
                    token.set(a, new Token("id", token.get(a).Valor));
                }
            }
        }
    }

    public void cambiar_token2() {
        String c = "";
        for (int a = 0; a < token2.size(); a++) {
            if (!token2.get(a).ID.equals("Error")) {
                if (token2.get(a).Valor.equals("(")) {
                    token2.set(a, new Token("(", token2.get(a).Valor));
                } else if (token2.get(a).Valor.equals(")")) {
                    token2.set(a, new Token(")", token2.get(a).Valor));
                } else if (token2.get(a).Valor.equals("+")) {
                    token2.set(a, new Token("+", token2.get(a).Valor));
                } else if (token2.get(a).Valor.equals("-")) {
                    token2.set(a, new Token("-", token2.get(a).Valor));
                } else if (token2.get(a).Valor.equals("*")) {
                    token2.set(a, new Token("*", token2.get(a).Valor));
                } else if (token2.get(a).Valor.equals("/")) {
                    token2.set(a, new Token("/", token2.get(a).Valor));
                } else if (is_Numero(token2.get(a).Valor)) {
                    token2.set(a, new Token("num", token2.get(a).Valor));
                } else if (token2.get(a).Valor.length() > 7) {
                    c = token2.get(a).Valor;
                    if (c.substring(0, 4).equals("pot(")) {
                        token2.set(a, new Token("pot(num,num)", token2.get(a).Valor));
                    } else if (c.substring(0, 4).equals("mod(")) {
                        token2.set(a, new Token("mod(num,num)", token2.get(a).Valor));
                    }
                } else if (is_Letra(token2.get(a).Valor)) {
                    token2.set(a, new Token("id", token2.get(a).Valor));
                }
            }
        }
    }

    public boolean is_Numero(String numero) {
        try {
            Integer.parseInt(numero);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean is_Letra(String letra) {
        return letra.matches("[a-zA-Z]+");
    }

    public void limpiar() {
        lexcsv = null;
        token = null;
        token2 = null;
        valor = null;
        cont = 0;
        z = 0;
        linea = 1;
        notificacion = null;
        estados = null;
        ent = 0;
        entrada = null;
        pila = null;
        T = null;
    }

    public void limpiar_Fuente() {
        token = null;
        token2 = null;
        valor = null;
        cont = 0;
        z = 0;
        linea = 1;
        notificacion = null;
        ent = 0;
        entrada = null;
        pila = null;
        T = null;
    }

    public String notificacion() {
        String Notif = "ERROR: linea " + linea + ", columna " + z;
        return Notif;
    }

    //Analizador Sintactico
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

    public void ejecutable() {
        //entrada();
        tabla_Sint();
        matrizSintactica();
    }

    public void matrizSintactica() {
//        String MAS = "+", MENOS = "-", POR = "*", DIV = "/", ID = "id", NUM = "num", PAR_A = "(", PAR_C = ")", POT = "pot(num,num)", MOD = "mod(num,num)", FIN = "$";

        try {
            estadoSintactico = new ArrayList<>();

            for (int i = 0; i < sintacticocsv.size(); i++) {
                estadoSintactico.add(new LexCSV(sintacticocsv.get(i).Simbolo, Mas, sintacticocsv.get(i).MAS));
                estadoSintactico.add(new LexCSV(sintacticocsv.get(i).Simbolo, Menos, sintacticocsv.get(i).MENOS));
                estadoSintactico.add(new LexCSV(sintacticocsv.get(i).Simbolo, Por, sintacticocsv.get(i).POR));
                estadoSintactico.add(new LexCSV(sintacticocsv.get(i).Simbolo, Div, sintacticocsv.get(i).DIV));
                estadoSintactico.add(new LexCSV(sintacticocsv.get(i).Simbolo, Id, sintacticocsv.get(i).ID));
                estadoSintactico.add(new LexCSV(sintacticocsv.get(i).Simbolo, Num, sintacticocsv.get(i).NUM));
                estadoSintactico.add(new LexCSV(sintacticocsv.get(i).Simbolo, Par_A, sintacticocsv.get(i).PAC_A));
                estadoSintactico.add(new LexCSV(sintacticocsv.get(i).Simbolo, Par_C, sintacticocsv.get(i).PAR_C));
                estadoSintactico.add(new LexCSV(sintacticocsv.get(i).Simbolo, Pot, sintacticocsv.get(i).POT));
                estadoSintactico.add(new LexCSV(sintacticocsv.get(i).Simbolo, Mod, sintacticocsv.get(i).MOD));
                estadoSintactico.add(new LexCSV(sintacticocsv.get(i).Simbolo, Fin, sintacticocsv.get(i).FIN));
            }
        } catch (Exception e) {
            System.out.println("Error matrizSintactica: " + e);
        }
    }

    public void entrada() {
        try {
            entrada = new Stack<String>();
            for (int i = 0; i < token_aux.size(); i++) {
                if (token_aux.get(i) != "\n") {
                    entrada.push(token_aux.get(i));
                } else {
                    entrada.push("$");
                }
            }
        } catch (Exception e) {
            System.out.println("Error entrada: " + e);
        }
    }

    public void recorrer_sintactico() {
        String A = "", a = "", res = "", R = "";

        try {
            T = new Stack<String>();
            pila = new Stack<String>();
//            infijo = new Stack<String>();

            T.push(Simbolo.Mas);
            T.push(Simbolo.Menos);
            T.push(Simbolo.Por);
            T.push(Simbolo.Div);
            T.push(Simbolo.Id);
            T.push(Simbolo.Num);
            T.push(Simbolo.Par_A);
            T.push(Simbolo.Par_C);
            T.push(Simbolo.Pot);
            T.push(Simbolo.Fin);

            pila.push(Simbolo.Fin);
            pila.push(Simbolo.Inicio);
            while (!(a = sig_entrada()).isEmpty()) {
                while (!(A = pila.peek()).equals(Simbolo.Fin)) {
                    res = mover_sint(A, a);
                    if (terminales(res) || A.equals(Simbolo.Fin) || res.equals("vacio") || terminales(A)) {
                        if (res.equals(a) || A.equals(a)) {
                            pila.pop();
//                            infijo.push(token3.get(ent - 1).Valor);
                            R += token3.get(ent - 1).Valor;
                            a = sig_entrada();
                        } else if (res.equals("vacio")) {
                            pila.pop();
                        } else {
                            errorSintactico();
                            break;
                        }
                    } else {
                        invertir(res);
                    }
                }
                if (A.equals("$")) {
                    JOptionPane.showMessageDialog(null, "Sintaix Aceptada: \n" + R);
                    pila.push(Simbolo.Inicio);
//                    System.out.println("Postfijo: " + postfijo(infijo));
//                    infijo = new Stack<String>();
                    R = "";
                }
            }

        } catch (Exception e) {
            System.out.println("Error recorrer_sintactico: " + e);
        }
    }

    public String sig_entrada() {
        String val;
        val = entrada.get(ent);
        ent++;
        return val;
    }

    public String mover_sint(String estado, String caracter) {
        String sig = "";
        for (int i = 0; i < estadoSintactico.size(); i++) {
            String inicio = estadoSintactico.get(i).inicio;
            String atributo = estadoSintactico.get(i).atributo;
            String fin = estadoSintactico.get(i).fin;
            if (inicio.equals(estado) && atributo.equals(caracter)) {
                sig = fin;
            }
        }
        return sig;
    }

    public boolean terminales(String cadena) {
        String val = "";
        for (int i = 0; i < T.size(); i++) {
            val += " " + T.elementAt(i);
        }

        if (val.contains(cadena)) {
            return true;
        } else {
            return false;
        }
    }

    public void invertir(String cadena) {
        String invertida = "";
        pila.pop();
        for (int i = cadena.length() - 1; i >= 0; i--) {
            invertida = String.valueOf(cadena.charAt(i));
            pila.push(invertida);
        }
    }

    void errorSintactico() {
        JOptionPane.showMessageDialog(null, "Error Sintactico");
    }

    public int pref(String op) {
        int prf = 0;
        if (op.substring(0, 4).equals("pot(") || op.substring(0, 4).equals("mod(")) {
            prf = 5;
        }
        if (op.equals("*") || op.equals("/")) {
            prf = 4;
        }
        if (op.equals("+") || op.equals("-")) {
            prf = 3;
        }
        if (op.equals(")")) {
            prf = 2;
        }
        if (op.equals("(")) {
            prf = 1;
        }
        return prf;
    }

    String postfijo(Stack cadena) {
        Stack< String> post;
        Stack< String> P;
        Stack< String> S;

        try {
            post = new Stack< String>();
            P = new Stack< String>();
            S = new Stack< String>();
            post = cadena;
            System.out.println("Post: " + post);
            while (!post.isEmpty()) {
                switch (pref(post.peek())) {
                    case 1:
                        P.push(post.pop());
                        break;
                    case 2:
                        while (!P.peek().equals("(")) {
                            S.push(P.pop());
                        }
                        P.pop();
                        post.pop();
                        break;
                    case 3:
                    case 4:
                        while (pref(P.peek()) >= pref(post.peek())) {
                            S.push(P.pop());
                        }
                        P.push(post.pop());
                        break;
                    default:
                        S.push(post.pop());
                }
            }
            return S.toString();
        } catch (Exception e) {
            System.out.println("Error Postfijo: " + e);
        }
        return null;
    }

}
