import React from "react";
import NavBar from "../../Components/NavBar";
import { render,screen } from "@testing-library/react";

describe("Testing Navbar Component",()=>{

    test("Snapshot testing",()=>{
        const {container} = render(<NavBar/>);

        expect(container).toMatchSnapshot();
    });

    test("Checking component is rendering or not",()=>{
        render(<NavBar/>);
        localStorage.setItem("token","sdjs");
        
        const navText = screen.getAllByText("NEX Food Ordering");
        
        expect(navText[0]).toBeInTheDocument();
    });

    
      test("renders Sign In link correctly", () => {
        render(<NavBar />);
        const signInLinkElement = screen.getByText(/Sign In/i);
        expect(signInLinkElement).toBeInTheDocument();
      });
    
      test("renders Sign Up link correctly", () => {
        render(<NavBar />);
        const signUpLinkElement = screen.getByText(/Sign Up/i);
        expect(signUpLinkElement).toBeInTheDocument();
      });
    });