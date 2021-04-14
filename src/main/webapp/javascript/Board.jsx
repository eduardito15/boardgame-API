import React, {Component} from "react";
import Square from "./Square";
import Stopwatch from "./Stopwatch";

class Board extends Component {

    render() {
        if (!this.props.board['squares'] || !this.props.gameId) {
            return <div>No Board yet...</div>
        }

        let squares = this.props.board['squares'];
        return (
            <div>
                <div className="config">
                    <label className="select-label">GameId: </label>
                    <label className="select-label">{this.props.gameId}</label>
                    <label className="select-label">Time: </label>
                    <Stopwatch isGameEnded={this.props.status.ended} pace={() => this.props.pace()} currentTimeMin={this.props.currentTimeMin} currentTimeSec={this.props.currentTimeSec}/>
                </div>

                <ul>
                    {squares.map(r => {
                            return (<div key={r[0].row} className="board-row">
                                {r.map(cell => {
                                        return <Square key={this.props.columns * cell.row + cell.column} value={cell}
                                                       onClick={() => this.props.onClick(cell.row, cell.column)} disabled={this.props.status.ended}
                                                       onContextMenu={() => this.props.onContextMenu(cell.row, cell.column)}/>
                                    }
                                )}
                            </div>)
                        }
                    )}
                </ul>
            </div>
        )
    }
}

export default Board;