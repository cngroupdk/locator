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
import EmployeeDropdown from './EmployeeDropdown';
import BuildingDropdown from './BuildingDropdown';
import FloorDropdown from './FloorDropdown';
import RoomDropdown from './RoomDropdown';
import ImageMap from './ImageMap';

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

      popoverOpen: false,
      updateDone: false,
      myEditPen : null,
      myEditBuilding : null,
      myEditRoom : null
    };
  },

  componentDidMount: function(){
    var firstName = this.props.firstName;
    var lastName = this.props.lastName;

    if (typeof firstName !== "undefined" && typeof lastName !== "undefined") {

        var params = {firstName, lastName};
        var employeeFound = this.state.myEmployee.findEmployee(params);

        this.onSelectEmployee(employeeFound);
        this.state.myEmployee.updateName(firstName + " " + lastName);
        this.state.myEmployee.updateEmployeeIndex(employeeFound.data.index);
    }

  },

  setFloorplanPath : function(data){

    fetch('http://localhost:8080/floors/' + data.buildingId + '/' + data.floorName).then((response) => {return response.json()}).then(function(floorData){
      data.refToFloor.setMapPath('http://localhost:8080' + floorData.floorplanUrl);
    }).catch((error) => {
      console.error(error);
    });
  },

  setEmployeeImages : function(data){
    fetch('http://localhost:8080/rooms/' + data.buildingId + '/' + data.roomName).then((response) => {return response.json()}).then(function(roomData){

      var style =
      {
        visibility: 'visible',
        top: roomData.styleTop,
        left: roomData.styleLeft
      };

      data.refToImage.setMapPath('http://localhost:8080/' + data.employeeId + '.jpg');
      data.refToImage.setStyleProps(style);

      var newTop = parseInt(style.top.replace('px', ''), 10);
      var newLeft = parseInt(style.left.replace('px', ''), 10);
      style =
      {
        visibility: 'visible',
        top: newTop,
        left: newLeft
      };
      data.refToPen.setMapPath('http://localhost:8080/marker-pen.png');
      data.refToPen.setStyleProps(style);

    }).catch((error) => {
      console.error(error);
    });
  },

  onSelectEmployee: function(data) {

    var res = data.emplData.location.split("@");

    var inputData ={
      employeeId : data.emplData.id,
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
    this.state.myImage.setMapPath('http://localhost:8080/marker-location.png');
    this.state.myImage.setStyleProps(style);

    style =
    {
      visibility: 'hidden'
    };
    this.state.myEditPen.setStyleProps(style);
  },

  onEditLocationBuilding: function(event){

    var selectedBuilding = event.selectedBuilding;
    this.state.myEditRoom.loadCommentsFromServer('http://localhost:8080/rooms/' + selectedBuilding.buildingId);
  },

  onEditLocationRoom: function(event){

  },

  toggle() {

    var updateStatus = this.state.updateDone;

    if( this.state.popoverOpen === true &&
        updateStatus === true)
    {
      this.state.myEmployee.updateName('Select an Employee');
      this.state.myFloor.updateFloor('Select a Floor');
      this.state.myRoom.updateRoom('Select a Room');
      var style =
      {
        visibility: 'hidden'
      };
      this.state.myMap.setStyleProps(style);
      this.state.myImage.setStyleProps(style);
      this.state.myEditPen.setStyleProps(style);
      updateStatus = false;
    }

    this.setState({
      popoverOpen: !this.state.popoverOpen,
      updateDone: updateStatus
    });
  },

  onSubmitNewLocationHandler: function () {

    var roomName = this.state.myEditRoom.getCurrentRoom().currentRoom;

    var roomData = this.state.myEditRoom.getRoomData().selectedRoom;

    if( roomName !== 'Select a Room')
    {

      var employeeData = this.state.myEmployee.getEmployeeData().selectedEmployee;
      var newLocation = roomData.name + "@" + roomData.floorName + "@" + roomData.buildingId;

      employeeData.location = newLocation;

      var jsonData = JSON.stringify(employeeData);
      fetch('http://localhost:8080/employees/update/employee',{
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
        this.setState(
            {updateDone : true}
        );
        this.addNotification(message);
      }).catch((error) => {
        console.error(error);
      });
    }
    else{
      var message = {
        content: "Please Choose a New Room Before Clicking \"Submit\"",
        type: "info"
      };
      this.addNotification(message);
    }
  },

  addNotification: function(notificationMessage) {

    this.state.notificationSystem.addNotification({
      message: notificationMessage.content,
      level: notificationMessage.type
    });
  },

  render: function() {
    return (

        <div className="selectorBox">
          <Container className="Container">
            <NotificationSystem ref={(ref) => this.state.notificationSystem = ref} />
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
                  <Modal id="EditLocation"
                           placement="bottom"
                           isOpen={this.state.popoverOpen}
                           target="EditPen"
                           toggle={this.toggle}
                           >
                    <ModalHeader>Did this employee move?</ModalHeader>
                    <ModalBody>
                        Choose the new location:
                        <BuildingDropdown className="EditBuildingDropdown"
                                          onChange={this.onEditLocationBuilding}
                                          url="http://localhost:8080/buildings"
                                          ref={(ref) => this.state.myEditBuilding = ref}
                        />
                        <RoomDropdown className="EditRoomsDropdown"
                                      onChange={this.onEditLocationRoom}
                                      url="http://localhost:8080/rooms"
                                      ref={(ref) => this.state.myEditRoom = ref}
                        />
                        <Button id="EditLocationButton"
                                color="primary"
                                onClick={this.onSubmitNewLocationHandler}>
                          Submit
                        </Button>
                    </ModalBody>
                  </Modal>
                </Col>
              </Row>
            </Card>
          </Container>
        </div>

    );
  }
});

export default App;
