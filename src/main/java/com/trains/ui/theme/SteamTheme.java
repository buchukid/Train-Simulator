package com.trains.ui.theme;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

/** Brick station with a pitched roof and a clock, and a black steam engine trailing smoke. */
public class SteamTheme implements Theme {

	private static final Color BRICK = Color.decode("#a0442c");
	private static final Color BRICK_FULL = Color.decode("#d9472b");
	private static final Color MORTAR = Color.decode("#7a3020");
	private static final Color ROOF = Color.decode("#3b2a20");
	private static final Color CLOCK = Color.decode("#f3e6c8");
	private static final Color ENGINE = Color.decode("#1d1d1d");
	private static final Color BRASS = Color.decode("#b08d57");
	private static final Color CAB_LIGHT = Color.decode("#ffd27a");
	private static final Color SMOKE = new Color(207, 207, 207);

	@Override
	public String getName() {
		return "Steam";
	}

	@Override
	public Color getBackground() {
		return Color.decode("#2a2118");
	}

	@Override
	public Color getGrid() {
		return Color.decode("#32281e");
	}

	@Override
	public Color getText() {
		return CLOCK;
	}

	@Override
	public Color getTextDim() {
		return Color.decode("#b8a88f");
	}

	@Override
	public void drawStation(Graphics2D g, double x, double y, double size, boolean full) {
		double half = size / 2;

		// brick walls with mortar lines
		g.setColor(full ? BRICK_FULL : BRICK);
		g.fill(new Rectangle2D.Double(x - half, y - half, size, size));
		g.setColor(MORTAR);
		g.setStroke(new BasicStroke(1f));
		for (int row = 1; row < 4; row++) {
			double rowY = y - half + size * row / 4;
			g.draw(new Line2D.Double(x - half, rowY, x + half, rowY));
		}

		// pitched roof across the top half, seen from above
		Path2D roof = new Path2D.Double();
		roof.moveTo(x - half - size * 0.1, y - half + size * 0.4);
		roof.lineTo(x, y - half - size * 0.15);
		roof.lineTo(x + half + size * 0.1, y - half + size * 0.4);
		roof.closePath();
		g.setColor(ROOF);
		g.fill(roof);

		// clock on the roof
		double r = size * 0.14;
		g.setColor(CLOCK);
		g.fill(new Ellipse2D.Double(x - r, y - half + size * 0.12 - r, r * 2, r * 2));
		g.setColor(Color.BLACK);
		double clockY = y - half + size * 0.12;
		g.draw(new Line2D.Double(x, clockY, x, clockY - r * 0.7));
		g.draw(new Line2D.Double(x, clockY, x + r * 0.5, clockY));
	}

	@Override
	public void drawTrain(Graphics2D g, double length, double width, boolean stopped) {
		double half = length / 2;
		double w = width / 2;

		// smoke puffs drift behind the chimney while moving
		if (!stopped) {
			for (int i = 0; i < 3; i++) {
				double r = w * (0.35 + i * 0.2);
				g.setColor(new Color(SMOKE.getRed(), SMOKE.getGreen(), SMOKE.getBlue(), 130 - i * 40));
				g.fill(new Ellipse2D.Double(half * 0.3 - length * 0.22 * (i + 1) - r, -r, r * 2, r * 2));
			}
		}

		// cab at the back with a lit window
		g.setColor(ENGINE);
		g.fill(new Rectangle2D.Double(-half, -w, length * 0.3, width));
		g.setColor(CAB_LIGHT);
		g.fill(new Rectangle2D.Double(-half + length * 0.06, -w * 0.5, length * 0.16, w));

		// round boiler with a brass band
		g.setColor(ENGINE);
		g.fill(new RoundRectangle2D.Double(-half + length * 0.3, -w * 0.8, length * 0.7, width * 0.8, w, w));
		g.setColor(BRASS);
		g.fill(new Rectangle2D.Double(-half + length * 0.55, -w * 0.8, length * 0.05, width * 0.8));

		// thin brass outline so the black engine still shows up on the dark background
		g.setColor(BRASS);
		g.setStroke(new BasicStroke(1f));
		g.draw(new Rectangle2D.Double(-half, -w, length * 0.3, width));
		g.draw(new RoundRectangle2D.Double(-half + length * 0.3, -w * 0.8, length * 0.7, width * 0.8, w, w));

		// chimney near the front
		g.setColor(BRASS);
		double r = w * 0.45;
		g.fill(new Ellipse2D.Double(half * 0.3 - r, -r, r * 2, r * 2));
		g.setColor(Color.BLACK);
		g.fill(new Ellipse2D.Double(half * 0.3 - r * 0.6, -r * 0.6, r * 1.2, r * 1.2));
	}

	@Override
	public void drawTrack(Graphics2D g, double x1, double y1, double x2, double y2, double scale) {
		double dx = x2 - x1;
		double dy = y2 - y1;
		double length = Math.hypot(dx, dy);
		if (length == 0) {
			return;
		}
		double gauge = Math.max(2.0, scale * 0.35);
		// unit vector along the track and its perpendicular
		double ux = dx / length;
		double uy = dy / length;
		double px = -uy;
		double py = ux;

		// wooden sleepers across the track
		g.setColor(Color.decode("#5a4a3a"));
		g.setStroke(new BasicStroke((float) Math.max(1.5, scale * 0.25)));
		double spacing = Math.max(6.0, scale * 1.2);
		for (double d = spacing / 2; d < length; d += spacing) {
			double cx = x1 + ux * d;
			double cy = y1 + uy * d;
			g.draw(new Line2D.Double(cx + px * gauge * 1.6, cy + py * gauge * 1.6,
					cx - px * gauge * 1.6, cy - py * gauge * 1.6));
		}

		// two steel rails
		g.setColor(Color.decode("#a89a88"));
		g.setStroke(new BasicStroke(1.5f));
		g.draw(new Line2D.Double(x1 + px * gauge, y1 + py * gauge, x2 + px * gauge, y2 + py * gauge));
		g.draw(new Line2D.Double(x1 - px * gauge, y1 - py * gauge, x2 - px * gauge, y2 - py * gauge));
	}
}
