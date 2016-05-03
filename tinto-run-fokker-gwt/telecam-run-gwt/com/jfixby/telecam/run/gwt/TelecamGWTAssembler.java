package com.jfixby.telecam.run.gwt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.jfixby.cmns.adopted.gdx.GdxSimpleTriangulator;
import com.jfixby.cmns.adopted.gdx.base64.GdxBase64;
import com.jfixby.cmns.adopted.gdx.json.GdxJson;
import com.jfixby.cmns.adopted.gdx.log.GdxLogger;
import com.jfixby.cmns.api.angles.Angles;
import com.jfixby.cmns.api.assets.Names;
import com.jfixby.cmns.api.base64.Base64;
import com.jfixby.cmns.api.collections.Collections;
import com.jfixby.cmns.api.collisions.Collisions;
import com.jfixby.cmns.api.color.Colors;
import com.jfixby.cmns.api.debug.Debug;
import com.jfixby.cmns.api.err.Err;
import com.jfixby.cmns.api.file.File;
import com.jfixby.cmns.api.file.FileSystemSandBox;
import com.jfixby.cmns.api.file.cache.FileCache;
import com.jfixby.cmns.api.geometry.Geometry;
import com.jfixby.cmns.api.graphs.Graphs;
import com.jfixby.cmns.api.input.UserInput;
import com.jfixby.cmns.api.io.IO;
import com.jfixby.cmns.api.json.Json;
import com.jfixby.cmns.api.log.L;
import com.jfixby.cmns.api.math.FloatMath;
import com.jfixby.cmns.api.math.IntegerMath;
import com.jfixby.cmns.api.math.MathTools;
import com.jfixby.cmns.api.math.SimpleTriangulator;
import com.jfixby.cmns.api.md5.MD5;
import com.jfixby.cmns.api.sys.ExecutionMode;
import com.jfixby.cmns.api.sys.Sys;
import com.jfixby.cmns.api.util.JUtils;
import com.jfixby.r3.api.RedTriplane;
import com.jfixby.r3.api.RedTriplaneFlags;
import com.jfixby.r3.api.RedTriplaneParams;
import com.jfixby.r3.api.game.GameLogic;
import com.jfixby.r3.api.ui.GameUI;
import com.jfixby.r3.api.ui.UIStarter;
import com.jfixby.r3.api.ui.unit.layer.LayerUtils;
import com.jfixby.r3.collide.RedCollisionsAlgebra;
import com.jfixby.r3.ext.api.font.R3Font;
import com.jfixby.r3.ext.api.scene2d.Scene2D;
import com.jfixby.r3.ext.api.text.R3Text;
import com.jfixby.r3.ext.font.gdx.gwt.ft.GwtR3Font;
import com.jfixby.r3.ext.text.red.RedTriplaneText;
import com.jfixby.r3.fokker.api.FokkerEngineAssembler;
import com.jfixby.r3.fokker.api.UnitsSpawner;
import com.jfixby.r3.fokker.api.assets.FokkerAtlasLoader;
import com.jfixby.r3.fokker.api.assets.FokkerRasterDataRegister;
import com.jfixby.r3.fokker.api.assets.FokkerTextureLoader;
import com.jfixby.r3.fokker.gwt.unitsspawner.GWTUnitsSpawner;
import com.jfixby.r3.ui.RedUIManager;
import com.jfixby.rana.api.asset.AssetsManager;
import com.jfixby.rana.api.asset.AssetsManagerFlags;
import com.jfixby.rana.api.pkg.ResourcesManager;
import com.jfixby.red.color.RedColors;
import com.jfixby.red.debug.RedDebug;
import com.jfixby.red.engine.core.Fokker;
import com.jfixby.red.engine.core.resources.RedAssetsManager;
import com.jfixby.red.engine.core.unit.layers.RedLayerUtils;
import com.jfixby.red.engine.scene2d.RedScene2D;
import com.jfixby.red.err.RedError;
import com.jfixby.red.filesystem.cache.RedFileCache;
import com.jfixby.red.filesystem.sandbox.RedFileSystemSandBox;
import com.jfixby.red.geometry.RedGeometry;
import com.jfixby.red.graphs.RedGraphs;
import com.jfixby.red.gwt.collections.GwtCollections;
import com.jfixby.red.gwt.math.GwtFloatMath;
import com.jfixby.red.gwt.sys.GwtSystem;
import com.jfixby.red.input.RedInput;
import com.jfixby.red.io.RedIO;
import com.jfixby.red.math.RedAngles;
import com.jfixby.red.math.RedIntegerMath;
import com.jfixby.red.math.RedMathTools;
import com.jfixby.red.name.RedAssetsNamespace;
import com.jfixby.red.triplane.resources.fsbased.FileSystemBasedResource;
import com.jfixby.red.triplane.resources.fsbased.RedResourcesManager;
import com.jfixby.red.util.RedJUtils;
import com.jfixby.red.util.md5.AlpaeroMD5;
import com.jfixby.redtriplane.fokker.assets.GwtAtlasReader;
import com.jfixby.redtriplane.fokker.assets.GwtTextureReader;
import com.jfixby.redtriplane.fokker.assets.RedFokkerRasterDataRegister;
import com.jfixby.redtriplane.fokker.filesystem.assets.fs.GdxAssetsFileSystem;
import com.jfixby.redtriplane.fokker.fs.AssetsInfo;
import com.jfixby.telecam.game.TelecamTheGame;

public class TelecamGWTAssembler implements FokkerEngineAssembler {

    @Override
    public void assembleEngine() {
	L.installComponent(new GdxLogger());

	Err.installComponent(new RedError());
	Debug.installComponent(new RedDebug());
	Collections.installComponent(new GwtCollections());

	JUtils.installComponent(new RedJUtils());
	FloatMath.installComponent(new GwtFloatMath());
	Sys.installComponent(new GwtSystem());

	IntegerMath.installComponent(new RedIntegerMath());
	Names.installComponent(new RedAssetsNamespace());
	IO.installComponent(new RedIO());
	Graphs.installComponent(new RedGraphs());
	SimpleTriangulator.installComponent(new GdxSimpleTriangulator());
	Angles.installComponent(new RedAngles());

	UserInput.installComponent(new RedInput());

	RedGeometry geometry = new RedGeometry();
	Geometry.installComponent(geometry);
	Colors.installComponent(new RedColors());
	MathTools.installComponent(new RedMathTools());

	Json.installComponent(new GdxJson());
	Base64.installComponent(new GdxBase64());
	MD5.installComponent(new AlpaeroMD5());

	// LocalFileSystem.installComponent(new GdxAssetsFileSystem());

	installResources();

	Scene2D.installComponent(new RedScene2D());
	R3Font.installComponent(new GwtR3Font());
	R3Text.installComponent(new RedTriplaneText());

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

	RedUIManager telecam_ui_starter = new RedUIManager();
	UIStarter.installComponent(telecam_ui_starter);
	GameUI.installComponent(telecam_ui_starter);
	GameLogic.installComponent(new TelecamTheGame());

	Collisions.installComponent(new RedCollisionsAlgebra());

	RedTriplane.installComponent(new Fokker());

	Sys.setExecutionMode(ExecutionMode.EARLY_DEVELOPMENT);
	Sys.setFlag(RedTriplaneFlags.PrintLogMessageOnMissingSprite, true);
	Sys.setFlag(RedTriplaneFlags.ExitOnMissingSprite, false);
	Sys.setFlag(RedTriplaneFlags.AllowMissingRaster, true);
	Sys.setFlag(AssetsManagerFlags.AutoresolveDependencies, true);
	Sys.setStringParameter(RedTriplaneParams.DefaultFont, "Arial");

	// JsonTest.test();

	GWTUnitsSpawner spawner = new GWTUnitsSpawner();
	{
	    spawner.registerUnitClass(Names.newAssetID("com.jfixby.telecam.ui.load.TelecamLoader"),
		    new com.jfixby.telecam.ui.load.TelecamLoader());
	    spawner.registerUnitClass(Names.newAssetID("com.jfixby.telecam.ui.game.GameMainUI"),
		    new com.jfixby.telecam.ui.game.GameMainUI());
	    spawner.registerUnitClass(Names.newAssetID("com.jfixby.redtriplane.fokker.screens.RedTriplaneLogoScreen"),
		    new com.jfixby.redtriplane.fokker.screens.RedTriplaneLogoScreen());
	    spawner.registerUnitClass(Names.newAssetID("com.jfixby.redtriplane.fokker.screens.ProgressBarScreen"),
		    new com.jfixby.redtriplane.fokker.screens.ProgressBarScreen());

	}
	UnitsSpawner.installComponent(spawner);

    }

    private void installResources() {
	printAssetsInfo();
	RedResourcesManager res_manager = new RedResourcesManager();
	ResourcesManager.installComponent(res_manager);

	GdxAssetsFileSystem gdxFS = new GdxAssetsFileSystem();
	L.d("print index");
	gdxFS.getIndex().print();
	File dev_assets_home = gdxFS.ROOT();

	{
	    File bank_folder = dev_assets_home.child("bank-telecam");
	    if (bank_folder.exists()) {
		FileSystemBasedResource resource = new FileSystemBasedResource(bank_folder);
		res_manager.installResource(resource);
	    }
	}
    }

    private void printAssetsInfo() {
	FileHandle fh = Gdx.files.internal(AssetsInfo.FILE_NAME);
	if (!fh.exists()) {
	    Err.reportError(fh + " not found");
	    return;
	}
	String data = fh.readString();
	AssetsInfo info = Json.deserializeFromString(AssetsInfo.class, data);
	info.print();
    }

}
