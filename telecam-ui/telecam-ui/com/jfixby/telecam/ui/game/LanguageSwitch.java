package com.jfixby.telecam.ui.game;

import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.r3.api.ui.unit.input.MouseMovedEvent;
import com.jfixby.r3.api.ui.unit.input.TouchDownEvent;
import com.jfixby.r3.api.ui.unit.input.TouchDraggedEvent;
import com.jfixby.r3.api.ui.unit.input.TouchUpEvent;
import com.jfixby.r3.api.ui.unit.input.U_OnMouseInputEventListener;
import com.jfixby.telecam.ui.game.splash.SceneWrapper;

public class LanguageSwitch implements U_OnMouseInputEventListener {

	private LanguageManager languageManager;
	private InputBar bar;
	private GameMainUI main;
	private SceneContainer container;
	private AssetID key;
	private String button_name;
	private String locale_name;

	public LanguageSwitch(String button_name, String locale_name, AssetID key, LanguageManager languageManager) {
		this.languageManager = languageManager;
		bar = languageManager.getInputBar();
		main = bar.getGameMainUI();
		container = main.getSceneContainer();
		this.key = key;
		this.button_name = button_name;
		this.locale_name = locale_name;
		// container.
	}

	@Override
	public boolean onMouseMoved(MouseMovedEvent input_event) {
		return true;
	}

	@Override
	public boolean onTouchDown(TouchDownEvent input_event) {
		SceneWrapper wrapper = container.getCurrentSceneWrapper();
		wrapper.setupLanguage(locale_name);
		return true;
	}

	@Override
	public boolean onTouchUp(TouchUpEvent input_event) {
		return true;
	}

	@Override
	public boolean onTouchDragged(TouchDraggedEvent input_event) {
		return true;
	}

}
