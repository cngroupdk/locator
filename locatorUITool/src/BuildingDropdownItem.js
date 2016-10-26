/**
 * Created by cano on 26.10.2016.
 */
import React from 'react';
import { DropdownItem } from 'reactstrap';

var BuildingDropdownItem = React.createClass({

    render: function() {
        return (
            <DropdownItem className="buildingItem"
                          onClick={this.props.update}
                          value={this.props.buildingData.buildingGuid}>
                {this.props.buildingData.name}
            </DropdownItem>
        );
    }
});

export default BuildingDropdownItem;