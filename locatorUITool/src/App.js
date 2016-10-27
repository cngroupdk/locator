import React from 'react';
import { Container, Row, Col, Card, Button, Input, InputGroup, InputGroupAddon} from 'reactstrap';
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

    sendNewRoomToBackEnd: function(data){
        this.serverRequest = $.post( "ajax/test.html", function( data ) {

        }.bind(this));
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
    },

    onMouseClickHandler: function(){

    },
    onAddRoomClickHandler: function () {

    },
    onSetCoordClickHandler: function () {

    },

    render:function() {
        return (
            <div className="App">
                <div className="AddRoomBox">
                    <Container className="Container">
                        <Row>
                            <Card block >
                                <Col xs="4">
                                        <h3>Add a Room</h3>
                                        <hr/>
                                        <BuildingDropdown className="MyBuilding" url="http://localhost:8080/buildings" onChange={this.onSelectAddSetBuilding} ref={(ref) => this.state.refAddBuilding = ref}/>
                                        <FloorDropdown  className="MyFloor"
                                                        onChange={this.onSelectAddSetFloor}
                                                        ref={(ref) => this.state.refAddFloor = ref} />
                                        <InputGroup>
                                            <InputGroupAddon>Add Room Number</InputGroupAddon>
                                            <Input placeholder="Type room number..." value={this.state.refAddRoom}/>
                                        </InputGroup>
                                        <br />
                                        <InputGroup>
                                            <InputGroupAddon>Add Room Capacity</InputGroupAddon>
                                            <Input placeholder="Type room capacity..." value={this.state.refAddCapacity}/>
                                        </InputGroup>
                                        <br />
                                        <Button disabled={this.state.AddSubmitDisabled}  id="AddRoomButton" color="primary" onClick={this.onAddRoomClickHandler}>Submit</Button>
                                </Col>
                            </Card>
                            <Card block >
                                <Col sm="3">
                                    <h3>Set Room Coordinates</h3>
                                    <hr/>
                                    <BuildingDropdown className="MyBuilding" url="http://localhost:8080/buildings" onChange={this.onSelectCoordSetBuilding} ref={(ref) => this.state.refCoordBuilding = ref}/>
                                    <FloorDropdown className="MyFloor" onChange={this.onSelectCoordSetFloor} ref={(ref) => this.state.refCoordFloor = ref}/>
                                    <RoomDropdown disabled={true} className="MyRoom"
                                                  ref={(ref) => this.state.refCoordRoom = ref}/>
                                    <br />
                                    <InputGroup>
                                        <InputGroupAddon>X</InputGroupAddon>
                                        <Input disabled={true} placeholder="X Coordinate value" value={this.state.refCoordX}/>
                                    </InputGroup>
                                    <br />
                                    <InputGroup>
                                        <InputGroupAddon>Y</InputGroupAddon>
                                        <Input disabled={true} placeholder="Y Coordinate value" value={this.state.refCoordY}/>
                                    </InputGroup>
                                    <br />
                                    <Button disabled={this.state.CoordSubmitDisabled} id="RoomCoordButton" color="primary" onClick={this.onSetCoordClickHandler}>Submit</Button>
                                </Col>
                                <Col>
                                    <ImageMap clickEnter={this.onMouseClickHandler()} className="FloorMap" ref={(ref) => this.state.refCoordMap = ref}/>
                                </Col>
                            </Card>
                        </Row>
                        <hr/>

                    </Container>
                </div>
            </div>
        );
    }

});



export default App;
