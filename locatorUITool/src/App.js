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

        this.setState({
            openBuildingDetails : null,
            openBuildingAdd : true,
            openFloorDetails : null,
            openFloorAdd : null,
            openRoomAdd : null,
            openRoomDetails : null
        });
    },

    openAddFloorForm:function(){
        var openBuildingDetails = this.state.openBuildingDetails;
        this.setState({
            openFloorAdd : openBuildingDetails
        });
    },

    openAddRoomForm:function(){
        var openFloorDetails = this.state.openFloorDetails;
        this.setState({
            openRoomAdd : openFloorDetails
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
                                {(this.state.openBuildingDetails == null && this.state.openFloorDetails == null)?
                                    <div>
                                        <hr/>
                                        <Button onClick={this.openAddBuildingForm}>Create a New Building</Button>
                                    </div>
                                    : null }
                                {this.state.openBuildingDetails != null ?
                                    <div>
                                        <hr/>
                                        <Button onClick={this.openAddFloorForm}>Create a New Floor</Button>
                                    </div>
                                    : null }
                                {this.state.openFloorDetails != null ?
                                    <div>
                                        <hr/>
                                        <Button onClick={this.openAddRoomForm}>Create a New Room</Button>
                                    </div>
                                    : null }
                            </Col>
                            {this.state.openBuildingDetails != null ?
                            <Col sm="4">
                                <AddBuildingForm ref={(ref) => this.state.refSelectedBuilding = ref}
                                                                                           onChange={this.onResponseFromUpdate}
                                                                                           onValidate={this.showValidationMessage}
                                                                                           disableAdd={true}
                                                                                           nodeInfo={this.state.openNode}/>
                            </Col> : null}
                            {this.state.openBuildingAdd != null ?
                            <Col sm="4">
                                <AddBuildingForm onChange={this.onResponseFromUpdate}
                                                 onValidate={this.showValidationMessage}
                                                 disableAdd={false} />
                            </Col>
                            : null}
                            {this.state.openFloorDetails != null ?
                            <Col sm="4">
                                 <AddFloorForm ref={(ref) => this.state.refSelectedFloor = ref}
                                                                                 onChange={this.onResponseFromUpdate}
                                                                                 onValidate={this.showValidationMessage}
                                                                                 disableAdd={true}
                                                                                 nodeInfo={this.state.openNode}/>
                            </Col>
                            : null}
                            {this.state.openFloorAdd != null ?
                            <Col sm="4">
                                <AddFloorForm onChange={this.onResponseFromUpdate}
                                              onValidate={this.showValidationMessage}
                                              disableAdd={false}
                                              nodeInfo={this.state.openNode}
                                              addingFloor ={false}/>
                            </Col>
                            : null}
                            {this.state.openRoomDetails != null ?
                            <Col sm="4">
                                <AddRoomForm ref={(ref) => this.state.refSelectedRoom = ref}
                                             onChange={this.onResponseFromUpdate}
                                             onValidate={this.showValidationMessage}
                                             disableAdd={true}
                                             nodeInfo={this.state.openNode}/>
                                <br />
                            </Col>
                            : null}
                            {this.state.openRoomAdd != null ?
                            <Col sm="4">
                                <AddRoomForm onChange={this.onResponseFromUpdate}
                                             onValidate={this.showValidationMessage}
                                             nodeInfo={this.state.openNode}
                                             addingRoom ={true}
                                             disableAdd={false}/>
                            </Col>
                                : null}
                            </Card>
                            {this.state.openRoomDetails != null ? <AddRoomCoordForm onChange={this.onResponseFromUpdate}
                                                                                    onValidate={this.showValidationMessage}
                                                                                    nodeInfo={this.state.openNode}/> : null}
                        </Row>
                    </Container>
                    <hr/>
                </div>
            </div>
        );
    }

});



export default App;
