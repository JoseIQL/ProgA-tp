package GUI;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import BLL.Usuario;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JPasswordField;

public class PantallaDividida extends JFrame {

	private JPanel contentPane;
	private JTextField loginEmailField;
	private JPasswordField loginPasswordField;
	private JTextField registerUsernameField;
	private JTextField registerEmailField;
	private JPasswordField registerPasswordField;
	private JButton btnLogin;
	private JButton btnRegister;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				DLL.Conexion.getInstance();
				PantallaDividida frame = new PantallaDividida();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PantallaDividida() {
		setTitle("Jumbox - Acceso al Sistema");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 450);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new java.awt.BorderLayout(0, 0));
		setContentPane(contentPane);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, java.awt.BorderLayout.CENTER);

		JPanel panelLogin = new JPanel(null);
		tabbedPane.addTab("Login", null, panelLogin, null);

		JLabel lblEmailLogin = new JLabel("Email:");
		lblEmailLogin.setBounds(40, 50, 80, 25);
		panelLogin.add(lblEmailLogin);

		loginEmailField = new JTextField();
		loginEmailField.setBounds(130, 50, 200, 25);
		panelLogin.add(loginEmailField);

		JLabel lblPassLogin = new JLabel("Contraseña:");
		lblPassLogin.setBounds(40, 100, 80, 25);
		panelLogin.add(lblPassLogin);

		loginPasswordField = new JPasswordField();
		loginPasswordField.setBounds(130, 100, 200, 25);
		panelLogin.add(loginPasswordField);

		btnLogin = new JButton("Iniciar Sesión");
		btnLogin.setBounds(150, 160, 120, 40);
		panelLogin.add(btnLogin);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(PantallaDividida.class.getResource("/img/super.png")));
		lblNewLabel.setBounds(400, 50, 225, 225);
		panelLogin.add(lblNewLabel);

		JPanel panelRegister = new JPanel(null);
		tabbedPane.addTab("Register", null, panelRegister, null);
		panelRegister.setLayout(null);

		JLabel lblUserReg = new JLabel("Nombre:");
		lblUserReg.setBounds(40, 50, 80, 25);
		panelRegister.add(lblUserReg);

		registerUsernameField = new JTextField();
		registerUsernameField.setBounds(130, 50, 200, 25);
		panelRegister.add(registerUsernameField);

		JLabel lblEmailReg = new JLabel("Email:");
		lblEmailReg.setBounds(40, 100, 80, 25);
		panelRegister.add(lblEmailReg);

		registerEmailField = new JTextField();
		registerEmailField.setBounds(130, 100, 200, 25);
		panelRegister.add(registerEmailField);

		JLabel lblPassReg = new JLabel("Contraseña:");
		lblPassReg.setBounds(40, 150, 80, 25);
		panelRegister.add(lblPassReg);

		registerPasswordField = new JPasswordField();
		registerPasswordField.setBounds(130, 150, 200, 25);
		panelRegister.add(registerPasswordField);

		btnRegister = new JButton("Registrarse");
		btnRegister.setBounds(150, 210, 120, 40);
		panelRegister.add(btnRegister);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(PantallaDividida.class.getResource("/img/super.png")));
		lblNewLabel_1.setBounds(400, 50, 225, 225);
		panelRegister.add(lblNewLabel_1);

		btnLogin.addActionListener(e -> {
			String email = loginEmailField.getText();
			String password = new String(loginPasswordField.getPassword());
			Usuario usuarioLogueado = Usuario.iniciarSesion(email, password);

			if (usuarioLogueado != null) {
				switch (usuarioLogueado.getRol().toLowerCase()) {
				case "gerente":
					new HomeGerente(usuarioLogueado).setVisible(true);
					break;
				case "empleado":
					new HomeEmpleado(usuarioLogueado).setVisible(true);
					break;
				case "cliente":
					new HomeCliente(usuarioLogueado).setVisible(true);
					break;
				default:
					JOptionPane.showMessageDialog(this, "Rol no reconocido");
					return;
				}
				this.dispose();
			}
		});

		btnRegister.addActionListener(e -> {
			String username = registerUsernameField.getText();
			String email = registerEmailField.getText();
			String password = new String(registerPasswordField.getPassword());

			if (Usuario.registrarse(username, email, password, "Cliente")) {
				JOptionPane.showMessageDialog(this, "¡Cuenta de cliente creada!");
				tabbedPane.setSelectedIndex(0);
			}
		});
	}
}