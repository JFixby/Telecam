
package com.jfixby.telecam.ui;

import com.jfixby.cmns.api.color.Colors;
import com.jfixby.cmns.api.geometry.CanvasPosition;
import com.jfixby.cmns.api.geometry.Geometry;
import com.jfixby.cmns.api.geometry.ORIGIN_RELATIVE_HORIZONTAL;
import com.jfixby.cmns.api.geometry.ORIGIN_RELATIVE_VERTICAL;
import com.jfixby.cmns.api.geometry.Rectangle;
import com.jfixby.cmns.api.geometry.RectangleCorner;
import com.jfixby.cmns.api.log.L;
import com.jfixby.r3.api.ui.unit.geometry.GeometryComponentsFactory;
import com.jfixby.r3.api.ui.unit.geometry.RectangleComponent;
import com.jfixby.r3.api.ui.unit.layer.Layer;
import com.jfixby.r3.api.ui.unit.raster.Raster;

public class BackgroundGray {
	private Raster gray;
	private final UserInputBar master;
	private Layer root;
	private RectangleComponent blinker;
	private final CanvasPosition center = Geometry.newCanvasPosition();
	private double grayMaxHeight;

	public BackgroundGray (final UserInputBar userInputBar) {
		this.master = userInputBar;
	}

	public void setup (final Layer component_root) {
		this.root = component_root;
		this.gray = component_root.findComponent();
		this.root.detatchComponent(this.gray);
		final GeometryComponentsFactory factory = this.root.getComponentsFactory().getGeometryDepartment();
		this.blinker = factory.newRectangle();
		this.root.attachComponent(this.blinker);
		this.blinker.setFillColor(Colors.WHITE());
		this.blinker.setBorderColor(Colors.WHITE());
		this.root.attachComponent(this.gray);

		this.blinker.hide();
		this.blinker.setDebugRenderFlag(false);
		this.grayMaxHeight = this.gray.getHeight();
		this.gray.setOriginRelative(ORIGIN_RELATIVE_HORIZONTAL.CENTER, ORIGIN_RELATIVE_VERTICAL.BOTTOM);
	}

	public void updateScreen (final Rectangle viewport_update) {
		this.gray.setWidth(viewport_update.getWidth());
		this.gray.setPositionY(viewport_update.getHeight());
		this.gray.setPositionX(viewport_update.getWidth() / 2d);

		this.center.setXY(viewport_update.getWidth() / 2d, viewport_update.getHeight() - this.grayMaxHeight / 2);

		this.blinker.setOriginRelative(ORIGIN_RELATIVE_HORIZONTAL.CENTER, ORIGIN_RELATIVE_VERTICAL.CENTER);
		this.blinker.setPositionX(viewport_update.getWidth() / 2);
		this.blinker.setPositionY(viewport_update.getHeight() / 2);
		final double width = viewport_update.getWidth() * 2;
		final double height = viewport_update.getHeight() * 2;
		this.blinker.setSize(width, height);
	}

	public CanvasPosition getCenter () {
		return this.center;
	}

	public void hide () {
		this.root.hide();
	}

	public RectangleCorner getTopLeftCorner () {
		return this.gray.getTopLeftCorner();
	}

	public double getWidth () {
		return this.gray.getWidth();
	}

	public void show () {
		this.root.show();
	}

	public void setBlinkBegin () {
		this.blinker.show();
		this.blinker.setOpacity(1);
	}

	public void setShootProgress (final double f) {
		this.blinker.setOpacity(f);
	}

	public void setShootProgressDone () {
		this.blinker.hide();
		this.blinker.setOpacity(1f);
	}

	public void setBackgroundOpacity (final double f) {
		this.gray.setOpacity(f);
	}

	public void setRelativeHeight (final double d) {
		this.gray.setHeight(d * this.grayMaxHeight);
		L.d("gray", this.gray);
	}

}
