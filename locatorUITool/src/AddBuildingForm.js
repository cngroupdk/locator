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
            refAddBuildingAddress : "",
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
                        refAddBuildingAddress: result.address,
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

    onAddBuildingAddressInputChange(event){
        this.setState({refAddBuildingAddress: event.target.value});
    },

    onAddBuildingSubmit(){
        this.onBuildingSubmitHandler('add');
    },

    onUpdateBuildingSubmit(){
        this.onBuildingSubmitHandler('update');
    },

    onDeleteBuildingSubmit(){
        this.onBuildingSubmitHandler('delete');
    },

    toggle() {
        var id = this.state.refAddBuildingId;
        var name = this.state.refAddBuildingName;
        var city = this.state.refAddBuildingCity;
        var address = this.state.refAddBuildingAddress;

        if(id !== "" && name !== "" && city !== "" && address !== "")
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
        var address = this.state.refAddBuildingAddress;

        var passedValidation = false;

        if(id !== "" && name !== "" && city !== "" && address !== "")
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
                    address: selected.address
                };
            }
            else {
                rawData = {
                    buildingGuid: -1,
                    buildingId: id,
                    type: "Office Space",
                    name: name,
                    city: city,
                    address: address,
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
                passedValidation = true;
            }).catch((response) => {
                var messageContent = "Error, New Building Not Submitted. Please Contact Your Administrator.";
                this.props.onChange({response, messageContent});
            });
        }
        else{
            this.props.onValidate();
        }

        if(passedValidation === true) {
            this.setState({
                refAddBuildingId: "",
                refAddBuildingName: "",
                refAddBuildingCity: "",
                refAddBuildingAddress: ""
            });
        }

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
                    <InputGroupAddon>Address</InputGroupAddon>
                    <Input className="AddBuilding"
                           placeholder="Type address..."
                           value={this.state.refAddBuildingAddress}
                           onChange={this.onAddBuildingAddressInputChange}
                           disabled={this.state.disableAdd}/>
                </InputGroup>
                <br />
                {this.state.disableAdd === false ?
                <Button id="AddRoomButton"
                        color="primary"
                        onClick={this.onAddBuildingSubmit}>Submit</Button> : null}
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