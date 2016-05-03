package com.jfixby.r3.test.text;

import com.jfixby.cmns.api.angles.Angles;
import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.cmns.api.assets.Names;
import com.jfixby.cmns.api.color.Colors;
import com.jfixby.r3.api.text.Text;
import com.jfixby.r3.api.ui.unit.ComponentsFactory;
import com.jfixby.r3.api.ui.unit.RootLayer;
import com.jfixby.r3.api.ui.unit.Unit;
import com.jfixby.r3.api.ui.unit.UnitManager;
import com.jfixby.r3.api.ui.unit.camera.Camera;
import com.jfixby.r3.api.ui.unit.txt.TextBar;
import com.jfixby.r3.api.ui.unit.txt.TextBarSpecs;
import com.jfixby.r3.api.ui.unit.txt.TextFactory;
import com.jfixby.rana.api.asset.AssetHandler;
import com.jfixby.rana.api.asset.AssetsManager;

public class TextTest extends Unit {

    private RootLayer root;
    private ComponentsFactory components_factory;
    private Camera camera;

    @Override
    public void onCreate(UnitManager unitManager) {
	super.onCreate(unitManager);

	root = unitManager.getRootLayer();
	components_factory = unitManager.getComponentsFactory();

	// CameraSpecs cam_spec =
	// cddomponentsFactory.getCameraDepartment().newCameraSpecs();
	// cam_spec.setSimpleCameraPolicy(SIMPLE_CAMERA_POLICY.EXPAND_CAMERA_VIEWPORT_ON_SCREEN_RESIZE);
	// camera = componentsFactory.getCameraDepartment().newCamera(cam_spec);
	// // camera.setSize(800, 600);
	// camera.setPosition(0, 0);
	// float m = 3.333333f;
	// camera.setSize(1024 * m, m * 768);
	// camera.setOriginRelative(0.5, 0.5);
	// camera.setPosition(0, 0);
	// // camera.setRotation(Angles.g30());
	// this.root.setCamera(camera);
	// RasterizedStringSpecs specs =
	// componentsFactory.getTextDepartment().newRasterStringSpecs();
	//
	// AssetID text_asset_id =
	// Names.newAssetID("com.jfixby.telecam.scene-002.psd.text.txt");
	// AssetsManager.autoResolveAsset(text_asset_id);
	// AssetHandler text_asset = AssetsManager.obtainAsset(text_asset_id,
	// componentsFactory);
	// Text text = (Text) text_asset.asset();
	// TextTranslationsList translations = text.listTranslations();
	// translations.print();
	//
	// String locale_name = "italiano";
	// // String locale_name = "русский";
	// TextTranslation translation =
	// translations.getByLocalization(locale_name);
	//
	// StringValue string_value = translation.getChars();
	// // String string_value = TextFactory.Pangram_EN + "\n"
	// // + TextFactory.Pangram_EN + "\n" + TextFactory.Pangram_EN;
	// specs.setString(string_value);
	//
	// AssetID font_id = Names.newAssetID("GenericFont.otf");
	//
	// RasterizedFontSpecs font_specs =
	// componentsFactory.getTextDepartment().newFontSpecs();
	//
	// font_specs.setFontName(font_id);
	// font_specs.setFontSize(128f);
	// font_specs.setColor(Colors.BLACK());
	// font_specs.setRequiredCharacters(string_value);
	//
	// RasterizedFont font =
	// componentsFactory.getTextDepartment().newFont(font_specs);
	//
	// specs.setFont(font);
	//
	// RasterizedString string =
	// componentsFactory.getTextDepartment().newRasterString(specs);
	// string.setDebugRenderFlag(true);
	//
	// // string
	//
	// root.attachComponent(string);
	//
	// // string.setPositionXY(10, 10);
	// string.setRotation(Angles.g30());

	TextFactory text_factory = components_factory.getTextDepartment();

	AssetID text_asset_id = Names.newAssetID("com.jfixby.telecam.scene-002.psd.text.txt");
	AssetsManager.autoResolveAsset(text_asset_id);
	AssetHandler text_asset = AssetsManager.obtainAsset(text_asset_id, components_factory);
	Text text = (Text) text_asset.asset();

	TextBarSpecs text_bar_specs = text_factory.newTextBarSpecs();

	text_bar_specs.setLocaleName("english");

	AssetID font_id = Names.newAssetID("GenericFont.otf");

	text_bar_specs.setText(text);
	text_bar_specs.setPadding(0);
	text_bar_specs.setFontColor(Colors.WHITE());
	// text_bar_specs.setBackgroundRaster(bg);
	text_bar_specs.setFont(font_id);
	text_bar_specs.setFontSize(-1 + 128 + (1f / 3f));
	text_bar_specs.setFontScale(1 + 1f / 3f);
	TextBar text_bar = text_factory.newTextBar(text_bar_specs);

	double canvas_x = 0;
	double canvas_y = 0;
	text_bar.setPositionXY(0, 0);
	text_bar.setPositionXY(canvas_x, canvas_y);
	text_bar.setRotation(Angles.g30());

	root.attachComponent(text_bar);
    }

    @Override
    public void onStart() {
	super.onStart();
    }

    @Override
    public void onResume() {
	super.onResume();
    }

    @Override
    public void onPause() {
	super.onPause();
    }

    @Override
    public void onDestroy() {
	super.onDestroy();
    }

}
