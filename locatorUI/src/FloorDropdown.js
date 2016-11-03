/**
 * Created by cano on 20.10.2016.
 */
import React from 'react';
import { Dropdown, DropdownToggle, DropdownMenu } from 'reactstrap';
import FloorDropdownItem from './FloorDropdownItem';
import $ from 'jquery';

var FloorDropdown = React.createClass({

    getInitialState: function() {
        return {
            floorIndex : -1,
            floorName : 'Select a Floor',
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

    loadCommentsFromServer: function() {

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

    getCurrentFloor : function(){
        var currentFloor = this.state.floorName;
        return{
            currentFloor
        };
    },

    updateFloor : function(newName){
        this.setState(
            {floorName : newName}
        );
    },

    updateFloorIndex : function(newIndex){
        this.setState(
            {floorIndex : newIndex}
        );
    },

    onClickUpdateFloor : function(event){
        var data = {
            event,
            flData : this.state.data[event.target.value],
            index : event.target.value
        };

        var newFloor = data.flData.floorName + " @ " + data.flData.buildingId;
        this.props.onChange(data);
        this.updateFloor(newFloor);
        this.updateFloorIndex(event.target.value);
    },

    render: function() {
        var floorNodes = this.state.data.map(
            function (floor) {
                return (
                    <FloorDropdownItem className="floorDropdownItem"
                                       floorData={floor}
                                       key={floor.floorId}
                                       update={this.onClickUpdateFloor}/>
                );
            }
            , this);

        return (
            <Dropdown tether className="m-y-1" isOpen={this.state.dd4} toggle={() => { this.setState({ dd4: !this.state.dd4 })}}>
                <DropdownToggle disabled={this.props.disabled} className="floorDropdownToggle" caret color="primary">
                    {this.state.floorName}
                </DropdownToggle>
                <DropdownMenu className="floorDropdownMenu">
                    {floorNodes}
                </DropdownMenu>
            </Dropdown>
        );
    }
});

export default FloorDropdown;