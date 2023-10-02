import React from "react";
import "../assets/main.css";
import { useState } from "react";
import { useForm } from "react-hook-form";
import axios from "axios";
import NavBar from "./NavBar";

const Signin = (props) => {
  const { reset } = useForm();

  const [values, setValues] = useState({
    password: "",
    email: "",
  });

  const handleChange = (event) => {
    setValues({ ...values, [event.target.name]: event.target.value });
  };

  const handleSubmit = (event) => {
    axios
      .post("http://localhost:8080/users/signin", values)
      .then((response) => {
        // if (response.status === 200) {
          localStorage.setItem("token", "saasssadasdasd");
          localStorage.setItem("email", values.email);
          props.history.push({ pathname: "/welcome" });
          reset();
        // } 
        
      })
      .catch((error) => {
        console.log(error);
        //alert(error)
        if (error.response.status === 400) {
          alert("Invalid Credentials");
        } else {
          alert(error.response.data);
        }
      });
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
          <h2>Sign In</h2>
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
                  placeholder="Email"
                  onChange={handleChange}
                  name="email"
                  required
                  pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$"
                  title="Enter a valid Email"
                  value={values.email}
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
                  pattern=".{8,}"
                  title="Eight or more characters"
                />
              </div>
              <button type="submit" className="btn btn-secondary btn-block">
                Log In
              </button>

              <br />
              <br />

              <a href="/user">Forgot Password</a>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Signin;
