import React, { useEffect, useState } from "react";
import "../assets/transactions.css";
import { Card, Table } from "react-bootstrap";
import { DropdownButton, Dropdown } from "react-bootstrap";
import { useForm } from "react-hook-form";
import NavBar from "./NavBar";
import { Button } from "react-bootstrap";
import axios from "axios";
import FoodItems from "./FoodItems";
import { useHistory } from "react-router-dom";

const Transaction = (props) => {
  const [food, setFood] = useState([]);
  const [cart, setCart] = useState([]);
  const [name, setName] = useState([]);
  const [category, setCategory] = useState([]);
  const email = localStorage.getItem("email");
  const { reset } = useForm();
  const history = useHistory();
  const [orderList, setOrderList] = useState([]);

  function addToCart(data) {
    var change = false;

    for (let i = 0; i < cart.length; i++) {
      if (cart[i].Id === data.Id) {
        cart[i][data.Id] = data[data.Id];
        change = true;
        break;
      }
    }

    if (change) {
      setCart([...cart]);
    } else {
      setCart([...cart, data]);
    }
  }

  function getFood() {
    axios.get("http://localhost:8080/foods/getFoods").then((response) => {
      setFood(response.data);
    });
  }

  function handleCategoryClick(e) {
    console.log(e.target.innerText);
    axios
      .get("http://localhost:8080/foods/getFoodCategory/" + e.target.innerText)
      .then((response) => {
        setFood(response.data);
        console.log(response.data);
      });
  }

  function getOrderList() {
    let obj = {};

    cart.map((item) => {
      obj[item.Id] = parseInt(item[item.Id]);
    });

    setOrderList(obj);
  }

  function getName() {
    axios.get("http://localhost:8080/users/name/" + email).then((response) => {
      setName(response.data);
    });
  }

  function getCategory() {
    axios
      .get("http://localhost:8080/foods/getFoodCategory")
      .then((response) => {
        setCategory(response.data);
        console.log(response.data);
      });
  }

  useEffect(() => {
    getFood();
    getName();
    getCategory();
    getOrderList();
  }, []);

  const handleOrder = () => {
    if (cart.length === 0) {
      alert("Please Insert into Cart");
    } else {
      let obj = {};

      cart.map((item) => {
        obj[item.Id] = parseInt(item[item.Id]);
      });

      const orderList = {
        orderList: obj,
      };
      axios
        .post("http://localhost:8080/orders/createOrder/" + email, orderList)
        .then((response) => {
          if (response.status === 200) {
            alert("Order Created");
            history.push({ pathname: "/conformation" });
            reset();
          }
        })
        .catch((error) => {
          // if (error.response.status === 400) {
          alert("Order Already Created");
          // } else {
          //   alert(error);
          // }
        });
      reset();
    }
  };

  const handleUpdate = () => {
    if (cart.length === 0) {
      alert("Please Insert into Cart");
    } else {
      let obj = {};

      cart.map((item) => {
        obj[item.Id] = parseInt(item[item.Id]);
      });

      const orderList = {
        orderList: obj,
      };

      axios
        .put("http://localhost:8080/orders/updateOrder/" + name, orderList)
        .then((response) => {
          if (response.status === 200) {
            alert("Order Updated");
            history.push({ pathname: "/conformation" });
            reset();
          }
        });
      reset();
    }
  };

  if (localStorage.getItem("token") != null) {
    return (
      <div className="Page">
        <div className="scroll">
          <Card className={"border border-dark bg-dark text-light paddingTop"}>
            <Card.Header>
              <h3>Welcome {name}</h3>
              <br />

              <DropdownButton
                variant="primary"
                id="dropdown-basic-button"
                title="Categories"
              >
                {category.map((c) => (
                  <>
                    {/* <div key={c.categoryId} id={c.categoryId}></div> */}
                    <Dropdown.Item
                      key={c.categoryId}
                      onClick={handleCategoryClick}
                      value={c.category}
                    >
                      {c.category}
                    </Dropdown.Item>
                  </>
                ))}
              </DropdownButton>
            </Card.Header>
            <Card.Body>
              <Table bordered hover striped variant="dark">
                <thead fixed="top">
                  <tr>
                    <th>Name</th>
                    <th>Price</th>
                    <th>Category</th>
                    <th>Quantity</th>
                    <th></th>
                  </tr>
                </thead>
                <tbody>
                  {food.length === 0 ? (
                    <tr align="center">
                      <td colSpan="6">No Food</td>
                    </tr>
                  ) : (
                    food.map((t, index) => (
                      <FoodItems addToCart={addToCart} key={index} data={t} />
                    ))
                  )}
                </tbody>
              </Table>
              <h4>Your Cart</h4>
              {cart.map((item) => {
                return (
                  <p>
                    {item.Id} : {item[item.Id]}
                  </p>
                );
              })}
              <Button onClick={handleOrder}>Order</Button>
              <Button onClick={handleUpdate}>Change Order</Button>
            </Card.Body>
          </Card>
        </div>
      </div>
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

export default Transaction;
