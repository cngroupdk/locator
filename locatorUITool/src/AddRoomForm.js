/**
 * Created by cano on 9.11.2016.
 */
import React from 'react';
import { Col, CardTitle, Button, Input, InputGroup, InputGroupAddon} from 'reactstrap';
import BuildingDropdown from './BuildingDropdown';
import FloorDropdown from './FloorDropdown';

var AddRoomForm = React.createClass({

    getInitialState: function () {
        return {

            refAddRoomBuilding : null,
            refAddRoomFloor : null,
            refAddRoomName : "",
            refAddRoomCapacity: "",

        };
    },

    componentDidMount() {
        this.state.refAddRoomFloor.disableDropdown(true);
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


    onAddRoomSubmitHandler(){

        var buildingName = this.state.refAddRoomBuilding.getCurrentBuilding().currentBuilding;
        var floorName = this.state.refAddRoomFloor.getCurrentFloor().currentFloor;
        var roomName = this.state.refAddRoomName;
        var roomCapacity = this.state.refAddRoomCapacity;

        var buildingData = this.state.refAddRoomBuilding.getBuildingData();
        var floorData = this.state.refAddRoomFloor.getFloorData();

        if( buildingName!== 'Choose Building' &&
            floorName!== 'Choose Floor' &&
            roomName !== '' &&
            roomCapacity !== ''){

            var rawData = {
                name : roomName,
                type : 'Development',
                capacity : roomCapacity,
                assignedPeople : 0,
                buildingId : buildingData.selectedBuilding.buildingId,
                roomId : 0,
                styleTop: '0px',
                styleLeft: '0px',
                floorName : floorData.selectedFloor.floorName
            };

            var jsonData = JSON.stringify(rawData);

            fetch('http://localhost:8080/rooms/new/room',{
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
                <CardTitle>Add a Room</CardTitle>
                <hr/>
                <BuildingDropdown className="MyBuilding"
                                  url="http://localhost:8080/buildings"
                                  onChange={this.onSelectAddRoomSetBuilding}
                                  ref={(ref) => this.state.refAddRoomBuilding = ref}
                                  disabled={false}
                />
                <FloorDropdown  className="MyFloor"
                                onChange={this.onSelectFloor}
                                ref={(ref) => this.state.refAddRoomFloor = ref} />
                <InputGroup>
                    <InputGroupAddon>Room Name</InputGroupAddon>
                    <Input className="CoordinateInput"
                           placeholder="Type room name..."
                           value={this.state.refAddRoomName}
                           onChange={this.onAddRoomNameInputChange}/>
                </InputGroup>
                <br />
                <InputGroup>
                    <InputGroupAddon>Room Capacity</InputGroupAddon>
                    <Input className="CoordinateInput"
                           placeholder="Type room capacity..."
                           value={this.state.refAddRoomCapacity}
                           onChange={this.onAddRoomCapacityInputChange}/>
                </InputGroup>
                <br />
                <Button id="AddRoomButton"
                        color="primary"
                        onClick={this.onAddRoomSubmitHandler}>Submit</Button>
            </Col>
        );
    }
});

export default AddRoomForm;