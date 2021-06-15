/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thuchanh3_ltm_;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author thanh
 */
public class main_for_client2 {

    public static void main(String[] args) {
        try {
            // TODO code application logic here
            myClient cl1 = new myClient(InetAddress.getLocalHost(), 3333, "client 2");
            cl1.run();
        } catch (UnknownHostException ex) {
            Logger.getLogger(Thuchanh3_LTM_.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Thuchanh3_LTM_.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
