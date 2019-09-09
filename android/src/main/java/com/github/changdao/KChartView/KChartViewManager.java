package com.github.changdao.KChartView;

import javax.annotation.Nullable;

import android.app.Activity;

import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.common.MapBuilder;
//import com.facebook.react.uimanager.BaseViewPropertyApplicator;
//import com.facebook.react.uimanager.CatalystStylesDiffMap;
import com.facebook.react.uimanager.SimpleViewManager;
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


public class KChartViewManager extends SimpleViewManager<KChartView> {
    public static final String REACT_CLASS = "RNKChartView";
    private static final int COMMAND_APPEND_DATA = 1;
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
            mKChartView.showLoading();
            
            //final List<KLineEntity> data = DataRequest.getALL(context);
            //mAdapter.addFooterData(data);
            //mKChartView.startAnimation();
            //mKChartView.refreshEnd();
            Log.i("KCHARTVIEW","mKChartView data loaded.");
        }
        catch(Exception e )
        {
            Log.i("KCHARTVIEW","Exception found:");
            Log.i("KCHARTVIEW",e.getMessage());
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

    @ReactProp(name = "data")
    public void setData(KChartView view, ReadableArray propArray) {


        mAdapter =  new KChartAdapter();
        mKChartView.setAdapter(mAdapter);
        final List<KLineEntity> data = DataExtract.extract(propArray);
        DataHelper.calculate(data);
        mAdapter.addFooterData(data);
        mKChartView.startAnimation();
        mKChartView.refreshEnd();
        
        /*if (!LibsChecker.checkVitamioLibs(mActivity))
            return;

        view.setVideoPath(streamUrl);

        view.setMediaController(new MediaController(mContext));
        view.requestFocus();

        view.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                // optional need Vitamio 4.0
                mediaPlayer.setPlaybackSpeed(1.0f);
            }
        });*/
    }

    @ReactProp(name = "backgroundColor")
    public void setBackgroundColor(KChartView view,Integer color){
        mKChartView.setBackgroundColor(color);
    }

    @Override 
    public Map<String, Integer> getCommandsMap() {
        return MapBuilder.of(
            "appendData", COMMAND_APPEND_DATA
        );
    }

    @Override
    public void receiveCommand(final KChartView view, int commandId, final ReadableArray args) {
        switch (commandId) {
            case COMMAND_APPEND_DATA: 
                Log.i("KCHARTVIEW","receiveCommand, appendData");
                mAdapter.addFooterData(DataHelper.calculate(DataExtract.extract(args.getArray(0))));
                break;
        }
    }
}
