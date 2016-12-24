
package com.jfixby.telecam.run.desktop;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.lwjgl.FokkerLwjglApplication;
import com.badlogic.gdx.backends.lwjgl.FokkerLwjglApplicationConfiguration;
import com.jfixby.r3.engine.core.FokkerStarter;
import com.jfixby.r3.engine.core.FokkerStarterConfig;
import com.jfixby.r3.fokker.adaptor.GdxAdaptor;
import com.jfixby.r3.fokker.api.FokkerEngineAssembler;
import com.jfixby.r3.fokker.api.UnitsMachineExecutor;
import com.jfixby.scarabei.adopted.gdx.GdxSimpleTriangulator;
import com.jfixby.scarabei.adopted.gdx.base64.GdxBase64;
import com.jfixby.scarabei.api.angles.Angles;
import com.jfixby.scarabei.api.assets.Names;
import com.jfixby.scarabei.api.base64.Base64;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.color.Colors;
import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.geometry.Geometry;
import com.jfixby.scarabei.api.graphs.Graphs;
import com.jfixby.scarabei.api.input.UserInput;
import com.jfixby.scarabei.api.io.IO;
import com.jfixby.scarabei.api.json.Json;
import com.jfixby.scarabei.api.log.L;
import com.jfixby.scarabei.api.math.FloatMath;
import com.jfixby.scarabei.api.math.IntegerMath;
import com.jfixby.scarabei.api.math.MathTools;
import com.jfixby.scarabei.api.math.SimpleTriangulator;
import com.jfixby.scarabei.api.md5.MD5;
import com.jfixby.scarabei.api.sys.Sys;
import com.jfixby.scarabei.api.sys.settings.SystemSettings;
import com.jfixby.scarabei.api.taskman.TaskManager;
import com.jfixby.scarabei.api.util.JUtils;
import com.jfixby.scarabei.red.color.RedColors;
import com.jfixby.scarabei.red.debug.RedDebug;
import com.jfixby.scarabei.red.desktop.collections.DesktopCollections;
import com.jfixby.scarabei.red.desktop.math.DesktopFloatMath;
import com.jfixby.scarabei.red.desktop.sys.DesktopSystem;
import com.jfixby.scarabei.red.err.RedError;
import com.jfixby.scarabei.red.geometry.RedGeometry;
import com.jfixby.scarabei.red.graphs.RedGraphs;
import com.jfixby.scarabei.red.input.RedInput;
import com.jfixby.scarabei.red.io.RedIO;
import com.jfixby.scarabei.red.log.SimpleLogger;
import com.jfixby.scarabei.red.math.RedAngles;
import com.jfixby.scarabei.red.math.RedIntegerMath;
import com.jfixby.scarabei.red.math.RedMathTools;
import com.jfixby.scarabei.red.name.RedAssetsNamespace;
import com.jfixby.scarabei.red.sys.RedSystemSettings;
import com.jfixby.scarabei.red.sys.RedTaskManager;
import com.jfixby.scarabei.red.util.RedJUtils;
import com.jfixby.scarabei.red.util.md5.RSADataSecurityIncMD5;

public class RunTelecamDesktop {
	public static void main (final String[] arg) {

		setupBasicComponents();
		final FokkerStarterConfig config = FokkerStarter.newRedTriplaneConfig();

		final FokkerEngineAssembler engine_assembler = new TelecamDesktopAssembler();
		config.setEngineAssembler(engine_assembler);

		final FokkerStarter triplane_starter = FokkerStarter.newRedTriplane(config);
		final UnitsMachineExecutor machine = triplane_starter.getUnitsMachineExecutor();

		final GdxAdaptor adaptor = new GdxAdaptor(machine);

		final FokkerLwjglApplicationConfiguration cfg = new FokkerLwjglApplicationConfiguration();
		cfg.title = "Test";
		cfg.useGL30 = false;
		cfg.width = 800;
		cfg.height = 1232;
// cfg.vSyncEnabled = false;
// cfg.r = 1;
// cfg.g = 1;
// cfg.b = 1;
// cfg.a = 1;
// cfg.overrideDensity = 10;
// cfg.foregroundFPS = 60;

		final ApplicationListener gdx_listener = adaptor.getGDXApplicationListener();

		// gdx_listener = new HttpRequestTest();
		// GdxEntryPoint point = new GdxEntryPoint();
		// new LwjglApplication(point, cfg);
		new FokkerLwjglApplication(gdx_listener, cfg);

	}

	private static void setupBasicComponents () {
		L.installComponent(new SimpleLogger());
		Collections.installComponent(new DesktopCollections());
		Err.installComponent(new RedError());
		Debug.installComponent(new RedDebug());
		JUtils.installComponent(new RedJUtils());
		FloatMath.installComponent(new DesktopFloatMath());
		TaskManager.installComponent(new RedTaskManager());
		Sys.installComponent(new DesktopSystem());
		SystemSettings.installComponent(new RedSystemSettings());

		IntegerMath.installComponent(new RedIntegerMath());
		Names.installComponent(new RedAssetsNamespace());
		IO.installComponent(new RedIO());
		Graphs.installComponent(new RedGraphs());
		SimpleTriangulator.installComponent(new GdxSimpleTriangulator());
		Angles.installComponent(new RedAngles());

		UserInput.installComponent(new RedInput());

		final RedGeometry geometry = new RedGeometry();
		Geometry.installComponent(geometry);
		Colors.installComponent(new RedColors());
		MathTools.installComponent(new RedMathTools());
		// --
		Json.installComponent("com.jfixby.cmns.adopted.gdx.json.RedJson");
		Base64.installComponent(new GdxBase64());
		MD5.installComponent(new RSADataSecurityIncMD5());
	}
}
