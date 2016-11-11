/**
 * Created by cano on 9.11.2016.
 */
import React from 'react';
import request from 'superagent';
import { CardTitle, Button, Input, InputGroup, InputGroupAddon, Modal, ModalHeader, ModalBody} from 'reactstrap';
import BuildingDropdown from './BuildingDropdown';
import Dropzone from 'react-dropzone';
import $ from 'jquery';

var AddFloorForm = React.createClass({

    getInitialState: function () {
        return {
            modal : false,
            refAddFloorBuilding: null,
            refAddFloorName : "",
            refAddFloorRoomNumber : "",
            refAddFloorURL: "",
            refNewFile: null,
            nodeInfo : this.props.nodeInfo,
            disableAdd : this.props.disableAdd,
            selectedFloor : null,
            disableIMG : true,
        };
    },

    componentDidMount() {
        this.loadFloorDetails(this.state.nodeInfo);
    },

    loadFloorDetails(node){
        if(!(node == null)){
            var buildingURL = 'http://localhost:8080/floors/' + node.buildingId + '/' + node.name;

            this.serverRequest = $.get(buildingURL, function (result) {

                if(!(this.state.refAddFloorBuilding == null)) {
                    this.state.refAddFloorBuilding.updateBuilding(result.buildingId);

                    this.setState({
                        refAddFloorName: result.floorName,
                        refAddFloorRoomNumber: result.roomsNumber,
                        refAddFloorURL: result.floorplanUrl,
                        selectedFloor: result
                    });
                }

            }.bind(this));
        }
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

    onAddFloorSubmit(){
        this.onBuildingSubmitHandler('add');
    },

    onDeleteFloorSubmit(){
        this.onBuildingSubmitHandler('delete');
    },

    toggle() {
        var buildingName = this.state.refAddFloorBuilding.getCurrentBuilding().currentBuilding;
        var floorName = this.state.refAddFloorName;
        var floorRoomsNumber = this.state.refAddFloorRoomNumber;
        var floorURL = this.state.refAddFloorURL;

        if( buildingName!== 'Choose Building' &&
            floorName !== '' &&
            floorURL !== '' &&
            floorRoomsNumber !== ''){
            this.setState({
                modal: !this.state.modal
            });
        }
        else{
            this.props.onValidate();
        }

        this.setState({
            disableIMG: true
        });

    },

    onAddFloorSubmitHandler(type){

        var url = 'http://localhost:8080/floors/new/floor';
        if (type === "delete")
            url = 'http://localhost:8080/floors/delete/floor';

        var buildingName = this.state.refAddFloorBuilding.getCurrentBuilding().currentBuilding;
        var floorName = this.state.refAddFloorName;
        var floorRoomsNumber = this.state.refAddFloorRoomNumber;
        var floorURL = this.state.refAddFloorURL;

        var buildingData = this.state.refAddFloorBuilding.getBuildingData();

        if( buildingName!== 'Choose Building' &&
            floorName !== '' &&
            floorURL !== '' &&
            floorRoomsNumber !== ''){

            var rawData = null;

            if (type === "delete") {

                var selected = this.state.selectedFloor;
                rawData = {
                    floorId: selected.floorId,
                    floorName: selected.floorName,
                    roomsNumber: selected.roomsNumber,
                    floorplanUrl: selected.floorplanUrl,
                    type: selected.type,
                    buildingId: selected.buildingId
                };
            }
            else {
                rawData = {
                    floorId: 0,
                    floorName: floorName,
                    roomsNumber: floorRoomsNumber,
                    floorplanUrl: '/' + floorURL,
                    type: 'General',
                    buildingId: buildingData.selectedBuilding.buildingId
                };
            }

            var jsonData = JSON.stringify(rawData);

            fetch(url,{
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

    onDrop: function (acceptedFiles, rejectedFiles) {
        if(acceptedFiles.length > 0 && rejectedFiles.length == 0) {
            this.setState({
                refNewFile: acceptedFiles
            });
        }
        console.log('Accepted files: ', acceptedFiles);
        console.log('Rejected files: ', rejectedFiles);
    },

    onShowConfirmation(){
        this.setState({
            disableIMG : false,
            modal: !this.state.modal
        });
    },

    onSubmitNewFloorplan(){

        var req = request.post('http://localhost:8080/files/upload');

        this.state.refNewFile.forEach((file)=> {
            req.attach('file', file);
        });

        req.end(
            function(error, response){
                var messageContent = "Success! New Picture Submitted.";
                this.props.onChange({response, messageContent});
            }
        );

        this.setState({
            disableIMG : false
        });

    },

    render:function() {
        return (
            <div>
                {this.state.disableAdd === false ? <CardTitle>Add a Floor</CardTitle> : null}
                {this.state.disableAdd === true ? <CardTitle>Floor Details</CardTitle> : null}
                <hr/>
                <BuildingDropdown className="MyBuilding"
                                  url="http://localhost:8080/buildings"
                                  ref={(ref) => this.state.refAddFloorBuilding = ref}
                                  onChange={this.onSelectBuilding}
                                  disabled={this.state.disableAdd}
                />
                <InputGroup>
                    <InputGroupAddon>Floor Name</InputGroupAddon>
                    <Input className="CoordinateInput"
                           placeholder="Type floor name..."
                           value={this.state.refAddFloorName}
                           onChange={this.onAddFloorNameInputChange}
                           disabled={this.state.disableAdd}/>
                </InputGroup>
                <br />
                <InputGroup>
                    <InputGroupAddon>Contains</InputGroupAddon>
                    <Input className="CoordinateInput"
                           placeholder="Type # of Rooms..."
                           value={this.state.refAddFloorRoomNumber}
                           onChange={this.onAddFloorRoomNumberInputChange}
                           disabled={this.state.disableAdd}/>
                    <InputGroupAddon>Rooms</InputGroupAddon>
                </InputGroup>
                <br />
                <InputGroup>
                    <InputGroupAddon>Image File</InputGroupAddon>
                    <Input className="CoordinateInput"
                           placeholder="Add image file name..."
                           value={this.state.refAddFloorURL}
                           onChange={this.onAddFloorURLInputChange}
                           disabled={true}/>
                </InputGroup>
                <br/>
                <Dropzone onDrop={this.onDrop}>
                    <div>Drop image / Click to Select.</div>
                </Dropzone>

                {this.state.refNewFile ?
                <div>
                    <p>Uploading {this.state.refNewFile.length} files...</p>
                    <div>{this.state.refNewFile.map((file) => <img id="FloorMapPreview" src={file.preview} />)}</div>
                    <br/>
                    <Button id="AddRoomButton"
                            color="primary"
                            onClick={this.onShowConfirmation}>Upload Image</Button>
                    <br/>
                </div> : null}
                <br/>
                {this.state.disableAdd === false ?
                <Button id="AddRoomButton"
                        color="primary"
                        onClick={this.toggle}>Submit</Button> : null }

                {this.state.disableAdd === true ?
                    <Button id="AddRoomButton"
                            color="primary"
                            onClick={this.toggle}>Delete</Button> : null }

                <Modal id="AddBuilding"
                       isOpen={this.state.modal}
                       toggle={this.toggle}
                >
                    <ModalHeader>Confirmation</ModalHeader>
                    <ModalBody>
                        <p>Are you sure?</p>

                        {this.state.disableAdd === false ?
                            <Button id="EditLocationButton"
                                    onClick={this.onAddFloorSubmit}>
                                Submit
                            </Button> : null}

                        {(this.state.disableAdd === true && this.state.disableIMG === true)?
                            <Button id="EditLocationButton"
                                    onClick={this.onDeleteFloorSubmit}>
                                Submit Deletion
                            </Button> : null}


                        {this.state.disableIMG === false ?
                            <Button id="EditLocationButton"
                                    onClick={this.onSubmitNewFloorplan}>
                                Submit Image
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


export default AddFloorForm;