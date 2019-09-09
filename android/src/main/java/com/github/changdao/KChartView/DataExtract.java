package com.github.changdao.KChartView;

import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableArray;

import com.github.tifezh.kchart.chart.KLineEntity;

import java.util.List;
import java.util.ArrayList;

import android.util.Log;


public class DataExtract{
	public static List<KLineEntity> extract(ReadableArray propArray){
		Log.i("KCHARTVIEW","START extract data");
		List<KLineEntity> result = new ArrayList<KLineEntity>();

        for (int i = 0; i < propArray.size(); i++) {
            ReadableMap dataSetReadableMap = propArray.getMap(i);
            String open = dataSetReadableMap.getString("Open");
            String close = dataSetReadableMap.getString("Close");
            String high  = dataSetReadableMap.getString("High");
            String low  = dataSetReadableMap.getString("Low");
            String date = dataSetReadableMap.getString("Date");
            String volume = dataSetReadableMap.getString("Volume");
            //Log.i("KCHARTVIEW","open:"+open+ "close:"+close);
            KLineEntity ent = new KLineEntity();
            ent.Date = date;
            ent.Volume = Float.parseFloat(volume);
            ent.Open = Float.parseFloat(open);
            ent.Close = Float.parseFloat(close);
            ent.High = Float.parseFloat(high);
            ent.Low = Float.parseFloat(low);
            result.add(ent);

        }

        Log.i("KCHARTVIEW","mKChartView extract Data:"+result.size());
        return result;
	}
}