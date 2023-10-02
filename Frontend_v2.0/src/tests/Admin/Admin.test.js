import { render, screen } from "@testing-library/react";
import Admin from "../../Components/Admin";
import userEvent from "@testing-library/user-event";
import { act } from "react-dom/test-utils";
import React from "react";
import { fireEvent, waitFor } from "@testing-library/react";
import { server } from "../../mocks/server";
import { rest } from "msw";

describe("Testing Admin Component", () => {
  test("Snapshot testing", () => {
    const { container } = render(<Admin />);

    expect(container).toMatchSnapshot();
  });

  test("Checking component is rendering or not", () => {
    render(<Admin />);
    const signingText = screen.getAllByText("Admin Sign In");
    const navText = screen.getAllByText("NEX Food Ordering");
    expect(signingText[0]).toBeInTheDocument();
    expect(navText[0]).toBeInTheDocument();
  });


  test('renders Admin form elements correctly', () => {
    render(<Admin />);
    const idInput = screen.getByPlaceholderText('Admin ID');
    const passwordInput = screen.getByPlaceholderText('Password');
    const loginButton = screen.getByRole('button', { name: 'Log In' });
    expect(idInput).toBeInTheDocument();
    expect(passwordInput).toBeInTheDocument();
    expect(loginButton).toBeInTheDocument();
  });

  test('updates state correctly when input values change', () => {
    render(<Admin />);
    const idInput = screen.getByPlaceholderText('Admin ID');
    const passwordInput = screen.getByPlaceholderText('Password');
    fireEvent.change(idInput, { target: { value: '101' } });
    fireEvent.change(passwordInput, { target: { value: '101' } });
    expect(idInput).toHaveValue('101');
    expect(passwordInput).toHaveValue('101');
  });

//   test('submitting form with valid login details redirects to welcomea page', () => {
//     const mockPush = jest.fn();
//     jest.mock('react-router-dom', () => ({
//       ...jest.requireActual('react-router-dom'),
//       useHistory: () => ({
//         push: mockPush,
//       }),
//     }));
//     render(<Admin />);
//     const idInput = screen.getByPlaceholderText('Admin ID');
//     const passwordInput = screen.getByPlaceholderText('Password');
//     const loginButton = screen.getByRole('button', { name: 'Log In' });
//     fireEvent.change(idInput, { target: { value: '101' } });
//     fireEvent.change(passwordInput, { target: { value: '101' } });
//     fireEvent.click(loginButton);
    
//   });

  test('submitting form with invalid login details shows error message', async() => {
    render(<Admin />);
    const idInput = screen.getByPlaceholderText('Admin ID');
    const passwordInput = screen.getByPlaceholderText('Password');
    const loginButton = screen.getByRole('button', { name: 'Log In' });
    fireEvent.change(idInput, { target: { value: '102' } });
    fireEvent.change(passwordInput, { target: { value: '102' } });
    fireEvent.click(loginButton);
    const mockAlert = jest.fn();
    
  });
  
});