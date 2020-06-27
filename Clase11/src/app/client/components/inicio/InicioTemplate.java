package app.client.components.inicio;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import app.client.components.accion.AccionComponent;
import app.client.components.accion.AccionTemplate;
import app.client.components.tarjeta.TarjetaComponent;
import app.services.servicesGraphics.ObjGraficosService;
import app.services.servicesGraphics.RecursosService;
import models.Accion;

public class InicioTemplate extends JPanel{

    private static final long serialVersionUID = -1137494554115287686L;
    
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
        this.iTarjeta1 = new ImageIcon("Clase11/resources/img/tarjeta1.jpg");
        this.iTarjeta2 = new ImageIcon("Clase11/resources/img/tarjeta2.jpg");
        this.iTarjeta3 = new ImageIcon("Clase11/resources/img/tarjeta3.jpg");
    }

    public void crearContenidoPMision(){
        this.pMision.add(
            new TarjetaComponent(
                "Nuestra Misión", 
                iTarjeta1, 
                "Brindar cursos a la comunidad académica para complementar habilidades fuera del pensum común." 
            ).getTarjetaTemplate()
        );
    }

    public void crearContenidoPVision(){
        this.pVision.add(
            new TarjetaComponent(
                "Nuestra Visión", 
                iTarjeta2, 
                "Brindar cursos académicos al 80% de los estudiantes de ingeniería de Sistemas." 
            ).getTarjetaTemplate()
        );
    }

    public void crearContenidoPNosotros(){
        this.pNosotros.add(
            new TarjetaComponent(
                "Sobre Nosotros", 
                iTarjeta3, 
                "Somos un grupo de trabajo de la Universidad distrital Francisco jose de Caldas."
            ).getTarjetaTemplate()
        );
    }

    public void crearContenidoPAcciones(){
        this.lAcciones = sObjGraficos.construirJLabel(
            "Nuestros Servicios", 10, 10, 160, 30, null, sRecursos.getColorAzul(), 
            null, sRecursos.getFontTitulo(), "c"
        );
        this.pAcciones.add(lAcciones);

        int numeroAccion=0;
        Accion accion = inicioComponent.obtenerAccion(numeroAccion);
        while(accion != null){
            AccionTemplate pAccion= new AccionComponent(
                accion.getImagenAccion(), accion.getNombreAccion(), accion.getDescripcionAccion()
            ).getAccionTemplate();
            if(numeroAccion <=2)
                pAccion.setLocation(15 + numeroAccion * pAccion.getWidth() + numeroAccion * 15 , 50);
            else
                pAccion.setLocation(15 + (numeroAccion - 3) * pAccion.getWidth() + (numeroAccion - 3) * 15 , 190);
            this.pAcciones.add(pAccion);
            numeroAccion ++;
            accion = inicioComponent.obtenerAccion(numeroAccion);
        }
    }
}