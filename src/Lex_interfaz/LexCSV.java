/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lex_interfaz;

/**
 *
 * @author Usuario iTC
 */
public class LexCSV {
    String inicio,atributo,fin;
    
    public LexCSV(String inicio, String atributo, String fin) {
        this.inicio = inicio;
        this.atributo = atributo;
        this.fin = fin;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getAtributo() {
        return atributo;
    }

    public void setAtributo(String atributo) {
        this.atributo = atributo;
    }

    public String getFin() {
        return fin;
    }

    public void setFin(String fin) {
        this.fin = fin;
    }

    @Override
    public String toString() {
        return "LexCSV{" + "inicio=" + inicio + ", atributo=" + atributo + ", fin=" + fin + '}';
    }

    
}
