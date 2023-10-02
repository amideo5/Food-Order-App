import React from "react";
import NavigationBar from "../../Components/NavigationBar";
import { getByText, render,screen } from "@testing-library/react";
import { fireEvent, waitFor } from "@testing-library/react";
import { server } from "../../mocks/server";
import { rest } from "msw";


describe("Testing Navbar Component",()=>{

    test("Snapshot testing",()=>{
        const {container} = render(<NavigationBar/>);

        expect(container).toMatchSnapshot();
    });

    test("Checking component is rendering or not",()=>{
        render(<NavigationBar/>);
        localStorage.setItem("token","sdjs");
        
        const navText = screen.getAllByText("NEX Food Ordering");
        
        expect(navText[0]).toBeInTheDocument();
    });

    test("renders Add Food link correctly", () => {
        render(<NavigationBar />);
        const addFoodLinkElement = screen.getByText(/Food List/i);
        expect(addFoodLinkElement).toBeInTheDocument();
      });
    
      test("renders Update Food link correctly", () => {
        render(<NavigationBar />);
        const updateFoodLinkElement = screen.getByText(/Orders/i);
        expect(updateFoodLinkElement).toBeInTheDocument();
      });

});

describe("handleLogout function", () => {
    it("should remove token and email from local storage", async() => {
      render(<NavigationBar />);
      const props = {
        history: { push: jest.fn() },
      };
      const mockAlert = jest.fn();
      localStorage.setItem("token", "test-token");
      localStorage.setItem("email", "test-email");
      
      fireEvent.click(screen.getByText("Logout"));

          
  
      expect(localStorage.getItem("token")).toBeNull();
      expect(localStorage.getItem("email")).toBeNull();
    });
  });