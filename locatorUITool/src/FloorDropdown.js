/**
 * Created by cano on 20.10.2016.
 */
import React from 'react';
import { Dropdown, DropdownToggle, DropdownMenu } from 'reactstrap';
import FloorDropdownItem from './FloorDropdownItem';
import $ from 'jquery';

var FloorDropdown = React.createClass({

    loadCommentsFromServer: function(url) {

        this.serverRequest = $.get(url, function (result) {
            this.setState({
                data: result
            });
        }.bind(this));

    },

    enableDropdown : function(newBool){
        this.setState(
            {disabled : newBool}
        );
    },

    updateFloor : function(newName){
        this.setState(
            {floorName : newName}
        );
    },

    updateSelectedIndex : function(newIndex){
        this.setState(
            {selectedIndex : newIndex}
        );
    },

    onClickUpdateFloor : function(e){

        var flData = this.state.data[e.target.value];
        var newFloor = flData.floorName + " @ " + flData.buildingId;
        this.props.onChange(flData);
        this.updateFloor(newFloor);
        this.updateSelectedIndex(e.target.value);
    },

    getInitialState: function() {
        return {
            selectedIndex : -1,
            disabled : true,
            floorName : 'Choose Floor',
            data: []
        };
    },

    getFloorSelectedData: function(){
        var selectedFloor = this.state.data[this.state.selectedIndex];

        return{
            selectedFloor
        };
    },

    componentDidMount: function() {

    },

    render: function() {

        var counter=0;
        var floorNodes = this.state.data.map(
            function (floor) {
                return (
                    <FloorDropdownItem  className="floorDropdownItem"
                                        floorData={floor}
                                        key={counter}
                                        counter={counter++}
                                        update={this.onClickUpdateFloor}
                />
                );
            }
            , this);

        return (
            <Dropdown tether className="m-y-1" isOpen={this.state.dd4} toggle={() => { this.setState({ dd4: !this.state.dd4 })}}>
                <DropdownToggle disabled={this.state.disabled} className="floorDropdownToggle" caret color="primary">
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