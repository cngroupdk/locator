import React, { Component } from 'react';
import { Container, Row, Col, Input, Label, Card, Button} from 'reactstrap';
import BuildingDropdown from './BuildingDropdown';
import FloorDropdown from './FloorDropdown';
import RoomDropdown from './RoomDropdown';
import ImageMap from './ImageMap';
import $ from 'jquery';
import './App.css';

var App = React.createClass({

    getInitialState: function () {
        return {
            roomAddMyBuilding : null,
            roomAddMyFloor : null,
            roomAddMyRoom : 'Add Room Number',
            roomAddMyCapacity: 'Specify Room Capacity',

            roomCoordMyBuilding : null,
            roomCoordMyFloor : null,
            roomCoordMyRoomNumber : null,
            roomCoordMyMap : null,

            roomAddFloorEnabled: '',
            roomCoordMyRoomEnabled:''

        };
    },

    sendNewRoomToBackEnd: function(data){
        this.serverRequest = $.post( "ajax/test.html", function( data ) {

        }.bind(this));
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
                                <Col xs="3">
                                        <h3>Add a Room</h3>
                                        <hr/>
                                        <BuildingDropdown className="MyBuilding" url="http://localhost:8080/buildings" ref={(ref) => this.state.roomAddMyBuilding = ref}/>
                                        <FloorDropdown className="MyFloor" url="http://localhost:8080/floors"
                                                       ref={(ref) => this.state.roomAddMyFloor = ref}
                                                       />
                                        <Label for="RoomNumber">{this.state.roomAddMyRoom}</Label>
                                        <Input id="RoomNumber" placeholder="Type room number..." />
                                        <Label for="RoomCapacity">{this.state.roomAddMyCapacity}</Label>
                                        <Input id="RoomCapacity" placeholder="Type room capacity number..." />
                                        <Button id="AddRoomButton" color="primary" onClick={this.onAddRoomClickHandler}>Submit</Button>
                                </Col>
                            </Card>
                            <Card block >
                                <Col sm="3">
                                    <h3>Set Room Coordinates</h3>
                                    <hr/>
                                    <BuildingDropdown className="MyBuilding" url="http://localhost:8080/buildings" ref={(ref) => this.state.roomCoordMyBuilding = ref}/>
                                    <FloorDropdown className="MyFloor" url="http://localhost:8080/floors" ref={(ref) => this.state.roomCoordMyFloor = ref}/>
                                    <RoomDropdown className="MyRoom" url="http://localhost:8080/rooms"
                                                  ref={(ref) => this.state.roomCoordMyRoom = ref}
                                                  />
                                    <Button id="RoomCoordButton" color="primary" onClick={this.onSetCoordClickHandler}>Submit</Button>
                                </Col>
                                <Col>
                                    <ImageMap clickEnter={this.onMouseClickHandler()} className="FloorMap" ref={(ref) => this.state.roomCoordMyMap = ref}/>
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
