import { render, screen } from "@testing-library/react";
import Signin from "../../Components/signin";
import userEvent from "@testing-library/user-event";
import { act } from "react-dom/test-utils";
import React from "react";
import { fireEvent, waitFor } from "@testing-library/react";
import { server } from "../../mocks/server";
import { rest } from "msw";

describe("Testing SignIn Component", () => {
  test("Snapshot testing", () => {
    const { container } = render(<Signin />);

    expect(container).toMatchSnapshot();
  });

  test("Checking component is rendering or not", () => {
    render(<Signin />);
    const signingText = screen.getAllByText("Sign In");
    const navText = screen.getAllByText("NEX Food Ordering");
    expect(signingText[0]).toBeInTheDocument();
    expect(navText[0]).toBeInTheDocument();
  });


  test("should submit form with valid credentials and redirect to welcome page", async () => {
    const props = {
      history: { push: jest.fn() },
    };
    const { getByPlaceholderText, getByText } = render(<Signin {...props} />);

    fireEvent.change(getByPlaceholderText("Email"), {
      target: { value: "test@test.com" },
    });
    fireEvent.change(getByPlaceholderText("Password"), {
      target: { value: "password" },
    });
    fireEvent.click(getByText("Log In"));

    await waitFor(() =>
      expect(props.history.push).toHaveBeenCalledWith({ pathname: "/welcome" })
    );
  });

  test("should display error message when form submitted with invalid credentials", async () => {
    server.resetHandlers(
      rest.post("http://localhost:8080/users/signin", (req, res, ctx) => {
        return res(
          // Respond with a 200 status code
          ctx.status(400),
          ctx.body("Invalid data")
        );
      })
    );

    const mockAlert = jest.fn();
    global.alert = mockAlert;
    const { getByPlaceholderText, getByText } = render(<Signin />);

    fireEvent.change(getByPlaceholderText("Email"), {
      target: { value: "test@test.com" },
    });
    fireEvent.change(getByPlaceholderText("Password"), {
      target: { value: "invalidpassword" },
    });
    fireEvent.click(getByText("Log In"));

    await waitFor(() => expect(mockAlert).toBeCalled());
  });


  test("should display error message when form submitted with invalid credentials", async () => {
    server.resetHandlers(
      rest.post("http://localhost:8080/users/signin", (req, res, ctx) => {
        return res(
          // Respond with a 200 status code
          ctx.status(500),
          ctx.body("Invalid data")
        );
      })
    );

    const mockAlert = jest.fn();
    global.alert = mockAlert;
    const { getByPlaceholderText, getByText } = render(<Signin />);

    fireEvent.change(getByPlaceholderText("Email"), {
      target: { value: "test@test.com" },
    });
    fireEvent.change(getByPlaceholderText("Password"), {
      target: { value: "invalidpassword" },
    });
    fireEvent.click(getByText("Log In"));

    await waitFor(() => expect(mockAlert).toBeCalled());
  });

});
