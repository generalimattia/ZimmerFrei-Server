import React, { Component } from "react";
import ReactDOM from 'react-dom';
import CustomerList from './CustomerList';
import RoomList from './RoomList';
import Overview from './Overview';
import DataTable from './DataTable';
import moment from 'moment'
import '../css/materialize.css';
import '../css/DataTable.css';

class Main extends Component {

    constructor(props) {
        super(props)
        this.state = {
            customers: [],
            rooms: [],
            reservations: [],
            headings: [],
            rows: []
        }
    }

    componentDidMount() {
        fetch("http://localhost:8080/customers")
            .then(res => res.json())
            .then(
                (response) => {
                    this.setState({
                        customers: response._embedded.customers
                    });
                },
                (error) => {
                    alert(error);
                }
            )

        fetch("http://localhost:8080/rooms")
            .then(res => res.json())
            .then(
                (response) => {
                    const availableRooms = response._embedded.rooms

                    let daysInMonth = getDaysInMonth()

                    this.setState({
                        rooms: availableRooms
                    });

                    availableRooms.forEach(room => {
                        
                        fetch(`http://localhost:8080/reservations?roomId=${room.id}&from=2020-04-01&to=2020-04-30`)
                            .then(res => res.json())
                            .then(
                                (response) => {
                                    const reservations = response._embedded.reservations

                                    const days = [room.name.toString()]
                                    for (let index = 1; index <= daysInMonth; index++) {
                                        const reservation = reservations.find(function(reservation) {
                                            const startDay = moment(reservation.startDate, "YYYY-MM-DD").format("D")
                                            return index >= parseInt(startDay, 10)
                                        })

                                        console.log(reservation)

                                        if(reservation == undefined) {
                                            days.push("#FFFFFF")
                                        } else {
                                            days.push(reservation.color)
                                        }
                                    }

                                    const daysByRoom = this.state.rows
                                    daysByRoom.push(days)

                                    this.setState({
                                        rows : daysByRoom
                                    });
                                },
                                (error) => {
                                    alert(error);
                                }
                            )
                        });
                },
                (error) => {
                    alert(error);
                }
            )

        fetch("http://localhost:8080/reservations?roomId=1&from=2020-04-01&to=2020-04-30")
            .then(res => res.json())
            .then(
                (response) => {
                    this.setState({
                        reservations: response._embedded.reservations
                    });
                },
                (error) => {
                    alert(error);
                }
            )
        
            let daysInMonth = getDaysInMonth()
            let days = ["Camere"]
            for (let index = 0; index < daysInMonth; index++) {
                days.push((index+1).toString())
            }
    
            this.setState({
                headings: days
            })
    }

    render() {
        if(this.state.headings.length > 0 && this.state.rows.length > 0)  {
            return (
                <div id="main" className="container">
                    <h1>Piano Prenotazioni</h1>
                    <DataTable headings={this.state.headings} rows={this.state.rows} />
                    <div id="customers">
                        <h1>Customers</h1>
                        <CustomerList customers={this.state.customers}/>
                    </div>
    
                    <div id="rooms">
                        <h1>Rooms</h1>
                        <RoomList rooms={this.state.rooms}/>
                    </div>
                </div>
            );
        } else {
            return (
                <div id="main" className="container">
                    <div id="customers">
                        <h1>Customers</h1>
                        <CustomerList customers={this.state.customers}/>
                    </div>
    
                    <div id="rooms">
                        <h1>Rooms</h1>
                        <RoomList rooms={this.state.rooms}/>
                    </div>
                </div>
            );
        }
    }
}


function getDaysInMonth() {
    let today = new Date()
    let daysInMonth = new Date(today.getFullYear(), today.getMonth(), 0).getDate()
    return daysInMonth
}

ReactDOM.render(
    <Main />,
    document.getElementById('react-mountpoint')
);