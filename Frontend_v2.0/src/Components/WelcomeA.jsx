import React, { useEffect, useState } from "react";
import NavigationBar from "./NavigationBar";
import '../assets/welcome.css';
import {Card, Button } from "react-bootstrap";
import NavBar from "./NavBar";
import axios from "axios";
import NavBarA from "./NavBarA";

const WelcomeA = (props) => { 
  const handleAddFood = () => {
    props.history.push({pathname: '/add'})
  }


  const handleUpdateFood = () => {
    props.history.push({pathname: '/update'})
  }

  if (localStorage.getItem('token')!=null)
  {    
    return (
      <>
      
      <div className="welcomePage">
      <NavBarA/>
      <div className="col m-5">
        ADMIN PAGE
        </div>
      
      <div className="row m-5">
      <div className="col m-5">
      <Card style={{ width: '18rem' }}>
        
        <Card.Body>
          <Card.Title>
            Add Food
          </Card.Title><br />
          <Button variant="primary" onClick={handleAddFood}>Add</Button>
        </Card.Body>
      </Card>
      </div>
      <div className="col m-5">
      <Card style={{ width: '18rem' }}>
        
        <Card.Body>
          <Card.Title>
            Update Food
          </Card.Title>
          <Button variant="primary"  onClick={handleUpdateFood}>Update</Button>
        </Card.Body>
      </Card>
      </div>
      </div>
      </div>
      </>
    );
  }
  if (localStorage.getItem('token') == null)
  {
    return (
      <>
        <NavBar/>
          <div className="form-container">
              You have to login to use this application
          </div>
      </>
    );  
  }
}

export default WelcomeA;