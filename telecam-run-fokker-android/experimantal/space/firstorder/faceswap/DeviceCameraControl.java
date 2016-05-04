package space.firstorder.faceswap;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;

public interface DeviceCameraControl {

    // Synchronous interface
    void prepareCamera();

    void startPreview();

    void stopPreview();

    void takePicture();

    byte[] getPictureData();

    // Asynchronous interface - need when called from a non platform thread (GDX OpenGl thread)
    void startPreviewAsync();

    void stopPreviewAsync();

    byte[] takePictureAsync(long timeout);

    void saveAsJpeg(FileHandle jpgfile, Pixmap cameraPixmap);

    boolean isReady();

    void prepareCameraAsync();
}