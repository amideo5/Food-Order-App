import React from "react";
import NavBarA from "../../Components/NavBarA";
import { getByText, render,screen } from "@testing-library/react";
import { fireEvent, waitFor } from "@testing-library/react";
import { server } from "../../mocks/server";
import { rest } from "msw";


describe("Testing NavBarA Component",()=>{

    test("Snapshot testing",()=>{
        const {container} = render(<NavBarA/>);

        expect(container).toMatchSnapshot();
    });

    test("Checking component is rendering or not",()=>{
        render(<NavBarA/>);
        localStorage.setItem("token","sdjs");
        
        const navText = screen.getAllByText("NEX Food Ordering");
        
        expect(navText[0]).toBeInTheDocument();
    });

    test("renders Add Food link correctly", () => {
        render(<NavBarA />);
        const addFoodLinkElement = screen.getByText(/Add Food/i);
        expect(addFoodLinkElement).toBeInTheDocument();
      });
    
      test("renders Update Food link correctly", () => {
        render(<NavBarA />);
        const updateFoodLinkElement = screen.getByText(/Update Food/i);
        expect(updateFoodLinkElement).toBeInTheDocument();
      });

});

describe("handleLogout function", () => {
    it("should remove token and email from local storage", async() => {
      render(<NavBarA />);
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