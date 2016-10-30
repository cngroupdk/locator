/**
 * Created by cano on 26.10.2016.
 */
import React from 'react';
import { DropdownItem } from 'reactstrap';

var RoomDropdownItem = React.createClass({

    render: function() {
        return (
            <DropdownItem className="roomItem"
                          onClick={this.props.update}
                          value={this.props.counter}>
                {this.props.roomData.name + " @ " + this.props.roomData.floorName + " @ " + this.props.roomData.buildingId}
            </DropdownItem>
        );
    }
});

export default RoomDropdownItem;