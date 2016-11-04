/**
 * Created by cano on 4.11.2016.
 */
import React from 'react';
import App from './App';

var DirectEmployeeSearch=React.createClass({

    render: function() {
        var firstName = this.props.params.firstName;
        var lastName = this.props.params.lastName;

        return (
            <App firstName={firstName} lastName={lastName}/>
        );
    }
});

export default DirectEmployeeSearch;