
package com.jfixby.telecam.ui.input.cropper.rotator;

import com.jfixby.cmns.api.color.Colors;
import com.jfixby.cmns.api.geometry.CanvasPosition;
import com.jfixby.cmns.api.geometry.ORIGIN_RELATIVE_HORIZONTAL;
import com.jfixby.cmns.api.geometry.ORIGIN_RELATIVE_VERTICAL;
import com.jfixby.cmns.api.math.CustomAngle;
import com.jfixby.r3.api.ui.unit.layer.Layer;
import com.jfixby.r3.api.ui.unit.txt.RasterizedFont;
import com.jfixby.r3.api.ui.unit.txt.RasterizedFontSpecs;
import com.jfixby.r3.api.ui.unit.txt.RasterizedString;
import com.jfixby.r3.api.ui.unit.txt.RasterizedStringSpecs;
import com.jfixby.r3.api.ui.unit.txt.TextFactory;

public class AngleIndicator {

	private final Rotator master;
	private Layer root;
	private RasterizedString string;

	public AngleIndicator (final Rotator rotator) {
		this.master = rotator;
	}

	public void setup (final Layer root) {
		this.root = root;
		final TextFactory factory = this.root.getComponentsFactory().getTextDepartment();
		final RasterizedStringSpecs stringSpec = factory.newRasterStringSpecs();

		final RasterizedFontSpecs font_specs = factory.newFontSpecs();
		font_specs.setFontName(this.master.getFontSettings().getName());
		final float m = this.master.getFontSettings().getMultiplier();
		font_specs.setFontSize(24 / m);
		font_specs.setFontScale(m);
		font_specs.setBorderSize(0f);
		font_specs.setRequiredCharacters(this.master.getFontSettings().getRequiredChars());
		font_specs.setColor(Colors.WHITE());

		final RasterizedFont font = factory.newFont(font_specs);
		stringSpec.setFont(font);

		this.string = factory.newRasterString(stringSpec);
		this.string.setValue("+44.99°");
		this.root.attachComponent(this.string);
		this.string.setDebugRenderFlag(!true);
		this.string.setOriginRelativeX(ORIGIN_RELATIVE_HORIZONTAL.CENTER);
		this.string.setOriginRelativeY(ORIGIN_RELATIVE_VERTICAL.BOTTOM);
	}

	public void update (final CanvasPosition position) {
		final double width = this.master.getWidth();
		final double height = this.master.getHeight();
		this.string.setPosition(position);
		this.string.setPositionY(this.string.getPositionY() - height / 2);

	}

	public void updateValue (final CustomAngle angle) {
		final int degrees = (int)angle.toDegrees();
		if (degrees == 0) {
			this.string.setValue("");
			return;
		}
		String value = degrees + "°";
		if (degrees < 0) {
			value = "-" + value;
		}
		this.string.setValue(value);
	}

}
