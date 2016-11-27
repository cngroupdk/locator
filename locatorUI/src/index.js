import React from 'react';
import ReactDOM from 'react-dom';
import { Router, Route, hashHistory } from 'react-router'
import App from './App';
import DirectEmployeeSearch from './DirectEmployeeSearch';
import Unassigned from './Unassigned';
import './App.css';
import './index.css';

ReactDOM.render((
        <Router history={hashHistory}>
            <Route path="/" component={App}/>
            <Route path="/:employeeId" component={DirectEmployeeSearch}/>
            <Route path="/tools/unassigned" component={Unassigned}/>
        </Router>
    ), document.getElementById('root'));