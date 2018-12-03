/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safe.gui;

import com.safe.entity.Capacitacion;
import com.safe.entity.Certificado;
import com.safe.entity.Cliente;
import com.safe.entity.Eval_Terr;
import com.safe.entity.Expositor;
import com.safe.entity.List_Asis_Cap;
import com.safe.entity.List_Trab_Cap;
import com.safe.entity.Medico;
import com.safe.entity.Obs_Ingeniero;
import com.safe.entity.Obs_Supervisor;
import com.safe.entity.PlanCapacitacion;
import com.safe.entity.Sesion_Cap;
import com.safe.entity.Sesion_Salud;
import com.safe.gui.component.ButtonTableComponent;
import com.safe.entity.SoliEvalTer;
import com.safe.entity.TipoCapacitacion;
import com.safe.entity.TipoExamen;
import com.safe.entity.Usuario;
import com.safe.gui.component.Bind;
import com.safe.gui.component.WindowComponent;
import com.safe.service.AsistenciaTrabajadorService;
import com.safe.service.CapacitacionService;
import com.safe.service.CertificadoService;
import com.safe.service.TipoCapacitacionService;
import com.safe.service.ClienteService;
import com.safe.service.EvaluacionTerrenoService;
import com.safe.service.ExpositorService;
import com.safe.service.ListTrabajadorService;
import com.safe.service.MedicoService;
import com.safe.service.ObservacionService;
import com.safe.service.PlanCapacitacionService;
import com.safe.service.SesionService;
import com.safe.service.TokenManager;
import com.safe.service.SessionManager;
import com.safe.service.SolicitudService;
import com.safe.service.TipoExamenService;
import com.safe.service.UsuarioService;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Properties;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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
    private final TipoCapacitacionService tipoCapacitacionService;
    private final TipoExamenService tipoExamenService;
    private final CapacitacionService capacitacionService;
    private final PlanCapacitacionService planCapacitacionService;
    private final SolicitudService solicitudService;
    private final EvaluacionTerrenoService evaluacionTerrenoService;
    private final ObservacionService observacionService;
    private final SesionService sesionService;
    private final ExpositorService expositorService;
    private final MedicoService medicoService;
    private final ListTrabajadorService listTrabajadorService;
    private final AsistenciaTrabajadorService asistenciaTrabajadorService;
    private final CertificadoService certificadoService;
    
    
    private javax.swing.JMenu jMenuProfile;
    private java.awt.Component horizontalGlue;
    private final String domain;
    private Object selectedEntity;
    private Object secondEntity;
    LinkedHashMap<Long, Capacitacion> mapCapcitacion;
    
    private final SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy");
    private final SimpleDateFormat dateInverted = new SimpleDateFormat("yyyy-MM-dd");

    
    
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
        tipoCapacitacionService = new TipoCapacitacionService(this.domain);
        tipoExamenService = new TipoExamenService(this.domain);
        capacitacionService = new CapacitacionService(this.domain);
        planCapacitacionService = new PlanCapacitacionService(this.domain);
        solicitudService = new SolicitudService(this.domain);
        evaluacionTerrenoService = new EvaluacionTerrenoService(this.domain);
        observacionService = new ObservacionService(this.domain);
        sesionService = new SesionService(this.domain);
        expositorService = new ExpositorService(this.domain);
        medicoService  = new MedicoService(this.domain);
        listTrabajadorService = new ListTrabajadorService(this.domain);
        asistenciaTrabajadorService = new AsistenciaTrabajadorService(this.domain);
        certificadoService = new CertificadoService(this.domain);
        
        int sessionTime;
        try {
            sessionTime = (int)Integer.parseInt(getConfig().getProperty("safe.sessionMinuteTime"));
        } catch (IOException e) {
            sessionTime = 30;
        }
        System.out.println("Se establece la session a " + sessionTime + " minutos");
        setLayout(new BorderLayout());
        WindowComponent.centerWindow(this);
        
        session = new SessionManager(this, sessionTime);
        initCustoms();
    }
    
    private String dateInvert(String d){
        try {
            d = dateInverted.format(date.parse(d));
        } catch (ParseException ex) {
            d = "";
        }
        
        return d;
    }
    
    private void editarEmpresa(java.awt.event.MouseEvent evt){
        int row = jTable4.rowAtPoint(evt.getPoint());
        int col = jTable4.columnAtPoint(evt.getPoint());
        if(col == 7){                        
            Cliente cliente = (Cliente)jTable4.getValueAt(row, col);
            selectedEntity = cliente;
            Bind.setComponent(cliente, this);
            jButton8.setEnabled(true);
            jLabelClienteTitle.setText("Editar empresa");
            changePanel(clienteForm);
        }
    }
    
    private void editarUsuario(java.awt.event.MouseEvent evt){
        int row = jTable5.rowAtPoint(evt.getPoint());
        int col = jTable5.columnAtPoint(evt.getPoint());
        if(col == 7){
            Usuario usuario = (Usuario)jTable5.getValueAt(row, col);
            selectedEntity = usuario;
            Bind.setComponent(usuario, this);
            jButton12.setEnabled(true);
            jLabelUsuarioTitle.setText("Editar usuario");
            changePanel(usuarioForm);
        }
    }
    
    private void editarTipoCapacitacion(java.awt.event.MouseEvent evt) {
        int row = jTable6.rowAtPoint(evt.getPoint());
        int col = jTable6.columnAtPoint(evt.getPoint());
        if(col == 2){
            TipoCapacitacion tipo = (TipoCapacitacion)jTable6.getValueAt(row, col);
            selectedEntity = tipo;
            Bind.setComponent(tipo, this);                      
            jButton19.setEnabled(true);
            jLabelTipoCapacitacionTitle.setText("Editar tipo de capacitación");
            changePanel(tipoCapacitacionForm);
        }
    }
    
    private void editarPlanCapacitacion(java.awt.event.MouseEvent evt){
        int row = jTable1.rowAtPoint(evt.getPoint());
        int col = jTable1.columnAtPoint(evt.getPoint());
        if(col == 6){
            PlanCapacitacion plan = (PlanCapacitacion)jTable1.getValueAt(row, col);
            String rut = jTable1.getValueAt(row, 3).toString();
            Cliente cliente = clienteService.getOneByRut(rut);
            jLabel137.setText(cliente.getRazonsocial());
            selectedEntity = plan;
            loadGestionCapacitacion(plan.getIdplancap());
            changePanel(planCapacitacionForm);
        }
    }
    
    private void editarTipoExamen(java.awt.event.MouseEvent evt){
        int row = jTable21.rowAtPoint(evt.getPoint());
        int col = jTable21.columnAtPoint(evt.getPoint());
        if(col == 2){
            TipoExamen tipo = (TipoExamen)jTable21.getValueAt(row, col);
            selectedEntity = tipo;
            Bind.setComponent(tipo, this);
            jButton48.setEnabled(true);
            jLabelTipoExamenTitle.setText("Editar tipo de examen");
            changePanel(tipoExamenForm);
        }
    }
    
    private void abrirPDFEvaluacion(int row){
        SoliEvalTer solicitud = (SoliEvalTer)jTable7.getValueAt(row, 6);
        String url = solicitud.getPdf();
        openWebUrl(url);
    }
    
    private void editarEvaluacion(java.awt.event.MouseEvent evt){
        int row = jTable7.rowAtPoint(evt.getPoint());
        int col = jTable7.columnAtPoint(evt.getPoint());
        if (col == 6) {
            SoliEvalTer solicitud = (SoliEvalTer)jTable7.getValueAt(row, 6);
            Eval_Terr evaluacion = (Eval_Terr)jTable7.getValueAt(row, 7);
            selectedEntity = evaluacion;
            secondEntity = solicitud;
            Cliente cliente = clienteService.getOne((int)solicitud.getClienteidcliente());
            Bind.setComponent(cliente, this);
            Bind.setComponent(solicitud, this);

            Obs_Supervisor[] obsS = observacionService.getSupervisorCollection();
            if(obsS != null){
                DefaultTableModel model = (DefaultTableModel)jTable2.getModel();
                model.setRowCount(0);
                for(Obs_Supervisor o: obsS){
                    Object[] item = {
                        o.getFechahoraobssupervisor(),
                        o.getObssupervisor(),
                    };
                    model.addRow(item);
                }
            }
            Obs_Ingeniero[] obsI = observacionService.getIngenieroCollection();
            if(obsI != null){
                DefaultTableModel model = (DefaultTableModel)jTable3.getModel();
                model.setRowCount(0);
                for(Obs_Ingeniero o: obsI){
                    Object[] item = {
                        o.getFechahoraobsing(),
                        o.getObsing(),
                    };
                    model.addRow(item);
                }
            }
            changePanel(evaluacionForm);
        } else if(col == 7){
            this.abrirPDFEvaluacion(row);
        }
    }
    
    private void editarExpositor(MouseEvent evt) {
        int row = jTable13.rowAtPoint(evt.getPoint());
        int col = jTable13.columnAtPoint(evt.getPoint());
        if(col == 5){
            Expositor expositor = (Expositor)jTable13.getValueAt(row, col);
            selectedEntity = expositor;
            Bind.setComponent(expositor, this);
            jButton39.setEnabled(true);
            jLabelExpositorTitle.setText("Editar expositor");
            changePanel(expositorForm);
        }
    }
    
    private void editarMedico(java.awt.event.MouseEvent evt){
        int row = jTable19.rowAtPoint(evt.getPoint());
        int col = jTable19.columnAtPoint(evt.getPoint());
        if(col == 5){
            Medico medico = (Medico)jTable19.getValueAt(row, col);
            selectedEntity = medico;
            Bind.setComponent(medico, this);
            jButton43.setEnabled(true);
            jLabelMedicoTitle.setText("Editar médico");
            changePanel(medicoForm);
        }
    }
    
    private void editarOtro(java.awt.event.MouseEvent evt){
        
    }
    
    private void initCustoms(){
        Main self = this;
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                self.editarPlanCapacitacion(evt);
            }
        });
        jTable4.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                self.editarEmpresa(evt);
            }
        });
        jTable5.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                self.editarUsuario(evt);
            }
        });    
        jTable6.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                self.editarTipoCapacitacion(evt);
            }
        });
        jTable7.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                self.editarEvaluacion(evt);
            }
        });
        jTable21.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                self.editarTipoExamen(evt);
            }
        });  
        
        jTable13.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                self.editarExpositor(evt);
            }
        });
        
        jTable19.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                self.editarMedico(evt);
            }
        });
        
        Vector<TipoCapacitacion> tiposCap = new Vector<>();
        for(TipoCapacitacion t: tipoCapacitacionService.getCollection()){
            tiposCap.add(t);
        }
        jComboBox6.setModel(new javax.swing.DefaultComboBoxModel(tiposCap));
        jTable1.getColumnModel().getColumn(6).setCellRenderer(new ButtonTableComponent("[+]"));
        jTable4.getColumnModel().getColumn(7).setCellRenderer(new ButtonTableComponent("[+]"));
        jTable5.getColumnModel().getColumn(7).setCellRenderer(new ButtonTableComponent("[+]"));
        jTable6.getColumnModel().getColumn(2).setCellRenderer(new ButtonTableComponent("[+]"));
        jTable7.getColumnModel().getColumn(6).setCellRenderer(new ButtonTableComponent("[+]"));
        jTable7.getColumnModel().getColumn(7).setCellRenderer(new ButtonTableComponent("PDF"));
        jTable8.getColumnModel().getColumn(3).setCellRenderer(new ButtonTableComponent("[+]"));
        jTable8.getColumnModel().getColumn(4).setCellRenderer(new ButtonTableComponent("[-]"));
        jTable10.getColumnModel().getColumn(7).setCellRenderer(new ButtonTableComponent("[-]"));
        jTable11.getColumnModel().getColumn(6).setCellRenderer(new ButtonTableComponent("[ ]"));
        jTable13.getColumnModel().getColumn(5).setCellRenderer(new ButtonTableComponent("[+]"));
        jTable19.getColumnModel().getColumn(5).setCellRenderer(new ButtonTableComponent("[+]"));
        jTable21.getColumnModel().getColumn(2).setCellRenderer(new ButtonTableComponent("[+]"));
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
            case "ROLE_ENGINEER": currentMenu =jMenuEngineer;
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
    
    private void loadGestionCapacitacion(long capplanid){
        Capacitacion[] capacitaciones = capacitacionService.getCollection();
        this.mapCapcitacion = new LinkedHashMap<>();
        HashMap<Long, Expositor> mapExpositor = new HashMap<>();
        
        Expositor[] expositores = expositorService.getCollection();
        for(Expositor e: expositores){
            mapExpositor.put(e.getIdexpositor(), e);
            jComboBox8.addItem(e.getNombreexpositor());
        }
        
        if(capacitaciones != null ){
            DefaultTableModel modelCapacitacion = (DefaultTableModel)jTable8.getModel();
            modelCapacitacion.setRowCount(0);
            for(Capacitacion c: capacitaciones){
                if(c.getPlancapidplancap() != capplanid) continue;
                this.mapCapcitacion.put(c.getIdcap(), c);
                jComboBox7.addItem(c.getNombrecapacitacion());
                jComboBox19.addItem(c.getNombrecapacitacion());
                Object[] item = {
                    c.getNombrecapacitacion(),
                    c.getTipocapidtipocap(),
                    CapacitacionService.ESTADOS[(int)c.getEstadocapacitacion()],
                    c,
                    c,
                };
                modelCapacitacion.addRow(item);
            }
        }
        
        Sesion_Cap[] sesionesCap = sesionService.getCapacitacionCollection();
        Capacitacion cap = null;
        if(sesionesCap != null ){
            DefaultTableModel modelSession = (DefaultTableModel)jTable9.getModel();
            modelSession.setRowCount(0);
            for(Sesion_Cap s: sesionesCap){
                cap = this.mapCapcitacion.get(s.getCapacitacionidcap());
                if(cap == null) continue;
                Object[] item = {
                    cap.getNombrecapacitacion(),
                    s.getCupossesionString(),
                    s.getNombresesion(),
                    mapExpositor.get(s.getExpositoridexpositor()).getNombreexpositor(),
                    s.getFechasesion(),
                    s.getHorainiciocap(),
                    s.getHoraterminocap(),
                    s.getDescripcionsesion(),
                };
                modelSession.addRow(item);
            }
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

        jMenuAdmin = new javax.swing.JMenuBar();
        jMenuEmpresa = new javax.swing.JMenu();
        jMenuEmpListar = new javax.swing.JMenuItem();
        jMenuEmpCrear = new javax.swing.JMenuItem();
        jMenuUsuario = new javax.swing.JMenu();
        jMenuUsrListar = new javax.swing.JMenuItem();
        jMenuUsrCrear = new javax.swing.JMenuItem();
        jMenuTipoCapacitacion = new javax.swing.JMenu();
        jMenuTipoCapListar = new javax.swing.JMenuItem();
        jMenuTipoCapCrear = new javax.swing.JMenuItem();
        jMenuExamen = new javax.swing.JMenu();
        jMenuExmListar = new javax.swing.JMenuItem();
        jMenuExmCrear = new javax.swing.JMenuItem();
        jMenuSupervisor = new javax.swing.JMenuBar();
        jMenuTerreno = new javax.swing.JMenu();
        jMenuTerListar = new javax.swing.JMenuItem();
        jMenuTerCrear = new javax.swing.JMenuItem();
        jMenuPlanCap = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuPlanSalud = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuExpositor = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuMedico = new javax.swing.JMenu();
        jMenuMedListar1 = new javax.swing.JMenuItem();
        jMenuMedCrear1 = new javax.swing.JMenuItem();
        jMenuCal = new javax.swing.JMenu();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuEngineer = new javax.swing.JMenuBar();
        jMenuTerrenoEng = new javax.swing.JMenu();
        jMenuTerEngListar = new javax.swing.JMenuItem();
        clienteMain = new javax.swing.JInternalFrame();
        jButton5 = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jTextField11 = new javax.swing.JTextField();
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
        tipoExamenMain = new javax.swing.JInternalFrame();
        jButton45 = new javax.swing.JButton();
        jLabel132 = new javax.swing.JLabel();
        jLabel133 = new javax.swing.JLabel();
        jScrollPane27 = new javax.swing.JScrollPane();
        jTable21 = new javax.swing.JTable();
        jTextField40 = new javax.swing.JTextField();
        tipoExamenForm = new javax.swing.JInternalFrame();
        jLabelTipoExamenTitle = new javax.swing.JLabel();
        jLabel134 = new javax.swing.JLabel();
        jTextField41 = new javax.swing.JTextField();
        jButton46 = new javax.swing.JButton();
        jButton47 = new javax.swing.JButton();
        jButton48 = new javax.swing.JButton();
        jLabel135 = new javax.swing.JLabel();
        jComboBox18 = new javax.swing.JComboBox<>();
        evaluacionMain = new javax.swing.JInternalFrame();
        jComboBoxEstado2 = new javax.swing.JComboBox<>();
        jFormattedTextField4 = new javax.swing.JFormattedTextField();
        jFormattedTextField5 = new javax.swing.JFormattedTextField();
        jButton13 = new javax.swing.JButton();
        jLabel37 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jComboBoxTipo1 = new javax.swing.JComboBox<>();
        jScrollPane10 = new javax.swing.JScrollPane();
        jTable7 = new javax.swing.JTable();
        evaluacionForm = new javax.swing.JInternalFrame();
        jLabel9 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabelRut = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabelTipoVisita = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabelDireccionVisita = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextAreaDescVisita = new javax.swing.JTextArea();
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
        jButton54 = new javax.swing.JButton();
        planCapacitacionMain = new javax.swing.JInternalFrame();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jFormattedTextField2 = new javax.swing.JFormattedTextField();
        jButton1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jTextField17 = new javax.swing.JTextField();
        jButton14 = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        jLabel138 = new javax.swing.JLabel();
        newPlanCapacitacionForm = new javax.swing.JInternalFrame();
        jTextField18 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jButton15 = new javax.swing.JButton();
        jButton20 = new javax.swing.JButton();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        planCapacitacionForm = new javax.swing.JInternalFrame();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        jLabel57 = new javax.swing.JLabel();
        jTextField19 = new javax.swing.JTextField();
        jLabel58 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jLabel59 = new javax.swing.JLabel();
        jComboBox6 = new javax.swing.JComboBox<>();
        jButton21 = new javax.swing.JButton();
        jScrollPane12 = new javax.swing.JScrollPane();
        jTable8 = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jComboBox7 = new javax.swing.JComboBox<>();
        jLabel60 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        jFormattedTextField6 = new javax.swing.JFormattedTextField();
        jLabel62 = new javax.swing.JLabel();
        jTextField22 = new javax.swing.JTextField();
        jComboBox8 = new javax.swing.JComboBox<>();
        jLabel63 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        jFormattedTextField7 = new javax.swing.JFormattedTextField();
        jLabel66 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        jFormattedTextField8 = new javax.swing.JFormattedTextField();
        jFormattedTextField9 = new javax.swing.JFormattedTextField();
        jLabel68 = new javax.swing.JLabel();
        jScrollPane13 = new javax.swing.JScrollPane();
        jTextArea3 = new javax.swing.JTextArea();
        jButton22 = new javax.swing.JButton();
        jLabel69 = new javax.swing.JLabel();
        jButton49 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable9 = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jLabel70 = new javax.swing.JLabel();
        jTextField23 = new javax.swing.JTextField();
        jButton23 = new javax.swing.JButton();
        jLabel71 = new javax.swing.JLabel();
        jButton24 = new javax.swing.JButton();
        jScrollPane15 = new javax.swing.JScrollPane();
        jTable10 = new javax.swing.JTable();
        jButton50 = new javax.swing.JButton();
        jComboBox19 = new javax.swing.JComboBox<>();
        jLabel139 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jComboBox9 = new javax.swing.JComboBox<>();
        jLabel72 = new javax.swing.JLabel();
        jScrollPane16 = new javax.swing.JScrollPane();
        jTable11 = new javax.swing.JTable();
        jLabel73 = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        jButton25 = new javax.swing.JButton();
        jButton26 = new javax.swing.JButton();
        jButton51 = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jLabel75 = new javax.swing.JLabel();
        jComboBox10 = new javax.swing.JComboBox<>();
        jLabel76 = new javax.swing.JLabel();
        jLabel77 = new javax.swing.JLabel();
        jScrollPane17 = new javax.swing.JScrollPane();
        jTable12 = new javax.swing.JTable();
        jButton52 = new javax.swing.JButton();
        jLabel136 = new javax.swing.JLabel();
        jLabel137 = new javax.swing.JLabel();
        planSaludMain = new javax.swing.JInternalFrame();
        jFormattedTextField14 = new javax.swing.JFormattedTextField();
        jFormattedTextField15 = new javax.swing.JFormattedTextField();
        jButton33 = new javax.swing.JButton();
        jLabel98 = new javax.swing.JLabel();
        jLabel99 = new javax.swing.JLabel();
        jLabel100 = new javax.swing.JLabel();
        jScrollPane24 = new javax.swing.JScrollPane();
        jTable18 = new javax.swing.JTable();
        jTextField27 = new javax.swing.JTextField();
        jButton34 = new javax.swing.JButton();
        newPlanSaludForm = new javax.swing.JInternalFrame();
        jTextField24 = new javax.swing.JTextField();
        jLabel78 = new javax.swing.JLabel();
        jLabel79 = new javax.swing.JLabel();
        jLabel80 = new javax.swing.JLabel();
        jLabel101 = new javax.swing.JLabel();
        jLabel102 = new javax.swing.JLabel();
        jButton27 = new javax.swing.JButton();
        jButton35 = new javax.swing.JButton();
        jLabel103 = new javax.swing.JLabel();
        jLabel104 = new javax.swing.JLabel();
        jLabel105 = new javax.swing.JLabel();
        jLabel106 = new javax.swing.JLabel();
        planSaludForm = new javax.swing.JInternalFrame();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel12 = new javax.swing.JPanel();
        jComboBox13 = new javax.swing.JComboBox<>();
        jLabel81 = new javax.swing.JLabel();
        jLabel82 = new javax.swing.JLabel();
        jFormattedTextField10 = new javax.swing.JFormattedTextField();
        jLabel83 = new javax.swing.JLabel();
        jTextField25 = new javax.swing.JTextField();
        jComboBox14 = new javax.swing.JComboBox<>();
        jLabel84 = new javax.swing.JLabel();
        jLabel85 = new javax.swing.JLabel();
        jFormattedTextField11 = new javax.swing.JFormattedTextField();
        jLabel86 = new javax.swing.JLabel();
        jLabel87 = new javax.swing.JLabel();
        jFormattedTextField12 = new javax.swing.JFormattedTextField();
        jFormattedTextField13 = new javax.swing.JFormattedTextField();
        jLabel88 = new javax.swing.JLabel();
        jScrollPane19 = new javax.swing.JScrollPane();
        jTextArea4 = new javax.swing.JTextArea();
        jScrollPane20 = new javax.swing.JScrollPane();
        jTable14 = new javax.swing.JTable();
        jButton28 = new javax.swing.JButton();
        jLabel89 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel90 = new javax.swing.JLabel();
        jTextField26 = new javax.swing.JTextField();
        jButton29 = new javax.swing.JButton();
        jLabel91 = new javax.swing.JLabel();
        jButton30 = new javax.swing.JButton();
        jScrollPane21 = new javax.swing.JScrollPane();
        jTable15 = new javax.swing.JTable();
        jPanel14 = new javax.swing.JPanel();
        jComboBox15 = new javax.swing.JComboBox<>();
        jLabel92 = new javax.swing.JLabel();
        jScrollPane22 = new javax.swing.JScrollPane();
        jTable16 = new javax.swing.JTable();
        jLabel93 = new javax.swing.JLabel();
        jLabel94 = new javax.swing.JLabel();
        jButton31 = new javax.swing.JButton();
        jButton32 = new javax.swing.JButton();
        jPanel15 = new javax.swing.JPanel();
        jLabel95 = new javax.swing.JLabel();
        jComboBox16 = new javax.swing.JComboBox<>();
        jLabel96 = new javax.swing.JLabel();
        jLabel97 = new javax.swing.JLabel();
        jScrollPane23 = new javax.swing.JScrollPane();
        jTable17 = new javax.swing.JTable();
        jLabel107 = new javax.swing.JLabel();
        jLabel108 = new javax.swing.JLabel();
        expositorMain = new javax.swing.JInternalFrame();
        jComboBoxEstado3 = new javax.swing.JComboBox<>();
        jFormattedTextField16 = new javax.swing.JFormattedTextField();
        jFormattedTextField17 = new javax.swing.JFormattedTextField();
        jButton36 = new javax.swing.JButton();
        jLabel109 = new javax.swing.JLabel();
        jLabel110 = new javax.swing.JLabel();
        jLabel111 = new javax.swing.JLabel();
        jScrollPane18 = new javax.swing.JScrollPane();
        jTable13 = new javax.swing.JTable();
        expositorForm = new javax.swing.JInternalFrame();
        jLabelExpositorTitle = new javax.swing.JLabel();
        jLabel113 = new javax.swing.JLabel();
        jTextField28 = new javax.swing.JTextField();
        jLabel114 = new javax.swing.JLabel();
        jTextField31 = new javax.swing.JTextField();
        jLabel119 = new javax.swing.JLabel();
        jTextField34 = new javax.swing.JTextField();
        jLabel120 = new javax.swing.JLabel();
        jTextField35 = new javax.swing.JTextField();
        jButton37 = new javax.swing.JButton();
        jButton38 = new javax.swing.JButton();
        jButton39 = new javax.swing.JButton();
        jLabel122 = new javax.swing.JLabel();
        jComboBox12 = new javax.swing.JComboBox<>();
        medicoMain = new javax.swing.JInternalFrame();
        jComboBoxEstado4 = new javax.swing.JComboBox<>();
        jFormattedTextField18 = new javax.swing.JFormattedTextField();
        jFormattedTextField19 = new javax.swing.JFormattedTextField();
        jButton40 = new javax.swing.JButton();
        jLabel112 = new javax.swing.JLabel();
        jLabel115 = new javax.swing.JLabel();
        jLabel116 = new javax.swing.JLabel();
        jScrollPane25 = new javax.swing.JScrollPane();
        jTable19 = new javax.swing.JTable();
        medicoForm = new javax.swing.JInternalFrame();
        jLabelMedicoTitle = new javax.swing.JLabel();
        jLabel117 = new javax.swing.JLabel();
        jTextField32 = new javax.swing.JTextField();
        jLabel118 = new javax.swing.JLabel();
        jTextField33 = new javax.swing.JTextField();
        jLabel121 = new javax.swing.JLabel();
        jTextField36 = new javax.swing.JTextField();
        jLabel123 = new javax.swing.JLabel();
        jTextField37 = new javax.swing.JTextField();
        jButton41 = new javax.swing.JButton();
        jButton42 = new javax.swing.JButton();
        jButton43 = new javax.swing.JButton();
        jLabel124 = new javax.swing.JLabel();
        jComboBox17 = new javax.swing.JComboBox<>();
        jLabel125 = new javax.swing.JLabel();
        jTextField38 = new javax.swing.JTextField();
        calendarMain = new javax.swing.JInternalFrame();
        jButton44 = new javax.swing.JButton();
        jCalendar1 = new com.toedter.calendar.JCalendar();
        jScrollPane26 = new javax.swing.JScrollPane();
        jTable20 = new javax.swing.JTable();
        jTextField39 = new javax.swing.JTextField();
        jLabel126 = new javax.swing.JLabel();
        jComboBox11 = new javax.swing.JComboBox<>();
        jLabel127 = new javax.swing.JLabel();
        jLabel128 = new javax.swing.JLabel();
        jLabel129 = new javax.swing.JLabel();
        jLabel130 = new javax.swing.JLabel();
        jLabel131 = new javax.swing.JLabel();
        jPanelWelcome = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabelProfile = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

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

        jMenuTipoCapacitacion.setText("Capacitaciones");

        jMenuTipoCapListar.setText("Listar");
        jMenuTipoCapListar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuTipoCapListarActionPerformed(evt);
            }
        });
        jMenuTipoCapacitacion.add(jMenuTipoCapListar);

        jMenuTipoCapCrear.setText("Crear");
        jMenuTipoCapCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuTipoCapCrearActionPerformed(evt);
            }
        });
        jMenuTipoCapacitacion.add(jMenuTipoCapCrear);

        jMenuAdmin.add(jMenuTipoCapacitacion);

        jMenuExamen.setText("Exámenes");

        jMenuExmListar.setText("Listar");
        jMenuExmListar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuExmListarActionPerformed(evt);
            }
        });
        jMenuExamen.add(jMenuExmListar);

        jMenuExmCrear.setText("Crear");
        jMenuExmCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuExmCrearActionPerformed(evt);
            }
        });
        jMenuExamen.add(jMenuExmCrear);

        jMenuAdmin.add(jMenuExamen);

        jMenuSupervisor.setOpaque(false);

        jMenuTerreno.setText("Evaluación En Terreno");
        jMenuTerreno.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jMenuTerreno.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jMenuTerListar.setText("Listar");
        jMenuTerListar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuTerListarActionPerformed(evt);
            }
        });
        jMenuTerreno.add(jMenuTerListar);

        jMenuTerCrear.setText("Crear");
        jMenuTerCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuTerCrearActionPerformed(evt);
            }
        });
        jMenuTerreno.add(jMenuTerCrear);

        jMenuSupervisor.add(jMenuTerreno);

        jMenuPlanCap.setText("Plan Capacitación");
        jMenuPlanCap.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jMenuItem1.setText("Listar");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenuPlanCap.add(jMenuItem1);

        jMenuItem2.setText("Crear");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenuPlanCap.add(jMenuItem2);

        jMenuSupervisor.add(jMenuPlanCap);

        jMenuPlanSalud.setText("Plan de Salud");
        jMenuPlanSalud.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jMenuItem3.setText("Listar");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenuPlanSalud.add(jMenuItem3);

        jMenuItem4.setText("Crear");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenuPlanSalud.add(jMenuItem4);

        jMenuSupervisor.add(jMenuPlanSalud);

        jMenuExpositor.setText("Expositores");
        jMenuExpositor.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jMenuItem5.setText("Listar");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenuExpositor.add(jMenuItem5);

        jMenuItem6.setText("Crear");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenuExpositor.add(jMenuItem6);

        jMenuSupervisor.add(jMenuExpositor);

        jMenuMedico.setText("Médicos");
        jMenuMedico.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jMenuMedListar1.setText("Listar");
        jMenuMedListar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuMedListar1ActionPerformed(evt);
            }
        });
        jMenuMedico.add(jMenuMedListar1);

        jMenuMedCrear1.setText("Crear");
        jMenuMedCrear1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuMedCrear1ActionPerformed(evt);
            }
        });
        jMenuMedico.add(jMenuMedCrear1);

        jMenuSupervisor.add(jMenuMedico);

        jMenuCal.setText("Calendario");
        jMenuCal.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jMenuItem7.setText("Ver");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenuCal.add(jMenuItem7);

        jMenuSupervisor.add(jMenuCal);

        jMenuEngineer.setOpaque(false);

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

        jMenuEngineer.add(jMenuTerrenoEng);

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

        clienteForm.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        clienteForm.setVisible(true);

        jLabelClienteTitle.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabelClienteTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelClienteTitle.setText("Crear empresa");

        jLabel19.setText("Razón Social");

        jTextField1.setName("com.safe.entity.Cliente.razonsocial"); // NOI18N

        jLabel20.setText("RUT");

        jTextField2.setName("com.safe.entity.Cliente.rutcliente"); // NOI18N

        jLabel21.setText("Giro");

        jTextField3.setName("com.safe.entity.Cliente.girocliente"); // NOI18N

        jLabel22.setText("Dirección");

        jTextField4.setName("com.safe.entity.Cliente.direccioncliente"); // NOI18N

        jLabel23.setText("Comuna");

        jLabel24.setText("Fono Oficina");

        jTextField6.setName("com.safe.entity.Cliente.teloficina"); // NOI18N

        jLabel25.setText("Nombe Contacto");

        jTextField7.setName("com.safe.entity.Cliente.nombrecontacto"); // NOI18N

        jLabel26.setText("Fono Contacto");

        jTextField8.setName("com.safe.entity.Cliente.fonocontacto"); // NOI18N

        jLabel27.setText("Mail Contacto");

        jTextField9.setName("com.safe.entity.Cliente.mailcontacto"); // NOI18N

        jLabel28.setText("Cargo Contacto");

        jTextField10.setName("com.safe.entity.Cliente.cargocontacto"); // NOI18N

        jLabel29.setText("Observaciones");

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jTextArea2.setName("com.safe.entity.Cliente.observacionescliente"); // NOI18N
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, clienteFormLayout.createSequentialGroup()
                .addGroup(clienteFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(clienteFormLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabelClienteTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, clienteFormLayout.createSequentialGroup()
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
                    .addGroup(clienteFormLayout.createSequentialGroup()
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

        jTextField1.getAccessibleContext().setAccessibleName("");

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

        usuarioForm.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        usuarioForm.setVisible(true);

        jLabelUsuarioTitle.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabelUsuarioTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelUsuarioTitle.setText("Crear usuario");

        jLabel32.setText("RUN");

        jTextField13.setName("com.safe.entity.Usuario.runusuario"); // NOI18N

        jLabel33.setText("Nombres");

        jTextField14.setName("com.safe.entity.Usuario.nombresusuario"); // NOI18N

        jLabel34.setText("Apellido paterno");

        jTextField15.setName("com.safe.entity.Usuario.appaterno"); // NOI18N

        jLabel35.setText("Apellido materno");

        jTextField16.setName("com.safe.entity.Usuario.apmaterno"); // NOI18N

        jLabel36.setText("Fecha Nacimiento");

        jLabel38.setText("Gereno");

        jLabel39.setText("Fono Contacto");

        jTextField20.setName("com.safe.entity.Usuario.telusuario"); // NOI18N

        jLabel40.setText("Mail Contacto");

        jTextField21.setName("com.safe.entity.Usuario.mailusuario"); // NOI18N

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

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(Arrays.copyOfRange(com.safe.service.UsuarioService.PERFIL,1,7)));
        jComboBox1.setName("com.safe.entity.Usuario.perfilidperfilIndex"); // NOI18N
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel43.setText("Estado");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(Arrays.copyOfRange(com.safe.service.UsuarioService.ESTADOS,1,4)));
        jComboBox2.setName("com.safe.entity.Usuario.estadousuarioIndex"); // NOI18N

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel(com.safe.service.UsuarioService.GENERO));
        jComboBox3.setName("com.safe.entity.Usuario.sexousuarioIndex"); // NOI18N

        jFormattedTextField3.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter()));
        jFormattedTextField3.setToolTipText("dd-mm-yyyy");
        jFormattedTextField3.setName("com.safe.entity.Usuario.fnacimientousuario"); // NOI18N
        jFormattedTextField3.setPreferredSize(new java.awt.Dimension(4, 20));
        jFormattedTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFormattedTextField3ActionPerformed(evt);
            }
        });

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
                        .addComponent(jButton10)))
                .addContainerGap())
            .addComponent(jLabelUsuarioTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

        jTextField29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField29ActionPerformed(evt);
            }
        });

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

        tipoCapacitacionForm.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        tipoCapacitacionForm.setVisible(true);

        jLabelTipoCapacitacionTitle.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabelTipoCapacitacionTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTipoCapacitacionTitle.setText("Crear tipo de capacitación");

        jLabel55.setText("Nombre");

        jTextField30.setName("com.safe.entity.TipoCapacitacion.descripcap"); // NOI18N

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
        jComboBox5.setName("com.safe.entity.TipoCapacitacion.estadoIndex"); // NOI18N

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
            .addComponent(jLabelTipoCapacitacionTitle, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

        tipoExamenMain.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        tipoExamenMain.setTitle("Capacitaciones");
        tipoExamenMain.setOpaque(false);
        tipoExamenMain.setVisible(true);

        jButton45.setText("Buscar");
        jButton45.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton45ActionPerformed(evt);
            }
        });

        jLabel132.setText("Nombre tipo examen");
        jLabel132.setToolTipText("");

        jLabel133.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel133.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel133.setText("Listado de Tipos de Examen");
        jLabel133.setToolTipText("");

        jTable21.setModel(new javax.swing.table.DefaultTableModel(
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
        jTable21.getTableHeader().setReorderingAllowed(false);
        jScrollPane27.setViewportView(jTable21);
        jTable21.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        javax.swing.GroupLayout tipoExamenMainLayout = new javax.swing.GroupLayout(tipoExamenMain.getContentPane());
        tipoExamenMain.getContentPane().setLayout(tipoExamenMainLayout);
        tipoExamenMainLayout.setHorizontalGroup(
            tipoExamenMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tipoExamenMainLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel132, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField40, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton45)
                .addGap(8, 8, 8))
            .addGroup(tipoExamenMainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tipoExamenMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel133, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane27, javax.swing.GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE))
                .addContainerGap())
        );
        tipoExamenMainLayout.setVerticalGroup(
            tipoExamenMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tipoExamenMainLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel133)
                .addGap(18, 18, 18)
                .addGroup(tipoExamenMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel132)
                    .addComponent(jButton45)
                    .addComponent(jTextField40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane27, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(50, Short.MAX_VALUE))
        );

        tipoExamenForm.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        tipoExamenForm.setVisible(true);

        jLabelTipoExamenTitle.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabelTipoExamenTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTipoExamenTitle.setText("Crear tipo de examen");

        jLabel134.setText("Nombre");

        jTextField41.setName("com.safe.entity.TipoExamen.descrip_exam"); // NOI18N

        jButton46.setText("Guardar");
        jButton46.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton46ActionPerformed(evt);
            }
        });

        jButton47.setText("Cancelar");
        jButton47.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton47ActionPerformed(evt);
            }
        });

        jButton48.setText("Eliminar");
        jButton48.setEnabled(false);
        jButton48.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton48ActionPerformed(evt);
            }
        });

        jLabel135.setText("Estado");

        jComboBox18.setModel(new javax.swing.DefaultComboBoxModel(Arrays.copyOfRange(com.safe.service.TipoExamenService.ESTADOS, 1, 3)));
        jComboBox18.setName("com.safe.entity.TipoExamen.estadoIndex"); // NOI18N

        javax.swing.GroupLayout tipoExamenFormLayout = new javax.swing.GroupLayout(tipoExamenForm.getContentPane());
        tipoExamenForm.getContentPane().setLayout(tipoExamenFormLayout);
        tipoExamenFormLayout.setHorizontalGroup(
            tipoExamenFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tipoExamenFormLayout.createSequentialGroup()
                .addGroup(tipoExamenFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, tipoExamenFormLayout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addGroup(tipoExamenFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel134)
                            .addComponent(jLabel135))
                        .addGap(92, 92, 92)
                        .addGroup(tipoExamenFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField41)
                            .addComponent(jComboBox18, 0, 175, Short.MAX_VALUE)))
                    .addGroup(tipoExamenFormLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton48)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton47)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton46)
                .addContainerGap())
            .addComponent(jLabelTipoExamenTitle, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        tipoExamenFormLayout.setVerticalGroup(
            tipoExamenFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tipoExamenFormLayout.createSequentialGroup()
                .addComponent(jLabelTipoExamenTitle)
                .addGap(23, 23, 23)
                .addGroup(tipoExamenFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel134)
                    .addComponent(jTextField41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(tipoExamenFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel135)
                    .addComponent(jComboBox18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 67, Short.MAX_VALUE)
                .addGroup(tipoExamenFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton46)
                    .addComponent(jButton47)
                    .addComponent(jButton48))
                .addContainerGap())
        );

        evaluacionMain.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        evaluacionMain.setVisible(true);

        jComboBoxEstado2.setModel(new javax.swing.DefaultComboBoxModel(com.safe.service.SolicitudService.ESTADOS));
        jComboBoxEstado2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxEstado2ActionPerformed(evt);
            }
        });

        jFormattedTextField4.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("dd-MM-yyyy"))));
        jFormattedTextField4.setToolTipText("dd-mm-yyyy");

        jFormattedTextField5.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter()));
        jFormattedTextField5.setToolTipText("dd-mm-yyyy");

        jButton13.setText("Buscar");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jLabel37.setText("Filtro de Estado");

        jLabel41.setText("Filtro de Fecha");

        jLabel44.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel44.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel44.setText("Listado de evaluaciones en terreno");
        jLabel44.setToolTipText("");

        jLabel45.setText("Filtro tipo visita");

        jComboBoxTipo1.setModel(new javax.swing.DefaultComboBoxModel(com.safe.service.SolicitudService.TIPOS));

        jTable7.setModel(new javax.swing.table.DefaultTableModel(
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
        jTable7.getTableHeader().setReorderingAllowed(false);
        jScrollPane10.setViewportView(jTable7);
        jTable7.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        if (jTable7.getColumnModel().getColumnCount() > 0) {
            jTable7.getColumnModel().getColumn(4).setHeaderValue("Tipo");
            jTable7.getColumnModel().getColumn(7).setHeaderValue("PDF");
        }

        javax.swing.GroupLayout evaluacionMainLayout = new javax.swing.GroupLayout(evaluacionMain.getContentPane());
        evaluacionMain.getContentPane().setLayout(evaluacionMainLayout);
        evaluacionMainLayout.setHorizontalGroup(
            evaluacionMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(evaluacionMainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(evaluacionMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane10)
                    .addComponent(jLabel44, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(evaluacionMainLayout.createSequentialGroup()
                        .addComponent(jLabel45)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxTipo1, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(evaluacionMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel37, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(evaluacionMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jComboBoxEstado2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(evaluacionMainLayout.createSequentialGroup()
                                .addComponent(jFormattedTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jFormattedTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton13)))))
                .addContainerGap())
        );
        evaluacionMainLayout.setVerticalGroup(
            evaluacionMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(evaluacionMainLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel44)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(evaluacionMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton13)
                    .addComponent(jFormattedTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFormattedTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel41))
                .addGap(18, 18, 18)
                .addGroup(evaluacionMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxEstado2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel37)
                    .addComponent(jLabel45)
                    .addComponent(jComboBoxTipo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(80, Short.MAX_VALUE))
        );

        evaluacionForm.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        evaluacionForm.setVisible(true);

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

        jLabel11.setText("RUT Cliente");

        jLabelRut.setText("00.000.000-0");
        jLabelRut.setToolTipText("");
        jLabelRut.setName("com.safe.entity.Cliente.rutcliente"); // NOI18N

        jLabel13.setText("Tipo de visita");

        jLabelTipoVisita.setText("TIPO");
        jLabelTipoVisita.setName("com.safe.entity.SoliEvalTer.tipovisitaText"); // NOI18N

        jLabel12.setText("Dirección visita");

        jLabelDireccionVisita.setText("DIRECCION");
        jLabelDireccionVisita.setName("com.safe.entity.SoliEvalTer.direccionvisita"); // NOI18N

        jLabel17.setText("Descripción visita");

        jTextAreaDescVisita.setEditable(false);
        jTextAreaDescVisita.setName("com.safe.entity.SoliEvalTer.descripcionvisita"); // NOI18N
        jScrollPane3.setViewportView(jTextAreaDescVisita);

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
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 531, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addGroup(jPanel5Layout.createSequentialGroup()
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
                            .addComponent(jLabel17))
                        .addGap(0, 14, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(174, Short.MAX_VALUE))
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

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Observaciones al supervisor");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 940, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(130, 130, 130)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(jButton2))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Derivar Evaluación", jPanel2);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Fecha", "Descripción"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane5.setViewportView(jTable2);

        jLabel8.setText("Observaciones del supervisor");

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
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 934, Short.MAX_VALUE))
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
                "Fecha", "Descripción"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
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
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 934, Short.MAX_VALUE))
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

        jButton54.setText("Cerrar");

        javax.swing.GroupLayout evaluacionFormLayout = new javax.swing.GroupLayout(evaluacionForm.getContentPane());
        evaluacionForm.getContentPane().setLayout(evaluacionFormLayout);
        evaluacionFormLayout.setHorizontalGroup(
            evaluacionFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(evaluacionFormLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(evaluacionFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, evaluacionFormLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton54)))
                .addContainerGap())
        );
        evaluacionFormLayout.setVerticalGroup(
            evaluacionFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(evaluacionFormLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton54)
                .addContainerGap())
        );

        planCapacitacionMain.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        planCapacitacionMain.setVisible(true);

        jFormattedTextField1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("dd/MM/yyyy"))));
        jFormattedTextField1.setToolTipText("dd/mm/yyyy");
        jFormattedTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFormattedTextField1ActionPerformed(evt);
            }
        });

        jFormattedTextField2.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter()));
        jFormattedTextField2.setToolTipText("dd/mm/yyyy");
        jFormattedTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFormattedTextField2ActionPerformed(evt);
            }
        });

        jButton1.setText("Buscar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel4.setText("Filtro de Fecha");
        jLabel4.setToolTipText("");

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Gestión de capacitaciones");
        jLabel5.setToolTipText("");

        jLabel6.setText("RUT Cliente");
        jLabel6.setToolTipText("");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Creado el", "Fecha Inicio", "Fecha Fin", "Cliente", "Sessiones", "Estado", "Gestionar"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
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

        jTextField17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField17ActionPerformed(evt);
            }
        });

        jButton14.setText("Crear plan anual");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        jLabel18.setText("Inicio");
        jLabel18.setToolTipText("");

        jLabel138.setText("Fin");
        jLabel138.setToolTipText("");

        javax.swing.GroupLayout planCapacitacionMainLayout = new javax.swing.GroupLayout(planCapacitacionMain.getContentPane());
        planCapacitacionMain.getContentPane().setLayout(planCapacitacionMainLayout);
        planCapacitacionMainLayout.setHorizontalGroup(
            planCapacitacionMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(planCapacitacionMainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(planCapacitacionMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, planCapacitacionMainLayout.createSequentialGroup()
                        .addGap(241, 241, 241)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField17, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(planCapacitacionMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(planCapacitacionMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(planCapacitacionMainLayout.createSequentialGroup()
                                .addComponent(jFormattedTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(planCapacitacionMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton14)))
                            .addComponent(jLabel138))))
                .addContainerGap())
        );
        planCapacitacionMainLayout.setVerticalGroup(
            planCapacitacionMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(planCapacitacionMainLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(planCapacitacionMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton14)
                    .addGroup(planCapacitacionMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel18)
                        .addComponent(jLabel138)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(planCapacitacionMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jFormattedTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6)
                    .addComponent(jTextField17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE)
                .addContainerGap())
        );

        newPlanCapacitacionForm.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        newPlanCapacitacionForm.setTitle("Nuevo plan anual");
        newPlanCapacitacionForm.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        newPlanCapacitacionForm.setVisible(true);

        jLabel3.setText("RUT Empresa");

        jLabel46.setText("Nombre Cliente");

        jLabel47.setText("Telefono Empresa");

        jLabel48.setText("Mail Contacto");

        jLabel49.setText("Dirección");

        jButton15.setText("Crear plan anual");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        jButton20.setText("Buscar");
        jButton20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton20ActionPerformed(evt);
            }
        });

        jLabel50.setText("jLabel50");
        jLabel50.setName("com.safe.entity.Cliente.razonsocial"); // NOI18N

        jLabel51.setText("jLabel51");
        jLabel51.setName("com.safe.entity.Cliente.teloficina"); // NOI18N

        jLabel52.setText("jLabel52");
        jLabel52.setName("com.safe.entity.Cliente.mailcontacto"); // NOI18N

        jLabel56.setText("jLabel56");
        jLabel56.setName("com.safe.entity.Cliente.direccioncliente"); // NOI18N

        javax.swing.GroupLayout newPlanCapacitacionFormLayout = new javax.swing.GroupLayout(newPlanCapacitacionForm.getContentPane());
        newPlanCapacitacionForm.getContentPane().setLayout(newPlanCapacitacionFormLayout);
        newPlanCapacitacionFormLayout.setHorizontalGroup(
            newPlanCapacitacionFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(newPlanCapacitacionFormLayout.createSequentialGroup()
                .addGroup(newPlanCapacitacionFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(newPlanCapacitacionFormLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(newPlanCapacitacionFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel46)
                            .addGroup(newPlanCapacitacionFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(newPlanCapacitacionFormLayout.createSequentialGroup()
                                    .addComponent(jLabel49)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, newPlanCapacitacionFormLayout.createSequentialGroup()
                                    .addComponent(jLabel47)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, newPlanCapacitacionFormLayout.createSequentialGroup()
                                    .addComponent(jLabel48)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, newPlanCapacitacionFormLayout.createSequentialGroup()
                                    .addComponent(jLabel3)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jButton20)))))
                    .addGroup(newPlanCapacitacionFormLayout.createSequentialGroup()
                        .addGap(121, 121, 121)
                        .addComponent(jButton15)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        newPlanCapacitacionFormLayout.setVerticalGroup(
            newPlanCapacitacionFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(newPlanCapacitacionFormLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(newPlanCapacitacionFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jButton20))
                .addGap(21, 21, 21)
                .addGroup(newPlanCapacitacionFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel46)
                    .addComponent(jLabel50))
                .addGap(18, 18, 18)
                .addGroup(newPlanCapacitacionFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel47)
                    .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(newPlanCapacitacionFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel48)
                    .addComponent(jLabel52))
                .addGap(18, 18, 18)
                .addGroup(newPlanCapacitacionFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel49)
                    .addComponent(jLabel56))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton15)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        planCapacitacionForm.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        planCapacitacionForm.setTitle("Plan de capacitación");
        planCapacitacionForm.setVisible(true);

        jLabel57.setText("Nombre capacitación");

        jLabel58.setText("Estado capacitación");

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel(Arrays.copyOfRange(com.safe.service.CapacitacionService.ESTADOS, 1, 3)));

        jLabel59.setText("Tipo capacitación");

        jButton21.setText("Crear");
        jButton21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton21ActionPerformed(evt);
            }
        });

        jTable8.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre Capacitación", "Estado", "Tipo", "Editar", "Eliminar"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane12.setViewportView(jTable8);
        if (jTable8.getColumnModel().getColumnCount() > 0) {
            jTable8.getColumnModel().getColumn(0).setPreferredWidth(150);
        }

        jButton3.setText("Cerrar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closePlanCapacitacionForm(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane12)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel57)
                            .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel58)
                            .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel59, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton21))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGap(0, 1025, Short.MAX_VALUE)
                        .addComponent(jButton3)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel57)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton21)
                            .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel58)
                        .addComponent(jLabel59)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 311, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Capacitaciones", jPanel6);

        jComboBox7.setName("com.safe.entity.Sesion_Cap.capacitacionidcap"); // NOI18N

        jLabel60.setText("Capacitación");

        jLabel61.setText("Cupos");

        jFormattedTextField6.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        jFormattedTextField6.setName("com.safe.entity.Sesion_Cap.cupossesionString"); // NOI18N

        jLabel62.setText("Lugar");

        jTextField22.setName("com.safe.entity.Sesion_Cap.nombresesion"); // NOI18N

        jComboBox8.setName("com.safe.entity.Sesion_Cap.expositoridexpositor"); // NOI18N

        jLabel63.setText("Expositor");

        jLabel65.setText("Fecha");

        jFormattedTextField7.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter()));
        jFormattedTextField7.setName("com.safe.entity.Sesion_Cap.fechasesion"); // NOI18N

        jLabel66.setText("Hora inicio");

        jLabel67.setText("Hora término");

        jFormattedTextField8.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getTimeInstance(java.text.DateFormat.SHORT))));
        jFormattedTextField8.setName("com.safe.entity.Sesion_Cap.horaterminocap"); // NOI18N

        jFormattedTextField9.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getTimeInstance(java.text.DateFormat.SHORT))));
        jFormattedTextField9.setName("com.safe.entity.Sesion_Cap.horainiciocap"); // NOI18N

        jLabel68.setText("Descripción");

        jTextArea3.setColumns(20);
        jTextArea3.setRows(5);
        jTextArea3.setName("com.safe.entity.Sesion_Cap.descripcionsesion"); // NOI18N
        jScrollPane13.setViewportView(jTextArea3);

        jButton22.setText("Añadir nueva sesión");
        jButton22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton22ActionPerformed(evt);
            }
        });

        jLabel69.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel69.setText("Añadir nueva sesión");

        jButton49.setText("Cerrar");
        jButton49.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closePlanCapacitacionForm(evt);
            }
        });

        jTable9.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Capacitación", "Cupos", "Lugar", "Expositor", "Fecha", "Hora inicio", "Hora término", "Descripción"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(jTable9);
        if (jTable9.getColumnModel().getColumnCount() > 0) {
            jTable9.getColumnModel().getColumn(0).setResizable(false);
        }

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane4))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel7Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jButton22))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jFormattedTextField7, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel65, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboBox7, javax.swing.GroupLayout.Alignment.LEADING, 0, 214, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jFormattedTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel66)
                                    .addComponent(jFormattedTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addComponent(jLabel67)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel68)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addComponent(jFormattedTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(24, 24, 24)
                                        .addComponent(jScrollPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 687, Short.MAX_VALUE))
                                    .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addComponent(jTextField22, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel7Layout.createSequentialGroup()
                                                .addComponent(jLabel63)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                            .addComponent(jComboBox8, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel69)
                                    .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addComponent(jLabel60)
                                        .addGap(131, 131, 131)
                                        .addComponent(jLabel61)
                                        .addGap(38, 38, 38)
                                        .addComponent(jLabel62)))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton49)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel69)
                .addGap(12, 12, 12)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel60)
                    .addComponent(jLabel61)
                    .addComponent(jLabel62)
                    .addComponent(jLabel63))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFormattedTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel65)
                    .addComponent(jLabel66)
                    .addComponent(jLabel67)
                    .addComponent(jLabel68))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jFormattedTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jFormattedTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jFormattedTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton49)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Sesiones", jPanel7);

        jLabel70.setText("RUN trabajador");

        jButton23.setText("Agregar");
        jButton23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton23ActionPerformed(evt);
            }
        });

        jLabel71.setText("Carga masiva (CSV)");

        jButton24.setText("Cargar Archivo");

        jTable10.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Creado el", "Capacitación", "RUN", "Nombre", "Apellido", "Fono Contacto", "Email", "Quitar"
            }
        ));
        jScrollPane15.setViewportView(jTable10);

        jButton50.setText("Cerrar");
        jButton50.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closePlanCapacitacionForm(evt);
            }
        });

        jComboBox19.setName("com.safe.entity.Sesion_Cap.capacitacionidcap"); // NOI18N

        jLabel139.setText("Capacitación");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addContainerGap(1031, Short.MAX_VALUE)
                        .addComponent(jButton50))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane15)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField23, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel70))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel139)
                                    .addComponent(jComboBox19, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel8Layout.createSequentialGroup()
                                        .addComponent(jButton23)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jButton24))
                                    .addGroup(jPanel8Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(jLabel71)))))
                        .addGap(3, 3, 3)))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel70)
                    .addComponent(jLabel71)
                    .addComponent(jLabel139))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton23)
                    .addComponent(jButton24)
                    .addComponent(jComboBox19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane15, javax.swing.GroupLayout.DEFAULT_SIZE, 293, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton50)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Participantes", jPanel8);

        jComboBox9.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel72.setText("Sesión");

        jTable11.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Creado el", "RUN", "Nombre", "Apellido", "Fono Contacto", "Email", "Asistió"
            }
        ));
        jScrollPane16.setViewportView(jTable11);

        jLabel73.setText("Inscritos:");

        jLabel74.setText("99");

        jButton25.setText("Cargar asistencia");

        jButton26.setText("Descargar asistencia");
        jButton26.setToolTipText("");

        jButton51.setText("Cerrar");
        jButton51.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closePlanCapacitacionForm(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel72)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox9, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel73)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel74)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 440, Short.MAX_VALUE)
                        .addComponent(jButton25))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane16)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jButton51))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton26)))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel72)
                    .addComponent(jLabel73)
                    .addComponent(jComboBox9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel74)
                    .addComponent(jButton25))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton26)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane16, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton51)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Asistencia", jPanel9);

        jLabel75.setText("Capacitación");

        jComboBox10.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox10ActionPerformed(evt);
            }
        });

        jLabel76.setText("Inscritos:");

        jLabel77.setText("99");

        jTable12.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Creado el", "RUN", "Nombre", "Apellido", "Fono Contacto", "Email", "PDF"
            }
        ));
        jScrollPane17.setViewportView(jTable12);

        jButton52.setText("Cerrar");
        jButton52.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closePlanCapacitacionForm(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane17, javax.swing.GroupLayout.DEFAULT_SIZE, 1100, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton52))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel75)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox10, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel10Layout.createSequentialGroup()
                    .addGap(448, 448, 448)
                    .addComponent(jLabel76)
                    .addGap(18, 18, 18)
                    .addComponent(jLabel77)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel75)
                    .addComponent(jComboBox10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane17, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton52)
                .addContainerGap())
            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel10Layout.createSequentialGroup()
                    .addGap(16, 16, 16)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel76)
                        .addComponent(jLabel77))
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jTabbedPane2.addTab("Certificados", jPanel10);

        jLabel136.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel136.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel136.setText("Gestion de plan de capacitación");

        jLabel137.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel137.setText("\"Nombre de la empresa\"");

        javax.swing.GroupLayout planCapacitacionFormLayout = new javax.swing.GroupLayout(planCapacitacionForm.getContentPane());
        planCapacitacionForm.getContentPane().setLayout(planCapacitacionFormLayout);
        planCapacitacionFormLayout.setHorizontalGroup(
            planCapacitacionFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel136, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel137, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(planCapacitacionFormLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2)
                .addContainerGap())
        );
        planCapacitacionFormLayout.setVerticalGroup(
            planCapacitacionFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(planCapacitacionFormLayout.createSequentialGroup()
                .addComponent(jLabel136)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel137)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane2)
                .addContainerGap())
        );

        planSaludMain.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        planSaludMain.setVisible(true);

        jFormattedTextField14.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("dd/MM/yyyy"))));
        jFormattedTextField14.setToolTipText("dd/mm/yyyy");
        jFormattedTextField14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFormattedTextField14ActionPerformed(evt);
            }
        });

        jFormattedTextField15.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter()));
        jFormattedTextField15.setToolTipText("dd/mm/yyyy");
        jFormattedTextField15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFormattedTextField15ActionPerformed(evt);
            }
        });

        jButton33.setText("Buscar");
        jButton33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton33ActionPerformed(evt);
            }
        });

        jLabel98.setText("Filtro de Fecha");
        jLabel98.setToolTipText("");

        jLabel99.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel99.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel99.setText("Gestión de plan de salud");
        jLabel99.setToolTipText("");

        jLabel100.setText("RUT Cliente");
        jLabel100.setToolTipText("");

        jTable18.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Creado el", "Fecha Inicio", "Fecha Fin", "Cliente", "Sessiones", "Estado", "Gestionar"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable18.getTableHeader().setReorderingAllowed(false);
        jScrollPane24.setViewportView(jTable18);
        jTable18.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        jTextField27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField27ActionPerformed(evt);
            }
        });

        jButton34.setText("Crear plan anual");
        jButton34.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton34ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout planSaludMainLayout = new javax.swing.GroupLayout(planSaludMain.getContentPane());
        planSaludMain.getContentPane().setLayout(planSaludMainLayout);
        planSaludMainLayout.setHorizontalGroup(
            planSaludMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(planSaludMainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(planSaludMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane24)
                    .addComponent(jLabel99, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, planSaludMainLayout.createSequentialGroup()
                        .addComponent(jLabel100)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField27, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel98, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFormattedTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jFormattedTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton33))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, planSaludMainLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton34)))
                .addContainerGap())
        );
        planSaludMainLayout.setVerticalGroup(
            planSaludMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(planSaludMainLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel99)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton34)
                .addGap(14, 14, 14)
                .addGroup(planSaludMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton33)
                    .addComponent(jFormattedTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFormattedTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel98)
                    .addComponent(jLabel100)
                    .addComponent(jTextField27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane24, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        newPlanSaludForm.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        newPlanSaludForm.setTitle("Nuevo plan salud");
        newPlanSaludForm.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        newPlanSaludForm.setVisible(true);

        jLabel78.setText("RUT Empresa");

        jLabel79.setText("Nombre Cliente");

        jLabel80.setText("Telefono Empresa");

        jLabel101.setText("Mail Contacto");

        jLabel102.setText("Dirección");

        jButton27.setText("Crear plan salud");
        jButton27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton27ActionPerformed(evt);
            }
        });

        jButton35.setText("Buscar");
        jButton35.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton35ActionPerformed(evt);
            }
        });

        jLabel103.setText("jLabel50");

        jLabel104.setText("jLabel51");

        jLabel105.setText("jLabel52");

        jLabel106.setText("jLabel56");

        javax.swing.GroupLayout newPlanSaludFormLayout = new javax.swing.GroupLayout(newPlanSaludForm.getContentPane());
        newPlanSaludForm.getContentPane().setLayout(newPlanSaludFormLayout);
        newPlanSaludFormLayout.setHorizontalGroup(
            newPlanSaludFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(newPlanSaludFormLayout.createSequentialGroup()
                .addGroup(newPlanSaludFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(newPlanSaludFormLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(newPlanSaludFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel79)
                            .addGroup(newPlanSaludFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(newPlanSaludFormLayout.createSequentialGroup()
                                    .addComponent(jLabel102)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel106, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jLabel103, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, newPlanSaludFormLayout.createSequentialGroup()
                                    .addComponent(jLabel80)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel104, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, newPlanSaludFormLayout.createSequentialGroup()
                                    .addComponent(jLabel101)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel105, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, newPlanSaludFormLayout.createSequentialGroup()
                                    .addComponent(jLabel78)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jTextField24, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jButton35)))))
                    .addGroup(newPlanSaludFormLayout.createSequentialGroup()
                        .addGap(121, 121, 121)
                        .addComponent(jButton27)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        newPlanSaludFormLayout.setVerticalGroup(
            newPlanSaludFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(newPlanSaludFormLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(newPlanSaludFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel78)
                    .addComponent(jButton35))
                .addGap(21, 21, 21)
                .addGroup(newPlanSaludFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel79)
                    .addComponent(jLabel103))
                .addGap(18, 18, 18)
                .addGroup(newPlanSaludFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel80)
                    .addComponent(jLabel104, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(newPlanSaludFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel101)
                    .addComponent(jLabel105))
                .addGap(18, 18, 18)
                .addGroup(newPlanSaludFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel102)
                    .addComponent(jLabel106))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton27)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        planSaludForm.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        planSaludForm.setTitle("Plan de salud");
        planSaludForm.setVisible(true);

        jComboBox13.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel81.setText("Capacitación");

        jLabel82.setText("Cupos");

        jFormattedTextField10.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));

        jLabel83.setText("Lugar");

        jComboBox14.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel84.setText("Expositor");

        jLabel85.setText("Fecha");

        jFormattedTextField11.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT))));

        jLabel86.setText("Hora inicio");

        jLabel87.setText("Hora término");

        jFormattedTextField12.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));

        jFormattedTextField13.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));

        jLabel88.setText("Descripción");

        jTextArea4.setColumns(20);
        jTextArea4.setRows(5);
        jScrollPane19.setViewportView(jTextArea4);

        jTable14.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Capacitación", "Cupos", "Lugar", "Expositor", "Fecha", "Hora inicio", "Hora término", "Descripción"
            }
        ));
        jScrollPane20.setViewportView(jTable14);
        if (jTable14.getColumnModel().getColumnCount() > 0) {
            jTable14.getColumnModel().getColumn(0).setResizable(false);
        }

        jButton28.setText("Añadir nueva sesión");

        jLabel89.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel89.setText("Añadir nueva sesión");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton28))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane20, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jFormattedTextField11, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel85, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboBox13, javax.swing.GroupLayout.Alignment.LEADING, 0, 214, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jFormattedTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel86)
                                    .addComponent(jFormattedTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel12Layout.createSequentialGroup()
                                        .addComponent(jTextField25, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jComboBox14, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(jPanel12Layout.createSequentialGroup()
                                        .addComponent(jLabel87)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel88)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(jPanel12Layout.createSequentialGroup()
                                        .addComponent(jFormattedTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(24, 24, 24)
                                        .addComponent(jScrollPane19))))
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel89)
                                    .addGroup(jPanel12Layout.createSequentialGroup()
                                        .addComponent(jLabel81)
                                        .addGap(131, 131, 131)
                                        .addComponent(jLabel82)
                                        .addGap(38, 38, 38)
                                        .addComponent(jLabel83)
                                        .addGap(142, 142, 142)
                                        .addComponent(jLabel84)))
                                .addGap(0, 137, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel89)
                .addGap(12, 12, 12)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel81)
                    .addComponent(jLabel82)
                    .addComponent(jLabel83)
                    .addComponent(jLabel84))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFormattedTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel85)
                    .addComponent(jLabel86)
                    .addComponent(jLabel87)
                    .addComponent(jLabel88))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jFormattedTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jFormattedTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jFormattedTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton28)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane20, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane3.addTab("Sesiones", jPanel12);

        jLabel90.setText("Agregar trabajador por RUN");

        jButton29.setText("Agregar");

        jLabel91.setText("Carga masiva (CSV)");

        jButton30.setText("Cargar Archivo");

        jTable15.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Creado el", "RUN", "Nombre", "Apellido", "Fono Contacto", "Email", "Quitar"
            }
        ));
        jScrollPane21.setViewportView(jTable15);

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane21, javax.swing.GroupLayout.DEFAULT_SIZE, 691, Short.MAX_VALUE)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel90)
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addComponent(jTextField26, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton29)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel91)
                            .addComponent(jButton30))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel90)
                    .addComponent(jLabel91))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton29)
                    .addComponent(jButton30))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane21, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("Participantes", jPanel13);

        jComboBox15.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel92.setText("Sesión");

        jTable16.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Creado el", "RUN", "Nombre", "Apellido", "Fono Contacto", "Email", "Quitar"
            }
        ));
        jScrollPane22.setViewportView(jTable16);

        jLabel93.setText("Inscritos:");

        jLabel94.setText("99");

        jButton31.setText("Cargar asistencia");

        jButton32.setText("Descargar asistencia");
        jButton32.setToolTipText("");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel92)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox15, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel93)
                .addGap(18, 18, 18)
                .addComponent(jLabel94)
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton32)
                    .addComponent(jButton31))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane22, javax.swing.GroupLayout.PREFERRED_SIZE, 691, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel92)
                    .addComponent(jLabel93)
                    .addComponent(jComboBox15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel94)
                    .addComponent(jButton31))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton32)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane22, javax.swing.GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane3.addTab("Asistencia", jPanel14);

        jLabel95.setText("Sesión");

        jComboBox16.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox16ActionPerformed(evt);
            }
        });

        jLabel96.setText("Inscritos:");

        jLabel97.setText("99");

        jTable17.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Creado el", "RUN", "Nombre", "Apellido", "Fono Contacto", "Email", "PDF"
            }
        ));
        jScrollPane23.setViewportView(jTable17);

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane23, javax.swing.GroupLayout.DEFAULT_SIZE, 703, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel15Layout.createSequentialGroup()
                    .addGap(12, 12, 12)
                    .addComponent(jLabel95)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jComboBox16, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jLabel96)
                    .addGap(18, 18, 18)
                    .addComponent(jLabel97)
                    .addContainerGap(210, Short.MAX_VALUE)))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(jScrollPane23, javax.swing.GroupLayout.DEFAULT_SIZE, 347, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel15Layout.createSequentialGroup()
                    .addGap(11, 11, 11)
                    .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel95)
                        .addComponent(jLabel96)
                        .addComponent(jComboBox16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel97))
                    .addContainerGap(360, Short.MAX_VALUE)))
        );

        jTabbedPane3.addTab("Examenes", jPanel15);

        jLabel107.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel107.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel107.setText("Gestion de plan de salud");

        jLabel108.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel108.setText("\"Nombre de la empresa\"");

        javax.swing.GroupLayout planSaludFormLayout = new javax.swing.GroupLayout(planSaludForm.getContentPane());
        planSaludForm.getContentPane().setLayout(planSaludFormLayout);
        planSaludFormLayout.setHorizontalGroup(
            planSaludFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(planSaludFormLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(planSaludFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel107, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTabbedPane3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel108, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        planSaludFormLayout.setVerticalGroup(
            planSaludFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(planSaludFormLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel107)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel108)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 421, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        expositorMain.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        expositorMain.setVisible(true);

        jComboBoxEstado3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Activo", "Desactivo" }));
        jComboBoxEstado3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxEstado3ActionPerformed(evt);
            }
        });

        jFormattedTextField16.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.MEDIUM))));
        jFormattedTextField16.setToolTipText("dd-mm-yyyy");

        jFormattedTextField17.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.MEDIUM))));
        jFormattedTextField17.setToolTipText("dd-mm-yyyy");

        jButton36.setText("Buscar");
        jButton36.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton36ActionPerformed(evt);
            }
        });

        jLabel109.setText("Filtro de Estado");
        jLabel109.setToolTipText("");

        jLabel110.setText("Filtro de Fecha");
        jLabel110.setToolTipText("");
        jLabel110.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel110MouseClicked(evt);
            }
        });

        jLabel111.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel111.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel111.setText("Listado de expositores");
        jLabel111.setToolTipText("");

        jTable13.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Creado el", "Nombre", "Teléfono", "Email", "Estado", "Detalle"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable13.setColumnSelectionAllowed(true);
        jTable13.getTableHeader().setReorderingAllowed(false);
        jScrollPane18.setViewportView(jTable13);
        jTable13.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        javax.swing.GroupLayout expositorMainLayout = new javax.swing.GroupLayout(expositorMain.getContentPane());
        expositorMain.getContentPane().setLayout(expositorMainLayout);
        expositorMainLayout.setHorizontalGroup(
            expositorMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(expositorMainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(expositorMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane18, javax.swing.GroupLayout.DEFAULT_SIZE, 851, Short.MAX_VALUE)
                    .addComponent(jLabel111, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(expositorMainLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(expositorMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel109, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel110, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(expositorMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(expositorMainLayout.createSequentialGroup()
                                .addComponent(jFormattedTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jFormattedTextField17, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton36))
                            .addComponent(jComboBoxEstado3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        expositorMainLayout.setVerticalGroup(
            expositorMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(expositorMainLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel111)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(expositorMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton36)
                    .addComponent(jFormattedTextField17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFormattedTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel110))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(expositorMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxEstado3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel109))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane18, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
                .addContainerGap())
        );

        expositorForm.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        expositorForm.setVisible(true);

        jLabelExpositorTitle.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabelExpositorTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelExpositorTitle.setText("Crear expositor");

        jLabel113.setText("RUN");

        jTextField28.setName("com.safe.entity.Expositor.runexpositor"); // NOI18N

        jLabel114.setText("Nombres");

        jTextField31.setName("com.safe.entity.Expositor.nombreexpositor"); // NOI18N

        jLabel119.setText("Fono Contacto");

        jTextField34.setName("com.safe.entity.Expositor.telexpositor"); // NOI18N

        jLabel120.setText("Mail Contacto");

        jTextField35.setName("com.safe.entity.Expositor.mailexpositor"); // NOI18N

        jButton37.setText("Guardar");
        jButton37.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton37ActionPerformed(evt);
            }
        });

        jButton38.setText("Cancelar");
        jButton38.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton38ActionPerformed(evt);
            }
        });

        jButton39.setText("Eliminar");
        jButton39.setEnabled(false);
        jButton39.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton39ActionPerformed(evt);
            }
        });

        jLabel122.setText("Estado");

        jComboBox12.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Desactivado", "Activo" }));
        jComboBox12.setName("com.safe.entity.Expositor.estadoexpositorIndex"); // NOI18N

        javax.swing.GroupLayout expositorFormLayout = new javax.swing.GroupLayout(expositorForm.getContentPane());
        expositorForm.getContentPane().setLayout(expositorFormLayout);
        expositorFormLayout.setHorizontalGroup(
            expositorFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(expositorFormLayout.createSequentialGroup()
                .addGroup(expositorFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, expositorFormLayout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addGroup(expositorFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel114)
                            .addComponent(jLabel113))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(expositorFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField28, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                            .addComponent(jTextField31))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                        .addGroup(expositorFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(expositorFormLayout.createSequentialGroup()
                                .addComponent(jLabel122)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jComboBox12, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, expositorFormLayout.createSequentialGroup()
                                .addComponent(jLabel120)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                                .addComponent(jTextField35, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, expositorFormLayout.createSequentialGroup()
                                .addComponent(jLabel119)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextField34, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, expositorFormLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton39)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton38)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton37)))
                .addContainerGap())
            .addComponent(jLabelExpositorTitle, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        expositorFormLayout.setVerticalGroup(
            expositorFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(expositorFormLayout.createSequentialGroup()
                .addComponent(jLabelExpositorTitle)
                .addGap(21, 21, 21)
                .addGroup(expositorFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(expositorFormLayout.createSequentialGroup()
                        .addComponent(jLabel113)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(expositorFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel114)
                            .addComponent(jTextField31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(expositorFormLayout.createSequentialGroup()
                        .addGroup(expositorFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel119)
                            .addComponent(jTextField34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(expositorFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel120)
                            .addComponent(jTextField35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(expositorFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel122)
                            .addComponent(jComboBox12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 70, Short.MAX_VALUE)
                .addGroup(expositorFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton37)
                    .addComponent(jButton38)
                    .addComponent(jButton39))
                .addContainerGap())
        );

        medicoMain.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        medicoMain.setVisible(true);

        jComboBoxEstado4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Activo", "Desactivado" }));
        jComboBoxEstado4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxEstado4ActionPerformed(evt);
            }
        });

        jFormattedTextField18.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter()));
        jFormattedTextField18.setToolTipText("dd-mm-yyyy");

        jFormattedTextField19.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter()));
        jFormattedTextField19.setToolTipText("dd-mm-yyyy");

        jButton40.setText("Buscar");
        jButton40.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton40ActionPerformed(evt);
            }
        });

        jLabel112.setText("Filtro de Estado");
        jLabel112.setToolTipText("");

        jLabel115.setText("Filtro de Fecha");
        jLabel115.setToolTipText("");

        jLabel116.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel116.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel116.setText("Listado de médicos");
        jLabel116.setToolTipText("");

        jTable19.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Creado el", "Nombre", "Teléfono", "Email", "Estado", "Detalle"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable19.getTableHeader().setReorderingAllowed(false);
        jScrollPane25.setViewportView(jTable19);
        jTable19.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        javax.swing.GroupLayout medicoMainLayout = new javax.swing.GroupLayout(medicoMain.getContentPane());
        medicoMain.getContentPane().setLayout(medicoMainLayout);
        medicoMainLayout.setHorizontalGroup(
            medicoMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(medicoMainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(medicoMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane25, javax.swing.GroupLayout.DEFAULT_SIZE, 680, Short.MAX_VALUE)
                    .addComponent(jLabel116, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(medicoMainLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(medicoMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel112, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel115, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(medicoMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(medicoMainLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jComboBoxEstado4, 0, 246, Short.MAX_VALUE))
                            .addGroup(medicoMainLayout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(jFormattedTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jFormattedTextField19)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton40)))))
                .addContainerGap())
        );
        medicoMainLayout.setVerticalGroup(
            medicoMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(medicoMainLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel116)
                .addGap(36, 36, 36)
                .addGroup(medicoMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton40)
                    .addComponent(jFormattedTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFormattedTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel115))
                .addGap(18, 18, 18)
                .addGroup(medicoMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxEstado4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel112))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane25, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                .addContainerGap())
        );

        jFormattedTextField18.getAccessibleContext().setAccessibleName("");

        medicoForm.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        medicoForm.setVisible(true);

        jLabelMedicoTitle.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabelMedicoTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelMedicoTitle.setText("Crear médico");

        jLabel117.setText("RUN");

        jTextField32.setName("com.safe.entity.Medico.runmedico"); // NOI18N

        jLabel118.setText("Nombres");

        jTextField33.setName("com.safe.entity.Medico.nombremedico"); // NOI18N

        jLabel121.setText("Fono Contacto");

        jTextField36.setName("com.safe.entity.Medico.telmedico"); // NOI18N

        jLabel123.setText("Mail Contacto");

        jTextField37.setName("com.safe.entity.Medico.mailmedico"); // NOI18N

        jButton41.setText("Guardar");
        jButton41.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton41ActionPerformed(evt);
            }
        });

        jButton42.setText("Cancelar");
        jButton42.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton42ActionPerformed(evt);
            }
        });

        jButton43.setText("Eliminar");
        jButton43.setEnabled(false);
        jButton43.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton43ActionPerformed(evt);
            }
        });

        jLabel124.setText("Estado");

        jComboBox17.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Desactivado", "Activo" }));
        jComboBox17.setName("com.safe.entity.Medico.estadomedicoIndex"); // NOI18N

        jLabel125.setText("Universidad");

        jTextField38.setName("com.safe.entity.Medico.universidadMed"); // NOI18N

        javax.swing.GroupLayout medicoFormLayout = new javax.swing.GroupLayout(medicoForm.getContentPane());
        medicoForm.getContentPane().setLayout(medicoFormLayout);
        medicoFormLayout.setHorizontalGroup(
            medicoFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(medicoFormLayout.createSequentialGroup()
                .addGroup(medicoFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, medicoFormLayout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addGroup(medicoFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(medicoFormLayout.createSequentialGroup()
                                .addGroup(medicoFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel118, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel117))
                                .addGap(27, 27, 27)
                                .addGroup(medicoFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField33, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                                    .addComponent(jTextField32)))
                            .addGroup(medicoFormLayout.createSequentialGroup()
                                .addComponent(jLabel125)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField38, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                        .addGroup(medicoFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(medicoFormLayout.createSequentialGroup()
                                .addComponent(jLabel124)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jComboBox17, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, medicoFormLayout.createSequentialGroup()
                                .addComponent(jLabel123)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                                .addComponent(jTextField37, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, medicoFormLayout.createSequentialGroup()
                                .addComponent(jLabel121)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextField36, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, medicoFormLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton43)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton42)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton41)))
                .addContainerGap())
            .addComponent(jLabelMedicoTitle, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        medicoFormLayout.setVerticalGroup(
            medicoFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(medicoFormLayout.createSequentialGroup()
                .addComponent(jLabelMedicoTitle)
                .addGap(21, 21, 21)
                .addGroup(medicoFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(medicoFormLayout.createSequentialGroup()
                        .addGroup(medicoFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel121)
                            .addComponent(jTextField36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(medicoFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel123)
                            .addComponent(jTextField37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(medicoFormLayout.createSequentialGroup()
                        .addComponent(jLabel117)
                        .addGap(18, 18, 18)
                        .addGroup(medicoFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel118)
                            .addComponent(jTextField33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(medicoFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel124)
                    .addComponent(jComboBox17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel125))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 70, Short.MAX_VALUE)
                .addGroup(medicoFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton41)
                    .addComponent(jButton42)
                    .addComponent(jButton43))
                .addContainerGap())
        );

        calendarMain.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        calendarMain.setVisible(true);

        jButton44.setText("Buscar");
        jButton44.setToolTipText("");
        jButton44.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton44ActionPerformed(evt);
            }
        });

        jCalendar1.setDate(new java.util.Date(1543881707000L));

        jTable20.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre", "Hora Inicio", "Hora Fin"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane26.setViewportView(jTable20);

        jLabel126.setText("RUT Cliente");

        jComboBox11.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Capacitación", "Salud", "Capacitación y Salud" }));

        jLabel127.setText("Tipo Plan");

        jLabel128.setText("Nombre cliente");

        jLabel129.setText("NOMBRE DEL CLIENTE");
        jLabel129.setName("com.safe.entity.Cliente.razonsocial"); // NOI18N

        jLabel130.setText("RUT");

        jLabel131.setText("99999999-9");
        jLabel131.setName("com.safe.entity.Cliente.rutcliente"); // NOI18N

        javax.swing.GroupLayout calendarMainLayout = new javax.swing.GroupLayout(calendarMain.getContentPane());
        calendarMain.getContentPane().setLayout(calendarMainLayout);
        calendarMainLayout.setHorizontalGroup(
            calendarMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(calendarMainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(calendarMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(calendarMainLayout.createSequentialGroup()
                        .addComponent(jLabel128)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel129, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel130)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel131, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jCalendar1, javax.swing.GroupLayout.PREFERRED_SIZE, 584, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(calendarMainLayout.createSequentialGroup()
                        .addComponent(jLabel126)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField39, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton44)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(calendarMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(calendarMainLayout.createSequentialGroup()
                        .addComponent(jLabel127)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox11, 0, 207, Short.MAX_VALUE))
                    .addComponent(jScrollPane26, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        calendarMainLayout.setVerticalGroup(
            calendarMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(calendarMainLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(calendarMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel126)
                    .addComponent(jTextField39, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton44)
                    .addComponent(jComboBox11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel127))
                .addGap(10, 10, 10)
                .addGroup(calendarMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel128)
                    .addComponent(jLabel129)
                    .addComponent(jLabel130)
                    .addComponent(jLabel131))
                .addGap(18, 18, 18)
                .addGroup(calendarMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane26, javax.swing.GroupLayout.DEFAULT_SIZE, 429, Short.MAX_VALUE)
                    .addComponent(jCalendar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema de administración SAFE");
        setPreferredSize(new java.awt.Dimension(960, 768));
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

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("Perfil de usuario:");

        jLabelProfile.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelProfile.setText("Administrador");

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 48)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Bienvenido");

        javax.swing.GroupLayout jPanelWelcomeLayout = new javax.swing.GroupLayout(jPanelWelcome);
        jPanelWelcome.setLayout(jPanelWelcomeLayout);
        jPanelWelcomeLayout.setHorizontalGroup(
            jPanelWelcomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelWelcomeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelWelcomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelWelcomeLayout.createSequentialGroup()
                        .addGap(293, 293, 293)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelProfile, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 305, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanelWelcomeLayout.setVerticalGroup(
            jPanelWelcomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelWelcomeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanelWelcomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabelProfile))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelWelcome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(239, 239, 239)
                .addComponent(jPanelWelcome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(427, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuEmpCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuEmpCrearActionPerformed
        selectedEntity = null;
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
        jLabelClienteTitle.setText("Crear empresa");
        changePanel(clienteForm);
    }//GEN-LAST:event_jMenuEmpCrearActionPerformed

    private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved
        session.alive();
    }//GEN-LAST:event_formMouseMoved

    private void formWindowStateChanged(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowStateChanged

    }//GEN-LAST:event_formWindowStateChanged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // buscar capacitaciones
        jTable1.setRowSorter(null);
        RowFilter<Object, Object> filter = new RowFilter<Object, Object>() {
            @Override
            public boolean include(Entry entry) {
              if(entry.getValue(1) == null) return false;
              String fini = dateInvert((String)entry.getValue(1));
              String ffin = dateInvert((String)entry.getValue(2));
              String rut = (String) entry.getValue(3);
              String rutSearch = jTextField17.getText();
              String fiSearch = dateInvert(jFormattedTextField1.getText());
              String ffSearch = dateInvert(jFormattedTextField2.getText());
              
              return (rut.contains(rutSearch) || rutSearch.length() == 0) &&
              (fini.equals(fiSearch) || fiSearch.length() == 0) &&
              (ffin.equals(ffSearch) || ffSearch.length() == 0);
            }
        };
        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(jTable1.getModel());
        sorter.setRowFilter(filter);
        jTable1.setRowSorter(sorter);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jMenuTerCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuTerCrearActionPerformed
        selectedEntity = null;
        changePanel(evaluacionForm);
    }//GEN-LAST:event_jMenuTerCrearActionPerformed

    private void jMenuTerListarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuTerListarActionPerformed
        jMenuTerEngListarActionPerformed(evt);
    }//GEN-LAST:event_jMenuTerListarActionPerformed

    private void jMenuTerrenoEngMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuTerrenoEngMouseClicked

    }//GEN-LAST:event_jMenuTerrenoEngMouseClicked

    private void jMenuTerEngListarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuTerEngListarActionPerformed
        //listar solicitudes de evaluaciones en terreno
        selectedEntity = null;
        secondEntity = null;
        if(token.getUserRole().equals("ROLE_SUPERVISOR")) {
            jLabel7.setText("Observaciones al ingeniero");
            jButton2.setText("Derivar al ingeniero");
        }else if(token.getUserRole().equals("ROLE_ENGINEER")) {
            jLabel7.setText("Observaciones al supervisor");
            jButton2.setText("Derivar al supervisor");
        }
        Eval_Terr[] evaluaciones = evaluacionTerrenoService.getCollection();
        if(evaluaciones != null ){
            DefaultTableModel model = (DefaultTableModel)jTable7.getModel();            
            model.setRowCount(0);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String createdAt, derivatedAt;
            Cliente cliente;
            for(Eval_Terr e: evaluaciones){
                SoliEvalTer solicitud = solicitudService.getOne(e.getSolievalteridsolicitud().intValue());
                cliente = clienteService.getOne((int)solicitud.getClienteidcliente());
                try {
                    createdAt = date.format(df.parse(solicitud.getFechacreacion()));
                } catch (ParseException ex) {
                    createdAt = "";
                }
                try {
                    derivatedAt = date.format(df.parse(solicitud.getFechaderivacion()));
                } catch (ParseException ex) {
                    derivatedAt = "";
                }
                
                Object[] item = {
                    createdAt,
                    derivatedAt,
                    "TECNICO", //TODO: falta tecnico ,
                    cliente.getRazonsocial(),
                    SolicitudService.TIPOS[(int)solicitud.getTipovisitteridtipovister()],
                    SolicitudService.ESTADOS[(int)solicitud.getEstadosolievalter()],
                    solicitud,
                    e,
                };
                model.addRow(item);
            }
        }
        changePanel(evaluacionMain);
    }//GEN-LAST:event_jMenuTerEngListarActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if(token.getUserRole().equals("ROLE_SUPERVISOR")) {
            //derivar a ingeniero    
            Obs_Ingeniero observacion = new Obs_Ingeniero();
            observacion.setObsing(jTextArea1.getText());
            observacion.setEvalterridevalterr(((Eval_Terr)selectedEntity).getIdevalterr());
            observacionService.saveIngeniero(observacion);
            
            SoliEvalTer solicitud = (SoliEvalTer)secondEntity;
            solicitud.setEstadosolievalter(3);
            solicitudService.save(solicitud);
            
            DefaultTableModel model = (DefaultTableModel)jTable3.getModel();
            Object[] item = {
                observacion.getFechahoraobsing(),
                observacion.getObsing(),
            };
            model.addRow(item);
            
        }else if(token.getUserRole().equals("ROLE_ENGINEER")) {
            // derivar a supervisor
            Obs_Supervisor observacion = new Obs_Supervisor();
            observacion.setObssupervisor(jTextArea1.getText());
            observacion.setEvalterridevalterr(((Eval_Terr)selectedEntity).getIdevalterr());
            observacionService.saveSupervisor(observacion);
            
            SoliEvalTer solicitud = (SoliEvalTer)secondEntity;
            solicitud.setEstadosolievalter(2);
            solicitudService.save(solicitud);
            
            DefaultTableModel model = (DefaultTableModel)jTable2.getModel();
            Object[] item = {
                observacion.getFechahoraobssupervisor(),
                observacion.getObssupervisor(),
            };
            model.addRow(item);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        //rechazar y derivar al tecnico en terreno y queda registrado en el historico del supervisor
        Obs_Supervisor observacion = new Obs_Supervisor();
        observacion.setObssupervisor(jTextArea1.getText());
        observacion.setEvalterridevalterr(((Eval_Terr)selectedEntity).getIdevalterr());
        observacionService.saveSupervisor(observacion);
        
        SoliEvalTer solicitud = (SoliEvalTer)secondEntity;
        solicitud.setEstadosolievalter(5);
        solicitudService.save(solicitud);
        
        DefaultTableModel model = (DefaultTableModel)jTable2.getModel();
        Object[] item = {
            observacion.getFechahoraobssupervisor(),
            observacion.getObssupervisor(),
        };
        model.addRow(item);
        JOptionPane.showMessageDialog(null, "Se ha registrado la observacion de rechazo correctamente.");
        evaluacionForm.setVisible(false);
        jMenuTerEngListarActionPerformed(null);
        
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
        selectedEntity = null;
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
        if(selectedEntity instanceof Cliente) {
            cliente = (Cliente)selectedEntity;
        }
        Bind.setEntity(cliente, this);
        cliente.setEstadocliente(1);
        long idcliente = clienteService.save(cliente);
        cliente.setIdcliente(idcliente);
        selectedEntity = cliente;
        jLabelClienteTitle.setText("Editar empresa");
        jButton8.setEnabled(true);
        JOptionPane.showMessageDialog(null, "Empresa se ha guardado correctamente.");
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        int dialogResult = JOptionPane.showConfirmDialog (null, "¿Desea eliminar esta empresa?","Eliminar",JOptionPane.YES_NO_OPTION);
        if(dialogResult == JOptionPane.YES_OPTION){
            if(selectedEntity instanceof Cliente) {
                Cliente cliente = (Cliente)selectedEntity;
                clienteService.delete(cliente.getRutcliente());
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

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // guardar usuario
        Usuario usuario = new Usuario();
        if(selectedEntity instanceof Usuario) {
            usuario = (Usuario)selectedEntity;
        }
        Bind.setEntity(usuario, this);
        usuario.setClienteidcliente(1); //TODO: debe elegirse de un combobox
        long id = usuarioService.save(usuario);
        usuario.setIdusuario(id);
        selectedEntity = usuario;
        jLabelUsuarioTitle.setText("Editar usuario");
        jButton12.setEnabled(true);
        JOptionPane.showMessageDialog(null, "Usuario se ha guardado correctamente.");
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
            if(selectedEntity instanceof Usuario) {
                Usuario usuario  = (Usuario)selectedEntity;
                usuarioService.delete(usuario.getRunusuario());
            }
            jButton12.setEnabled(false);
            usuarioForm.setVisible(false);
            jMenuUsrListarActionPerformed(evt);
        }
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jMenuUsrListarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuUsrListarActionPerformed
        // listar usuarios
        selectedEntity = null;
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
        selectedEntity = null;
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
        jLabelUsuarioTitle.setText("Crear usuario");
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
              return r.toLowerCase().contains(nombre.toLowerCase()) || nombre.equals("");
            }
        };
        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(jTable6.getModel());
        sorter.setRowFilter(filter);
        jTable6.setRowSorter(sorter);
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jMenuTipoCapListarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuTipoCapListarActionPerformed
        // listado de capacitaciones
        selectedEntity = null;
        TipoCapacitacion[] tipos = tipoCapacitacionService.getCollection();
        if(tipos != null ){
            DefaultTableModel model = (DefaultTableModel)jTable6.getModel();
            model.setRowCount(0);
            for(TipoCapacitacion t: tipos){
                Object[] item = {
                    t.getDescripcap(),
                    TipoCapacitacionService.ESTADOS[(int)t.getEstado()],
                    t,
                };
                model.addRow(item);
            }
        }
        changePanel(tipoCapacitacionMain);
    }//GEN-LAST:event_jMenuTipoCapListarActionPerformed
    
    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        // guardar tipo capacitación
        TipoCapacitacion tipo = new TipoCapacitacion();
        if (selectedEntity instanceof TipoCapacitacion){
            tipo = (TipoCapacitacion)selectedEntity;
        }
        Bind.setEntity(tipo, this);
        long id = tipoCapacitacionService.save(tipo);
        tipo.setIdtipocap(id);
        jLabelTipoCapacitacionTitle.setText("Editar tipo de capacitación");
        jButton19.setEnabled(true);
        selectedEntity = tipo;
        JOptionPane.showMessageDialog(null, "Tipo de capacitación se ha guardado correctamente.");
    }//GEN-LAST:event_jButton17ActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        // cancelar tipo capacitación:
        tipoCapacitacionForm.setVisible(false);
        jMenuTipoCapListarActionPerformed(evt);
    }//GEN-LAST:event_jButton18ActionPerformed

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        // Eliminar capacitacion
        int dialogResult = JOptionPane.showConfirmDialog (null, "¿Desea eliminar este tipo de capacitación?","Eliminar",JOptionPane.YES_NO_OPTION);
        if(dialogResult == JOptionPane.YES_OPTION){
            if(selectedEntity instanceof TipoCapacitacion) {
                TipoCapacitacion tipo = (TipoCapacitacion)selectedEntity;
                tipoCapacitacionService.delete(tipo.getIdtipocap());
            }
            jButton19.setEnabled(false);
            tipoCapacitacionForm.setVisible(false);
            jMenuTipoCapListarActionPerformed(evt);
        }
    }//GEN-LAST:event_jButton19ActionPerformed

    private void jMenuTipoCapCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuTipoCapCrearActionPerformed
        // Crear capacitacion
        selectedEntity = null;
        jTextField30.setText("");
        jComboBox5.setSelectedIndex(0);
        jButton19.setEnabled(false);
        selectedEntity = null;
        jLabelTipoCapacitacionTitle.setText("Crear tipo de capacitación");
        changePanel(tipoCapacitacionForm);
    }//GEN-LAST:event_jMenuTipoCapCrearActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        //listar planes de capacitaciones
        selectedEntity = null;
        PlanCapacitacion[] planes = planCapacitacionService.getCollection();
        if(planes != null ){
            DefaultTableModel model = (DefaultTableModel)jTable1.getModel();
            model.setRowCount(0);
            for(PlanCapacitacion p: planes){
                Cliente cliente = clienteService.getOne((int)p.getClienteidcliente());
                Date fechacreado = new Date();
                try {
                    fechacreado = date.parse(p.getFechacreacion());
                } catch (ParseException ex) {}
                Object[] item = {
                    date.format(fechacreado),
                    date.format(new Date()), //TODO: falta fecha de inicio
                    date.format(new Date()), //TODO: falta fecha de fin
                    cliente.getRutcliente(),
                    "0", //TODO: falta cantidad de sesiones
                    CapacitacionService.ESTADOS[(int)p.getEstadoplancap()],
                    p,
                };
                model.addRow(item);
            }
        }
        changePanel(planCapacitacionMain);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jComboBoxEstado2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxEstado2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxEstado2ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jFormattedTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFormattedTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextField2ActionPerformed

    private void jFormattedTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFormattedTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextField1ActionPerformed

    private void jTextField17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField17ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField17ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        //crear nuevo plan
        changePanel(newPlanCapacitacionForm);
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton20ActionPerformed
        // buscar cliente por rut        
        Bind.setComponent(new Cliente(), this);
        Cliente cliente = clienteService.getOneByRut(jTextField18.getText());
        if(cliente != null) {
            Bind.setComponent(cliente, this);
            selectedEntity = cliente;
        } else {
            JOptionPane.showMessageDialog(null, "Empresa no encotrada.");
        }
    }//GEN-LAST:event_jButton20ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        // TODO add your handling code here:
        PlanCapacitacion plan = new PlanCapacitacion();
        
        Cliente cliente = (Cliente)selectedEntity;
        selectedEntity = null;
        plan.setClienteidcliente(cliente.getIdcliente());
        plan.setEstadoplancap(1);
        plan.setFechacreacion(date.format(new Date()));
        plan = planCapacitacionService.save(plan);
        loadGestionCapacitacion(plan.getIdplancap());
        changePanel(planCapacitacionForm);
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton21ActionPerformed
        // crear nueva capacitacion
        Capacitacion capacitacion = new Capacitacion();
        capacitacion.setEstadocapacitacion(jComboBox4.getSelectedIndex() + 1);
        capacitacion.setNombrecapacitacion(jTextField19.getText());
        
        TipoCapacitacion capTipo = (TipoCapacitacion)jComboBox6.getSelectedItem();
        capacitacion.setTipocapidtipocap(capTipo.getIdtipocap());
        long capplanid = 1;
        capacitacion.setPlancapidplancap(capplanid);
        
        capacitacionService.save(capacitacion);
        loadGestionCapacitacion(capplanid);
    }//GEN-LAST:event_jButton21ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // nuevo plan de capacitacion
        selectedEntity = null;
        jLabel50.setText("");
        jLabel51.setText("");
        jLabel52.setText("");
        jLabel56.setText("");
        changePanel(newPlanCapacitacionForm);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jComboBox10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox10ActionPerformed

    private void jComboBox16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox16ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox16ActionPerformed

    private void jFormattedTextField14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFormattedTextField14ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextField14ActionPerformed

    private void jFormattedTextField15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFormattedTextField15ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextField15ActionPerformed

    private void jButton33ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton33ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton33ActionPerformed

    private void jTextField27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField27ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField27ActionPerformed

    private void jButton34ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton34ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton34ActionPerformed

    private void jButton27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton27ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton27ActionPerformed

    private void jButton35ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton35ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton35ActionPerformed

    private void jComboBoxEstado3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxEstado3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxEstado3ActionPerformed

    private void jButton36ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton36ActionPerformed
        // buscar expositores
        jTable13.setRowSorter(null);
        RowFilter<Object, Object> filter = new RowFilter<Object, Object>() {
            @Override
            public boolean include(RowFilter.Entry entry) {
              if(entry.getValue(1) == null) return false;
              String fecha = dateInvert((String)entry.getValue(0));
              String estado = (String) entry.getValue(4);
              String esSearch = jComboBoxEstado3.getSelectedItem().toString();
              String fiSearch = dateInvert(jFormattedTextField16.getText());
              String ffSearch = dateInvert(jFormattedTextField17.getText());
              return (estado.equals(esSearch) || esSearch.length() == 0) &&
              (fecha.compareTo(fiSearch) >= 0 || fiSearch.length() == 0) && 
              (fecha.compareTo(ffSearch) <= 0 || ffSearch.length() == 0);
            }
        };
        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(jTable13.getModel());
        sorter.setRowFilter(filter);
        jTable13.setRowSorter(sorter);
    }//GEN-LAST:event_jButton36ActionPerformed

    private void jButton39ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton39ActionPerformed
        // eliminar expositor
        Expositor expositor = (Expositor)selectedEntity;
        expositorService.delete(expositor.getIdexpositor());
        expositorForm.setVisible(false);
        jMenuItem5ActionPerformed(evt);
    }//GEN-LAST:event_jButton39ActionPerformed

    private void jButton38ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton38ActionPerformed
        // cerrar ventana editar|crear expositor
        expositorForm.setVisible(false);
        jMenuItem5ActionPerformed(evt);
    }//GEN-LAST:event_jButton38ActionPerformed

    private void jButton37ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton37ActionPerformed
        // guardar expositor
        Expositor expositor = new Expositor();
        if(selectedEntity instanceof Expositor) {
            expositor = (Expositor)selectedEntity;
        }
        Bind.setEntity(expositor, this);
        long id = expositorService.save(expositor);
        expositor.setIdexpositor(id);
        selectedEntity = expositor;
        jLabelExpositorTitle.setText("Editar expositor");
        JOptionPane.showMessageDialog(null, "Expositor guardado correctamente.");
    }//GEN-LAST:event_jButton37ActionPerformed

    private void jComboBoxEstado4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxEstado4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxEstado4ActionPerformed

    private void jButton40ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton40ActionPerformed
        // buscar medicos
        jTable19.setRowSorter(null);
        RowFilter<Object, Object> filter = new RowFilter<Object, Object>() {
            @Override
            public boolean include(RowFilter.Entry entry) {
                if(entry.getValue(1) == null) return false;
                String fecha = dateInvert((String)entry.getValue(0));
                String estado = (String) entry.getValue(4);
                String esSearch = jComboBoxEstado4.getSelectedItem().toString();
                String fiSearch = dateInvert(jFormattedTextField18.getText());
                String ffSearch = dateInvert(jFormattedTextField19.getText());
              
                return (estado.equals(esSearch) || esSearch.length() == 0) &&
                (fecha.compareTo(fiSearch) >= 0 || fiSearch.length() == 0) && 
                (fecha.compareTo(ffSearch) <= 0 || ffSearch.length() == 0);
            }
        };
        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(jTable19.getModel());
        sorter.setRowFilter(filter);
        jTable19.setRowSorter(sorter);
    }//GEN-LAST:event_jButton40ActionPerformed

    private void jButton41ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton41ActionPerformed
        // guardar medico
        Medico medico = new Medico();
        if(selectedEntity instanceof Medico) {
            medico = (Medico)selectedEntity;
        }
        Bind.setEntity(medico, this);
        long id = medicoService.save(medico);
        medico.setIdmedico(id);
        selectedEntity = medico;
        jLabelExpositorTitle.setText("Editar médico");
        JOptionPane.showMessageDialog(null, "Médico guardado correctamente.");
    }//GEN-LAST:event_jButton41ActionPerformed

    private void jButton42ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton42ActionPerformed
        // cancelar medico form
        medicoForm.setVisible(false);
        jMenuMedListar1ActionPerformed(evt);
    }//GEN-LAST:event_jButton42ActionPerformed

    private void jButton43ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton43ActionPerformed
        // eliminar medico
        Medico medico = (Medico)selectedEntity;
        medicoService.delete(medico.getIdmedico());
        medicoForm.setVisible(false);
        jMenuMedListar1ActionPerformed(evt);
    }//GEN-LAST:event_jButton43ActionPerformed

    private void jButton45ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton45ActionPerformed
        // buscar tipo examen
        String nombre = jTextField40.getText();
        jTable21.setRowSorter(null);
        RowFilter<Object, Object> filter = new RowFilter<Object, Object>() {
            @Override
            public boolean include(Entry entry) {
              if(entry.getValue(0) == null) return false;
              String r = (String) entry.getValue(0);
              return r.toLowerCase().contains(nombre.toLowerCase()) || nombre.equals("");
            }
        };
        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(jTable21.getModel());
        sorter.setRowFilter(filter);
        jTable21.setRowSorter(sorter);
    }//GEN-LAST:event_jButton45ActionPerformed

    private void jButton46ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton46ActionPerformed
        // guardar tipo examen
        TipoExamen tipo = new TipoExamen();
        if(selectedEntity instanceof TipoExamen) {
            tipo = (TipoExamen)selectedEntity;
        }
        Bind.setEntity(tipo, this);
        long id = tipoExamenService.save(tipo);
        tipo.setIdtipoexam(id);
        selectedEntity = tipo;
        jLabelTipoExamenTitle.setText("Editar tipo de examen");
        jButton48.setEnabled(true);
        JOptionPane.showMessageDialog(null, "Tipo de examen se ha guardado correctamente.");
    }//GEN-LAST:event_jButton46ActionPerformed

    private void jButton47ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton47ActionPerformed
        // cancelar tipo examen
        tipoExamenForm.setVisible(false);
        jMenuExmListarActionPerformed(evt);
    }//GEN-LAST:event_jButton47ActionPerformed

    private void jButton48ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton48ActionPerformed
        // eliminar tipo examen
        int dialogResult = JOptionPane.showConfirmDialog (null, "¿Desea eliminar este tipo de examen?","Eliminar",JOptionPane.YES_NO_OPTION);
        if(dialogResult == JOptionPane.YES_OPTION){
            if(selectedEntity instanceof TipoExamen) {
                TipoExamen tipo = (TipoExamen)selectedEntity;
                tipoExamenService.delete(tipo.getIdtipoexam());
            }
            jButton48.setEnabled(false);
            tipoExamenForm.setVisible(false);
            jMenuExmListarActionPerformed(evt);
        }
    }//GEN-LAST:event_jButton48ActionPerformed

    private void jMenuExmListarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuExmListarActionPerformed
        // Listado de exámenes
        selectedEntity = null;
        TipoExamen[] tipos = tipoExamenService.getCollection();
        if(tipos != null ){
            DefaultTableModel model = (DefaultTableModel)jTable21.getModel();
            model.setRowCount(0);
            for(TipoExamen t: tipos){
                Object[] item = {
                    t.getDescrip_exam(),
                    TipoCapacitacionService.ESTADOS[(int)t.getEstado()],
                    t,
                };
                model.addRow(item);
            }
        }
        changePanel(tipoExamenMain);
    }//GEN-LAST:event_jMenuExmListarActionPerformed

    private void jMenuExmCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuExmCrearActionPerformed
        // Nuevo tipo examen
        selectedEntity = null;
        jTextField41.setText("");
        jComboBox18.setSelectedIndex(0);
        jButton48.setEnabled(false);
        jLabelTipoExamenTitle.setText("Crear tipo de examen");
        changePanel(tipoExamenForm);
    }//GEN-LAST:event_jMenuExmCrearActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // listar planes de salud
        changePanel(planSaludMain);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // crear plan de salud
        changePanel(planSaludForm);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        // listado de expositores
        selectedEntity = null;
        Expositor[] expositores = expositorService.getCollection();
        if(expositores != null ){
            DefaultTableModel model = (DefaultTableModel)jTable13.getModel();                    
            model.setRowCount(0);
            for(Expositor e: expositores){
                Object[] item = {
                    date.format(new Date()), //TODO: falta fecha                    
                    e.getNombreexpositor(),
                    e.getTelexpositor(),
                    e.getMailexpositor(),
                    e.getEstadoexpositor()==1?"Activo":"Desactivo",
                    e,
                };
                model.addRow(item);
            }
        }
        changePanel(expositorMain);
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        // crear expositor
        selectedEntity = null;
        jLabelExpositorTitle.setText("Crear expositor");
        Expositor expositor = new Expositor();
        expositor.setEstadoexpositor(1);
        Bind.setComponent(expositor, this);
        changePanel(expositorForm);
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuMedCrear1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuMedCrear1ActionPerformed
        // crear médico
        selectedEntity = null;
        jLabelExpositorTitle.setText("Crear médico");
        Medico medico = new Medico();
        medico.setEstadomedico(1);
        Bind.setComponent(medico, this);
        changePanel(medicoForm);
    }//GEN-LAST:event_jMenuMedCrear1ActionPerformed

    private void jMenuMedListar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuMedListar1ActionPerformed
        // listado de médicos
        selectedEntity = null;
        Medico[] medicos = medicoService.getCollection();
        if(medicos != null ){
            DefaultTableModel model = (DefaultTableModel)jTable19.getModel();                    
            model.setRowCount(0);
            for(Medico m: medicos){
                Object[] item = {
                    date.format(new Date()), //TODO: falta fecha                    
                    m.getNombremedico(),
                    m.getTelmedico(),
                    m.getMailmedico(),
                    m.getEstadomedico()==1?"Activo":"Desactivo",
                    m,
                };
                model.addRow(item);
            }
        }
        changePanel(medicoMain);
    }//GEN-LAST:event_jMenuMedListar1ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        // mostrar calendario
        Bind.setComponent(new Cliente(), this);
        int cbindex = jComboBox11.getSelectedIndex();
        Sesion_Cap[] capacitaciones = null;
        Sesion_Salud[] salud = null;
        switch(cbindex){
            case 0: // capacitaciones
                capacitaciones = sesionService.getCapacitacionCollection();
                break;
            case 1: //horas medicas                
                salud = sesionService.getSaludCollection();
                break;
            case 2: // ambas
                capacitaciones = sesionService.getCapacitacionCollection();
                salud = sesionService.getSaludCollection();
                break;
        }
        Calendar cal = Calendar.getInstance();
        if(capacitaciones != null){
            for(Sesion_Cap c: capacitaciones){
                Date d;
                try {
                    d = dateInverted.parse(c.getFechasesion());
                    cal.setTime(d);                    
                    jCalendar1.setDate(d);
                    JPanel jpanel = jCalendar1.getDayChooser().getDayPanel();
                    Component compo[] = jpanel.getComponents();
                    for (Component comp : compo) {
                        if (!(comp instanceof JButton))
                            continue;

                        JButton btn = (JButton) comp;
                        if (btn.getText().equals(String.valueOf(cal.get(Calendar.DAY_OF_MONTH))))
                            btn.setBackground(Color.red);
                    }
                } catch (ParseException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        if(salud != null){
            for(Sesion_Salud s: salud){
                
            }
        }
        
        changePanel(calendarMain);
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jTextField29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField29ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField29ActionPerformed

    private void jFormattedTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFormattedTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextField3ActionPerformed

    private void closePlanCapacitacionForm(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closePlanCapacitacionForm
        // cerrar ventana de plan de capacitacion
        planCapacitacionForm.setVisible(false);
        jMenuItem1ActionPerformed(evt);
    }//GEN-LAST:event_closePlanCapacitacionForm

    private void jButton22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton22ActionPerformed
        // crear una nueva sesion de capacitacion
        Sesion_Cap sesion = new Sesion_Cap();
        Bind.setEntity(sesion, this);
        sesion.setEstadosesioncap(1L);
        sesionService.saveCapacitacion(sesion);
        Object[] item = {
           jComboBox7.getSelectedItem(),
           sesion.getCupossesionString(),
           sesion.getNombresesion(),
           jComboBox8.getSelectedItem(),
           sesion.getFechasesion(),
           sesion.getHorainiciocap(),
           sesion.getHoraterminocap(),
           sesion.getDescripcionsesion(),
        };
        DefaultTableModel model = (DefaultTableModel)jTable9.getModel();
        model.addRow(item);
        JOptionPane.showMessageDialog(null, "Se ha agregado una nueva sesión correctamente.");
    }//GEN-LAST:event_jButton22ActionPerformed

    private void jButton23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton23ActionPerformed
        // agreagr trabajador a la lista de participantes
        
        String rut = jTextField23.getText();
        Usuario trabajador = usuarioService.getOne(rut);
        
        if(trabajador == null || trabajador.getPerfilidperfil() != 4) {
            JOptionPane.showMessageDialog(null, "No se ha encontado al trabajador.");
            return;
        }
        int idx = jComboBox19.getSelectedIndex();
        long capid = (long)this.mapCapcitacion.keySet().toArray()[idx];
        
        Sesion_Cap[] sesiones = sesionService.getCapacitacionCollection();
        DefaultTableModel model = (DefaultTableModel)jTable10.getModel();
        for(Sesion_Cap s: sesiones) {
            if( s.getCapacitacionidcap() != capid) continue;
            List_Trab_Cap list = new List_Trab_Cap();
            list.setUsuarioidusuario(trabajador.getIdusuario());
            
            //crear lista de asistencia para el trabajador en las sesiones
            List_Asis_Cap asis = new List_Asis_Cap();
            asis.setSesioncapidsesioncap(s.getIdsesioncap());
            long id_asis = asistenciaTrabajadorService.saveListCap(asis);  //TODO falla al insertar el WS
            
            //asignar certifiacdo al trabajador
            Certificado cert = new Certificado();
            long id_cert = certificadoService.save(cert);  //Crear un certificado en estado 0 sin codigo
            list.setLisasiscapidlistacap(id_asis);
            list.setCertificadoidcertificado(id_cert); //TODO debe ser nulo ya que el certificado se genera despues
            listTrabajadorService.saveListCap(list);
            Object[] item = {
                date.format(new Date()),
                this.mapCapcitacion.get(capid).getNombrecapacitacion(),
                trabajador.getRunusuario(),
                trabajador.getNombresusuario(),
                trabajador.getAppaterno()+" "+trabajador.getApmaterno(),
                trabajador.getTelusuario(),
                trabajador.getMailusuario(),
                trabajador,
            };
            model.addRow(item);
            JOptionPane.showMessageDialog(null, "Trabajador agregado correctamente.");
        }
    }//GEN-LAST:event_jButton23ActionPerformed

    private void jLabel110MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel110MouseClicked
        // TODO add your handling code here:
        jFormattedTextField16.cut();
    }//GEN-LAST:event_jLabel110MouseClicked

    private void jButton44ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton44ActionPerformed
        // buscar cliente para calendario
        String rut = jTextField39.getText();
        Cliente cliente = clienteService.getOneByRut(rut);
        Bind.setComponent(cliente, this);
    }//GEN-LAST:event_jButton44ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JInternalFrame calendarMain;
    private javax.swing.JInternalFrame clienteForm;
    private javax.swing.JInternalFrame clienteMain;
    private javax.swing.JInternalFrame evaluacionForm;
    private javax.swing.JInternalFrame evaluacionMain;
    private javax.swing.JInternalFrame expositorForm;
    private javax.swing.JInternalFrame expositorMain;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton22;
    private javax.swing.JButton jButton23;
    private javax.swing.JButton jButton24;
    private javax.swing.JButton jButton25;
    private javax.swing.JButton jButton26;
    private javax.swing.JButton jButton27;
    private javax.swing.JButton jButton28;
    private javax.swing.JButton jButton29;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton30;
    private javax.swing.JButton jButton31;
    private javax.swing.JButton jButton32;
    private javax.swing.JButton jButton33;
    private javax.swing.JButton jButton34;
    private javax.swing.JButton jButton35;
    private javax.swing.JButton jButton36;
    private javax.swing.JButton jButton37;
    private javax.swing.JButton jButton38;
    private javax.swing.JButton jButton39;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton40;
    private javax.swing.JButton jButton41;
    private javax.swing.JButton jButton42;
    private javax.swing.JButton jButton43;
    private javax.swing.JButton jButton44;
    private javax.swing.JButton jButton45;
    private javax.swing.JButton jButton46;
    private javax.swing.JButton jButton47;
    private javax.swing.JButton jButton48;
    private javax.swing.JButton jButton49;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton50;
    private javax.swing.JButton jButton51;
    private javax.swing.JButton jButton52;
    private javax.swing.JButton jButton54;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private com.toedter.calendar.JCalendar jCalendar1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox10;
    private javax.swing.JComboBox<String> jComboBox11;
    private javax.swing.JComboBox<String> jComboBox12;
    private javax.swing.JComboBox<String> jComboBox13;
    private javax.swing.JComboBox<String> jComboBox14;
    private javax.swing.JComboBox<String> jComboBox15;
    private javax.swing.JComboBox<String> jComboBox16;
    private javax.swing.JComboBox<String> jComboBox17;
    private javax.swing.JComboBox<String> jComboBox18;
    private javax.swing.JComboBox<String> jComboBox19;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JComboBox<String> jComboBox6;
    private javax.swing.JComboBox<String> jComboBox7;
    private javax.swing.JComboBox<String> jComboBox8;
    private javax.swing.JComboBox<String> jComboBox9;
    private javax.swing.JComboBox<String> jComboBoxEstado1;
    private javax.swing.JComboBox<String> jComboBoxEstado2;
    private javax.swing.JComboBox<String> jComboBoxEstado3;
    private javax.swing.JComboBox<String> jComboBoxEstado4;
    private javax.swing.JComboBox<String> jComboBoxTipo1;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JFormattedTextField jFormattedTextField10;
    private javax.swing.JFormattedTextField jFormattedTextField11;
    private javax.swing.JFormattedTextField jFormattedTextField12;
    private javax.swing.JFormattedTextField jFormattedTextField13;
    private javax.swing.JFormattedTextField jFormattedTextField14;
    private javax.swing.JFormattedTextField jFormattedTextField15;
    private javax.swing.JFormattedTextField jFormattedTextField16;
    private javax.swing.JFormattedTextField jFormattedTextField17;
    private javax.swing.JFormattedTextField jFormattedTextField18;
    private javax.swing.JFormattedTextField jFormattedTextField19;
    private javax.swing.JFormattedTextField jFormattedTextField2;
    private javax.swing.JFormattedTextField jFormattedTextField3;
    private javax.swing.JFormattedTextField jFormattedTextField4;
    private javax.swing.JFormattedTextField jFormattedTextField5;
    private javax.swing.JFormattedTextField jFormattedTextField6;
    private javax.swing.JFormattedTextField jFormattedTextField7;
    private javax.swing.JFormattedTextField jFormattedTextField8;
    private javax.swing.JFormattedTextField jFormattedTextField9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel102;
    private javax.swing.JLabel jLabel103;
    private javax.swing.JLabel jLabel104;
    private javax.swing.JLabel jLabel105;
    private javax.swing.JLabel jLabel106;
    private javax.swing.JLabel jLabel107;
    private javax.swing.JLabel jLabel108;
    private javax.swing.JLabel jLabel109;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel110;
    private javax.swing.JLabel jLabel111;
    private javax.swing.JLabel jLabel112;
    private javax.swing.JLabel jLabel113;
    private javax.swing.JLabel jLabel114;
    private javax.swing.JLabel jLabel115;
    private javax.swing.JLabel jLabel116;
    private javax.swing.JLabel jLabel117;
    private javax.swing.JLabel jLabel118;
    private javax.swing.JLabel jLabel119;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel120;
    private javax.swing.JLabel jLabel121;
    private javax.swing.JLabel jLabel122;
    private javax.swing.JLabel jLabel123;
    private javax.swing.JLabel jLabel124;
    private javax.swing.JLabel jLabel125;
    private javax.swing.JLabel jLabel126;
    private javax.swing.JLabel jLabel127;
    private javax.swing.JLabel jLabel128;
    private javax.swing.JLabel jLabel129;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel130;
    private javax.swing.JLabel jLabel131;
    private javax.swing.JLabel jLabel132;
    private javax.swing.JLabel jLabel133;
    private javax.swing.JLabel jLabel134;
    private javax.swing.JLabel jLabel135;
    private javax.swing.JLabel jLabel136;
    private javax.swing.JLabel jLabel137;
    private javax.swing.JLabel jLabel138;
    private javax.swing.JLabel jLabel139;
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
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JLabel jLabel99;
    private javax.swing.JLabel jLabelClienteTitle;
    private javax.swing.JLabel jLabelDireccionVisita;
    private javax.swing.JLabel jLabelExpositorTitle;
    private javax.swing.JLabel jLabelMedicoTitle;
    private javax.swing.JLabel jLabelProfile;
    private javax.swing.JLabel jLabelRut;
    private javax.swing.JLabel jLabelTipoCapacitacionTitle;
    private javax.swing.JLabel jLabelTipoExamenTitle;
    private javax.swing.JLabel jLabelTipoVisita;
    private javax.swing.JLabel jLabelUsuarioTitle;
    private javax.swing.JMenuBar jMenuAdmin;
    private javax.swing.JMenu jMenuCal;
    private javax.swing.JMenuItem jMenuEmpCrear;
    private javax.swing.JMenuItem jMenuEmpListar;
    private javax.swing.JMenu jMenuEmpresa;
    private javax.swing.JMenuBar jMenuEngineer;
    private javax.swing.JMenu jMenuExamen;
    private javax.swing.JMenuItem jMenuExmCrear;
    private javax.swing.JMenuItem jMenuExmListar;
    private javax.swing.JMenu jMenuExpositor;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuMedCrear1;
    private javax.swing.JMenuItem jMenuMedListar1;
    private javax.swing.JMenu jMenuMedico;
    private javax.swing.JMenu jMenuPlanCap;
    private javax.swing.JMenu jMenuPlanSalud;
    private javax.swing.JMenuBar jMenuSupervisor;
    private javax.swing.JMenuItem jMenuTerCrear;
    private javax.swing.JMenuItem jMenuTerEngListar;
    private javax.swing.JMenuItem jMenuTerListar;
    private javax.swing.JMenu jMenuTerreno;
    private javax.swing.JMenu jMenuTerrenoEng;
    private javax.swing.JMenuItem jMenuTipoCapCrear;
    private javax.swing.JMenuItem jMenuTipoCapListar;
    private javax.swing.JMenu jMenuTipoCapacitacion;
    private javax.swing.JMenuItem jMenuUsrCrear;
    private javax.swing.JMenuItem jMenuUsrListar;
    private javax.swing.JMenu jMenuUsuario;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanelWelcome;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JScrollPane jScrollPane19;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane20;
    private javax.swing.JScrollPane jScrollPane21;
    private javax.swing.JScrollPane jScrollPane22;
    private javax.swing.JScrollPane jScrollPane23;
    private javax.swing.JScrollPane jScrollPane24;
    private javax.swing.JScrollPane jScrollPane25;
    private javax.swing.JScrollPane jScrollPane26;
    private javax.swing.JScrollPane jScrollPane27;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable10;
    private javax.swing.JTable jTable11;
    private javax.swing.JTable jTable12;
    private javax.swing.JTable jTable13;
    private javax.swing.JTable jTable14;
    private javax.swing.JTable jTable15;
    private javax.swing.JTable jTable16;
    private javax.swing.JTable jTable17;
    private javax.swing.JTable jTable18;
    private javax.swing.JTable jTable19;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable20;
    private javax.swing.JTable jTable21;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTable jTable5;
    private javax.swing.JTable jTable6;
    private javax.swing.JTable jTable7;
    private javax.swing.JTable jTable8;
    private javax.swing.JTable jTable9;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextArea jTextArea3;
    private javax.swing.JTextArea jTextArea4;
    private javax.swing.JTextArea jTextAreaDescVisita;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField17;
    private javax.swing.JTextField jTextField18;
    private javax.swing.JTextField jTextField19;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField20;
    private javax.swing.JTextField jTextField21;
    private javax.swing.JTextField jTextField22;
    private javax.swing.JTextField jTextField23;
    private javax.swing.JTextField jTextField24;
    private javax.swing.JTextField jTextField25;
    private javax.swing.JTextField jTextField26;
    private javax.swing.JTextField jTextField27;
    private javax.swing.JTextField jTextField28;
    private javax.swing.JTextField jTextField29;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField30;
    private javax.swing.JTextField jTextField31;
    private javax.swing.JTextField jTextField32;
    private javax.swing.JTextField jTextField33;
    private javax.swing.JTextField jTextField34;
    private javax.swing.JTextField jTextField35;
    private javax.swing.JTextField jTextField36;
    private javax.swing.JTextField jTextField37;
    private javax.swing.JTextField jTextField38;
    private javax.swing.JTextField jTextField39;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField40;
    private javax.swing.JTextField jTextField41;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JInternalFrame medicoForm;
    private javax.swing.JInternalFrame medicoMain;
    private javax.swing.JInternalFrame newPlanCapacitacionForm;
    private javax.swing.JInternalFrame newPlanSaludForm;
    private javax.swing.JInternalFrame planCapacitacionForm;
    private javax.swing.JInternalFrame planCapacitacionMain;
    private javax.swing.JInternalFrame planSaludForm;
    private javax.swing.JInternalFrame planSaludMain;
    private javax.swing.JInternalFrame tipoCapacitacionForm;
    private javax.swing.JInternalFrame tipoCapacitacionMain;
    private javax.swing.JInternalFrame tipoExamenForm;
    private javax.swing.JInternalFrame tipoExamenMain;
    private javax.swing.JInternalFrame usuarioForm;
    private javax.swing.JInternalFrame usuarioMain;
    // End of variables declaration//GEN-END:variables

}
