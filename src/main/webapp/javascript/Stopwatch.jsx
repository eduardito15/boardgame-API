import React from 'react';

class Stopwatch extends React.Component {

    componentDidMount() {
        this.start();
    }

    formatTime(val, ...rest) {
        let value = val.toString();
        if (value.length < 2) {
            value = '0' + value;
        }
        if (rest[0] === 'ms' && value.length < 3) {
            value = '0' + value;
        }
        return value;
    }

    start() {
        this.watch = setInterval(() => this.props.pace(), 10);
    }

    render() {
        return (
            <div className="stopwatch">
                <div className={'stopwatch__display'}>
        <span>
          {this.formatTime(this.props.currentTimeMin, 'min')}:
            {this.formatTime(this.props.currentTimeSec, 'sec')}
        </span>
                </div>

            </div>
        );
    }
}

export default Stopwatch;
