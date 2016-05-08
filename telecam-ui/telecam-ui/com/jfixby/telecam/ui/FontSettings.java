
package com.jfixby.telecam.ui;

import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.cmns.api.assets.Names;

public class FontSettings {
	private final AssetID font_id = Names.newAssetID("otf.Arcon-Rounded-Regular.Arcon-Rounded-Regular");

	public AssetID getName () {
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
