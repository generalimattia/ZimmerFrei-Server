import React, { Component } from "react";
import ReactDOM from 'react-dom';
import CustomerList from './CustomerList';
import RoomList from './RoomList';
import Overview from './Overview';
import '../css/materialize.css';

class Main extends Component {

    constructor(props) {
        super(props)
        this.state = {
            customers: [],
            rooms: [],
            reservations: []
        }
    }

    componentDidMount() {
        fetch("http://localhost:8080/customers")
            .then(res => res.json())
            .then(
                (response) => {
                    console.log(response)
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
                    console.log(response)
                    this.setState({
                        rooms: response._embedded.rooms
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
                    console.log(response)
                    this.setState({
                        reservations: response._embedded.reservations
                    });
                },
                (error) => {
                    alert(error);
                }
            )
    }

    render() {
        return (
            <div id="main">
                <div id="overview">
                    <h1>Overview</h1>
                    <Overview reservations={this.state.reservations}/>
                </div>

                <div id="customers" className="container">
                    <h1>Customers</h1>
                    <CustomerList customers={this.state.customers}/>
                </div>

                <div id="rooms" className="container">
                    <h1>Rooms</h1>
                    <RoomList rooms={this.state.rooms}/>
                </div>
            </div>
        );
    }
}

ReactDOM.render(
    <Main />,
    document.getElementById('react-mountpoint')
);