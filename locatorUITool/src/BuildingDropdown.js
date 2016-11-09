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
            buildingIndex: -1,
            disabled : true,
            buildingName : 'Choose Building',
            data: []
        };
    },

    componentDidMount: function() {
        this.loadCommentsFromServer();
    },

    componentWillUnmount: function() {

    },

    loadCommentsFromServer: function () {

        this.serverRequest = $.get(this.props.url, function (result) {
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

    onClickUpdateBuilding : function(e){

        var buildingData = this.state.data[e.target.value];
        var newBuilding = buildingData.name;
        this.props.onChange(buildingData);
        this.updateBuilding(newBuilding);
        this.updateBuildingIndex(e.target.value);
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