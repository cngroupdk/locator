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

    getInitialState: function() {
        return {
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
                    <FloorDropdownItem floorData={floor} key={floor.floorId}/>
                );
            }
        );

        return (
                <Dropdown tether className="m-y-1" isOpen={this.state.dd4} toggle={() => { this.setState({ dd4: !this.state.dd4 })}}>
                    <DropdownToggle caret>
                        <Button color="primary">Select the Floor </Button>
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
            <DropdownItem className="floorList">
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
            data: []
        };
    },

    componentDidMount: function() {
        this.loadCommentsFromServer();
    },

    render: function() {
        var employeeNodes = this.state.data.map(
            function (employee) {
                return (
                    <EmployeeDropdownItem employeeData={employee} key={employee.employeeId}/>
                );
            }
        );

        return (
                <Dropdown tether className="m-y-1" isOpen={this.state.dd4} toggle={() => { this.setState({ dd4: !this.state.dd4 })}}>
                    <DropdownToggle caret>
                        <Button color="primary">Select the Staff Member</Button>
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
            <DropdownItem className="employeeList">
                {this.props.employeeData.lastName + " " + this.props.employeeData.firstName}
            </DropdownItem>
        );
    }
});

var SelectorBox = React.createClass({

    render: function() {
        return (
            <div className="selectorBox">
                <h1>Resource Locator</h1>
                <hr/>
                <div>
                    <EmployeesDropdown url="/employees"/>
                    <FloorDropdown url="/floors"/>
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

