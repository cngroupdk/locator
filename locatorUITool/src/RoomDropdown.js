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
            roomName : 'Choose Room',
            data: []
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


    onClickUpdateRoom : function(e){

        var roomData = this.state.data[e.target.value];
        var newRoom = roomData.name;
        //this.props.onChange(e.target.value, roomData);
        this.updateFloor(newRoom);
    },

    render: function() {
        var roomNodes = this.state.data.map(
            function (room) {
                return (
                    <RoomDropdownItem className="roomDropdownItem"
                                          roomData={room}
                                          key={room.roomId}
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