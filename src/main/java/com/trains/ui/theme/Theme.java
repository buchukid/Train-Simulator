package com.trains.ui.theme;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * A visual skin for the simulation (Strategy pattern).
 * <p>
 * SimulationView works out where things go on screen and then hands the actual drawing to the
 * current Theme, so the whole look can be swapped at runtime without SimulationView changing.
 * </p>
 */
public interface Theme {

	/** @return the name shown when switching themes */
	String getName();

	Color getBackground();

	Color getGrid();

	Color getText();

	Color getTextDim();

	/**
	 * Draws a station centred on (x, y).
	 * @param size the station's width in pixels, already scaled for the current zoom
	 * @param full whether the station's cargo hold is full
	 */
	void drawStation(Graphics2D g, double x, double y, double size, boolean full);

	/**
	 * Draws a train centred on the origin, facing along +x.
	 * <p>
	 * The caller has already translated and rotated the graphics, so a theme only ever draws one
	 * upright train and never has to deal with angles itself.
	 * </p>
	 */
	void drawTrain(Graphics2D g, double length, double width, boolean stopped);

	/**
	 * Draws one section of track. Themes can override this, the default is a plain line.
	 */
	default void drawTrack(Graphics2D g, double x1, double y1, double x2, double y2, double scale) {
		g.setColor(Color.decode("#7d8799"));
		g.setStroke(new BasicStroke((float) Math.max(2.0, scale * 0.45),
				BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		g.drawLine((int) Math.round(x1), (int) Math.round(y1), (int) Math.round(x2), (int) Math.round(y2));
	}
}
