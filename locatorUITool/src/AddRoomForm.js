/**
 * Created by cano on 9.11.2016.
 */
import React from 'react';
import { Col, CardTitle, Button, Input, InputGroup, InputGroupAddon} from 'reactstrap';
import BuildingDropdown from './BuildingDropdown';
import FloorDropdown from './FloorDropdown';
import $ from 'jquery';

var AddRoomForm = React.createClass({

    getInitialState: function () {
        return {

            refAddRoomBuilding : null,
            refAddRoomFloor : null,
            refAddRoomName : "",
            refAddRoomCapacity: "",
            nodeInfo : this.props.nodeInfo,
            disableAdd : this.props.disableAdd,
            selectedRoom : null

        };
    },

    componentDidMount() {
        this.loadRoomDetails(this.state.nodeInfo);
    },

    loadRoomDetails(node){
        if(!(node == null)){
            var buildingURL = 'http://localhost:8080/rooms/' + node.buildingId + '/' + node.name;

            this.serverRequest = $.get(buildingURL, function (result) {

                if(!(this.state.refAddRoomBuilding == null)) {
                    this.state.refAddRoomBuilding.updateBuilding(result.buildingId);
                    this.state.refAddRoomFloor.updateFloor(result.floorName);

                    this.setState({
                        refAddRoomName : result.name,
                        refAddRoomCapacity: result.capacity,
                        selectedRoom: result
                    });
                }

            }.bind(this));
        }
    },

    onSelectAddRoomSetBuilding : function(buildingData){
        this.state.refAddRoomFloor.loadCommentsFromServer('http://localhost:8080/floors/' + buildingData.buildingId);
        this.state.refAddRoomFloor.updateFloor("Choose Floor");
        this.state.refAddRoomFloor.disableDropdown(false);
    },

    onAddRoomNameInputChange(event) {
        this.setState({refAddRoomName: event.target.value});
    },

    onAddRoomCapacityInputChange(event) {
        this.setState({refAddRoomCapacity: event.target.value});
    },

    onAddRoomSubmit(){
        this.onRoomSubmitHandler('add');
    },

    onDeleteRoomSubmit(){
        this.onRoomSubmitHandler('delete');
    },

    onRoomSubmitHandler(type){

        var url = 'http://localhost:8080/rooms/new/room';
        if (type === "delete")
            url = 'http://localhost:8080/rooms/delete/room';

        var buildingName = this.state.refAddRoomBuilding.getCurrentBuilding().currentBuilding;
        var floorName = this.state.refAddRoomFloor.getCurrentFloor().currentFloor;
        var roomName = this.state.refAddRoomName;
        var roomCapacity = this.state.refAddRoomCapacity;

        var buildingData = this.state.refAddRoomBuilding.getBuildingData();
        var floorData = this.state.refAddRoomFloor.getFloorData();

        if( buildingName!== 'Choose Building' &&
            floorName!== 'Choose Floor' &&
            roomName !== '' &&
            roomCapacity !== '') {

            var rawData = null;

            if (type === "delete") {

                var selected = this.state.selectedRoom;
                rawData = {
                    name: selected.name,
                    type: selected.type,
                    capacity: selected.capacity,
                    assignedPeople: selected.assignedPeople,
                    buildingId: selected.buildingId,
                    roomId: selected.roomId,
                    styleTop: selected.styleTop,
                    styleLeft: selected.styleLeft,
                    floorName: selected.floorName
                };
            }
            else {

                rawData = {
                    name: roomName,
                    type: 'Development',
                    capacity: roomCapacity,
                    assignedPeople: 0,
                    buildingId: buildingData.selectedBuilding.buildingId,
                    roomId: 0,
                    styleTop: '0px',
                    styleLeft: '0px',
                    floorName: floorData.selectedFloor.floorName
                };
            }

            var jsonData = JSON.stringify(rawData);

            fetch(url,{
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: jsonData
            })
            .then((response) => {
                var messageContent = "Success! New Room Submitted.";
                this.props.onChange({response, messageContent});
            }).catch((response) => {
                var messageContent = "Error, New Room Not Submitted. Please Contact Your Administrator.";
                this.props.onChange({response, messageContent});
            });
        }
        else{
            this.props.onValidate();
        }
        this.state.refAddRoomBuilding.updateBuilding("Choose Building");
        this.state.refAddRoomFloor.updateFloor("Choose Floor");
        this.setState({
            refAddRoomName : "",
            refAddRoomCapacity : ""
        });
    },

    onSelectFloor : function() {

    },

    render:function() {
        return (

            <Col sm="4">
                {this.state.disableAdd === false ? <CardTitle>Add a Room</CardTitle> : null}
                {this.state.disableAdd === true ? <CardTitle>Room Details</CardTitle> : null}
                <hr/>
                <BuildingDropdown className="MyBuilding"
                                  url="http://localhost:8080/buildings"
                                  onChange={this.onSelectAddRoomSetBuilding}
                                  ref={(ref) => this.state.refAddRoomBuilding = ref}
                                  disabled={this.state.disableAdd}
                />
                <FloorDropdown  className="MyFloor"
                                onChange={this.onSelectFloor}
                                ref={(ref) => this.state.refAddRoomFloor = ref}
                                disabled={this.state.disableAdd}/>
                <InputGroup>
                    <InputGroupAddon>Room Name</InputGroupAddon>
                    <Input className="CoordinateInput"
                           placeholder="Type room name..."
                           value={this.state.refAddRoomName}
                           onChange={this.onAddRoomNameInputChange}
                           disabled={this.state.disableAdd}/>
                </InputGroup>
                <br />
                <InputGroup>
                    <InputGroupAddon>Room Capacity</InputGroupAddon>
                    <Input className="CoordinateInput"
                           placeholder="Type room capacity..."
                           value={this.state.refAddRoomCapacity}
                           onChange={this.onAddRoomCapacityInputChange}
                           disabled={this.state.disableAdd}/>
                </InputGroup>
                <br />
                {this.state.disableAdd === false ?
                    <Button id="AddRoomButton"
                            color="primary"
                            onClick={this.onAddRoomSubmit}>Submit</Button> : null }
                {this.state.disableAdd === true ?
                    <Button id="AddRoomButton"
                            color="primary"
                            onClick={this.onDeleteRoomSubmit}>Delete</Button> : null }
            </Col>
        );
    }
});

export default AddRoomForm;