
package space.firstorder.faceswap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;

public class FirstOrderInputProcessor extends CameraInputController {
	final Runnable runnable;

	public FirstOrderInputProcessor (final Camera c, final Runnable runnable) {
		super(c);
		this.runnable = runnable;
	}

	@Override
	public boolean touchDown (final int screenX, final int screenY, final int pointer, final int button) {
// int height = Gdx.graphics.getHeight();
// int width = Gdx.graphics.getWidth();
//
// int yInverted = height - screenY;
		Gdx.app.log("Example", "touch started at (" + screenX + ", " + screenY + ")");

		if (screenX < 250 && screenY < 250) {
			if (this.runnable != null) {

				// call the changeColorsAction runnable interface, implemented in Main
				this.runnable.run();
				return true;
			}
		}

		// otherwise, just do camera input controls
		return super.touchDown(screenX, screenY, pointer, button);
	}
}
