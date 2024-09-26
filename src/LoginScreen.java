import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginScreen extends JFrame {

    // Componentes da interface
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;

    // Construtor da tela de login
    public LoginScreen() {
        // Configurações da janela
        setTitle("Login");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // Rótulos e campos de texto
        JLabel lblUsername = new JLabel("Usuário:");
        lblUsername.setBounds(20, 20, 80, 25);
        add(lblUsername);

        txtUsername = new JTextField();
        txtUsername.setBounds(100, 20, 160, 25);
        add(txtUsername);

        JLabel lblPassword = new JLabel("Senha:");
        lblPassword.setBounds(20, 50, 80, 25);
        add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(100, 50, 160, 25);
        add(txtPassword);

        // Botão de login
        btnLogin = new JButton("Login");
        btnLogin.setBounds(100, 80, 160, 25);
        add(btnLogin);

        // Ação do botão de login
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                authenticateUser();
            }
        });
    }

    // Método para autenticar o usuário
    private void authenticateUser() {
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());

        // Conectar ao banco de dados e verificar credenciais
        try {
            // Configurações da conexão usando autenticação integrada
            String url = "jdbc:sqlserver://MTZNOTFS059377;databaseName=Fintech";

            // Estabelecendo a conexão usando autenticação integrada do Windows
            Connection connection = DriverManager.getConnection(url);

            // Consulta SQL para verificar o usuário e senha
            String sql = "SELECT * FROM Usuarios WHERE username = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            // Verificar se encontrou o usuário
            if (resultSet.next()) {
                JOptionPane.showMessageDialog(this, "Login bem-sucedido!");
                // Ação após login bem-sucedido (ex: abrir outra janela)
            } else {
                JOptionPane.showMessageDialog(this, "Usuário ou senha incorretos.");
            }

            // Fechar conexão
            connection.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao conectar ao banco de dados.");
        }
    }
}
