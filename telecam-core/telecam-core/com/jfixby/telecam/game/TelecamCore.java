
package com.jfixby.telecam.game;

import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.cmns.api.assets.Names;
import com.jfixby.r3.api.logic.BusinessLogicComponent;
import com.jfixby.r3.api.ui.UI;
import com.jfixby.r3.api.ui.UIAction;
import com.jfixby.telecam.api.TelecamUI;

public class TelecamCore implements BusinessLogicComponent {

	public static final AssetID unit_id = Names.newAssetID("com.jfixby.telecam.ui.TelecamUnit");
	public static final AssetID debug_unit_id = Names.newAssetID("com.jfixby.telecam.ui.TelecamDebugUnit");
	private final UIAction<TelecamUI> goPhoto = new UIAction<TelecamUI>() {

		@Override
		public void perform (final TelecamUI ui) {

			ui.goPhoto();
		}

	};

	@Override
	public void start () {

		UI.loadUnit(unit_id);
		UI.disableUserInput();
		UI.pushAction(this.goPhoto);

// final TelecamUI uiController = UI.getUIController();
// uiController.goPhoto();
// uiController.

	}
}
