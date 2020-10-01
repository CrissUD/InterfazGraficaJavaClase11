package app.client.components.inicio;

import app.client.components.accion.AccionComponent;
import app.client.components.accion.AccionTemplate;
import app.client.components.tarjeta.TarjetaComponent;

import app.services.graphicServices.ObjGraficosService;
import app.services.graphicServices.RecursosService;

import models.Accion;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class InicioTemplate extends JPanel{
  private static final long serialVersionUID = 1L;
  
  // Declaración Servicios y dependencias
  private InicioComponent inicioComponent;
  private ObjGraficosService sObjGraficos;
  private RecursosService sRecursos; 

  // Declaración Objetos Gráficos 
  private JPanel pMision, pVision, pNosotros, pAcciones;
  private JLabel lAcciones;

  // Declaración Objetos Decoradores
  private ImageIcon iTarjeta1, iTarjeta2, iTarjeta3;
  
  public InicioTemplate(InicioComponent inicioComponent){
    this.inicioComponent = inicioComponent;
    this.inicioComponent.getClass();
    sObjGraficos = ObjGraficosService.getService();
    sRecursos = RecursosService.getService();

    this.crearObjetosDecoradores();
    this.crearJPanels();
    this.crearContenidoPMision();
    this.crearContenidoPVision();
    this.crearContenidoPNosotros();
    this.crearContenidoPAcciones();
    
    this.setSize(850, 600);
    this.setBackground(sRecursos.getColorGrisClaro());
    this.setLayout(null);
    this.setVisible(true);
  }

  public void crearJPanels(){
    this.pMision = sObjGraficos.construirJPanel(20, 15, 256, 230, Color.WHITE, null);
    this.add(pMision);

    this.pVision = sObjGraficos.construirJPanel(296, 15, 256, 230, Color.WHITE, null);
    this.add(pVision);
    
    this.pNosotros = sObjGraficos.construirJPanel(572, 15, 256, 230, Color.WHITE, null);
    this.add(pNosotros);

    this.pAcciones = sObjGraficos.construirJPanel(20, 260, 810, 330, Color.WHITE, null);
    this.add(pAcciones);
  }

  public void crearObjetosDecoradores(){
    this.iTarjeta1 = new ImageIcon("Clase11/resources/images/tarjetas/tarjeta1.jpg");
    this.iTarjeta2 = new ImageIcon("Clase11/resources/images/tarjetas/tarjeta2.jpg");
    this.iTarjeta3 = new ImageIcon("Clase11/resources/images/tarjetas/tarjeta3.jpg");
  }

  public void crearContenidoPMision() {
    this.pMision.add( 
      new TarjetaComponent(
        "Nuestra Misión",
        iTarjeta1,
        "Brindar cursos a la comunidad académica para" +
        "complementar habilidades fuera del pensum común."
      ).getTarjetaTemplate()
    );
  }

  public void crearContenidoPVision() {
    this.pVision.add(
      new TarjetaComponent(
        "Nuestra Visión",
        iTarjeta2,
        "Brindar cursos académicos al 80% de los" +
        "estudiantes de ingeniería de Sistemas."
      ).getTarjetaTemplate()
    );
  }

  public void crearContenidoPNosotros() {
    this.pNosotros.add(
      new TarjetaComponent(
        "Sobre Nosotros",
        iTarjeta3,
        "Somos un grupo de trabajo de la Universidad" +
        "distrital Francisco jose de Caldas."
      ).getTarjetaTemplate()
    );
  }

  public void crearContenidoPAcciones() {
    this.lAcciones = sObjGraficos.construirJLabel(
      "Nuestros Servicios",
      10, 10, 160, 30,
      null, null,
      sRecursos.getFontTitulo(),
      null,
      sRecursos.getColorPrincipal(),
      null,
      "c"
    );
    this.pAcciones.add(lAcciones);

    int numeroAccion = 0, fila = 0;
    Accion accion = inicioComponent.obtenerAccion(numeroAccion);
    while (accion != null) {
      AccionTemplate pAccion = 
        new AccionComponent(accion).getAccionTemplate();
      pAccion.setLocation(
        15 + ((pAccion.getWidth() + 15) * (numeroAccion % 3)),
        50 + ((pAccion.getHeight() + 15) * fila)
      );
      if (numeroAccion % 3 == 2) fila++;
      this.pAcciones.add(pAccion);
      numeroAccion++;
      accion = inicioComponent.obtenerAccion(numeroAccion);
    }
  }
}