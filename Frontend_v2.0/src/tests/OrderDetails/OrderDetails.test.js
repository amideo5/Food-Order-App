import { render, screen } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import { act } from "react-dom/test-utils";
import OrderDetails from "../../Components/OrderDetails";
import { fireEvent, waitFor } from "@testing-library/react";

describe("Testing Order Details Component", () => {
  test("Snapshot testing", () => {
    const { container } = render(<OrderDetails />);

    expect(container).toMatchSnapshot();
  });

  test("Checking component is rendering or not", () => {
    render(<OrderDetails />);
    localStorage.setItem("token", "sdjs");
    const navText = screen.getAllByText("NEX Food Ordering");
    expect(navText[0]).toBeInTheDocument();
  });

  test("Normal flow", async () => {
    localStorage.setItem("email", "adn@gmail.com");
    render(<OrderDetails />);

    act(async ()=>{
        expect( await screen.findByText("Aryan")).toBeInTheDocument()
        expect(await screen.findByText("rajma")).toBeInTheDocument();
    })

  });
});
