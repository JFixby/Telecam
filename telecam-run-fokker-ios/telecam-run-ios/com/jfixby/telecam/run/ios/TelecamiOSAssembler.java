package com.jfixby.telecam.run.ios;

import com.jfixby.cmns.adopted.gdx.GdxSimpleTriangulator;
import com.jfixby.cmns.adopted.gdx.base64.GdxBase64;
import com.jfixby.cmns.adopted.gdx.json.GdxJson;
import com.jfixby.cmns.adopted.gdx.log.GdxLogger;
import com.jfixby.cmns.api.assets.Names;
import com.jfixby.cmns.api.base64.Base64;
import com.jfixby.cmns.api.collisions.Collisions;
import com.jfixby.cmns.api.color.Colors;
import com.jfixby.cmns.api.file.File;
import com.jfixby.cmns.api.file.FileSystemSandBox;
import com.jfixby.cmns.api.file.cache.FileCache;
import com.jfixby.cmns.api.geometry.Geometry;
import com.jfixby.cmns.api.graphs.Graphs;
import com.jfixby.cmns.api.io.IO;
import com.jfixby.cmns.api.json.Json;
import com.jfixby.cmns.api.log.L;
import com.jfixby.cmns.api.math.SimpleTriangulator;
import com.jfixby.cmns.api.md5.MD5;
import com.jfixby.cmns.api.sys.ExecutionMode;
import com.jfixby.cmns.api.sys.Sys;
import com.jfixby.r3.api.RedTriplane;
import com.jfixby.r3.api.RedTriplaneFlags;
import com.jfixby.r3.api.game.GameLogic;
import com.jfixby.r3.api.ui.GameUI;
import com.jfixby.r3.api.ui.UIStarter;
import com.jfixby.r3.collide.RedCollisionsAlgebra;
import com.jfixby.r3.ext.api.scene2d.Scene2D;
import com.jfixby.r3.fokker.api.FokkerEngineAssembler;
import com.jfixby.r3.fokker.api.assets.FokkerAtlasLoader;
import com.jfixby.r3.fokker.api.assets.FokkerRasterDataRegister;
import com.jfixby.r3.fokker.api.assets.FokkerTextureLoader;
import com.jfixby.r3.ui.RedUIManager;
import com.jfixby.rana.api.asset.AssetsManager;
import com.jfixby.rana.api.asset.AssetsManagerFlags;
import com.jfixby.rana.api.pkg.ResourcesManager;
import com.jfixby.red.color.RedColors;
import com.jfixby.red.engine.core.Fokker;
import com.jfixby.red.engine.core.resources.RedAssetsManager;
import com.jfixby.red.engine.scene2d.RedScene2D;
import com.jfixby.red.filesystem.cache.RedFileCache;
import com.jfixby.red.filesystem.sandbox.RedFileSystemSandBox;
import com.jfixby.red.geometry.RedGeometry;
import com.jfixby.red.graphs.RedGraphs;
import com.jfixby.red.io.RedIO;
import com.jfixby.red.name.RedAssetsNamespace;
import com.jfixby.red.triplane.resources.fsbased.FileSystemBasedResource;
import com.jfixby.red.triplane.resources.fsbased.RedResourcesManager;
import com.jfixby.red.util.md5.AlpaeroMD5;
import com.jfixby.redtriplane.fokker.assets.GwtAtlasReader;
import com.jfixby.redtriplane.fokker.assets.GwtTextureReader;
import com.jfixby.redtriplane.fokker.assets.RedFokkerRasterDataRegister;
import com.jfixby.redtriplane.fokker.filesystem.assets.fs.GdxAssetsFileSystem;
import com.jfixby.telecam.game.TelecamTheGame;

public class TelecamiOSAssembler implements FokkerEngineAssembler {

	@Override
	public void assembleEngine() {
		L.installComponent(new GdxLogger());

		Names.installComponent(new RedAssetsNamespace());
		IO.installComponent(new RedIO());
		Graphs.installComponent(new RedGraphs());
		SimpleTriangulator.installComponent(new GdxSimpleTriangulator());
		Geometry.installComponent(new RedGeometry());
		Colors.installComponent(new RedColors());
		MD5.installComponent(new AlpaeroMD5());
		// GWTUnitsSpawner spawner = new GWTUnitsSpawner();
		{
		}
		// UnitsSpawner.installComponent(spawner);

		Json.installComponent(new GdxJson());
		Base64.installComponent(new GdxBase64());

		Scene2D.installComponent(new RedScene2D());

		// FileSystemPacker.installComponent(new Base64FileSystemPacker());

		FileSystemSandBox.installComponent(new RedFileSystemSandBox());

		// VirtualFileSystem vfs = new VirtualFileSystem();
		// cache_path = vfs;

		FileCache.installComponent(new RedFileCache());
		FokkerRasterDataRegister.installComponent(new RedFokkerRasterDataRegister());
		FokkerAtlasLoader.installComponent(new GwtAtlasReader());
		FokkerTextureLoader.installComponent(new GwtTextureReader());
		AssetsManager.installComponent(new RedAssetsManager());
		FokkerAtlasLoader.register();
		FokkerTextureLoader.register();

		installResources();

		// --
		{
			// PSDUnpacker.installComponent(new RedPSDUnpacker());
			// SpriteDecomposer.installComponent(new RedSpriteDecomposer());
			// ImageProcessing.installComponent(new FokkerImageProcessing());
			// TexturePacker.installComponent(new GdxTexturePacker());
		}

		RedUIManager telecam_ui_starter = new RedUIManager();
		UIStarter.installComponent(telecam_ui_starter);
		GameUI.installComponent(telecam_ui_starter);
		GameLogic.installComponent(new TelecamTheGame());

		// JBox2D box2d_j = new JBox2D(); //

		// Physics2DComponent r3_physics_j = new RedTriplanePhysics(box2d_j);

		// Physics2D.installComponent(r3_physics_j);

		// Box2D.installComponent(box2d);
		Collisions.installComponent(new RedCollisionsAlgebra());

		RedTriplane.installComponent(new Fokker());
		Sys.setExecutionMode(ExecutionMode.EARLY_DEVELOPMENT);
		Sys.setFlag(RedTriplaneFlags.PrintLogMessageOnMissingSprite, true);
		Sys.setFlag(RedTriplaneFlags.ExitOnMissingSprite, false);
		Sys.setFlag(RedTriplaneFlags.AllowMissingRaster, true);
		Sys.setFlag(AssetsManagerFlags.AutoresolveDependencies, true);

	}

	private void installResources() {
		// printAssetsInfo();
		RedResourcesManager res_manager = new RedResourcesManager();

		ResourcesManager.installComponent(res_manager);

		GdxAssetsFileSystem gdxFS = new GdxAssetsFileSystem();
		L.d("print index");
		gdxFS.getIndex().print();
		File home = gdxFS.ROOT();

		{
			FileSystemBasedResource resource = new FileSystemBasedResource(home.child("bank-europa"));
			res_manager.installResource(resource);
		}
		{
			FileSystemBasedResource resource = new FileSystemBasedResource(home.child("bank-florida"));
			res_manager.installResource(resource);
		}
	}

}
