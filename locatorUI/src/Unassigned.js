/**
 * Created by victorcano on 23/11/2016.
 */
import React from 'react';
import { Card, Row, Col, CardImg, Container } from 'reactstrap';
import { Link } from 'react-router';
import NotificationSystem from 'react-notification-system';
import UnassignedEmployee from './UnassignedEmployee';
import AssignLocation from './AssignLocation';
import $ from 'jquery';

var Unassigned=React.createClass({

    getInitialState: function() {
        return {

            notificationSystem : null,
            myAssignLocation : null,
            mySelectedEmployee : null,
            updateDone : false,
            data: []
        };
    },

    componentDidMount: function() {
        this.loadCommentsFromServer();
    },

    loadCommentsFromServer: function(url) {
        var newURL = '';

        if (url == null) {
            newURL = 'http://localhost:8080/employees/unassigned';
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
    onClickHandler(data) {

        var updateStatus = this.state.updateDone;

        var popoverOpen = false;
        if(this.state.myAssignLocation !== null)
        {
            this.state.myAssignLocation.setSelectedEmployee(data.emplData);
            popoverOpen = this.state.myAssignLocation.getPopoverState();
        }

        if( popoverOpen === true &&
            updateStatus === true)
        {
            updateStatus = false;
        }

        this.setState({

            updateDone: updateStatus,
            mySelectedEmployee: data.emplData
        });

        if(this.state.myAssignLocation !== null)
        {
            this.state.myAssignLocation.toggle();
        }
    },

    onSubmitNewLocation: function (data){

        if(data.type !== 'ValidationError'){
            this.setState(
                {updateDone : true}
            );
        }

        this.addNotification(data.message);
        this.state.myAssignLocation.toggle();

    },

    addNotification: function(notificationMessage) {

        this.state.notificationSystem.addNotification({
            message: notificationMessage.content,
            level: notificationMessage.type
        });
    },

    render: function() {

        var counter=0;

        var unassignedStaff = this.state.data.map(
            function (emplData) {
                return (
                    <UnassignedEmployee className="UnassignedEmployee"
                                        emplData={emplData}
                                        key={counter}
                                        counter={counter++}
                                        onClickHandler={this.onClickHandler}/>
                );
            }
            , this);


        return (
            <div>
                <Container className="Container">
                    <NotificationSystem ref={(ref) => this.state.notificationSystem = ref} />
                    <hr/>
                    <h1 className="display-3">Employee Assignment Tool</h1>
                    <p className="lead">CN Group - Staff Locator</p>
                    <p><Link to="/">To Staff Locator</Link></p>
                    <hr/>
                    {unassignedStaff}
                    <AssignLocation id="Modal"
                                    ref={(ref) => this.state.myAssignLocation = ref}
                                    onToggleClick={this.toggle}
                                    onSubmit={this.onSubmitNewLocation}
                                    selectedEmployee={this.state.mySelectedEmployee}/>
                </Container>
            </div>
        );
    }
});

export default Unassigned;