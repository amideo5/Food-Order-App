import React from "react";
import "../assets/signup.css";
import "../assets/main.css";
import { useState } from "react";
import { useForm } from "react-hook-form";
import axios from "axios";
import NavBar from "./NavBar";

const Signup = (props) => {
  const { reset } = useForm();

  const [values, setValues] = useState({
    name: "",
    password: "",
    email: "",
    confirmPassword: "",
  });

  const handleChange = (event) => {
    setValues({ ...values, [event.target.name]: event.target.value });
  };

  const handleSubmit = (event) => {
    if (values.password == values.confirmPassword) {
      var register = {
        name: values.name,
        password: values.password,
        email: values.email,
      };
      axios
        .post("http://localhost:8080/users/createUser", register)
        .then((response) => {
          // if (response.status == 200) {
            alert("User created");
            props.history.push({ pathname: "/" });
            reset();
          // }
        })
        .catch((error) => {
          console.log(error);
          // alert(error)
          if (error.response.status == 400) {
            alert("User Already Exists");
          } else {
            alert(error.response.data.message);
          }
        });
    } else {
      alert("all bad");
    }
    reset();
    event.preventDefault();
  };

  return (
    <div className="page">
      <NavBar />
      <div className="form-container">
        <div className="form-box-signup">
          <div className="header-form">
            <h4 className="text-primary text-center">
              <i
                className="fa fa-user-circle"
                style={{ fontSize: "110px" }}
              ></i>
            </h4>
            <div className="image"></div>
          </div>
          <h2>Sign Up</h2>
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
                  pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$"
                  required
                  title="Enter a valid Email"
                  value={values.email}
                />
              </div>
              <div className="input-group mb-3">
                <div className="input-group-prepend">
                  <span className="input-group-text">
                    <i className="fa fa-user fa-2x"></i>
                  </span>
                </div>
                <input
                  type="text"
                  className="form-control"
                  placeholder="Name"
                  name="name"
                  required
                  value={values.name}
                  onChange={handleChange}
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
              <div className="input-group mb-3">
                <div className="input-group-prepend">
                  <span className="input-group-text">
                    <i className="fa fa-lock fa-2x"></i>
                  </span>
                </div>
                <input
                  type="password"
                  className="form-control"
                  placeholder="Confirm Password"
                  onChange={handleChange}
                  name="confirmPassword"
                  required
                  value={values.confirmPassword}
                  pattern=".{8,}"
                  title="Eight or more characters"
                />
              </div>
              <button type="submit" className="btn btn-secondary btn-block">
                Register
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

export default Signup;
