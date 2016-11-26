
package com.jfixby.telecam.ui;

import com.jfixby.cmns.api.assets.ID;
import com.jfixby.cmns.api.assets.Names;

public class FontSettings {
	private final ID font_id = Names.newID("otf.Arcon-Rounded-Regular.Arcon-Rounded-Regular");

	public ID getName () {
		return this.font_id;
	}

	private final String requiredChars = "0123456789:°+-.,";

	public String getRequiredChars () {
		return this.requiredChars;
	}

	public float getMultiplier () {
		return 0.5f;
	}
}
