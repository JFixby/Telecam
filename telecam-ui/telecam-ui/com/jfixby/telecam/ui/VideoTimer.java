
package com.jfixby.telecam.ui;

import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.cmns.api.assets.Names;
import com.jfixby.cmns.api.color.Colors;
import com.jfixby.cmns.api.geometry.CanvasPosition;
import com.jfixby.cmns.api.geometry.ORIGIN_RELATIVE_HORIZONTAL;
import com.jfixby.cmns.api.geometry.ORIGIN_RELATIVE_VERTICAL;
import com.jfixby.r3.api.ui.unit.layer.Layer;
import com.jfixby.r3.api.ui.unit.raster.Raster;
import com.jfixby.r3.api.ui.unit.txt.RasterizedFont;
import com.jfixby.r3.api.ui.unit.txt.RasterizedFontSpecs;
import com.jfixby.r3.api.ui.unit.txt.RasterizedString;
import com.jfixby.r3.api.ui.unit.txt.RasterizedStringSpecs;
import com.jfixby.r3.api.ui.unit.txt.TextFactory;

public class VideoTimer {

	private Layer root;
	private final UserInputBar master;
	private Raster background;
	private RasterizedString string;
	private final String requiredChars = "0123456789:°";
	private final AssetID font_id = Names.newAssetID("com.jfixby.telecam.font.prepacked");

	public VideoTimer (final UserInputBar userInputBar) {
		this.master = userInputBar;
	}

	public void setup (final Layer button_root) {
		this.root = button_root;
// this.root.print();
		this.background = button_root.findComponent();
		this.background.setOriginRelativeX(ORIGIN_RELATIVE_HORIZONTAL.CENTER);
		this.background.setOriginRelativeY(ORIGIN_RELATIVE_VERTICAL.CENTER);
		this.background.setDebugRenderFlag(true);

		final TextFactory factory = this.root.getComponentsFactory().getTextDepartment();
		final RasterizedStringSpecs stringSpec = factory.newRasterStringSpecs();
		stringSpec.setString("00:00");

		final RasterizedFontSpecs font_specs = factory.newFontSpecs();
		font_specs.setFontName(this.font_id);
		font_specs.setFontSize(24);
		font_specs.setFontScale(1);
		font_specs.setRequiredCharacters(this.requiredChars);
		font_specs.setColor(Colors.WHITE());

		final RasterizedFont font = factory.newFont(font_specs);
		stringSpec.setFont(font);

		this.string = factory.newRasterString(stringSpec);
		this.root.attachComponent(this.string);

		this.string.setOriginRelativeX(ORIGIN_RELATIVE_HORIZONTAL.CENTER);
		this.string.setOriginRelativeY(ORIGIN_RELATIVE_VERTICAL.CENTER);

	}

	public void update (final CanvasPosition position) {
		this.background.setPositionX(position.getX());
		this.string.setPosition(this.background.getPosition());
	}

	public void hide () {
		this.root.hide();
	}

}
