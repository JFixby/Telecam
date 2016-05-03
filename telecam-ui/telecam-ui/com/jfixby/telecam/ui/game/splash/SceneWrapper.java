
package com.jfixby.telecam.ui.game.splash;

import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.cmns.api.assets.Names;
import com.jfixby.cmns.api.collections.Collection;
import com.jfixby.cmns.api.debug.Debug;
import com.jfixby.cmns.api.debug.DebugTimer;
import com.jfixby.cmns.api.err.Err;
import com.jfixby.cmns.api.log.L;
import com.jfixby.cmns.api.util.JUtils;
import com.jfixby.cmns.api.util.StateSwitcher;
import com.jfixby.r3.api.locale.LocalizedComponent;
import com.jfixby.r3.api.ui.UILoaderListener;
import com.jfixby.r3.api.ui.unit.ComponentsFactory;
import com.jfixby.r3.api.ui.unit.raster.Raster;
import com.jfixby.r3.ext.api.scene2d.Scene;
import com.jfixby.r3.ext.api.scene2d.Scene2D;
import com.jfixby.r3.ext.api.scene2d.Scene2DSpawningConfig;
import com.jfixby.rana.api.asset.AssetHandler;
import com.jfixby.rana.api.asset.AssetsConsumer;
import com.jfixby.rana.api.asset.AssetsManager;

public class SceneWrapper implements AssetsConsumer {

	private final Raster raster;
	private final AssetID splash_id;
	private final int index;
	private final UILoaderListener loader_listener = new UILoaderListener() {

		@Override
		public void onUILoaderDone () {
			SceneWrapper.this.activate();
		}

	};
	private final AssetID id_scene_i;
	private AssetHandler asset_handler;
	private Scene scene;
	private final StateSwitcher<ICON_SPLASHRER_STATE> state;
	private final Splasher master;
	private SceneWrapper previous;
	public static String locale_name = "русский";
	final DebugTimer timer = Debug.newTimer();

	private void activate () {

		this.state.expectState(ICON_SPLASHRER_STATE.LOADING);

		if (this.previous != null) {
			this.previous.deactivate();
		}
		this.previous = null;
		this.raster.setOpacity(0);
		this.master.onIconSplasherActivated(this);
		this.state.switchState(ICON_SPLASHRER_STATE.ACTIVE);

	}

	public SceneWrapper (final int i, final Raster splash, final Splasher master) {
		this.index = i;
		this.master = master;
		this.raster = splash;
		this.splash_id = splash.getAssetID();
		this.state = JUtils.newStateSwitcher(ICON_SPLASHRER_STATE.INACTIVE);

		this.id_scene_i = sceneID(this.index);
		this.deployScene();
	}

	public static AssetID sceneID (final int index) {
		final String replacement = ((1000 + index + 1) + "").substring(1, 1 + 3);
		final String scene_id = template.replaceAll("#", replacement);
		return Names.newAssetID(scene_id);

	}

	static final String template = "com.jfixby.telecam.scene-#.psd";
	public static final int FIRST_SCENE_INDEX = 2;
	public static final int LAST_SCENE_INDEX = 20;

	public void load (final SceneWrapper previous) {
		this.state.expectState(ICON_SPLASHRER_STATE.INACTIVE);

		L.d("splash_id", this.splash_id);
		L.d("         ", this.id_scene_i);

		if (previous != null) {
// GameUI.pushLoadAssetsTask(Collections.newList(this.id_scene_i), this.loader_listener);

			this.previous = previous;
			this.state.switchState(ICON_SPLASHRER_STATE.LOADING);
			this.activate();
		} else {
			this.state.switchState(ICON_SPLASHRER_STATE.LOADING);
			this.activate();
		}
	}

	private void deployScene () {
		Debug.checkTrue(this.scene == null);
		this.timer.reset();
		this.asset_handler = AssetsManager.obtainAsset(this.id_scene_i, this);
		if (this.asset_handler == null) {
			AssetsManager.printAllLoadedAssets();
			Err.reportError("Failed to load: " + this.id_scene_i + " asset not found");
		}
		Debug.checkNull("asset_handler: " + this.id_scene_i, this.asset_handler);
		final ComponentsFactory components_factory = this.raster.getComponentsFactory();
		this.timer.printTime("scene found in");
		this.timer.reset();
		final Scene2DSpawningConfig config = Scene2D.newSceneSpawningConfig();
		config.setStructureID(this.id_scene_i);
		config.setDefaultLocaleName(locale_name);
		config.setRenderDebugInfo(true);

		this.scene = Scene2D.spawnScene(components_factory, config);
		this.timer.printTime("scene deployed in");
	}

	public void deactivate () {
		this.state.expectState(ICON_SPLASHRER_STATE.ACTIVE);
		this.master.onIconSplasherDeactivated(this);
		this.raster.setOpacity(1);
// this.scene = null;
// AssetsManager.releaseAsset(this.asset_handler, this);
// this.asset_handler = null;
		this.state.switchState(ICON_SPLASHRER_STATE.INACTIVE);

	}

	public Scene getScene () {
		return this.scene;
	}

	public void setupLanguage (final String locale_name) {
		L.d("setup-language", locale_name);
		Debug.checkNull("locale_name", locale_name);
		Debug.checkEmpty("locale_name", locale_name);
		this.locale_name = locale_name;
		if (this.state.currentState() == ICON_SPLASHRER_STATE.ACTIVE) {
			final Collection<LocalizedComponent> list = this.scene.listLocalizedComponents();
			for (int i = 0; i < list.size(); i++) {
				final LocalizedComponent element = list.getElementAt(i);
				element.setLocaleName(locale_name);
			}
		}
	}

	public AssetID getSceneID () {
		return this.id_scene_i;
	}

	public AssetID getSplasherID () {
		return this.splash_id;
	}

}
