/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ifceppd.redesensores.models;

import javax.swing.DefaultListModel;

/**
 *
 * @author elysson
 */
public class Cliente {
    private String nome;
    private DefaultListModel sensores;
    
    public Cliente(String nome, DefaultListModel sensores) {
        this.nome = nome;
        this.sensores = sensores;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public DefaultListModel getSensores() {
        return sensores;
    }

    public void setSensores(DefaultListModel sensores) {
        this.sensores = sensores;
    }
    
}
