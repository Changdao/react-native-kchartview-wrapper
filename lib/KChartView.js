import PropTypes from 'prop-types';
import React from 'react';
import {
	requireNativeComponent,
	View
} from 'react-native';

import MoveEnhancer from './MoveEnhancer'
import ScaleEnhancer from "./ScaleEnhancer";
import HighlightEnhancer from "./HighlightEnhancer";
import ScrollEnhancer from "./ScrollEnhancer";




class KChartView extends React.Component {
	getNativeComponentName(){
		return 'RNKChartView'
	}
	getNativeComponentRef(){
		return this.nativeComponentRef
	}
	render(){
		return <RNKChartView {...this.props} ref={ref=>this.nativeComponentRef=ref} />;
	}
}

/*
KChartView.propTypes = {
	
}
*/

var RNKChartView = requireNativeComponent('RNKChartView',KChartView,{nativeOnly:{onSelect:true,onChange:true}});

export default ScrollEnhancer(HighlightEnhancer(ScaleEnhancer(MoveEnhancer(KChartView))))