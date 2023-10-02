import React from "react";
import "../assets/main.css";
import { useState } from "react";
import { useForm } from "react-hook-form";
import axios from "axios";
import NavBar from "./NavBar";

const Admin = (props) => {
  const { reset } = useForm();

  const [values, setValues] = useState({
    password: "",
    id: "",
  });

  const handleChange = (event) => {
    setValues({ ...values, [event.target.name]: event.target.value });
  };

  const handleSubmit = (event) => {
          if(values.id == "101" && values.password == "101"){
          localStorage.setItem("token", "saasssadasdasd");
          localStorage.setItem("id", values.id);
          props.history.push({ pathname: "/welcomea" });
          reset();
        }
        else{
            alert("Invalid Login")
            reset();
        }
        
     
      
    reset();
    event.preventDefault();
  };

  return (
    <div className="page">
      <NavBar />
      <div className="form-container">
        <div className="form-box">
          <div className="header-form">
            <h4 className="text-primary text-center">
              <i
                className="fa fa-user-circle"
                style={{ fontSize: "110px" }}
              ></i>
            </h4>
            <div className="image"></div>
          </div>
          <h2>Admin Sign In</h2>
          <div className="body-form">
            <form className="m-3" onSubmit={handleSubmit}>
              <div className="input-group mb-3">
                <div className="input-group-prepend">
                  <span className="input-group-text">
                    <i className="fa fa-envelope fa-2x"></i>
                  </span>
                </div>

                <input
                  type="text"
                  className="form-control"
                  placeholder="Admin ID"
                  onChange={handleChange}
                  name="id"
                  required
                  value={values.id}
                />
              </div>
              <div className="input-group mb-3">
                <div className="input-group-prepend">
                  <span className="input-group-text">
                    <i className="fa fa-lock fa-2x"></i>
                  </span>
                </div>
                <input
                  type="password"
                  className="form-control"
                  placeholder="Password"
                  onChange={handleChange}
                  name="password"
                  required
                  value={values.password}
                  
                />
              </div>
              <button type="submit" className="btn btn-secondary btn-block">
                Log In
              </button>

              <br />
              <br />

              
            </form>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Admin;
