/**
 * Created by cano on 9.11.2016.
 */

import React from 'react';
import { CardTitle, Button, Input, InputGroup, InputGroupAddon, Modal, ModalHeader, ModalBody} from 'reactstrap';
import $ from 'jquery';


var AddBuildingForm = React.createClass({

    getInitialState: function () {
        return {
            modal: false,
            refAddBuildingId : "",
            refAddBuildingName : "",
            refAddBuildingCity : "",
            refAddBuildingStreet : "",
            refAddBuildingNumber : "",
            refAddBuildingPostalCode : "",
            nodeInfo : this.props.nodeInfo,
            disableAdd : this.props.disableAdd,
            selectedBuilding : null

        };
    },

    componentDidMount() {
        this.loadBuildingDetails(this.state.nodeInfo);
    },

    loadBuildingDetails(node){
        if(!(node == null)){
            var buildingURL = 'http://localhost:8080/buildings/' + node.buildingId;

            this.serverRequest = $.get(buildingURL, function (result) {

                if(!(this.state.refAddBuildingId == null)) {

                    this.setState({
                        refAddBuildingId: result.buildingId,
                        refAddBuildingName: result.name,
                        refAddBuildingCity: result.city,
                        refAddBuildingStreet: result.streetName,
                        refAddBuildingNumber: result.streetNumber,
                        refAddBuildingPostalCode: result.postalCode,
                        selectedBuilding: result
                    });

                }
            }.bind(this));
        }
    },

    disableAdd(event){
      this.setState({disableAdd: event});
    },

    disableDelete(event){
        this.setState({disableDelete: event});
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

    onAddBuildingSubmit(){
        this.onBuildingSubmitHandler('add');
    },

    onDeleteBuildingSubmit(){
        this.onBuildingSubmitHandler('delete');
    },

    toggle() {
        var id = this.state.refAddBuildingId;
        var name = this.state.refAddBuildingName;
        var city = this.state.refAddBuildingCity;
        var street = this.state.refAddBuildingStreet;
        var number = this.state.refAddBuildingNumber;
        var pc = this.state.refAddBuildingPostalCode;

        if(id !== "" && name !== "" && city !== "" && street !== "" && number !== "" && pc !== "")
        {
            this.setState({
                modal: !this.state.modal
            });
        }
        else{
            this.props.onValidate();
        }

    },

    onBuildingSubmitHandler(type){

        var url = 'http://localhost:8080/buildings/new/building';
        if (type === "delete")
            url = 'http://localhost:8080/buildings/delete/building';

        var id = this.state.refAddBuildingId;
        var name = this.state.refAddBuildingName;
        var city = this.state.refAddBuildingCity;
        var street = this.state.refAddBuildingStreet;
        var number = this.state.refAddBuildingNumber;
        var pc = this.state.refAddBuildingPostalCode;

        if(id !== "" && name !== "" && city !== "" && street !== "" && number !== "" && pc !== "")
        {
            var rawData = null;

            if (type === "delete") {

                var selected = this.state.selectedBuilding;
                rawData = {
                    buildingGuid: selected.buildingGuid,
                    buildingId: selected.buildingId,
                    type: selected.type,
                    name: selected.name,
                    city: selected.city,
                    postalCode: selected.postalCode,
                    streetName: selected.streetName,
                    streetNumber: selected.streetNumber
                };
            }
            else {
                rawData = {
                    buildingGuid: -1,
                    buildingId: id,
                    type: "Office Space",
                    name: name,
                    city: city,
                    postalCode: pc,
                    streetName: street,
                    streetNumber: number
                };
            }
            var jsonData = JSON.stringify(rawData);

            fetch(url,{
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
            <div>
                {this.state.disableAdd === false ? <CardTitle>Add a Building</CardTitle> : null}
                {this.state.disableAdd === true ? <CardTitle>Building Details</CardTitle> : null}
                <hr/>
                <InputGroup>
                    <InputGroupAddon>Building Id</InputGroupAddon>
                    <Input className="AddBuilding"
                           placeholder="Type building ID..."
                           value={this.state.refAddBuildingId}
                           onChange={this.onAddBuildingIdInputChange}
                           disabled={this.state.disableAdd}/>
                </InputGroup>
                <br />
                <InputGroup>
                    <InputGroupAddon>Building Name</InputGroupAddon>
                    <Input className="AddBuilding"
                           placeholder="Type building name..."
                           value={this.state.refAddBuildingName}
                           onChange={this.onAddBuildingNameInputChange}
                           disabled={this.state.disableAdd}/>
                </InputGroup>
                <br />
                <InputGroup>
                    <InputGroupAddon>City</InputGroupAddon>
                    <Input className="AddBuilding"
                           placeholder="Type city..."
                           value={this.state.refAddBuildingCity}
                           onChange={this.onAddBuildingCityInputChange}
                           disabled={this.state.disableAdd}/>
                </InputGroup>
                <br />
                <InputGroup>
                    <InputGroupAddon>Street</InputGroupAddon>
                    <Input className="AddBuilding"
                           placeholder="Type street..."
                           value={this.state.refAddBuildingStreet}
                           onChange={this.onAddBuildingStreetInputChange}
                           disabled={this.state.disableAdd}/>
                </InputGroup>
                <br />
                <InputGroup>
                    <InputGroupAddon>Number</InputGroupAddon>
                    <Input className="AddBuilding"
                           placeholder="Type number..."
                           value={this.state.refAddBuildingNumber}
                           onChange={this.onAddBuildingNumberInputChange}
                           disabled={this.state.disableAdd}/>
                </InputGroup>
                <br />
                <InputGroup>
                    <InputGroupAddon>Postal Code</InputGroupAddon>
                    <Input className="AddBuilding"
                           placeholder="Type Postal Code..."
                           value={this.state.refAddBuildingPostalCode}
                           onChange={this.onAddBuildingPostalInputChange}
                           disabled={this.state.disableAdd}/>
                </InputGroup>
                <br />
                {this.state.disableAdd === false ?
                <Button id="AddRoomButton"
                        color="primary"
                        onClick={this.toggle}>Submit</Button> : null}
                {this.state.disableAdd === true ?
                    <Button id="AddRoomButton"
                        color="primary"
                        onClick={this.toggle}>Delete</Button> : null}
                <Modal id="AddBuilding"
                   isOpen={this.state.modal}
                   toggle={this.toggle}
                >
                    <ModalHeader>Confirmation</ModalHeader>
                    <ModalBody>
                        <p>Are you sure?</p>
                        {this.state.disableAdd === false ?
                        <Button id="EditLocationButton"
                                onClick={this.onAddBuildingSubmit}>
                            Submit
                        </Button> : null}
                        {this.state.disableAdd === true ?
                            <Button id="EditLocationButton"
                                    onClick={this.onDeleteBuildingSubmit}>
                                Submit
                            </Button> : null}
                        <Button id="EditLocationButton"
                                onClick={this.toggle}>
                            Cancel
                        </Button>
                    </ModalBody>
                </Modal>
            </div>
        );
    }

});

export default AddBuildingForm;