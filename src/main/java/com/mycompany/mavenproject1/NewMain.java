/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author asus
 */
public class NewMain {
    
    static Socket socket;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        IO.Options opts = new IO.Options();
        try {
            socket = IO.socket("http://159.65.104.236:3001/");
            socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                    
                    JSONObject jsono = new JSONObject();
                    
                    try {
                        
                        jsono.put("nombre", "pantalla1");
                        jsono.put("id", socket.id());
                        jsono.put("function", 1);
                        
                        socket.emit("conectandoPantalla", jsono);
                    } catch (JSONException e) {
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
                    
                    
                }

            }).on("pantallaUsado", new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                    
                    JSONObject obj = (JSONObject) args[0];
                    
                    try{
                        String nombre = obj.getString("nombre");
                        JOptionPane.showMessageDialog(null, "Esta funcion en uso");
                    }catch(JSONException j){
                        j.printStackTrace();
                    }
                    
                }
                
           }).on("ResivirCarro", new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                    
                    JSONObject obj = (JSONObject) args[0];
                    
                    try{
                        String nombre = obj.getString("nombre");
                        JOptionPane.showMessageDialog(null, "resiviste un carro");
                    }catch(JSONException j){
                        j.printStackTrace();
                    }
                    
                }
                
           })        
                    ;
            socket.connect();
        } catch (URISyntaxException ex) {
            Logger.getLogger(NewMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        
      new pantalla1().setVisible(true);

    }

}
