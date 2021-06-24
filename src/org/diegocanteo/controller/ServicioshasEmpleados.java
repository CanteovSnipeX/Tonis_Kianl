
package org.diegocanteo.controller;

import eu.schudt.javafx.controls.calendar.DatePicker;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
import org.diegocanteo.bean.Empleado;
import org.diegocanteo.bean.SerhasEmp;
import org.diegocanteo.bean.Servicio;
import org.diegocanteo.db.Conexion;
import org.diegocanteo.system.Principal;


public class ServicioshasEmpleados implements Initializable{
private enum operaciones{ NUEVO, GUARDAR , ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO};
private Principal escenarioPrincipal;
private operaciones tipoDeOperacion = operaciones.NINGUNO;
private ObservableList <SerhasEmp> listarSerhasEmp;
private ObservableList <Servicio> listarServicios;
private ObservableList <Empleado> listaEmpleado;
private DatePicker fecha ;
@FXML private TextField txtCodigoServicioshasEmpleado;
@FXML private TextField txtHoraEvento;
@FXML private TextField txtLugarEvento;
@FXML private ComboBox cmbCodigoServicio;
@FXML private ComboBox cmbCodigoEmpleado;       
@FXML private GridPane grpFechaEvento;
@FXML private TableView tblServiciohasEmpleado;
@FXML private TableColumn colCodigoServicioshasEmpleado;
@FXML private TableColumn colFechaEvento;
@FXML private TableColumn colHoraEvento;
@FXML private TableColumn colLugarEvento;
@FXML private TableColumn colCodigoServicio;
@FXML private TableColumn colCodigoEmpleado;
@FXML private Button btnNuevo;
@FXML private Button btnEditar;
@FXML private Button btnEliminar;
@FXML private Button btnReportar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        fecha = new  DatePicker (Locale.ENGLISH);
        fecha.setDateFormat(new  SimpleDateFormat("yyy-MM-dd"));
        fecha.getCalendarView().todayButtonTextProperty().set("Todasy");
        fecha.getCalendarView().setShowWeeks(false);
        fecha.getStylesheets().add("/org/diegocanteo/resource/DatePicker.css");
        grpFechaEvento.add(fecha,0,0);
        cmbCodigoServicio.setItems(getServicio());
        cmbCodigoEmpleado.setItems(getEmpleado());
    }

    
    public void cargarDatos(){
        tblServiciohasEmpleado.setItems(getSerhasEmp());
        colCodigoServicioshasEmpleado.setCellValueFactory(new PropertyValueFactory<SerhasEmp,Integer>("codigoServicioshasEmpleado"));
        colFechaEvento.setCellValueFactory(new PropertyValueFactory<SerhasEmp,Date>("fechaEvento"));
        colHoraEvento.setCellValueFactory(new PropertyValueFactory<SerhasEmp,String>("horaEvento"));
        colLugarEvento.setCellValueFactory(new PropertyValueFactory<SerhasEmp,String>("lugarEvento"));
        colCodigoServicio.setCellValueFactory(new PropertyValueFactory<SerhasEmp,Integer>("codigoServicio"));
        colCodigoEmpleado.setCellValueFactory(new PropertyValueFactory<SerhasEmp,Integer>("codigoEmpleado"));
    }
    
    public void seleccionarElemento(){
        txtCodigoServicioshasEmpleado.setText(String.valueOf(((SerhasEmp)tblServiciohasEmpleado.getSelectionModel().getSelectedItem()).getCodigoServicioshasEmpleado()));  
        fecha.selectedDateProperty().set(((SerhasEmp)tblServiciohasEmpleado.getSelectionModel().getSelectedItem()).getFechaEvento());
        txtHoraEvento.setText(((SerhasEmp)tblServiciohasEmpleado.getSelectionModel().getSelectedItem()).getHoraEvento());
        txtLugarEvento.setText(((SerhasEmp)tblServiciohasEmpleado.getSelectionModel().getSelectedItem()).getLugarEvento());
        cmbCodigoServicio.getSelectionModel().select(buscarServicio(((SerhasEmp)tblServiciohasEmpleado.getSelectionModel().getSelectedItem()).getCodigoServicio()));
        cmbCodigoEmpleado.getSelectionModel().select(buscarEmpleado(((SerhasEmp)tblServiciohasEmpleado.getSelectionModel().getSelectedItem()).getCodigoEmpleado()));  
    }
    
    
        
       public ObservableList <SerhasEmp> getSerhasEmp(){
           ArrayList<SerhasEmp> lista = new ArrayList<SerhasEmp>();
           try{
              PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarServicioshasEmpleados}");
              ResultSet resultado = procedimiento.executeQuery();
               while(resultado.next()){
                    lista.add(new SerhasEmp(resultado.getInt("codigoServicioshasEmpleado"),
                                        resultado.getDate("fechaEvento"),
                                        resultado.getString("horaEvento"),
                                        resultado.getString("lugarEvento"),
                                        resultado.getInt("codigoServicio"),
                                        resultado.getInt("codigoEmpleado")));
               }
           }catch(Exception e ){
              e.printStackTrace();
           }
                return listarSerhasEmp = FXCollections.observableArrayList(lista);
   
       }
    

      public ObservableList <Servicio> getServicio(){
        ArrayList<Servicio> lista = new ArrayList<Servicio>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarServicio}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Servicio(resultado.getInt("codigoServicio"),
                                       resultado.getDate("fechaServicio"),
                                       resultado.getString("tipoServicio"),
                                       resultado.getString("horaServicio"),
                                       resultado.getString("lugarServicio"),
                                       resultado.getString("telefonoContacto"),
                                       resultado.getInt("codigoEmpresa")));
            }
        }catch(Exception e ){
        e.printStackTrace();
        } 
        return listarServicios = FXCollections.observableArrayList(lista);
    }
      
      
      public Servicio buscarServicio(int codigoServicio){
         Servicio resultado = null;
          try{
           PreparedStatement procedimiento  = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarServicio(?)}");
           procedimiento.setInt(1, codigoServicio);
           ResultSet registro = procedimiento.executeQuery();   
           while(registro.next()){
             resultado = new Servicio(registro.getInt("codigoServicio"),
                                          registro.getDate("fechaServicio"),
                                          registro.getString("tipoServicio"),
                                          registro.getString("horaServicio"),
                                          registro.getString("lugarServicio"),
                                          registro.getString("telefonoContacto"),
                                          registro.getInt("codigoEmpresa")
                                      );
           }                          
      }catch(Exception e){
          e.printStackTrace();
      }
             return resultado;

      }
      
      
      
      
    public ObservableList<Empleado>getEmpleado(){
    ArrayList<Empleado> lista = new ArrayList<Empleado>();
       try{
           PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarEmpleado}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()){
                lista.add(new Empleado(resultado.getInt("codigoEmpleado"),
                         resultado.getString("numeroEmpleado"),
                        resultado.getString("nombreEmpleado"),
                        resultado.getString("apellidoEmpleado"),
                        resultado.getString("direccionEmpleado"),
                        resultado.getString("telefonoContacto"),
                        resultado.getString("gradoCocinero"),
                        resultado.getInt("codigoTipoEmpleado")));
            }
       }catch(Exception e ){
           e.printStackTrace();
       }
       
       return listaEmpleado = FXCollections.observableArrayList(lista);
   }
    
    
      public Empleado buscarEmpleado(int codigoEmpleado){
     Empleado resultado = null;
       try{
           PreparedStatement procedimiento  = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarEmpleado(?)}");
           procedimiento.setInt(1, codigoEmpleado);
           ResultSet registro = procedimiento.executeQuery();
           while(registro.next()){
             resultado = new Empleado(registro.getInt("codigoEmpleado"),
                                            registro.getString("numeroEmpleado"),
                                            registro.getString("nombreEmpleado"),
                                            registro.getString("apellidoEmpleado"),
                                            registro.getString("direccionEmpleado"),
                                            registro.getString("telefonoContacto"),
                                            registro.getString("gradoCocinero"),
                                            registro.getInt("codigoTipoEmpleado")
                     
             );
               
           }
       }catch(Exception e){
           e.printStackTrace();
       }
       return resultado;
           
       }
      
      
      public void nuevo (){
        switch (tipoDeOperacion){
            case NINGUNO:
                limpiarControles();
                activarControles();
                btnNuevo.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEliminar.setDisable(false);
                btnEditar.setDisable(true);
                btnReportar.setDisable(true);
                tipoDeOperacion = operaciones.GUARDAR;
                break ;
            case  GUARDAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                btnEliminar.setDisable(false);
                btnEditar.setDisable(false);
                btnReportar.setDisable(false);
                tipoDeOperacion = operaciones.NINGUNO;
                cargarDatos();
                break ;
                
        }
    }
      
      public void guardar(){
       SerhasEmp registro = new SerhasEmp();
       registro.setFechaEvento(fecha.getSelectedDate());
       registro.setHoraEvento(txtHoraEvento.getText());
       registro.setLugarEvento(txtLugarEvento.getText());
       registro.setCodigoServicio(((Servicio)cmbCodigoServicio.getSelectionModel().getSelectedItem()).getCodigoServicio());
       registro.setCodigoEmpleado(((Empleado)cmbCodigoEmpleado.getSelectionModel().getSelectedItem()).getCodigoEmpleado());
       try{
           PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call  sp_AgregarServicioshasEmpleados(?,?,?,?,?)}");
           procedimiento.setDate(1, new java.sql.Date(registro.getFechaEvento().getTime()));
           procedimiento.setString(2,registro.getHoraEvento());
           procedimiento.setString(3, registro.getLugarEvento());
           procedimiento.setInt(4,registro.getCodigoServicio());
           procedimiento.setInt(5,registro.getCodigoEmpleado());
           procedimiento.executeQuery();
           listarSerhasEmp.add(registro);
       }catch(Exception e){
           e.printStackTrace();
       }
          
   }
      
      
      public void eliminar(){
      switch(tipoDeOperacion){
          case GUARDAR:
                desactivarControles();
                limpiarControles();
                btnNuevo.setText("Nuevo");
                btnNuevo.setDisable(false);
                btnEliminar.setText("Eliminar");
                btnEliminar.setDisable(false);
                btnEditar.setDisable(false);
                btnReportar.setDisable(false);
                tipoDeOperacion = operaciones.NINGUNO;
                break;
          default:
              if(tblServiciohasEmpleado.getSelectionModel().getSelectedItem() !=null){
                        int resultado = JOptionPane.showConfirmDialog(null, "Estas seguro de Eliminar el registro?", "Eliminar Registro", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                        if(resultado == JOptionPane.YES_OPTION){
                            try{
                                PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{ call sp_EliminarServicioshasEmpleados(?)}");
                                procedimiento.setInt(1,((SerhasEmp)tblServiciohasEmpleado.getSelectionModel().getSelectedItem()).getCodigoServicioshasEmpleado());    
                                procedimiento.execute();
                                listarSerhasEmp.remove(tblServiciohasEmpleado.getSelectionModel().getSelectedItem());
                                limpiarControles();
                            }catch(Exception e ){
                                e.printStackTrace(); 
                            }
                        }
                        }else{
                      JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento");
                }
          
        }
  }
      
     
      
  public void editar(){
        switch(tipoDeOperacion){
            case NINGUNO:
                if(tblServiciohasEmpleado.getSelectionModel().getSelectedItem() != null){
                    btnEditar.setText("Actualizar");
                    btnReportar.setDisable(false);
                    btnReportar.setText("Cancelar");
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    activarControles();
                    tipoDeOperacion = operaciones.ACTUALIZAR;
                }else{
                    JOptionPane.showMessageDialog(null, "Debe Seleccionar un Elemento");
            }
            break;
            case ACTUALIZAR:
                actualizar();
                btnEditar.setText("Editar");
                btnReportar.setText("Reporte");
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                tipoDeOperacion = operaciones.NINGUNO;
                cargarDatos();
                break;
        } 
  }
         
  
  public void  actualizar(){
    try{
     PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarServicioshasEmpleados( ?, ?, ?, ?)}");
        SerhasEmp registro = (SerhasEmp)tblServiciohasEmpleado.getSelectionModel().getSelectedItem();
            registro.setFechaEvento(fecha.getSelectedDate());
            registro.setHoraEvento(txtHoraEvento.getText());
            registro.setLugarEvento(txtLugarEvento.getText());
                procedimiento.setInt(1, registro.getCodigoServicioshasEmpleado());
                procedimiento.setDate(2, new java.sql.Date(registro.getFechaEvento().getTime()));
                procedimiento.setString(3, registro.getHoraEvento());
                procedimiento.setString(4, registro.getLugarEvento()); 
                procedimiento.execute();
    } catch(Exception e ) {
        e.printStackTrace();
    }

  }
      
       public void cancelar(){
       switch(tipoDeOperacion){
           case ACTUALIZAR:
               desactivarControles();
               limpiarControles();
               btnEditar.setText("Editar");
               btnEditar.setDisable(false);
               btnReportar.setText("Reporte");
               btnReportar.setDisable(false);
               btnNuevo.setDisable(false);
               btnEliminar.setDisable(false);
               tipoDeOperacion = operaciones.NINGUNO;
               break;
            }
   }
       
      
     public void desactivarControles(){
         grpFechaEvento.setDisable(false);
         txtHoraEvento.setEditable(false);
         txtLugarEvento.setEditable(false);
         cmbCodigoServicio.setDisable(false);
         cmbCodigoEmpleado.setDisable(false);
     } 
     
     
     public void activarControles(){
         grpFechaEvento.setDisable(false);
         txtHoraEvento.setEditable(true);
         txtLugarEvento.setEditable(true);
         cmbCodigoServicio.setDisable(false);
         cmbCodigoEmpleado.setDisable(false);
     }
     
     public void limpiarControles(){
         txtHoraEvento.setText("");  
         txtLugarEvento.setText("");
         cmbCodigoServicio.getSelectionModel().getSelectedItem();
         cmbCodigoEmpleado.getSelectionModel().getSelectedItem();
     }
      

      public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void menuPrincipal (){
        escenarioPrincipal.menuPrincipal();
  
     }
     
    public void menuprincipal(){
        escenarioPrincipal.menuPrincipal();
    }
}




  