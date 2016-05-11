
package com.jfixby.telecam.ui.input.red;

import com.jfixby.cmns.api.floatn.FixedFloat2;
import com.jfixby.r3.api.ui.unit.layer.Layer;
import com.jfixby.r3.api.ui.unit.raster.Raster;

public class Brigde extends RasterOffsetComponent {

	private final FixedFloat2 leftOffset;
	private final FixedFloat2 rightOffset;
	private double componentWidth;
	private final LeftCircle left;
	private final RightCircle right;

	public Brigde (final RedButton slider, final RightCircle right, final LeftCircle left) {
		super(slider);
		this.leftOffset = left.getOriginalOffset();
		this.rightOffset = right.getOriginalOffset();
		this.right = right;
		this.left = left;
	}

	@Override
	public void setup (final Raster findComponent, final Layer root) {
		super.setup(findComponent, root);

// this.componentWidth = this.rightOffset.getX() - this.leftOffset.getX();
		this.componentWidth = this.right.getOffsetX() - this.left.getOffsetX();

	}

	public double getComponentWidth () {
		return this.componentWidth;
	}

}
