
package com.jfixby.telecam.ui.actions;

import com.jfixby.r3.api.ui.UIAction;
import com.jfixby.telecam.ui.TelecamUnit;

public class UIOperations {

	static public final UIAction<TelecamUnit> goPhotoShoot = new GoPhotoShoot();
	static public final UIAction<TelecamUnit> goAndroidImageGallery = new GoAndroidImageGallery();

	static public final UIAction<TelecamUnit> doShootPhoto = new DoShootPhoto();
	static public final UIAction<TelecamUnit> doBlink = new DoBlink();
	static public final UIAction<TelecamUnit> doDiscardPhoto = new DoDiscardPhoto();
	static public final UIAction<TelecamUnit> goAcceptDeclinePhoto = new GoAcceptDeclinePhoto();
	public static final UIAction<TelecamUnit> goCropper = new GoCropper();

}
