// tutorial8.js
const {
    Button,
    ButtonDropdown,
    ButtonGroup,
    ButtonToolbar,
    Dropdown,
    DropdownItem,
    DropdownMenu,
    DropdownToggle,
    TetherContent,
    Tooltip
} = Reactstrap;

var FloorMap = React.createClass({

    getInitialState: function() {
        return {
            showMap : false,
            data: []
        };
    },

    render: function() {

        return (
            <div>
                <img src={'/floorplan.jpg'} className="floorplanMap"/>
            </div>
        );
    }




});


var FloorDropdown = React.createClass({

    loadCommentsFromServer: function() {
        $.ajax({
            url: this.props.url,
            dataType: 'json',
            cache: false,
            success: function(data) {
                this.setState({data: data});
            }.bind(this),
            error: function(xhr, status, err) {
                console.error(this.props.url, status, err.toString());
            }.bind(this)
        });
    },

    updateFloor : function(e){

        var flData = this.state.data[e.target.value];
        var newFloor = flData.floorNumber + " @ " + flData.buildingId;
        this.setState(
            {floorName : newFloor}
        );
    },

    getInitialState: function() {
        return {
            floorName : 'Select the floor',
            data: []
        };
    },

    componentDidMount: function() {
        this.loadCommentsFromServer();
    },

    render: function() {
        var floorNodes = this.state.data.map(
            function (floor) {
                return (
                    <FloorDropdownItem floorData={floor}
                                       key={floor.floorId}
                                       update = {this.updateFloor}/>
                );
            }
        , this);

        return (
                <Dropdown tether className="m-y-1" isOpen={this.state.dd4} toggle={() => { this.setState({ dd4: !this.state.dd4 })}}>
                    <DropdownToggle caret>
                        <Button color="primary">{this.state.floorName}</Button>
                    </DropdownToggle>
                    <DropdownMenu>
                        {floorNodes}
                    </DropdownMenu>
                </Dropdown>
        );
    }
});

var FloorDropdownItem = React.createClass({

    render: function() {
        return (
            <DropdownItem className="floorList"
                          onClick={this.props.update}
                          value = {this.props.floorData.floorId}>
                {this.props.floorData.floorNumber + " @ " + this.props.floorData.buildingId}
            </DropdownItem>
        );
    }
});

var EmployeesDropdown = React.createClass({

    loadCommentsFromServer: function() {
        $.ajax({
            url: this.props.url,
            dataType: 'json',
            cache: false,
            success: function(data) {
                this.setState({data: data});
            }.bind(this),
            error: function(xhr, status, err) {
                console.error(this.props.url, status, err.toString());
            }.bind(this)
        });
    },

    getInitialState: function() {
        return {
            chosenName: 'Select the Staff Member',
            data: []
        };
    },

    updateName : function(e){

        var emplData = this.state.data[e.target.value];
        var newName = emplData.lastName + ", " + emplData.firstName;
        this.setState(
            {chosenName : newName}
        );
    },

    componentDidMount: function() {
        this.loadCommentsFromServer();
    },

    render: function() {
        var employeeNodes = this.state.data.map(
            function (employee) {
                return (
                    <EmployeeDropdownItem employeeData={employee}
                                          key={employee.employeeGuid}
                                          update = {this.updateName} />
                );
            }
        , this);

        return (
                <Dropdown tether className="m-y-1" isOpen={this.state.dd4} toggle={() => { this.setState({ dd4: !this.state.dd4 })}}>
                    <DropdownToggle caret>
                        <Button color="primary">{this.state.chosenName}</Button>
                    </DropdownToggle>
                    <DropdownMenu>
                        {employeeNodes}
                    </DropdownMenu>
                </Dropdown>
        );
    }
});

var EmployeeDropdownItem = React.createClass({

    render: function() {
        return (
            <DropdownItem className="employeeList"
                          onClick={this.props.update}
                          value = {this.props.employeeData.employeeGuid}>
                {this.props.employeeData.lastName + ", " + this.props.employeeData.firstName}
            </DropdownItem>
        );
    }
});

var SelectorBox = React.createClass({

    getInitialState: function () {
        return { mapVisible: true };
    },

    onClick: function() {
        this.setState({mapVisible: !this.state.mapVisible});
    },

    render: function() {
        return (
            <div className="selectorBox">
                <h1>Resource Locator</h1>
                <hr/>
                <div>
                    <EmployeesDropdown url="/employees"/>
                    <FloorDropdown url="/floors"/>
                    {
                        this.state.mapVisible ?  <FloorMap/>  : null
                    }
                </div>
                <hr/>
            </div>
        );
    }
});

ReactDOM.render(
    <SelectorBox/>,
    document.getElementById('content')
);

