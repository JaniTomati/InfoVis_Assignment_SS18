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


	private static final int WIDTH = 120;
	private static final int HEIGHT = 120;

	private Model model = null;
	private Rectangle2D markerRectangle = new Rectangle2D.Double(0,0,0,0);

	public Rectangle2D getMarkerRectangle() {
		return markerRectangle;
	}

	@Override
	public void paint(Graphics g) {

		Graphics2D g2D = (Graphics2D) g;

		/*for (String l : model.getLabels()) {
			Debug.print(l);
			Debug.print(",  ");
			Debug.println("");
		}
		for (Range range : model.getRanges()) {
			Debug.print(range.toString());
			Debug.print(",  ");
			Debug.println("");
		}*/
		/*
		for (Data d : model.getList()) {
			Debug.print(d.toString());
			Debug.println("");
		}*/

		int amount_labels = model.getLabels().size();
		ArrayList<String> labels = model.getLabels();

		ArrayList<Data> data = model.getList();


		for(int i = 0; i < amount_labels; ++i) {
			for(int j = 0; j < amount_labels; ++j) {
				if(i == 0) {
					Font font = new Font ("Arial", Font.BOLD, 10);
					AffineTransform trans = new AffineTransform();
					trans.rotate(-Math.toRadians(90), 0, 0);
					Font rotated_font = font.deriveFont(trans);
					g2D.setFont(rotated_font);
					g2D.drawString(labels.get(j), i * WIDTH + 25, j * HEIGHT + (50 + HEIGHT/2));
				} if (j == 0) {
					g2D.setFont(new Font("Arial", Font.BOLD, 10));
					g2D.drawString(labels.get(i), i * WIDTH + (50 + WIDTH/2), j * HEIGHT + 25);
				}

				int offset = model.getList().size();
				int count = 0;
				double LOWERBORDER = 10000.0;
				double HIGHERBORDER = 0.0;
				double LOWERBORDERJ = 10000.0;
				double HIGHERBORDERJ = 0.0;
				for(Data d: data) {
					if(count < offset) {
						if(d.getValue(i) < LOWERBORDER) {
							LOWERBORDER = d.getValue(i);
						} if(d.getValue(i) > HIGHERBORDER) {
							HIGHERBORDER = d.getValue(i);
						} if(d.getValue(j) < LOWERBORDERJ) {
							LOWERBORDERJ = d.getValue(j);
						} if(d.getValue(j) > HIGHERBORDERJ) {
							HIGHERBORDERJ = d.getValue(j);
						}
						LOWERBORDER = LOWERBORDER - LOWERBORDER*0.1;
						LOWERBORDERJ = LOWERBORDERJ - LOWERBORDERJ*0.1;
						HIGHERBORDER = HIGHERBORDER + HIGHERBORDER*0.1;
						HIGHERBORDERJ = HIGHERBORDERJ + HIGHERBORDERJ*0.1;
					}
					count++;

					g2D.setColor(d.getColor());
					g2D.drawOval( (int) (((d.getValue(i) - LOWERBORDER) * HEIGHT) / (HIGHERBORDER - LOWERBORDER) + i * HEIGHT + 50),
							(int) (((d.getValue(j) - LOWERBORDERJ) * WIDTH) / (HIGHERBORDERJ - LOWERBORDERJ) + j * WIDTH + 50), 7, 7);
					g2D.setColor(Color.BLACK);
				}
				g2D.draw(new Rectangle2D.Double(i * WIDTH + 50, j * HEIGHT + 50, WIDTH, HEIGHT));
			}
		}


	}
	public void setModel(Model model) {
		this.model = model;
	}
}
