import PropTypes from 'prop-types';
import React from 'react';
import {
	UIManager, findNodeHandle,
	requireNativeComponent,
	View
} from 'react-native';

import MoveEnhancer from './MoveEnhancer'
import ScaleEnhancer from "./ScaleEnhancer";
import HighlightEnhancer from "./HighlightEnhancer";
import ScrollEnhancer from "./ScrollEnhancer";




export default class KChartView extends React.Component {
	getNativeComponentName(){
		return 'RNKChartView'
	}
	getNativeComponentRef(){
		return this.nativeComponentRef
	}
	render(){
		return <RNKChartView {...this.props} ref={ref=>this.nativeComponentRef=ref} />;
	}
	appendData(data)
	{
		UIManager.dispatchViewManagerCommand(
        	findNodeHandle(this.getNativeComponentRef()),
        	//UIManager.getViewManagerConfig(this.getNativeComponentName()).Commands.appendData,
        	(
        		UIManager.getViewManagerConfig?
        			UIManager.getViewManagerConfig(this.getNativeComponentName()).Commands.appendData:
        			UIManager[this.getNativeComponentName()].Commands.appendData
        	),
        [data]
      );
	}
	showLoading(data)
	{	
		UIManager.dispatchViewManagerCommand(
        	findNodeHandle(this.getNativeComponentRef()),
        	//UIManager.getViewManagerConfig(this.getNativeComponentName()).Commands.appendData,
        	(
        		UIManager.getViewManagerConfig?
        			UIManager.getViewManagerConfig(this.getNativeComponentName()).Commands.showLoading:
        			UIManager[this.getNativeComponentName()].Commands.showLoading
        	),
        [data]
      );
	}
}

/*
KChartView.propTypes = {
	
}
*/

var RNKChartView = requireNativeComponent('RNKChartView',KChartView,{nativeOnly:{onSelect:true,onChange:true}});

//export default ScrollEnhancer(HighlightEnhancer(ScaleEnhancer(MoveEnhancer(KChartView))))