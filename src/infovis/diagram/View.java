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
	private double translateX;
	private double translateY;
	private Rectangle2D marker = new Rectangle2D.Double();      // to highlight currently viewed area in overview
	private Rectangle2D overviewRect = new Rectangle2D.Double();
	private int markerPosX = 0;
	private int markerPosY = 0;
	private boolean translate = false;


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
		g2D.translate(-translateX, -translateY);
		paintDiagram(g2D);
		g2D.translate(translateX, translateY);

		drawOverview(g2D);
		updateMarker((int) translateX,(int) translateY, getScale());

	}

	private void drawOverview(Graphics2D g2D) {
		g2D.scale(0.25 / getScale(), 0.25 / getScale());
		overviewRect.setRect(0,0, MAX_WIDTH, MAX_HEIGHT);
		g2D.clearRect(0,0, MAX_WIDTH, MAX_HEIGHT); // do not show vertices under overview

		// set color and show overview rect
		g2D.setColor(Color.BLACK); // rectangle "stroke"
		g2D.draw(overviewRect);

		// paint smaller scaled version
		paintDiagram(g2D);	// paint overview diagram

		// set marker rectangle (current viewed part of diagram)
		Color red_opaque = new Color(255, 0, 0, 125);
		g2D.setColor(red_opaque);
		updateMarker(markerPosX, markerPosY, getScale());
		//updateMarker((int) (translateX / getScale()), (int) (translateY / getScale()), scale);
		g2D.fill(marker);
	}

	private void paintDiagram(Graphics2D g2D) {
		for (Element element: model.getElements()) {
			element.paint(g2D);
		}
	}
	
	private void getCanvasSize() {
		for (Element element : model.getElements()) {
			element.getX();
			element.getY();
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
	public void setTranslateY(double translateY) {
		this.translateY = translateY;
	}
	public void updateTranslation(double x, double y) {
		setTranslateX(x);
		setTranslateY(y);
	}
	public void updateOverview(Graphics2D g2D) {
		g2D.translate(translateX, translateY);
		drawOverview(g2D);
		g2D.translate(-translateX, -translateY);
	}
	public void updateMarker(int x, int y, double scale) {
		double width  = getWidth() / scale;
		double height = getHeight() / scale;
		markerPosX = x;
		markerPosY = y;
		translate = true;

		if(x + width > MAX_WIDTH) {
			x = MAX_WIDTH - (int) width;
			translate = false;
		}
		if(y + height > MAX_HEIGHT) {
			y = MAX_HEIGHT - (int) height;
			translate = false;
		}
		if(x < 0){
			x = 0;
			translate = false;
		}
		if(y < 0){
			translate = false;
			y = 0;
		}
		marker.setRect(x, y, width, height); // 16, 10
	}
	public Rectangle2D getMarker(){
		return marker;
	}
	public boolean markerContains(int x, int y){
		return marker.contains(x, y);
	}

	public boolean overviewContains(int x, int y){
		return overviewRect.contains(x, y);
	}
}
 