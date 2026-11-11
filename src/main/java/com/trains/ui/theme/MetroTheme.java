package com.trains.ui.theme;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

/** Flat transit map style: thick red lines, white ring badges for stations and boxy carriages. */
public class MetroTheme implements Theme {

	private static final Color LINE = Color.decode("#e4322b");
	private static final Color INK = Color.decode("#1a1a1a");
	private static final Color FULL = Color.decode("#ffb000");
	private static final Color WINDOW = Color.decode("#ffd400");

	@Override
	public String getName() {
		return "Metro";
	}

	@Override
	public Color getBackground() {
		return Color.decode("#f2f2ee");
	}

	@Override
	public Color getGrid() {
		return Color.decode("#e6e6e0");
	}

	@Override
	public Color getText() {
		return INK;
	}

	@Override
	public Color getTextDim() {
		return Color.decode("#555555");
	}

	@Override
	public void drawStation(Graphics2D g, double x, double y, double size, boolean full) {
		double r = size / 2;
		g.setColor(full ? FULL : Color.WHITE);
		g.fill(new Ellipse2D.Double(x - r, y - r, size, size));
		g.setColor(INK);
		g.setStroke(new BasicStroke((float) Math.max(3.0, size * 0.14)));
		g.draw(new Ellipse2D.Double(x - r, y - r, size, size));
	}

	@Override
	public void drawTrain(Graphics2D g, double length, double width, boolean stopped) {
		double half = length / 2;
		double w = width / 2;
		int cars = 3;
		double gap = length * 0.04;
		double carLength = (length - gap * (cars - 1)) / cars;

		for (int i = 0; i < cars; i++) {
			double carX = -half + i * (carLength + gap);
			g.setColor(stopped ? Color.decode("#666666") : INK);
			g.fill(new RoundRectangle2D.Double(carX, -w, carLength, width, 3, 3));
			// two windows per car
			g.setColor(WINDOW);
			double windowSize = Math.min(carLength * 0.25, width * 0.4);
			g.fill(new Rectangle2D.Double(carX + carLength * 0.15, -windowSize / 2, windowSize, windowSize));
			g.fill(new Rectangle2D.Double(carX + carLength * 0.6, -windowSize / 2, windowSize, windowSize));
		}
	}

	@Override
	public void drawTrack(Graphics2D g, double x1, double y1, double x2, double y2, double scale) {
		g.setColor(LINE);
		g.setStroke(new BasicStroke((float) Math.max(4.0, scale * 0.9),
				BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		g.draw(new Line2D.Double(x1, y1, x2, y2));
	}
}
