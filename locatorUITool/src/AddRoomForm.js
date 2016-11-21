/**
 * Created by cano on 9.11.2016.
 */
import React from 'react';
import { CardTitle, Button, Input, InputGroup, InputGroupAddon, Modal, ModalHeader, ModalBody} from 'reactstrap';
import BuildingDropdown from './BuildingDropdown';
import FloorDropdown from './FloorDropdown';
import $ from 'jquery';

var AddRoomForm = React.createClass({

    getInitialState: function () {
        return {
            modal : false,
            refAddRoomBuilding : null,
            refAddRoomFloor : null,
            refAddRoomName : "",
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
            var buildingURL = 'http://localhost:8080/floors/' + node.buildingId + '/' + node.name;

            if(this.props.addingRoom == null) {
                buildingURL = 'http://localhost:8080/rooms/' + node.buildingId + '/' + node.name;
            }

            this.serverRequest = $.get(buildingURL, function (result) {

                if(!(this.state.refAddRoomBuilding == null)) {
                    this.state.refAddRoomBuilding.updateBuilding(result.buildingId);
                    this.state.refAddRoomFloor.updateFloor(result.floorName);

                    if(this.props.addingRoom == null) {
                        this.setState({
                            refAddRoomName : result.name,
                            selectedRoom: result
                        });
                    }
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

    onAddRoomSubmit(){
        this.onRoomSubmitHandler({type:'add', url:'http://localhost:8080/rooms/new/room',
        messageContent:'Room Added'});
    },

    onDeleteRoomSubmit(){
        this.onRoomSubmitHandler({type:'delete', url:'http://localhost:8080/rooms/delete/room',
        messageContent:'Room Deleted'});
    },

    toggle() {
        var buildingName = this.state.refAddRoomBuilding.getCurrentBuilding().currentBuilding;
        var floorName = this.state.refAddRoomFloor.getCurrentFloor().currentFloor;
        var roomName = this.state.refAddRoomName;

        if( buildingName!== 'Choose Building' &&
            floorName!== 'Choose Floor' &&
            roomName !== '') {
            this.setState({
                modal: !this.state.modal
            });
        }
        else{
            this.props.onValidate();
        }

    },

    onRoomSubmitHandler(data){

        var url = data.url;
        var type = data.type;
        var messageContent = data.messageContent;

        var buildingName = this.state.refAddRoomBuilding.getCurrentBuilding().currentBuilding;
        var floorName = this.state.refAddRoomFloor.getCurrentFloor().currentFloor;
        var roomName = this.state.refAddRoomName;
        var passedValidation = false;

        var buildingData = this.state.refAddRoomBuilding.getBuildingData();
        var floorData = this.state.refAddRoomFloor.getFloorData();

        if( buildingName!== 'Choose Building' &&
            floorName!== 'Choose Floor' &&
            roomName !== '') {

            var rawData = null;

            if (type === "delete") {

                var selected = this.state.selectedRoom;
                rawData = {
                    name: selected.name,
                    type: selected.type,
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
                    buildingId: buildingName,
                    roomId: 0,
                    styleTop: '0px',
                    styleLeft: '0px',
                    floorName: floorName
                };
            }

            var jsonData = JSON.stringify(rawData);

            fetch(url,{
                method: 'POST',
                mode: 'cors',
                redirect: 'follow',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: jsonData
            })
            .then((response) => {
                this.props.onChange({response, messageContent});
                passedValidation = true;
            }).catch((response) => {
                var messageContent = "Error, Process Not Completed. Please Contact Your Administrator.";
                this.props.onChange({response, messageContent});
            });
        }
        else{
            this.props.onValidate();
        }

        if(passedValidation === true){
            this.state.refAddRoomBuilding.updateBuilding("Choose Building");
            this.state.refAddRoomFloor.updateFloor("Choose Floor");
            this.setState({
                refAddRoomName : ""
            });
        }
    },

    onSelectFloor : function() {

    },

    render:function() {
        return (

            <div>
                {this.state.disableAdd === false ? <CardTitle>Add a Room</CardTitle> : null}
                {this.state.disableAdd === true ? <CardTitle>Room Details</CardTitle> : null}
                <hr/>
                <BuildingDropdown className="MyBuilding"
                                  url="http://localhost:8080/buildings"
                                  onChange={this.onSelectAddRoomSetBuilding}
                                  ref={(ref) => this.state.refAddRoomBuilding = ref}
                                  disabled={true}
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
                {this.state.disableAdd === false ?
                    <Button id="AddRoomButton"
                            color="primary"
                            onClick={this.onAddRoomSubmit}>Submit</Button> : null }
                {this.state.disableAdd === true ?
                    <Button id="AddRoomButton"
                            color="primary"
                            onClick={this.toggle}>Delete</Button> : null }
                <Modal id="AddBuilding"
                       isOpen={this.state.modal}
                       toggle={this.toggle}
                >
                    <ModalHeader>Confirmation</ModalHeader>
                    <ModalBody>
                        <p>Are you sure?</p>
                        {this.state.disableAdd === true ?
                            <Button id="EditLocationButton"
                                    onClick={this.onDeleteRoomSubmit}>
                                Submit Deletion
                            </Button> : null}
                        <Button id="EditLocationButton"
                                onClick={this.toggle}>
                            Cancel
                        </Button>
                    </ModalBody>
                </Modal>
            </div>
        );
    }
});

export default AddRoomForm;