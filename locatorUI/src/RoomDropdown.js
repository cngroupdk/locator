/**
 * Created by cano on 26.10.2016.
 */
import React from 'react';
import { Dropdown, DropdownToggle, DropdownMenu } from 'reactstrap';
import RoomDropdownItem from './RoomDropdownItem';
import $ from 'jquery';

var RoomDropdown = React.createClass({

    loadCommentsFromServer: function () {

        this.serverRequest = $.get(this.props.url, function (result) {
            this.setState({
                data: result
            });
        }.bind(this));

    },

    getInitialState: function() {
        return {
            roomIndex : -1,
            roomName : 'Select a Room',
            data: []
        };
    },

    getRoomData: function(){

        var selectedRoom = this.state.data[this.state.roomIndex];

        return{
            selectedRoom
        };
    },

    getCurrentRoom : function(){
        var currentRoom = this.state.roomName;
        return{
            currentRoom
        };
    },

    componentDidMount: function() {
        this.loadCommentsFromServer();
    },

    updateRoom : function(newName){
        this.setState(
            {roomName : newName}
        );
    },

    updateRoomIndex : function(newIndex){
        this.setState(
            {roomIndex : newIndex}
        );
    },

    onClickUpdateRoom : function(e){
        var data = {
            roomData : this.state.data[e.target.value],
            index : e.target.value
        };

        var newRoom = data.roomData.name + " @ " + data.roomData.floorName + " @ " + data.roomData.buildingId;

        this.props.onChange(data);
        this.updateRoom(newRoom);
        this.updateRoomIndex(e.target.value);
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
                <DropdownToggle className="roomDropdownToggle" caret color="primary">
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