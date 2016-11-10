import React from 'react';
import NotificationSystem from 'react-notification-system';
import { Container, Row, Col, Card, Button} from 'reactstrap';
import AssetsTree from './AssetsTree';
import AddBuildingForm from './AddBuildingForm';
import AddFloorForm from './AddFloorForm';
import AddRoomForm from './AddRoomForm';
import AddRoomCoordForm from './AddRoomCoordForm';
import './App.css';

var App = React.createClass({

    getInitialState: function () {
        return {

            notificationSystem : null,
            refTree : null,
            refSelectedBuilding : null,
            refSelectedFloor : null,
            refSelectedRoom : null,
            openNode : null,

            assetsData : [
            {
                name: 'Loading...',
                toggled: false,
            }
            ],

            openBuildingDetails : null,
            openFloorDetails : null,
            openRoomDetails : null,

            openBuildingAdd : null,
            openFloorAdd : null,
            openRoomAdd : null

        };
    },

    componentDidMount() {

    },

    addNotification: function(notificationMessage) {

        this.state.notificationSystem.addNotification({
            message: notificationMessage.content,
            level: notificationMessage.type
        });
    },

    updateFormStatus: function (event) {

        var buildingFormOpenStatus = event.node.buildingGuid;
        var floorFormOpenStatus = event.node.floorId;
        var roomFormOpenStatus = event.node.roomId;

        this.setState({
            openNode : event.node,
            openBuildingDetails: buildingFormOpenStatus,
            openFloorDetails: floorFormOpenStatus,
            openRoomDetails: roomFormOpenStatus,

            openBuildingAdd : null,
            openFloorAdd : null,
            openRoomAdd : null
        });


        if(!(this.state.refSelectedBuilding ==null))
        {
            this.state.refSelectedBuilding.loadBuildingDetails(event.node);
        }
        if(!(this.state.refSelectedFloor == null))
        {
            this.state.refSelectedFloor.loadFloorDetails(event.node);
        }
        if(!(this.state.refSelectedRoom == null))
        {
            this.state.refSelectedRoom.loadRoomDetails(event.node);
        }

    },

    showReply : function(responseData){
        var message;
        if(responseData.response.status === 200) {
            message = {
                content: responseData.messageContent,
                type: "success"
            };
        }
        else{
            message = {
                content: responseData.messageContent,
                type: "error"
            };
        }
        this.addNotification(message);
    },

    onResponseFromUpdate: function(responseData){

        this.showReply(responseData);
        this.state.refTree.loadCommentsFromServer('http://localhost:8080/tree/');
        this.setState({
            openBuildingDetails : null,
            openFloorDetails : null,
            openRoomDetails : null,

            openBuildingAdd : null,
            openFloorAdd : null,
            openRoomAdd : null
        });
    },

    showValidationMessage: function(){
        var message = { content: "Please fill all parameters before clicking \"Submit\"",
            type: "info"};
        this.addNotification(message);
    },

    openAddBuildingForm:function(){
        var openBuildingDetails = this.state.openBuildingDetails;
        this.setState({
            openBuildingAdd : openBuildingDetails
        });
    },

    openAddFloorForm:function(){
        var openFloorDetails = this.state.openFloorDetails;
        this.setState({
            openFloorAdd : openFloorDetails
        });
    },

    openAddRoomForm:function(){
        var openRoomDetails = this.state.openRoomDetails;
        this.setState({
            openRoomAdd : openRoomDetails
        });
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
                                            onChange={this.updateFormStatus}
                                            ref={(ref) => this.state.refTree = ref}/>
                            </Col>
                            <Col sm="4">
                                {this.state.openBuildingDetails != null ? <AddBuildingForm ref={(ref) => this.state.refSelectedBuilding = ref}
                                                                                           onChange={this.onResponseFromUpdate}
                                                                                           onValidate={this.showValidationMessage}
                                                                                           disableAdd={true}
                                                                                           nodeInfo={this.state.openNode}/> : null}
                                {this.state.openBuildingDetails != null ? <br /> : null}
                                {this.state.openBuildingDetails != null ? <Button onClick={this.openAddBuildingForm}>Create a New Building</Button> : null}
                            </Col>
                            <Col sm="4">
                                {(this.state.openBuildingDetails != null && this.state.openBuildingAdd != null)?
                                                                          <AddBuildingForm onChange={this.onResponseFromUpdate}
                                                                                           onValidate={this.showValidationMessage}
                                                                                           disableAdd={false} /> : null}
                            </Col>
                            <Col sm="4">
                                {this.state.openFloorDetails != null ? <AddFloorForm ref={(ref) => this.state.refSelectedFloor = ref}
                                                                                 onChange={this.onResponseFromUpdate}
                                                                                 onValidate={this.showValidationMessage}
                                                                                 disableAdd={true}
                                                                                 nodeInfo={this.state.openNode}/>  : null}
                                {this.state.openFloorDetails != null ? <br /> : null}
                                {this.state.openFloorDetails != null ? <Button onClick={this.openAddFloorForm}>Create a New Floor</Button> : null}
                            </Col>
                            <Col sm="4">
                                {(this.state.openFloorDetails != null && this.state.openFloorAdd != null) ?
                                                                        <AddFloorForm onChange={this.onResponseFromUpdate}
                                                                                      onValidate={this.showValidationMessage}
                                                                                      disableAdd={false}/>  : null}
                            </Col>
                            <Col sm="4">
                                {this.state.openRoomDetails != null ? <AddRoomForm ref={(ref) => this.state.refSelectedRoom = ref}
                                                                               onChange={this.onResponseFromUpdate}
                                                                               onValidate={this.showValidationMessage}
                                                                               disableAdd={true}
                                                                               nodeInfo={this.state.openNode}/> : null}
                                {this.state.openRoomDetails != null ? <br /> : null}
                                {this.state.openRoomDetails != null ? <Button onClick={this.openAddRoomForm}>Create a New Room</Button> : null}
                            </Col>
                            <Col sm="4">
                                {(this.state.openRoomDetails != null && this.state.openRoomAdd != null) ?
                                                                      <AddRoomForm onChange={this.onResponseFromUpdate}
                                                                                   onValidate={this.showValidationMessage}
                                                                                   disableAdd={false}/> : null}
                            </Col>
                            </Card>
                            {this.state.openRoomDetails != null ? <AddRoomCoordForm onChange={this.onResponseFromUpdate}
                                                                           onValidate={this.showValidationMessage}/> : null}
                        </Row>
                    </Container>
                    <hr/>
                </div>
            </div>
        );
    }

});



export default App;
