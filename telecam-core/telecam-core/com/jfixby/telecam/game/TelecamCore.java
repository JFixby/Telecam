
package com.jfixby.telecam.game;

import com.jfixby.r3.api.logic.BusinessLogicComponent;
import com.jfixby.r3.api.ui.UI;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.assets.Names;

public class TelecamCore implements BusinessLogicComponent {

	public static final ID unit_id = Names.newID("com.jfixby.telecam.ui.TelecamUnit");
	public static final ID debug_unit_id = Names.newID("com.jfixby.telecam.ui.TelecamDebugUnit");

	@Override
	public void start () {

		UI.loadUnit(unit_id);

	}
}
