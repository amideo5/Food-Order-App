import React from "react";
import NavigationBar from "./NavigationBar";
import "../assets/welcome.css";
import NavBar from "./NavBar";
import Transaction from "./Transactions";

const Welcome = () => {
  if (localStorage.getItem("token") !== null) {
    return (
      <>
        <div className="welcomePage">
          <NavigationBar />
          <Transaction />
        </div>
      </>
    );
  }
  else {
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

export default Welcome;
