package com.jfixby.telecam.ui.game;

import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.cmns.api.collections.Collections;
import com.jfixby.cmns.api.collections.Map;
import com.jfixby.cmns.api.collections.Mapping;
import com.jfixby.r3.api.ui.unit.input.InputComponent;

public class LanguageManager {

    private AssetID name_space;
    private InputBar inputBar;

    // private Map<AssetID, U_OnMouseInputEventListener> listeners =
    // Collections.newMap();

    public LanguageManager(Mapping<AssetID, InputComponent> listInputComponents, InputBar inputBar) {
	// listInputComponents.print("input");
	this.inputBar = inputBar;
	name_space = inputBar.InputBar_SceneID;
	Map<String, String> button_names = Collections.newMap();
	button_names.put("flag_ru", "русский");
	button_names.put("flag_en", "english");
	button_names.put("flag_it", "italiano");
	// List("flag_ru", "flag_en", "flag_it")

	for (int i = 0; i < button_names.size(); i++) {
	    String button_name = button_names.getKeyAt(i);
	    String locale_name = button_names.getValueAt(i);
	    AssetID key = name_space.child(button_name);

	    LanguageSwitch lang_switch = new LanguageSwitch(button_name, locale_name, key, this);
	    // listeners.put(key, lang_switch);

	    InputComponent button = listInputComponents.get(key);
	    // U_OnMouseInputEventListener listener = listeners.get(key);
	    button.setInputListener(lang_switch);
	}

    }

    public InputBar getInputBar() {
	return this.inputBar;
    }

}
