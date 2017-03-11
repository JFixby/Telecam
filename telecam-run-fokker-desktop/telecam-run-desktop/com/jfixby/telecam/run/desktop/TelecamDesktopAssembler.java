
package com.jfixby.telecam.run.desktop;

import java.io.IOException;

import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.jfixby.r3.api.EngineParams;
import com.jfixby.r3.api.EngineParams.Assets;
import com.jfixby.r3.api.EngineParams.Settings;
import com.jfixby.r3.api.RedTriplane;
import com.jfixby.r3.api.shader.R3Shader;
import com.jfixby.r3.api.ui.UI;
import com.jfixby.r3.api.ui.FokkerUIManager;
import com.jfixby.r3.api.ui.unit.layer.LayerUtils;
import com.jfixby.r3.collide.RedCollisionsAlgebra;
import com.jfixby.r3.engine.core.Fokker;
import com.jfixby.r3.engine.core.unit.layers.RedLayerUtils;
import com.jfixby.r3.engine.core.unit.shader.R3FokkerShader;
import com.jfixby.r3.ext.api.scene2d.Scene2D;
import com.jfixby.r3.ext.api.text.R3Text;
import com.jfixby.r3.ext.text.red.RedTriplaneText;
import com.jfixby.r3.fokker.api.FokkerEngineAssembler;
import com.jfixby.r3.fokker.api.FokkerEngineParams;
import com.jfixby.r3.fokker.api.assets.FokkerTextureLoader;
import com.jfixby.r3.fokker.api.unit.UnitsSpawner;
import com.jfixby.r3.fokker.assets.RedFokkerTextureLoader;
import com.jfixby.r3.fokker.backend.RedUnitSpawner;
import com.jfixby.r3.fokker.filesystem.assets.GdxFileSystem;
import com.jfixby.r3.fokker.fs.AssetsInfo;
import com.jfixby.r3.ui.RedUIManager;
import com.jfixby.rana.api.asset.AssetsManager;
import com.jfixby.rana.api.asset.AssetsManagerFlags;
import com.jfixby.rana.api.pkg.ResourcesManager;
import com.jfixby.red.engine.core.resources.RedAssetsManager;
import com.jfixby.red.engine.scene2d.RedScene2D;
import com.jfixby.red.triplane.resources.fsbased.RedResourcesManager;
import com.jfixby.red.triplane.resources.fsbased.RedResourcesManagerSpecs;
import com.jfixby.scarabei.api.collisions.Collisions;
import com.jfixby.scarabei.api.file.File;
import com.jfixby.scarabei.api.file.FileSystemSandBox;
import com.jfixby.scarabei.api.file.LocalFileSystem;
import com.jfixby.scarabei.api.file.cache.FileCache;
import com.jfixby.scarabei.api.java.gc.GCFisher;
import com.jfixby.scarabei.api.sys.Sys;
import com.jfixby.scarabei.api.sys.settings.ExecutionMode;
import com.jfixby.scarabei.api.sys.settings.SystemSettings;
import com.jfixby.scarabei.red.desktop.filesystem.win.WinFileSystem;
import com.jfixby.scarabei.red.filesystem.cache.RedFileCache;
import com.jfixby.scarabei.red.filesystem.sandbox.RedFileSystemSandBox;
import com.jfixby.scarabei.red.filesystem.virtual.InMemoryFileSystem;
import com.jfixby.scarabei.red.java.gc.RedGCFisher;
//import com.jfixby.telecam.game.TelecamTheGame;
import com.jfixby.telecam.game.TelecamCore;

public class TelecamDesktopAssembler implements FokkerEngineAssembler {

	@Override
	public void assembleEngine () {

		LocalFileSystem.installComponent(new WinFileSystem());

		try {
			this.installResources();
		} catch (final IOException e) {
			e.printStackTrace();
		}

		Scene2D.installComponent(new RedScene2D());
		R3Text.installComponent(new RedTriplaneText());
		R3Shader.installComponent(new R3FokkerShader());

		// FileSystemPacker.installComponent(new RedFileSystemPacker());

		FileSystemSandBox.installComponent(new RedFileSystemSandBox());

		// String java_path_cache = "D:\\[DATA]\\[RED-ASSETS]\\cache";
		// File cache_path = LocalFileSystem.newFile(java_path_cache);

		// VirtualFileSystem vfs = new VirtualFileSystem();
		// cache_path = vfs;
		LayerUtils.installComponent(new RedLayerUtils());
		FileCache.installComponent(new RedFileCache());

		FokkerTextureLoader.installComponent(new RedFokkerTextureLoader());
		AssetsManager.installComponent(new RedAssetsManager());
		FokkerTextureLoader.register();

		ResourcesManager.registerPackageReader(Scene2D.getPackageReader());
		ResourcesManager.registerPackageReader(R3Text.getTTFFontPackageReader());
		ResourcesManager.registerPackageReader(R3Text.getStringsPackageReader());
		ResourcesManager.registerPackageReader(R3Shader.getPackageReader());

		final RedUIManager telecam_ui_starter = new RedUIManager();
		FokkerUIManager.installComponent(telecam_ui_starter);
		UI.installComponent(telecam_ui_starter);
		RedTriplane.setGameStarter(new TelecamCore());

		Collisions.installComponent(new RedCollisionsAlgebra());
		RedTriplane.installComponent(new Fokker());

		SystemSettings.setExecutionMode(ExecutionMode.EARLY_DEVELOPMENT);
		SystemSettings.setFlag(Settings.PrintLogMessageOnMissingSprite, true);
		SystemSettings.setFlag(Settings.ExitOnMissingSprite, false);
		SystemSettings.setFlag(Settings.AllowMissingRaster, true);
		SystemSettings.setFlag(AssetsManager.UseAssetSandBox, false);

		SystemSettings.setFlag(AssetsManager.ReportUnusedAssets, false);
		SystemSettings.setFlag(Settings.DisableLogo, true);
		SystemSettings.setStringParameter(FokkerEngineParams.TextureFilter.Mag, TextureFilter.Nearest + "");
		SystemSettings.setStringParameter(FokkerEngineParams.TextureFilter.Min, TextureFilter.Nearest + "");
		SystemSettings.setFlag(Settings.DisableLogo, true);
		SystemSettings.setFlag(AssetsManagerFlags.AutoresolveDependencies, true);
		SystemSettings.setFlag(R3Text.RenderRasterStrings, true);
		SystemSettings.setStringParameter(Assets.DefaultFont, "Arial");
		SystemSettings.setLongParameter(Assets.DEFAULT_LOGO_FADE_TIME, 2000L);
		SystemSettings.setStringParameter(Assets.CLEAR_SCREEN_COLOR_ARGB, "#00000000");
		SystemSettings.setLongParameter(GCFisher.DefaultBaitSize, 1 * 1024 * 1024);

		UnitsSpawner.installComponent(new RedUnitSpawner());
		// /-----------------------------------------

		// ImageProcessing.installComponent(new DesktopImageProcessing());
		// ImageGWT.installComponent(new RedImageGWT());
		// JsonTest.test();
		GCFisher.installComponent(new RedGCFisher());
		// final BaitInfo bait_info = GCFisher.throwBait();
// L.d("throw GC bait", bait_info);
	}

	private void installResources () throws IOException {
		// File dev_assets_home =
		// LocalFileSystem.newFile(TelecamAssetsConfig.PACKED_ASSETS_HOME);

		final File dev_assets_home = LocalFileSystem.ApplicationHome().parent().child("telecam-assets").child("content");

		SystemSettings.setStringParameter(Assets.ASSET_INFO_TAG, "" + dev_assets_home);
		this.printAssetsInfo(dev_assets_home);

		final RedResourcesManagerSpecs specs = new RedResourcesManagerSpecs();
		final RedResourcesManager res_manager = new RedResourcesManager(specs);
		ResourcesManager.installComponent(res_manager);

		// dev_assets_home = preload(dev_assets_home);
		final GdxFileSystem gdx = new GdxFileSystem();
		{
			final File bank_folder = dev_assets_home.child("bank-telecam");

			res_manager.findAndInstallResources(bank_folder);
		}
		{
			final File bank_folder = dev_assets_home.child("bank-r3");

			res_manager.findAndInstallResources(bank_folder);
		}

	}

	private File preload (File dev_assets_home) {
		final InMemoryFileSystem virtualFS = new InMemoryFileSystem();
		try {
			virtualFS.copyFolderContentsToFolder(dev_assets_home, virtualFS.ROOT());
			dev_assets_home = virtualFS.ROOT();
		} catch (final IOException e) {
			e.printStackTrace();
			Sys.exit();
		}
		return dev_assets_home;
	}

	private void printAssetsInfo (final File dev_assets_home) throws IOException {

		final File assets_file = dev_assets_home.child(AssetsInfo.FILE_NAME);
		// String super_file = fh.file().getAbsolutePath();
		if (!assets_file.exists()) {
			return;
		}
		assets_file.checkExists();

		AssetsInfo info;
		try {
			info = assets_file.readData(AssetsInfo.class);
		} catch (final IOException e) {
			e.printStackTrace();
			return;
		}

		SystemSettings.setStringParameter(EngineParams.Assets.ASSET_INFO_TAG, info.toString());

		info.print();
	}

}
