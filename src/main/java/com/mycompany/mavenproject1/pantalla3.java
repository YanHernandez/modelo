package com.mycompany.mavenproject1;

import static com.mycompany.mavenproject1.codigoAyuda.socket;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Label;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.json.JSONException;
import org.json.JSONObject;
import static sun.applet.AppletResourceLoader.getImage;

public class pantalla3 extends javax.swing.JFrame {
    
        int[] listaPedidos=
    {1,3,3,2,3,2,2,1,1,3,2,1,
    3,3,2,2,3,2,1,3,2,1,3,1,2,
    2,2,3,3,2,2,1,3,2,1,2,2,1,
    3,3,2,2,3,2,1,3,2,1,3,1,2,
    1,2,3,2,3,2,1,3,2,1,3,2,1,
    2,2,3,3,2,2,1,3,2,1,2,2,1,
    1,2,3,2,3,2,1,3,2,1,3,2,1};
        
        
    
    int posicion=0;
    int velocidad;

    int numPantalla=3;
    int  anchoPantalla;
    int altoPantalla;
    int numCamiones=1;
    
    public JPanel depositoAbajo=new JPanel();
    public JPanel depositoArriba;
    
    public int anchoDepositovoArriba;
    public int altoDepositovoArriba;
    public int anchoDepositovoAbajo;
    public int altoDepositovoAbajo;

    public double cantidadDeposito=343;
    public JLabel labelCantidadDeposito=new JLabel();

    public JLabel cantidad,cantidad2;
    codigoAyuda codigo;    
    
    int camionesEnCola=0;
    boolean semaforo=true;
    public pantalla3() {
        initComponents();
        setTitle("Pantalla 2");
        setExtendedState(this.MAXIMIZED_BOTH);
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        anchoPantalla= (int)screenSize.getWidth();
        altoPantalla= (int)screenSize.getHeight();
        codigo=new codigoAyuda(anchoPantalla, altoPantalla, depositoAbajo, depositoArriba, labelCantidadDeposito, contenedor,numPantalla);       
        codigo.setPantalla3(this);
        this.depositoAbajo=codigo.crearDepositoAbajo("Deposito principal - Salida", "IZ", anchoDepositovoAbajo, altoDepositovoAbajo, anchoPantalla, altoPantalla, contenedor, this);
        labelCantidadDeposito=codigo.formatoLabelCantidad();
        labelCantidadDeposito.setText(cantidadDeposito+" TONELADAS");
        depositoAbajo.add(labelCantidadDeposito);
        this.depositoArriba=codigo.crearDepositoAriba("Deposito principal - Entrada", "IZ", anchoDepositovoArriba, altoDepositovoArriba, anchoPantalla, altoPantalla, contenedor, this);
        codigo.conectarSocket(this);
        velocidad=codigo.velocidad;
    }
    
    public void empezarSimulacion(int numCamiones){
        this.numCamiones=numCamiones;
        empezar h=new empezar();
        h.start();
    }
    
    public class empezar extends Thread{   
        @Override
        public void run(){
            
            izqder[] h=new izqder[numCamiones];
            
            for (int i = 0; i < numCamiones; i++) {
                h[i]=new izqder();
                h[i].start();
                
                try {
                    Thread.sleep(500*velocidad);
                } catch (InterruptedException ex) {
                    Logger.getLogger(pantalla1.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public void verificarCola(){
        if(camionesEnCola>0 && cantidadDeposito>30){
            moverIzqder();
        }
    }
    
    public void moverIzqder(){
        if(camionesEnCola==0 && semaforo==false){
            camionesEnCola++;
        }else if(camionesEnCola==0 && semaforo==true && cantidadDeposito>30){
            izqder h=new izqder();
            h.start();
        }else if(camionesEnCola>0 && semaforo==true && cantidadDeposito>30){
            izqder h=new izqder();
            h.start();
        }else if(camionesEnCola>0 && semaforo==false){
            camionesEnCola++;
        }    
    }
    
    public void moverDerizq(){
        derizq h=new derizq();
        h.start();
    }
    
    private class izqder extends Thread{   
       
        @Override
        public void run(){
            semaforo=false;
            if(camionesEnCola>0){
                camionesEnCola--;
            }
            JLabel camion=codigo.crearCamionB(anchoPantalla,"/camion1.gif");
            contenedor.add(camion);
            
            int x=depositoAbajo.getWidth()-camion.getWidth();
            int y=(int)(altoPantalla-(camion.getHeight()+(camion.getHeight()*7/10)));
            camion.setLocation(x, (int)(y));
            repaint();
            cantidadDeposito-=30;
            labelCantidadDeposito.setText(cantidadDeposito+" TONELADAS");
            JSONObject j=new JSONObject();
            try {
                j.put("tofunction", 1);
                j.put("cantidad", -30);
                socket.emit("modificarCantidad",j);
            } catch (JSONException ex) {
                Logger.getLogger(pantalla3.class.getName()).log(Level.SEVERE, null, ex);
            }
            while (true) {
                x+=2;
                try{
                    Thread.sleep(velocidad);
                }catch(InterruptedException ex){
                    break;
                }
                camion.setLocation(x, y);
                repaint();
                if(x==depositoAbajo.getWidth()+camion.getWidth()*2 || x+1==depositoAbajo.getWidth()+camion.getWidth()*2){
                    
                    semaforo=true;
                    if(camionesEnCola>0){
                        moverIzqder();
                    }
                }
                
                if(x==anchoPantalla-camion.getWidth() || x+1==anchoPantalla-camion.getWidth()){
                    try {      
                        JSONObject j2=new JSONObject();
//                        int pantallaAEnviar=listaPedidos[posicion]+3;
                        j2.put("tofunction",4);
                        socket.emit("siguientePantalla",j2);
//                        posicion++;
//                        if(posicion==listaPedidos.length){
//                            posicion=0;
//                        }
                    } catch (Exception e) {
                    }
                }
                
                
                if(x==anchoPantalla){
                    
                    break;
                }
            }
        }
    }
    
    private class derizq extends Thread{   
        public void run(){
        JLabel l=codigo.crearCamionB(anchoPantalla,"/camion2.gif");
        contenedor.add(l);
        int x=anchoPantalla;
        int y=(int)(depositoArriba.getHeight()-l.getHeight());
        contenedor.add(l);
        l.setLocation(x, (int)(y));
        repaint();
        
            while(true){
                x-=2;
                try{
                    Thread.sleep(velocidad);
                }catch(InterruptedException ex){JOptionPane.showMessageDialog(null, ex);}
                
                l.setLocation(x, y);
                repaint();
                
                if(x==depositoArriba.getWidth()-l.getWidth() || x-1==depositoArriba.getWidth()-l.getWidth()){
                    moverIzqder();
                }
                if(x==0-l.getWidth()){
                    break;
                }
            }
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        contenedor = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });
        getContentPane().setLayout(new javax.swing.OverlayLayout(getContentPane()));

        contenedor.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout contenedorLayout = new javax.swing.GroupLayout(contenedor);
        contenedor.setLayout(contenedorLayout);
        contenedorLayout.setHorizontalGroup(
            contenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        contenedorLayout.setVerticalGroup(
            contenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        getContentPane().add(contenedor);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        if(KeyEvent.getKeyText(evt.getKeyCode()).equals("Abajo")){
            moverIzqder();
        }
        
        if(KeyEvent.getKeyText(evt.getKeyCode()).equals("Arriba")){
            derizq h=new derizq();
            h.start();
        }
    }//GEN-LAST:event_formKeyPressed

    private class detenerArriba extends Thread{   
        public void run(){
            
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(pantalla3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(pantalla3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(pantalla3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(pantalla3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new pantalla3().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel contenedor;
    // End of variables declaration//GEN-END:variables
}
