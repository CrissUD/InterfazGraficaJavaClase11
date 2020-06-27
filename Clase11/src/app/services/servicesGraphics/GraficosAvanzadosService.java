package app.services.servicesGraphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableCellRenderer;

public class GraficosAvanzadosService {
    
    static private GraficosAvanzadosService servicio;

    private GraficosAvanzadosService(){}

    public DefaultTableCellRenderer devolverTablaPersonalizada(
        Color colorPrincipal, Color colorSecundario, Color colorSeleccion, Color colorFuente, Font fuente
    ){
        class MiRender extends DefaultTableCellRenderer{
            private static final long serialVersionUID = -8946942932242371111L;

            @Override
            public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected, boolean hasFocus, int row,int column
            ){
                JLabel cell = (JLabel)super.getTableCellRendererComponent (table, value, isSelected, hasFocus, row, column);
                cell.getClass(); 
                this.setOpaque(true);
                this.setFont(fuente);
                this.setHorizontalAlignment(SwingConstants.CENTER);
                if (row % 2 != 0){
                    this.setBackground(colorPrincipal);
                    this.setForeground(colorFuente);
                } 
                else{
                    this.setBackground(colorSecundario);
                    this.setForeground(colorFuente);
                }
                if(isSelected==true){
                    this.setBackground(colorSeleccion);
                    this.setForeground(colorSecundario);
                }
                return this;
            }
        }
        return new MiRender();
    }

    public BasicScrollBarUI devolverScrollPersonalizado(Color colorBarraNormal, Color colorBarraArrastrada){
        return new BasicScrollBarUI() {
            private final Dimension d = new Dimension();
        
            @Override
            protected JButton createDecreaseButton(int orientation) {
                return new JButton() {
                    private static final long serialVersionUID = -5412240959518806029L;
                    @Override
                    public Dimension getPreferredSize() {
                        return d;
                    }
                };
            }
        
            @Override
            protected JButton createIncreaseButton(int orientation) {
                return new JButton() {
                    private static final long serialVersionUID = 2093457850332785270L;
                    @Override
                    public Dimension getPreferredSize() {
                        return d;
                    }
                };
            }
        
            @Override
            protected void paintTrack(Graphics g, JComponent c, Rectangle r) {
            }
        
            @Override
            protected void paintThumb(Graphics g, JComponent c, Rectangle r) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
                JScrollBar sb = (JScrollBar) c;
                if (!sb.isEnabled() || r.width > r.height) {
                    return;
                } else if (isDragging) {
                    g2.setPaint(colorBarraArrastrada);
                } else if (isThumbRollover()) {
                    g2.setPaint(colorBarraNormal);
                } else {
                    g2.setPaint(colorBarraNormal);
                }
                g2.fillRoundRect(r.x + 4, r.y, 8, r.height, 10, 10);
                g2.setPaint(Color.WHITE);
                g2.drawRoundRect(r.x + 4, r.y, 10, r.height, 10, 10);
                g2.dispose();
            }
        
            @Override
            protected void setThumbBounds(int x, int y, int width, int height) {
                super.setThumbBounds(x, y, width, height);
                scrollbar.repaint();
            }
        };
    }

    public Border devolverBordeDifuminado(Color colorBase){
        Border bordeFinal = null;
        Color siguienteColor = new Color(colorBase.getRed()+4, colorBase.getGreen()+4, colorBase.getBlue()+4);
        int contador=0;
        while(siguienteColor.getRed() < 255){
            if(contador==0){ 
                Border borde1=  BorderFactory.createLineBorder(colorBase,1,true);  
                Border borde2=  BorderFactory.createLineBorder(siguienteColor,1,true);
                bordeFinal = BorderFactory.createCompoundBorder(borde2,borde1);
            }
            else{
                siguienteColor = new Color(siguienteColor.getRed()+4, siguienteColor.getGreen()+4, siguienteColor.getBlue()+4);
                Border borde=  BorderFactory.createLineBorder(siguienteColor,1,true);
                bordeFinal = BorderFactory.createCompoundBorder(borde, bordeFinal);
            }
            contador ++;
        }
        return bordeFinal;
    }
    
    public Border DibujarBordeRedondeado (int radio,int opcion) {
        Border b = new Border() {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int ancho, int alto) {
                Graphics2D g2= (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
                RoundRectangle2D rectangulo= new RoundRectangle2D.Double();
                g2.setColor(new Color(200,200,200));
                rectangulo.setRoundRect(x, y, ancho-1, alto-1, radio, radio);
                Area area = new Area(rectangulo);
                Component parent  = c.getParent();
                if (parent!=null) {
                    Rectangle rect = new Rectangle(0,0,ancho, alto);
                    Area borderRegion = new Area(rect);
                    borderRegion.subtract(area);
                    g2.setClip(borderRegion);
                    if(opcion==1){
                        ImageIcon yourImage=(ImageIcon)((JLabel) parent).getIcon();   
                        Image image = yourImage.getImage();
                        g2.drawImage(image, 0, 0, image.getWidth(null), image.getHeight(null),c.getX(), c.getY(), image.getWidth(null)+c.getX(), image.getHeight(null)+c.getY(),c);
                    }
                    g2.setClip(null);
                }       
                g2.draw(area);
            }

            @Override
            public Insets getBorderInsets(Component cmpnt) {
                return new Insets(radio+1, radio+1, radio+2, radio);
            }

            @Override
            public boolean isBorderOpaque() {
                return true;
            }
        } ;
        return b;
    }
    
    public AbstractBorder DibujarBordeCircular() {
        AbstractBorder b= new AbstractBorder() {
        
            private static final long serialVersionUID = 2009875951859777681L;

            @Override
            public void paintBorder(Component c,Graphics g,int x, int y, int ancho, int alto) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
                Ellipse2D circle2D = new Ellipse2D.Double();//java2d
                //dibuja el circulo en toda su extensión
                circle2D.setFrameFromCenter( 
                    new Point(x+ancho/2,y+alto/2), //centro 
                    new Point( ancho , alto) //ancho y alto
                );                         
                Area area = new Area(circle2D);        
                //pinta el fondo con el color del componente padre 
                Component parent  = c.getParent();
                if (parent!=null) {
                    Color bg = parent.getBackground();
                    Rectangle rect = new Rectangle(0,0,ancho, alto);
                    Area borderRegion = new Area(rect);
                    borderRegion.subtract(area);
                    g2.setClip(borderRegion);
                    g2.setColor(bg);
                    g2.fillRect(0, 0, ancho, alto);
                    g2.setClip(null);
                }                       
                g2.draw(area);
            }
        };
        return b;
    }
    
    public static GraficosAvanzadosService getService(){
        if(servicio == null)
            servicio = new GraficosAvanzadosService();
        return servicio;
    }
}