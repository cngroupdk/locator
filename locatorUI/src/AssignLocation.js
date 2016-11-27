/**
 * Created by victorcano on 23/11/2016.
 */
import React from 'react';
import {
    Button,
    Modal,
    ModalHeader,
    ModalBody
} from 'reactstrap';
import BuildingDropdown from './BuildingDropdown';
import RoomDropdown from './RoomDropdown';

var AssignLocation = React.createClass({

    getInitialState: function () {
        return {

            popoverOpen : false,
            myEditBuilding: null,
            myEditRoom : null,
            selectedEmployee: this.props.selectedEmployee
        };
    },

    onEditLocationBuilding: function(event){

        var selectedBuilding = event.selectedBuilding;
        this.state.myEditRoom.loadCommentsFromServer('http://localhost:8080/rooms/' + selectedBuilding.buildingId);
    },

    onEditLocationRoom: function(event){

    },

    onToggleClick(){
        this.props.onToggleClick();
    },

    getPopoverState(){

        var popoverOpen = this.state.popoverOpen;

        return {
            popoverOpen
        };
    },

    setSelectedEmployee(selectedEmployee){
        this.setState({
            selectedEmployee: selectedEmployee,
        });
    },
    toggle() {
        this.setState({
            popoverOpen: !this.state.popoverOpen,
        });
    },


    onSubmitNewLocationHandler: function () {

        var roomName = this.state.myEditRoom.getCurrentRoom().currentRoom;
        var roomData = this.state.myEditRoom.getRoomData().selectedRoom;

        if( roomName !== 'Select a Room')
        {

            var employeeData = this.state.selectedEmployee;
            var newLocation = roomData.name + "@" + roomData.floorName + "@" + roomData.buildingId;

            employeeData.location = newLocation;

            var jsonData = JSON.stringify(employeeData);
            fetch('http://localhost:8080/employees/update/employee',{
                method: 'POST',
                mode: 'cors',
                redirect: 'follow',
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
                this.props.onSubmit({updateDone : true, message});

            }).catch((error) => {
                console.error(error);
            });
        }
        else{
            var message = {
                content: "Please Choose a New Room Before Clicking \"Submit\"",
                type: "ValidationError"
            };
            this.props.onSubmit({updateDone : false, message});
        }
    },


    render: function() {
        return (

            <div className="selectorBox">
                <Modal id="EditLocation"
                       placement="bottom"
                       isOpen={this.state.popoverOpen}
                       target="EditPen"
                       toggle={this.onToggleClick}
                >
                    <ModalHeader>Edit Employee Location</ModalHeader>
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
            </div>
        );
    }

});

export default AssignLocation;