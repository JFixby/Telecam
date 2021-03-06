
package com.jfixby.telecam.run.android;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.jfixby.android.api.Android;
import com.jfixby.cmns.adopted.gdx.GdxSimpleTriangulator;
import com.jfixby.cmns.adopted.gdx.base64.GdxBase64;
import com.jfixby.cmns.adopted.gdx.json.RedJson;
import com.jfixby.cmns.api.angles.Angles;
import com.jfixby.cmns.api.assets.Names;
import com.jfixby.cmns.api.base64.Base64;
import com.jfixby.cmns.api.collections.Collections;
import com.jfixby.cmns.api.color.Colors;
import com.jfixby.cmns.api.debug.Debug;
import com.jfixby.cmns.api.err.Err;
import com.jfixby.cmns.api.geometry.Geometry;
import com.jfixby.cmns.api.graphs.Graphs;
import com.jfixby.cmns.api.input.UserInput;
import com.jfixby.cmns.api.io.IO;
import com.jfixby.cmns.api.json.Json;
import com.jfixby.cmns.api.math.FloatMath;
import com.jfixby.cmns.api.math.IntegerMath;
import com.jfixby.cmns.api.math.MathTools;
import com.jfixby.cmns.api.math.SimpleTriangulator;
import com.jfixby.cmns.api.md5.MD5;
import com.jfixby.cmns.api.sys.Sys;
import com.jfixby.cmns.api.sys.settings.SystemSettings;
import com.jfixby.cmns.api.taskman.TaskManager;
import com.jfixby.cmns.api.util.JUtils;
import com.jfixby.r3.fokker.api.FokkerEngineAssembler;
import com.jfixby.r3.fokker.api.UnitsMachineExecutor;
import com.jfixby.red.android.collections.AndroidCollections;
import com.jfixby.red.android.math.AndroidFloatMath;
import com.jfixby.red.android.sys.AndroidSystem;
import com.jfixby.red.color.RedColors;
import com.jfixby.red.debug.RedDebug;
import com.jfixby.red.engine.core.FokkerStarter;
import com.jfixby.red.engine.core.FokkerStarterConfig;
import com.jfixby.red.err.RedError;
import com.jfixby.red.geometry.RedGeometry;
import com.jfixby.red.graphs.RedGraphs;
import com.jfixby.red.input.RedInput;
import com.jfixby.red.io.RedIO;
import com.jfixby.red.math.RedAngles;
import com.jfixby.red.math.RedIntegerMath;
import com.jfixby.red.math.RedMathTools;
import com.jfixby.red.name.RedAssetsNamespace;
import com.jfixby.red.sys.RedSystemSettings;
import com.jfixby.red.sys.RedTaskManager;
import com.jfixby.red.triplane.fokker.android.RedTriplaneAndroidApplication;
import com.jfixby.red.triplane.fokker.android.RedTriplaneAndroidApplicationConfig;
import com.jfixby.red.util.RedJUtils;
import com.jfixby.red.util.md5.RSADataSecurityIncMD5;
import com.jfixby.redtriplane.fokker.adaptor.GdxAdaptor;

import android.content.pm.ActivityInfo;

//public class RunTelecamAndroid extends RedTriplaneAndroidApplication {
public class RunTelecamAndroidFokker extends RedTriplaneAndroidApplication {

	void setup () {
		Err.installComponent(new RedError());
		Debug.installComponent(new RedDebug());
		Collections.installComponent(new AndroidCollections());

		JUtils.installComponent(new RedJUtils());
		FloatMath.installComponent(new AndroidFloatMath());
		TaskManager.installComponent(new RedTaskManager());
		Sys.installComponent(new AndroidSystem());
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

		Json.installComponent(new RedJson());
		Base64.installComponent(new GdxBase64());
		MD5.installComponent(new RSADataSecurityIncMD5());

	}

	static {
		orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
		useCamera = true;
	}

	@Override
	public RedTriplaneAndroidApplicationConfig doGdxDeploy (final RedTriplaneAndroidApplication app) {
		this.setup();
		final RedTriplaneAndroidApplicationConfig red_config = new RedTriplaneAndroidApplicationConfig();

		final FokkerStarterConfig config = FokkerStarter.newRedTriplaneConfig();
		Android.installComponent(this);
		final FokkerEngineAssembler engine_assembler = new TelecamAndroidAssembler();
		config.setEngineAssembler(engine_assembler);

		final FokkerStarter triplane_starter = FokkerStarter.newRedTriplane(config);
		final UnitsMachineExecutor machine = triplane_starter.getUnitsMachineExecutor();

		final GdxAdaptor adaptor = new GdxAdaptor(machine);

		final ApplicationListener gdx_listener = adaptor.getGDXApplicationListener();

		final AndroidApplicationConfiguration android_config = new AndroidApplicationConfiguration();
		android_config.useGL30 = false;
		android_config.hideStatusBar = true;
		android_config.r = 8;
		android_config.g = 8;
		android_config.b = 8;
		android_config.a = 8;

// if (this.graphics.getView() instanceof SurfaceView) {
// final SurfaceView glView = (SurfaceView)this.graphics.getView();
// // force alpha channel - I'm not sure we need this as the GL surface is already using alpha channel
// glView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
// }

		red_config.setGdxListener(gdx_listener);
		red_config.setAndroidApplicationConfig(android_config);

		return red_config;

	}

	@Override
	public void post (final Runnable r) {
		this.handler.post(r);
	}

}
