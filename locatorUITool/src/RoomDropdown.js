/**
 * Created by cano on 26.10.2016.
 */
import React from 'react';
import { Dropdown, DropdownToggle, DropdownMenu } from 'reactstrap';
import RoomDropdownItem from './RoomDropdownItem';
import $ from 'jquery';

var RoomDropdown = React.createClass({

    loadCommentsFromServer: function (url) {

        this.serverRequest = $.get(url, function (result) {
            this.setState({
                data: result
            });
        }.bind(this));

    },

    getInitialState: function() {
        return {
            selectedIndex : -1,
            disabled : true,
            roomName : 'Choose Room',
            data: []
        };
    },

    getRoomSelectedData: function(){

        var selectedRoom = this.state.data[this.state.selectedIndex];

        return{
            selectedRoom
        };
    },

    componentDidMount: function() {

    },

    enableDropdown : function(newBool){
        this.setState(
            {disabled : newBool}
        );
    },

    updateRoom : function(newName){
        this.setState(
            {roomName : newName}
        );
    },

    updateSelectedIndex : function(newIndex){
        this.setState(
            {selectedIndex : newIndex}
        );
    },

    onClickUpdateRoom : function(e){

        var roomData = this.state.data[e.target.value];
        var newRoom = roomData.name;
        this.updateRoom(newRoom);
        this.updateSelectedIndex(e.target.value);
    },

    render: function() {
        var counter=0;

        var roomNodes = this.state.data.map(
            function (room) {
                return (
                    <RoomDropdownItem   className="roomDropdownItem"
                                        roomData={room}
                                        key={counter}
                                        counter={counter++}
                                        update={this.onClickUpdateRoom}/>
                );
            }
            , this);

        return (
            <Dropdown tether className="m-y-1" isOpen={this.state.dd4} toggle={() => { this.setState({ dd4: !this.state.dd4 })}}>
                <DropdownToggle disabled={this.state.disabled} className="roomDropdownToggle" caret color="primary">
                    {this.state.roomName}
                </DropdownToggle>
                <DropdownMenu className="roomDropdownMenu">
                    {roomNodes}
                </DropdownMenu>
            </Dropdown>
        );
    }

});

export default RoomDropdown;