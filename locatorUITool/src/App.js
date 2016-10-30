import React from 'react';
import { Container, Row, Col, Card, Cardblock, CardTitle, Button, Input, InputGroup, InputGroupAddon} from 'reactstrap';
import BuildingDropdown from './BuildingDropdown';
import FloorDropdown from './FloorDropdown';
import RoomDropdown from './RoomDropdown';
import ImageMap from './ImageMap';
import $ from 'jquery';
import './App.css';

var App = React.createClass({

    getInitialState: function () {
        return {
            refAddBuilding : null,
            refAddFloor : null,
            refAddRoom : "",
            refAddCapacity: "",

            refCoordBuilding : null,
            refCoordFloor : null,
            refCoordRoom : null,
            refCoordX : "",
            refCoordY : "",
            refCoordMap : null,

            AddSubmitDisabled: true,
            CoordSubmitDisabled: true
        };
    },

    onSelectAddSetBuilding : function(buildingData){
        this.state.refAddFloor.loadCommentsFromServer('http://localhost:8080/floors/' + buildingData.buildingId);
        this.state.refAddFloor.enableDropdown(false);
    },

    onSelectAddSetFloor : function(flData){

    },

    onSelectCoordSetBuilding : function(buildingData){
        this.state.refCoordFloor.loadCommentsFromServer('http://localhost:8080/floors/' + buildingData.buildingId);
        this.state.refCoordFloor.enableDropdown(false);
    },

    onSelectCoordSetFloor : function(flData){

        this.state.refCoordRoom.loadCommentsFromServer('http://localhost:8080/rooms/byfloor/' + flData.buildingId + '/' + flData.floorName);
        this.state.refCoordRoom.enableDropdown(false);
        this.state.refCoordMap.setMapPath('http://localhost:8080' + flData.floorplanUrl);
    },

    onMouseClickHandler: function(event){
        var x = event.clientX;
        var y = event.clientY;
        this.setState({refCoordX: x});
        this.setState({refCoordY: y});
    },

    onSetCoordSubmitHandler: function () {

    },

    onRoomNumberInputChange(event) {
        this.setState({refAddRoom: event.target.value});
    },

    onRoomCapacityInputChange(event) {
        this.setState({refAddCapacity: event.target.value});
    },

    onAddRoomSubmitHandler(){

        var floorDisabled = this.state.refAddFloor.getDropdownDisabled();

        if( this.state.refAddRoom !== '' &&
            this.state.refAddCapacity !== ''){

            var buildingData = this.state.refAddBuilding.getBuildingData();
            var floorData = this.state.refAddFloor.getFloorData();
            var roomName = this.state.refAddRoom;
            var roomCapacity = this.state.refAddCapacity;

            var rawData = {
                name : roomName,
                type : 'Development',
                buildingId : buildingData.selectedBuilding.buildingId,
                capacity : roomCapacity,
                assignedPeople : 0,
                roomId : 0,
                styleTop: '0px',
                styleLeft: '0px',
                floorName : floorData.selectedFloor.floorName
            };

            var jsonData = JSON.stringify(rawData);

            $.ajax({
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                type: 'POST',
                url: "http://localhost:8080/rooms/newroom",
                data: jsonData
            })
            .done(function(data) {
                this.state.refCoordRoom.loadCommentsFromServer('http://localhost:8080/rooms/byfloor/' + floorData.selectedFloor.buildingId + '/' + floorData.selectedFloor.floorName);
            })
            .fail(function(jqXhr) {
                console.log('failed to register');
            });

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

    render:function() {
        return (
            <div className="App">
                <div className="AddRoomBox">
                    <hr/>
                    <Container className="Container">
                        <Row>
                            <Card block >
                                <Col sm="4">
                                    <CardTitle>Add a Room</CardTitle>
                                    <hr/>

                                    <BuildingDropdown className="MyBuilding"
                                                      url="http://localhost:8080/buildings"
                                                      onChange={this.onSelectAddSetBuilding}
                                                      ref={(ref) => this.state.refAddBuilding = ref}/>

                                    <FloorDropdown  className="MyFloor"
                                                    onChange={this.onSelectAddSetFloor}
                                                    ref={(ref) => this.state.refAddFloor = ref} />

                                    <InputGroup>
                                        <InputGroupAddon>Name</InputGroupAddon>
                                        <Input className="CoordinateInput"
                                               placeholder="Type room name..."
                                               value={this.state.refAddRoom}
                                               onChange={this.onRoomNumberInputChange}/>
                                    </InputGroup>
                                    <br />
                                    <InputGroup>
                                        <InputGroupAddon>Capacity</InputGroupAddon>
                                        <Input className="CoordinateInput"
                                               placeholder="Type room capacity..."
                                               value={this.state.refAddCapacity}
                                               onChange={this.onRoomCapacityInputChange}/>
                                    </InputGroup>
                                    <br />
                                    <Button id="AddRoomButton"
                                            color="primary"
                                            onClick={this.onAddRoomSubmitHandler}>Submit</Button>
                                </Col>
                            </Card>
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
                                    <Button disabled={this.state.CoordSubmitDisabled}
                                            id="RoomCoordButton"
                                            color="primary"
                                            onClick={this.onSetCoordSubmitHandler}>Submit</Button>
                                </Col>
                                <Col xs="5">
                                    <ImageMap clickEnter={this.onMouseClickHandler}
                                              className="FloorMap"
                                              ref={(ref) => this.state.refCoordMap = ref}/>
                                </Col>
                            </Card>
                        </Row>
                    </Container>
                    <hr/>
                </div>
            </div>
        );
    }

});



export default App;
