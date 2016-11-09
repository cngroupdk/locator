/**
 * Created by cano on 9.11.2016.
 */

import React from 'react';
import { Col, CardTitle, Button, Input, InputGroup, InputGroupAddon} from 'reactstrap';


var AddBuildingForm = React.createClass({

    getInitialState: function () {
        return {

            refAddBuildingId : "",
            refAddBuildingName : "",
            refAddBuildingCity : "",
            refAddBuildingStreet : "",
            refAddBuildingNumber : "",
            refAddBuildingPostalCode : "",

        };
    },

    onAddBuildingIdInputChange(event){
        this.setState({refAddBuildingId: event.target.value});
    },

    onAddBuildingNameInputChange(event){
        this.setState({refAddBuildingName: event.target.value});
    },

    onAddBuildingCityInputChange(event){
        this.setState({refAddBuildingCity: event.target.value});
    },

    onAddBuildingStreetInputChange(event){
        this.setState({refAddBuildingStreet: event.target.value});
    },


    onAddBuildingNumberInputChange(event){
        this.setState({refAddBuildingNumber: event.target.value});
    },

    onAddBuildingPostalInputChange(event){
        this.setState({refAddBuildingPostalCode: event.target.value});
    },

    onAddBuildingSubmitHandler(){
        var id = this.state.refAddBuildingId;
        var name = this.state.refAddBuildingName;
        var city = this.state.refAddBuildingCity;
        var street = this.state.refAddBuildingStreet;
        var number = this.state.refAddBuildingNumber;
        var pc = this.state.refAddBuildingPostalCode;

        if(id !== "" && name !== "" && city !== "" && street !== "" && number !== "" && pc !== "")
        {
            var rawData = {
                buildingGuid : -1,
                buildingId : id,
                type : "Office Space",
                name : name,
                city : city,
                postalCode : pc,
                streetName : street,
                streetNumber : number
            };

            var jsonData = JSON.stringify(rawData);

            fetch('http://localhost:8080/buildings/new/building',{
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: jsonData
            })
            .then((response) => {
                var messageContent = "Success! New Building Submitted.";
                this.props.onChange({response, messageContent});
            }).catch((response) => {
                var messageContent = "Error, New Building Not Submitted. Please Contact Your Administrator.";
                this.props.onChange({response, messageContent});
            });
        }
        else{
            this.props.onValidate();
        }

        this.setState({
            refAddBuildingId : "",
            refAddBuildingName : "",
            refAddBuildingCity : "",
            refAddBuildingStreet : "",
            refAddBuildingNumber : "",
            refAddBuildingPostalCode : ""
        });

    },

    render:function() {
        return (
            <Col sm="4">
                <CardTitle>Add a Building</CardTitle>
                <hr/>
                <InputGroup>
                    <InputGroupAddon>Building Id</InputGroupAddon>
                    <Input className="AddBuilding"
                           placeholder="Type building ID..."
                           value={this.state.refAddBuildingId}
                           onChange={this.onAddBuildingIdInputChange}/>
                </InputGroup>
                <br />
                <InputGroup>
                    <InputGroupAddon>Building Name</InputGroupAddon>
                    <Input className="AddBuilding"
                           placeholder="Type building name..."
                           value={this.state.refAddBuildingName}
                           onChange={this.onAddBuildingNameInputChange}/>
                </InputGroup>
                <br />
                <InputGroup>
                    <InputGroupAddon>City</InputGroupAddon>
                    <Input className="AddBuilding"
                           placeholder="Type city..."
                           value={this.state.refAddBuildingCity}
                           onChange={this.onAddBuildingCityInputChange}/>
                </InputGroup>
                <br />
                <InputGroup>
                    <InputGroupAddon>Street</InputGroupAddon>
                    <Input className="AddBuilding"
                           placeholder="Type street..."
                           value={this.state.refAddBuildingStreet}
                           onChange={this.onAddBuildingStreetInputChange}/>
                </InputGroup>
                <br />
                <InputGroup>
                    <InputGroupAddon>Number</InputGroupAddon>
                    <Input className="AddBuilding"
                           placeholder="Type number..."
                           value={this.state.refAddBuildingNumber}
                           onChange={this.onAddBuildingNumberInputChange}/>
                </InputGroup>
                <br />
                <InputGroup>
                    <InputGroupAddon>Postal Code</InputGroupAddon>
                    <Input className="AddBuilding"
                           placeholder="Type Postal Code..."
                           value={this.state.refAddBuildingPostalCode}
                           onChange={this.onAddBuildingPostalInputChange}/>
                </InputGroup>
                <br />
                <Button id="AddRoomButton"
                        color="primary"
                        onClick={this.onAddBuildingSubmitHandler}>Submit</Button>

            </Col>
        );
    }

});

export default AddBuildingForm;