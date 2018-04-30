package infovis.diagram;

import infovis.diagram.elements.Element;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;

import javax.swing.JPanel;



public class View extends JPanel{
	private final static int  MAX_WIDTH  = 800;
	private final static int  MAX_HEIGHT = 720;
	
	private Model model = null;
	private Color color = Color.BLUE;
	private double scale = 1;
	private double translateX= 0;
	private double translateY=0;
	private Rectangle2D marker = new Rectangle2D.Double();      // to highlight currently viewed area in overview
	private Rectangle2D overviewRect = new Rectangle2D.Double();   

	public Model getModel() {
		return model;
	}
	public void setModel(Model model) {
		this.model = model;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}

	
	public void paint(Graphics g) {
		
		Graphics2D g2D = (Graphics2D) g;
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2D.clearRect(0, 0, getWidth(), getHeight());
		
		g2D.scale(getScale(), getScale());
				
		paintDiagram(g2D);
	
		// set position of overview rect 
		g2D.scale(1 / getScale(), 1 / getScale());
		overviewRect.setRect(0, 0, 200, 180);
		g2D.clearRect(0, 0, 200, 180); // do not show vertices under overview

		
		// set color and show overview rect
		g2D.setColor(Color.BLACK); // rectangle "stroke"
        g2D.draw(overviewRect);
        
        // paint smaller scaled version
        g2D.scale(0.25, 0.25);
		paintDiagram(g2D);
		
        
		// set marker rectangle (current viewed part of diagram)
		Color red_opaque = new Color(255, 0, 0, 125);
	    g2D.setColor(red_opaque);
	    g2D.fill(marker);
		updateMarker(0, 0, getScale());	
		
	}
	
	private void paintDiagram(Graphics2D g2D){
		for (Element element: model.getElements()){
			element.paint(g2D);
		}
	}
	
	public void setScale(double scale) {
		this.scale = scale;
	}
	public double getScale(){
		return scale;
	}
	public double getTranslateX() {
		return translateX;
	}
	public void setTranslateX(double translateX) {
		this.translateX = translateX;
	}
	public double getTranslateY() {
		return translateY;
	}
	public void setTranslateY(double tansslateY) {
		this.translateY = tansslateY;
	}
	public void updateTranslation(double x, double y){
		setTranslateX(x);
		setTranslateY(y);
	}	
	public void updateMarker(int x, int y, double scale){
		double width  = getWidth() / scale;
		double height = getHeight() / scale;
		if (width > MAX_WIDTH)
			width = (MAX_WIDTH) / scale; 
		if (height > MAX_HEIGHT)
			height = (MAX_HEIGHT) / scale;
		
		marker.setRect(x, y, width, height); // 16, 10 
	}
	public Rectangle2D getMarker(){
		return marker;
	}
	public boolean markerContains(int x, int y){
		return marker.contains(x, y);
	}
}
 