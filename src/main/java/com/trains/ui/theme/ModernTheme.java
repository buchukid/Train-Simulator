package com.trains.ui.theme;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.awt.geom.RoundRectangle2D;

/** Glass platform with a canopy, and a sleek green electric train with a headlight. */
public class ModernTheme implements Theme {

	private static final Color GLASS = new Color(95, 179, 230, 110);
	private static final Color GLASS_EDGE = Color.decode("#9fd6ff");
	private static final Color CANOPY = Color.decode("#dfe7ef");
	private static final Color FLOOR = Color.decode("#3a4659");
	private static final Color FULL = Color.decode("#d95f5f");
	private static final Color BODY = Color.decode("#2fbf71");
	private static final Color BODY_STOPPED = Color.decode("#8fa3a0");
	private static final Color WINDOW = Color.decode("#bfe9ff");
	private static final Color HEADLIGHT = new Color(255, 248, 192, 70);

	@Override
	public String getName() {
		return "Modern";
	}

	@Override
	public Color getBackground() {
		return Color.decode("#141c30");
	}

	@Override
	public Color getGrid() {
		return Color.decode("#1d2740");
	}

	@Override
	public Color getText() {
		return Color.decode("#e6edf3");
	}

	@Override
	public Color getTextDim() {
		return Color.decode("#9aa5b1");
	}

	@Override
	public void drawStation(Graphics2D g, double x, double y, double size, boolean full) {
		double half = size / 2;

		// platform floor
		g.setColor(full ? FULL : FLOOR);
		g.fill(new RoundRectangle2D.Double(x - half, y - half, size, size, size / 4, size / 4));

		// glass shelter with two panel lines
		double inset = size * 0.15;
		g.setColor(GLASS);
		g.fill(new RoundRectangle2D.Double(x - half + inset, y - half + inset, size - inset * 2, size - inset * 2, 4, 4));
		g.setColor(GLASS_EDGE);
		g.draw(new RoundRectangle2D.Double(x - half + inset, y - half + inset, size - inset * 2, size - inset * 2, 4, 4));
		g.drawLine((int) (x - size / 6), (int) (y - half + inset), (int) (x - size / 6), (int) (y + half - inset));
		g.drawLine((int) (x + size / 6), (int) (y - half + inset), (int) (x + size / 6), (int) (y + half - inset));

		// canopy bar across the top
		g.setColor(CANOPY);
		g.fill(new RoundRectangle2D.Double(x - half - size * 0.1, y - half - size * 0.08, size * 1.2, size * 0.18, 6, 6));
	}

	@Override
	public void drawTrain(Graphics2D g, double length, double width, boolean stopped) {
		double half = length / 2;
		double w = width / 2;

		// headlight beam in front, only while moving
		if (!stopped) {
			g.setColor(HEADLIGHT);
			Path2D beam = new Path2D.Double();
			beam.moveTo(half, 0);
			beam.lineTo(half + length * 0.5, -width * 0.6);
			beam.lineTo(half + length * 0.5, width * 0.6);
			beam.closePath();
			g.fill(beam);
		}

		// body: a carriage plus a front car with a rounded nose
		g.setColor(stopped ? BODY_STOPPED : BODY);
		g.fill(new RoundRectangle2D.Double(-half, -w, length * 0.45, width, w, w));
		Path2D front = new Path2D.Double();
		front.moveTo(-half + length * 0.48, -w);
		front.lineTo(half - w, -w);
		front.quadTo(half, -w, half, 0);
		front.quadTo(half, w, half - w, w);
		front.lineTo(-half + length * 0.48, w);
		front.closePath();
		g.fill(front);

		// window strips
		g.setColor(WINDOW);
		g.fill(new RoundRectangle2D.Double(-half + length * 0.05, -w * 0.45, length * 0.35, w * 0.9, 3, 3));
		g.fill(new RoundRectangle2D.Double(-half + length * 0.53, -w * 0.45, length * 0.3, w * 0.9, 3, 3));
	}
}
