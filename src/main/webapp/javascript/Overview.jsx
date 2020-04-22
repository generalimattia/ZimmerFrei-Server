import React, { Component } from "react";
import '../css/materialize.css';

function createTable() {
    let table = []

    for (let j = 0; j < 6; j++ ) {
        let items = []

        for (let i = 1; i < 31; i++ ) {
           items.push(<td key={i.toString()}>{i}</td>)
        }

        table.push(<tr key={j.toString()}>{items}</tr>)
    }
    return table
}

class Overview extends Component {

    render() {
        if (!this.props.reservations) {
            return <div>No reservations yet...</div>
        }
        return (
            <table className="striped">
                <tbody>
                    {createTable()}
                </tbody>
            </table>
        );
    }
}

export default Overview;