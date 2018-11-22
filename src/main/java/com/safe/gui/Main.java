/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safe.gui;

import com.safe.entity.Cliente;
import com.safe.gui.component.ButtonTableComponent;
import com.safe.entity.SoliEvalTer;
import com.safe.gui.component.WindowComponenet;
import com.safe.service.ClienteService;
import com.safe.service.TokenManager;
import com.safe.service.SessionManager;
import com.safe.service.SolicitudService;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JInternalFrame;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author familia
 */
public class Main extends javax.swing.JFrame {
    
    private final TokenManager token;
    private final Login loginForm;
    private final SessionManager session;
    private final ClienteService clienteService;
    
    private javax.swing.JMenu jMenuProfile;
    private java.awt.Component horizontalGlue;
    private final String domain;
    
    
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
        int sessionTime;
        try {
            sessionTime = (int)Integer.parseInt(getConfig().getProperty("safe.sessionMinuteTime"));
        } catch (IOException e) {
            sessionTime = 30;
        }
        System.out.println("Se establece la session a " + sessionTime + " minutos");
        /*
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int px = (screenSize.width / 2) - ( this.getWidth() / 2);
        int py = (screenSize.height / 2) - ( this.getHeight() / 2);
        */
        setLayout(new BorderLayout());
        //this.setLocation(new java.awt.Point(px, py));
        WindowComponenet.centerWindow(this);
        
        session = new SessionManager(this, sessionTime);
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
        jMenuMedicoSup = new javax.swing.JMenu();
        jMenuMedListar1 = new javax.swing.JMenuItem();
        jMenuMedEditar1 = new javax.swing.JMenuItem();
        jMenuCal = new javax.swing.JMenu();
        jMenuAdmin = new javax.swing.JMenuBar();
        jMenuEmpresa = new javax.swing.JMenu();
        jMenuEmpCrear = new javax.swing.JMenuItem();
        jMenuEmpListar = new javax.swing.JMenuItem();
        jMenuEmpEditar = new javax.swing.JMenuItem();
        jMenuEmpEliminar = new javax.swing.JMenuItem();
        jMenuUsuario = new javax.swing.JMenu();
        jMenuUsrCrear = new javax.swing.JMenuItem();
        jMenuUsrListar = new javax.swing.JMenuItem();
        jMenuUsrEditar = new javax.swing.JMenuItem();
        jMenuUsrEliminar = new javax.swing.JMenuItem();
        jMenuCapacitacion = new javax.swing.JMenu();
        jMenuCapCrear = new javax.swing.JMenuItem();
        jMenuCapListar = new javax.swing.JMenuItem();
        jMenuCapEditar = new javax.swing.JMenuItem();
        jMenuCapEliminar = new javax.swing.JMenuItem();
        jMenuExamen = new javax.swing.JMenu();
        jMenuExmCrear = new javax.swing.JMenuItem();
        jMenuExmListar = new javax.swing.JMenuItem();
        jMenuExmEditar = new javax.swing.JMenuItem();
        jMenuExmEliminar = new javax.swing.JMenuItem();
        jMenuMedico = new javax.swing.JMenu();
        jMenuMedCrear = new javax.swing.JMenuItem();
        jMenuMedListar = new javax.swing.JMenuItem();
        jMenuMedEditar = new javax.swing.JMenuItem();
        jMenuMedEliminar = new javax.swing.JMenuItem();
        jMenuEnginer = new javax.swing.JMenuBar();
        jMenuTerrenoEng = new javax.swing.JMenu();
        jMenuTerEngListar = new javax.swing.JMenuItem();
        jPanelTerrenoList = new javax.swing.JPanel();
        jDialog1 = new javax.swing.JDialog();
        jInternalFrame1 = new javax.swing.JInternalFrame();
        jFormattedTextField3 = new javax.swing.JFormattedTextField();
        jButton5 = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
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

        jMenuMedicoSup.setText("Médicos");
        jMenuMedicoSup.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jMenuMedListar1.setText("Listar");
        jMenuMedicoSup.add(jMenuMedListar1);

        jMenuMedEditar1.setText("Editar");
        jMenuMedEditar1.setEnabled(false);
        jMenuMedicoSup.add(jMenuMedEditar1);

        jMenuSupervisor.add(jMenuMedicoSup);

        jMenuCal.setText("Calendario");
        jMenuCal.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jMenuSupervisor.add(jMenuCal);

        jMenuAdmin.setOpaque(false);

        jMenuEmpresa.setText("Empresas");

        jMenuEmpCrear.setText("Crear");
        jMenuEmpCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuEmpCrearActionPerformed(evt);
            }
        });
        jMenuEmpresa.add(jMenuEmpCrear);

        jMenuEmpListar.setText("Listar");
        jMenuEmpListar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuEmpListarActionPerformed(evt);
            }
        });
        jMenuEmpresa.add(jMenuEmpListar);

        jMenuEmpEditar.setText("Editar");
        jMenuEmpEditar.setEnabled(false);
        jMenuEmpresa.add(jMenuEmpEditar);

        jMenuEmpEliminar.setText("Eliminar");
        jMenuEmpEliminar.setEnabled(false);
        jMenuEmpresa.add(jMenuEmpEliminar);

        jMenuAdmin.add(jMenuEmpresa);

        jMenuUsuario.setText("Usuarios");

        jMenuUsrCrear.setText("Crear");
        jMenuUsuario.add(jMenuUsrCrear);

        jMenuUsrListar.setText("Listar");
        jMenuUsuario.add(jMenuUsrListar);

        jMenuUsrEditar.setText("Editar");
        jMenuUsrEditar.setEnabled(false);
        jMenuUsuario.add(jMenuUsrEditar);

        jMenuUsrEliminar.setText("Eliminar");
        jMenuUsrEliminar.setEnabled(false);
        jMenuUsuario.add(jMenuUsrEliminar);

        jMenuAdmin.add(jMenuUsuario);

        jMenuCapacitacion.setText("Capacitaciones");

        jMenuCapCrear.setText("Crear");
        jMenuCapacitacion.add(jMenuCapCrear);

        jMenuCapListar.setText("Listar");
        jMenuCapacitacion.add(jMenuCapListar);

        jMenuCapEditar.setText("Editar");
        jMenuCapEditar.setEnabled(false);
        jMenuCapacitacion.add(jMenuCapEditar);

        jMenuCapEliminar.setText("Eliminar");
        jMenuCapEliminar.setEnabled(false);
        jMenuCapacitacion.add(jMenuCapEliminar);

        jMenuAdmin.add(jMenuCapacitacion);

        jMenuExamen.setText("Exámenes");

        jMenuExmCrear.setText("Crear");
        jMenuExamen.add(jMenuExmCrear);

        jMenuExmListar.setText("Listar");
        jMenuExamen.add(jMenuExmListar);

        jMenuExmEditar.setText("Editar");
        jMenuExmEditar.setEnabled(false);
        jMenuExamen.add(jMenuExmEditar);

        jMenuExmEliminar.setText("Eliminar");
        jMenuExmEliminar.setEnabled(false);
        jMenuExamen.add(jMenuExmEliminar);

        jMenuAdmin.add(jMenuExamen);

        jMenuMedico.setText("Médicos");

        jMenuMedCrear.setText("Crear");
        jMenuMedCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuMedCrearActionPerformed(evt);
            }
        });
        jMenuMedico.add(jMenuMedCrear);

        jMenuMedListar.setText("Listar");
        jMenuMedico.add(jMenuMedListar);

        jMenuMedEditar.setText("Editar");
        jMenuMedEditar.setEnabled(false);
        jMenuMedico.add(jMenuMedEditar);

        jMenuMedEliminar.setText("Eliminar");
        jMenuMedEliminar.setEnabled(false);
        jMenuMedico.add(jMenuMedEliminar);

        jMenuAdmin.add(jMenuMedico);

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

        jPanelTerrenoList.setPreferredSize(new java.awt.Dimension(720, 400));

        javax.swing.GroupLayout jPanelTerrenoListLayout = new javax.swing.GroupLayout(jPanelTerrenoList);
        jPanelTerrenoList.setLayout(jPanelTerrenoListLayout);
        jPanelTerrenoListLayout.setHorizontalGroup(
            jPanelTerrenoListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 720, Short.MAX_VALUE)
        );
        jPanelTerrenoListLayout.setVerticalGroup(
            jPanelTerrenoListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );

        jDialog1.setAlwaysOnTop(true);
        jDialog1.setMinimumSize(new java.awt.Dimension(680, 430));
        jDialog1.setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 680, Short.MAX_VALUE)
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 495, Short.MAX_VALUE)
        );

        jInternalFrame1.setClosable(true);
        jInternalFrame1.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        jInternalFrame1.setTitle("Empresas");
        jInternalFrame1.setOpaque(false);
        jInternalFrame1.setVisible(true);

        jFormattedTextField3.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("dd/MM/yyyy"))));
        jFormattedTextField3.setToolTipText("dd/mm/yyyy");

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

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 720, Short.MAX_VALUE)
            .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jInternalFrame1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 696, Short.MAX_VALUE)
                        .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jInternalFrame1Layout.createSequentialGroup()
                            .addGap(0, 0, Short.MAX_VALUE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jFormattedTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jButton5)))
                    .addContainerGap()))
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
            .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jInternalFrame1Layout.createSequentialGroup()
                    .addGap(30, 30, 30)
                    .addComponent(jLabel16)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton5)
                        .addComponent(jFormattedTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel15))
                    .addGap(18, 18, 18)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(30, Short.MAX_VALUE)))
        );

        jInternalFrame2.setClosable(true);
        jInternalFrame2.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        jInternalFrame2.setVisible(true);

        jComboBoxEstado.setModel(new javax.swing.DefaultComboBoxModel(com.safe.service.SolicitudService.ESTADOS));

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
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuEmpCrearActionPerformed

    private void jMenuMedCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuMedCrearActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuMedCrearActionPerformed

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
            jTable1.getColumnModel().getColumn(6).setCellRenderer(new ButtonTableComponent("[+]"));
            jTable1.getColumnModel().getColumn(7).setCellRenderer(new ButtonTableComponent("PDF"));
            if(jTable1.getListeners(java.util.EventListener.class).length == 0){            
                jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        int row = jTable1.rowAtPoint(evt.getPoint());
                        int col = jTable1.columnAtPoint(evt.getPoint());
                        if (col == 6) {
                            SoliEvalTer solicitud = (SoliEvalTer)jTable1.getValueAt(row, col);
                            Cliente cliente = clienteService.get((int)solicitud.getClienteidcliente());
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
            }
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
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jMenuEmpListarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuEmpListarActionPerformed
        //listar empresas
        ClienteService clientesSrv = new ClienteService(domain);
        Cliente[] clientes = clientesSrv.getCollection();
        if(clientes != null ){
            DefaultTableModel model = (DefaultTableModel)jTable4.getModel();
            jTable4.getColumnModel().getColumn(6).setCellRenderer(new ButtonTableComponent("[+]"));
            jTable4.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    int row = jTable4.rowAtPoint(evt.getPoint());
                    int col = jTable4.columnAtPoint(evt.getPoint());
                    if (col == 6) {
                        Cliente cliente = (Cliente)jTable4.getValueAt(row, col);
                    }
                }
            });            
            model.setRowCount(0);
            for(Cliente c: clientes){
                Object[] item = {
                    "FECHA",
                    c.getRutcliente(),
                    c.getRazonsocial(),
                    c.getNombrecontacto(),
                    c.getTeloficina(),
                    c.getMailcontacto(),
                    c,
                };
                model.addRow(item);
            }
        }
        changePanel(jInternalFrame1);
    }//GEN-LAST:event_jMenuEmpListarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox<String> jComboBoxEstado;
    private javax.swing.JComboBox<String> jComboBoxTipo;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JFormattedTextField jFormattedTextField2;
    private javax.swing.JFormattedTextField jFormattedTextField3;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JInternalFrame jInternalFrame2;
    private javax.swing.JInternalFrame jInternalFrame3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelDireccionVisita;
    private javax.swing.JLabel jLabelProfile;
    private javax.swing.JLabel jLabelRut;
    private javax.swing.JLabel jLabelTipoVisita;
    private javax.swing.JMenuBar jMenuAdmin;
    private javax.swing.JMenu jMenuCal;
    private javax.swing.JMenuItem jMenuCapCrear;
    private javax.swing.JMenuItem jMenuCapEditar;
    private javax.swing.JMenuItem jMenuCapEliminar;
    private javax.swing.JMenuItem jMenuCapListar;
    private javax.swing.JMenu jMenuCapacitacion;
    private javax.swing.JMenuItem jMenuEmpCrear;
    private javax.swing.JMenuItem jMenuEmpEditar;
    private javax.swing.JMenuItem jMenuEmpEliminar;
    private javax.swing.JMenuItem jMenuEmpListar;
    private javax.swing.JMenu jMenuEmpresa;
    private javax.swing.JMenuBar jMenuEnginer;
    private javax.swing.JMenu jMenuExamen;
    private javax.swing.JMenuItem jMenuExmCrear;
    private javax.swing.JMenuItem jMenuExmEditar;
    private javax.swing.JMenuItem jMenuExmEliminar;
    private javax.swing.JMenuItem jMenuExmListar;
    private javax.swing.JMenu jMenuExpositor;
    private javax.swing.JMenuItem jMenuMedCrear;
    private javax.swing.JMenuItem jMenuMedEditar;
    private javax.swing.JMenuItem jMenuMedEditar1;
    private javax.swing.JMenuItem jMenuMedEliminar;
    private javax.swing.JMenuItem jMenuMedListar;
    private javax.swing.JMenuItem jMenuMedListar1;
    private javax.swing.JMenu jMenuMedico;
    private javax.swing.JMenu jMenuMedicoSup;
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
    private javax.swing.JMenuItem jMenuUsrEditar;
    private javax.swing.JMenuItem jMenuUsrEliminar;
    private javax.swing.JMenuItem jMenuUsrListar;
    private javax.swing.JMenu jMenuUsuario;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanelTerrenoList;
    private javax.swing.JPanel jPanelWelcome;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextAreaDescVisita;
    private javax.swing.JTextArea jTextAreaObsVisita;
    // End of variables declaration//GEN-END:variables
}
