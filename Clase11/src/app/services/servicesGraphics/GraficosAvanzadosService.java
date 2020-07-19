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
import java.awt.geom.RectangularShape;

import javax.swing.BorderFactory;
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
        return new DefaultTableCellRenderer(){
            private static final long serialVersionUID = -8946942932242371111L;

            @Override
            public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected, boolean hasFocus, int row,int column
            ){
                JLabel cell = (JLabel) super.getTableCellRendererComponent (table, value, isSelected, hasFocus, row, column);
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
        };
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
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
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
        int contador = 0;
        while(siguienteColor.getRed() < 255){
            if(contador == 0){ 
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
    
    public Border DibujarBordeRedondeado (Color color, int radio, boolean esLineal, Image imagen) {
        Border bordeRedondeado = new Border() {

            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int ancho, int alto) {
                Graphics2D g2= (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
                Area area = new Area();
                Component padreContenedor  = c.getParent();
                RoundRectangle2D rectanguloBordeado = new RoundRectangle2D.Double();
                rectanguloBordeado.setRoundRect(x, y, ancho-1, alto-1, radio, radio);
                if (padreContenedor != null) {
                    if(esLineal){
                        diburjarFondo(c, padreContenedor, imagen, g2, ancho, alto);
                        dibujarBorde(c, g2, color, x, y, ancho, alto, esLineal, rectanguloBordeado);
                    }
                    else{
                        area = dibujarBorde(c, g2, color, x, y, ancho, alto, esLineal, rectanguloBordeado);
                        diburjarFondo(c, padreContenedor, imagen, g2, ancho, alto);
                        g2.setClip(null);
                        g2.draw(area);
                    }
                }
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
        return bordeRedondeado;
    }
    
    public AbstractBorder DibujarBordeCircular(Color color, boolean esLineal, Image imagen) {
        AbstractBorder bordeCircular = new AbstractBorder() {
        
            private static final long serialVersionUID = 2009875951859777681L;

            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int ancho, int alto) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
                Area area = new Area();
                Component padreContenedor  = c.getParent();
                Ellipse2D circulo = new Ellipse2D.Double();
                circulo.setFrameFromCenter( 
                    new Point(x + ancho / 2, y + alto / 2),
                    new Point(ancho, alto)
                );
                if (padreContenedor != null) {
                    if(esLineal){
                        diburjarFondo(c, padreContenedor, imagen, g2, ancho, alto);
                        dibujarBorde(c, g2, color, x, y, ancho, alto, esLineal, circulo);
                    }
                    else{
                        area = dibujarBorde(c, g2, color, x, y, ancho, alto, esLineal, circulo);
                        diburjarFondo(c, padreContenedor, imagen, g2, ancho, alto);
                        g2.setClip(null);
                        g2.draw(area);
                    }
                }
            }
        };
        return bordeCircular;
    }

    public void diburjarFondo(Component c, Component padreContenedor, Image imagen, Graphics2D g2, int ancho, int alto){
        if(imagen != null)
            g2.drawImage(
                imagen, 
                0, 0, imagen.getWidth(null), imagen.getHeight(null),
                c.getX(), c.getY(), imagen.getWidth(null) + c.getX(), imagen.getHeight(null) + c.getY(),
                c
            );
        else{
            Color colorFondo = padreContenedor.getBackground();
            g2.setColor(colorFondo);
            g2.fillRect(0, 0, ancho, alto);
        }
    }

    public Area dibujarBorde(
        Component c, Graphics2D g2, Color color, int x, int y, int ancho, int alto, boolean esLineal, RectangularShape figura
    ){
        if(color == null)
            g2.setPaint(c.getBackground());
        else
            g2.setPaint(color);
        Area area = new Area(figura);
        Rectangle rectangulo = new Rectangle(0,0,ancho, alto);
        Area RegionBorde = new Area(rectangulo);
        RegionBorde.subtract(area);
        g2.setClip(RegionBorde);
        if(esLineal)
            g2.setClip(null);
            g2.draw(area);
        return area;
    }
    
    public static GraficosAvanzadosService getService(){
        if(servicio == null)
            servicio = new GraficosAvanzadosService();
        return servicio;
    }
}