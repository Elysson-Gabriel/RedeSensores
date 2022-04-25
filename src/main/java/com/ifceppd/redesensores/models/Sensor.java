/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ifceppd.redesensores.models;

/**
 *
 * @author elysson
 */
public class Sensor {
    private String nome;
    private String paramMonitor;
    private int limiteMin;
    private int limiteMax;
    private int leituraAtual;

    public Sensor(String nome, String paramMonitor, int limiteMin, int limiteMax) {
        this.nome = nome;
        this.paramMonitor = paramMonitor;
        this.limiteMin = limiteMin;
        this.limiteMax = limiteMax;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getParamMonitor() {
        return paramMonitor;
    }

    public void setParamMonitor(String paramMonitor) {
        this.paramMonitor = paramMonitor;
    }

    public int getLimiteMin() {
        return limiteMin;
    }

    public void setLimiteMin(int limiteMin) {
        this.limiteMin = limiteMin;
    }

    public int getLimiteMax() {
        return limiteMax;
    }

    public void setLimiteMax(int limiteMax) {
        this.limiteMax = limiteMax;
    }
    
    public int getLeituraAtual() {
        return leituraAtual;
    }

    public void setLeituraAtual(int leituraAtual) {
        this.leituraAtual = leituraAtual;
    }
    
}
