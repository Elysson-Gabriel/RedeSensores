/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.ifceppd.redesensores.mom;

import com.ifceppd.redesensores.models.Sensor;
import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.JFrame;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.advisory.DestinationSource;
import org.apache.activemq.command.ActiveMQTopic;

/**
 *
 * @author elysson
 */
public class SensorPublisher extends javax.swing.JFrame {
    
    private String unidade = null;
    private String nome = null;
    private String param = null;
    private Publisher publisher = null;
    private int min, max;
    private Sensor atual;
    
    private static final String url = ActiveMQConnection.DEFAULT_BROKER_URL;
    private ConnectionFactory connectionFactory = null;
    private ActiveMQConnection connection = null;
    
    /**
     * Creates new form Cliente
     */
    public SensorPublisher() {
        initComponents();
    }
    
    public SensorPublisher(Sensor s) throws JMSException {
        /*
         * Estabelecendo conexão com o Servidor JMS
        */		
        this.connectionFactory = new ActiveMQConnectionFactory(url);
        this.connection = (ActiveMQConnection) connectionFactory.createConnection();
        this.connection.start();
        this.atual = s;
        
        initComponents();
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.nome = s.getNome();
        this.min = s.getLimiteMin();
        this.max = s.getLimiteMax();
        this.param = s.getParamMonitor();
        
        DefaultBoundedRangeModel sm = new DefaultBoundedRangeModel();
        
        switch (param) {
            case "Temperatura":
                this.jLabelImg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgTemperatura.jpg")));
                unidade = "°C";
                sm.setMinimum(-90);
                sm.setMaximum(60);
                break;
            case "Umidade":
                this.jLabelImg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgUmidade.png")));
                unidade = "%";
                sm.setMinimum(0);
                sm.setMaximum(100);
                break;
            case "Velocidade":
                this.jLabelImg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgVelocidade.png")));
                unidade = "km/h";
                sm.setMinimum(0);
                sm.setMaximum(250);
                break;
            default:
                break;
        }
        
        this.jLabelTitulo.setText(nome);
        this.jTextFieldMin.setText(min + " " + unidade);
        this.jTextFieldMax.setText(max + " " + unidade);
        sm.setValue((min + max)/2);
        this.jSliderLeitura.setModel(sm);
        this.jTextFieldLeitura.setText(jSliderLeitura.getValue() + " " + unidade);
        this.jTextFieldLeitura.setForeground(Color.GREEN);
                
        try {
            publisher = new Publisher(s.getNome() + "(" + s.getParamMonitor() + ")");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private class Publisher {
        /*
         * URL do servidor JMS. DEFAULT_BROKER_URL indica que o servidor está em localhost
        */	

        private static final String url = ActiveMQConnection.DEFAULT_BROKER_URL;

        private Session session;
        private MessageProducer publisher;
        private Connection connection;

        public Publisher(String topicName) throws Exception{
            /*
             * Estabelecendo conexão com o Servidor JMS
            */		
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
            Connection connection = connectionFactory.createConnection();
            connection.start();

            /*
             * Criando Session 
             */		
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            
            /*
             * Criando Topic
            */     
            Destination dest = session.createTopic(topicName);
            
            /*
            * Criando Produtor
            */
            MessageProducer publisher = session.createProducer(dest);

            this.connection = connection;
            this.session = session;
            this.publisher = publisher;

            connection.start();
        }

        public void writeMessage(String text)throws JMSException{
            TextMessage message = session.createTextMessage();
            message.setText(text);

            /*
             * Publicando Mensagem
             */
            publisher.send(message);
        }

        public void close() throws JMSException{
            publisher.close();
            session.close();
            connection.close();
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

        jPanelPrincipal = new javax.swing.JPanel();
        jLabelTitulo = new javax.swing.JLabel();
        jLabelImg = new javax.swing.JLabel();
        jSliderLeitura = new javax.swing.JSlider();
        jTextFieldLeitura = new javax.swing.JTextField();
        jLabelMin = new javax.swing.JLabel();
        jTextFieldMin = new javax.swing.JTextField();
        jLabelMax = new javax.swing.JLabel();
        jTextFieldMax = new javax.swing.JTextField();
        jLabelMin1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(null);

        jPanelPrincipal.setBackground(new java.awt.Color(255, 255, 255));

        jLabelTitulo.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabelTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTitulo.setText("Nome do sensor");

        jLabelImg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgVelocidade.png"))); // NOI18N

        jSliderLeitura.setMajorTickSpacing(1);
        jSliderLeitura.setMaximum(250);
        jSliderLeitura.setMinimum(-90);
        jSliderLeitura.setSnapToTicks(true);
        jSliderLeitura.setValue(15);
        jSliderLeitura.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSliderLeituraStateChanged(evt);
            }
        });

        jTextFieldLeitura.setEditable(false);
        jTextFieldLeitura.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextFieldLeitura.setBorder(null);

        jLabelMin.setText("Mín:");

        jTextFieldMin.setEditable(false);
        jTextFieldMin.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextFieldMin.setBorder(null);
        jTextFieldMin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldMinActionPerformed(evt);
            }
        });

        jLabelMax.setText("Max:");

        jTextFieldMax.setEditable(false);
        jTextFieldMax.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextFieldMax.setBorder(null);

        jLabelMin1.setText("Leitura atual:");

        javax.swing.GroupLayout jPanelPrincipalLayout = new javax.swing.GroupLayout(jPanelPrincipal);
        jPanelPrincipal.setLayout(jPanelPrincipalLayout);
        jPanelPrincipalLayout.setHorizontalGroup(
            jPanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPrincipalLayout.createSequentialGroup()
                .addGroup(jPanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelPrincipalLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabelTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSliderLeitura, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanelPrincipalLayout.createSequentialGroup()
                        .addGap(114, 114, 114)
                        .addComponent(jLabelMin1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldLeitura, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jLabelImg)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelMax, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelPrincipalLayout.createSequentialGroup()
                        .addComponent(jLabelMin)
                        .addGap(2, 2, 2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextFieldMin, javax.swing.GroupLayout.DEFAULT_SIZE, 61, Short.MAX_VALUE)
                    .addComponent(jTextFieldMax))
                .addContainerGap())
        );
        jPanelPrincipalLayout.setVerticalGroup(
            jPanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelImg)
                    .addGroup(jPanelPrincipalLayout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addGroup(jPanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelMin)
                            .addComponent(jTextFieldMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelMax)
                            .addComponent(jTextFieldMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanelPrincipalLayout.createSequentialGroup()
                        .addComponent(jLabelTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)
                        .addComponent(jSliderLeitura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldLeitura, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelMin1))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanelPrincipal);
        jPanelPrincipal.setBounds(0, 0, 610, 140);

        setBounds(0, 0, 629, 178);
    }// </editor-fold>//GEN-END:initComponents

    private void jSliderLeituraStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSliderLeituraStateChanged
        // TODO add your handling code here:
        int leitura = this.jSliderLeitura.getValue();
        
        if(leitura > max || leitura < min){
            this.jTextFieldLeitura.setForeground(Color.red);
            try {
                String msg =  nome + "(" + param +"): " 
                        + String.valueOf(leitura) + " " + unidade;
                this.publisher.writeMessage(msg);
            } catch (JMSException ex) {
                Logger.getLogger(SensorPublisher.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            this.jTextFieldLeitura.setForeground(Color.green);
        }
        
        jTextFieldLeitura.setText(jSliderLeitura.getValue() + " " + unidade);
    }//GEN-LAST:event_jSliderLeituraStateChanged

    private void jTextFieldMinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldMinActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldMinActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        try {
            // TODO add your handling code here:
            String topicName = atual.getNome() + "(" + atual.getParamMonitor() + ")";
            this.connection.destroyDestination(new ActiveMQTopic(topicName));
        } catch (JMSException ex) {
            Logger.getLogger(SensorPublisher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formWindowClosing

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SensorPublisher.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SensorPublisher.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SensorPublisher.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SensorPublisher.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SensorPublisher().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabelImg;
    private javax.swing.JLabel jLabelMax;
    private javax.swing.JLabel jLabelMin;
    private javax.swing.JLabel jLabelMin1;
    private javax.swing.JLabel jLabelTitulo;
    private javax.swing.JPanel jPanelPrincipal;
    private javax.swing.JSlider jSliderLeitura;
    private javax.swing.JTextField jTextFieldLeitura;
    private javax.swing.JTextField jTextFieldMax;
    private javax.swing.JTextField jTextFieldMin;
    // End of variables declaration//GEN-END:variables
}
