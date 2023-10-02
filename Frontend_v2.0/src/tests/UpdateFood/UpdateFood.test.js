import React from "react";
import { getAllByText, render, screen } from "@testing-library/react";
import Update from "../../Components/UpdateFood";
import userEvent from "@testing-library/user-event";
import { server } from "../../mocks/server";
import { rest } from "msw";
import axios from "axios";
import { createMemoryHistory } from "history";
import { Router } from "react-router-dom";
import { fireEvent, waitFor } from "@testing-library/react";

describe("Update component", () => {
  test("Snapshot testing", () => {
    const { container } = render(<Update />);

    expect(container).toMatchSnapshot();
  });

  it("updates the food name input", () => {
    render(<Update />);
    const foodNameInput = screen.getByPlaceholderText("Name");
    expect(foodNameInput).toBeInTheDocument();
    fireEvent.change(foodNameInput, "New Food Name");
    expect(foodNameInput).toHaveValue("");
  });

  it("updates the food price input", () => {
    render(<Update />);
    const foodPriceInput = screen.getByPlaceholderText("Price");
    expect(foodPriceInput).toBeInTheDocument();
    fireEvent.change(foodPriceInput, "15");
    expect(foodPriceInput).toHaveValue("");
  });

  it("updates the food category input", () => {
    render(<Update />);
    const foodCategoryInput = screen.getByPlaceholderText("Category");
    expect(foodCategoryInput).toBeInTheDocument();
    fireEvent.change(foodCategoryInput, "New Food Category");
    expect(foodCategoryInput).toHaveValue("");
  });

  test("should update food and redirect to /add page", async () => {

    server.resetHandlers(
        rest.put("http://localhost:8080/foods/updateFood/*", (req, res, ctx) => {
          return res(
            // Respond with a 200 status code
            ctx.status(200),
            ctx.body("Food Updated")
          );
        })
      );

        const props = {
            history: { push: jest.fn() },
          };

          const mockAlert = jest.fn();
    global.alert = mockAlert;

        const { getByPlaceholderText, getByText } = render(<Update/>);

        fireEvent.change(getByPlaceholderText("Name"), {
          target: { value: "Name" },
        });
        fireEvent.change(getByPlaceholderText("Price"), {
            target: { value: "10" },
          });
        fireEvent.change(getByPlaceholderText("Category"), {
          target: { value: "Starter" },
        });

        const but = screen.getByTestId("updateFood");
        fireEvent.click(but);

        await waitFor(() =>
        expect(mockAlert).toBeCalled());
      });
});
