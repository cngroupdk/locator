import React from 'react';
import NotificationSystem from 'react-notification-system';
import {
    Button,
    Container,
    Row,
    Col,
    Card,
    CardTitle,
    CardSubtitle,
    Modal,
    ModalHeader,
    ModalBody
} from 'reactstrap';
import { Link } from 'react-router';
import EmployeeDropdown from './EmployeeDropdown';
import FloorDropdown from './FloorDropdown';
import RoomDropdown from './RoomDropdown';
import ImageMap from './ImageMap';
import AssignLocation from './AssignLocation';

var App=React.createClass({

  getInitialState: function () {
    return {

      notificationSystem : null,

      showEmployee: false,
      myMap : null,
      myEmployee : null,
      myFloor : null,
      myRoom : null,
      myImage : null,

      updateDone: false,
      myEditPen : null,
      myAssignLocation : null

    };
  },

  componentDidMount: function(){

  },

  setFloorplanPath : function(data){

    fetch('http://localhost:8080/floors/' + data.buildingId + '/' + data.floorName).then((response) => {return response.json()}).then(function(floorData){
      data.refToFloor.setMapPath('http://localhost:8080/files' + floorData.floorplanUrl);
    }).catch((error) => {
      console.error(error);
    });
  },

  setEmployeeImages : function(data){

    fetch('http://localhost:8080/employees/photo/folder')
    .then((response) => {
      return response.json()
    })
    .then(function(response){

      var employeePhoto = response.url + data.employeeId + '.jpg';

      data.refToImage.setMapPath(employeePhoto);

    }).catch((error) => {
      console.error(error);
    });

    fetch('http://localhost:8080/rooms/' + data.buildingId + '/' + data.roomName).then((response) => {return response.json()}).then(function(roomData){

      var style =
      {
        visibility: 'visible',
        top: roomData.styleTop,
        left: roomData.styleLeft
      };

      data.refToImage.setStyleProps(style);

      var newTop = parseInt(style.top.replace('px', ''), 10);
      var newLeft = parseInt(style.left.replace('px', ''), 10);
      style =
      {
        visibility: 'visible',
        top: newTop,
        left: newLeft
      };
      data.refToPen.setMapPath('http://localhost:8080/files/marker-pen.png');
      data.refToPen.setStyleProps(style);

    }).catch((error) => {
      console.error(error);
    });
  },

  onEmployeeDataReceived: function() {

    if (typeof this.props.employeeId !== "undefined") {

      var id = this.props.employeeId;

      var params = {id};
      var employeeFound = this.state.myEmployee.findEmployee(params);

      this.onSelectEmployee(employeeFound);
      this.state.myEmployee.updateName(employeeFound.emplData.firstName + " " + employeeFound.emplData.lastName);
      this.state.myEmployee.updateEmployeeIndex(employeeFound.index);
    }

  },

  onSelectEmployee: function(data) {

    var res = data.emplData.location.split("@");

    var inputData ={
      employeeId : data.emplData.employeeId,
      buildingId : res[2],
      floorName : res[1],
      roomName : res[0],
      refToFloor : this.state.myMap,
      refToImage : this.state.myImage,
      refToPen : this.state.myEditPen
    };
    var style =
    {
      visibility: 'visible'
    };
    this.state.myMap.setStyleProps(style);
    this.setFloorplanPath(inputData);
    this.setEmployeeImages(inputData);
    this.state.myFloor.updateFloor(inputData.floorName + ' @ ' + inputData.buildingId);
    this.state.myRoom.updateRoom(data.emplData.location);
  },


  onSelectFloor: function(data) {

    var inputData ={
      buildingId : data.flData.buildingId,
      floorName : data.flData.floorName,
      refToFloor : this.state.myMap
    };

    this.setFloorplanPath(inputData);
    this.state.myEmployee.updateName('Select an Employee');
    this.state.myRoom.updateRoom('Select a Room');
    var style = {visibility: 'hidden'};
    this.state.myImage.setStyleProps(style);
    this.state.myEditPen.setStyleProps(style);

  },

  onSelectRoom: function(data) {

    var inputData ={
      buildingId : data.roomData.buildingId,
      floorName : data.roomData.floorName,
      refToFloor : this.state.myMap
    };

    this.setFloorplanPath(inputData);
    this.state.myEmployee.updateName('Select an Employee');

    var floorName = data.roomData.floorName + '@' + data.roomData.buildingId;
    this.state.myFloor.updateFloor(floorName);

    var newTop = parseInt(data.roomData.styleTop.replace('px', ''), 10);
    var newLeft = parseInt(data.roomData.styleLeft.replace('px', ''), 10) + 14;

    var style =
    {
      visibility: 'visible',
      maxWidth: '40px',
      top: newTop,
      left: newLeft
    };
    this.state.myImage.setMapPath('http://localhost:8080/files/marker-location.png');
    this.state.myImage.setStyleProps(style);

    style =
    {
      visibility: 'hidden'
    };
    this.state.myEditPen.setStyleProps(style);
  },

  reset(){

    var style =
    {
      visibility: 'hidden'
    };
    this.state.myMap.setStyleProps(style);
    this.state.myImage.setStyleProps(style);
    this.state.myEditPen.setStyleProps(style);

    this.state.myEmployee.updateName('Select an Employee');
    this.state.myFloor.updateFloor('Select a Floor');
    this.state.myRoom.updateRoom('Select a Room');
  },

  toggle() {

    var updateStatus = this.state.updateDone;

    var popoverOpen = false;
    if(this.state.myAssignLocation !== null)
    {
      this.state.myAssignLocation.setSelectedEmployee(this.state.myEmployee.getEmployeeData().selectedEmployee);
      popoverOpen = this.state.myAssignLocation.getPopoverState();
    }

    if( popoverOpen === true &&
        updateStatus === true)
    {
      this.reset();
      updateStatus = false;
    }

    this.setState({
      updateDone: updateStatus
    });

    if(this.state.myAssignLocation !== null)
    {
      this.state.myAssignLocation.toggle();
    }

  },

  onSubmitNewLocation: function (data){

    this.reset();
    if(data.type !== 'ValidationError'){
      this.setState(
          {updateDone : true}
      );
    }

    this.addNotification(data.message);
    this.state.myAssignLocation.toggle();


  },

  addNotification: function(notificationMessage) {

    this.state.notificationSystem.addNotification({
      message: notificationMessage.content,
      level: notificationMessage.type
    });
  },

  render: function() {

    var modalEmployee = null;

    if (this.state.myEmployee) {
      modalEmployee = this.state.myEmployee.getEmployeeData().selectedEmployee;
    }

    return (

        <div className="selectorBox">
          <Container className="Container">
            <NotificationSystem ref={(ref) => this.state.notificationSystem = ref} />
            <hr/>
            <h1 className="display-3">Locator</h1>
            <p className="lead">CN Group - Staff Locator</p>
            <p><Link to="/tools/unassigned">Unassigned Users</Link></p>
            <Card className="card" block>
              <CardSubtitle>Select an employee, floor number or room to display the location</CardSubtitle>
              <Row>
                <Col sm="3">
                  <EmployeeDropdown className="MyEmployees"
                                    onChange={this.onSelectEmployee}
                                    onReceipt={this.onEmployeeDataReceived}
                                    url="http://localhost:8080/employees/assigned"
                                    ref={(ref) => this.state.myEmployee = ref}/>
                </Col>
                <Col sm="3">
                  <FloorDropdown className="MyFloors"
                                 onChange={this.onSelectFloor}
                                 url="http://localhost:8080/floors"
                                 ref={(ref) => this.state.myFloor = ref}/>
                </Col>
                <Col sm="3">
                  <RoomDropdown className="MyRooms"
                                onChange={this.onSelectRoom}
                                url="http://localhost:8080/rooms"
                                ref={(ref) => this.state.myRoom = ref}/>
                </Col>
              </Row>
              <Row>
                <Col xs="12">
                  <ImageMap id="FloorMap"
                            ref={(ref) => this.state.myMap = ref}
                  />
                  <ImageMap id="Staff"
                            ref={(ref) => this.state.myImage = ref}
                  />
                  <ImageMap id="EditPen"
                            ref={(ref) => this.state.myEditPen = ref}
                            clickHandler={this.toggle}
                  />
                  <AssignLocation id="Modal"
                                  ref={(ref) => this.state.myAssignLocation = ref}
                                  onToggleClick={this.toggle}
                                  onSubmit={this.onSubmitNewLocation}
                                  selectedEmployee={modalEmployee}/>
                </Col>
              </Row>
            </Card>
          </Container>
        </div>

    );
  }
});

export default App;
