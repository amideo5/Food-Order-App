import { useState } from "react";
import { Button } from "react-bootstrap";
import "../assets/transactions.css";

export default function FoodItems({ data, addToCart }, prop) {
  const [order, setOrder] = useState();
  const obj = {};

  function handleChange(e) {
    setOrder(e.target.value);
  }

  function submitOrder() {
    obj["Id"] = data.foodName;
    obj[data.foodName] = order;
    addToCart(obj);
  }

  return (
    <>
      <tr id={data.id}>
        <td>{data.foodName}</td>
        <td>{data.foodPrice}</td>
        <td>{data.foodCategory}</td>
        <td>
          <input
            type="number"
            name="quantity"
            required
            placeholder="Quantity"
            value={order}
            onChange={handleChange}
            
          />
        </td>
        <td>
          <Button onClick={submitOrder}>Add to Cart</Button>
        </td>
      </tr>
    </>
  );
}
