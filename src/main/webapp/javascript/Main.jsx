import React, { Component } from "react";
import ReactDOM from 'react-dom';
import '../css/Main.css';

class Main extends Component {

    render() {
        return (
            <div id="main">
                <h1>Minesweeper</h1>
            </div>
        );
    }
}

ReactDOM.render(
    <Main />,
    document.getElementById('react-mountpoint')
);