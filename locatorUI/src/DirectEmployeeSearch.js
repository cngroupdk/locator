/**
 * Created by cano on 4.11.2016.
 */
import React from 'react';
import App from './App';

var DirectEmployeeSearch=React.createClass({

    render: function() {
        var id = this.props.params.employeeId;

        return (
            <App employeeId={id}/>
        );
    }
});

export default DirectEmployeeSearch;