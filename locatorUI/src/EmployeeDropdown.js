/**
 * Created by cano on 20.10.2016.
 */
import React from 'react';
import { Dropdown, DropdownToggle, DropdownMenu } from 'reactstrap';
import EmployeeDropdownItem from './EmployeeDropdownItem';
import $ from 'jquery';

var EmployeeDropdown = React.createClass({


    getInitialState: function() {
        return {
            employeeIndex : -1,
            chosenName: 'Select an Employee',
            data: [],
            disabled : false
        };
    },

    componentDidMount: function() {
        this.loadCommentsFromServer();
    },

    componentWillUnmount: function() {
        this.serverRequest.abort();
    },

    loadCommentsFromServer: function(url) {
        var newURL = '';

        if (url == null) {
            newURL = this.props.url;
        }
        else{
            newURL = url;
        }

        this.serverRequest = $.get(newURL, function (result) {
            this.setState({
                data: result
            });
        }.bind(this));

    },

    disableDropdown : function(newBool){
        this.setState(
            {disabled : newBool}
        );
    },

    updateEmployeeIndex : function(newIndex){
        this.setState(
            {employeeIndex : newIndex}
        );
    },

    getEmployeeData: function(){
        var selectedEmployee = this.state.data[this.state.employeeIndex];
        return{
            selectedEmployee
        };
    },

    getCurrentName : function(){
        var currentName = this.state.chosenName;
        return{
            currentName
        };
    },

    updateName : function(newName){
        this.setState({chosenName : newName});
    },

    findEmployee : function(data){
        var length = this.state.data.length;
        var index = -1;
        var emplData = null;

        for(var i=0; i < length; i++){
            emplData =  this.state.data[i];
            if(emplData.firstName === data.firstName
            && emplData.lastName === data.lastName){
                index = i;
                break;
            }
        }

        var foundData = {
            emplData,
            index
        }

        return(
            foundData
        );
    },

    onClickUpdateName : function(event){
        var data = {
            event,
            emplData : this.state.data[event.target.value],
            index : event.target.value
        };

        this.props.onChange(data);
        this.updateName(data.emplData.firstName + " " + data.emplData.lastName);
        this.updateEmployeeIndex(event.target.value);
    },

    render: function() {
        var employeeNodes = this.state.data.map(
            function (employee) {
                return (
                    <EmployeeDropdownItem className="EmployeeDropdownItem"
                                          employeeData={employee}
                                          key={employee.employeeGuid}
                                          update={this.onClickUpdateName}
                    />
                );
            }
            , this);

        return (
            <Dropdown tether className="m-y-1" isOpen={this.state.dd4} toggle={() => { this.setState({ dd4: !this.state.dd4 })}}>
                <DropdownToggle disabled={this.props.disabled} className="EmployeeDropdownToggle" caret color="primary">
                    {this.state.chosenName}
                </DropdownToggle>
                <DropdownMenu className="EmployeeDropdownMenu">
                    {employeeNodes}
                </DropdownMenu>
            </Dropdown>
        );
    }
});

export default EmployeeDropdown;