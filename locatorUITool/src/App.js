import React from 'react';
import NotificationSystem from 'react-notification-system';
import { Container, Row, Col, Card} from 'reactstrap';
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
            openBuildingAdd: buildingFormOpenStatus,
            openFloorAdd: floorFormOpenStatus,
            openRoomAdd: roomFormOpenStatus
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
                                {this.state.openBuildingAdd != null ? <AddBuildingForm onChange={this.onResponseFromUpdate}
                                                                                        onValidate={this.showValidationMessage}
                                                                                        disableAdd={false} /> : null}
                                {this.state.openBuildingAdd != null ? <AddBuildingForm ref={(ref) => this.state.refSelectedBuilding = ref}
                                                                                       onChange={this.onResponseFromUpdate}
                                                                                       onValidate={this.showValidationMessage}
                                                                                       disableAdd={true}
                                                                                       nodeInfo={this.state.openNode}/> : null}
                                {this.state.openFloorAdd != null ? <AddFloorForm onChange={this.onResponseFromUpdate}
                                                                                    onValidate={this.showValidationMessage}
                                                                                    disableAdd={false}/>  : null}
                                {this.state.openFloorAdd != null ? <AddFloorForm ref={(ref) => this.state.refSelectedFloor = ref}
                                                                                 onChange={this.onResponseFromUpdate}
                                                                                 onValidate={this.showValidationMessage}
                                                                                 disableAdd={true}
                                                                                 nodeInfo={this.state.openNode}/>  : null}
                                {this.state.openRoomAdd != null ? <AddRoomForm onChange={this.onResponseFromUpdate}
                                                                               onValidate={this.showValidationMessage}
                                                                               disableAdd={false}/> : null}
                                {this.state.openRoomAdd != null ? <AddRoomForm ref={(ref) => this.state.refSelectedRoom = ref}
                                                                               onChange={this.onResponseFromUpdate}
                                                                               onValidate={this.showValidationMessage}
                                                                               disableAdd={true}
                                                                               nodeInfo={this.state.openNode}/> : null}
                            </Card>
                            {this.state.openRoomAdd != null ? <AddRoomCoordForm onChange={this.onResponseFromUpdate}
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
