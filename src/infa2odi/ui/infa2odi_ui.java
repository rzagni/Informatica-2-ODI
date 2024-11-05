package infa2odi.ui;

/**
 * <p>
 * Licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License.
 * You may use, modify, and share this code for non-commercial purposes, provided you give appropriate
 * credit, indicate if changes were made, and distribute any modified work under the same license.
 * </p>
 * 
 * <p>
 * License: <a href="http://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Renzo Zagni
 * @license Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License
 * @see <a href="http://creativecommons.org/licenses/by-nc-sa/4.0/">Creative Commons License</a>
 */

import java.awt.Choice;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import oracle.odi.publicapi.samples.SimpleOdiInstanceHandle;

/**
 * A user interface class for interacting with ODI and Infa repositories.
 */
public class infa2odi_ui extends JFrame {
    private JPanel contentPane;
    private JTextField master_user;
    private JTextField login_user;
    private JPasswordField master_pwd;
    private JFileChooser fileChooser;
    private JLabel lblNewLabel;
    private JLabel lblPassword;
    private JLabel lblRepository;
    private JLabel lblWorkRepository;
    private JLabel lblSourceSchema;
    private JLabel lblTargetSchema;
    private JLabel information;
    private JLabel information2;
    private JLabel information3;
    private JPasswordField login_pwd;
    private JButton btnGoAhead;
    private JButton btnLogin;
    private JButton btnBrowse;
    private JButton btnClose;
    private File file;
    public static Choice cmbSourceLogicalSchema;
    public static Choice cmbTargetLogicalSchema;
    public static SimpleOdiInstanceHandle odiInstanceHandle;

    /**
     * Launches the application.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    infa2odi_ui frame = new infa2odi_ui();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Creates the frame for the application.
     */
    public infa2odi_ui() {
        setAlwaysOnTop(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 94, 650, 338);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        initializeComponents();
        addActionListeners();
    }

    /**
     * Initializes and sets up the UI components.
     */
    private void initializeComponents() {
        information = new JLabel("First, kindly complete the following information:");
        information.setBounds(10, 20, 600, 14);
        contentPane.add(information);

        information2 = new JLabel("Also please select the file you'd like to parse");
        information2.setBounds(10, 130, 600, 14);
        information2.setVisible(false);
        contentPane.add(information2);

        information3 = new JLabel("You've selected : /home/oracle/workspace/WC_SIL_ExchangeRateGeneral_OKS.XML ");
        information3.setBounds(10, 180, 650, 14);
        information3.setVisible(false);
        contentPane.add(information3);

        // Initialize Labels, TextFields, and Buttons
        lblNewLabel = new JLabel("Master Repository Username:");
        lblNewLabel.setBounds(10, 54, 185, 14);
        contentPane.add(lblNewLabel);

        master_user = new JTextField();
        master_user.setBounds(201, 51, 200, 20);
        master_user.setText("prod_odi_repo");
        contentPane.add(master_user);

        lblRepository = new JLabel("Master Repository Password:");
        lblRepository.setBounds(10, 89, 185, 14);
        contentPane.add(lblRepository);

        master_pwd = new JPasswordField();
        master_pwd.setBounds(201, 86, 200, 20);
        master_pwd.setText("oracle");
        contentPane.add(master_pwd);

        lblPassword = new JLabel("Login Username: ");
        lblPassword.setBounds(65, 122, 114, 14);
        contentPane.add(lblPassword);

        login_user = new JTextField();
        login_user.setBounds(201, 119, 200, 20);
        login_user.setText("SUPERVISOR");
        contentPane.add(login_user);

        lblWorkRepository = new JLabel("Login Password:");
        lblWorkRepository.setBounds(65, 147, 110, 14);
        contentPane.add(lblWorkRepository);

        login_pwd = new JPasswordField();
        login_pwd.setBounds(201, 147, 200, 20);
        login_pwd.setText("SUPERVISOR");
        contentPane.add(login_pwd);

        fileChooser = new JFileChooser();

        lblSourceSchema = new JLabel("Source Schema: ");
        lblSourceSchema.setBounds(10, 54, 105, 14);
        contentPane.add(lblSourceSchema);
        lblSourceSchema.setVisible(false);

        cmbSourceLogicalSchema = new Choice();
        cmbSourceLogicalSchema.setBounds(130, 51, 150, 20);
        contentPane.add(cmbSourceLogicalSchema);
        cmbSourceLogicalSchema.setVisible(false);

        lblTargetSchema = new JLabel("Target Schema:");
        lblTargetSchema.setBounds(10, 89, 99, 14);
        contentPane.add(lblTargetSchema);
        lblTargetSchema.setVisible(false);

        cmbTargetLogicalSchema = new Choice();
        cmbTargetLogicalSchema.setBounds(130, 86, 150, 20);
        contentPane.add(cmbTargetLogicalSchema);
        cmbTargetLogicalSchema.setVisible(false);

        btnClose = new JButton("Close");
        btnClose.setBounds(270, 110, 89, 23);
        btnClose.setVisible(false);
        contentPane.add(btnClose);

        btnBrowse = new JButton("Browse..");
        btnBrowse.setBounds(10, 150, 89, 23);
        btnBrowse.setVisible(false);
        contentPane.add(btnBrowse);

        btnGoAhead = new JButton("Parse File");
        btnGoAhead.setBounds(201, 250, 130, 23);
        btnGoAhead.setVisible(false);
        contentPane.add(btnGoAhead);

        btnLogin = new JButton("Login");
        btnLogin.setBounds(300, 210, 89, 23);
        contentPane.add(btnLogin);
    }

    /**
     * Adds action listeners for UI components to handle events.
     */
    private void addActionListeners() {
        btnClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        btnBrowse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fileChooser.showOpenDialog(infa2odi_ui.this);
                file = fileChooser.getSelectedFile();
                information3.setText("You've selected : " + file.getName() + ".");
                information3.setVisible(true);
            }
        });

        btnGoAhead.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (file != null && file.exists()) {
                    infa2odi.infa_import.parsingFiles(odiInstanceHandle, cmbSourceLogicalSchema.getSelectedItem(), cmbTargetLogicalSchema.getSelectedItem(), file.getPath());
                    information.setText("Your file has been parsed successfully.");
                    information2.setText("Thanks for using ODI-INFA wizard.");
                    information2.setBounds(10, 54, 500, 14);

                    lblSourceSchema.setVisible(false);
                    cmbSourceLogicalSchema.setVisible(false);
                    lblTargetSchema.setVisible(false);
                    cmbTargetLogicalSchema.setVisible(false);
                    btnGoAhead.setVisible(false);
                    btnBrowse.setVisible(false);
                    btnClose.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a valid XML file");
                }
            }
        });

        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String login_user_txt = login_user.getText();
                String login_pwd_txt = new String(login_pwd.getPassword());
                String master_user_txt = master_user.getText();
                String master_pwd_txt = new String(master_pwd.getPassword());

                odiInstanceHandle = infa2odi.infa_import.login(login_user_txt, login_pwd_txt, master_user_txt, master_pwd_txt);
                if (odiInstanceHandle == null) return;

                login_user.setVisible(false);
                login_pwd.setVisible(false);
                master_user.setVisible(false);
                master_pwd.setVisible(false);

                lblSourceSchema.setVisible(true);
                cmbSourceLogicalSchema.setVisible(true);
                lblTargetSchema.setVisible(true);
                cmbTargetLogicalSchema.setVisible(true);
                btnGoAhead.setVisible(true);
                btnBrowse.setVisible(true);
                information2.setVisible(true);

                information.setText("Now, please select in which logical schema you'd like to introduce your mapping");
            }
        });
    }
}
