import React from "react";
import "../assets/main.css";
import NavBar from "./NavBar";
import NavigationBar from "./NavigationBar";
import { Button } from "react-bootstrap";
import { useHistory } from "react-router-dom";

const Conformation = (props) => {
  const history = useHistory();
  const handleUpdate = () => {
    history.push({ pathname: "/orderDetails" });
  };

  if (localStorage.getItem("token") != null) {
    return (
      <div className="page">
        <NavigationBar />
        <div className="form-container">
          <div className="form-box">
            <div className="header-form">
              <h4 className="text-primary text-center">
                <i className="fa fa-check" style={{ fontSize: "110px" }}></i>
              </h4>
              <div className="image"></div>
            </div>

            <div className="body-form">
              <form className="m-3">
                <h4>Order Successful</h4>
                <br />
                <br />
                <br />
                <Button onClick={handleUpdate}>View Order Details</Button>
              </form>
            </div>
          </div>
        </div>
      </div>
    );
  }
  if (localStorage.getItem("token") == null) {
    return (
      <div className="page">
        <NavBar />
        <div className="form-container">
          You have to login to use this application
        </div>
      </div>
    );
  }
};

export default Conformation;
