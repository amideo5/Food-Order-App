import React from "react";
import "../assets/signup.css";
import "../assets/main.css";
import { useState } from "react";
import { useForm } from "react-hook-form";
import axios from "axios";
import NavBar from "./NavBar";
import NavBarA from "./NavBarA";

const Update = (props) => {
  const { reset } = useForm();

  const [values, setValues] = useState({
    foodName: "",
    foodPrice: "",
    foodCategory: "",
  });

  const handleChange = (event) => {
    setValues({ ...values, [event.target.name]: event.target.value });
  };

  const handleUpdate = (event) => {
    var register = {
      foodName: values.foodName,
      foodPrice: values.foodPrice,
      foodCategory: values.foodCategory,
    };
    axios
      .put(
        "http://localhost:8080/foods/updateFood/" + values.foodName,
        register
      )
      .then((response) => {
        console.log(response);
        if (response.status == 200) {
          
          alert("Food Updated");

          //props.history.push({ pathname: "/add" });
          reset();
        }
      });

    reset();
    event.preventDefault();
  };

  return (
    <div className="page">
      <NavBarA />
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
          <h2>Update Food</h2>
          <br />
          <div className="body-form">
            <form className="m-3" onSubmit={handleUpdate}>
              <div className="input-group mb-3">
                <div className="input-group-prepend">
                  
                </div>

                <input
                  type="text"
                  className="form-control"
                  placeholder="Name"
                  onChange={handleChange}
                  name="foodName"
                  required
                  value={values.foodName}
                />
              </div>
              <div className="input-group mb-3">
                <div className="input-group-prepend">
                  
                </div>
                <input
                  type="text"
                  className="form-control"
                  placeholder="Price"
                  name="foodPrice"
                  required
                  value={values.foodPrice}
                  onChange={handleChange}
                />
              </div>
              <div className="input-group mb-3">
                <div className="input-group-prepend">
                  
                </div>
                <input
                  type="text"
                  className="form-control"
                  placeholder="Category"
                  onChange={handleChange}
                  name="foodCategory"
                  required
                  value={values.foodCategory}
                />
              </div>
              <br />
              <button
                type="submit"
                className="btn btn-secondary btn-block"
                onSubmit={handleUpdate}
                data-testid="updateFood"
              >
                Update Food
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

export default Update;
