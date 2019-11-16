/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lex_interfaz;

/**
 *
 * @author Medina
 */
public class Token {
    public String ID, Valor;

    public Token(String ID, String Valor) {
        this.ID = ID;
        this.Valor = Valor;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getValor() {
        return Valor;
    }

    public void setValor(String Valor) {
        this.Valor = Valor;
    }

    @Override
    public String toString() {
        return "Token{" + "ID=" + ID + ", Valor=" + Valor + '}';
    }
    
}
