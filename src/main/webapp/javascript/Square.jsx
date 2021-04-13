import React, {Component} from "react";

class Square extends Component {
    render() {
        if (!this.props.value) {
            return null;
        }

        if (this.props.value.flag == 1) {
            return (
                <button className="square">
                    F
                </button>
            )
        }

        if (this.props.value.mine == 1) {
            return (
                <button className="square">
                    M
                </button>
            )
        }

        if (this.props.value.turned) {
            return (
                <button className="square">
                    {this.props.value.adjacentMines}
                </button>
            )
        }

        return (
            <button className="square" onClick={this.props.onClick} onContextMenu={this.props.onContextMenu} disabled={this.props.disabled}>
            </button>
        )
    }
}

export default Square;