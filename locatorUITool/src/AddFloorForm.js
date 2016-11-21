/**
 * Created by cano on 9.11.2016.
 */
import React from 'react';
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
            refAddFloorURL: "",
            refNewFile: null,
            nodeInfo : this.props.nodeInfo,
            selectedFloor : null
        };
    },

    componentDidMount() {
        this.loadFloorDetails(this.state.nodeInfo);
    },

    loadFloorDetails(node){
        if(!(node == null)){

            var buildingURL =
            'http://localhost:8080/floors/' + node.buildingId + '/' + node.name;
            if(this.props.disableAdd === false) {
                buildingURL = 'http://localhost:8080/buildings/' + node.buildingId;
            }

            this.serverRequest = $.get(buildingURL, function (result) {

                if(!(this.state.refAddFloorBuilding == null)) {

                    this.state.refAddFloorBuilding.updateBuilding(result.buildingId);

                    if(this.props.disableAdd === true) {
                        this.setState({
                            refAddFloorName: result.floorName,
                            refAddFloorURL: result.floorplanUrl,
                            selectedFloor: result
                        });
                    }
                }

            }.bind(this));
        }
    },

    onAddFloorNameInputChange(event) {
        this.setState({refAddFloorName: event.target.value});
    },

    onAddFloorURLInputChange(event) {
        this.setState({refAddFloorURL: event.target.value});
    },

    onAddFloorSubmit(){
        this.onFloorSubmitHandler({
            type: 'add',
            url: 'http://localhost:8080/floors/new/floor',
            messageContent: "New Floor Added."});
    },

    onDeleteFloorSubmit(){
        this.onFloorSubmitHandler({
            type: 'delete',
            url: 'http://localhost:8080/floors/delete/floor',
            messageContent:"New Floor Deleted."});
    },


    onUpdateFloorSubmit(){

        if(this.state.refNewFile != null) {

            this.onFloorSubmitHandler({
                type: 'update',
                url: 'http://localhost:8080/floors/update/floor',
                messageContent: "Floor Data Updated."
            });
        }
        else{
            this.props.onValidate();
        }
    },

    toggle() {
        var buildingName = this.state.refAddFloorBuilding.getCurrentBuilding().currentBuilding;
        var floorName = this.state.refAddFloorName;
        var floorURL = this.state.refAddFloorURL;

        if( buildingName!== 'Choose Building' &&
            floorName !== '' &&
            floorURL !== ''){
            this.setState({
                modal: !this.state.modal
            });
        }
        else{
            this.props.onValidate();
        }

    },

    onSelectBuilding : function() {

    },

    onDrop: function (acceptedFiles, rejectedFiles) {

        if(acceptedFiles.length > 0 && rejectedFiles.length == 0) {
            this.setState({
                refAddFloorURL : '/' + acceptedFiles[0].name,
                refNewFile: acceptedFiles
            });
        }
    },

    onFloorSubmitHandler(data){

        var url = data.url;
        var type = data.type;
        var messageContent = data.messageContent;

        var buildingName = this.state.refAddFloorBuilding.getCurrentBuilding().currentBuilding;
        var floorName = this.state.refAddFloorName;
        var floorURL = this.state.refAddFloorURL;
        var passedValidation = false;

        var buildingData = this.state.refAddFloorBuilding.getBuildingData();

        if( buildingName!== 'Choose Building' &&
            floorName !== '' &&
            floorURL !== '' ){

            if(this.state.refNewFile != null){
                this.onSubmitNewFloorplan();
            }

            var rawData = null;

            if (type !== "add") {

                var selected = this.state.selectedFloor;
                rawData = {
                    floorId: selected.floorId,
                    floorName: selected.floorName,
                    floorplanUrl: floorURL,
                    type: selected.type,
                    buildingId: selected.buildingId
                };
            }
            else {
                rawData = {
                    floorId: 0,
                    floorName: floorName,
                    floorplanUrl: floorURL,
                    type: 'General',
                    buildingId: buildingName
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
                this.props.onChange({response, messageContent});
                passedValidation = true;
            }).catch((response) => {
                var messageContent = "Error, Floor Update Not Submitted. Please Contact Your Administrator.";
                this.props.onChange({response, messageContent});
            });

        }
        else{
            this.props.onValidate();
        }

        if(passedValidation === true) {
            this.state.refAddFloorBuilding.updateBuilding("Choose Building");
            this.setState({
                refAddFloorName : "",
                refAddFloorURL: "",
                refNewFile: null
            });
        }
    },

    onSubmitNewFloorplan(){

        var data = new FormData();

        this.state.refNewFile.forEach((file)=> {
            data.append('file', file);
        });


        fetch('http://localhost:8080/files/upload',{
            method: 'POST',
            mode: 'cors',
            redirect: 'follow',
            body: data
        })
        .then((response) => {
            var messageContent = "New Image Added to Database.";
            this.props.onChange({response, messageContent});
        }).catch((response) => {
            var messageContent = "Error, New Building Not Submitted. Please Contact Your Administrator.";
            this.props.onChange({response, messageContent});
        });

    },

    render:function() {
        return (
            <div>
                {this.props.disableAdd === false ? <CardTitle>Add a Floor</CardTitle> : null}
                {this.props.disableAdd === true ? <CardTitle>Floor Details</CardTitle> : null}
                <hr/>
                <BuildingDropdown className="MyBuilding"
                                  url="http://localhost:8080/buildings"
                                  ref={(ref) => this.state.refAddFloorBuilding = ref}
                                  onChange={this.onSelectBuilding}
                                  disabled={true}
                />
                <InputGroup>
                    <InputGroupAddon>Floor Name</InputGroupAddon>
                    <Input className="CoordinateInput"
                           placeholder="Type floor name..."
                           value={this.state.refAddFloorName}
                           onChange={this.onAddFloorNameInputChange}
                           disabled={this.props.disableAdd}/>
                </InputGroup>
                <br />
                <InputGroup>
                    <InputGroupAddon>Image File</InputGroupAddon>
                    <Input className="CoordinateInput"
                           placeholder="Drop image / Click below ..."
                           value={this.state.refAddFloorURL}
                           onChange={this.onAddFloorURLInputChange}
                           disabled={true}/>
                </InputGroup>
                {this.props.disableAdd === false ?
                    <div>
                        <br/>
                        <Button id="AddRoomButton"
                            color="primary"
                            onClick={this.onAddFloorSubmit}>Submit</Button>
                    </div>
                : null }

                <hr/>

                {this.props.disableAdd === true ?
                    <div>
                        <Button id="AddRoomButton"
                            onClick={this.onUpdateFloorSubmit}>Update Floor Image</Button>

                        <Button id="AddRoomButton"
                                onClick={this.toggle}>Delete</Button>
                    </div>
                : null }
                <br/>
                <Dropzone onDrop={this.onDrop}>
                    {this.state.refNewFile ?
                        <div>{this.state.refNewFile.map((file) => <img id="FloorMapPreview" src={file.preview} key={0}/>)}</div>
                        :
                        <div>Drop image / Click to Select.</div>}
                </Dropzone>
                {this.state.refNewFile ? <div>Drop image / Click to Select.</div>: null}
                <Modal id="AddBuilding"
                       isOpen={this.state.modal}
                       toggle={this.toggle}
                >
                    <ModalHeader>Confirmation</ModalHeader>
                    <ModalBody>
                        <p>Are you sure?</p>

                        {this.props.disableAdd === true?
                            <Button id="EditLocationButton"
                                    onClick={this.onDeleteFloorSubmit}>
                                Submit Deletion
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