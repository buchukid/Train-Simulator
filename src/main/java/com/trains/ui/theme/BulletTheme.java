package com.trains.ui.theme;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

/** Wide modern terminal with a curved roof, and a long white bullet train with a blue stripe. */
public class BulletTheme implements Theme {

	private static final Color WALL = Color.decode("#e9edf2");
	private static final Color WALL_FULL = Color.decode("#f2b5a5");
	private static final Color ROOF_LIGHT = Color.decode("#d4dde7");
	private static final Color ROOF_DARK = Color.decode("#93a3b5");
	private static final Color GLASS = Color.decode("#5b8bd6");
	private static final Color BODY = Color.decode("#f8f9fb");
	private static final Color STRIPE = Color.decode("#1e5bd8");
	private static final Color COCKPIT = Color.decode("#1b2a44");

	@Override
	public String getName() {
		return "Bullet";
	}

	@Override
	public Color getBackground() {
		return Color.decode("#3d3358");
	}

	@Override
	public Color getGrid() {
		return Color.decode("#463c63");
	}

	@Override
	public Color getText() {
		return Color.WHITE;
	}

	@Override
	public Color getTextDim() {
		return Color.decode("#d0c8e6");
	}

	@Override
	public void drawStation(Graphics2D g, double x, double y, double size, boolean full) {
		// wider than it is tall, like a long terminal building
		double width = size * 1.5;
		double height = size;
		double left = x - width / 2;
		double top = y - height / 2;

		g.setColor(full ? WALL_FULL : WALL);
		g.fill(new Rectangle2D.Double(left, top, width, height));

		// curved roof shaded with a gradient so it looks rounded
		g.setPaint(new GradientPaint((float) x, (float) top, ROOF_LIGHT, (float) x, (float) (top + height * 0.5), ROOF_DARK));
		g.fill(new RoundRectangle2D.Double(left - size * 0.08, top - size * 0.08, width + size * 0.16, height * 0.55, size * 0.5, size * 0.5));

		// row of glass panels along the front
		g.setColor(GLASS);
		int panels = 4;
		double panelWidth = width / (panels * 1.5);
		for (int i = 0; i < panels; i++) {
			double panelX = left + panelWidth * 0.25 + i * panelWidth * 1.5;
			g.fill(new Rectangle2D.Double(panelX, top + height * 0.6, panelWidth, height * 0.3));
		}
	}

	@Override
	public void drawTrain(Graphics2D g, double length, double width, boolean stopped) {
		// a bullet train is longer and thinner than the other themes
		double half = length * 0.65;
		double w = width * 0.4;

		Path2D body = new Path2D.Double();
		body.moveTo(-half, -w);
		body.lineTo(half * 0.3, -w);
		body.quadTo(half, -w, half, 0); // long tapered nose
		body.quadTo(half, w, half * 0.3, w);
		body.lineTo(-half, w);
		body.closePath();
		g.setColor(stopped ? Color.decode("#c9ccd2") : BODY);
		g.fill(body);

		g.setColor(STRIPE);
		g.fill(new Rectangle2D.Double(-half, w * 0.2, half * 1.6, w * 0.35));

		g.setColor(COCKPIT);
		g.fill(new RoundRectangle2D.Double(half * 0.45, -w * 0.6, half * 0.3, w * 0.7, 3, 3));
	}
}
