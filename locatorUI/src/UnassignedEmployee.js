/**
 * Created by victorcano on 23/11/2016.
 */
import React from 'react';
import { Card, Row, Col, CardImg } from 'reactstrap';
import ImageMap from './ImageMap';
import $ from 'jquery';

var UnassignedEmployee=React.createClass({
    getInitialState: function() {
        return {
            emplData: this.props.emplData,
            value: this.props.counter,
            myEditPen : null,
            myImage : null
        };
    },


    componentDidMount: function() {
        if (this.state.myEditPen) {
            this.state.myEditPen.setMapPath('http://localhost:8080/files/marker-pen.png');
        }

        this.serverRequest = $.get('http://localhost:8080/employees/photo/folder', function (response) {

            var employeePhoto = response.url + this.state.emplData.employeeId + '.jpg';

            this.state.myImage.setMapPath(employeePhoto);

        }.bind(this));

        /*if(this.state.myImage){
            fetch('http://localhost:8080/employees/photo/folder')
            .then((response) => {
                return response.json()
            })
            .then(function (response) {

                var employeePhoto = response.url + this.state.emplData.employeeId + '.jpg';

                this.state.myImage.setMapPath(employeePhoto);

            }).catch((error) => {
                console.error(error);
            });
        }*/
    },

    toggle : function(){
        this.props.onClickHandler({value: this.state.value, emplData : this.state.emplData});
    },

    render: function() {

        var title = this.props.emplData.firstName + ' ' + this.props.emplData.lastName;

        return (
            <div>
                <p className="lead">{title}</p>
                <Row>
                    <Col xs="3">
                        <Card block>
                        <ImageMap id="Unassigned"
                                  ref={(ref) => this.state.myImage = ref}
                        />
                        <ImageMap id="UnassignedPen"
                                  ref={(ref) => this.state.myEditPen = ref}
                                  clickHandler={this.toggle}
                                  imgSource='http://localhost:8080/files/marker-pen.png'
                        />
                        </Card>
                    </Col>
                </Row>
            </div>
        );
    }
});

export default UnassignedEmployee;