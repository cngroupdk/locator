/**
 * Created by cano on 26.10.2016.
 */
import React from 'react';
import { Dropdown, DropdownToggle, DropdownMenu } from 'reactstrap';
import BuildingDropdownItem from './BuildingDropdownItem';
import $ from 'jquery';

var BuildingDropdown = React.createClass({

    loadCommentsFromServer: function () {

        this.serverRequest = $.get(this.props.url, function (result) {
            this.setState({
                data: result
            });
        }.bind(this));

    },

    getInitialState: function() {
        return {
            buildingName : 'Choose Building',
            data: []
        };
    },

    componentDidMount: function() {
        this.loadCommentsFromServer();
    },

    updateBuilding : function(newName){
        this.setState(
            {buildingName : newName}
        );
    },

    onClickUpdateBuilding : function(e){

        var buildingData = this.state.data[e.target.value];
        var newBuilding = buildingData.name;
        this.props.onChange(buildingData);
        this.updateBuilding(newBuilding);
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