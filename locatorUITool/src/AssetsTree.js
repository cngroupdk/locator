/**
 * Created by cano on 7.11.2016.
 */
import React from 'react';
import $ from 'jquery';
import {Treebeard, decorators} from 'react-treebeard';

var AssetsTree = React.createClass({

    getInitialState: function() {
        return {
            assetsData : this.props.assetsData,
            cursor: null
        };
    },


    componentDidMount: function() {
        this.loadCommentsFromServer('http://localhost:8080/tree/');
    },

    componentWillUnmount: function() {
        this.serverRequest.abort();
    },

    loadCommentsFromServer: function(url) {

        this.serverRequest = $.get(url, function (result) {
            this.setState({
                assetsData: result
            });
        }.bind(this));

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

        var data = {node, toggled};

        this.props.onChange(data);
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