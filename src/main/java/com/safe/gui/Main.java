/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safe.gui;

import com.safe.entity.Capacitacion;
import com.safe.entity.Cliente;
import com.safe.gui.component.ButtonTableComponent;
import com.safe.entity.SoliEvalTer;
import com.safe.entity.Usuario;
import com.safe.gui.component.WindowComponenet;
import com.safe.service.TipoCapacitacionService;
import com.safe.service.ClienteService;
import com.safe.service.TokenManager;
import com.safe.service.SessionManager;
import com.safe.service.SolicitudService;
import com.safe.service.UsuarioService;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author familia
 */
public class Main extends javax.swing.JFrame {
    
    private final TokenManager token;
    private final Login loginForm;
    private final SessionManager session;
    private final ClienteService clienteService;
    private final UsuarioService usuarioService;
    private final TipoCapacitacionService capacitacionService;
    
    private javax.swing.JMenu jMenuProfile;
    private java.awt.Component horizontalGlue;
    private final String domain;
    private Object selectedEntity;
    
    private final SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy");

    
    
    /**
     * Creates new form Main
     * @param loginForm LoginFomr
     * @param token token service
     * @param args
     */
    public Main(Login loginForm, TokenManager token, String args[]) {
        initComponents();        
        initUserMenu();
        this.token = token;
        this.loginForm = loginForm;
        this.domain = args[0];
        clienteService = new ClienteService(this.domain);
        usuarioService = new UsuarioService(this.domain);
        capacitacionService = new TipoCapacitacionService(this.domain);
        int sessionTime;
        try {
            sessionTime = (int)Integer.parseInt(getConfig().getProperty("safe.sessionMinuteTime"));
        } catch (IOException e) {
            sessionTime = 30;
        }
        System.out.println("Se establece la session a " + sessionTime + " minutos");
        setLayout(new BorderLayout());
        WindowComponenet.centerWindow(this);
        
        session = new SessionManager(this, sessionTime);
        initCustoms();
    }
    
    private void initCustoms(){
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = jTable1.rowAtPoint(evt.getPoint());
                int col = jTable1.columnAtPoint(evt.getPoint());
                if (col == 6) {
                    SoliEvalTer solicitud = (SoliEvalTer)jTable1.getValueAt(row, col);
                    Cliente cliente = clienteService.getOne((int)solicitud.getClienteidcliente());
                    jLabelRut.setText(cliente.getRutcliente());
                    jLabelTipoVisita.setText(SolicitudService.TIPOS[(int)solicitud.getTipovisitteridtipovister()]);
                    jLabelDireccionVisita.setText(solicitud.getDireccionvisita());                        
                    changePanel(jInternalFrame3);
                }else if(col == 7){                        
                    String url =jTable1.getValueAt(row, col).toString();
                    openWebUrl(url);
                }
            }
        });
        jTable1.getColumnModel().getColumn(6).setCellRenderer(new ButtonTableComponent("[+]"));
        jTable1.getColumnModel().getColumn(7).setCellRenderer(new ButtonTableComponent("PDF"));
        
        jTable4.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = jTable4.rowAtPoint(evt.getPoint());
                int col = jTable4.columnAtPoint(evt.getPoint());
                if(col == 7){                        
                    Cliente cliente = (Cliente)jTable4.getValueAt(row, col);
                    jTextField1.setText(cliente.getRazonsocial());
                    jTextField2.setText(cliente.getRutcliente());
                    jTextField3.setText(cliente.getGirocliente());
                    jTextField4.setText(cliente.getDireccioncliente());
                    jTextField5.setText("Santiago"); //TODO: falta deto del WS Debe ser Un combobox.
                    jTextField6.setText(cliente.getTeloficina());
                    jTextField7.setText(cliente.getNombrecontacto());
                    jTextField8.setText(cliente.getFonocontacto());
                    jTextField9.setText(cliente.getMailcontacto());
                    jTextField10.setText(cliente.getCargocontacto());
                    jTextArea2.setText(cliente.getObservacionescliente());
                    jButton6.setName(String.valueOf(cliente.getIdcliente()));
                    jButton8.setName(String.valueOf(cliente.getIdcliente()));
                    jButton8.setEnabled(true);
                    changePanel(clienteForm);
                }
            }
        });
        jTable4.getColumnModel().getColumn(7).setCellRenderer(new ButtonTableComponent("[+]"));
        jTable5.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = jTable5.rowAtPoint(evt.getPoint());
                int col = jTable5.columnAtPoint(evt.getPoint());
                if(col == 7){
                   
                        Usuario usuario = (Usuario)jTable5.getValueAt(row, col);
                        selectedEntity = usuario;
                        
                        jTextField13.setText(usuario.getRunusuario());
                        jTextField14.setText(usuario.getNombresusuario());
                        jTextField15.setText(usuario.getAppaterno());
                        jTextField16.setText(usuario.getApmaterno());
                        ;
                        try {
                            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            jFormattedTextField3.setText(date.format(dt.parse(usuario.getFnacimientousuario())));
                        } catch (ParseException ex) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        jTextField20.setText(usuario.getTelusuario());
                        jTextField21.setText(usuario.getMailusuario());
                        jComboBox1.setSelectedIndex((int)usuario.getPerfilidperfil() - 1);
                        jComboBox2.setSelectedIndex((int)usuario.getEstadousuario() - 1);
                        jComboBox3.setSelectedItem(usuario.getSexousuario());
                        
                        jButton10.setName(String.valueOf(usuario.getIdusuario()));
                        jButton12.setName(String.valueOf(usuario.getIdusuario()));
                        jButton12.setEnabled(true);
                        changePanel(usuarioForm);
                    
                }
            }
        });
        jTable5.getColumnModel().getColumn(7).setCellRenderer(new ButtonTableComponent("[+]"));
        
        jTable6.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = jTable6.rowAtPoint(evt.getPoint());
                int col = jTable6.columnAtPoint(evt.getPoint());
                if(col == 2){
                   
                        Capacitacion capacitacion = (Capacitacion)jTable6.getValueAt(row, col);
                        selectedEntity = capacitacion;
                        
                        jTextField30.setText(capacitacion.getNombrecapacitacion());                        
                        jComboBox5.setSelectedIndex((int)capacitacion.getEstadocapacitacion() - 1);
                        
                        jButton17.setName(String.valueOf(capacitacion.getIdcap()));
                        jButton19.setName(String.valueOf(capacitacion.getIdcap()));
                        jButton19.setEnabled(true);
                        changePanel(tipoCapacitacionForm);
                    
                }
            }
        });
        jTable6.getColumnModel().getColumn(2).setCellRenderer(new ButtonTableComponent("[+]"));
    }
    
    private void changePanel(JInternalFrame panel){
        Container root = getRootPane().getContentPane();
        if(root.getComponentCount() > 0){
            if(root.getComponent(0) == panel) {
                panel.setVisible(true);
                return;
            }
            root.remove(root.getComponent(0));
        }        
        root.add(panel);
        panel.repaint();
        panel.setVisible(true);
    }
    
    public void signin(){
        configureRoleMenu();        
        jLabelProfile.setText(token.getRoleName());
        this.setVisible(true);
        this.loginForm.setVisible(false);
        session.start();
    }
    
    public void logout(String reason){
        session.stop();
        this.setVisible(false);
        setJMenuBar(null);                
        this.loginForm.getPasswordField().setText("");
        this.loginForm.getLabelMessage().setText(reason);
        this.loginForm.setVisible(true);
    }
    
    public Login getLoginForm(){
        return this.loginForm;
    }
    
    private void configureRoleMenu(){   
        javax.swing.JMenuBar currentMenu = null;
        switch(token.getUserRole()){
            case "ROLE_ADMIN": currentMenu = jMenuAdmin;                
            break;            
            case "ROLE_SUPERVISOR": currentMenu = jMenuSupervisor;
            break;
            case "ROLE_ENGINER": currentMenu =jMenuEnginer;
            break;
        }
        jMenuProfile.setText(token.getUser().getFullName());
        if(currentMenu instanceof javax.swing.JMenuBar){
            currentMenu.add(horizontalGlue);
            currentMenu.add(jMenuProfile);
            setJMenuBar(currentMenu);
        }
    }
    
    private void initUserMenu(){
        jMenuProfile = new javax.swing.JMenu();
        horizontalGlue = javax.swing.Box.createHorizontalGlue();
        javax.swing.JMenuItem item = new javax.swing.JMenuItem();
        item.setText("Inicio");
        item.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Container root = getRootPane().getContentPane();
                for(Component c: root.getComponents()){
                    root.remove(c);
                }
                root.add(jPanelWelcome);
                jPanelWelcome.repaint();
            }
        });
        jMenuProfile.add(item);
        
        item = new javax.swing.JMenuItem();
        item.setText("Cerrar Sesión");
        item.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logout("Su sesión se ha cerrado con exito.");
            }
        });
        jMenuProfile.add(item);                  
        jMenuProfile.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
    }
    
    private Properties getConfig() throws IOException {
        Properties prop = new Properties();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(
                System.getProperty("user.dir") +
                "/resources/config.properties"
        );

        if (inputStream instanceof InputStream) {
            prop.load(inputStream);
        } else {
            throw new FileNotFoundException("falta archivo file 'config.properties'");
        }
        
        return prop;
    }
    
    private void openWebUrl(String url) {
        java.net.URI uri = java.net.URI.create(url);
        try {
            java.awt.Desktop.getDesktop().browse(uri);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuSupervisor = new javax.swing.JMenuBar();
        jMenuTerreno = new javax.swing.JMenu();
        jMenuTerCrear = new javax.swing.JMenuItem();
        jMenuTerListar = new javax.swing.JMenuItem();
        jMenuTerEditar = new javax.swing.JMenuItem();
        jMenuTerEliminar = new javax.swing.JMenuItem();
        jMenuPlanCap = new javax.swing.JMenu();
        jMenuPlanSalud = new javax.swing.JMenu();
        jMenuExpositor = new javax.swing.JMenu();
        jMenuMedico = new javax.swing.JMenu();
        jMenuMedListar1 = new javax.swing.JMenuItem();
        jMenuMedEditar1 = new javax.swing.JMenuItem();
        jMenuCal = new javax.swing.JMenu();
        jMenuAdmin = new javax.swing.JMenuBar();
        jMenuEmpresa = new javax.swing.JMenu();
        jMenuEmpListar = new javax.swing.JMenuItem();
        jMenuEmpCrear = new javax.swing.JMenuItem();
        jMenuUsuario = new javax.swing.JMenu();
        jMenuUsrListar = new javax.swing.JMenuItem();
        jMenuUsrCrear = new javax.swing.JMenuItem();
        jMenuCapacitacion = new javax.swing.JMenu();
        jMenuCapListar = new javax.swing.JMenuItem();
        jMenuCapCrear = new javax.swing.JMenuItem();
        jMenuExamen = new javax.swing.JMenu();
        jMenuExmListar = new javax.swing.JMenuItem();
        jMenuExmCrear = new javax.swing.JMenuItem();
        jMenuEnginer = new javax.swing.JMenuBar();
        jMenuTerrenoEng = new javax.swing.JMenu();
        jMenuTerEngListar = new javax.swing.JMenuItem();
        clienteMain = new javax.swing.JInternalFrame();
        jButton5 = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jTextField11 = new javax.swing.JTextField();
        jInternalFrame2 = new javax.swing.JInternalFrame();
        jComboBoxEstado = new javax.swing.JComboBox<>();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jFormattedTextField2 = new javax.swing.JFormattedTextField();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jComboBoxTipo = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jInternalFrame3 = new javax.swing.JInternalFrame();
        jLabel9 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabelRut = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabelTipoVisita = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabelDireccionVisita = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextAreaDescVisita = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextAreaObsVisita = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        clienteForm = new javax.swing.JInternalFrame();
        jLabelClienteTitle = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jTextField10 = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        usuarioMain = new javax.swing.JInternalFrame();
        jButton9 = new javax.swing.JButton();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        jTable5 = new javax.swing.JTable();
        jTextField12 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jComboBoxEstado1 = new javax.swing.JComboBox<>();
        usuarioForm = new javax.swing.JInternalFrame();
        jLabelUsuarioTitle = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jTextField13 = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        jTextField14 = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jTextField15 = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        jTextField16 = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jTextField20 = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        jTextField21 = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel43 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jComboBox3 = new javax.swing.JComboBox<>();
        jFormattedTextField3 = new javax.swing.JFormattedTextField();
        tipoCapacitacionMain = new javax.swing.JInternalFrame();
        jButton16 = new javax.swing.JButton();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jScrollPane11 = new javax.swing.JScrollPane();
        jTable6 = new javax.swing.JTable();
        jTextField29 = new javax.swing.JTextField();
        tipoCapacitacionForm = new javax.swing.JInternalFrame();
        jLabelTipoCapacitacionTitle = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jTextField30 = new javax.swing.JTextField();
        jButton17 = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        jButton19 = new javax.swing.JButton();
        jLabel64 = new javax.swing.JLabel();
        jComboBox5 = new javax.swing.JComboBox<>();
        jPanelWelcome = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabelProfile = new javax.swing.JLabel();

        jMenuSupervisor.setOpaque(false);

        jMenuTerreno.setText("Evaluación En Terreno");
        jMenuTerreno.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jMenuTerreno.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jMenuTerCrear.setText("Crear");
        jMenuTerCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuTerCrearActionPerformed(evt);
            }
        });
        jMenuTerreno.add(jMenuTerCrear);

        jMenuTerListar.setText("Listar");
        jMenuTerListar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuTerListarActionPerformed(evt);
            }
        });
        jMenuTerreno.add(jMenuTerListar);

        jMenuTerEditar.setText("Editar");
        jMenuTerEditar.setEnabled(false);
        jMenuTerreno.add(jMenuTerEditar);

        jMenuTerEliminar.setText("Eliminar");
        jMenuTerEliminar.setEnabled(false);
        jMenuTerreno.add(jMenuTerEliminar);

        jMenuSupervisor.add(jMenuTerreno);

        jMenuPlanCap.setText("Plan Capacitación");
        jMenuPlanCap.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jMenuSupervisor.add(jMenuPlanCap);

        jMenuPlanSalud.setText("Plan de Salud");
        jMenuPlanSalud.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jMenuSupervisor.add(jMenuPlanSalud);

        jMenuExpositor.setText("Expositores");
        jMenuExpositor.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jMenuSupervisor.add(jMenuExpositor);

        jMenuMedico.setText("Médicos");
        jMenuMedico.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jMenuMedListar1.setText("Listar");
        jMenuMedico.add(jMenuMedListar1);

        jMenuMedEditar1.setText("Editar");
        jMenuMedEditar1.setEnabled(false);
        jMenuMedico.add(jMenuMedEditar1);

        jMenuSupervisor.add(jMenuMedico);

        jMenuCal.setText("Calendario");
        jMenuCal.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jMenuSupervisor.add(jMenuCal);

        jMenuAdmin.setOpaque(false);

        jMenuEmpresa.setText("Empresas");

        jMenuEmpListar.setText("Listar");
        jMenuEmpListar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuEmpListarActionPerformed(evt);
            }
        });
        jMenuEmpresa.add(jMenuEmpListar);

        jMenuEmpCrear.setText("Crear");
        jMenuEmpCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuEmpCrearActionPerformed(evt);
            }
        });
        jMenuEmpresa.add(jMenuEmpCrear);

        jMenuAdmin.add(jMenuEmpresa);

        jMenuUsuario.setText("Usuarios");

        jMenuUsrListar.setText("Listar");
        jMenuUsrListar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuUsrListarActionPerformed(evt);
            }
        });
        jMenuUsuario.add(jMenuUsrListar);

        jMenuUsrCrear.setText("Crear");
        jMenuUsrCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuUsrCrearActionPerformed(evt);
            }
        });
        jMenuUsuario.add(jMenuUsrCrear);

        jMenuAdmin.add(jMenuUsuario);

        jMenuCapacitacion.setText("Capacitaciones");

        jMenuCapListar.setText("Listar");
        jMenuCapListar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuCapListarActionPerformed(evt);
            }
        });
        jMenuCapacitacion.add(jMenuCapListar);

        jMenuCapCrear.setText("Crear");
        jMenuCapCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuCapCrearActionPerformed(evt);
            }
        });
        jMenuCapacitacion.add(jMenuCapCrear);

        jMenuAdmin.add(jMenuCapacitacion);

        jMenuExamen.setText("Exámenes");

        jMenuExmListar.setText("Listar");
        jMenuExamen.add(jMenuExmListar);

        jMenuExmCrear.setText("Crear");
        jMenuExamen.add(jMenuExmCrear);

        jMenuAdmin.add(jMenuExamen);

        jMenuEnginer.setOpaque(false);

        jMenuTerrenoEng.setText("Evaluaciones en Terreno");
        jMenuTerrenoEng.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenuTerrenoEngMouseClicked(evt);
            }
        });

        jMenuTerEngListar.setText("Listar");
        jMenuTerEngListar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuTerEngListarActionPerformed(evt);
            }
        });
        jMenuTerrenoEng.add(jMenuTerEngListar);

        jMenuEnginer.add(jMenuTerrenoEng);

        clienteMain.setClosable(true);
        clienteMain.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        clienteMain.setTitle("Empresas");
        clienteMain.setOpaque(false);
        clienteMain.setVisible(true);

        jButton5.setText("Buscar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel15.setText("Rut Empresa");
        jLabel15.setToolTipText("");

        jLabel16.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("Listado de empresas");
        jLabel16.setToolTipText("");

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Creado el", "RUT", "Nombre", "Contacto", "Tipo", "Telefono", "Email", "Detalles"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable4.getTableHeader().setReorderingAllowed(false);
        jScrollPane7.setViewportView(jTable4);
        jTable4.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        if (jTable4.getColumnModel().getColumnCount() > 0) {
            jTable4.getColumnModel().getColumn(0).setHeaderValue("Creado el");
            jTable4.getColumnModel().getColumn(1).setHeaderValue("RUT");
            jTable4.getColumnModel().getColumn(3).setHeaderValue("Contacto");
            jTable4.getColumnModel().getColumn(4).setHeaderValue("Tipo");
            jTable4.getColumnModel().getColumn(5).setHeaderValue("Telefono");
            jTable4.getColumnModel().getColumn(7).setHeaderValue("Editar");
        }

        javax.swing.GroupLayout clienteMainLayout = new javax.swing.GroupLayout(clienteMain.getContentPane());
        clienteMain.getContentPane().setLayout(clienteMainLayout);
        clienteMainLayout.setHorizontalGroup(
            clienteMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, clienteMainLayout.createSequentialGroup()
                .addContainerGap(329, Short.MAX_VALUE)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5)
                .addGap(8, 8, 8))
            .addGroup(clienteMainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(clienteMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane7))
                .addContainerGap())
        );
        clienteMainLayout.setVerticalGroup(
            clienteMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(clienteMainLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel16)
                .addGap(18, 18, 18)
                .addGroup(clienteMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jButton5)
                    .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(50, Short.MAX_VALUE))
        );

        jInternalFrame2.setClosable(true);
        jInternalFrame2.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        jInternalFrame2.setVisible(true);

        jComboBoxEstado.setModel(new javax.swing.DefaultComboBoxModel(com.safe.service.SolicitudService.ESTADOS));
        jComboBoxEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxEstadoActionPerformed(evt);
            }
        });

        jFormattedTextField1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("dd/MM/yyyy"))));
        jFormattedTextField1.setToolTipText("dd/mm/yyyy");

        jFormattedTextField2.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter()));
        jFormattedTextField2.setToolTipText("dd/mm/yyyy");

        jButton1.setText("Buscar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel3.setText("Filtro de Estado");
        jLabel3.setToolTipText("");

        jLabel4.setText("Filtro de Fecha");
        jLabel4.setToolTipText("");

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Listado de evaluaciones en terreno");
        jLabel5.setToolTipText("");

        jLabel6.setText("Filtro tipo visita");
        jLabel6.setToolTipText("");

        jComboBoxTipo.setModel(new javax.swing.DefaultComboBoxModel(com.safe.service.SolicitudService.TIPOS));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Creado el", "Derivado el", "Técnico", "Cliente", "Tipo", "Estado", "Gestionar", "PDF"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setColumnSelectionAllowed(true);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);
        jTable1.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        javax.swing.GroupLayout jInternalFrame2Layout = new javax.swing.GroupLayout(jInternalFrame2.getContentPane());
        jInternalFrame2.getContentPane().setLayout(jInternalFrame2Layout);
        jInternalFrame2Layout.setHorizontalGroup(
            jInternalFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jInternalFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jInternalFrame2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jInternalFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jInternalFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jComboBoxEstado, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jInternalFrame2Layout.createSequentialGroup()
                                .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jFormattedTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton1)))))
                .addContainerGap())
        );
        jInternalFrame2Layout.setVerticalGroup(
            jInternalFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame2Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jInternalFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jFormattedTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(jInternalFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel6)
                    .addComponent(jComboBoxTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(80, Short.MAX_VALUE))
        );

        jInternalFrame3.setClosable(true);
        jInternalFrame3.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        jInternalFrame3.setVisible(true);

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Gestión de formulario");
        jLabel9.setToolTipText("");

        jTabbedPane1.setName(""); // NOI18N
        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseClicked(evt);
            }
        });

        jButton3.setText("Volver");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel11.setText("RUT Cliente");

        jLabelRut.setText("00.000.000-0");
        jLabelRut.setToolTipText("");

        jLabel13.setText("Tipo de visita");

        jLabelTipoVisita.setText("TIPO");

        jLabel12.setText("Dirección visita");

        jLabelDireccionVisita.setText("DIRECCION");

        jLabel17.setText("Descripción visita");

        jLabel18.setText("Observaciones");

        jTextAreaDescVisita.setEditable(false);
        jScrollPane3.setViewportView(jTextAreaDescVisita);

        jTextAreaObsVisita.setEditable(false);
        jScrollPane4.setViewportView(jTextAreaObsVisita);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel13))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelTipoVisita)
                            .addComponent(jLabelRut)))
                    .addComponent(jLabelDireccionVisita, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addComponent(jLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane4)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(jLabelRut))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(jLabelTipoVisita))
                        .addGap(24, 24, 24)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabelDireccionVisita))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addGap(71, 71, 71)
                        .addComponent(jLabel18))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(237, 237, 237)
                        .addComponent(jButton3)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 186, Short.MAX_VALUE)
                .addComponent(jButton3)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Detalle", jPanel1);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        jButton2.setText("Derivar a supervisor");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton4.setText("Rechazar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Observaciones al supervisor");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(114, 114, 114)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton4))
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(216, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(287, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addGap(4, 4, 4)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton4))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Derivar Evaluación", jPanel2);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Fecha", "Hora", "Descripción"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane5.setViewportView(jTable2);

        jLabel8.setText("Observaciones de supervisor");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 627, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Observaciones Supervisor", jPanel3);

        jLabel10.setText("Observaciones del ingeniero");

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Fecha", "Hora", "Descripción"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane6.setViewportView(jTable3);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 627, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Observaciones Ingeniero", jPanel4);

        javax.swing.GroupLayout jInternalFrame3Layout = new javax.swing.GroupLayout(jInternalFrame3.getContentPane());
        jInternalFrame3.getContentPane().setLayout(jInternalFrame3Layout);
        jInternalFrame3Layout.setHorizontalGroup(
            jInternalFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jInternalFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jInternalFrame3Layout.setVerticalGroup(
            jInternalFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame3Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        clienteForm.setVisible(true);

        jLabelClienteTitle.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabelClienteTitle.setText("Creacion de empresa");

        jLabel19.setText("Razón Social");

        jLabel20.setText("RUT");

        jLabel21.setText("Giro");

        jLabel22.setText("Dirección");

        jLabel23.setText("Comuna");

        jLabel24.setText("Fono Oficina");

        jLabel25.setText("Nombe Contacto");

        jLabel26.setText("Fono Contacto");

        jLabel27.setText("Mail Contacto");

        jLabel28.setText("Cargo Contacto");

        jLabel29.setText("Observaciones");

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane8.setViewportView(jTextArea2);

        jButton6.setText("Guardar");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setText("Cancelar");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setText("Eliminar");
        jButton8.setEnabled(false);
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout clienteFormLayout = new javax.swing.GroupLayout(clienteForm.getContentPane());
        clienteForm.getContentPane().setLayout(clienteFormLayout);
        clienteFormLayout.setHorizontalGroup(
            clienteFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(clienteFormLayout.createSequentialGroup()
                .addGap(274, 274, 274)
                .addComponent(jLabelClienteTitle)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(clienteFormLayout.createSequentialGroup()
                .addGroup(clienteFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(clienteFormLayout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addGroup(clienteFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(clienteFormLayout.createSequentialGroup()
                                .addComponent(jLabel24)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField6, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE))
                            .addGroup(clienteFormLayout.createSequentialGroup()
                                .addGroup(clienteFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel21)
                                    .addComponent(jLabel22)
                                    .addComponent(jLabel23)
                                    .addComponent(jLabel20)
                                    .addComponent(jLabel19))
                                .addGap(16, 16, 16)
                                .addGroup(clienteFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField1)
                                    .addComponent(jTextField2)
                                    .addComponent(jTextField5)
                                    .addComponent(jTextField4)
                                    .addComponent(jTextField3))))
                        .addGap(18, 18, 18)
                        .addGroup(clienteFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(clienteFormLayout.createSequentialGroup()
                                .addComponent(jLabel28)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(clienteFormLayout.createSequentialGroup()
                                .addComponent(jLabel29)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(clienteFormLayout.createSequentialGroup()
                                .addComponent(jLabel25)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(clienteFormLayout.createSequentialGroup()
                                .addComponent(jLabel26)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(clienteFormLayout.createSequentialGroup()
                                .addComponent(jLabel27)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, clienteFormLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton6)))
                .addContainerGap())
        );
        clienteFormLayout.setVerticalGroup(
            clienteFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(clienteFormLayout.createSequentialGroup()
                .addComponent(jLabelClienteTitle)
                .addGap(23, 23, 23)
                .addGroup(clienteFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(clienteFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(clienteFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27)
                    .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(clienteFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28)
                    .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(clienteFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(clienteFormLayout.createSequentialGroup()
                        .addGroup(clienteFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel23)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel29))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(clienteFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel24)
                            .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(clienteFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton6)
                    .addComponent(jButton7)
                    .addComponent(jButton8))
                .addContainerGap())
        );

        usuarioMain.setClosable(true);
        usuarioMain.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        usuarioMain.setTitle("Usuarios");
        usuarioMain.setOpaque(false);
        usuarioMain.setVisible(true);

        jButton9.setText("Buscar");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jLabel30.setText("Rut usuario");
        jLabel30.setToolTipText("");

        jLabel31.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel31.setText("Listado de usuarios");
        jLabel31.setToolTipText("");

        jTable5.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Creado el", "RUT", "Nombre", "Perfil", "Telefono", "Email", "Estado", "Editar"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable5.setColumnSelectionAllowed(true);
        jTable5.getTableHeader().setReorderingAllowed(false);
        jScrollPane9.setViewportView(jTable5);
        jTable5.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        jLabel14.setText("Filtro de Estado");
        jLabel14.setToolTipText("");

        jComboBoxEstado1.setModel(new javax.swing.DefaultComboBoxModel(com.safe.service.UsuarioService.ESTADOS));
        jComboBoxEstado1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxEstado1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout usuarioMainLayout = new javax.swing.GroupLayout(usuarioMain.getContentPane());
        usuarioMain.getContentPane().setLayout(usuarioMainLayout);
        usuarioMainLayout.setHorizontalGroup(
            usuarioMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(usuarioMainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(usuarioMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(usuarioMainLayout.createSequentialGroup()
                        .addGroup(usuarioMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel31, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane9))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, usuarioMainLayout.createSequentialGroup()
                        .addGap(0, 307, Short.MAX_VALUE)
                        .addGroup(usuarioMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(usuarioMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jComboBoxEstado1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextField12, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton9)
                        .addGap(8, 8, 8))))
        );
        usuarioMainLayout.setVerticalGroup(
            usuarioMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(usuarioMainLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel31)
                .addGap(18, 18, 18)
                .addGroup(usuarioMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(jButton9)
                    .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(usuarioMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxEstado1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(50, Short.MAX_VALUE))
        );

        usuarioForm.setVisible(true);

        jLabelUsuarioTitle.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabelUsuarioTitle.setText("Creacion de usuario");

        jLabel32.setText("RUN");

        jLabel33.setText("Nombres");

        jLabel34.setText("Apellido paterno");

        jLabel35.setText("Apellido materno");

        jLabel36.setText("Fecha Nacimiento");

        jLabel38.setText("Gereno");

        jLabel39.setText("Fono Contacto");

        jLabel40.setText("Mail Contacto");

        jLabel42.setText("Perfil");

        jButton10.setText("Guardar");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton11.setText("Cancelar");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jButton12.setText("Eliminar");
        jButton12.setEnabled(false);
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(Arrays.copyOfRange(com.safe.service.UsuarioService.PERFIL, 1, 7)));

        jLabel43.setText("Estado");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(Arrays.copyOfRange(com.safe.service.UsuarioService.ESTADOS, 1, 4)));

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel(com.safe.service.UsuarioService.GENERO));

        jFormattedTextField3.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter()));
        jFormattedTextField3.setToolTipText("dd-mm-yyyy");
        jFormattedTextField3.setPreferredSize(new java.awt.Dimension(4, 20));

        javax.swing.GroupLayout usuarioFormLayout = new javax.swing.GroupLayout(usuarioForm.getContentPane());
        usuarioForm.getContentPane().setLayout(usuarioFormLayout);
        usuarioFormLayout.setHorizontalGroup(
            usuarioFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(usuarioFormLayout.createSequentialGroup()
                .addGroup(usuarioFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, usuarioFormLayout.createSequentialGroup()
                        .addGroup(usuarioFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(usuarioFormLayout.createSequentialGroup()
                                .addGap(41, 41, 41)
                                .addGroup(usuarioFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel34)
                                    .addComponent(jLabel35)
                                    .addComponent(jLabel33)
                                    .addComponent(jLabel32))
                                .addGap(20, 20, 20))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, usuarioFormLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel36)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addGroup(usuarioFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField13)
                            .addComponent(jTextField14)
                            .addComponent(jTextField15)
                            .addComponent(jTextField16)
                            .addGroup(usuarioFormLayout.createSequentialGroup()
                                .addComponent(jFormattedTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(usuarioFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(usuarioFormLayout.createSequentialGroup()
                                .addComponent(jLabel43)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, usuarioFormLayout.createSequentialGroup()
                                .addComponent(jLabel40)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                                .addComponent(jTextField21, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, usuarioFormLayout.createSequentialGroup()
                                .addComponent(jLabel42)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, usuarioFormLayout.createSequentialGroup()
                                .addGroup(usuarioFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel39)
                                    .addComponent(jLabel38))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(usuarioFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jComboBox3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jTextField20, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE))))
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, usuarioFormLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton10))
                    .addGroup(usuarioFormLayout.createSequentialGroup()
                        .addGap(274, 274, 274)
                        .addComponent(jLabelUsuarioTitle)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        usuarioFormLayout.setVerticalGroup(
            usuarioFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(usuarioFormLayout.createSequentialGroup()
                .addComponent(jLabelUsuarioTitle)
                .addGap(20, 20, 20)
                .addGroup(usuarioFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel38)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(usuarioFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel39)
                    .addComponent(jTextField20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(usuarioFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel40)
                    .addComponent(jTextField21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(usuarioFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35)
                    .addComponent(jTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(usuarioFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36)
                    .addComponent(jLabel42)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFormattedTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(usuarioFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel43)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addGroup(usuarioFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton10)
                    .addComponent(jButton11)
                    .addComponent(jButton12))
                .addContainerGap())
        );

        tipoCapacitacionMain.setClosable(true);
        tipoCapacitacionMain.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        tipoCapacitacionMain.setTitle("Capacitaciones");
        tipoCapacitacionMain.setOpaque(false);
        tipoCapacitacionMain.setVisible(true);

        jButton16.setText("Buscar");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        jLabel53.setText("Nombre tipo Capacitación");
        jLabel53.setToolTipText("");

        jLabel54.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel54.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel54.setText("Listado de Tipos de Capacitación");
        jLabel54.setToolTipText("");

        jTable6.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre", "Estado", "Editar"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable6.setColumnSelectionAllowed(true);
        jTable6.getTableHeader().setReorderingAllowed(false);
        jScrollPane11.setViewportView(jTable6);
        jTable6.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        javax.swing.GroupLayout tipoCapacitacionMainLayout = new javax.swing.GroupLayout(tipoCapacitacionMain.getContentPane());
        tipoCapacitacionMain.getContentPane().setLayout(tipoCapacitacionMainLayout);
        tipoCapacitacionMainLayout.setHorizontalGroup(
            tipoCapacitacionMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tipoCapacitacionMainLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField29, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton16)
                .addGap(8, 8, 8))
            .addGroup(tipoCapacitacionMainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tipoCapacitacionMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel54, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE))
                .addContainerGap())
        );
        tipoCapacitacionMainLayout.setVerticalGroup(
            tipoCapacitacionMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tipoCapacitacionMainLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel54)
                .addGap(18, 18, 18)
                .addGroup(tipoCapacitacionMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel53)
                    .addComponent(jButton16)
                    .addComponent(jTextField29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(50, Short.MAX_VALUE))
        );

        tipoCapacitacionForm.setVisible(true);

        jLabelTipoCapacitacionTitle.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabelTipoCapacitacionTitle.setText("Creacion de tipo de capacitación");

        jLabel55.setText("Nombre");

        jButton17.setText("Guardar");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });

        jButton18.setText("Cancelar");
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });

        jButton19.setText("Eliminar");
        jButton19.setEnabled(false);
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });

        jLabel64.setText("Estado");

        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel(Arrays.copyOfRange(com.safe.service.TipoCapacitacionService.ESTADOS, 1, 3)));

        javax.swing.GroupLayout tipoCapacitacionFormLayout = new javax.swing.GroupLayout(tipoCapacitacionForm.getContentPane());
        tipoCapacitacionForm.getContentPane().setLayout(tipoCapacitacionFormLayout);
        tipoCapacitacionFormLayout.setHorizontalGroup(
            tipoCapacitacionFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tipoCapacitacionFormLayout.createSequentialGroup()
                .addGroup(tipoCapacitacionFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, tipoCapacitacionFormLayout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addGroup(tipoCapacitacionFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel55)
                            .addComponent(jLabel64))
                        .addGap(92, 92, 92)
                        .addGroup(tipoCapacitacionFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField30)
                            .addComponent(jComboBox5, 0, 175, Short.MAX_VALUE)))
                    .addGroup(tipoCapacitacionFormLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton19)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton17)
                .addContainerGap())
            .addGroup(tipoCapacitacionFormLayout.createSequentialGroup()
                .addGap(149, 149, 149)
                .addComponent(jLabelTipoCapacitacionTitle)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        tipoCapacitacionFormLayout.setVerticalGroup(
            tipoCapacitacionFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tipoCapacitacionFormLayout.createSequentialGroup()
                .addComponent(jLabelTipoCapacitacionTitle)
                .addGap(23, 23, 23)
                .addGroup(tipoCapacitacionFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel55)
                    .addComponent(jTextField30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(tipoCapacitacionFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel64)
                    .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 67, Short.MAX_VALUE)
                .addGroup(tipoCapacitacionFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton17)
                    .addComponent(jButton18)
                    .addComponent(jButton19))
                .addContainerGap())
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema de administración SAFE");
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                formMouseMoved(evt);
            }
        });
        addWindowStateListener(new java.awt.event.WindowStateListener() {
            public void windowStateChanged(java.awt.event.WindowEvent evt) {
                formWindowStateChanged(evt);
            }
        });

        jPanelWelcome.setPreferredSize(new java.awt.Dimension(720, 400));

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 48)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Bienvenido");

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("Perfil de usuario:");

        jLabelProfile.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelProfile.setText("Administrador");

        javax.swing.GroupLayout jPanelWelcomeLayout = new javax.swing.GroupLayout(jPanelWelcome);
        jPanelWelcome.setLayout(jPanelWelcomeLayout);
        jPanelWelcomeLayout.setHorizontalGroup(
            jPanelWelcomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelWelcomeLayout.createSequentialGroup()
                .addGap(242, 242, 242)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelProfile, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(237, 237, 237))
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanelWelcomeLayout.setVerticalGroup(
            jPanelWelcomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelWelcomeLayout.createSequentialGroup()
                .addContainerGap(133, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 160, Short.MAX_VALUE)
                .addGroup(jPanelWelcomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelProfile)
                    .addComponent(jLabel2))
                .addGap(35, 35, 35))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 720, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanelWelcome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanelWelcome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuEmpCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuEmpCrearActionPerformed
        jTextField1.setText("");
        jTextField2.setText("");
        jTextField3.setText("");
        jTextField4.setText("");
        jTextField5.setText("");
        jTextField6.setText("");
        jTextField7.setText("");
        jTextField8.setText("");
        jTextField9.setText("");
        jTextField10.setText("");
        jTextArea2.setText("");
        jButton8.setEnabled(false);
        jButton6.setName(null);
        jButton8.setName(null);
        changePanel(clienteForm);
    }//GEN-LAST:event_jMenuEmpCrearActionPerformed

    private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved
        session.alive();
    }//GEN-LAST:event_formMouseMoved

    private void formWindowStateChanged(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowStateChanged
        System.out.println("Listo!");
    }//GEN-LAST:event_formWindowStateChanged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jMenuTerCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuTerCrearActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuTerCrearActionPerformed

    private void jMenuTerListarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuTerListarActionPerformed
        changePanel(jInternalFrame2);
    }//GEN-LAST:event_jMenuTerListarActionPerformed

    private void jMenuTerrenoEngMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuTerrenoEngMouseClicked

    }//GEN-LAST:event_jMenuTerrenoEngMouseClicked

    private void jMenuTerEngListarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuTerEngListarActionPerformed
        changePanel(jInternalFrame2);
        SolicitudService terreno = new SolicitudService(domain);
        SoliEvalTer[] solicitudes = terreno.getCollection();
        if(solicitudes != null ){
            DefaultTableModel model = (DefaultTableModel)jTable1.getModel();            
            model.setRowCount(0);
            for(SoliEvalTer s: solicitudes){
                Object[] item = {
                    s.getFechacreacion(),
                    s.getFechaderivacion(),
                    s.getTecnico().getNombresusuario(),
                    s.getClientenombre(),
                    SolicitudService.TIPOS[(int)s.getTipovisitteridtipovister()],
                    SolicitudService.ESTADOS[(int)s.getEstadosolievalter()],
                    s,
                    s.getPdf(),
                };
                model.addRow(item);
            }
        }
    }//GEN-LAST:event_jMenuTerEngListarActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // derivar a supervisor y dejar registro en historico de ingeniero
        
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        //rechazar y derivar al tecnico en terreno y queda registrado en el historico del supervidor
        
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTabbedPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MouseClicked
        int tab = jTabbedPane1.getSelectedIndex();
        switch(tab){
            case 0:
                jPanel1.add(jPanel5);
                break;
            case 1:
                jPanel2.add(jPanel5);
                break;
        }
    }//GEN-LAST:event_jTabbedPane1MouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        //buscar por rut empresa
        jTable4.setRowSorter(null);
        RowFilter<Object, Object> filter = new RowFilter<Object, Object>() {
            public boolean include(Entry entry) {
              if(entry.getValue(1) == null) return false;
              String r = (String) entry.getValue(1);
              return r.contains(jTextField11.getText()) || jTextField11.getText().length() == 0;
            }
        };
        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(jTable4.getModel());
        sorter.setRowFilter(filter);
        jTable4.setRowSorter(sorter);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jMenuEmpListarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuEmpListarActionPerformed
        //listar empresas
        Cliente[] clientes = clienteService.getCollection();
        if(clientes != null ){
            DefaultTableModel model = (DefaultTableModel)jTable4.getModel();            
            model.setRowCount(0);
            for(Cliente c: clientes){
                if(c.getEstadocliente() == 0) continue;
                Object[] item = {
                    date.format(new Date()), //TODO: falta fecha
                    c.getRutcliente(),
                    c.getRazonsocial(),
                    c.getNombrecontacto(),
                    "TIPO", //TODO: falta tipo
                    c.getTeloficina(),
                    c.getMailcontacto(),
                    c,
                };
                model.addRow(item);
            }
        }
        changePanel(clienteMain);
    }//GEN-LAST:event_jMenuEmpListarActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        //cancelar ventana cliente
        clienteForm.setVisible(false);
        jMenuEmpListarActionPerformed(evt);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        //guardar e inmediatamente permite editar
        
        Cliente cliente = new Cliente();
        if(jButton6.getName() != null && jButton6.getName().length() > 0) {
            cliente.setIdcliente(Integer.parseInt(jButton6.getName()));
        }
        cliente.setRazonsocial(jTextField1.getText());
        cliente.setRutcliente(jTextField2.getText());
        cliente.setGirocliente(jTextField3.getText());
        cliente.setDireccioncliente(jTextField4.getText());
        //jTextField5.setText("Santiago"); //TODO: falta deto del WS Debe ser Un combobox.
        cliente.setTeloficina(jTextField6.getText());
        cliente.setNombrecontacto(jTextField7.getText());
        cliente.setFonocontacto(jTextField8.getText());
        cliente.setMailcontacto(jTextField9.getText());
        cliente.setCargocontacto(jTextField10.getText());
        cliente.setObservacionescliente(jTextArea2.getText());
        cliente.setEstadocliente(1);
        
        //TODO: enviar al webservice el cliente
        long idcliente = clienteService.save(cliente);
        jButton6.setName(String.valueOf(idcliente));
        jButton8.setName(String.valueOf(idcliente));
        jLabelClienteTitle.setText("Editar Empresa");
        jButton8.setEnabled(true);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        int dialogResult = JOptionPane.showConfirmDialog (null, "¿Desea eliminar esta empresa?","Eliminar",JOptionPane.YES_NO_OPTION);
        if(dialogResult == JOptionPane.YES_OPTION){
            if(jButton8.getName() != null && jButton8.getName().length() > 0) {
                clienteService.delete(Long.parseLong(jButton8.getName()));
            }
            jButton8.setEnabled(false);
            clienteForm.setVisible(false);
            jMenuEmpListarActionPerformed(evt);
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // Buscar usuario por run
        jTable5.setRowSorter(null);
        RowFilter<Object, Object> filter = new RowFilter<Object, Object>() {
            @Override
            public boolean include(Entry entry) {
              if(entry.getValue(1) == null) return false;
              String r = (String) entry.getValue(1);
              return r.contains(jTextField12.getText()) || jTextField12.getText().length() == 0;
            }
        };
        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(jTable5.getModel());
        sorter.setRowFilter(filter);
        jTable5.setRowSorter(sorter);
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jComboBoxEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxEstadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxEstadoActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        Usuario usuario = new Usuario();
        if(jButton10.getName() != null && jButton10.getName().length() > 0) {
            usuario = (Usuario)selectedEntity;
        }
        
        usuario.setRunusuario(jTextField13.getText());
        usuario.setNombresusuario(jTextField14.getText());
        usuario.setAppaterno(jTextField15.getText());
        usuario.setApmaterno(jTextField16.getText());        
        try {
            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            usuario.setFnacimientousuario(dt.format(date.parse(jFormattedTextField3.getText())));
        } catch (ParseException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        usuario.setSexousuario(jComboBox3.getSelectedItem().toString());
        usuario.setTelusuario(jTextField20.getText());
        usuario.setMailusuario(jTextField21.getText());
        usuario.setPerfilidperfil(jComboBox1.getSelectedIndex() + 1);
        usuario.setEstadousuario(jComboBox2.getSelectedIndex() + 1);
        
        long id = usuarioService.save(usuario);
        jButton10.setName(String.valueOf(id));
        jButton12.setName(String.valueOf(id));
        jLabelUsuarioTitle.setText("Editar Usuario");
        jButton12.setEnabled(true);
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // Cancelar crear o editar usuario
        clienteForm.setVisible(false);
        jMenuUsrListarActionPerformed(evt);
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // eliminar usuario
        int dialogResult = JOptionPane.showConfirmDialog (null, "¿Desea eliminar este usuario?","Eliminar",JOptionPane.YES_NO_OPTION);
        if(dialogResult == JOptionPane.YES_OPTION){
            if(jButton12.getName() != null && jButton12.getName().length() > 0) {
                usuarioService.delete(Long.parseLong(jButton12.getName()));
            }
            jButton12.setEnabled(false);
            usuarioForm.setVisible(false);
            jMenuUsrListarActionPerformed(evt);
        }
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jMenuUsrListarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuUsrListarActionPerformed
        // listar usuarios
        Usuario[] usuarios = usuarioService.getCollection();
        if(usuarios != null ){
            DefaultTableModel model = (DefaultTableModel)jTable5.getModel();                    
            model.setRowCount(0);
            for(Usuario u: usuarios){
                Object[] item = {
                    date.format(new Date()), //TODO: falta fecha                    
                    u.getRunusuario(),
                    u.getNombresusuario(),
                    UsuarioService.PERFIL[(int)u.getPerfilidperfil()],
                    u.getTelusuario(),
                    u.getMailusuario(),
                    UsuarioService.ESTADOS[(int)u.getEstadousuario()],  
                    u,
                };
                model.addRow(item);
            }
        }
        changePanel(usuarioMain);
    }//GEN-LAST:event_jMenuUsrListarActionPerformed

    private void jMenuUsrCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuUsrCrearActionPerformed
        jTextField13.setText("");
        jTextField14.setText("");
        jTextField15.setText("");
        jTextField16.setText("");
        jFormattedTextField3.setText("");                        
        jTextField20.setText("");
        jTextField21.setText("");
        jComboBox1.setSelectedIndex(0);
        jComboBox2.setSelectedIndex(0);
        jComboBox3.setSelectedIndex(0);
        jButton12.setEnabled(false);
        jButton10.setName(null);
        jButton10.setName(null);
        changePanel(usuarioForm);
    }//GEN-LAST:event_jMenuUsrCrearActionPerformed

    private void jComboBoxEstado1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxEstado1ActionPerformed
        // Filtrar por estado
        String estado = jComboBoxEstado1.getSelectedItem().toString();
        jTable5.setRowSorter(null);
        RowFilter<Object, Object> filter = new RowFilter<Object, Object>() {
            @Override
            public boolean include(Entry entry) {
              if(entry.getValue(6) == null) return false;
              String r = (String) entry.getValue(6);
              return r.equals(estado) || estado.equals(UsuarioService.ESTADOS[0]);
            }
        };
        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(jTable5.getModel());
        sorter.setRowFilter(filter);
        jTable5.setRowSorter(sorter);
    }//GEN-LAST:event_jComboBoxEstado1ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        // Filtrar por capacitacion
        String nombre = jTextField29.getText();
        jTable6.setRowSorter(null);
        RowFilter<Object, Object> filter = new RowFilter<Object, Object>() {
            @Override
            public boolean include(Entry entry) {
              if(entry.getValue(0) == null) return false;
              String r = (String) entry.getValue(0);
              return r.contains(nombre) || nombre.equals("");
            }
        };
        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(jTable6.getModel());
        sorter.setRowFilter(filter);
        jTable6.setRowSorter(sorter);
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jMenuCapListarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuCapListarActionPerformed
        // listado de capacitaciones
        Capacitacion[] capacitaciones = capacitacionService.getCollection();
        if(capacitaciones != null ){
            DefaultTableModel model = (DefaultTableModel)jTable6.getModel();
            model.setRowCount(0);
            for(Capacitacion c: capacitaciones){
                Object[] item = {
                    c.getNombrecapacitacion(),
                    TipoCapacitacionService.ESTADOS[(int)c.getEstadocapacitacion()],
                    c,
                };
                model.addRow(item);
            }
        }
        changePanel(tipoCapacitacionMain);
    }//GEN-LAST:event_jMenuCapListarActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        // guardar capacitacion
        Capacitacion capacitacion = new Capacitacion();
        if(jButton17.getName() != null && jButton17.getName().length() > 0) {
            capacitacion = (Capacitacion)selectedEntity;
        }
        
        capacitacion.setNombrecapacitacion(jTextField30.getText());
        capacitacion.setEstadocapacitacion(jComboBox5.getSelectedIndex() + 1);       
        
        long id = capacitacionService.save(capacitacion);
        jButton17.setName(String.valueOf(id));
        jButton19.setName(String.valueOf(id));
        jLabelTipoCapacitacionTitle.setText("Editar Capacitacion");
        jButton19.setEnabled(true);
    }//GEN-LAST:event_jButton17ActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        // TODO add your handling code here:
        tipoCapacitacionForm.setVisible(false);
        jMenuCapListarActionPerformed(evt);
    }//GEN-LAST:event_jButton18ActionPerformed

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        // Eliminar capacitacion
        int dialogResult = JOptionPane.showConfirmDialog (null, "¿Desea eliminar esta capacitación?","Eliminar",JOptionPane.YES_NO_OPTION);
        if(dialogResult == JOptionPane.YES_OPTION){
            if(jButton19.getName() != null && jButton19.getName().length() > 0) {
                capacitacionService.delete(Long.parseLong(jButton19.getName()));
            }
            jButton19.setEnabled(false);
            tipoCapacitacionForm.setVisible(false);
            jMenuCapListarActionPerformed(evt);
        }
    }//GEN-LAST:event_jButton19ActionPerformed

    private void jMenuCapCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuCapCrearActionPerformed
        // Crear capacitacion
        jTextField30.setText("");
        jComboBox5.setSelectedIndex(0);
        jButton19.setEnabled(false);
        jButton19.setName(null);
        jButton17.setName(null);
        changePanel(tipoCapacitacionForm);
    }//GEN-LAST:event_jMenuCapCrearActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JInternalFrame clienteForm;
    private javax.swing.JInternalFrame clienteMain;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JComboBox<String> jComboBoxEstado;
    private javax.swing.JComboBox<String> jComboBoxEstado1;
    private javax.swing.JComboBox<String> jComboBoxTipo;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JFormattedTextField jFormattedTextField2;
    private javax.swing.JFormattedTextField jFormattedTextField3;
    private javax.swing.JInternalFrame jInternalFrame2;
    private javax.swing.JInternalFrame jInternalFrame3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelClienteTitle;
    private javax.swing.JLabel jLabelDireccionVisita;
    private javax.swing.JLabel jLabelProfile;
    private javax.swing.JLabel jLabelRut;
    private javax.swing.JLabel jLabelTipoCapacitacionTitle;
    private javax.swing.JLabel jLabelTipoVisita;
    private javax.swing.JLabel jLabelUsuarioTitle;
    private javax.swing.JMenuBar jMenuAdmin;
    private javax.swing.JMenu jMenuCal;
    private javax.swing.JMenuItem jMenuCapCrear;
    private javax.swing.JMenuItem jMenuCapListar;
    private javax.swing.JMenu jMenuCapacitacion;
    private javax.swing.JMenuItem jMenuEmpCrear;
    private javax.swing.JMenuItem jMenuEmpListar;
    private javax.swing.JMenu jMenuEmpresa;
    private javax.swing.JMenuBar jMenuEnginer;
    private javax.swing.JMenu jMenuExamen;
    private javax.swing.JMenuItem jMenuExmCrear;
    private javax.swing.JMenuItem jMenuExmListar;
    private javax.swing.JMenu jMenuExpositor;
    private javax.swing.JMenuItem jMenuMedEditar1;
    private javax.swing.JMenuItem jMenuMedListar1;
    private javax.swing.JMenu jMenuMedico;
    private javax.swing.JMenu jMenuPlanCap;
    private javax.swing.JMenu jMenuPlanSalud;
    private javax.swing.JMenuBar jMenuSupervisor;
    private javax.swing.JMenuItem jMenuTerCrear;
    private javax.swing.JMenuItem jMenuTerEditar;
    private javax.swing.JMenuItem jMenuTerEliminar;
    private javax.swing.JMenuItem jMenuTerEngListar;
    private javax.swing.JMenuItem jMenuTerListar;
    private javax.swing.JMenu jMenuTerreno;
    private javax.swing.JMenu jMenuTerrenoEng;
    private javax.swing.JMenuItem jMenuUsrCrear;
    private javax.swing.JMenuItem jMenuUsrListar;
    private javax.swing.JMenu jMenuUsuario;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanelWelcome;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTable jTable5;
    private javax.swing.JTable jTable6;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextArea jTextAreaDescVisita;
    private javax.swing.JTextArea jTextAreaObsVisita;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField20;
    private javax.swing.JTextField jTextField21;
    private javax.swing.JTextField jTextField29;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField30;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JInternalFrame tipoCapacitacionForm;
    private javax.swing.JInternalFrame tipoCapacitacionMain;
    private javax.swing.JInternalFrame usuarioForm;
    private javax.swing.JInternalFrame usuarioMain;
    // End of variables declaration//GEN-END:variables
}
