import React from "react";
import { render, screen } from "@testing-library/react";
import { fireEvent, waitFor } from "@testing-library/react";
import { server } from "../../mocks/server";
import { rest } from "msw";
import Signup from "../../Components/signup";

const mockAlert = jest.fn();
global.alert = mockAlert;

describe("Testing SignUp Component", () => {
  
  
  test("Snapshot testing", () => {
    const { container } = render(<Signup />);
    expect(container).toMatchSnapshot();
  });

  test("Checking component is rendering or not", () => {
    render(<Signup />);
    const signingText = screen.getAllByText("Sign Up");
    const navText = screen.getAllByText("NEX Food Ordering");
    expect(signingText[0]).toBeInTheDocument();
    expect(navText[0]).toBeInTheDocument();
  });

  it("should submit form with valid credentials and redirect to SignIn page", async () => {
    // server.resetHandlers(
    //   rest.post("http://localhost:8080/users/createUser", (req, res, ctx) => {
    //     return res(
    //       // Respond with a 200 status code
    //       ctx.status(200),
    //       ctx.body("Registered")
    //     );
    //   })
    // );

    const props = {
      history: { push: jest.fn() },
    };

    const { getByPlaceholderText, getByText } = render(<Signup {...props} />);

    fireEvent.change(getByPlaceholderText("Email"), {
      target: { value: "test@test.com" },
    });
    fireEvent.change(getByPlaceholderText("Name"), {
      target: { value: "Aryan" },
    });
    fireEvent.change(getByPlaceholderText("Password"), {
      target: { value: "password" },
    });
    fireEvent.change(getByPlaceholderText("Confirm Password"), {
      target: { value: "password" },
    });
    fireEvent.click(getByText("Register"));

    await waitFor(() =>
      expect(props.history.push).toHaveBeenCalledWith({ pathname: "/" })
    );
  });

  it("should submit form with invalid credentials ", async () => {
    server.resetHandlers(
      rest.post("http://localhost:8080/users/createUser", (req, res, ctx) => {
        return res(
          // Respond with a 200 status code
          ctx.status(400),
          ctx.json({
            message: "error",
          })
        );
      })
    );

    const props = {
      history: { push: jest.fn() },
    };

    const { getByPlaceholderText, getByText } = render(<Signup {...props} />);

    fireEvent.change(getByPlaceholderText("Email"), {
      target: { value: "test@test.com" },
    });
    fireEvent.change(getByPlaceholderText("Name"), {
      target: { value: "Aryan" },
    });
    fireEvent.change(getByPlaceholderText("Password"), {
      target: { value: "password" },
    });
    fireEvent.change(getByPlaceholderText("Confirm Password"), {
      target: { value: "password" },
    });
    fireEvent.click(getByText("Register"));

    await waitFor(() =>
      expect(mockAlert).toBeCalledTimes(1)
    );
  });


  it("server Error ", async () => {
    server.resetHandlers(
      rest.post("http://localhost:8080/users/createUser", (req, res, ctx) => {
        return res(
          // Respond with a 200 status code
          ctx.status(500),
          ctx.json({
            message: "error",
          })
        );
      })
    );

    const props = {
      history: { push: jest.fn() },
    };

    const { getByPlaceholderText, getByText } = render(<Signup {...props} />);

    fireEvent.change(getByPlaceholderText("Email"), {
      target: { value: "test@test.com" },
    });
    fireEvent.change(getByPlaceholderText("Name"), {
      target: { value: "Aryan" },
    });
    fireEvent.change(getByPlaceholderText("Password"), {
      target: { value: "password" },
    });
    fireEvent.change(getByPlaceholderText("Confirm Password"), {
      target: { value: "password" },
    });
    fireEvent.click(getByText("Register"));

    await waitFor(() =>
      expect(mockAlert).toBeCalledTimes(1)
    );
  });


  it("should submit form with invalid credentials ", async () => {

    const props = {
      history: { push: jest.fn() },
    };

    const { getByPlaceholderText, getByText } = render(<Signup {...props} />);

    fireEvent.change(getByPlaceholderText("Email"), {
      target: { value: "test@test.com" },
    });
    fireEvent.change(getByPlaceholderText("Name"), {
      target: { value: "Aryan" },
    });
    fireEvent.change(getByPlaceholderText("Password"), {
      target: { value: "password" },
    });
    fireEvent.change(getByPlaceholderText("Confirm Password"), {
      target: { value: "password1" },
    });
    fireEvent.click(getByText("Register"));

    await waitFor(() =>
      expect(mockAlert).toBeCalledTimes(1)
    );
  });

});
