package com.jfixby.telecam.run.test;

import com.jfixby.android.api.AndroidComponent;

public class AndroidTest implements AndroidComponent {
    String application_home_path_string = System.getProperty("user.dir");

    @Override
    public long getMaxHeapSize() {
	return 1024;
    }

    @Override
    public long getRecommendedHeapSize() {
	return 1024;
    }

    @Override
    public String getApplicationPrivateDirPath() {
	return application_home_path_string;
    }

}
