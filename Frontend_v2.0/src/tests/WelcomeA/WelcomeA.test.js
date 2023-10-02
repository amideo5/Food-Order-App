import { render,screen } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import { act } from "react-dom/test-utils";
import WelcomeA from "../../Components/WelcomeA";


describe("Testing WelcomeA Component",()=>{

    beforeEach(()=>{
        localStorage.clear();
    })

    test("Snapshot testing",()=>{
        const {container} = render(<WelcomeA/>);

        expect(container).toMatchSnapshot();
    });

    test("Checking component is rendering or not",()=>{
        localStorage.setItem("token","sdjs");
        
        render(<WelcomeA/>);
        const welcome = screen.getByText("ADMIN PAGE");
        const navText = screen.getAllByText("NEX Food Ordering");
        expect(welcome).toBeInTheDocument();
        expect(navText[0]).toBeInTheDocument();
    });

    test("Checking component is rendering or not",()=>{
        
        render(<WelcomeA/>);
        
        expect(screen.getByText("You have to login to use this application")).toBeInTheDocument();
        // expect(navText[0]).toBeInTheDocument();
    });

    
        // it('should navigate to /add when called', () => {
        //   const mockHistory = { push: jest.fn() };
        //   const props = { history: mockHistory };
        //   const handleAddFood = jest.fn();
        //   handleAddFood(props);
        //   expect(mockHistory.push).toHaveBeenCalledWith({ pathname: '/add' });
        // });
      
});