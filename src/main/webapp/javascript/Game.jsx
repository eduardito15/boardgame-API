import React, {Component} from "react";
import Board from "./Board";
import Select from 'react-select'
import Button from "reactstrap";


class Game extends Component {

    constructor(props) {
        super(props)
        this.state = {
            gameId: '',
            board: {},
            rows: 0,
            columns: 0,
            mines: 0,
            selectedCustom: false,
            user: '',
            isGameEnded: false,
            currentTimeMs: 0,
            currentTimeSec: 0,
            currentTimeMin: 0,
        }
        this.handleChange = this.handleChange.bind(this);

    }

    newGame() {
        fetch("/api/game/new", {
            method: "POST",
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({
                gameName: 'minesweeper',
                columns: this.state.columns,
                rows: this.state.rows,
                mines: this.state.mines,
                user: 'edu',
                board: {},
                status: null
            })
        })
            .then(res => res.json())
            .then(
                (response) => {

                    this.setState({
                        gameId: response.gameId,
                        rows: response.rows,
                        columns: response.columns,
                        board: response.board,
                        status: 'PLAYING',
                        isGameEnded: false,
                        currentTimeMin: 0,
                        currentTimeSec:0,
                        currentTimeMs: 0
                    });
                },
                (error) => {
                    alert(error);
                }
            )
    }

    handleClick(row, column) {
        this.sendAction(row, column, "TURN");
    }

    handleRightClick(row, column) {
        this.sendAction(row, column, "FLAG");
    }

    sendAction(row, column, action) {
        fetch("/api/game/action", {
            method: "POST",
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({
                gameId: this.state.gameId,
                row: row,
                column: column,
                action: action,
                timeMin: this.state.currentTimeMin,
                timeSec: this.state.currentTimeSec
            })
        })
            .then(res => res.json())
            .then(
                (response) => {
                    response.boardResult.forEach(s => {
                        this.state.board['squares'][s.row][s.column] = s;
                        if (response.status != 'PLAYING') {
                            this.setState( {
                                isGameEnded: true
                            })
                        }
                        this.setState({
                            status: response.status,
                            board: this.state.board
                        })
                    })
                },
                (error) => {
                    alert(error);
                }
            )
    }

    handleChange(selectedOption) {
        switch (selectedOption.value) {
            case "8*8": {
                this.setState({
                    columns: 8,
                    rows: 8,
                    mines: 10,
                    selectedCustom: false
                });
                break;
            }
            case "16*16": {
                this.setState({
                    columns: 16,
                    rows: 16,
                    mines: 40,
                    selectedCustom: false

                });
                break;
            }
            case "16*30": {
                this.setState({
                    columns: 30,
                    rows: 16,
                    mines: 99,
                    selectedCustom: false
                });
                break;
            }
            case "custom": {
                this.setState({
                    columns: 0,
                    rows: 0,
                    mines: 0,
                    selectedCustom: true
                });
                break;
            }
        }
    }

    onChangeInput(valueName) {
        if (valueName.target != null && valueName.target.value != null) {
            switch (valueName.target.name) {
                case 'rows': {
                    this.setState({
                        rows: valueName.target.value
                    });
                    break;
                }
                case 'columns': {
                    this.setState({
                        columns: valueName.target.value
                    });
                    break;
                }
                case 'mines': {
                    this.setState({
                        mines: valueName.target.value
                    });
                    break;
                }
                case 'user': {
                    this.setState({
                        user: valueName.target.value
                    });
                    break;
                }
            }
        }
    }

    pace() {
        if (!this.state.isGameEnded) {
            this.setState({currentTimeMs: this.state.currentTimeMs + 10});
            if (this.state.currentTimeMs >= 1000) {
                this.setState({currentTimeSec: this.state.currentTimeSec + 1});
                this.setState({currentTimeMs: 0});
            }
            if (this.state.currentTimeSec >= 60) {
                this.setState({currentTimeMin: this.state.currentTimeMin + 1});
                this.setState({currentTimeSec: 0});
            }
        }
    }

    render() {

        const options = [
            {value: '8*8', label: '8*8 10 mines'},
            {value: '16*16', label: '16*16 40 mines'},
            {value: '16*30', label: '16*30 99 mines'},
            {value: 'custom', label: 'Custom'}
        ]
        return (
            <div id="main">
                <h1>Minesweeper</h1>

                <div className="config">
                    <label className="select-label">Name: </label>
                    <input type="text" name="user" value={this.state.user} onChange={(e) => this.onChangeInput(e)}/>
                    <label className="select-label">Level: </label>
                    <Select className="select" options={options} onChange={this.handleChange}/>
                    <Button className="button" color="link" onClick={() => this.newGame()}>New Game</Button>
                </div>
                {this.state.selectedCustom ?
                    <div className="config">
                        <label className="select-label">Rows: </label>
                        <input type="number" name="rows" value={this.state.rows} onChange={(e) => this.onChangeInput(e)}/>
                        <label className="select-label">Columns: </label>
                        <input type="number" name="columns" value={this.state.columns} onChange={(e) => this.onChangeInput(e)}/>
                        <label className="select-label">Mines: </label>
                        <input type="number" name="mines" value={this.state.mines} onChange={(e) => this.onChangeInput(e)}/>

                    </div> : ''
                }

                <div className="game">
                    <div className="game-board">


                        <Board board={this.state.board} gameId={this.state.gameId} rows={this.state.rows}
                               columns={this.state.columns} onClick={(row, column) => this.handleClick(row, column)}
                               status={this.state.status} isGameEnded={this.state.isGameEnded} pace={() => this.pace()}
                               currentTimeMin={this.state.currentTimeMin} currentTimeSec={this.state.currentTimeSec}
                               onContextMenu={(row, column) => this.handleRightClick(row, column)}/>
                    </div>
                </div>
                {this.state.status === 'LOSE' ?
                    <div>
                        GAME OVER
                    </div> : ''
                }
            </div>
        );
    }
}

export default Game;
