package com.github.changdao.KChartView;

import javax.annotation.Nullable;

import android.app.Activity;

import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.Arguments;

import com.facebook.react.common.MapBuilder;
//import com.facebook.react.uimanager.BaseViewPropertyApplicator;
//import com.facebook.react.uimanager.CatalystStylesDiffMap;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.facebook.react.bridge.UiThreadUtil;
//import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
//import com.facebook.react.uimanager.ViewManager;
//import com.facebook.react.views.text.ReactTextShadowNode;

import com.github.tifezh.kchartlib.chart.KChartView;
import com.github.tifezh.kchartlib.chart.BaseKChartView;
import com.github.tifezh.kchart.chart.KChartAdapter;
import com.github.tifezh.kchartlib.chart.formatter.DateFormatter;
import com.github.tifezh.kchart.chart.KLineEntity;

import java.util.List;
import java.util.Map;

import android.util.Log;


public class KChartViewManager extends SimpleViewManager<KChartView> implements KChartView.KChartRefreshListener {
    public static final String REACT_CLASS = "RNKChartView";
    private static final int COMMAND_APPEND_DATA = 1;
    private static final int COMMAND_SHOW_LOADING = 2;
    private static final int COMMAND_APPEND_DATA_NO_MORE = 3;
    private static final int COMMAND_RELOAD = 4;
    //private static final int COMMAND_RESET = 2;

    private Activity mActivity = null;

    private ThemedReactContext mContext = null;
    
    private KChartAdapter mAdapter;
    
    private KChartView mKChartView;

    // public KChartViewManager(Activity activity) {
    //     mActivity = activity;
    // }

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    public KChartView createViewInstance(ThemedReactContext context) {
        try
        {
            mContext = context;
            mKChartView =  new KChartView(context);
            
            mKChartView.setDateTimeFormatter(new DateFormatter());
            mKChartView.setGridRows(4);
            mKChartView.setGridColumns(4);
            mKChartView.setOnSelectedChangedListener(new BaseKChartView.OnSelectedChangedListener() {
                @Override
                public void onSelectedChanged(BaseKChartView view, Object point, int index) {
                    KLineEntity data = (KLineEntity) point;
                    //Log.i("onSelectedChanged", "index:" + index + " closePrice:" + data.getClosePrice());
                }
            });
            mKChartView.setRefreshListener(this);
            
            mAdapter =  new KChartAdapter();
            mKChartView.setAdapter(mAdapter);

            mKChartView.resetLoadMoreEnd();
            mKChartView.showLoading();
            
            //final List<KLineEntity> data = DataRequest.getALL(context);
            //mAdapter.addFooterData(data);
            //mKChartView.startAnimation();
            //mKChartView.refreshEnd();
            Log.i("ReactNativeJS KChartView","mKChartView create view instance.");
        }
        catch(Exception e )
        {
            Log.i("ReactNativeJS KChartView","Exception found:");
            Log.i("ReactNativeJS KChartView",e.getMessage());
        }
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                //final List<KLineEntity> data = DataRequest.getALL(ExampleActivity.this);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //mAdapter.addFooterData(data);
                        mKChartView.startAnimation();
                        mKChartView.refreshEnd();
                    }
                });
            }
        }).start();*/
        return mKChartView;
    }

    //@ReactProp(name = "data")
    /*public void setData(KChartView view, ReadableArray propArray) {
        Log.i("ReactNativeJS KChartView","setData");
        if(propArray.size()==0)
        {
             Log.i("ReactNativeJS KChartView","no Data return ");
             return;
        }
        mAdapter =  new KChartAdapter();
        mKChartView.setAdapter(mAdapter);
        final List<KLineEntity> data = DataExtract.extract(propArray);
        DataHelper.calculate(data);
        mAdapter.addFooterData(data);
        //mKChartView.startAnimation();
        mKChartView.refreshComplete();
    }*/

    @ReactProp(name = "backgroundColor")
    public void setBackgroundColor(KChartView view,Integer color){
        Log.i("ReactNativeJS KChartView","setBackgroundColor is called");
        mKChartView.setBackgroundColor(color);
    }

    @ReactProp(name = "increasingColor")
    public void setIncreasingColor(KChartView view,Integer color){
        mKChartView.setIncreasingColor(color);
    }

    @ReactProp(name = "decreasingColor")
    public void setDecreasingColor(KChartView view,Integer color){
        mKChartView.setDecreasingColor(color);
    }

    @ReactProp(name = "progressBarColor")
    public void setProgressBarColor(KChartView view, Integer color){
        mKChartView.setProgressBarColor(color);
    }


    @Override
    public void onLoadMoreBegin(KChartView chart) {
        
        WritableMap event = Arguments.createMap();
        //event.putString("message", "MyMessage");
        //ReactContext reactContext = (ReactContext)getContext();
        mContext.getJSModule(RCTEventEmitter.class).receiveEvent(
            mKChartView.getId(),
            "onLoadMore",
            event
        );
        
        Log.i("ReactNativeJS KChartview","onLoadMoreBegin");
    }


    // public Map getExportedCustomBubblingEventTypeConstants() {
    //     return MapBuilder.builder()
    //         .put(
    //             "loadMore",
    //             MapBuilder.of(
    //                 "phasedRegistrationNames",
    //                 MapBuilder.of("bubbled", "onLoadMore")))
    //                 .build();
    // }

    @Override
    @Nullable
    public Map getExportedCustomDirectEventTypeConstants() {
        Log.i("ReactNativeJS KChartView","getExportedCustomDirectEventTypeConstants is called");
        Map<String, Map<String, String>> map = MapBuilder.of(
            "onLoadMore", MapBuilder.of("registrationName", "onLoadMore")
        );
        return map;
    }

    

    @Override 
    public Map<String, Integer> getCommandsMap() {
        Log.i("ReactNativeJS KChartView","getCommandsMap is called");
        return MapBuilder.of(
            "appendData", COMMAND_APPEND_DATA,
            "appendDataNoMore", COMMAND_APPEND_DATA_NO_MORE,
            "showLoading", COMMAND_SHOW_LOADING,
            "reload", COMMAND_RELOAD
        );
    }

    @Override
    public void receiveCommand(final KChartView view, int commandId, final ReadableArray args) {
        switch (commandId) {
            case COMMAND_RELOAD:
                mAdapter =  new KChartAdapter();
                mKChartView.setAdapter(mAdapter);
                mKChartView.resetLoadMoreEnd();
                mKChartView.refreshComplete();
                mKChartView.showLoading();
                break;
            case COMMAND_APPEND_DATA_NO_MORE:
                Log.i("ReactNativeJS KChartView","receiveCommand, appendDataNoMore");
                final List<KLineEntity> data1 = DataExtract.extract(args.getArray(0));
                DataHelper.calculate(data1);
                mAdapter.addFooterData(data1);
                mKChartView.refreshEnd();
                break;
            case COMMAND_APPEND_DATA: 
                Log.i("ReactNativeJS KChartView","receiveCommand, appendData");
                final List<KLineEntity> data2 = DataExtract.extract(args.getArray(0));
                DataHelper.calculate(data2);
                mAdapter.addFooterData(data2);
                mKChartView.refreshComplete();
                break;
            case COMMAND_SHOW_LOADING:
                Log.i("ReactNativeJS KChartView","receiveCommand, show loading 2");
                mKChartView.resetLoadMoreEnd();
                mKChartView.showLoading();
                /*UiThreadUtil.runOnUiThread(
                    new Runnable() {
                        @Override
                        public void run() {
                            //final List<KLineEntity> data = DataRequest.getALL(ExampleActivity.this);
                            
                                    //mAdapter.addFooterData(data);
                                    //mKChartView.startAnimation();
                                    //mKChartView.refreshEnd();
                            mKChartView.showLoading();
                            
                        }
                    }
                );*/
                
                
                break;
        }
    }
}
