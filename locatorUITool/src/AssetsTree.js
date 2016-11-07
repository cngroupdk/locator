/**
 * Created by cano on 7.11.2016.
 */
import React from 'react';
import {TreeView} from 'react-bootstrap-treeview';

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

    render(){
        return (
            <TreeView data={this.state.assetsData} />
        );
    }

});

export default AssetsTree;