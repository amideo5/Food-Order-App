import React from "react";
import { render, screen } from "@testing-library/react";
import User from "../../Components/ChangeUser";
import userEvent from "@testing-library/user-event";
import { server } from "../../mocks/server";
import { rest } from "msw";
import { fireEvent, waitFor } from "@testing-library/react";

describe("User component", () => {
  test("Snapshot testing", () => {
    const { container } = render(<User />);

    expect(container).toMatchSnapshot();
  });

  it("updates the email input", () => {
    render(<User />);
    const emailInput = screen.getByPlaceholderText("Email");
    expect(emailInput).toBeInTheDocument();
    fireEvent.change(emailInput, "New Email Name");
    expect(emailInput).toHaveValue("");
  });

  it("updates the name input", () => {
    render(<User />);
    const nameInput = screen.getByPlaceholderText("Name");
    expect(nameInput).toBeInTheDocument();
    fireEvent.change(nameInput, "New Name");
    expect(nameInput).toHaveValue("");
  });

  it("updates the password input", () => {
    render(<User />);
    const passwordInput = screen.getByPlaceholderText("Password");
    expect(passwordInput).toBeInTheDocument();
    fireEvent.change(passwordInput, "12345678");
    expect(passwordInput).toHaveValue("");
  });

  it("updates the confirm password input", () => {
    render(<User />);
    const confirmpasswordInput =
      screen.getByPlaceholderText("Confirm Password");
    expect(confirmpasswordInput).toBeInTheDocument();
    fireEvent.change(confirmpasswordInput, "12345678");
    expect(confirmpasswordInput).toHaveValue("");
  });

  it("calls handleUpdate function when submit button is clicked", async () => {
    server.resetHandlers(
      rest.post("http://localhost:8080/users/updateUser/", (req, res, ctx) => {
        return res(
          // Respond with a 200 status code
          ctx.status(500),
          ctx.body("User Updated")
        );
      })
    );

    const props = {
      history: { push: jest.fn() },
    };

    const mockAlert = jest.fn();
    global.alert = mockAlert;

    const { getByPlaceholderText, getByText } = render(<User />);

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
    const but = screen.getAllByText("Update");
    fireEvent.click(but[1]);

    await waitFor(() => expect(mockAlert).toBeCalled());
  });

  it("Normal flow", async () => {

    // server.resetHandlers(
    //   rest.put("http://localhost:8080/users/updateUser/", (req, res, ctx) => {
    //     return res(
    //       // Respond with a 200 status code
    //       ctx.status(200),
    //       ctx.body("User Updated")
    //     );
    //   })
    // );

    const props = {
      history: { push: jest.fn() },
    };

    const mockAlert = jest.fn();
    global.alert = mockAlert;

    const { getByPlaceholderText, getByText } = render(<User />);

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
    const but = screen.getAllByText("Update");
    fireEvent.click(but[1]);

    await waitFor(() => expect(mockAlert).toBeCalledTimes(1));
    // expect(mockAlert).toBeCalledTimes(8)
  });

  it("All bad Both passwords are different", async () => {

    const props = {
      history: { push: jest.fn() },
    };

    const mockAlert = jest.fn();
    global.alert = mockAlert;

    const { getByPlaceholderText, getByText } = render(<User />);

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
      target: { value: "password2" },
    });
    const but = screen.getAllByText("Update");
    fireEvent.click(but[1]);

    await waitFor(() => expect(mockAlert).toBeCalledTimes(1));
    // expect(mockAlert).toBeCalledTimes(8)
  });

});
