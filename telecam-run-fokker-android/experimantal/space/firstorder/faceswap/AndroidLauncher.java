
package space.firstorder.faceswap;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.SurfaceView;

public class AndroidLauncher extends AndroidApplication {
	private int origWidth;
	private int origHeight;

	@Override
	protected void onCreate (final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		final AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
		// we need to change the default pixel format - since it does not include an alpha channel
		// we need the alpha channel so the camera preview will be seen behind the GL scene
		cfg.r = 8;
		cfg.g = 8;
		cfg.b = 8;
		cfg.a = 8;

		final DeviceCameraControl cameraControl = new AndroidDeviceCameraController(this);
		this.initialize(new Main(cameraControl), cfg);
// initialize(new Main(null), cfg);
		if (this.graphics.getView() instanceof SurfaceView) {
			final SurfaceView glView = (SurfaceView)this.graphics.getView();
			// force alpha channel - I'm not sure we need this as the GL surface is already using alpha channel
			glView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
		}
		// we don't want the screen to turn off during the long image saving process
		this.graphics.getView().setKeepScreenOn(true);
		// keep the original screen size
		this.origWidth = this.graphics.getWidth();
		this.origHeight = this.graphics.getHeight();
	}

	public void post (final Runnable r) {
		this.handler.post(r);
	}

	public void setFixedSize (final int width, final int height) {
		if (this.graphics.getView() instanceof SurfaceView) {
			final SurfaceView glView = (SurfaceView)this.graphics.getView();
			glView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
			glView.getHolder().setFixedSize(width, height);
		}
	}

	public void restoreFixedSize () {
		if (this.graphics.getView() instanceof SurfaceView) {
			final SurfaceView glView = (SurfaceView)this.graphics.getView();
			glView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
			glView.getHolder().setFixedSize(this.origWidth, this.origHeight);
		}
	}
}
