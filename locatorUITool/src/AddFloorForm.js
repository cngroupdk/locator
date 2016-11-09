/**
 * Created by cano on 9.11.2016.
 */
import React from 'react';
import { Col, CardTitle, Button, Input, InputGroup, InputGroupAddon} from 'reactstrap';
import BuildingDropdown from './BuildingDropdown';

var AddFloorForm = React.createClass({

    getInitialState: function () {
        return {

            refAddFloorBuilding: null,
            refAddFloorName : "",
            refAddFloorRoomNumber : "",
            refAddFloorURL: "",

        };
    },

    onAddFloorNameInputChange(event) {
        this.setState({refAddFloorName: event.target.value});
    },

    onAddFloorRoomNumberInputChange(event) {
        this.setState({refAddFloorRoomNumber: event.target.value});
    },

    onAddFloorURLInputChange(event) {
        this.setState({refAddFloorURL: event.target.value});
    },

    onAddFloorSubmitHandler(){

        var buildingName = this.state.refAddFloorBuilding.getCurrentBuilding().currentBuilding;
        var floorName = this.state.refAddFloorName;
        var floorRoomsNumber = this.state.refAddFloorRoomNumber;
        var floorURL = this.state.refAddFloorURL;

        var buildingData = this.state.refAddFloorBuilding.getBuildingData();

        if( buildingName!== 'Choose Building' &&
            floorName !== '' &&
            floorURL !== '' &&
            floorRoomsNumber !== ''){

            var rawData = {
                floorId : 0,
                floorName : floorName,
                roomsNumber : floorRoomsNumber,
                floorplanUrl : '/' + floorURL,
                type : 'General',
                buildingId : buildingData.selectedBuilding.buildingId
            };

            var jsonData = JSON.stringify(rawData);

            fetch('http://localhost:8080/floors/new/floor',{
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: jsonData
            })
            .then((response) => {
                var messageContent = "Success! New Floor Submitted.";
                this.props.onChange({response, messageContent});
            }).catch((response) => {
                var messageContent = "Error, New Floor Not Submitted. Please Contact Your Administrator.";
                this.props.onChange({response, messageContent});
            });
        }
        else{
            this.props.onValidate();
        }

        this.state.refAddFloorBuilding.updateBuilding("Choose Building");
        this.setState({
            refAddFloorName : "",
            refAddFloorRoomNumber : "",
            refAddFloorURL: "",
        });
    },

    onSelectBuilding : function() {

    },

    render:function() {
        return (
            <Col sm="5">
                <CardTitle>Add a Floor</CardTitle>
                <hr/>

                <BuildingDropdown className="MyBuilding"
                                  url="http://localhost:8080/buildings"
                                  ref={(ref) => this.state.refAddFloorBuilding = ref}
                                  onChange={this.onSelectBuilding}
                                  disabled={false}
                />
                <InputGroup>
                    <InputGroupAddon>Floor Name</InputGroupAddon>
                    <Input className="CoordinateInput"
                           placeholder="Type floor name..."
                           value={this.state.refAddFloorName}
                           onChange={this.onAddFloorNameInputChange}/>
                </InputGroup>
                <br />
                <InputGroup>
                    <InputGroupAddon>Contains</InputGroupAddon>
                    <Input className="CoordinateInput"
                           placeholder="Type # of Rooms..."
                           value={this.state.refAddFloorRoomNumber}
                           onChange={this.onAddFloorRoomNumberInputChange}/>
                    <InputGroupAddon>Rooms</InputGroupAddon>
                </InputGroup>
                <br />
                <InputGroup>
                    <InputGroupAddon>Image File</InputGroupAddon>
                    <Input className="CoordinateInput"
                           placeholder="Type floor image file name..."
                           value={this.state.refAddFloorURL}
                           onChange={this.onAddFloorURLInputChange}/>
                </InputGroup>
                <br />
                <Button id="AddRoomButton"
                        color="primary"
                        onClick={this.onAddFloorSubmitHandler}>Submit</Button>
            </Col>
        );
    }
});


export default AddFloorForm;