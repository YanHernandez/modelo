package com.mycompany.mavenproject1;

import static com.mycompany.mavenproject1.codigoAyuda.socket;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.KeyEvent;
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
public class pantalla2 extends javax.swing.JFrame {

    int velocidad;
    int numPantalla=1;
    int  anchoPantalla;
    int altoPantalla;
    
    
    public JPanel depositoAbajo=new JPanel();
    public JPanel depositoArriba;
    JLabel retro;
    
    public int anchoDepositovoArriba;
    public int altoDepositovoArriba;
    public int anchoDepositovoAbajo;
    public int altoDepositovoAbajo;

    public double cantidadDeposito=343;
    public JLabel labelCantidadDeposito=new JLabel();

    public JLabel cantidad,cantidad2;
    int numCamiones=1;
    codigoAyuda codigo;    
    
    int camionesEnCola=0;
    boolean semaforo=true;
    public pantalla2() {
        initComponents();
        setTitle("Pantalla "+numPantalla);
        setExtendedState(this.MAXIMIZED_BOTH);
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        anchoPantalla= (int)screenSize.getWidth();
        altoPantalla= (int)screenSize.getHeight();
        codigo=new codigoAyuda(anchoPantalla, altoPantalla, depositoAbajo, depositoArriba, labelCantidadDeposito, contenedor,numPantalla);       
        codigo.setPantalla2(this);
        this.depositoAbajo=codigo.crearDepositoAbajo("Deposito principal - Entrada", "DE", anchoDepositovoAbajo, altoDepositovoAbajo, anchoPantalla, altoPantalla, contenedor, this);
        labelCantidadDeposito=codigo.formatoLabelCantidad();
        labelCantidadDeposito.setText("Cantidad amacenada: "+cantidadDeposito);
        depositoAbajo.add(labelCantidadDeposito);
        this.depositoArriba=codigo.crearDepositoAriba("Deposito principal - Salida", "DE", anchoDepositovoArriba, altoDepositovoArriba, anchoPantalla, altoPantalla, contenedor, this);
        codigo.conectarSocket(this);
        
        retro=retro("/retroFija.png");
        contenedor.add(retro);        
        
        this.velocidad=codigo.velocidad;
    }
    
    
    public void empezarSimulacion(int numCamiones){
        this.camionesEnCola=numCamiones;
//        empezar h=new empezar();
        izqder h=new izqder();
        h.start();
    }
    
    public void moverDerizq(){
        derizq h=new derizq();
        h.start();
    }
    
    public void moverIzqder(){
        if(camionesEnCola==0 && semaforo==false){
            camionesEnCola++;
        }else if(camionesEnCola==0 && semaforo==true ){
            izqder h=new izqder();
            h.start();
        }else if(camionesEnCola>0 && semaforo==true){
            izqder h=new izqder();
            h.start();
        }else if(camionesEnCola>0 && semaforo==false){
            camionesEnCola++;
        }
        
    }
    
    public class empezar extends Thread{   
        @Override
        public void run(){
            
            izqder[] h=new izqder[numCamiones];
            
            for (int i = 0; i < numCamiones; i++) {
                h[i]=new izqder();
                h[i].start();
                try {
                    Thread.sleep(2500*velocidad);
                } catch (InterruptedException ex) {
                    Logger.getLogger(pantalla1.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public class izqder extends Thread{   
       
        @Override
        public void run(){
            semaforo=false;
            if(camionesEnCola>0){
                camionesEnCola--;
            }
            JLabel camion=codigo.crearCamionA("/camion1.gif");
            contenedor.add(camion);
            
            int x=0-camion.getWidth();
            int y=(int)(altoPantalla-(camion.getHeight()+(camion.getHeight()*7/10)));
            camion.setLocation(x, (int)(y));
            repaint();

            while (true) {
                x+=2;
                try{
                    Thread.sleep(velocidad);
                }catch(InterruptedException ex){
                    break;
                }
                camion.setLocation(x, y);
                repaint();
                if(x==retro.getLocation().x-30 || x-1==retro.getLocation().x-30){
                    try{
                        contenedor.remove(retro);
                        retro=retro("/retro.gif");
                        contenedor.add(retro);
                        repaint();
                        Thread.sleep(1750*velocidad);
                        contenedor.remove(retro);
                        retro=retro("/retroFija.png");
                        contenedor.add(retro);
                        repaint();
                        semaforo=true;
                        if(camionesEnCola>0){
                            moverIzqder();
                        }
                    }catch(InterruptedException ex){
                        JOptionPane.showMessageDialog(null, ex);
                    }
                }
                
                if(x>=anchoPantalla-anchoDepositovoAbajo-camion.getWidth()){
                    
                }
                if(x==anchoPantalla-anchoDepositovoAbajo || x-1==anchoPantalla-anchoDepositovoAbajo){
                    cantidadDeposito+=90;
                    labelCantidadDeposito.setText("Cantidad amacenada: "+cantidadDeposito);
                    try {      
                        
                        JSONObject j=new JSONObject();
                        j.put("tofunction", 3);
                        j.put("cantidad", 90);
                        socket.emit("modificarCantidad",j);
                    } catch (Exception e) {
                    }
                    break;
                }
                repaint();
            }
            try {
                Thread.sleep(2500*velocidad);
            } catch (InterruptedException ex) {
                Logger.getLogger(pantalla2.class.getName()).log(Level.SEVERE, null, ex);
            }
                        
                        derizq h=new derizq();
                        h.start();
        }
    }
    
    private class derizq extends Thread{   
        public void run(){
        JLabel l=codigo.crearCamionA("/camion2.gif");
        contenedor.add(l);
        int x=anchoPantalla-depositoArriba.getWidth();
        int y=(int)(depositoArriba.getHeight()-l.getHeight());
            System.out.println(y);
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
                
                if(x==0 || x+1==0){
                    moverIzqder();
                }
                if(x+1==0-l.getWidth() || x+1==0-l.getWidth()){
                    break;
                }
            }
        }
    }

    
    public JLabel retro(String retro){
        JLabel lRetro  = new JLabel();
        double anchoCamion=anchoPantalla*2/10;
        double altoCamion=anchoCamion*1517/2500;
        lRetro.setSize((int)anchoCamion,(int)altoCamion);

        URL url = this.getClass().getResource(retro);  
        ImageIcon fot = new ImageIcon(url);  

        Icon icono = new ImageIcon(fot.getImage().getScaledInstance(lRetro.getWidth(), lRetro.getHeight(), Image.SCALE_DEFAULT));
        lRetro.setIcon(icono);

        int x=lRetro.getWidth();
        int y=(int)(altoPantalla-(lRetro.getHeight()+(lRetro.getHeight()*7/10)));
        lRetro.setLocation(x, (int)(y));
        return lRetro;    
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
            java.util.logging.Logger.getLogger(pantalla1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(pantalla1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(pantalla1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(pantalla1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
   

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new pantalla2().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel contenedor;
    // End of variables declaration//GEN-END:variables
}
