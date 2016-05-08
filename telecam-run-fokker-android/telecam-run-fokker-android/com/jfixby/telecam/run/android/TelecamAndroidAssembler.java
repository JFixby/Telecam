
package com.jfixby.telecam.run.android;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.jfixby.cmns.adopted.gdx.log.GdxLogger;
import com.jfixby.cmns.api.collisions.Collisions;
import com.jfixby.cmns.api.err.Err;
import com.jfixby.cmns.api.file.File;
import com.jfixby.cmns.api.file.FileSystemSandBox;
import com.jfixby.cmns.api.file.LocalFileSystem;
import com.jfixby.cmns.api.file.cache.FileCache;
import com.jfixby.cmns.api.io.IO;
import com.jfixby.cmns.api.java.ByteArray;
import com.jfixby.cmns.api.java.gc.BaitInfo;
import com.jfixby.cmns.api.java.gc.GCFisher;
import com.jfixby.cmns.api.log.L;
import com.jfixby.cmns.api.memory.MemoryManager;
import com.jfixby.cmns.api.sys.settings.ExecutionMode;
import com.jfixby.cmns.api.sys.settings.SystemSettings;
import com.jfixby.cmns.api.util.JUtils;
import com.jfixby.r3.api.RedTriplane;
import com.jfixby.r3.api.RedTriplaneParams;
import com.jfixby.r3.api.game.GameLogic;
import com.jfixby.r3.api.shader.R3Shader;
import com.jfixby.r3.api.ui.UI;
import com.jfixby.r3.api.ui.UIStarter;
import com.jfixby.r3.api.ui.unit.layer.LayerUtils;
import com.jfixby.r3.collide.RedCollisionsAlgebra;
import com.jfixby.r3.ext.api.font.R3Font;
import com.jfixby.r3.ext.api.scene2d.Scene2D;
import com.jfixby.r3.ext.api.text.R3Text;
import com.jfixby.r3.ext.font.gdx.ft.GdxR3Font;
import com.jfixby.r3.ext.text.red.RedTriplaneText;
import com.jfixby.r3.fokker.api.FokkerEngineAssembler;
import com.jfixby.r3.fokker.api.FokkerEngineParams;
import com.jfixby.r3.fokker.api.UnitsSpawner;
import com.jfixby.r3.fokker.api.assets.FokkerAtlasLoader;
import com.jfixby.r3.fokker.api.assets.FokkerRasterDataRegister;
import com.jfixby.r3.fokker.api.assets.FokkerTextureLoader;
import com.jfixby.r3.fokker.backend.RedUnitSpawner;
import com.jfixby.r3.ui.RedUIManager;
import com.jfixby.rana.api.asset.AssetsManager;
import com.jfixby.rana.api.asset.AssetsManagerFlags;
import com.jfixby.rana.api.pkg.ResourcesManager;
import com.jfixby.red.android.filesystem.AndroidFileSystem;
import com.jfixby.red.android.memory.AndroidMemoryManager;
import com.jfixby.red.engine.core.Fokker;
import com.jfixby.red.engine.core.resources.RedAssetsManager;
import com.jfixby.red.engine.core.unit.layers.RedLayerUtils;
import com.jfixby.red.engine.core.unit.shader.R3FokkerShader;
import com.jfixby.red.engine.scene2d.RedScene2D;
import com.jfixby.red.filesystem.cache.RedFileCache;
import com.jfixby.red.filesystem.sandbox.RedFileSystemSandBox;
import com.jfixby.red.java.gc.RedGCFisher;
import com.jfixby.red.triplane.resources.fsbased.FileSystemBasedResource;
import com.jfixby.red.triplane.resources.fsbased.RedResourcesManager;
import com.jfixby.redtriplane.fokker.assets.GwtAtlasReader;
import com.jfixby.redtriplane.fokker.assets.GwtTextureReader;
import com.jfixby.redtriplane.fokker.assets.RedFokkerRasterDataRegister;
import com.jfixby.redtriplane.fokker.filesystem.assets.fs.GdxAssetsFileSystem;
import com.jfixby.redtriplane.fokker.fs.AssetsInfo;
import com.jfixby.telecam.game.TelecamCore;

public class TelecamAndroidAssembler implements FokkerEngineAssembler {

	@Override
	public void assembleEngine () {
		L.installComponent(new GdxLogger());

		// LocalFileSystem.installComponent(new GdxAssetsFileSystem());
		LocalFileSystem.installComponent(new AndroidFileSystem());

		this.installResources();

		Scene2D.installComponent(new RedScene2D());
		R3Font.installComponent(new GdxR3Font());
		R3Text.installComponent(new RedTriplaneText());
		R3Shader.installComponent(new R3FokkerShader());

		// FileSystemPacker.installComponent(new Base64FileSystemPacker());

		FileSystemSandBox.installComponent(new RedFileSystemSandBox());

		// VirtualFileSystem vfs = new VirtualFileSystem();
		// cache_path = vfs;

		LayerUtils.installComponent(new RedLayerUtils());
		FileCache.installComponent(new RedFileCache());
		FokkerRasterDataRegister.installComponent(new RedFokkerRasterDataRegister());

		FokkerAtlasLoader.installComponent(new GwtAtlasReader());
		FokkerTextureLoader.installComponent(new GwtTextureReader());
		AssetsManager.installComponent(new RedAssetsManager());
		FokkerAtlasLoader.register();
		FokkerTextureLoader.register();

		ResourcesManager.registerPackageReader(Scene2D.getPackageReader());
		ResourcesManager.registerPackageReader(R3Font.getPackageReader());
		ResourcesManager.registerPackageReader(R3Text.getPackageReader());
		ResourcesManager.registerPackageReader(R3Shader.getPackageReader());

		final RedUIManager telecam_ui_starter = new RedUIManager();
		UIStarter.installComponent(telecam_ui_starter);
		UI.installComponent(telecam_ui_starter);
		GameLogic.installComponent(new TelecamCore());

		Collisions.installComponent(new RedCollisionsAlgebra());
		RedTriplane.installComponent(new Fokker());

		SystemSettings.setExecutionMode(ExecutionMode.EARLY_DEVELOPMENT);
		SystemSettings.setFlag(RedTriplaneParams.PrintLogMessageOnMissingSprite, true);
		SystemSettings.setFlag(RedTriplaneParams.ExitOnMissingSprite, false);
		SystemSettings.setFlag(RedTriplaneParams.AllowMissingRaster, true);
		SystemSettings.setFlag(AssetsManager.UseAssetSandBox, false);
		SystemSettings.setFlag(RedTriplaneParams.DisableLogo, true);
		SystemSettings.setStringParameter(FokkerEngineParams.TextureFilter.Mag, TextureFilter.Nearest + "");
		SystemSettings.setStringParameter(FokkerEngineParams.TextureFilter.Min, TextureFilter.Nearest + "");
		SystemSettings.setFlag(RedTriplaneParams.DisableLogo, true);

		L.d();
		SystemSettings.setFlag(AssetsManagerFlags.AutoresolveDependencies, true);
		SystemSettings.setFlag(R3Font.RenderRasterStrings, true);

		SystemSettings.setStringParameter(RedTriplaneParams.DefaultFont, "Arial");
		SystemSettings.setLongParameter(RedTriplaneParams.DEFAULT_LOGO_FADE_TIME, 2000L);
		SystemSettings.setStringParameter(RedTriplaneParams.CLEAR_SCREEN_COLOR_ARGB, "#00000000");
		SystemSettings.setLongParameter(GCFisher.DefaultBaitSize, 1 * 1024 * 1024);

		UnitsSpawner.installComponent(new RedUnitSpawner());

		MemoryManager.installComponent(new AndroidMemoryManager());
		L.d("Max heap size: ", MemoryManager.getMaxHeapSize() + "Mb");
		L.d("Recommended heap size: ", MemoryManager.getRecommendedHeapSize() + "Mb");

		GCFisher.installComponent(new RedGCFisher());
		final BaitInfo bait_info = GCFisher.throwBait();
		L.d("throw GC bait", bait_info);

	}

	private void installResources () {
		this.printAssetsInfo();
		final RedResourcesManager res_manager = new RedResourcesManager();
		ResourcesManager.installComponent(res_manager);

		final GdxAssetsFileSystem gdxFS = new GdxAssetsFileSystem();

		// File android_home = LocalFileSystem.ApplicationHome();
		// try {
		// gdxFS.copyFolderContentsToFolder(gdxFS.ROOT(), android_home);
		// } catch (IOException e) {
		// e.printStackTrace();
		// Sys.exit();
		// }
		// L.d("android home", android_home);
		//
		// android_home.listChildren().print();

		// L.d("print index");
		// gdxFS.getIndex().print();
		// File dev_assets_home = android_home;
		final File dev_assets_home = gdxFS.ROOT();

		{
			final File bank_folder = dev_assets_home.child("bank-telecam");
			if (bank_folder.exists()) {
				final FileSystemBasedResource resource = new FileSystemBasedResource(bank_folder);
				res_manager.installResource(resource);
			}
		}
		{
			final File bank_folder = dev_assets_home.child("bank-r3");

			if (bank_folder.exists()) {
				final FileSystemBasedResource resource = new FileSystemBasedResource(bank_folder);
				res_manager.installResource(resource);
			}
		}
	}

	private void printAssetsInfo () {
		final FileHandle fh = Gdx.files.internal(AssetsInfo.FILE_NAME);
		if (!fh.exists()) {
			Err.reportError(fh + " not found");
			return;
		}
		final ByteArray data = JUtils.newByteArray(fh.readBytes());
		AssetsInfo info;
		try {
			info = IO.deserialize(AssetsInfo.class, data);
		} catch (final IOException e) {
			e.printStackTrace();
			return;
		}
		info.print();
	}

}
