/**
 * Created by cano on 26.10.2016.
 */

import React from 'react';

var ImageMap = React.createClass({

    getInitialState: function() {
        return {
            mapPath : this.props.imgSource
        };
    },

    setMapPath : function(newPath){
        this.setState(
            {mapPath : newPath}
        );
    },

    setStyleProps : function(style){
        this.setState(
            {style : style}
        );
    },

    render: function() {

        return (
            <div>
                <img className={this.props.className}
                     src={this.state.mapPath}
                     style={this.state.style}
                     onClick={this.props.clickEnter}
                     alt={this.props.alt}
                />
            </div>
        );
    }
});

export default ImageMap;