package com.jfixby.telecam.ui.load;

import com.jfixby.cmns.api.color.Colors;
import com.jfixby.cmns.api.geometry.ORIGIN_RELATIVE_HORIZONTAL;
import com.jfixby.cmns.api.geometry.ORIGIN_RELATIVE_VERTICAL;
import com.jfixby.cmns.api.geometry.Rectangle;
import com.jfixby.cmns.api.math.FloatMath;
import com.jfixby.cmns.api.sys.Sys;
import com.jfixby.r3.api.ui.unit.ComponentsFactory;
import com.jfixby.r3.api.ui.unit.geometry.GeometryComponentsFactory;
import com.jfixby.r3.api.ui.unit.geometry.RectangleComponent;
import com.jfixby.r3.api.ui.unit.layer.Layer;
import com.jfixby.r3.api.ui.unit.update.OnUpdateListener;
import com.jfixby.r3.api.ui.unit.update.UnitClocks;

public class ProgressBar {

    private Layer root;
    private Rectangle fill_rectangle_shape;
    private Rectangle border_rectangle_shape;

    private OnUpdateListener onUpdateListener = new OnUpdateListener() {

	@Override
	public void onUpdate(UnitClocks unit_clocks) {
	    double angle = Sys.SystemTime().currentTimeMillis() / 1000d;
	    double w = 0.8d * FloatMath.sin(angle) * FloatMath.sin(angle);
	    fill_rectangle_shape.setWidth(w);
	}

    };

    public void setup(ComponentsFactory components_factory) {
	root = components_factory.newLayer();

	GeometryComponentsFactory geo_factory = components_factory.getGeometryDepartment();

	{
	    RectangleComponent rectangle_component = geo_factory.newRectangle();
	    rectangle_component.setBorderColor(Colors.GREEN().customize().setAlpha(1.0f));
	    rectangle_component.setFillColor(Colors.GREEN().customize().setAlpha(1.0f));
	    rectangle_component.setDebugRenderFlag(!true);

	    // root.attachComponent(rectangle_component);
	    fill_rectangle_shape = rectangle_component.getShape();
	    fill_rectangle_shape.setWidth(0.8d);
	    fill_rectangle_shape.setHeight(0.1d);
	    fill_rectangle_shape.setOriginRelative(ORIGIN_RELATIVE_HORIZONTAL.LEFT, ORIGIN_RELATIVE_VERTICAL.CENTER);
	    fill_rectangle_shape.setPosition(-0.4d, 0);
	    root.attachComponent(rectangle_component);
	}
	{
	    RectangleComponent rectangle_component = geo_factory.newRectangle();
	    rectangle_component.setBorderColor(Colors.WHITE().customize().setAlpha(1.0f));
	    rectangle_component.setFillColor(Colors.GREEN().customize().setAlpha(0.0f));
	    rectangle_component.setDebugRenderFlag(!true);

	    // root.attachComponent(rectangle_component);
	    border_rectangle_shape = rectangle_component.getShape();
	    border_rectangle_shape.setWidth(0.8d);
	    border_rectangle_shape.setHeight(0.1d);
	    border_rectangle_shape.setOriginRelative(ORIGIN_RELATIVE_HORIZONTAL.CENTER,
		    ORIGIN_RELATIVE_VERTICAL.CENTER);
	    border_rectangle_shape.setPosition(0, 0);
	    root.attachComponent(rectangle_component);
	}
	root.attachComponent(onUpdateListener);

    }

    public Layer getRootLayer() {
	return root;
    }

    public void reset() {
    }
}
