package infovis.diagram.layout;

import infovis.debug.Debug;
import infovis.diagram.Model;
import infovis.diagram.View;
import infovis.diagram.elements.Edge;
import infovis.diagram.elements.Vertex;

public class Fisheye implements Layout{

	private double fishEyeCenterX = 250;
	private double fishEyeCenterY = 240;
	private double d = 4;

	public void setMouseCoords(int x, int y, View view) {
		fishEyeCenterX = x;
		fishEyeCenterY = y;
		transform(view.getModel(), view);
	}

	public Model transform(Model model, View view) {

		for(int i = 0; i < model.getVertices().size(); ++i) {
			Vertex current = model.getVertices().get(i);

			double fishX = calcFish(view.getWidth(), fishEyeCenterX, current.getX());
			double fishY = calcFish(view.getHeight(), fishEyeCenterY, current.getY());

			double qNormX = current.getX() + current.getWidth()/2;
			double qNormY = current.getY() + current.getHeight()/2;

			double qFishX = calcFish(view.getWidth(), fishEyeCenterX, qNormX);
			double qFishY = calcFish(view.getHeight(), fishEyeCenterY, qNormY);

			double sGeom = 2 * Math.min(Math.abs(qFishX - fishX), Math.abs(qFishY - fishY)) * 0.05;

			current.setFrame(fishX, fishY, current.getWidth() * sGeom, current.getHeight() * sGeom);

			model.getVertices().set(i, current);
		}
		view.setModel(model);
		view.repaint();

		return model;
	}

	public double calcFish(double pBorder, double pFocus, double pNorm) {
		double dMax = calcDMax(pBorder, pFocus, pNorm);
		double dNorm = pNorm - pFocus;
		double g = calcG(dNorm / dMax);

		return pFocus + g * dMax;
	}

	public double calcDMax(double pBorder, double pFocus, double pNorm) {
		double dMax = 0;
		if (pNorm > pFocus)
			dMax = pBorder - pFocus;
		else
			dMax = 0 - pFocus;
		return dMax;
	}

	public double calcG(double x) {
		return ((d + 1) * (x)) / ((d * x) + 1);
	}
}
