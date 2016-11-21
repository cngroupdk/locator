/**
 * Created by cano on 9.11.2016.
 */
import React from 'react';
import { Col, Card, CardTitle, Button, Input, InputGroup, InputGroupAddon, Modal, ModalHeader, ModalBody} from 'reactstrap';
import BuildingDropdown from './BuildingDropdown';
import FloorDropdown from './FloorDropdown';
import RoomDropdown from './RoomDropdown';
import ImageMap from './ImageMap';
import $ from 'jquery';

var AddRoomCoordForm = React.createClass({

    getInitialState: function () {
        return {

            notificationSystem : null,
            refTree : null,
            modal : false,
            nodeInfo : this.props.nodeInfo,

            refCoordBuilding : null,
            refCoordFloor : null,
            refCoordRoom : null,
            refCoordX : "",
            refCoordY : "",
            refCoordMap : null,
            refCoordIMG : null,

            assetsData : [
                {
                    name: 'Loading...',
                    toggled: false,
                }
            ],

            openBuildingAdd : null,
            openFloorAdd : null,
            openRoomAdd : null
        };
    },

    componentDidMount() {
        this.loadRoomDetails(this.state.nodeInfo);
    },

    loadRoomDetails(node){
        if(!(node == null)){

            var url = 'http://localhost:8080/rooms/' + node.buildingId + '/' + node.name;

            this.serverRequest = $.get(url, function (result) {

                if(!(this.state.refCoordBuilding == null)) {

                    this.state.refCoordBuilding.updateBuilding(result.buildingId);
                    this.state.refCoordFloor.updateFloor(result.floorName);
                    this.state.refCoordRoom.updateRoom(result.name);

                    this.setState({
                        selectedRoom: result
                    });
                }

            }.bind(this));

            url = 'http://localhost:8080/floors/' + node.buildingId + '/' + node.floorName;
            this.serverRequest = $.get(url, function (result) {

                if(!(this.state.refCoordBuilding == null)) {
                    this.state.refCoordMap.setMapPath('http://localhost:8080' + result.floorplanUrl);
                    var style = { visibility: 'visible' };
                    this.state.refCoordMap.setStyleProps(style);
                }

            }.bind(this));

        }
    },

    onXCoordinateInputChange(event) {
        this.setState({refCoordX: event.target.value});
    },

    onYCoordinateInputChange(event) {
        this.setState({refCoordY: event.target.value});
    },


    onSelectCoordSetBuilding : function(buildingData){

        this.state.refCoordFloor.loadCommentsFromServer('http://localhost:8080/floors/' + buildingData.buildingId);
        this.state.refCoordFloor.disableDropdown(false);
        this.state.refCoordFloor.updateFloor("Choose Floor");
        this.state.refCoordRoom.disableDropdown(false);
        this.state.refCoordRoom.updateRoom("Choose Room");

        var style = { visibility: 'hidden' };
        this.state.refCoordMap.setStyleProps(style);
        this.state.refCoordIMG.setStyleProps(style);

    },

    onSelectCoordSetFloor : function(flData){

        this.state.refCoordRoom.loadCommentsFromServer('http://localhost:8080/rooms/byfloor/' + flData.buildingId + '/' + flData.floorName);
        this.state.refCoordRoom.disableDropdown(false);
        this.state.refCoordRoom.updateRoom("Choose Room");

        this.state.refCoordMap.setMapPath('http://localhost:8080' + flData.floorplanUrl);
        var style = { visibility: 'visible' };
        this.state.refCoordMap.setStyleProps(style);
    },

    setXYCoord: function(data){

        var rects = data.target.getClientRects();
        var x = data.clientX - rects[0].left - 35;
        var y = (data.clientY - rects[0].top) - (rects[0].bottom - rects[0].top) - 40;

        if(x > 0.5){
            x = Math.ceil(x);
        }
        else{
            x = Math.floor(x);
        }

        if(y > 0.5){
            y = Math.ceil(y);
        }
        else{
            y = Math.floor(y);
        }

        this.setState({refCoordX: x, refCoordY: y});

        return{
            x,y
        };

    },

    onMouseClickHandler: function(event){

        var clientX = event.clientX;
        var clientY = event.clientY;
        var target = event.target;

        var className = event.target.className;

        if(className === "Icon"){
            var parent = event.target.parentElement.parentElement;
            target = parent.children[0].children[0];
        }

        var XY = this.setXYCoord({clientX, clientY, target});

        var style =
        {
            transition: 'all 0.5s',
            visibility: 'visible',
            maxWidth: '25px',
            position: 'relative',
            top: XY.y + 15,
            left: XY.x + 23
        };

        this.state.refCoordIMG.setMapPath('http://localhost:8080/marker-location.png');
        this.state.refCoordIMG.setStyleProps(style);
    },

    toggle() {
        var buildingName = this.state.refCoordBuilding.getCurrentBuilding().currentBuilding;
        var floorName = this.state.refCoordFloor.getCurrentFloor().currentFloor;
        var roomName = this.state.refCoordRoom.getCurrentRoom().currentRoom;
        var top = this.state.refCoordY;
        var left = this.state.refCoordX;

        if( buildingName!== 'Choose Building' &&
            floorName!== 'Choose Floor' &&
            roomName !== 'Choose Room' &&
            top!== '' &&
            left!== '')
        {
            this.setState({
                modal: !this.state.modal
            });
        }
        else{
            this.props.onValidate();
        }

    },

    onSetCoordSubmitHandler: function () {

        var buildingName = this.state.refCoordBuilding.getCurrentBuilding().currentBuilding;
        var floorName = this.state.refCoordFloor.getCurrentFloor().currentFloor;
        var roomName = this.state.refCoordRoom.getCurrentRoom().currentRoom;
        var top = this.state.refCoordY;
        var left = this.state.refCoordX;

        var buildingData = this.state.refCoordBuilding.getBuildingData();
        var floorData = this.state.refCoordFloor.getFloorData();

        if( buildingName!== 'Choose Building' &&
            floorName!== 'Choose Floor' &&
            roomName !== 'Choose Room' &&
            top!== '' &&
            left!== '')
        {

            var rawData = {
                name: roomName,
                type: 'Development',
                capacity: 0,
                assignedPeople: 0,
                buildingId: buildingName,
                roomId: 0,
                styleTop: top + 'px',
                styleLeft: left + 'px',
                floorName: floorName
            };

            var jsonData = JSON.stringify(rawData);
            fetch('http://localhost:8080/rooms/update/room',{
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
                var messageContent = "New Room XY Coordinates Submitted.";
                this.props.onChange({response, messageContent});
            }).catch((response) => {
                var messageContent = "Error, New Room XY Coordinates Not Submitted. Please Contact Your Administrator.";
                this.props.onChange({response, messageContent});
            });
        }
        else{
            this.props.onValidate();
        }

        this.state.refCoordBuilding.updateBuilding("Choose Building");
        this.state.refCoordFloor.updateFloor("Choose Floor");
        this.state.refCoordRoom.updateRoom("Choose Room");
        this.state.refCoordMap.setMapPath("");
        this.state.refCoordIMG.setMapPath("");
        this.setState({
            refCoordX : "",
            refCoordY : ""
        });
    },

    render:function() {
        return (

            <Card block>
                <Col xs="3">
                    <CardTitle>Set Room Coordinates</CardTitle>
                    <hr/>
                    <BuildingDropdown className="MyBuilding"
                                      url="http://localhost:8080/buildings"
                                      onChange={this.onSelectCoordSetBuilding}
                                      ref={(ref) => this.state.refCoordBuilding = ref}
                                      disabled={true}/>
                    <FloorDropdown className="MyFloor"
                                   onChange={this.onSelectCoordSetFloor}
                                   ref={(ref) => this.state.refCoordFloor = ref}/>
                    <RoomDropdown disabled={true}
                                  className="MyRoom"
                                  ref={(ref) => this.state.refCoordRoom = ref}/>
                    <br />
                    <InputGroup>
                        <InputGroupAddon>X</InputGroupAddon>
                        <Input className="CoordinateInput"
                               disabled={true}
                               placeholder="New X Coordinate value"
                               value={this.state.refCoordX}/>
                    </InputGroup>
                    <br />
                    <InputGroup>
                        <InputGroupAddon>Y</InputGroupAddon>
                        <Input className="CoordinateInput"
                               disabled={true}
                               placeholder="New Y Coordinate value"
                               value={this.state.refCoordY}/>
                    </InputGroup>
                    <br />
                    <Button id="RoomCoordButton"
                            color="primary"
                            onClick={this.onSetCoordSubmitHandler}>Submit</Button>
                </Col>
                <Col xs="5">
                    <ImageMap id="floorMap"
                              clickEnter={this.onMouseClickHandler}
                              className="FloorMap"
                              ref={(ref) => this.state.refCoordMap = ref}/>
                    <ImageMap clickEnter={this.onMouseClickHandler}
                              className="Icon"
                              ref={(ref) => this.state.refCoordIMG = ref}/>
                </Col>
            </Card>
        );
    }
});

export default AddRoomCoordForm;