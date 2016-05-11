
package com.jfixby.telecam.ui.actions;

import com.jfixby.r3.api.ui.UI;
import com.jfixby.r3.api.ui.UIAction;
import com.jfixby.telecam.ui.TelecamUnit;

public abstract class TelecamUIAction implements UIAction<TelecamUnit> {

	static public final TelecamUIAction goPhotoShoot = new GoPhotoShoot();
	static public final TelecamUIAction goVideoShoot = new GoVideoShoot();
	static public final TelecamUIAction switchToVideoShoot = new SwitchToVideoShoot();
	static public final TelecamUIAction switchToPhotoShoot = new SwitchToPhotoShoot();

	static public final TelecamUIAction goAndroidImageGallery = new GoAndroidImageGallery();

	static public final TelecamUIAction doShootPhoto = new DoShootPhoto();
	static public final TelecamUIAction doBlink = new DoBlink();
	static public final TelecamUIAction doDiscardPhoto = new DoDiscardPhoto();
	static public final TelecamUIAction doDiscardVideo = new DoDiscardVideo();
	static public final TelecamUIAction goAcceptDeclinePhoto = new GoAcceptDeclinePhoto();
	public static final TelecamUIAction goCropper = new GoCropper();
	public static final TelecamUIAction switchFlashMode = new SwitchFlashMode();
	public static final TelecamUIAction doSwitchCam = new DoSwitchCam();
	public static final TelecamUIAction goVideoRecording = new GoVideoRecording();
	public static final TelecamUIAction doRecordVideo = new DoRecordVideo();
	public static final TelecamUIAction doStopRecordVideo = new DoStopRecordVideo();
	public static final TelecamUIAction goVideoIdle = new GoVideoIdle();
	static public final TelecamUIAction goAcceptDeclineVideo = new GoAcceptDeclineVideo();

	public void push () {
		UI.pushAction(this);
	}

}
