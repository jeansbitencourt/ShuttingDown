/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shuttingdown;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jeans
 */
public class JanelaPrincipal extends javax.swing.JFrame {

    private boolean flag;
    private Integer time;
    private Integer count;
    private String operatingSystem;

    /**
     * Creates new form JanelaPrincipal
     */
    public JanelaPrincipal() {
        initComponents();
        ShuttingDownThread thread = new ShuttingDownThread();
        thread.start();
        operatingSystem = System.getProperty("os.name");
        flag = false;
        prog.setStringPainted(true);
        prog.setString("Aguardando...");
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        URL url = this.getClass().getResource("/images/icon.png");
        Image iconeTitulo = Toolkit.getDefaultToolkit().getImage(url);
        this.setIconImage(iconeTitulo);
        this.setTitle("ShuttingDown Timer");
        String[] options = null;
        System.out.println(operatingSystem);
        if("Linux".equals(operatingSystem.substring(0, 5)) || "Mac OS X".equals(operatingSystem.substring(0, 8))){
            options = new String[] { "minutos", "horas" };
        }else{
            options = new String[] { "minutos", "horas", "segundos" };
        }
        cbTipo.setModel(new javax.swing.DefaultComboBoxModel<>(options));
    }

    public void exec(Integer time) throws IOException {
        String shutdownCommand;
        System.out.println(System.getProperty("os.name"));
        if ("Linux".equals(operatingSystem.substring(0, 5)) || "Mac OS X".equals(operatingSystem.substring(0, 8))) {
            shutdownCommand = "shutdown -h " + (time / 60);
            Runtime.getRuntime().exec(new String[]{ "/bin/sh", "-c", shutdownCommand });
        } else if ("Windows".equals(operatingSystem.substring(0, 7))) {
            shutdownCommand = "shutdown -s -t " + time;
            Runtime.getRuntime().exec(shutdownCommand);
        } else {
            throw new RuntimeException("Unsupported operating system.");
        }
        System.out.println(shutdownCommand);
    }

    public void stopExec() throws IOException {
        String shutdownCancelCommand = "-";
        System.out.println(System.getProperty("os.name"));
        if ("Linux".equals(operatingSystem.substring(0, 5)) || "Mac OS X".equals(operatingSystem.substring(0, 8))) {
            shutdownCancelCommand = "shutdown -c";
            Runtime.getRuntime().exec(new String[]{ "/bin/sh", "-c", shutdownCancelCommand });         
        } else if ("Windows".equals(operatingSystem.substring(0, 7))) {
            shutdownCancelCommand = "shutdown -a";
            Runtime.getRuntime().exec(shutdownCancelCommand);
        } else {
            throw new RuntimeException("Unsupported operating system.");
        }
        System.out.println(shutdownCancelCommand);     
    }

    public void resetTime() {
        switch (cbTipo.getSelectedIndex()) {
            case 0:
                time = (Integer) tempo.getValue() * 60;
                break;
            case 1:
                time = (((Integer) tempo.getValue()) * 60) * 60;
                break;
            case 2:
                time = (Integer) tempo.getValue();
                break;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.SECOND, time);
        SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        lbInfo.setText("Horário definido para desligamento do computador: " + s.format(calendar.getTime()));

        count = 0;
        prog.setMinimum(0);
        prog.setMaximum(time);
        prog.setString("Aguardando...");
        prog.setValue(0);

        setTitle("ShuttingDown Timer");
    }

    private void atualizarTextos() {
        lbTitulo.setText("Defina o tempo em " + cbTipo.getSelectedItem().toString() + " para desligar seu computador:");
    }

    public class ShuttingDownThread extends Thread {

        @Override
        public void run() {
            super.run();
            SimpleDateFormat s;
            Calendar calendar = Calendar.getInstance();
            while (true) {
                if (flag) {

                    calendar.setTime(new Date());
                    s = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    setTitle("ShuttingDown Timer - Data/Hora atual: " + s.format(calendar.getTime()));

                    prog.setString("O seu computador será desligado em " + time / 60 + " minutos (" + time + " segundos)!");
                    prog.setValue(count);
                    time--;
                    count++;
                    if (time == 0) {
                        System.exit(0);
                    }
                } else {
                    lbInfo.setText("");
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(JanelaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
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

        btStart = new javax.swing.JButton();
        btStop = new javax.swing.JButton();
        prog = new javax.swing.JProgressBar();
        tempo = new javax.swing.JSpinner();
        lbTitulo = new javax.swing.JLabel();
        cbTipo = new javax.swing.JComboBox<>();
        lbInfo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        btStart.setText("Iniciar Contagem");
        btStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btStartActionPerformed(evt);
            }
        });

        btStop.setText("Cancelar");
        btStop.setEnabled(false);
        btStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btStopActionPerformed(evt);
            }
        });

        tempo.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        tempo.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        lbTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTitulo.setText("Defina o tempo em minutos para desligar seu computador:");

        cbTipo.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        cbTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "minutos", "horas", "segundos" }));
        cbTipo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbTipoItemStateChanged(evt);
            }
        });

        lbInfo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbInfo.setText("Horário definido para delisgar o computador:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(145, 145, 145)
                .addComponent(btStart, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btStop, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(147, 147, 147))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(prog, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(220, Short.MAX_VALUE)
                .addComponent(tempo, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(220, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(lbTitulo)
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tempo, javax.swing.GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE)
                    .addComponent(cbTipo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(prog, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbInfo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btStop, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btStart, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btStartActionPerformed
        btStop.setEnabled(true);
        btStart.setEnabled(false);
        flag = true;
        resetTime();
        try {
            exec(time);
        } catch (IOException ex) {
            Logger.getLogger(JanelaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btStartActionPerformed

    private void btStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btStopActionPerformed
        btStop.setEnabled(false);
        btStart.setEnabled(true);
        flag = false;
        resetTime();
        try {
            stopExec();
        } catch (IOException ex) {
            Logger.getLogger(JanelaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btStopActionPerformed

    private void cbTipoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbTipoItemStateChanged
        atualizarTextos();
    }//GEN-LAST:event_cbTipoItemStateChanged

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        try {
            stopExec();
        } catch (IOException ex) {
            Logger.getLogger(JanelaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formWindowClosing


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btStart;
    private javax.swing.JButton btStop;
    private javax.swing.JComboBox<String> cbTipo;
    private javax.swing.JLabel lbInfo;
    private javax.swing.JLabel lbTitulo;
    private javax.swing.JProgressBar prog;
    private javax.swing.JSpinner tempo;
    // End of variables declaration//GEN-END:variables
}
