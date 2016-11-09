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
            openBuildingAdd: buildingFormOpenStatus,
            openFloorAdd: floorFormOpenStatus,
            openRoomAdd: roomFormOpenStatus
        });

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
                                                                                        onValidate={this.showValidationMessage}/> : null}
                                {this.state.openFloorAdd != null ? <AddFloorForm onChange={this.onResponseFromUpdate}
                                                                                    onValidate={this.showValidationMessage}/>  : null}
                                {this.state.openRoomAdd != null ? <AddRoomForm onChange={this.onResponseFromUpdate}
                                                                                onValidate={this.showValidationMessage}/> : null}
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
