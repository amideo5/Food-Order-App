import React from "react";
import Transaction from "../../Components/Transactions";
import { getByText, render, screen } from "@testing-library/react";
import axios from "axios";
import { server } from "../../mocks/server";
import { rest } from "msw";
import { fireEvent, waitFor } from "@testing-library/react";
import { mockComponent } from "react-dom/test-utils";

describe("Page component", () => {
  test("Snapshot testing", () => {
    const { container } = render(<Transaction />);
    expect(container).toMatchSnapshot();
  });

  it("renders the Page component when token exists", () => {
    localStorage.setItem("token", "testToken");
    render(<Transaction />);
    const signingText = screen.getAllByText("Welcome");
    expect(signingText[0]).toBeInTheDocument();
  });

  it("renders the Page component when token exists", () => {
    localStorage.setItem("token", "testToken");
    render(<Transaction />);
    const signingText = screen.getAllByText("Your Cart");
    expect(signingText[0]).toBeInTheDocument();
  });

  it("renders the Page component when token exists", () => {
    localStorage.setItem("token", "testToken");
    render(<Transaction />);
    const signingText = screen.getAllByText("Categories");
    expect(signingText[0]).toBeInTheDocument();
  });

  it("renders the Page component when token exists", () => {
    localStorage.setItem("token", "testToken");
    render(<Transaction />);
    const signingText = screen.getAllByText("Order");
    expect(signingText[0]).toBeInTheDocument();
  });

  it("renders the Page component when token exists", () => {
    localStorage.setItem("token", "testToken");
    render(<Transaction />);
    const signingText = screen.getAllByText("Change Order");
    expect(signingText[0]).toBeInTheDocument();
  });

  it("get categories", async () => {
    render(<Transaction />);
    server.resetHandlers(
      rest.get(
        "http://localhost:8080/foods/getFoodCategory",
        (req, res, ctx) => {
          return res(
            // Respond with a 200 status code
            ctx.status(200),
            ctx.body(res.data)
          );
        }
      )
    );
    const props = {
      history: { push: jest.fn() },
    };
    const mockAlert = jest.fn();
    global.alert = mockAlert;
    const { getByPlaceholderText, getByText } = render(<Transaction {...props} />);
    const but = screen.getAllByText("Change Order");
    fireEvent.click(but[0]);

    await waitFor(() => expect(mockAlert).toBeCalled());
  });

  it("update order", async () => {
    render(<Transaction />);
    server.resetHandlers(
      rest.put(
        "http://localhost:8080/orders/updateOrder/*",
        (req, res, ctx) => {
          return res(
            // Respond with a 200 status code
            ctx.status(200),
            ctx.body("Food Updated")
          );
        }
      )
    );
    const props = {
      history: { push: jest.fn() },
    };
    const mockAlert = jest.fn();
    global.alert = mockAlert;
    const { getByPlaceholderText, getByText } = render(<Transaction {...props} />);
    const but = screen.getAllByText("Change Order");
    fireEvent.click(but[0]);

    await waitFor(() => expect(mockAlert).toBeCalled());
  });

  it("create order", async () => {
    render(<Transaction />);
    server.resetHandlers(
      rest.put(
        "http://localhost:8080/orders/createOrder/*",
        (req, res, ctx) => {
          return res(
            // Respond with a 200 status code
            ctx.status(200),
            ctx.body("Food Created")
          );
        }
      )
    );
    const props = {
      history: { push: jest.fn() },
    };
    const mockAlert = jest.fn();
    global.alert = mockAlert;
    const { getByPlaceholderText, getByText } = render(<Transaction {...props} />);
    const but = screen.getAllByText("Order");
    fireEvent.click(but[0]);

    await waitFor(() => expect(mockAlert).toBeCalled());
  });
});

