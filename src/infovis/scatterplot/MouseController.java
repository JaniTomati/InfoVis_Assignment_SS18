package infovis.scatterplot;

import infovis.diagram.elements.Edge;
import infovis.diagram.elements.Vertex;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MouseController implements MouseListener, MouseMotionListener {

	private Model model = null;
	private View view = null;
	private int x_start = 0;
	private int y_start = 0;

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent arg0) {
	}

	public void mouseExited(MouseEvent arg0) {
	}

	public void mousePressed(MouseEvent e) {
		x_start = e.getX();
		y_start = e.getY();
		//Iterator<Data> iter = model.iterator();
		//view.getMarkerRectangle().setRect(e.getX(),e.getY(),2,2);
		//view.repaint();
	}

	public void mouseReleased(MouseEvent e) {
		int x_end = e.getX();
		int y_end = e.getY();

		view.getMarkerRectangle().setRect(x_start, y_start, x_end-x_start, y_end-y_start);

		view.repaint();
		for (Data d: model.getList()) {
			d.setColor(Color.BLACK);
		}
	}

	public void mouseDragged(MouseEvent arg0) {
		//view.repaint();
	}

	public void mouseMoved(MouseEvent arg0) {
		view.repaint();
	}

	public void setModel(Model model) {
		this.model  = model;	
	}

	public void setView(View view) {
		this.view  = view;
	}

}
