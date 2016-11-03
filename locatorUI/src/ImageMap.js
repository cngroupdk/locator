/**
 * Created by cano on 20.10.2016.
 */
import React from 'react';
import {CardImg} from 'reactstrap';

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
                <CardImg id={this.props.id}
                         className="cardImg"
                         src={this.state.mapPath}
                         style={this.state.style}
                         onClick={this.props.clickHandler}
                         onMouseEnter={this.props.hoverEnter}
                         onMouseLeave={this.props.hoverLeave}
                         alt={this.props.alt}
                 />
            </div>
        );
    }
});

export default ImageMap;
