
package com.github.changdao.KChartView;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;
//import com.github.wuxudong.rncharts.charts.BarChartManager;
import com.github.changdao.KChartView.KChartViewManager;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import android.util.Log;

public class KChartViewPackage implements ReactPackage {

    static{
        Log.i("ENMEM","[INFO]kchartviewpackage enter.");
    }
    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        return Arrays.asList();
    }

    // Deprecated in RN 0.47
    public List<Class<? extends JavaScriptModule>> createJSModules() {
        return Collections.emptyList();
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        return Arrays.<ViewManager>asList(
                new KChartViewManager()
        );
    }

}