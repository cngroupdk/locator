/**
 * Created by cano on 26.10.2016.
 */
import React from 'react';
import { Dropdown, DropdownToggle, DropdownMenu } from 'reactstrap';
import BuildingDropdownItem from './BuildingDropdownItem';
import $ from 'jquery';

var BuildingDropdown = React.createClass({

    getInitialState: function() {
        return {
            buildingName : 'Select a Building',
            buildingIndex: -1,
            data: [],
            disabled : false
        };
    },

    componentDidMount: function() {
        this.loadCommentsFromServer();
    },

    componentWillUnmount: function() {
        this.serverRequest.abort();
    },

    loadCommentsFromServer: function(url) {
        var newURL = '';

        if (url == null) {
            newURL = this.props.url;
        }
        else{
            newURL = url;
        }
        this.serverRequest = $.get(newURL, function (result) {
            this.setState({
                data: result
            });
        }.bind(this));

    },

    disableDropdown : function(newBool){
        this.setState(
            {disabled : newBool}
        );
    },

    getBuildingData: function(){

        var selectedBuilding = this.state.data[this.state.buildingIndex];

        return{
            selectedBuilding
        };
    },

    getCurrentBuilding : function(){
        var currentBuilding = this.state.buildingName;
        return{
            currentBuilding
        };
    },

    updateBuilding : function(newName){
        this.setState(
            {buildingName : newName}
        );
    },

    updateBuildingIndex : function(newIndex){
        this.setState(
            {buildingIndex : newIndex}
        );
    },

    onClickUpdateBuilding : function(event){

        var data = {
            event,
            selectedBuilding:this.state.data[event.target.value],
            selectedIndex: event.target.value
        }

        this.props.onChange(data);
        var newBuilding = data.selectedBuilding.name;
        this.updateBuilding(newBuilding);
        this.updateBuildingIndex(event.target.value);
    },

    render: function() {
        var buildingNodes = this.state.data.map(
            function (building) {
                return (
                    <BuildingDropdownItem className="buildingDropdownItem"
                                       buildingData={building}
                                       key={building.buildingGuid}
                                       update={this.onClickUpdateBuilding}/>
                );
            }
            , this);

        return (
            <Dropdown tether className="m-y-1" isOpen={this.state.dd4} toggle={() => { this.setState({ dd4: !this.state.dd4 })}}>
                <DropdownToggle disabled={this.props.disabled} className="buildingDropdownToggle" caret color="primary">
                    {this.state.buildingName}
                </DropdownToggle>
                <DropdownMenu className="buildingDropdownMenu">
                    {buildingNodes}
                </DropdownMenu>
            </Dropdown>
        );
    }


});

export default BuildingDropdown;