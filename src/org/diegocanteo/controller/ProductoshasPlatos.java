
package org.diegocanteo.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.diegocanteo.bean.Platos;
import org.diegocanteo.bean.Productos;
import org.diegocanteo.bean.ProhasPla;
import org.diegocanteo.db.Conexion;
import org.diegocanteo.system.Principal;


public class ProductoshasPlatos  implements Initializable{

    private Principal escenariPrincipal;
    private  ObservableList <ProhasPla> listarProhasPla;
    private  ObservableList <Platos> listarPlatos;
    private  ObservableList <Productos> listarProductos;
    @FXML private ComboBox  cmbCodigoProductos;
    @FXML private ComboBox cmbCodigoPlatos;
    @FXML private TableView tblserhasPla;
    @FXML private TableColumn colCodigoPlatos;
    @FXML private TableColumn colCodigoProductos;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {  
        cargarDatos();
        cmbCodigoPlatos.setItems(getPlatos());
        cmbCodigoProductos.setItems(getProductos());
    }
    
   
    
    
    public void cargarDatos(){    
        tblserhasPla.setItems(getProhasPla());
        colCodigoPlatos.setCellValueFactory(new PropertyValueFactory<ProhasPla,Integer>("codigoPlatos"));
        colCodigoProductos.setCellValueFactory(new PropertyValueFactory<ProhasPla,Integer>("codigoProducto"));
    }
    
  
    public void seleccionarElemento(){
        cmbCodigoProductos.getSelectionModel().select(buscarProductos(((ProhasPla)tblserhasPla.getSelectionModel().getSelectedItem()).getCodigoProducto()));
        cmbCodigoPlatos.getSelectionModel().select(buscarPlatos(((ProhasPla)tblserhasPla.getSelectionModel().getSelectedItem()).getCodigoPlatos()));
    }
    
    
    public ObservableList<ProhasPla>getProhasPla(){
     ArrayList<ProhasPla> lista = new ArrayList<ProhasPla>();
     try{
         PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarProductoshasPlatos}");
            ResultSet resultado = procedimiento.executeQuery();           
                while (resultado.next()){
                    lista.add(new ProhasPla(
                                               resultado.getInt("codigoPlatos"),
                                               resultado.getInt("codigoProducto")
                    ));
                }

     }catch(Exception e ){
         e.printStackTrace();
     }
     return  listarProhasPla = FXCollections.observableArrayList(lista);
    }
    
    
    
    public ObservableList<Platos>getPlatos(){
        ArrayList<Platos> lista = new ArrayList<Platos>();
        try{
            PreparedStatement procedimiento =  Conexion.getInstance().getConexion().prepareCall("{call sp_ListarPlato}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()){
                lista.add(new Platos(resultado.getInt("codigoPlatos"),
                                resultado.getString("cantidad"),
                                resultado.getString("nombrePlato"),
                                resultado.getString("descripcionPlato"),
                                resultado.getDouble("precioPlato"),
                                resultado.getInt("CodigoTipoPlatos")));
                
            }
        }catch(Exception e ){
            e.printStackTrace();
        }
        return  listarPlatos = FXCollections.observableArrayList(lista);
    }
    
    
      
      public Platos buscarPlatos(int codigoPlatos){
         Platos resultado = null;
          try{
           PreparedStatement procedimiento  = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarPlato(?)}");
           procedimiento.setInt(1,codigoPlatos);
           ResultSet registro = procedimiento.executeQuery();   
           while(registro.next()){
                resultado = new Platos(registro.getInt("codigoPlatos"),
                                       registro.getString("cantidad"),
                                       registro.getString("nombrePlato"),
                                       registro.getString("descripcionPlato"),
                                       registro.getDouble("precioPlato"),
                                       registro.getInt("CodigoTipoPlatos"));
           }                          
      }catch(Exception e){
          e.printStackTrace();
      }
           return resultado;
      }
       
      
      
      public ObservableList<Productos>getProductos(){
        ArrayList<Productos> lista = new ArrayList<Productos>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarProductos}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Productos(resultado.getInt("codigoProducto"),
                                        resultado.getString("nombreProducto"),
                                        resultado.getString("cantidad")));
            }
           
        }catch(Exception e ){
            e.printStackTrace();
        }
       
        return listarProductos = FXCollections.observableArrayList(lista);
    }
    
      
    public  Productos buscarProductos (int codigoProductos){
        Productos resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_buscarProductos(?)}");
            procedimiento.setInt(1, codigoProductos);
            ResultSet registro = procedimiento.executeQuery();
                while(registro.next()){
                    resultado = new Productos(registro.getInt("codigoProducto"),
                                              registro.getString("nombreProducto"),
                                               registro.getString("cantidad")); 
                }

        }catch(Exception e){
            e.printStackTrace();
        }
        return resultado;
    }
    
     
    public Principal getEscenariPrincipal() {
        return escenariPrincipal;
    }

    public void setEscenariPrincipal(Principal escenariPrincipal) {
        this.escenariPrincipal = escenariPrincipal;
    }
    
    public void menuprincipal(){
        escenariPrincipal.menuPrincipal();
    }  
   
}
