/**
 * Created by cano on 26.10.2016.
 */
import React from 'react';
import { Dropdown, DropdownToggle, DropdownMenu } from 'reactstrap';
import RoomDropdownItem from './RoomDropdownItem';
import $ from 'jquery';

var RoomDropdown = React.createClass({

    getInitialState: function() {
        return {
            roomIndex : -1,
            roomName : 'Select a Room',
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

    loadCommentsFromServer: function (url) {

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

    onClickUpdateRoom : function(event){
        var data = {
            event,
            roomData : this.state.data[event.target.value],
            index : event.target.value
        };

        var newRoom = data.roomData.name + " @ " + data.roomData.floorName + " @ " + data.roomData.buildingId;

        this.props.onChange(data);
        this.updateRoom(newRoom);
        this.updateRoomIndex(event.target.value);
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
                <DropdownToggle disabled={this.props.disabled} className="roomDropdownToggle" caret color="primary">
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