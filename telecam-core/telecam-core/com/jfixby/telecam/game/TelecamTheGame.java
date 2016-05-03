
package com.jfixby.telecam.game;

import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.cmns.api.assets.Names;
import com.jfixby.cmns.api.collections.Collections;
import com.jfixby.cmns.api.collections.List;
import com.jfixby.cmns.api.sys.settings.ExecutionMode;
import com.jfixby.cmns.api.sys.settings.SystemSettings;
import com.jfixby.r3.api.RedTriplaneParams;
import com.jfixby.r3.api.game.GameLogicComponent;
import com.jfixby.r3.api.game.LoadTask;
import com.jfixby.r3.api.ui.GameUI;

public class TelecamTheGame implements GameLogicComponent {
	private long fade_time = 1500;

	public static final boolean TEST_TEXT = !true;
	public static final boolean TEST_SHADER = !true;

	public static final AssetID game_ui_unit_id = Names.newAssetID("com.jfixby.telecam.ui.game.UI");
// public static final AssetID loader_ui_unit_id = Names.newAssetID("com.jfixby.telecam.ui.load.TelecamLoader");

	@Override
	public void startGame () {
		this.fade_time = SystemSettings.getLongParameter(RedTriplaneParams.DEFAULT_LOGO_FADE_TIME);
		if (SystemSettings.executionModeCovers(ExecutionMode.EARLY_DEVELOPMENT)) {
			this.fade_time = 10;
		}

		if (TEST_TEXT) {
			GameUI.switchToGameUI(Names.newAssetID("com.jfixby.r3.test.text.TextTest"));
			return;
		}

		if (TEST_SHADER) {
			GameUI.switchToGameUI(Names.newAssetID("com.jfixby.r3.test.shader.ShaderTest"));
			return;
		}

		GameUI.switchToGameUI(game_ui_unit_id);
// GameUI.pushFadeIn(TelecamTheGame.this.fade_time);
		GameUI.allowUserInput();

	}

	private LoadTask buildTask () {
		final List<AssetID> list = Collections.newList();

		for (int i = 0; i < 20; i++) {

			final String id = ((1001 + i) + "").substring(1, 4);
			final AssetID toLoad = Names.newAssetID("com.jfixby.telecam.scene-" + id + ".psd");
			list.add(toLoad);
		}
// list.add(game_ui_unit_id);
		list.add(inputBarSceneID);

		return GameUI.prepareLoadGameUITask(list);
	}

	public static final AssetID inputBarSceneID = Names.newAssetID("com.jfixby.telecam.InputBar.psd");
}
