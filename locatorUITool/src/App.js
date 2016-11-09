import React from 'react';
import NotificationSystem from 'react-notification-system';
import { Container, Row, Col, Card, Cardblock, CardTitle, Button, Input, InputGroup, InputGroupAddon, Jumbotron} from 'reactstrap';
import BuildingDropdown from './BuildingDropdown';
import FloorDropdown from './FloorDropdown';
import RoomDropdown from './RoomDropdown';
import ImageMap from './ImageMap';
import AssetsTree from './AssetsTree';
import './App.css';

var App = React.createClass({

    getInitialState: function () {
        return {

            notificationSystem : null,

            refTree : null,
            refAddBuildingId : "",
            refAddBuildingName : "",
            refAddBuildingCity : "",
            refAddBuildingStreet : "",
            refAddBuildingNumber : "",
            refAddBuildingPostalCode : "",

            refAddRoomBuilding : null,
            refAddRoomFloor : null,
            refAddRoomName : "",
            refAddRoomCapacity: "",

            refAddFloorBuilding: null,
            refAddFloorName : "",
            refAddFloorRoomNumber : "",
            refAddFloorURL: "",

            refCoordBuilding : null,
            refCoordFloor : null,
            refCoordRoom : null,
            refCoordX : "",
            refCoordY : "",
            refCoordMap : null,
            refCoordIMG : null,

            AddSubmitDisabled: true,
            CoordSubmitDisabled: true,
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

    },

    onSelectAddRoomSetBuilding : function(buildingData){
        this.state.refAddRoomFloor.loadCommentsFromServer('http://localhost:8080/floors/' + buildingData.buildingId);
        this.state.refAddRoomFloor.disableDropdown(false);
        this.state.refAddRoomFloor.updateFloor("Choose Floor");
    },

    onSelectAddFloorSetBuilding : function(buildingData){
    },

    onSelectAddSetFloor : function(flData){

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

    onAddRoomNameInputChange(event) {
        this.setState({refAddRoomName: event.target.value});
    },

    onRoomCapacityInputChange(event) {
        this.setState({refAddRoomCapacity: event.target.value});
    },

    onAddFloorNameInputChange(event) {
        this.setState({refAddFloorName: event.target.value});
    },

    onAddFloorRoomNumberInputChange(event) {
        this.setState({refAddFloorRoomNumber: event.target.value});
    },

    onAddFloorURLInputChange(event) {
        this.setState({refAddFloorURL: event.target.value});
    },

    onAddBuildingIdInputChange(event){
        this.setState({refAddBuildingId: event.target.value});
    },

    onAddBuildingNameInputChange(event){
      this.setState({refAddBuildingName: event.target.value});
    },

    onAddBuildingCityInputChange(event){
        this.setState({refAddBuildingCity: event.target.value});
    },

    onAddBuildingStreetInputChange(event){
        this.setState({refAddBuildingStreet: event.target.value});
    },


    onAddBuildingNumberInputChange(event){
        this.setState({refAddBuildingNumber: event.target.value});
    },

    onAddBuildingPostalInputChange(event){
        this.setState({refAddBuildingPostalCode: event.target.value});
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
                buildingId: buildingData.selectedBuilding.buildingId,
                roomId: 0,
                styleTop: top + 'px',
                styleLeft: left + 'px',
                floorName: floorData.selectedFloor.floorName
            };

            var jsonData = JSON.stringify(rawData);
            fetch('http://localhost:8080/rooms/update/room',{
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: jsonData
            })
            .then((response) => {

                var message;
                if(response.status === 200) {
                    message = {
                        content: "Success! New Location for the Employee Submitted.",
                        type: "success"
                    };
                }
                else{
                    message = {
                        content: "Error: New Location not updated. Contact your Administrator.",
                        type: "error"
                    };
                }

                this.addNotification(message);
            }).catch((error) => {
                console.error(error);
            });
        }
        else{
            var message = { content: "Please fill all parameters before clicking \"Submit\"",
                type: "info"};
            this.addNotification(message);
        }
    },

    onAddBuildingSubmitHandler(){
        var id = this.state.refAddBuildingId;
        var name = this.state.refAddBuildingName;
        var city = this.state.refAddBuildingCity;
        var street = this.state.refAddBuildingStreet;
        var number = this.state.refAddBuildingNumber;
        var pc = this.state.refAddBuildingPostalCode;

        if(id !== "" && name !== "" && city !== "" && street !== "" && number !== "" && pc !== "")
        {
            var rawData = {
                buildingGuid : -1,
                buildingId : id,
                type : "Office Space",
                name : name,
                city : city,
                postalCode : pc,
                streetName : street,
                streetNumber : number
            };

            var jsonData = JSON.stringify(rawData);

            fetch('http://localhost:8080/buildings/new/building',{
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: jsonData
            })
            .then((response) => {

                var message;
                if(response.status === 200) {
                    message = {
                        content: "Success! New Building Submitted.",
                        type: "success"
                    };

                    this.state.refTree.loadCommentsFromServer('http://localhost:8080/tree/');
                    this.setState({
                        openBuildingAdd : null,
                        openFloorAdd : null,
                        openRoomAdd : null,
                        refAddBuildingId : "",
                        refAddBuildingName : "",
                        refAddBuildingCity : "",
                        refAddBuildingStreet : "",
                        refAddBuildingNumber : "",
                        refAddBuildingPostalCode : ""
                    });

                }
                else{
                    message = {
                        content: "Error: New Building not added. Contact your Administrator.",
                        type: "error"
                    };
                }

                this.addNotification(message);
            })
            .catch((error) => {
                console.error(error);
            });
        }
        else{
            var message = { content: "Please fill all parameters before clicking \"Submit\"",
                type: "info"};
            this.addNotification(message);
        }



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

                var message;
                if(response.status === 200) {
                    message = {
                        content: "Success! New Location for the Employee Submitted.",
                        type: "success"
                    };
                }
                else{
                    message = {
                        content: "Error: New Location not updated. Contact your Administrator.",
                        type: "error"
                    };
                }

                this.addNotification(message);
            })
            .catch((error) => {
                console.error(error);
            });
        }
        else{
            var message = { content: "Please fill all parameters before clicking \"Submit\"",
                type: "info"};
            this.addNotification(message);
        }

    },

    onAddFloorSubmitHandler(){

        var buildingName = this.state.refAddFloorBuilding.getCurrentBuilding().currentBuilding;
        var floorName = this.state.refAddFloorName;
        var floorRoomsNumber = this.state.refAddFloorRoomNumber;
        var floorURL = this.state.refAddFloorURL;

        var buildingData = this.state.refAddFloorBuilding.getBuildingData();

        if( buildingName!== 'Choose Building' &&
            floorName !== '' &&
            floorURL !== '' &&
            floorRoomsNumber !== ''){

            var rawData = {
                floorId : 0,
                floorName : floorName,
                roomsNumber : floorRoomsNumber,
                floorplanUrl : '/' + floorURL,
                type : 'General',
                buildingId : buildingData.selectedBuilding.buildingId
            };

            var jsonData = JSON.stringify(rawData);

            fetch('http://localhost:8080/floors/new/floor',{
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: jsonData
            })
            .then((response) => {

                var message;

                if(response.status === 200) {
                    message = {
                        content: "Success! New Location for the Employee Submitted.",
                        type: "success"
                    };
                }
                else{
                    message = {
                        content: "Error: New Location not updated. Contact your Administrator.",
                        type: "error"
                    };
                }

                this.addNotification(message);

            }).catch((error) => {
                console.error(error);
            });
        }
        else{
            var message = { content: "Please fill all parameters before clicking \"Submit\"",
                            type: "info"};
            this.addNotification(message);
        }
    },

    onXCoordinateInputChange(event) {
        this.setState({refCoordX: event.target.value});
    },

    onYCoordinateInputChange(event) {
        this.setState({refCoordY: event.target.value});
    },

    setFloorplanPath : function(buildingId, floorName, refToImage){

        fetch('http://localhost:8080/floors/' + buildingId + '/' + floorName).then((response) => {return response.json()}).then(function(emplData){
            refToImage.setMapPath('http://localhost:8080' + emplData.floorplanUrl);
        }).catch((error) => {
            console.error(error);
        });
    },

    addNotification: function(notificationMessage) {

        this.state.notificationSystem.addNotification({
            message: notificationMessage.content,
            level: notificationMessage.type
        });
    },

    doSomething: function (event) {

        var buildingFormOpenStatus = event.node.buildingGuid;
        var floorFormOpenStatus = event.node.floorId;
        var roomFormOpenStatus = event.node.roomId;

        this.setState({
            openBuildingAdd: buildingFormOpenStatus,
            openFloorAdd: floorFormOpenStatus,
            openRoomAdd: roomFormOpenStatus
        });

    },

    openAddBuildingForm: function() {
        return(
            <Col sm="4">
                <CardTitle>Add a Building</CardTitle>
                <hr/>
                <InputGroup>
                    <InputGroupAddon>Building Id</InputGroupAddon>
                    <Input className="AddBuilding"
                           placeholder="Type building ID..."
                           value={this.state.refAddBuildingId}
                           onChange={this.onAddBuildingIdInputChange}/>
                </InputGroup>
                <br />
                <InputGroup>
                    <InputGroupAddon>Building Name</InputGroupAddon>
                    <Input className="AddBuilding"
                           placeholder="Type building name..."
                           value={this.state.refAddBuildingName}
                           onChange={this.onAddBuildingNameInputChange}/>
                </InputGroup>
                <br />
                <InputGroup>
                    <InputGroupAddon>City</InputGroupAddon>
                    <Input className="AddBuilding"
                           placeholder="Type city..."
                           value={this.state.refAddBuildingCity}
                           onChange={this.onAddBuildingCityInputChange}/>
                </InputGroup>
                <br />
                <InputGroup>
                    <InputGroupAddon>Street</InputGroupAddon>
                    <Input className="AddBuilding"
                           placeholder="Type street..."
                           value={this.state.refAddBuildingStreet}
                           onChange={this.onAddBuildingStreetInputChange}/>
                </InputGroup>
                <br />
                <InputGroup>
                    <InputGroupAddon>Number</InputGroupAddon>
                    <Input className="AddBuilding"
                           placeholder="Type number..."
                           value={this.state.refAddBuildingNumber}
                           onChange={this.onAddBuildingNumberInputChange}/>
                </InputGroup>
                <br />
                <InputGroup>
                    <InputGroupAddon>Postal Code</InputGroupAddon>
                    <Input className="AddBuilding"
                           placeholder="Type Postal Code..."
                           value={this.state.refAddBuildingPostalCode}
                           onChange={this.onAddBuildingPostalInputChange}/>
                </InputGroup>
                <br />
                <Button id="AddRoomButton"
                        color="primary"
                        onClick={this.onAddBuildingSubmitHandler}>Submit</Button>

            </Col>
        );
    },

    openAddRoomForm: function() {

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
                                onChange={this.onSelectAddSetFloor}
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
                           onChange={this.onRoomCapacityInputChange}/>
                </InputGroup>
                <br />
                <Button id="AddRoomButton"
                        color="primary"
                        onClick={this.onAddRoomSubmitHandler}>Submit</Button>
            </Col>
        );
    },

    openAddFloorForm: function() {
        return (
            <Col sm="5">
                <CardTitle>Add a Floor</CardTitle>
                <hr/>

                <BuildingDropdown className="MyBuilding"
                                  url="http://localhost:8080/buildings"
                                  onChange={this.onSelectAddFloorSetBuilding}
                                  ref={(ref) => this.state.refAddFloorBuilding = ref}
                                  disabled={false}
                />
                <InputGroup>
                    <InputGroupAddon>Floor Name</InputGroupAddon>
                    <Input className="CoordinateInput"
                           placeholder="Type floor name..."
                           value={this.state.refAddFloorName}
                           onChange={this.onAddFloorNameInputChange}/>
                </InputGroup>
                <br />
                <InputGroup>
                    <InputGroupAddon>Contains</InputGroupAddon>
                    <Input className="CoordinateInput"
                           placeholder="Type # of Rooms..."
                           value={this.state.refAddFloorRoomNumber}
                           onChange={this.onAddFloorRoomNumberInputChange}/>
                    <InputGroupAddon>Rooms</InputGroupAddon>
                </InputGroup>
                <br />
                <InputGroup>
                    <InputGroupAddon>Image File</InputGroupAddon>
                    <Input className="CoordinateInput"
                           placeholder="Type floor image file name..."
                           value={this.state.refAddFloorURL}
                           onChange={this.onAddFloorURLInputChange}/>
                </InputGroup>
                <br />
                <Button id="AddRoomButton"
                        color="primary"
                        onClick={this.onAddFloorSubmitHandler}>Submit</Button>
            </Col>
        );
    },

    openSetRoomCoordinatesForm: function() {

        return(

            <Card block>
                <Col xs="3">
                    <CardTitle>Set Room Coordinates</CardTitle>
                    <hr/>
                    <BuildingDropdown className="MyBuilding"
                                      url="http://localhost:8080/buildings"
                                      onChange={this.onSelectCoordSetBuilding}
                                      ref={(ref) => this.state.refCoordBuilding = ref}/>
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
                               placeholder="X Coordinate value"
                               value={this.state.refCoordX}/>
                    </InputGroup>
                    <br />
                    <InputGroup>
                        <InputGroupAddon>Y</InputGroupAddon>
                        <Input className="CoordinateInput"
                               disabled={true}
                               placeholder="Y Coordinate value"
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

    },


    render:function() {
        return (
            <div className="App">
                <div className="AddRoomBox">
                    <hr/>
                    <Container className="Container">
                        <h1 className="display-3">Editor</h1>
                        <p className="lead">CN Group - Resource Locator</p>
                        <NotificationSystem ref={(ref) => this.state.notificationSystem = ref} />
                        <Row>
                            <Card block >
                                <Col sm="3">
                                    <AssetsTree assetsData={this.state.assetsData}
                                                onChange={this.doSomething}
                                                ref={(ref) => this.state.refTree = ref}/>
                                </Col>
                                {this.state.openBuildingAdd != null ? this.openAddBuildingForm() : null}
                                {this.state.openFloorAdd != null ? this.openAddFloorForm() : null}
                                {this.state.openRoomAdd != null ? this.openAddRoomForm() : null}
                            </Card>
                            {this.state.openRoomAdd != null ? this.openSetRoomCoordinatesForm() : null}
                        </Row>
                    </Container>
                    <hr/>
                </div>
            </div>
        );
    }

});



export default App;
