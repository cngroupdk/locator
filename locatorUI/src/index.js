import React from 'react';
import ReactDOM from 'react-dom';
import { Router, Route, hashHistory } from 'react-router'
import App from './App';
import DirectEmployeeSearch from './DirectEmployeeSearch';
import './App.css';
import './index.css';

/*
ReactDOM.render(
  <App />,
  document.getElementById('root')
);
*/


ReactDOM.render((
        <Router history={hashHistory}>
            <Route path="/" component={App}/>
            <Route path="/:firstName/:lastName" component={DirectEmployeeSearch}/>
        </Router>
    ), document.getElementById('root'));