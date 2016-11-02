/**
 * Created by cano on 20.10.2016.
 */
import React from 'react';
import { Dropdown, DropdownToggle, DropdownMenu } from 'reactstrap';
import EmployeeDropdownItem from './EmployeeDropdownItem';
import $ from 'jquery';

var EmployeeDropdown = React.createClass({

    loadCommentsFromServer: function() {
        this.serverRequest = $.get(this.props.url, function (result) {
            this.setState({
                data: result
            });
        }.bind(this));

    },

    getInitialState: function() {
        return {
            employeeIndex : -1,
            chosenName: 'Select an Employee',
            data: []
        };
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

    onClickUpdateName : function(e){
        var data = {
          emplData : this.state.data[e.target.value],
          index : e.target.value
        };

        this.props.onChange(data);
        this.updateName(data.emplData.firstName + " " + data.emplData.lastName);
        this.updateEmployeeIndex(e.target.value);
    },

    componentDidMount: function() {
        this.loadCommentsFromServer();
    },

    componentWillUnmount: function() {
        this.serverRequest.abort();
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
                <DropdownToggle className="EmployeeDropdownToggle" caret color="primary">
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