package com.mycompany.mavenproject1;

import static com.mycompany.mavenproject1.codigoAyuda.socket;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Yan
 */
public class pantalla7 extends javax.swing.JFrame {

    int numPantalla=7;
    int velocidad;
    int  anchoPantalla;
    int altoPantalla;
    
    
    public JPanel depositoAbajo=new JPanel();
    public JPanel depositoArriba;
    
    public int anchoDepositovoArriba;
    public int altoDepositovoArriba;
    public int anchoDepositovoAbajo;
    public int altoDepositovoAbajo;

    public double cantidadDeposito=0;
    public JLabel labelCantidadDeposito=new JLabel();

    public JLabel cantidad,cantidad2;
    codigoAyuda codigo;    
    public pantalla7() {
        initComponents();
        setTitle("Pantalla 4");
        setExtendedState(this.MAXIMIZED_BOTH);
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        anchoPantalla= (int)screenSize.getWidth();
        altoPantalla= (int)screenSize.getHeight();
        codigo=new codigoAyuda(anchoPantalla, altoPantalla, depositoAbajo, depositoArriba, labelCantidadDeposito, contenedor,numPantalla);       
        codigo.setPantalla7(this);
        this.depositoAbajo=codigo.crearDepositoAbajo("Carbon procesado - Entrada", "DE", anchoDepositovoAbajo, altoDepositovoAbajo, anchoPantalla, altoPantalla, contenedor, this);
        labelCantidadDeposito=codigo.formatoLabelCantidad();
        labelCantidadDeposito.setText(cantidadDeposito+" TONELADAS");
        depositoAbajo.add(labelCantidadDeposito);
        this.depositoArriba=codigo.crearDepositoAriba("Carbon procesado - Salida", "DE", anchoDepositovoArriba, altoDepositovoArriba, anchoPantalla, altoPantalla, contenedor, this);
        codigo.conectarSocket(this);
        velocidad=codigo.velocidad;
    }
    
    public void moverIzqder(){
        izqder h=new izqder();
        h.start();
    }
    
    public void moverDerizq(){
        derizq h=new derizq();
        h.start();
    }
    
    
    
    
    public class izqder extends Thread{   
       
        @Override
        public void run(){
            JLabel camion=codigo.crearCamionB(anchoPantalla,"/camion1.gif");
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
                if(x==anchoPantalla-anchoDepositovoAbajo-camion.getWidth() || x+1==anchoPantalla-anchoDepositovoAbajo-camion.getWidth()){
                    
                }
                if(x==anchoPantalla-anchoDepositovoAbajo || x+1==anchoPantalla-anchoDepositovoAbajo){
                    cantidadDeposito+=30;
                    labelCantidadDeposito.setText(cantidadDeposito+" TONELADAS");
                    try {      
                        JSONObject j=new JSONObject();
                        j.put("tofunction", 5);
                        j.put("cantidad", 30);
                        socket.emit("modificarCantidad",j);
                    } catch (Exception e) {
                    }
                    
                    if(cantidadDeposito>1000){
                        cantidadDeposito=0;
                        labelCantidadDeposito.setText(cantidadDeposito+" TONELADAS");
                    }
                    moverDerizq();
                    break;
                }
                repaint();
            }
        }
    }
    
    private class derizq extends Thread{   
        public void run(){
        JLabel l=codigo.crearCamionB(anchoPantalla,"/camion2.gif");
        contenedor.add(l);
        int x=anchoPantalla-depositoArriba.getWidth();
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
                
                if(x==0 || x-1==0){
                    try {
                        JSONObject j=new JSONObject();
                        j.put("tofunction", 3);
                    socket.emit("anteriorsiguientePantalla",j);
                    } catch (Exception e) {
                    }
                }
                if(x==0-l.getWidth() || x-1==0-l.getWidth()){
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
            izqder h=new izqder();
            h.start();
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
            java.util.logging.Logger.getLogger(pantalla7.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(pantalla7.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(pantalla7.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(pantalla7.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new pantalla7().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel contenedor;
    // End of variables declaration//GEN-END:variables
}
