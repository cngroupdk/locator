// tutorial8.js
const {
    Button,
    Dropdown,
    DropdownItem,
    DropdownMenu,
    DropdownToggle,
    Container, Row, Col
} = Reactstrap;

var FloorMap = React.createClass({

    getInitialState: function() {
        return {
            mapPath : this.props.imageSource,
            data: []
        };
    },

    setMapPath : function(newPath){
        this.setState(
            {mapPath : newPath}
        );
    },

    render: function() {

        return (
            <div>
                <img src={this.state.mapPath} className="floorplanMap"/>
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

    updateFloor : function(newName){
        this.setState(
            {floorName : newName}
        );
    },

    onClickUpdateFloor : function(e){

        var flData = this.state.data[e.target.value];
        var newName = this.props.onChange(e.target.value, flData);
        this.updateFloor(newName);
    },

    getInitialState: function() {
        return {
            floorName :  this.props.selectedFl,
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
                                       update = {this.onClickUpdateFloor}/>
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
            chosenName: this.props.selectedEmp,
            data: []
        };
    },

    updateName : function(newName){
        this.setState({chosenName : newName});
    },

    onClickUpdateName : function(e){
        var emplData = this.state.data[e.target.value];
        var newName = this.props.onChange(e.target.value, emplData);
        this.updateName(newName);
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
                                          update = {this.onClickUpdateName}
                    />
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
        return {
            selectedEmployee: 'Select an Employee',
            selectedFloor: 'Select a Floor',
            presentImage : '',
            myImage : null,
            myEmployee : null,
            myFloor : null
        };
    },

    setImagePath : function(buildingId, floorNumber, refToImage){

        fetch('/floors/' + buildingId + '/' + floorNumber).then((response) => {return response.json()}).then(function(emplData){
            refToImage.setMapPath(emplData.floorplanUrl);
        }).catch((error) => {
            console.error(error);
        });
    },

    onSelectEmployee: function(index, emplData) {

        var res = emplData.location.split("@");
        this.setImagePath(res[2], res[1], this.state.myImage);

        this.state.myFloor.updateFloor(res[1] + ' @ ' + res[2]);
        var newName = emplData.lastName + ", " + emplData.firstName;
        this.setState({ selectedEmployee : newName });
        return newName;
    },

    onSelectFloor: function(index, flData) {
        var newFloor = flData.floorNumber + " @ " + flData.buildingId;
        this.setImagePath(flData.buildingId, flData.floorNumber, this.state.myImage);

        this.state.myEmployee.updateName('Select an Employee');
        this.setState({ selectedFloor : newFloor });
        return newFloor;
    },

    render: function() {
        return (
            <div className="selectorBox">
                <Container>
                <h1>Resource Locator</h1>
                <hr/>
                <Row>
                    <Col>
                        <EmployeesDropdown onChange={this.onSelectEmployee} selectedEmp={this.state.selectedEmployee} url="/employees" ref={(ref) => this.state.myEmployee = ref}/>
                        <FloorDropdown onChange={this.onSelectFloor} selectedFl ={this.state.selectedFloor} url="/floors" ref={(ref) => this.state.myFloor = ref}/>
                        <FloorMap imageSource={this.state.presentImage} ref={(ref) => this.state.myImage = ref}/>
                    </Col>
                </Row>
                <hr/>
                </Container>
            </div>
        );
    }
});

ReactDOM.render(
    <SelectorBox/>,
    document.getElementById('content')
);

