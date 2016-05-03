package com.jfixby.telecam.ui.game.splash;

import com.jfixby.cmns.api.floatn.Float2;
import com.jfixby.cmns.api.geometry.Geometry;
import com.jfixby.r3.api.ui.unit.ComponentsFactory;
import com.jfixby.r3.api.ui.unit.LayerBasedComponent;
import com.jfixby.r3.api.ui.unit.camera.Camera;
import com.jfixby.r3.api.ui.unit.camera.CameraSpecs;
import com.jfixby.r3.api.ui.unit.camera.SIMPLE_CAMERA_POLICY;
import com.jfixby.r3.api.ui.unit.input.MouseMovedEvent;
import com.jfixby.r3.api.ui.unit.input.TouchArea;
import com.jfixby.r3.api.ui.unit.input.TouchAreaSpecs;
import com.jfixby.r3.api.ui.unit.input.TouchDownEvent;
import com.jfixby.r3.api.ui.unit.input.TouchDraggedEvent;
import com.jfixby.r3.api.ui.unit.input.TouchUpEvent;
import com.jfixby.r3.api.ui.unit.input.U_OnMouseInputEventListener;
import com.jfixby.r3.api.ui.unit.layer.Layer;

public class SplasherInput implements LayerBasedComponent {
    final private Layer root;
    final private ComponentsFactory components_factory;

    private TouchArea touch;
    final Float2 begin_touch = Geometry.newFloat2();
    final Float2 end_touch = Geometry.newFloat2();
    final Float2 current_touch = Geometry.newFloat2();

    private final U_OnMouseInputEventListener listener = new U_OnMouseInputEventListener() {

	@Override
	public boolean onMouseMoved(MouseMovedEvent input_event) {
	    // L.d(input_event + "", input_event.getCanvasPosition());
	    return true;
	}

	@Override
	public boolean onTouchDown(TouchDownEvent input_event) {
	    int pointer = input_event.getPointerNumber();
	    if (pointer != 0) {
		return true;
	    }
	    begin_touch.set(input_event.getCanvasPosition());
	    current_touch.set(input_event.getCanvasPosition());
	    master.captureCursor();
	    double shift = (current_touch.getX() - begin_touch.getX());
	    double relative_shift = -shift / camera.getWidth();
	    master.updateCursor(relative_shift);
	    return true;
	}

	@Override
	public boolean onTouchUp(TouchUpEvent input_event) {

	    int pointer = input_event.getPointerNumber();
	    if (pointer != 0) {
		return true;
	    }

	    current_touch.set(input_event.getCanvasPosition());
	    end_touch.set(input_event.getCanvasPosition());
	    double shift = (current_touch.getX() - begin_touch.getX());
	    double relative_shift = -shift / camera.getWidth();
	    master.updateCursor(relative_shift);
	    master.releaseCursor();
	    return true;
	}

	@Override
	public boolean onTouchDragged(TouchDraggedEvent input_event) {
	    int pointer = input_event.getPointerNumber();
	    if (pointer != 0) {
		return true;
	    }
	    current_touch.set(input_event.getCanvasPosition());
	    double shift = (current_touch.getX() - begin_touch.getX());
	    double relative_shift = -shift / camera.getWidth();
	    master.updateCursor(relative_shift);
	    return true;
	}

    };
    private Camera camera;

    private Splasher master;

    public SplasherInput(ComponentsFactory components_factory, Splasher splasher) {
	this.components_factory = components_factory;
	this.master = splasher;
	this.root = components_factory.newLayer();
	root.setName("SplasherInput");
	CameraSpecs cs = components_factory.getCameraDepartment().newCameraSpecs();
	cs.setSimpleCameraPolicy(SIMPLE_CAMERA_POLICY.KEEP_ASPECT_RATIO_ON_SCREEN_RESIZE);
	camera = components_factory.getCameraDepartment().newCamera(cs);
	camera.setOriginRelative(0, 0);
	camera.setPosition(0, 0);
	camera.setSize(Splasher.width, Splasher.height);

	TouchAreaSpecs ts = components_factory.getUserInputDepartment().newTouchAreaSpecs();
	ts.setArea(0, 0, Splasher.width, Splasher.height);

	touch = components_factory.getUserInputDepartment().newTouchArea(ts);
	touch.setDebugRenderFlag(!true);
	touch.setInputListener(listener);
	touch.shape().setOriginRelative(0, 0);
	touch.shape().setPosition(0, 0);
	this.root.attachComponent(touch);
	camera.setDebugName("SplasherInput");
	root.setCamera(camera);

	// L.d("weird_camera", camera);

    }

    @Override
    public Layer getRoot() {
	return root;
    }

}
