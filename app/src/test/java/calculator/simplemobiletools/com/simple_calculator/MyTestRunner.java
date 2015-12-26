package calculator.simplemobiletools.com.simple_calculator;

import org.junit.runners.model.InitializationError;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.manifest.AndroidManifest;
import org.robolectric.res.Fs;

public class MyTestRunner extends RobolectricTestRunner {

    public MyTestRunner(Class<?> testClass) throws InitializationError {
        super(testClass);
    }

    @Override
    protected AndroidManifest getAppManifest(Config config) {
        return new AndroidManifest(
                Fs.fileFromPath("../app/src/main/AndroidManifest.xml"),
                Fs.fileFromPath("../app/src/main/res"),
                Fs.fileFromPath("../app/src/main/assets"));
    }
}
