import React, { Component } from "react";
import '../css/materialize.css';

class CustomerList extends Component {
    render() {
        if (!this.props.customers) {
            return <div>No customers yet...</div>
        }
        return (
            <ul id="customer-list" className="collection">
                {this.props.customers.map(customer => (
                    <li className="collection-item" key={customer.id.toString()}>
                        {customer.firstName} {customer.lastName}
                    </li>
                ))}
            </ul>
        );
    }
}

export default CustomerList;