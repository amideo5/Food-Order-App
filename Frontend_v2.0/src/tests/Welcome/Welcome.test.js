import { render,screen } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import { act } from "react-dom/test-utils";
import Welcome from "../../Components/Welcome";


describe("Testing Welcome Component",()=>{

    beforeEach(()=>{
        localStorage.clear();
    })

    test("Snapshot testing",()=>{
        const {container} = render(<Welcome/>);

        expect(container).toMatchSnapshot();
    });

    test("Checking component is rendering or not",()=>{
        localStorage.setItem("token","sdjs");
        
        render(<Welcome/>);
        const welcome = screen.getByText("Welcome");
        const navText = screen.getAllByText("NEX Food Ordering");
        expect(welcome).toBeInTheDocument();
        expect(navText[0]).toBeInTheDocument();
    });

    test("Checking component is rendering or not",()=>{
        
        render(<Welcome/>);
        
        expect(screen.getByText("You have to login to use this application")).toBeInTheDocument();
        // expect(navText[0]).toBeInTheDocument();
    });


})