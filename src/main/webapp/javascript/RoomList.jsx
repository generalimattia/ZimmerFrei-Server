import React, { Component } from "react";
import '../css/materialize.css';

class RoomList extends Component {
    render() {
        if (!this.props.rooms) {
            return <div>No rooms yet...</div>
        }
        return (
            <ul id="room-list" className="collection">
                {this.props.rooms.map(room => (
                    <li className="collection-item" key={room.id.toString()}>
                        {room.name} {room.maxPersons}
                    </li>
                ))}
            </ul>
        );
    }
}

export default RoomList;