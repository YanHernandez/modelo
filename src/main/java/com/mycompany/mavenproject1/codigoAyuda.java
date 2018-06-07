
package com.mycompany.mavenproject1;

import static com.mycompany.mavenproject1.NewMain.socket;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Yan
 */


public class codigoAyuda {

    static Socket socket;
    int  anchoPantalla;
    int altoPantalla;
    
    JPanel depositoAbajo=new JPanel();
    JPanel depositoArriba=new JPanel();
    
    double cantidadDeposito=343;
    JLabel labelCantidadDeposito=new JLabel();
    
    

    JPanel contenedor=new JPanel();
    
    public int numCamiones=1;
    
    JLabel retro=new JLabel();
    
    int numPantalla=1;

//  static int velocidad=10;
//  static int velocidad=7;
//    static int velocidad=4;
    static int velocidad=2;
    
    pantalla1 pantalla1;
    pantalla2 pantalla2;
    pantalla3 pantalla3;
    pantalla4 pantalla4;
    pantalla5 pantalla5;
    pantalla6 pantalla6;
    pantalla7 pantalla7;

    public codigoAyuda(int anchoPantalla, int altoPantalla, JPanel depositoAbajo, JPanel depositoArriba, JLabel labelCantidadDeposito, JPanel contenedor,int numPantalla) {
        this.anchoPantalla = anchoPantalla;
        this.altoPantalla = altoPantalla;
        this.depositoAbajo = depositoAbajo;
        this.depositoArriba = depositoArriba;
        this.labelCantidadDeposito = labelCantidadDeposito;
        this.contenedor = contenedor;
        this.numPantalla=numPantalla;
    }

    public codigoAyuda(int anchoPantalla, int altoPantalla, JPanel contenedor,int numCamiones,JLabel retro,int numPantalla) {
        this.anchoPantalla = anchoPantalla;
        this.altoPantalla = altoPantalla;
        this.contenedor = contenedor;
        this.numCamiones=numCamiones;
        this.retro=retro;
        this.numPantalla=numPantalla;
    }

    public void setPantalla1(pantalla1 pantalla1) {
        this.pantalla1 = pantalla1;
    }

    public void setPantalla2(pantalla2 pantalla2) {
        this.pantalla2 = pantalla2;
    }

    public void setPantalla3(pantalla3 pantalla3) {
        this.pantalla3 = pantalla3;
    }

    public void setPantalla4(pantalla4 pantalla4) {
        this.pantalla4 = pantalla4;
    }

    public void setPantalla5(pantalla5 pantalla5) {
        this.pantalla5 = pantalla5;
    }

    public void setPantalla6(pantalla6 pantalla6) {
        this.pantalla6 = pantalla6;
    }

    public void setPantalla7(pantalla7 pantalla7) {
        this.pantalla7 = pantalla7;
    }
    
    public JPanel crearDepositoAbajo(String nombre,String x,
            int anchoDepositovoAbajo, int altoDepositovoAbajo, int anchoPantalla, int altoPantalla,
            JPanel contenedor,JFrame pantalla){
        
        JPanel datosDeposisito=new JPanel();
        datosDeposisito.setLayout(new javax.swing.BoxLayout(datosDeposisito, javax.swing.BoxLayout.PAGE_AXIS));
        datosDeposisito.setBackground(Color.GRAY);
        
        JLabel titulo=new JLabel(nombre);
        titulo.setFont(new Font("Tahoma", 1, 25));
        titulo.setForeground(Color.white);
        datosDeposisito.add(titulo);
        
        JPanel p=new JPanel();
        p.setBackground(Color.GRAY);
        
        anchoDepositovoAbajo=(int)anchoPantalla*3/10;    
        altoDepositovoAbajo=(int)altoPantalla*4/10;
        
        p.setSize(anchoDepositovoAbajo,altoDepositovoAbajo);
        
        if(x=="DE")
            p.setLocation(anchoPantalla-anchoDepositovoAbajo, altoPantalla-altoDepositovoAbajo);
        if(x=="IZ")
            p.setLocation(0, altoPantalla-altoDepositovoAbajo);
        
        p.add(datosDeposisito);
        
        contenedor.add(p);
        pantalla.repaint();
        
        return p;
        
        
    }
    
    public JPanel crearDepositoAriba(String nombre,String x,
            int anchoDepositovoArriba, int altoDepositovoArriba, int anchoPantalla, int altoPantalla,
            JPanel contenedor,JFrame pantalla){
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
        
        contenedor.add(p);
        pantalla.repaint();
        
        return p;
        
        
    }
    
    public JLabel crearCamionA(String camion){
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
    
    public void conectarSocket(JFrame p){
                IO.Options opts = new IO.Options();
        try {
            this.socket = IO.socket("http://159.65.104.236:3001/");
                    Emitter on = socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                        @Override
                        public void call(Object... args) {
                            JSONObject jsono = new JSONObject();
                            try {
                                jsono.put("nombre", "pantalla"+numPantalla);
                                jsono.put("id", socket.id());
                                jsono.put("function", numPantalla);
                                socket.emit("conectandoPantalla", jsono);
                            } catch (JSONException ex) {
                                System.out.println(ex);
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
                    }).on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
                        @Override
                        public void call(Object... args) {
                            JOptionPane.showMessageDialog(null, "No se pudo conectar");
                        }
                    }).on("pantallaUsado", new Emitter.Listener() {
                        @Override
                        public void call(Object... args) {
                            JSONObject obj = (JSONObject) args[0];
                            try{
                                String nombre = obj.getString("nombre");
                                JOptionPane.showMessageDialog(null, "Funcion en uso, elija otra");
                                p.dispose();
                            }catch(JSONException j){
                                j.printStackTrace();
                            }
                        }
                    }).on("arranca", new Emitter.Listener() {
                        
                        @Override
                        public void call(Object... args) {
                            JSONObject obj = (JSONObject) args[0];
                            try{
                                switch(numPantalla){
                                    case 1:
                                        numCamiones = obj.getInt("numCamiones1");      
                                        arranca1(numCamiones);
                                        break;
                                    case 2:
                                        arranca2();
                                        break;
                                    case 3:
                                        numCamiones = obj.getInt("numCamiones2");      
                                        arranca3();
                                        break;
                                    case 4:
                                        arranca4();
                                        break;
                                    case 5:
                                        arranca5();
                                        break;
                                    case 6:
                                        arranca6();
                                        break;
                                    case 7:
                                        System.out.println("si 7");
                                        arranca7();
                                        break;
                                    default:
                                        System.err.println(numPantalla);
                                }
//                        if(numPantalla==1){
//                            
//                        }else{
//                            System.out.println("arranca2");
//                            moverIzqder();
//                        }



                            }catch(Exception e){
                            }
                        }
                    }).on("devuleve", new Emitter.Listener() {
                        @Override
                        public void call(Object... args) {
                            
                            switch(numPantalla){
                                    case 1:
                                        devuelve1();
                                        break;
                                    case 2:
                                        arranca2();
                                        break;
                                    default:
                                        System.err.println(numPantalla);
                                }
                            
//                    JSONObject obj = (JSONObject) args[0];
                    try{
                        System.out.println("devuelve");
                    //                        pantalla3.derizq h=new pantalla3.derizq();
                        //h.start();
                    }catch(Exception e){
                    }
                        }
                    }).on("modificarCantidad", new Emitter.Listener() {
                        @Override
                        public void call(Object... args) {
                            JSONObject obj = (JSONObject) args[0];
                            try{
                                double cantidad = obj.getDouble("cantidad");
                                
                                switch(numPantalla){
                                    case 2:
                                        pantalla2.cantidadDeposito+=cantidad;
                                        pantalla2.labelCantidadDeposito.setText("Cantidad amacenada: "+pantalla2.cantidadDeposito);
                                        break;
                                    case 3:
                                        pantalla3.cantidadDeposito+=cantidad;
                                        pantalla3.labelCantidadDeposito.setText("Cantidad amacenada: "+pantalla3.cantidadDeposito);
                                        break;
                                }
                                
                                
                            }catch(Exception e){
                            }
                        }
                    });
            socket.connect();
        } catch (URISyntaxException ex) {
            Logger.getLogger(NewMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void arranca1(int numCamiones){
        pantalla1.empezarSimulacion(numCamiones);
    }
    
    public void arranca2(){
        pantalla2.moverIzqder();
    }
    
    public void arranca3(){
        pantalla3.empezarSimulacion(numCamiones);
    }
    
    public void arranca4(){
        pantalla4.moverIzqder();
    }
    
    public void arranca5(){
        pantalla5.moverIzqder();
    }
    
    public void arranca6(){
        pantalla6.moverIzqder();
    }
    
    public void arranca7(){
        pantalla7.moverIzqder();
    }
    
    
    public void devuelve1(){
        pantalla1.moverDerizq();
    }
    
    public void moverIzqder(){
        
            //izqder h=new izqder();
           // h.start();
    }

//    public class izqder extends Thread{   
//       
//        @Override
//        public void run(){
//            
//            JLabel camion=crearCamionA("/camion1.gif");
//            contenedor.add(camion);
//            
//            int x=0-camion.getWidth();
//            int y=(int)(altoPantalla-(camion.getHeight()+(camion.getHeight()*7/10)));
//            camion.setLocation(x, (int)(y));
//            pantalla.repaint();
//
//            while (true) {
//                x++;
//                try{
//                    Thread.sleep(4);
//                }catch(InterruptedException ex){
//                    break;
//                }
//                camion.setLocation(x, y);
//                pantalla.repaint();
//                if(numPantalla==1){
//                    if(x==anchoPantalla-camion.getWidth()){
//                        try{
//                             JSONObject j=new JSONObject();
//                             j.put("tofunction", 2);
//                             socket.emit("siguientePantalla",j);
//                         }catch(JSONException j){
//                             JOptionPane.showMessageDialog(null, j);    
//                         }
//                     }
//                     if(x==anchoPantalla){
//                         break;
//                     }
//                }else{
//                    if(x==anchoPantalla-depositoAbajo.getWidth()-camion.getWidth()){
//                    
//                    }
//                    if(x==anchoPantalla-depositoAbajo.getWidth()){
//                        cantidadDeposito+=90;
//                        labelCantidadDeposito.setText("Cantidad amacenada: "+cantidadDeposito);
//                        try {      
//
//                            JSONObject j=new JSONObject();
//                            j.put("tofunction", 3);
//                            j.put("cantidad", 90);
//                            NewMain.socket.emit("modificarCantidad",j);
//
//                            Thread.sleep(10000);
//
//    //                        pantalla.derizq h=new derizq();
//    //                        h.start();
//                        } catch (Exception e) {
//                        }
//                        break;
//                    }
////                if(x==lRetro.getLocation().x-30){
////                    
////                try{
////                    contenedor.remove(retro);
////                    retro=retro("/retro.gif");
////                    contenedor.add(retro);
////                    repaint();
////                    Thread.sleep(7000);
////                    contenedor.remove(retro);
////                    retro=retro("/retroFija.png");
////                    contenedor.add(retro);
////                    repaint();
////                    
////                }catch(InterruptedException ex){
////                    JOptionPane.showMessageDialog(null, ex);
////                }
////                }
//                
//                
//                }
//                
//                
//                pantalla.repaint();
//            }
//        }
//    }


    public class empezar extends Thread{   
        @Override
        public void run(){
            
//            izqder[] h=new izqder[numCamiones];
            
            for (int i = 0; i < numCamiones; i++) {
//                h[i]=new izqder();
//                h[i].start();
                moverIzqder();
                System.out.println("arranca"+i);
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(pantalla1.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }    
    
    public void moverDerizq(){
        
       //     derizq h=new derizq();
         //   h.start();
    }
//    
//    private class derizq extends Thread{   
//        public void run(){
//        JLabel l=crearCamionA("/camion2.gif");
//        contenedor.add(l);
//        int x=anchoPantalla;
//        int y=(int)(depositoArriba.getHeight()-l.getWidth());
//        l.setLocation(x, (int)(y));
//        pantalla.repaint();
//            while(true){
//                x--;
//                try{
//                    Thread.sleep(4);
//                }catch(InterruptedException ex){
//                    break;
//                }
//                l.setLocation(x, y);
//                pantalla.repaint();
//                if(x==0-l.getWidth()){
//                    try{
//                        Thread.sleep(15000);
//                        izqder h=new izqder();
//                        h.start();
//                    }catch(InterruptedException e){
//                    }
//                    break;
//                }
//            }
//        }
    }

