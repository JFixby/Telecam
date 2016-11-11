
package com.jfixby.telecam.ui;

import com.jfixby.cmns.api.color.Colors;
import com.jfixby.cmns.api.geometry.CanvasPosition;
import com.jfixby.cmns.api.geometry.ORIGIN_RELATIVE_HORIZONTAL;
import com.jfixby.cmns.api.geometry.ORIGIN_RELATIVE_VERTICAL;
import com.jfixby.r3.api.ui.unit.layer.Layer;
import com.jfixby.r3.api.ui.unit.raster.Raster;
import com.jfixby.r3.api.ui.unit.txt.RasterizedString;
import com.jfixby.r3.api.ui.unit.txt.RasterizedStringSpecs;
import com.jfixby.r3.api.ui.unit.txt.TextFactory;

public class VideoTimer {

	private Layer root;
	private final UserInputBar master;
	private Raster background;
	private RasterizedString string;

	public VideoTimer (final UserInputBar userInputBar) {
		this.master = userInputBar;
	}

	public void setup (final Layer button_root) {
		this.root = button_root;
// this.root.print();
		this.background = button_root.findComponent();
		this.background.setOriginRelativeX(ORIGIN_RELATIVE_HORIZONTAL.CENTER);
		this.background.setOriginRelativeY(ORIGIN_RELATIVE_VERTICAL.CENTER);
		this.background.setDebugRenderFlag(!true);

		final TextFactory factory = this.root.getComponentsFactory().getTextDepartment();
		final RasterizedStringSpecs stringSpec = factory.newRasterStringSpecs();

// final RasterizedFontSpecs stringSpec = factory.newFontSpecs();
		stringSpec.setFontName(this.master.getFontSettings().getName());
		final float m = this.master.getFontSettings().getMultiplier();
		stringSpec.setFontSize(24 / m);
		stringSpec.setFontScale(m);
		stringSpec.setBorderSize(0f);
		stringSpec.addRequiredCharacters(this.master.getFontSettings().getRequiredChars());
		stringSpec.setFontColor(Colors.WHITE());
// stringSpec.setBorderColor(Colors.WHITE().mix(Colors.BLACK(), 0.1f));

// final RasterizedFont font = factory.newFont(stringSpec);
// stringSpec.setFont(font);

		this.string = factory.newRasterString(stringSpec);
		this.string.setChars("00:00");
		this.root.attachComponent(this.string);
		this.string.setDebugRenderFlag(!true);
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
