import React, { Component } from "react";
import ReactDOM from 'react-dom';
import '../css/Main.css';
import Game from "./Game";
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';


class Main extends Component {

    render() {
        return (
            <Router>
                <Switch>
                    <Route path='/' exact={true} component={Game}/>
                </Switch>
            </Router>
        );
    }
}

ReactDOM.render(
    <Main />,
    document.getElementById('react-mountpoint'),
    document.addEventListener( "contextmenu", function(e) {e.preventDefault();})
);