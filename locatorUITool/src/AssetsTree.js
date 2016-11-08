/**
 * Created by cano on 7.11.2016.
 */
import React from 'react';
import {Treebeard, decorators, Toggle} from 'react-treebeard';

var AssetsTree = React.createClass({

    getInitialState: function() {
        return {
            assetsData : this.props.assetsData,
            cursor: null
        };
    },

    updateNodes: function(data) {
        this.setState({
            assetsData : data
        });
    },

    onToggle(node, toggled){
        if(this.state.cursor){
            this.state.cursor.active = false;
        }
        node.active = true;
        if(node.children){
            node.toggled = toggled;
        }
        this.setState({ cursor: node });
    },

    render(){

        decorators.Header = (props) => {
            const style = props.style;
            return (
                <div style={style.base} onClick={props.onClick}>
                    <div style={style.title}>
                        {props.node.name}
                    </div>
                </div>
            );
        };

        return (

            <Treebeard decorators={decorators}
                       data={this.state.assetsData}
                       onToggle={this.onToggle}/>
        );
    }

});

export default AssetsTree;