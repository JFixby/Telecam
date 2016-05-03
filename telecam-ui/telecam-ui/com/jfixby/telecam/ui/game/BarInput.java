package com.jfixby.telecam.ui.game;

import com.jfixby.cmns.api.geometry.CanvasPosition;
import com.jfixby.r3.api.ui.unit.CanvasPositionable;
import com.jfixby.r3.api.ui.unit.ComponentsFactory;
import com.jfixby.r3.api.ui.unit.LayerBasedComponent;
import com.jfixby.r3.api.ui.unit.layer.Layer;

public class BarInput implements LayerBasedComponent, CanvasPositionable {

	private ComponentsFactory components_factory;
	private Layer root;

	public BarInput(ComponentsFactory components_factory, InputBar inputBar) {
		this.components_factory = components_factory;
		this.root = components_factory.newLayer();
	}

	@Override
	public void setPosition(CanvasPosition position) {
	}

	@Override
	public void setPositionXY(double position_x, double position_y) {
	}

	@Override
	public Layer getRoot() {
		return root;
	}

}
