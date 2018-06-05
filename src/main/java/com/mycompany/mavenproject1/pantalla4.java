package com.mycompany.mavenproject1;
import static com.mycompany.mavenproject1.NewMain.socket;
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
public class pantalla4 extends javax.swing.JFrame {

    int funcion=4;
    
    public pantalla4() {
        initComponents();
        setTitle("Pantalla "+funcion);
        setExtendedState(this.MAXIMIZED_BOTH);
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        anchoPantalla= (int)screenSize.getWidth();
        altoPantalla= (int)screenSize.getHeight();

        crearDepositoAbajo("Deposito principal - Entrada", "DE");
        crearDepositoAriba("Deposito principal - Salida", "DE");
        conectarSocket(funcion);
    }
    
    int  anchoPantalla;
    int altoPantalla;
    JPanel depositoAbajo;
    JPanel depositoArriba;
    
    int anchoDepositovoArriba;
    int altoDepositovoArriba;
    int anchoDepositovoAbajo;
    int altoDepositovoAbajo;

    double cantidadDeposito=233;
    JLabel labelCantidadDeposito;

    public void crearDepositoAbajo(String nombre,String x){
        codigoAyuda c=new codigoAyuda();
        depositoAbajo=c.crearDepositoAbajo(nombre, x,anchoDepositovoAbajo, altoDepositovoAbajo, anchoPantalla, altoPantalla);
        labelCantidadDeposito=c.formatoLabelCantidad();
        labelCantidadDeposito.setText("Cantidad amacenada: "+cantidadDeposito);
        depositoAbajo.add(labelCantidadDeposito);
        contenedor.add(depositoAbajo);
        repaint();
    }
    
    public void crearDepositoAriba(String nombre,String x){
        codigoAyuda c=new codigoAyuda();
        depositoArriba=c.crearDepositoAriba(nombre, x, anchoDepositovoArriba, altoDepositovoArriba, anchoPantalla, altoPantalla);
        contenedor.add(depositoArriba);
        repaint();
    }
    
    public void conectarSocket(int numPantalla){
                IO.Options opts = new IO.Options();
        try {
            socket = IO.socket("http://159.65.104.236:3001/");
            socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    JSONObject jsono = new JSONObject();
                    try {                        
                        jsono.put("nombre", "pantalla"+numPantalla);
                        jsono.put("id", socket.id());
                        jsono.put("function", numPantalla);
                        socket.emit("conectandoPantalla", jsono); 
                    } catch (JSONException ex) {
                    }
                }
            }).on("event", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    JSONObject obj = (JSONObject) args[0];
                    System.out.println(obj.toString());
                }
            }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    System.exit(0);
                }
            }).on("pantallaUsado", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    JSONObject obj = (JSONObject) args[0];
                    try{
                        String nombre = obj.getString("nombre");
                        JOptionPane.showMessageDialog(null, "Funcion en uso, elija otra");
                        dispose();
                    }catch(JSONException j){
                        j.printStackTrace();
                    }
                }
           }).on("arranca", new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                    JSONObject obj = (JSONObject) args[0];
                    try{
                        
                        System.out.println("arranca");
                        izqder h=new izqder();
                        h.start();
                    }catch(Exception e){       
                        System.out.println(e);
                    }
                }
           }).on("devuleve", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
//                    JSONObject obj = (JSONObject) args[0];
                    try{
                        System.out.println("devuelve");
                        derizq h=new derizq();
                        h.start();
                    }catch(Exception e){       
                    }
                }
           }).on("modificarCantidad", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    JSONObject obj = (JSONObject) args[0];
                    try{
                        String nombre = obj.getString("cantidad");
                        System.out.println("Modificar en "+nombre);
                    }catch(Exception e){       
                    }
                }
           })        
                    ;
            socket.connect();
            
        } catch (URISyntaxException ex) {
            Logger.getLogger(NewMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private class izqder extends Thread{   
       
        @Override
        public void run(){
            codigoAyuda c=new codigoAyuda();
            JLabel camion=c.crearCamionB(anchoPantalla,"/camion1.gif");
            contenedor.add(camion);
            
            int x=0-camion.getWidth();
            int y=(int)(altoPantalla-(camion.getHeight()+(camion.getHeight()*7/10)));
            camion.setLocation(x, (int)(y));
            repaint();

            while (true) {
                x++;
                try{
                    Thread.sleep(4);
                }catch(InterruptedException ex){
                    break;
                }
                camion.setLocation(x, y);
                if(x==anchoPantalla-anchoDepositovoAbajo-camion.getWidth()){
                    
                }
                if(x==anchoPantalla-anchoDepositovoAbajo){
                    cantidadDeposito+=30;
                    labelCantidadDeposito.setText("Cantidad amacenada: "+cantidadDeposito);
                    try {      
                        Thread.sleep(10000);
                        derizq h=new derizq();
                        h.start();
                    } catch (Exception e) {
                    }
                    break;
                }
                repaint();
            }
        }
    }
    
    private class derizq extends Thread{   
        public void run(){
        codigoAyuda c=new codigoAyuda();
        JLabel l=c.crearCamionB(anchoPantalla,"/camion2.gif");
        contenedor.add(l);
        int x=anchoPantalla-depositoArriba.getWidth();
        int y=(int)(depositoArriba.getHeight()-l.getHeight());
        contenedor.add(l);
        l.setLocation(x, (int)(y));
        repaint();
            while(true){
                x--;
                try{
                    Thread.sleep(4);
                }catch(InterruptedException ex){JOptionPane.showMessageDialog(null, ex);}
                l.setLocation(x, y);
                repaint();
                if(x==0){
                    try {
                        JSONObject j=new JSONObject();
                        j.put("tofunction", 7);
                    socket.emit("siguientePantalla",j);
                    } catch (Exception e) {
                    }
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
            java.util.logging.Logger.getLogger(pantalla4.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(pantalla4.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(pantalla4.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(pantalla4.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new pantalla4().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel contenedor;
    // End of variables declaration//GEN-END:variables
}
