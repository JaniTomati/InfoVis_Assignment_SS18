package infovis.paracoords;

import infovis.scatterplot.Data;
import infovis.scatterplot.Model;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.sound.sampled.Line;
import javax.swing.JPanel;

public class View extends JPanel {
	private Model model = null;
	private static final int LINELENGTH = 500;
	private static final int LINEDISTANCE = 150;
	private static final int YLINECOORD = 50;

	private Rectangle2D markerRectangle = new Rectangle2D.Double(0,0,0,0);

	Rectangle2D getMarkerRectangle() {
		return markerRectangle;
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;

		int amount_labels = model.getLabels().size();
		ArrayList<String> labels = model.getLabels();

		ArrayList<Data> data = model.getList();
		ArrayList<Point2D> data_point_array = new ArrayList<Point2D>();
		ArrayList<Line2D> lines = new ArrayList<Line2D>();

		for (int i = 0; i < amount_labels; ++i) {
			g2D.drawLine(i * LINEDISTANCE + 20, YLINECOORD, i * LINEDISTANCE + 20, YLINECOORD + LINELENGTH);
			g2D.setColor(Color.BLACK);
			g2D.setFont(new Font("Arial", Font.BOLD, 10));
			g2D.drawString(labels.get(i), i * LINEDISTANCE + 20, YLINECOORD - 10);

			int offset = model.getList().size();
			int count = 0;
			double min_height = 10000.0;
			double max_height = 0.0;
			for (Data d : data) {
				// calculate borders of current cell
				if (count < offset) {
					if (d.getValue(i) < min_height) {
						min_height = d.getValue(i);
					}
					if (d.getValue(i) > max_height) {
						max_height = d.getValue(i);
					}
					min_height -= min_height * 0.001;
					max_height += max_height * 0.001;
				}
				count++;

				Point2D data_point = new Point2D.Double(i * LINEDISTANCE + 20,
						(int) (((d.getValue(i) - min_height) * LINELENGTH) / (max_height - min_height) + YLINECOORD));

				data_point_array.add(data_point);
				// draw data
				//g2D.fillOval(data_point.x, data_point.y, 7, 7);
				g2D.setColor(d.getColor());
				g2D.drawOval((int) ((Point2D.Double) data_point).x - 3, (int) ((Point2D.Double) data_point).y - 3, 6, 6);
				if(markerRectangle.contains(data_point)) {
					//d.setColor(Color.RED);
				}
			}
		}

		for(int i = 0; i < data_point_array.size()-1; ++i) {
			if(i >= data.size()) {
				lines.add(new Line2D.Double(data_point_array.get(i), data_point_array.get(i-data.size())));
				g2D.setColor(data.get(i % data.size()).getColor());
				g2D.drawLine((int) data_point_array.get(i).getX(),(int) data_point_array.get(i).getY(),
						(int) data_point_array.get(i - data.size()).getX(), (int) data_point_array.get(i - data.size()).getY());
				g2D.setColor(Color.BLACK);
			}
		}
		for(int i = 0; i < lines.size(); ++i) {
			if(markerRectangle.intersectsLine(lines.get(i))){
				data.get(i % data.size()).setColor(Color.RED);
				this.repaint();
			}
		}
	}

	@Override
	public void update(Graphics g) {
		paint(g);
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}
	
}
