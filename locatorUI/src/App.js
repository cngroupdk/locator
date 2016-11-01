import React from 'react';
import { Container, Row, Col, Card, CardTitle, CardSubtitle } from 'reactstrap';
import EmployeeDropdown from './EmployeeDropdown';
import FloorDropdown from './FloorDropdown';
import RoomDropdown from './RoomDropdown';
import ImageMap from './ImageMap';

var App=React.createClass({

  getInitialState: function () {
    return {
      showEmployee: false,
      myMap : null,
      myEmployee : null,
      myFloor : null,
      myRoom : null,
      myImage : null
    };
  },

  setFloorplanPath : function(buildingId, floorName, refToImage){

    fetch('http://localhost:8080/floors/' + buildingId + '/' + floorName).then((response) => {return response.json()}).then(function(emplData){
      refToImage.setMapPath('http://localhost:8080' + emplData.floorplanUrl);
    }).catch((error) => {
      console.error(error);
    });
  },

  setEmployeeImages : function(employeeId, buildingId, roomName, refToImage){
    fetch('http://localhost:8080/rooms/' + buildingId + '/' + roomName).then((response) => {return response.json()}).then(function(roomData){
      var style =
      {
        transition: 'all 0.5s',
        visibility: 'visible',
        maxWidth: '75px',
        position: 'relative',
        top: roomData.styleTop,
        left: roomData.styleLeft
      }
      refToImage.setMapPath('http://localhost:8080/' + employeeId + '.jpg');
      refToImage.setStyleProps(style);
    }).catch((error) => {
      console.error(error);
    });
  },

  onSelectEmployee: function(index, emplData) {

    var res = emplData.location.split("@");
    var buildingId = res[2];
    var floorName = res[1];
    var roomName = res[0];
    this.setFloorplanPath(buildingId, floorName, this.state.myMap);
    this.setEmployeeImages(emplData.id, buildingId, roomName, this.state.myImage);
    this.state.myFloor.updateFloor(floorName + ' @ ' + buildingId);
    this.state.myRoom.updateRoom(emplData.location);
  },

  onSelectFloor: function(index, flData) {

    this.setFloorplanPath(flData.buildingId, flData.floorName, this.state.myMap);
    this.state.myEmployee.updateName('Select an Employee');
    this.state.myRoom.updateRoom('Select a Room');
    var style = {visibility: 'hidden'};
    this.state.myImage.setStyleProps(style);

  },

  onSelectRoom: function(index, roomData) {

    this.setFloorplanPath(roomData.buildingId, roomData.floorName, this.state.myMap);
    this.state.myEmployee.updateName('Select an Employee');
    var floorName = roomData.floorName + '@' + roomData.buildingId;
    this.state.myFloor.updateFloor(floorName);

    var newTop = parseInt(roomData.styleTop.replace('px', ''));
    var newLeft = parseInt(roomData.styleLeft.replace('px', '')) + 14;

    var style =
    {
      transition: 'all 0.5s',
      visibility: 'visible',
      maxWidth: '40px',
      position: 'relative',
      top: newTop,
      left: newLeft
    }

    this.state.myImage.setMapPath('http://localhost:8080/map-marker.png');
    this.state.myImage.setStyleProps(style);
  },

  onMouseEnterHandler: function() {

    var employeeName = this.state.myEmployee.getCurrentName();

    if (employeeName.currentName !== 'Select an Employee') {

      var newStyle =
      {
        transition: 'all 0.5s',
        visibility: 'visible',
        maxWidth: '200px',
        position: 'relative',
        top: this.state.myImage.state.style.top,
        left: this.state.myImage.state.style.left
      }

      this.state.myImage.setStyleProps(newStyle);

    }
  },

  onMouseLeaveHandler: function() {

    var employeeName = this.state.myEmployee.getCurrentName();

    if (employeeName.currentName !== 'Select an Employee') {

      var newStyle =
      {
        transition: 'all 0.5s',
        visibility: 'visible',
        maxWidth: '75px',
        position: 'relative',
        top: this.state.myImage.state.style.top,
        left: this.state.myImage.state.style.left
      }

      this.state.myImage.setStyleProps(newStyle);
    }
  },

  render: function() {
    return (

        <div className="selectorBox">
          <Container className="Container">
            <Card block>
              <CardTitle>Resource Locator</CardTitle>
              <hr/>
              <CardSubtitle>Select an employee, floor number or room to display the location</CardSubtitle>
              <Row>
                <Col sm="3">
                  <EmployeeDropdown className="MyEmployees" onChange={this.onSelectEmployee} url="http://localhost:8080/employees" ref={(ref) => this.state.myEmployee = ref}/>
                </Col>
                <Col sm="3">
                  <FloorDropdown className="MyFloors" onChange={this.onSelectFloor} url="http://localhost:8080/floors" ref={(ref) => this.state.myFloor = ref}/>
                </Col>
                <Col sm="3">
                  <RoomDropdown className="MyRooms" onChange={this.onSelectRoom} url="http://localhost:8080/rooms" ref={(ref) => this.state.myRoom = ref}/>
                </Col>
              </Row>
              <Row>
                <hr/>
                <Col xs="12">
                  <ImageMap className="FloorMap" ref={(ref) => this.state.myMap = ref}/>
                  <ImageMap show={false}
                            className="staff"
                            ref={(ref) => this.state.myImage = ref}
                            hoverEnter={this.onMouseEnterHandler}
                            hoverLeave={this.onMouseLeaveHandler}
                  />
                </Col>
              </Row>
            </Card>
          </Container>
        </div>

    );
  }
});

export default App;
