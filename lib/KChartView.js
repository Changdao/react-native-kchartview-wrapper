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
	appendDataNoMore(data){
		UIManager.dispatchViewManagerCommand(
        	findNodeHandle(this.getNativeComponentRef()),
        	//UIManager.getViewManagerConfig(this.getNativeComponentName()).Commands.appendData,
        	(
        		UIManager.getViewManagerConfig?
        			UIManager.getViewManagerConfig(this.getNativeComponentName()).Commands.appendDataNoMore:
        			UIManager[this.getNativeComponentName()].Commands.appendDataNoMore
        	),
        [data]
      );
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
	showLoading()
	{	
		console.log('[TRACE]show loading is called');
		UIManager.dispatchViewManagerCommand(
        	findNodeHandle(this.getNativeComponentRef()),
        	//UIManager.getViewManagerConfig(this.getNativeComponentName()).Commands.appendData,
        	(
        		UIManager.getViewManagerConfig?
        			UIManager.getViewManagerConfig(this.getNativeComponentName()).Commands.showLoading:
        			UIManager[this.getNativeComponentName()].Commands.showLoading
        	),
        []
      );
	}
	reload()
	{
		console.log('[TRACE]reload is called');
		UIManager.dispatchViewManagerCommand(
        	findNodeHandle(this.getNativeComponentRef()),
        	//UIManager.getViewManagerConfig(this.getNativeComponentName()).Commands.appendData,
        	(
        		UIManager.getViewManagerConfig?
        			UIManager.getViewManagerConfig(this.getNativeComponentName()).Commands.reload:
        			UIManager[this.getNativeComponentName()].Commands.reload
        	),
			[]
		);
	}
	_onLoadMore(){
		console.log('====>load more data event got');
		if(!this.props.onLoadMore)return;
		this.props.onLoadMore();
	}
	render(){
		return <RNKChartView {...this.props} ref={ref=>this.nativeComponentRef=ref} onLoadMore={this._onLoadMore.bind(this)}/>;
	}
}

/*
KChartView.propTypes = {
	
}
*/

var RNKChartView = requireNativeComponent('RNKChartView',KChartView,{nativeOnly:{onLoadMore:true}});

//export default ScrollEnhancer(HighlightEnhancer(ScaleEnhancer(MoveEnhancer(KChartView))))