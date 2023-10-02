import React, { useEffect, useState } from "react";
import axios from "axios";
import NavigationBar from "./NavigationBar";
import "../assets/welcome.css";
import NavBar from "./NavBar";
import { Card, Button } from "react-bootstrap";

const OrderDetails = () => {
  const [orders, setOrders] = useState({
    items: {
      orderItems: {},
    },
  });
  const email = localStorage.getItem("email");

  useEffect(() => {
    getName();
  }, []);

  async function getName() {
    let name = await axios.get("http://localhost:8080/users/name/" + email);

    console.log(name);

    let data = await axios.get(
      "http://localhost:8080/orders/getOrder/" + name.data
    );

    console.log(data.data.items.orderItems);
    Object.keys(data.data.items.orderItems).map((key) => {
      console.log(data.data.items.orderItems[key]);
    });
    setOrders(data.data);
  }

  if (localStorage.getItem("token") != null) {
    return (
      <>
        <div className="welcomePage">
          <NavigationBar />

          <Card className={"border border-dark bg-light text-dark paddingTop"}>
            <Card.Header>
              <h1>Order Summary</h1>
            </Card.Header>
          </Card>

          <br />
          <br />
          <br />

          <Card style={{ width: "18rem" }}>
            <Card.Body>
              <Card.Title>{orders.orderId}</Card.Title>
              <br />
              <strong>Order Name :</strong> {orders.orderName}
              <br />
              <strong>Order Items :</strong>
              {Object.keys(orders.items.orderItems).map((key) => {
                return (
                  <div>
                    {key} : {orders.items.orderItems[key]}
                  </div>
                );
              })}
              <strong>Order Cost :</strong> {orders.cost}
            </Card.Body>
          </Card>
        </div>
      </>
    );
  }
  if (localStorage.getItem("token") == null) {
    return (
      <>
        <NavBar />
        <div className="form-container">
          You have to login to use this application
        </div>
      </>
    );
  }
};

export default OrderDetails;
