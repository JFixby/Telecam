
package com.jfixby.telecam.game;

import com.jfixby.r3.api.logic.GameStarter;
import com.jfixby.r3.api.ui.UI;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.assets.Names;

public class TelecamCore implements GameStarter {

	public static final ID unit_id = Names.newID("com.jfixby.telecam.ui.TelecamUnit");
	public static final ID debug_unit_id = Names.newID("com.jfixby.telecam.ui.TelecamDebugUnit");

	@Override
	public void onGameStart () {

		UI.loadUnit(unit_id);

	}
}
