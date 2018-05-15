package infovis.scatterplot;

import com.sun.corba.se.impl.orbutil.graph.Graph;
import infovis.debug.Debug;
import sun.security.util.Length;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JPanel;

public class View extends JPanel {


	private static final int LENGTH = 100;
	private static final int WIDTH = 100;

	private Model model = null;
	private Rectangle2D markerRectangle = new Rectangle2D.Double(0,0,0,0);

	public Rectangle2D getMarkerRectangle() {
		return markerRectangle;
	}

	@Override
	public void paint(Graphics g) {

		Graphics2D g2D = (Graphics2D) g;

		for (String l : model.getLabels()) {
			Debug.print(l);
			Debug.print(",  ");
			Debug.println("");
		}
		for (Range range : model.getRanges()) {
			Debug.print(range.toString());
			Debug.print(",  ");
			Debug.println("");
		}
		for (Data d : model.getList()) {
			Debug.print(d.toString());
			Debug.println("");
		}

		int amount_labels = model.getLabels().size();
		ArrayList<String> labels = model.getLabels();


		for(int i = 0; i < amount_labels; ++i) {
			for(int j = 0; j < amount_labels; ++j) {
				if(i == 0) {
					Font font = new Font ("Arial", Font.BOLD, 10);
					AffineTransform trans = new AffineTransform();
					trans.rotate(-Math.toRadians(90), 0, 0);
					Font rotated_font = font.deriveFont(trans);
					g2D.setFont(rotated_font);
					g2D.drawString(labels.get(j), i * LENGTH + 25, j * WIDTH + (50 + WIDTH/2));
				} else if (j == 0) {
					g2D.setFont(new Font("Arial", Font.BOLD, 10));
					g2D.drawString(labels.get(i), i * LENGTH + (50 + LENGTH/2), j * WIDTH + 25);
				}
				g2D.draw(new Rectangle2D.Double(i * LENGTH + 50, j * WIDTH + 50, LENGTH, WIDTH));
			}
		}


	}
	public void setModel(Model model) {
		this.model = model;
	}
}
