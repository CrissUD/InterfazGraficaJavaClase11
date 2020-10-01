package app.client.login;

import app.client.vistaPrincipal.VistaPrincipalComponent;
import app.services.logicServices.UsuarioService;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

public class LoginComponent extends MouseAdapter implements ActionListener {

  private LoginTemplate loginTemplate;
  private VistaPrincipalComponent vistaPrincipal;
  private UsuarioService sUsuario;
  private JButton boton;
  private JTextField text;
  private JLabel label;
  private String[] placeholders = { "Nombre Usuario", "Clave Usuario" };

  public LoginComponent() {
    sUsuario = UsuarioService.getService();
    this.loginTemplate = new LoginTemplate(this);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == loginTemplate.getBEntrar())
      this.enviarDatosUsuario();
    if (e.getSource() == loginTemplate.getBCerrar())
      System.exit(0);
    if (e.getSource() == loginTemplate.getBOpcion1())
      JOptionPane.showMessageDialog(null, "Boton Opcion", "Advertencia", 1);
    if (e.getSource() == loginTemplate.getBRegistrarse())
      JOptionPane.showMessageDialog(null, "Boton Registro", "Advertencia", 1);
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    if (e.getSource() instanceof JTextField) {
      text = ((JTextField) e.getSource());
      label = loginTemplate.getLabels(text);
      label.setIcon(loginTemplate.getIAzul(label));
      text.setForeground(loginTemplate.getRecursosService().getColorPrincipal());
      text.setBorder(loginTemplate.getRecursosService().getBInferiorAzul());
      if (
        text.getText().equals(placeholders[0]) || 
        text.getText().equals(placeholders[1])
      ) 
        text.setText("");
    }
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    if (e.getSource() instanceof JButton) {
      boton = ((JButton) e.getSource());
      boton.setBackground(loginTemplate.getRecursosService().getColorPrincipalOscuro());
    }
    if (e.getSource() instanceof JLabel) {
      label = ((JLabel) e.getSource());
      label.setIcon(loginTemplate.getINaranja(label));
    }
  }

  @Override
  public void mouseExited(MouseEvent e) {
    if (e.getSource() instanceof JButton) {
      boton = ((JButton) e.getSource());
      boton.setBackground(loginTemplate.getRecursosService().getColorPrincipal());
    }
    if (e.getSource() instanceof JLabel) {
      label = ((JLabel) e.getSource());
      label.setIcon(loginTemplate.getIBlanca(label));
    }
  }

  public void enviarDatosUsuario() {
    String nombreUsuario = loginTemplate.getTNombreUsuario().getText();
    String claveUsuario = new String(loginTemplate.getTClaveUsuario().getPassword());
    String tipoUsuario = ((String) loginTemplate.getCbTipoUsuario().getSelectedItem());
    if(!nombreUsuario.isBlank() && !claveUsuario.isBlank()) {
      if (sUsuario.verificarDatosUsuario(nombreUsuario, claveUsuario, tipoUsuario)) {
        JOptionPane.showMessageDialog(null, "Ingreso Exitoso", "Advertencia", 1);
        entrar();
      } else
        JOptionPane.showMessageDialog(null, "Algo quedo mal", "Advertencia", 2);
    } else
      JOptionPane.showMessageDialog(null, "No puede dejar un campo vacio", "Advertencia", 2);
  }

  public void entrar() {
    if (vistaPrincipal == null)
      this.vistaPrincipal = new VistaPrincipalComponent(this);
    else {
      this.vistaPrincipal.restaurarValores();
      this.vistaPrincipal.getVistaPrincipalTemplate().setVisible(true);
    }
    loginTemplate.setVisible(false);
  }

  public void restaurarValores() {
    this.getLoginTemplate().getTNombreUsuario().setText("Nombre Usuario");
    this.getLoginTemplate().getTNombreUsuario()
      .setBorder(this.getLoginTemplate().getRecursosService().getBInferiorGris());
    this.getLoginTemplate().getTNombreUsuario()
      .setForeground(this.getLoginTemplate().getRecursosService().getColorGrisOscuro());
    this.getLoginTemplate().getTClaveUsuario().setText("Clave Usuario");
    this.getLoginTemplate().getTClaveUsuario()
      .setBorder(this.getLoginTemplate().getRecursosService().getBInferiorGris());
    this.getLoginTemplate().getTClaveUsuario()
      .setForeground(this.getLoginTemplate().getRecursosService().getColorGrisOscuro());
    this.getLoginTemplate().getCbTipoUsuario().setSelectedIndex(0);
  }

  public LoginTemplate getLoginTemplate() { return this.loginTemplate; }
}