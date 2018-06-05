/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.net.URL;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Yan
 */
public class codigoAyuda {

    public codigoAyuda() {
        
    }
    
    public JPanel crearDepositoAbajo(String nombre,String x,
            int anchoDepositovoAbajo, int altoDepositovoAbajo, int anchoPantalla, int altoPantalla){
        
        JPanel datosDeposisito=new JPanel();
        datosDeposisito.setLayout(new javax.swing.BoxLayout(datosDeposisito, javax.swing.BoxLayout.PAGE_AXIS));
        datosDeposisito.setBackground(Color.GRAY);
        
        JLabel titulo=new JLabel(nombre);
        titulo.setFont(new Font("Tahoma", 1, 25));
        titulo.setForeground(Color.white);
        datosDeposisito.add(titulo);
        
        JPanel dAbajo=new JPanel();
        dAbajo.setBackground(Color.GRAY);
        
        anchoDepositovoAbajo=(int)anchoPantalla*3/10;    
        altoDepositovoAbajo=(int)altoPantalla*4/10;
        
        dAbajo.setSize(anchoDepositovoAbajo,altoDepositovoAbajo);
        
        if(x=="DE")
            dAbajo.setLocation(anchoPantalla-anchoDepositovoAbajo, altoPantalla-altoDepositovoAbajo);
        if(x=="IZ")
            dAbajo.setLocation(0, altoPantalla-altoDepositovoAbajo);
        
        dAbajo.add(datosDeposisito);
        
        return dAbajo;
    }
    
    public JPanel crearDepositoAriba(String nombre,String x,
            int anchoDepositovoArriba, int altoDepositovoArriba, int anchoPantalla, int altoPantalla){
        JPanel datosDeposisito=new JPanel();
        datosDeposisito.setLayout(new javax.swing.BoxLayout(datosDeposisito, javax.swing.BoxLayout.PAGE_AXIS));
        datosDeposisito.setBackground(Color.GRAY);
        
        JLabel titulo=new JLabel(nombre);
        titulo.setFont(new Font("Tahoma", 1, 25));
        titulo.setForeground(Color.white);
        
        datosDeposisito.add(titulo);        
//        datosDeposisito.add(cantidad2);
   
        JPanel p=new JPanel();
        p.setBackground(Color.GRAY);
        anchoDepositovoArriba=(int)anchoPantalla*3/10;    
        altoDepositovoArriba=(int)altoPantalla*4/10;
        p.setSize(anchoDepositovoArriba,altoDepositovoArriba);
        
        if(x=="DE")
            p.setLocation(anchoPantalla-anchoDepositovoArriba, 0);
        if(x=="IZ")
            p.setLocation(0, 0);
        
        p.add(datosDeposisito);
        
        return p;
    }
    
    public JLabel crearCamionA(int anchoPantalla,String camion){
        JLabel l  = new JLabel();
        double anchoCamion=anchoPantalla*2/15;
        double altoCamion=anchoCamion*1517/2500;
        l.setSize((int)anchoCamion,(int)altoCamion);

        URL url = this.getClass().getResource(camion);  
        ImageIcon fot = new ImageIcon(url);  

        Icon icono = new ImageIcon(fot.getImage().getScaledInstance(l.getWidth(), l.getHeight(), Image.SCALE_DEFAULT));
        l.setIcon(icono);

        return l;
    }
    
    public JLabel crearCamionB(int anchoPantalla,String camion){
        JLabel l  = new JLabel();
        double anchoCamion=anchoPantalla*2/20;
        double altoCamion=anchoCamion*1517/2500;
        l.setSize((int)anchoCamion,(int)altoCamion);

        URL url = this.getClass().getResource(camion);  
        ImageIcon fot = new ImageIcon(url);  

        Icon icono = new ImageIcon(fot.getImage().getScaledInstance(l.getWidth(), l.getHeight(), Image.SCALE_DEFAULT));
        l.setIcon(icono);

        return l;
    }
    
    public JLabel formatoLabelCantidad(){
        JLabel l=new JLabel();
        l.setFont(new Font("Tahoma", 1, 16));
        l.setForeground(Color.white);

        return l;
    }
}
