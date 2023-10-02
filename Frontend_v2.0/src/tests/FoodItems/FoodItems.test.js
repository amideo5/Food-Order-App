import userEvent from "@testing-library/user-event";
import { act } from "react-dom/test-utils";
import FoodItems from "../../Components/FoodItems";
import React from "react";
import { render, screen, fireEvent } from "@testing-library/react";

describe("Testing Food Items Component", () => {
  const data = {
    id: "1",
    foodName: "Burger",
    foodPrice: 10,
    foodCategory: "Fast Food",
  };

  const addToCart = jest.fn();

  test("Snapshot testing",()=>{
    const {container} = render(<FoodItems data={data} addToCart={addToCart}/>);
    expect(container).toMatchSnapshot();
    });

  it("should render the component with the given props", () => {
    render(<FoodItems data={data} addToCart={addToCart} />);
    expect(screen.getByText("Burger")).toBeInTheDocument();
    expect(screen.getByText("10")).toBeInTheDocument();
    expect(screen.getByText("Fast Food")).toBeInTheDocument();
  });

  it("should update the state on quantity input change", () => {
    render(<FoodItems data={data} addToCart={addToCart} />);
    const quantityInput = screen.getByPlaceholderText("Quantity");
    fireEvent.change(quantityInput, { target: { value: 2 } });
    expect(quantityInput.value).toBe("2");
  });

  it('should call addToCart function on clicking the "Add to Cart" button', () => {
    render(<FoodItems data={data} addToCart={addToCart} />);
    const addToCartButton = screen.getByText("Add to Cart");
    fireEvent.click(addToCartButton);
    expect(addToCart).toHaveBeenCalled();
  });
});
