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
public class SintCSV {
    String Simbolo,MAS,MENOS,POR,DIV,ID,NUM,PAC_A,PAR_C,POT,MOD,FIN;

    public SintCSV(String Simbolo, String MAS, String MENOS, String POR, String DIV, String ID, String NUM, String PAC_A, String PAR_C, String POT, String MOD, String FIN) {
        this.Simbolo = Simbolo;
        this.MAS = MAS;
        this.MENOS = MENOS;
        this.POR = POR;
        this.DIV = DIV;
        this.ID = ID;
        this.NUM = NUM;
        this.PAC_A = PAC_A;
        this.PAR_C = PAR_C;
        this.POT = POT;
        this.MOD = MOD;
        this.FIN = FIN;
    }

    public String getSimbolo() {
        return Simbolo;
    }

    public void setSimbolo(String Simbolo) {
        this.Simbolo = Simbolo;
    }

    public String getMAS() {
        return MAS;
    }

    public void setMAS(String MAS) {
        this.MAS = MAS;
    }

    public String getMENOS() {
        return MENOS;
    }

    public void setMENOS(String MENOS) {
        this.MENOS = MENOS;
    }

    public String getPOR() {
        return POR;
    }

    public void setPOR(String POR) {
        this.POR = POR;
    }

    public String getDIV() {
        return DIV;
    }

    public void setDIV(String DIV) {
        this.DIV = DIV;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNUM() {
        return NUM;
    }

    public void setNUM(String NUM) {
        this.NUM = NUM;
    }

    public String getPAC_A() {
        return PAC_A;
    }

    public void setPAC_A(String PAC_A) {
        this.PAC_A = PAC_A;
    }

    public String getPAR_C() {
        return PAR_C;
    }

    public void setPAR_C(String PAR_C) {
        this.PAR_C = PAR_C;
    }

    public String getPOT() {
        return POT;
    }

    public void setPOT(String POT) {
        this.POT = POT;
    }

    public String getMOD() {
        return MOD;
    }

    public void setMOD(String MOD) {
        this.MOD = MOD;
    }

    public String getFIN() {
        return FIN;
    }

    public void setFIN(String FIN) {
        this.FIN = FIN;
    }

    @Override
    public String toString() {
        return "SintCSV{" + "Simbolo=" + Simbolo + ", MAS=" + MAS + ", MENOR=" + MENOS + ", POR=" + POR + ", DIV=" + DIV + ", ID=" + ID + ", NUM=" + NUM + ", PAC_A=" + PAC_A + ", PAR_C=" + PAR_C + ", POT=" + POT + ", MOD=" + MOD + ", FIN=" + FIN + '}';
    }
    
}
