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

	Rectangle2D getMarkerRectangle() {
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
				double min_width = 10000.0;
				double max_width = 0.0;
				double min_height = 10000.0;
				double max_height = 0.0;
				for(Data d: data) {
					// calculate borders of current cell
					if(count < offset) {
						if(d.getValue(i) < min_width) {
							min_width = d.getValue(i);
						} if(d.getValue(i) > max_width) {
							max_width = d.getValue(i);
						} if(d.getValue(j) < min_height) {
							min_height = d.getValue(j);
						} if(d.getValue(j) > max_height) {
							max_height = d.getValue(j);
						}
						min_width  -= min_width  * 0.1;
						min_height -= min_height * 0.1;
						max_width  += max_width  * 0.1;
						max_height += max_height * 0.1;
					}
					count++;

					Point data_point = new Point((int) (((d.getValue(i) - min_width) * HEIGHT) / (max_width - min_width) + i * HEIGHT + 50),
							(int) (((d.getValue(j) - min_height) * WIDTH) / (max_height - min_height) + j * WIDTH + 50));

					if(markerRectangle.contains(data_point)) {
						d.setColor(Color.RED);
					}
					// draw data
					g2D.setColor(d.getColor());
					//g2D.fillOval(data_point.x, data_point.y, 7, 7);
					g2D.drawOval(data_point.x, data_point.y, 7, 7);
				}
				g2D.setColor(Color.BLACK);
				g2D.draw(new Rectangle2D.Double(i * WIDTH + 50, j * HEIGHT + 50, WIDTH, HEIGHT));
			}
		}


	}
	public void setModel(Model model) {
		this.model = model;
	}
}
