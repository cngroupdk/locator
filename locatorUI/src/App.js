import React from 'react';
import { Container, Row, Col, Card } from 'reactstrap';
import EmployeeDropdown from './EmployeeDropdown';
import FloorDropdown from './FloorDropdown';
import ImageMap from './ImageMap';

var App=React.createClass({

  getInitialState: function () {
    return {
      showEmployee: false,
      myMap : null,
      myEmployee : null,
      myFloor : null,
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
    this.state.myFloor.updateFloor(res[1] + ' @ ' + res[2]);
  },

  onSelectFloor: function(index, flData) {

    this.setFloorplanPath(flData.buildingId, flData.floorName, this.state.myMap);
    this.state.myEmployee.updateName('Select an Employee');
    var style = {visibility: 'hidden'};
    this.state.myImage.setStyleProps(style);

  },

  onMouseEnterHandler: function() {
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

    console.log('enter');
  },
  onMouseLeaveHandler: function() {
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
    console.log('leave');
  },

  render: function() {
    return (

        <div className="selectorBox">
          <Container className="Container">
            <Card block>
              <h1>Resource Locator</h1>
              <hr/>
              <Row>
                <Col xs="3">
                  <EmployeeDropdown className="MyEmployees" onChange={this.onSelectEmployee} url="http://localhost:8080/employees" ref={(ref) => this.state.myEmployee = ref}/>
                </Col>
                <Col xs="2">
                  <FloorDropdown className="MyFloors" onChange={this.onSelectFloor} url="http://localhost:8080/floors" ref={(ref) => this.state.myFloor = ref}/>
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
