/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import Controller.Koneksi;
import View.FormUtama;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author User
 */
public class Admin {
    public static String username;
    public String password;
    public String konfirmasi;
    public String role;
    private Koneksi con;
    private Statement st;
    private ResultSet res;
    private String query;

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String Username) {
        username = Username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getKonfirmasi() {
        return konfirmasi;
    }

    public void setKonfirmasi(String konfirmasi) {
        this.konfirmasi = konfirmasi;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
    public void Login(String username, String password) {
        con = new Koneksi();
        con.connect();
        try {
            st = con.connect().createStatement();
            res = st.executeQuery("select *from tb_admin where username ='" + username + "' And password = '" + password + "'");
            if (res.next()) {
                Admin.setUsername(username);
                JOptionPane.showMessageDialog(null, "Welcome " + username);
                new FormUtama().setVisible(true);
            } 
            else{
                JOptionPane.showMessageDialog(null, "Username or Password is Wrong");
            }
        } catch (SQLException e) {

        }
    }

    public void CekUsername(String username){
        con = new Koneksi();
        con.connect();        
        try{
            Statement st = con.conn.createStatement();
            res = st.executeQuery("select *from tb_admin where username = '"+username+"' and password "); 
            if(!res.next()){
                JOptionPane.showMessageDialog(null, "Username Is Not Valid !!");
            }
            
        }catch(SQLException ex){
            
        }
    }
    
    public void Update(String username, String password, String konfirmasi) {
        con = new Koneksi();
        con.connect();
        try {
            st = con.conn.createStatement();
            query = "update tb_admin set password = '"+password+"', konfirmasi = '"+konfirmasi+"' where username = '" + username + "'";
            st.executeUpdate(query);
            st.close();
            con.conn.close();
            JOptionPane.showMessageDialog(null, "Data Berhasil di Update");
        } catch (SQLException e) {

        }
    }
}
